package org.firstinspires.ftc.teamcode.Actuation;

import static java.lang.Thread.sleep;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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

    public LoadSpindexer(ActuatorControl.Actuators actuators){ // Cunstructor

        Currentstate=State.Empty;
    }

    public void Run(){
// State machine to load Spindexer

    switch(Currentstate){
        case Empty:
     if(Start){
         Currentstate = State.LoadOne;
     }
            break;
        case LoadOne:
            LoadBall(Position1,State.LoadTwo);
            break;
        case OneLoaded:
            break;
        case LoadTwo:
            LoadBall(Position2,State.LoadThree);
            break;
        case TwoLoaded:
            break;
        case LoadThree:
            LoadBall(Position3,State.Loaded);
            break;
        case ThreeLoaded:
            break;
        case Loaded:
            break;

        }

    }

public void LoadBall( Color Position, State NextState)  {

        actuators.IntakeMotor.StartMotor();
        actuators.feedcontrol.Forward();
        actuators.feedcontrol.startFeed();
        //While loop till detection occurs
        if(colordetector.colordetected()) {
            if(NextState==State.LoadThree){
                actuators.FeedKicker.SetSecond();

                actuators.FeedKicker.SetFirst();

            }
            actuators.feedcontrol.StopFeed();
            Position = colordetector.GetColor();
            Currentstate = NextState;
        }
    }
}
