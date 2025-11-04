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

public static State Currentstate=State.Empty;


public  static long CycleTime=2000;
public  static long CycleTimeFeed=5000;

public LinearOpMode opmode;



    public LoadSpindexer(LinearOpMode opmode,Actuators actuators,List<DetColor> colorPos){ // Cunstructor
       colordetector=new ColorDetector(opmode);
        this.colorPos=colorPos;
        Currentstate=State.Empty;
        this.opmode=opmode;
        this.actuators=actuators;
    }
    boolean rebounceb=false;

    public void run(){
        colordetector.run();

        if(opmode.gamepad2.a && !rebounceb && (ActuatorControl.controlstate==ActuatorControl.ControlState.ready)) {
            ActuatorControl.controlstate=ActuatorControl.ControlState.loading;
            Start=true;
        }
        rebounceb=opmode.gamepad2.a;



// State machine to load Spindexer

        switch(Currentstate) {
            case Empty:

                if (Start && !SpindexerLoaded) {

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
                ActuatorControl.controlstate = ActuatorControl.ControlState.ready;
                break;

            }


    }


    public enum ControlState{
        Ready,
        StartIntake,
        Position,
        DetectColor,
        kickball
    }

   public static ControlState controlstate=ControlState.Ready;
    public Boolean rebounce = false;
    public static Boolean SpindexerLoaded=false;

public void LoadBall( List<DetColor> colorPos, State NextState,int SpindexPos)  {

        // Cycle time controls t
    switch(controlstate) {
        case Ready:
            if (!SpindexerLoaded && Start) {
                controlstate = ControlState.StartIntake;
            }
            break;
        case StartIntake:
            actuators.IntakeMotor.StartMotor();
            actuators.feedcontrol.startFeed();
            controlstate = ControlState.Position;
            break;
        case Position:

            if (SpindexPos == 1 ) {
                actuators.spindexercontrol.setPosition(SpindexPos);
                controlstate=ControlState.DetectColor;

            } else {
                actuators.feedcontrol.StopFeed();
                opmode.sleep(500);
                actuators.spindexercontrol.setPosition(SpindexPos);
                opmode.sleep(500);
                actuators.feedcontrol.startFeed();
                controlstate = ControlState.DetectColor;

                }

            break;
        case DetectColor:
            if (Currentstate== State.LoadThree){
                colordetector.maxdist=8;
            }

            if (opmode.gamepad2.b && !rebounce || colordetector.colordetected()){  //colordetector.colordetected()  check to see if the ball is in the spindexer using Gampad as backup
                if (Currentstate== State.LoadThree) {                   // if third state use kicker to feed the last ball

                    controlstate=ControlState.kickball;
                }else {
                                   // Stop feed
                    colorPos.set(SpindexPos,colordetector.GetColor());              // Get Color Detected
                    Currentstate = NextState;
                    controlstate=ControlState.Ready;                  // Move to next state
                }


            }
            rebounce=opmode.gamepad2.b;
            break;
        case kickball:
            actuators.FeedKicker.SetSecond();
            opmode.sleep(500);
            actuators.FeedKicker.SetFirst();
            actuators.feedcontrol.StopFeed();
            actuators.IntakeMotor.StopMotor();
            SpindexerLoaded=true;
            controlstate= ControlState.Ready;
            Start=false;

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
