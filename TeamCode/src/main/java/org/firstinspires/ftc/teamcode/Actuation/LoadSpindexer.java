package org.firstinspires.ftc.teamcode.Actuation;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector.DetColor;

import java.util.Arrays;
import java.util.List;
@Config
public class LoadSpindexer {
public static enum State {
    Empty,
    LoadOne,
    LoadTwo,
    LoadThree,
    Loaded

}

public static boolean Start = false;
public static boolean exit = false;


public ColorDetector colordetector;

public Actuators actuators;

public static List<DetColor> colorPos;

State Currentstate;

private final ElapsedTime timer = new ElapsedTime();

public  static long CycleTime=5000;
public  static long CycleTimeFeed=5000;

public LinearOpMode opmode;



    public LoadSpindexer(LinearOpMode opmode,Actuators actuators,List<DetColor> colorPos){ // Cunstructor
        this.colorPos=colorPos;
        Currentstate=State.Empty;
        this.opmode=opmode;
        this.actuators=actuators;
    }
    boolean rebounceb=false;

    public void Run(){

        if(opmode.gamepad1.b && !rebounceb && (ActuatorControl.controlstate==ActuatorControl.ControlState.ready)) {
            ActuatorControl.controlstate=ActuatorControl.ControlState.loading;
            Start=true;
        }
        rebounceb=opmode.gamepad1.b;


// State machine to load Spindexer

        switch(Currentstate) {
            case Empty:

                if (Start) {

                    Currentstate = State.LoadOne;

                }
                break;
            case LoadOne:
                LoadBall(colorPos, State.LoadTwo, 1);
                break;
            case LoadTwo:
                LoadBall(colorPos, State.LoadThree, 2);
                break;
            case LoadThree:
                LoadBall(colorPos, State.Loaded, 3);
                break;
            case Loaded:
                if (timer.milliseconds() >= CycleTime){
                    ActuatorControl.controlstate = ActuatorControl.ControlState.ready;
                    timer.reset();
                }
                break;

            }

        }


    public enum ControlState{
        Ready,
        Position,
        DetectColor,
        kickball
    }

   public static ControlState controlstate=ControlState.Ready;
    public Boolean rebounce = false;
    public Boolean switch1 = true;
public void LoadBall( List<DetColor> colorPos, State NextState,int SpindexPos)  {

        // Cycle time controls t
    switch(controlstate) {
        case Ready:
            controlstate = ControlState.Position;
            actuators.IntakeMotor.StartMotor();
            actuators.feedcontrol.startFeed();

            break;
        case Position:


            if (SpindexPos == 1 && switch1 ) {
                actuators.spindexercontrol.setPosition(SpindexPos);
                switch1 = false;
            } else {
                actuators.feedcontrol.StopFeed();

                if (timer.milliseconds() >= CycleTimeFeed) {

                    actuators.spindexercontrol.setPosition(SpindexPos);  // set spindexer to first position
                    timer.reset();
                    controlstate=ControlState.DetectColor;
                    actuators.feedcontrol.startFeed();
                }

            }




            break;
        case DetectColor:

            if (opmode.gamepad1.y && !rebounce){  //colordetector.colordetected()  check to see if the ball is in the spindexer using Gampad as backup
                if (Currentstate== State.LoadThree) {                   // if third state use kicker to feed the last ball
                    controlstate=ControlState.kickball;
                }else {
                                   // Stop feed
                    //colorPos.set(SpindexPos,colordetector.GetColor());              // Get Color Detected
                    Currentstate = NextState;
                    controlstate=ControlState.Ready;                  // Move to next state
                }


            }
            rebounce=opmode.gamepad1.y;
            break;
        case kickball:
            if(timer.milliseconds()>=CycleTime){
                timer.reset();
                controlstate= ControlState.Ready;

            }else {
                actuators.LaunchKicker.SetFirst();
                actuators.feedcontrol.StopFeed();
            }
            break;
    }

    }

    public void StartLoading(){  //Starts Loading Sequence
        Start=true;

    }

    public void LoadOne(){
        Currentstate=State.LoadThree;
    }

    public void LoadTwo(){

        Currentstate=State.LoadTwo;

    }



}
