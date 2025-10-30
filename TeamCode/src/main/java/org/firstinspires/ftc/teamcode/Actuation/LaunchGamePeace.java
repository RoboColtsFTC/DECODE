package org.firstinspires.ftc.teamcode.Actuation;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;

import java.text.BreakIterator;


public class LaunchGamePeace {
    private boolean Rebounce = false;
    public Actuators actuators;
    public LinearOpMode opmode;
   public LaunchGamePeace(LinearOpMode opmode, Actuators actuators){

       this.opmode=opmode;
       this.actuators = actuators;

   }

   public void Run(){

       if(opmode.gamepad1.a && !Rebounce) {

           lanchall();

       }

   }

   public void lanchall(){

       for (int n=6;n>=3;n--) {
           actuators.LauncherMotor.StartMotor();
           opmode.sleep(4000); // Wait for motor to hit speed
           actuators.spindexercontrol.setPosition(n);  // increments betweeen all positions
           opmode.sleep(4000); // Wait for position 1
           actuators.LaunchKicker.SetFirst();
           opmode.sleep(4000); // Wait for position 1
           actuators.LaunchKicker.SetFirst();
           opmode.sleep(4000); // Wait for position 1
       }

   }

    public enum SpindleState {

       AllBallsLoaded,
        TwoBallsLoaded,
        OneBallLoaded,
        Empty


    }

   SpindleState BallState;
   public void launch(int pos){
       actuators.LauncherMotor.StartMotor();
       opmode.sleep(4000); // Wait for motor to hit speed
       actuators.spindexercontrol.setPosition(pos);  // increments betweeen all positions
       opmode.sleep(4000); // Wait for position 1
       actuators.LaunchKicker.SetFirst();
       opmode.sleep(4000); // Wait for position 1
       actuators.LaunchKicker.SetSecond();
       opmode.sleep(4000); // Wait for position 1

   }

   public void lanchall_case(){

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

               break;

       }

   }








    public void launchByCode(){

       // Todo Add code to launch ball by detected code

    }

}
