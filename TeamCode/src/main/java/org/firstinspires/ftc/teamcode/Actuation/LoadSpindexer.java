package org.firstinspires.ftc.teamcode.Actuation;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector.DetColor;
@Config
public class LoadSpindexer {
public enum State {
    Empty,
    LoadOne,
    LoadTwo,
    LoadThree,
    Loaded

}

public boolean Start = false;
public boolean exit = false;


public ColorDetector colordetector;

public Actuators actuators;
public DetColor Position1 =DetColor.UNKNOWN;
public DetColor Position2 =DetColor.UNKNOWN;
public DetColor Position3 =DetColor.UNKNOWN;
State Currentstate;

private final ElapsedTime timer = new ElapsedTime();

public  static long CycleTime=2000;
public LinearOpMode opmode;



    public LoadSpindexer(LinearOpMode opmode,Actuators actuators){ // Cunstructor

        Currentstate=State.Empty;
        this.opmode=opmode;
        this.actuators=actuators;
    }

    public void Run(){

        if(opmode.gamepad1.b && (ActuatorControl.controlstate==ActuatorControl.ControlState.ready)) {
            ActuatorControl.controlstate=ActuatorControl.ControlState.loading;
            Start=true;
        }



// State machine to load Spindexer

        switch(Currentstate) {
            case Empty:
                if (Start) {
                    Currentstate = State.LoadOne;
                }
                break;
            case LoadOne:
                LoadBall(Position1, State.LoadTwo, 1);
                break;
            case LoadTwo:
                LoadBall(Position2, State.LoadThree, 2);
                break;
            case LoadThree:
                LoadBall(Position3, State.Loaded, 3);
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

    ControlState controlstate;
public void LoadBall( DetColor Position, State NextState,int SpindexPos)  {

        // Cycle time controls t
    switch(controlstate){
        case Ready:
            controlstate=ControlState.Position;
            break;
        case Position:
            actuators.spindexercontrol.setPosition(SpindexPos);  // set spindexer to first position
            if(timer.milliseconds()>=CycleTime){
                controlstate=ControlState.DetectColor;
                timer.reset();
            }
            break;
        case DetectColor:

            if (colordetector.colordetected() || opmode.gamepad1.y){  // check to see if the ball is in the spindexer using Gampad as backup
                if (NextState == State.LoadThree) {                   // if third state use kicker to feed the last ball
                    controlstate=ControlState.kickball;
                }else {
                    actuators.feedcontrol.StopFeed();                 // Stop feed
                    Position = colordetector.GetColor();              // Get Color Detected
                    Currentstate = NextState;
                    controlstate=ControlState.Ready;                  // Move to next state
                }

            }
            break;
        case kickball:
            if(timer.milliseconds()>=CycleTime){
                timer.reset();
                controlstate= ControlState.Ready;

            }else {
                actuators.LaunchKicker.SetFirst();
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
