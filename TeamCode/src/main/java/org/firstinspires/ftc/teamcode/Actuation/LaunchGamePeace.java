package org.firstinspires.ftc.teamcode.Actuation;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;
import org.firstinspires.ftc.teamcode.Perception.AprilTag;
import org.firstinspires.ftc.teamcode.Perception.AprilTagData;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector.DetColor;




import java.util.Arrays;
import java.util.List;
@Config
public class LaunchGamePeace {
    private boolean Rebounce = false;
    public Actuators actuators;
    public LinearOpMode opmode;
    public static long CycleTime;

    public AprilTagData TagData;
    List<ColorDetector.DetColor> colorPos;

    private final ElapsedTime timer = new ElapsedTime();

    public LaunchGamePeace(LinearOpMode opmode, Actuators actuators, List<ColorDetector.DetColor> colorPos){
        this.colorPos=colorPos;
        this.opmode=opmode;
        this.actuators = actuators;

   }

   public void Run(){

       if(opmode.gamepad1.a && !Rebounce && ActuatorControl.controlstate==ActuatorControl.ControlState.ready) {

           lanchall();
           ActuatorControl.controlstate=ActuatorControl.ControlState.launching;

       }

   }


    public enum SpindleState {

       AllBallsLoaded,
        TwoBallsLoaded,
        OneBallLoaded,
        Empty


    }

    public enum ControlState{
        Ready,
        SetPosition,
        KickBall

    }
    ControlState controlstate;
   SpindleState BallState;
   public void launch(int pos){

       switch (controlstate){
           case Ready:
               actuators.LauncherMotor.StartMotor();
               if(timer.milliseconds()>=CycleTime){
                   timer.reset();
                   controlstate=ControlState.SetPosition;
               }
               break;

           case SetPosition:
               actuators.spindexercontrol.setPosition(pos);  // increments betweeen all positions
               if(timer.milliseconds()>=CycleTime){
                   timer.reset();
                   controlstate=ControlState.KickBall;
               }
               break;

           case KickBall:
               if(timer.milliseconds()>=CycleTime){
                   timer.reset();
                   controlstate=ControlState.Ready;

               }else {
                   actuators.LaunchKicker.SetFirst();
               }
               break;
       }



   }

   public void lanchall(){

       switch(BallState){

           case AllBallsLoaded:
               launch(6);


                   BallState = SpindleState.TwoBallsLoaded;

             break;

           case TwoBallsLoaded:
               launch(5);

                   BallState = SpindleState.OneBallLoaded;

           break;

           case OneBallLoaded:

               launch(4);
                   BallState = SpindleState.Empty;

               break;

           case Empty:
                if (timer.milliseconds()>=CycleTime) {
                    ActuatorControl.controlstate = ActuatorControl.ControlState.loading;
                    timer.reset();
                }
               break;

       }

   }
    public void lanchorder(List<Integer> order){

        switch(BallState){

            case AllBallsLoaded:
                launch(order.get(1));


                BallState = SpindleState.TwoBallsLoaded;

                break;

            case TwoBallsLoaded:
                launch(order.get(2));

                BallState = SpindleState.OneBallLoaded;

                break;

            case OneBallLoaded:

                launch(order.get(3));
                BallState = SpindleState.Empty;

                break;

            case Empty:
                if (timer.milliseconds()>=CycleTime) {
                    ActuatorControl.controlstate = ActuatorControl.ControlState.loading;
                    timer.reset();
                }
                break;

        }

    }


    public void launchByCode(){
        List<DetColor> Code=Arrays.asList(DetColor.UNKNOWN,DetColor.UNKNOWN, DetColor.UNKNOWN);
       switch(TagData.DetectedCode.CodeID) {
           case GPP:

                Code= Arrays.asList(DetColor.GREEN, DetColor.PURPLE, DetColor.PURPLE);

                break;
           case PGP:
                Code= Arrays.asList(DetColor.PURPLE, DetColor.GREEN, DetColor.PURPLE);

               break;
           case PPG:
               Code= Arrays.asList(DetColor.PURPLE, DetColor.PURPLE, DetColor.GREEN);

               break;
       }
       // Todo Add code to launch ball by detected code
        List<Integer> Lorder =MarchLists(colorPos,Code);

        lanchorder(Lorder);





    }

// Method used to determien fire order
    public static List<Integer> MarchLists(List<DetColor> ListA, List<DetColor> ListB) {

        List<Integer> IDList= Arrays.asList(0, 0, 0);
        Boolean[] FlagA={true,true,true};
        Boolean[] FlagB={true,true,true};

        for(int n =0;n<ListA.size();n++){


            for(int m =0;m<ListB.size();m++){


                if(ListA.get(n).equals(ListB.get(m))&FlagA[n]&&FlagB[m]){

                    IDList.set(n,m+1);
                    FlagA[n] = false;
                    FlagB[m] = false;


                }





            }
        }
        return IDList;
    }

}
