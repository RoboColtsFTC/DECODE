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
private final ElapsedTime ControlFeedTimer = new ElapsedTime();
private final ElapsedTime KickerTimer = new ElapsedTime();

    public LoadSpindexer(LinearOpMode opmode,Actuators actuators,List<DetColor> colorPos){ // Cunstructor
       colordetector=new ColorDetector(opmode);
        this.colorPos=colorPos;
        Currentstate=State.Empty;
        this.opmode=opmode;
        this.actuators=actuators;
        ControlFeedTimer.reset();
        KickerTimer.reset();
    }
    boolean rebounceb=false;

    public void run(){
        colordetector.run();

        // if(opmode.gamepad2.a && !rebounceb && (ActuatorControl.controlstate==ActuatorControl.ControlState.ready)) {
        //     ActuatorControl.controlstate=ActuatorControl.ControlState.loading;
        //     Start=true;
        // }
        // rebounceb=opmode.gamepad2.a;



// State machine to load Spindexer

        switch(Currentstate) {
            case Empty:
                if(opmode.gamepad2.a  && (ActuatorControl.controlstate==ActuatorControl.ControlState.ready)) {
                    ActuatorControl.controlstate=ActuatorControl.ControlState.loading;
                    urrentstate = State.LoadOne;
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


    public enum KickerState{
        IDLE,
        KICK,
        RETURNTOPOSITION
    }
public KickerState kickerstate = KickerState.IDLE;
    
    pubplic void ActuateFeedKicker(boolean start){
        switch(kickerstate){
                case: IDLE
                if(start){
                    
                    KickerTimer.reset();
                    kickerstate = KickerState.Kick
                }
                
                break;
                case: KICK
                 actuators.FeedKicker.SetSecond();
            
                if(KickerTimer.milliseconds>=500){
                 
                     
                    KickerTimer.reset();
                    kickerstate = KickerState.RETURNTOPOSITION
                }
                break;
                case: RETURNTOPOSITION
                    actuators.FeedKicker.SetFirst();
                    if(KickerTimer.milliseconds>=500){
                        actuators.feedcontrol.StopFeed();
                        actuators.IntakeMotor.StopMotor();
                        SpindexerLoaded=true;
                        controlstate= ControlState.Ready;
                        start=false;
                         KickerTimer.reset();
                        kickerstate = KickerState.IDLE
                }
                
                break;
                           
        }
        
    }
    
public enum FeedState{
    IDLE,
    STOPFEED,
    CHANGEPOSITION,
    STARTFEED
}
    
FeedState feedstate=FeedState.IDLE;
    public void ActuateFeed(int SpindexPos){
        switch(feedstate){
                case: IDLE
                    ControlFeedTimer.reset();
                    feedstate=FeedState.STOPFEED;
                    break;
                case: STOPFEED
                     actuators.feedcontrol.StopFeed();
                if(ControlFeedTimer.milliseconds()>=500){
                    
                    ControlFeedTimer.reset();
                    feedstate=FeedState.CHANGEPOSITION;
                }
                     break;
                case: CHANGEPOSITION
              
                  actuators.spindexercontrol.setPosition(SpindexPos);
                  if(ControlFeedTimer.milliseconds()>=500){
                        ControlFeedTimer.reset();
                        feedstate=FeedState.STARTFEED;
                  }
                     break;
                case:STARTFEED
                         actuators.feedcontrol.startFeed();
                         controlstate = ControlState.DetectColor;
                         feedstate=FeedState.IDLE;
                
                     break;
                            
        }
        
    }
    
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
                ActuateFeed(int SpindexPos)
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
            ActuateFeedKicker(boolean start)

            break;
    }

    }

    // public void StartLoading(){  //Starts Loading Sequence
    //     Start=true;

    // }

    // public void LoadOne(){
    //     Currentstate=State.LoadThree;
    // }

    // public void LoadTwo(){

    //     Currentstate=State.LoadTwo;

    //}



}
