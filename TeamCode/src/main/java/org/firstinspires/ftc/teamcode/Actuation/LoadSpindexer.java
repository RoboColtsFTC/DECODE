package org.firstinspires.ftc.teamcode.Actuation;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector.Color;

public class LoadSpindexer {
public enum State {
    Empty,
    LoadOne,
    OneLoaded,
    LoadTwo,
    TwoLoaded,
    LoadThree,
    ThreeLoaded,
    Loaded

}

public boolean Start = false;
public boolean exit = false;

public ActuatorControl actuatorcontrol;

public Color ColorState;

public ColorDetector colordetector;

public Actuators actuators;

public Color Position1 =Color.UNKNOWN;
public Color Position2 =Color.UNKNOWN;
public Color Position3 =Color.UNKNOWN;
State Currentstate;
public LinearOpMode opmode;



    public LoadSpindexer(LinearOpMode opmode,Actuators actuators){ // Cunstructor

        Currentstate=State.Empty;
        this.opmode=opmode;
        this.actuators=actuators;
    }

    public void Run(){

        if(opmode.gamepad1.b) {
            Start=true;
                 }



// State machine to load Spindexer

    switch(Currentstate){
        case Empty:
     if(Start){
         Currentstate = State.LoadOne;
     }
            break;
        case LoadOne:
            actuators.spindexercontrol.setPosition(1);  // set spindexer to first position
            LoadBall(Position1,State.LoadTwo);
            break;
        case OneLoaded:
            break;
        case LoadTwo:
            actuators.spindexercontrol.setPosition(2);  // set spindexer to first position
            LoadBall(Position2,State.LoadThree);
            break;
        case TwoLoaded:
            break;
        case LoadThree:
            actuators.spindexercontrol.setPosition(3); // set spindexer to first position
            LoadBall(Position3,State.Loaded);
            break;
        case ThreeLoaded:
            break;
        case Loaded:
            break;

        }

    }

public void LoadBall( Color Position, State NextState)  {

        actuators.IntakeMotor.StartMotor(); // Start Intake motor
        actuators.feedcontrol.Forward();    // Set Direction of feed
        actuators.feedcontrol.startFeed();  // Start Feed Rollers
        //While loop till detection occurs
        if(colordetector.colordetected()) {         // check to see if the ball is in the hopper.
            if(NextState==State.LoadThree){         // if third state use kicker to feed the last ball
                actuators.FeedKicker.SetSecond();   // Kick bal into launcher
                opmode.sleep(2000);      // wait 2 seconds
                actuators.FeedKicker.SetFirst();    // Return Back to second position.

            }
            actuators.feedcontrol.StopFeed();       // Stop feed
            Position = colordetector.GetColor();    // Get Color Detected
            Currentstate = NextState;               // Move to next state
        }
    }


    public void StartLoading(){  //Starts Loading Sequence
        Start=true;

    }

}
