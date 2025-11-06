package org.firstinspires.ftc.teamcode.Actuation;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector.DetColor;


import java.util.List;
@Config
public class LoadSpindexer {
public enum State {
    Empty,
    LoadOne,
    LoadTwo,
    LoadThree,
    Loaded

}



public ColorDetector colordetector;

public Actuators actuators;

public static List<DetColor> colorPos;

public static State Currentstate=State.Empty;


public LinearOpMode opmode;
private final ElapsedTime ControlFeedTimer = new ElapsedTime();
private final ElapsedTime KickerTimer = new ElapsedTime();

    public LoadSpindexer(LinearOpMode opmode,Actuators actuators,List<DetColor> colorPos){ // Cunstructor
       colordetector=new ColorDetector(opmode);
        LoadSpindexer.colorPos =colorPos;
        Currentstate=State.Empty;
        this.opmode=opmode;
        this.actuators=actuators;
        ControlFeedTimer.reset();
        KickerTimer.reset();
    }


    public void run(){

        colordetector.run();
        // State machine to load Spindexer

        switch(Currentstate) {
            case Empty:
                if(opmode.gamepad2.a && (ActuatorControl.controlstate==ActuatorControl.ControlState.ready)) {
                    ActuatorControl.controlstate=ActuatorControl.ControlState.loading;
                    Currentstate = State.LoadOne;
                }

                break;
            case LoadOne:
                LoadGamePeace( 1,true);

                if(gamepeaceloadingstate==GamePeaceLoadingState.IDLE){
                    Currentstate=State.LoadTwo;
                }

                break;
            case LoadTwo:
                LoadGamePeace( 2,true);

                if(gamepeaceloadingstate==GamePeaceLoadingState.IDLE){
                    Currentstate=State.LoadTwo;
                }

                break;
            case LoadThree:
                LoadGamePeace( 3,true);

                if(gamepeaceloadingstate==GamePeaceLoadingState.IDLE){
                    Currentstate=State.Loaded;
                    ActuatorControl.controlstate = ActuatorControl.ControlState.ready;
                }

                break;
            }
    }
    public enum GamePeaceLoadingState{
        IDLE,
        StartIntake,
        Position,
        DetectColor,
        kickball
    }

    public static GamePeaceLoadingState gamepeaceloadingstate=GamePeaceLoadingState.IDLE;
    public void LoadGamePeace(int SpindexPos,boolean start)  {

        // Cycle time controls t
        switch(gamepeaceloadingstate) {
            case IDLE:
                if (start) {
                    gamepeaceloadingstate = GamePeaceLoadingState.StartIntake;
                }
                break;
            case StartIntake:
                actuators.IntakeMotor.StartMotor();
                actuators.feedcontrol.startFeed();
                gamepeaceloadingstate = GamePeaceLoadingState.Position;
                break;
            case Position:

                if (SpindexPos == 1 ) {
                    actuators.spindexercontrol.setPosition(SpindexPos);
                    gamepeaceloadingstate=GamePeaceLoadingState.DetectColor;

                } else {
                    ActuateFeed(SpindexPos,true);

                    if(feedstate==FeedState.IDLE) {
                        gamepeaceloadingstate = GamePeaceLoadingState.DetectColor;
                    }

                }

                break;
            case DetectColor:
                DetectGamePeace(SpindexPos, true);

                if(detectgamepeace==DetectGamePeace.IDLE){
                    if (Currentstate==State.LoadThree){
                        gamepeaceloadingstate = GamePeaceLoadingState.kickball;
                    }else{
                        gamepeaceloadingstate=GamePeaceLoadingState.IDLE;
                    }
                }

                break;
            case kickball:
                ActuateFeedKicker(true);

                if (kickerstate == KickerState.IDLE){
                    gamepeaceloadingstate=GamePeaceLoadingState.IDLE;
                }

                break;
        }

    }



    public enum DetectGamePeace{
        IDLE,

        DetectGamePeace,
        ManualOveride,
        RecordColor
    }
    DetectGamePeace detectgamepeace=DetectGamePeace.IDLE;
    public void DetectGamePeace(int SpindexPos, boolean start){

        switch(detectgamepeace){
            case IDLE:
                if(start) {
                    if (Currentstate == State.LoadThree) {
                        colordetector.maxdist = 8;
                    }
                    detectgamepeace=DetectGamePeace.DetectGamePeace;
                }
                break;
            case DetectGamePeace:
                if ( colordetector.colordetected()){
                    detectgamepeace=DetectGamePeace.RecordColor;
                } else if(opmode.gamepad2.b) {
                    detectgamepeace=DetectGamePeace.ManualOveride;
                }
                break;
            case ManualOveride:
                colorPos.set(SpindexPos,DetColor.UNKNOWN);
                detectgamepeace=DetectGamePeace.IDLE;

                break;
            case RecordColor:
                colorPos.set(SpindexPos,colordetector.GetColor());
                detectgamepeace=DetectGamePeace.IDLE;

                break;


        }

    }



    public enum KickerState{
        IDLE,
        KICK,
        RETURNTOPOSITION
    }


    public KickerState kickerstate = KickerState.IDLE;
    
    public void ActuateFeedKicker(boolean start){
        switch(kickerstate){
            case IDLE:
                if(start){
                    
                    KickerTimer.reset();
                    kickerstate = KickerState.KICK;
                }
                
                break;
            case KICK:
                 actuators.FeedKicker.SetSecond();
            
                if(KickerTimer.milliseconds()>=500){
                 
                     
                    KickerTimer.reset();
                    kickerstate = KickerState.RETURNTOPOSITION;
                }
                break;
            case RETURNTOPOSITION:
                    actuators.FeedKicker.SetFirst();
                    if(KickerTimer.milliseconds()>=500){
                        actuators.feedcontrol.StopFeed();
                        actuators.IntakeMotor.StopMotor();
                        KickerTimer.reset();
                        kickerstate = KickerState.IDLE;
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
    public void ActuateFeed(int SpindexPos,Boolean Start){
        switch(feedstate){
            case IDLE:
                if(Start) {
                    ControlFeedTimer.reset();
                    feedstate = FeedState.STOPFEED;
                }
                break;
            case STOPFEED:
                actuators.feedcontrol.StopFeed();
                if(ControlFeedTimer.milliseconds()>=500){

                    ControlFeedTimer.reset();
                    feedstate=FeedState.CHANGEPOSITION;
                }
                break;
            case CHANGEPOSITION:
              
              actuators.spindexercontrol.setPosition(SpindexPos);
              if(ControlFeedTimer.milliseconds()>=500){
                    ControlFeedTimer.reset();
                    feedstate=FeedState.STARTFEED;
              }
                 break;
            case STARTFEED:
                 actuators.feedcontrol.startFeed();
                 feedstate=FeedState.IDLE;

                break;
                            
        }
        
    }



}
