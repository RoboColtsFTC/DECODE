package org.firstinspires.ftc.teamcode.Actuation;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;
import org.firstinspires.ftc.teamcode.Perception.AprilTagData;

import java.text.BreakIterator;

@Config
public class LaunchGamePeace {
    private boolean Rebounce = false;
    public Actuators actuators;
    public LinearOpMode opmode;
    public static long CycleTime;

    public AprilTagData TagData;

    private final ElapsedTime timer = new ElapsedTime();

    public LaunchGamePeace(LinearOpMode opmode, Actuators actuators){

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


    public void launchByCode(){



       // Todo Add code to launch ball by detected code

    }

}
