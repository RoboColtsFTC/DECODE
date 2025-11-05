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
    private boolean Rebounce1,Rebounce2,Rebounce3,Rebounce4 = false;
    public Actuators actuators;
    public LinearOpMode opmode;
    public static long CycleTime;

    public AprilTagData TagData;
    List<ColorDetector.DetColor> colorPos;

    private final ElapsedTime LauncherMotorTimer = new ElapsedTime();
    private final ElapsedTime LoadGamePeaceTimer  = new ElapsedTime();

    public LaunchGamePeace(LinearOpMode opmode, Actuators actuators, List<ColorDetector.DetColor> colorPos){
        this.colorPos=colorPos;
        this.opmode=opmode;
        this.actuators = actuators;
        LauncherMotorTimer.reset()   

   }
    public enum LauncherState {
        IDLE,
        MOTORSTARTUP,
        ACTIVELAUNCHALL,
        ACTIVELAUNCHBYCODE
              
    }

    public launcherstate = LauncherState.IDLE;
    public List<Integer> LaunchOrder= Arrays.asList(6, 5, 4);  // Defalt sequnce

    
   public void run(){

       Switch(launcherstate){

           case: IDLE{
        // Far Launching
               if(opmode.gamepad2.x && ActuatorControl.controlstate==ActuatorControl.ControlState.ready) {
        
                   ActuatorControl.controlstate=ActuatorControl.ControlState.launching;
                   actuators.LauncherMotor.SetPower(.73);
                   actuators.LauncherMotor.StartMotor();
                   LauncherMotorTimer.reset();
                   LaunchOrder= Arrays.asList(6, 5, 4); 
                   launcherstate= LauncherState.ACTIVELAUNCHALL;
                   
        
               }
               // Close Launching
              
        
               if(opmode.gamepad2.y && ActuatorControl.controlstate==ActuatorControl.ControlState.ready) {
        
                   ActuatorControl.controlstate=ActuatorControl.ControlState.launching;
                   actuators.LauncherMotor.SetPower(.67);
                   actuators.LauncherMotor.StartMotor();
                   LauncherMotorTimer.reset() ;
                   LaunchOrder= Arrays.asList(6, 5, 4); 
                   launcherstate= LauncherState.ACTIVELAUNCHALL;
                   
        
               }
           
        
               // Far Launching by code  todo test code
               if(opmode.gamepad2.dpad_up && ActuatorControl.controlstate==ActuatorControl.ControlState.ready) {
        
                   ActuatorControl.controlstate=ActuatorControl.ControlState.launching;
                   actuators.LauncherMotor.SetPower(.73);
                   actuators.LauncherMotor.StartMotor();
                   LauncherMotorTimer.reset();
                   LaunchOrder= GetLaunchOrderFromCode(); 
                   launcherstate= LauncherState.ACTIVELAUNCHBYCODE;
        
               }
         
        
                // Close Launching by code todo test code
               if(opmode.gamepad2.dpad_down && ActuatorControl.controlstate==ActuatorControl.ControlState.ready) {
        
                   ActuatorControl.controlstate=ActuatorControl.ControlState.launching;
                   actuators.LauncherMotor.SetPower(.67);
                   actuators.LauncherMotor.StartMotor();
                   LauncherMotorTimer.reset() ;
                   LaunchOrder= GetLaunchOrderFromCode();
                   launcherstate= LauncherState.ACTIVELAUNCHBYCODE;
        
               }
             

           break;
           case: MOTORSTARTUP
               if(LauncherMotorTimer.milliseconds()>=4000{
               
                launcherstate= LauncherState.ACTIVELAUNCHALL;
           }
               break;
           case: ACTIVELAUNCHALL
           
               launchByCode();
               break;
           case: ACTIVELAUNCHBYCODE // need to test this second of code
               LaunchOrder= Arrays.asList(6, 5, 4);
               launchByCode();
               break;
       }

       
   }

   }

    public enum LaunchSequence{
        IDLE,
        LAUNCHPOSITION1,
        LAUNCHPOSITION2,
        LAUNCHPOSITION3
        
    }

public  LaunchSequence launchsequence = LaunchSequence.IDLE;
   public void lanchall() {
      
        switch(launchsequence){
                case: IDLE
                    
                     launchsequence = LaunchSequence.LAUNCHPOSITION1;
                    break;
                case:LAUNCHPOSITION1
                    launch(LaunchOrder.get(1));
                    if(loadgamepeace=LoadGamePeace.IDLE){
                        launchsequence = LaunchSequence.LAUNCHPOSITION2;
                    }
                  break;
                    case:LAUNCHPOSITION2
                    launch(LaunchOrder.get(2));
                    if(loadgamepeace=LoadGamePeace.IDLE){
                        launchsequence = LaunchSequence.LAUNCHPOSITION3;
                    }
                break;
                    case:LAUNCHPOSITION3
                    launch(LaunchOrder.get(3));
                    if(loadgamepeace=LoadGamePeace.IDLE){
                        launchsequence = LaunchSequence.IDLE;
                    }
                
                break;
    

               actuators.LauncherMotor.StopMotor();
               ActuatorControl.controlstate = ActuatorControl.ControlState.ready;
               LoadSpindexer.Currentstate = LoadSpindexer.State.Empty;
               LoadSpindexer.SpindexerLoaded = false;
        
        }

   }
public enum LoadGamePeace{
    IDLE,
    SETPOSITION,
    ACTUATEKICKER,
    RETURNKICKERPOSITION
    
}

 LoadGamePeace loadgamepeace=LoadGamePeace.IDLE;

    public void launch(int pos){

        switch(loadgamepeace){

            case: IDLE
            LoadGamePeaceTimer.reset();
            loadgamepeace=LoadGamePeace.SETPOSITION;
            break;
                
            case:SETPOSITION
                if(LoadGamePeaceTimer.milliseconds()>=500){
                  actuators.spindexercontrol.setPosition(pos);
                  LoadGamePeaceTimer.reset();
                  loadgamepeace=LoadGamePeace.ACTUATEKICKER;
                
                }

                
            break;
            case:ACTUATEKICKER
                if(LoadGamePeaceTimer.milliseconds()>=500){
                 actuators.LaunchKicker.SetSecond();
                 LoadGamePeaceTimer.reset();
                 loadgamepeace=LoadGamePeace.RETURNKICKERPOSITION;
                }
            break;
            case:RETURNKICKERPOSITION
                if(LoadGamePeaceTimer.milliseconds()>=500){
                actuators.LaunchKicker.SetFirst();
                LoadGamePeaceTimer.reset();
                loadgamepeace=LoadGamePeace.IDLE;
                }
            break;


    }

    }
    public void lanchorder(List<Integer> order){

        opmode.sleep(6000);
        launch(order.get(1));
        launch(order.get(2));
        launch(order.get(3));
        actuators.LauncherMotor.StopMotor();
        ActuatorControl.controlstate = ActuatorControl.ControlState.ready;
        LoadSpindexer.Currentstate = LoadSpindexer.State.Empty;
        LoadSpindexer.SpindexerLoaded = false;


    }
    public List<Integer> GetLaunchOrderFromCode(){
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
        // launch inorder
        List<Integer> Lorder =MarchLists(colorPos,Code);

    }

    public void launchByCode(){  // used for autonomous mode
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
        // launch inorder
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

                    IDList.set(n,m+1+3);
                    FlagA[n] = false;
                    FlagB[m] = false;


                }





            }
        }
        return IDList;
    }

}
