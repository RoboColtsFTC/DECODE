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

       if(opmode.gamepad2.x && !Rebounce && ActuatorControl.controlstate==ActuatorControl.ControlState.ready) {

           ActuatorControl.controlstate=ActuatorControl.ControlState.launching;
           actuators.LauncherMotor.StartMotor();
           lanchall();


       }
       Rebounce=opmode.gamepad2.x;

   }


   public void lanchall(){
       opmode.sleep(3000);
       launch(6);
       launch(5);
       launch(4);
       LoadSpindexer.SpindexerLoaded=false;
       actuators.LauncherMotor.StopMotor();
       ActuatorControl.controlstate=ActuatorControl.ControlState.ready;
       LoadSpindexer.Currentstate= LoadSpindexer.State.Empty;
       LoadSpindexer.SpindexerLoaded=false;

   }

    public void launch(int pos){

        opmode.sleep(500);
        actuators.spindexercontrol.setPosition(pos);  // increments betweeen all positions
        opmode.sleep(500);
        actuators.LaunchKicker.SetSecond();
        opmode.sleep(500);
        actuators.LaunchKicker.SetFirst();

    }
    public void lanchorder(List<Integer> order){

        opmode.sleep(6000);
        launch(order.get(1));
        launch(order.get(2));
        launch(order.get(3));


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
