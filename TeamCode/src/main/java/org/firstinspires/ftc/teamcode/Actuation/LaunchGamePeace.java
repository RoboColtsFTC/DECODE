package org.firstinspires.ftc.teamcode.Actuation;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl.Actuators;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.Perception.AprilTagData;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector.DetColor;




import java.util.Arrays;
import java.util.List;
@Config
public class LaunchGamePeace {

    public Actuators actuators;
    public LinearOpMode opmode;

    public double MaxLauncherVelocity=28*5800*60;  // pulses per second max rpm of 5800
    public AprilTagData TagData;
    List<ColorDetector.DetColor> colorPos;

    private final ElapsedTime LauncherMotorTimer = new ElapsedTime();
    private final ElapsedTime LoadGamePeaceTimer = new ElapsedTime();

    public LaunchGamePeace(LinearOpMode opmode, Actuators actuators, List<ColorDetector.DetColor> colorPos) {
        this.colorPos = colorPos;
        this.opmode = opmode;
        this.actuators = actuators;

        LauncherMotorTimer.reset();

    }

    public enum LauncherState {
        IDLE,
        MOTORSTARTUP,
        ACTIVELAUNCH,


    }

    public LauncherState launcherstate = LauncherState.IDLE;
    public List<Integer> LaunchOrder = Arrays.asList(6, 5, 4);  // Defalt sequnce
public boolean autoLaunch =false;

public double autoVelocity=0;
    public Action Launch_Auto(double velocity){
        return new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                autoVelocity=velocity;
                autoLaunch=true;
                LoadSpindexer_run();

                return autoLaunch;
            }



        };
    }

    public void run(){
        LoadSpindexer_run();
    }

    public void LoadSpindexer_run() {


        switch (launcherstate) {

            case IDLE:

//                if (autoLaunch){
//                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
//                    actuators.LauncherMotor.SetVelocity(autoVelocity);
//                    actuators.LauncherMotor.StartMotor();
//                    LauncherMotorTimer.reset();
//                    LaunchOrder = Arrays.asList(6, 5, 4);
//                    launcherstate = LauncherState.MOTORSTARTUP;
//                    autoLaunch=false;
//
//                }
                // Far Launching
                if (opmode.gamepad2.x && ActuatorControl.controlstate == ActuatorControl.ControlState.ready) {

                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
                    actuators.LauncherMotor.SetVelocity(1650); //120.7 12.53v
                    actuators.LauncherMotor.StartMotor();
                    LauncherMotorTimer.reset();

//                    if(TagData.DetectedCode.IsDetected) {
//                        LaunchOrder = GetLaunchOrderFromCode();
//                    }else {
                        LaunchOrder = Arrays.asList(6, 5, 4);
                    //}
                    launcherstate = LauncherState.MOTORSTARTUP;


                }else if(autoLaunch){
                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
                    actuators.LauncherMotor.SetVelocity(autoVelocity); //120.7 12.53v.63
                    actuators.LauncherMotor.StartMotor();
                    LauncherMotorTimer.reset();
//                    if(TagData.DetectedCode.IsDetected) {
//                        LaunchOrder = GetLaunchOrderFromCode();
//                    }else {
                        LaunchOrder = Arrays.asList(6, 5, 4);
//                    }
                    launcherstate = LauncherState.MOTORSTARTUP;
                    opmode.telemetry.addLine("autoLaunch");
                }
                // Close Launching


                if (opmode.gamepad2.y && ActuatorControl.controlstate == ActuatorControl.ControlState.ready) {

                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
                    actuators.LauncherMotor.SetVelocity(1400); //60.4 inch 12.59v works at 45.9
                    actuators.LauncherMotor.StartMotor();
                    LauncherMotorTimer.reset();
//                    if(TagData.DetectedCode.IsDetected) {
//                        LaunchOrder = GetLaunchOrderFromCode();
//                    }else {
                        LaunchOrder = Arrays.asList(6, 5, 4);
//                    }
                    launcherstate = LauncherState.MOTORSTARTUP;

                }
//                if (opmode.gamepad2.dpad_right && ActuatorControl.controlstate == ActuatorControl.ControlState.ready) {
//
//                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
//                    actuators.LauncherMotor.SetVelocity(1400); //to close for apiril tags
//                    actuators.LauncherMotor.StartMotor();
//                    LauncherMotorTimer.reset();
//                    LaunchOrder = Arrays.asList(6, 5, 4);
//                    launcherstate = LauncherState.MOTORSTARTUP;
//
//                }

//                // Far Launching by code  todo test code
//                if (opmode.gamepad2.dpad_left && ActuatorControl.controlstate == ActuatorControl.ControlState.ready) {
//
//                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
//                    actuators.LauncherMotor.SetPower(.73);
//                    actuators.LauncherMotor.StartMotor();
//                    LauncherMotorTimer.reset();
//                    LaunchOrder = GetLaunchOrderFromCode();
//                    launcherstate = LauncherState.MOTORSTARTUP;
//
//                }
//
//
//                // Close Launching by code todo test code
//                if (opmode.gamepad2.dpad_right && ActuatorControl.controlstate == ActuatorControl.ControlState.ready) {
//
//                    ActuatorControl.controlstate = ActuatorControl.ControlState.launching;
//                    actuators.LauncherMotor.SetPower(.67);
//                    actuators.LauncherMotor.StartMotor();
//                    LauncherMotorTimer.reset();
//                    LaunchOrder = GetLaunchOrderFromCode();
//                    launcherstate = LauncherState.MOTORSTARTUP;
//
//                }

                break;
            case MOTORSTARTUP:
                if (actuators.LauncherMotor.isMotorAtVelocity()) {

                    launcherstate = LauncherState.ACTIVELAUNCH;
                }
                break;
            case ACTIVELAUNCH:

                launchall();
                if (launchsequence == LaunchSequence.IDLE) {
                    launcherstate = LauncherState.IDLE;
                    autoLaunch=false;

                }

                break;

        }


    }


    public enum LaunchSequence {
        IDLE,
        LAUNCHPOSITION1,
        LAUNCHPOSITION2,
        LAUNCHPOSITION3

    }

    public LaunchSequence launchsequence = LaunchSequence.IDLE;

    public void launchall() {

        switch (launchsequence) {
            case IDLE:

                launchsequence = LaunchSequence.LAUNCHPOSITION1;
                break;
            case LAUNCHPOSITION1:
                launch(LaunchOrder.get(0));
                if (loadgamepeace == LoadGamePeace.IDLE) {
                    launchsequence = LaunchSequence.LAUNCHPOSITION2;
                }
                break;
            case LAUNCHPOSITION2:
                launch(LaunchOrder.get(1));
                if (loadgamepeace == LoadGamePeace.IDLE) {
                    launchsequence = LaunchSequence.LAUNCHPOSITION3;
                }
                break;
            case LAUNCHPOSITION3:
                launch(LaunchOrder.get(2));
                if (loadgamepeace == LoadGamePeace.IDLE) {
                    launchsequence = LaunchSequence.IDLE;
                    //actuators.LauncherMotor.StopMotor();
                    actuators.LauncherMotor.SetVelocity(1400);
                    ActuatorControl.controlstate = ActuatorControl.ControlState.ready;
                    LoadSpindexer.Currentstate = LoadSpindexer.State.Empty;

                }

                break;

        }

    }

    public enum LoadGamePeace {
        IDLE,
        SETPOSITION,
        ACTUATEKICKER,
        RETURNKICKERPOSITION

    }

    LoadGamePeace loadgamepeace = LoadGamePeace.IDLE;

    public void launch(int pos) {

        switch (loadgamepeace) {

            case IDLE:
                LoadGamePeaceTimer.reset();
                loadgamepeace = LoadGamePeace.SETPOSITION;
                break;

            case SETPOSITION:
                if (LoadGamePeaceTimer.milliseconds() >= 100) {
                    actuators.spindexercontrol.setPosition(pos);
                    LoadGamePeaceTimer.reset();
                    loadgamepeace = LoadGamePeace.ACTUATEKICKER;

                }


                break;
            case ACTUATEKICKER:
                if (LoadGamePeaceTimer.milliseconds() >= 100) {
                    actuators.LaunchKicker.SetSecond();
                    LoadGamePeaceTimer.reset();
                    loadgamepeace = LoadGamePeace.RETURNKICKERPOSITION;
                }
                break;
            case RETURNKICKERPOSITION:
                if (LoadGamePeaceTimer.milliseconds() >= 100) {
                    actuators.LaunchKicker.SetFirst();
                    LoadGamePeaceTimer.reset();
                    loadgamepeace = LoadGamePeace.IDLE;
                }
                break;


        }

    }

    public List<Integer> GetLaunchOrderFromCode() {
        List<DetColor> Code = Arrays.asList(DetColor.UNKNOWN, DetColor.UNKNOWN, DetColor.UNKNOWN);
        switch (TagData.DetectedCode.CodeID) {
            case GPP:

                Code = Arrays.asList(DetColor.GREEN, DetColor.PURPLE, DetColor.PURPLE);

                break;
            case PGP:
                Code = Arrays.asList(DetColor.PURPLE, DetColor.GREEN, DetColor.PURPLE);

                break;
            case PPG:
                Code = Arrays.asList(DetColor.PURPLE, DetColor.PURPLE, DetColor.GREEN);

                break;
        }
        // launch inorder
       return  MarchLists(colorPos, Code);

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
        List<Integer> order =MarchLists(colorPos,Code);

        //lanchorder(Lorder);





    }

// Method used to determien fire order
public static List<Integer> MarchLists( List<DetColor> ListA,List<DetColor> ListB) {

    List<Integer> IDList = Arrays.asList(3, 3, 3);
    Boolean[] FlagA = {true, true, true,};
    Boolean[] FlagB = {true, true, true};

    for (int n = 0; n < ListA.size(); n++) {


        for (int m = 0; m < ListB.size(); m++) {


            if (ListA.get(n).equals(ListB.get(m)) & FlagA[n] && FlagB[m]) {

                IDList.set(n, m);
                FlagA[n] = false;
                FlagB[m] = false;


            }


        }


    }


    if (IDList.contains(3)) {
        IDList = Arrays.asList(6, 5, 4);


    } else{
        for (int k = 0; k < ListB.size(); k++){
            IDList.set(k,IDList.get(k)+3);
        }
    }
    return IDList;
}
}
