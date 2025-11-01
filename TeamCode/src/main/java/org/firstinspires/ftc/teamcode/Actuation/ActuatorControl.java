package org.firstinspires.ftc.teamcode.Actuation;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Actuation.Actuators.AngleServo;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.ContinuousMotor;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.FeedControl;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.SpindexerControl;

// This class is used for initializing all the actuators used in the Decode Game.
// It allows them to be initialized once and shared between different classes
@Config
public class ActuatorControl {

    public static class Actuators{
        public FeedControl feedcontrol;
        public AngleServo FeedKicker;
        public AngleServo LaunchKicker;
        public ContinuousMotor IntakeMotor;
        public ContinuousMotor LauncherMotor;
        public SpindexerControl spindexercontrol;

            }
    public Actuators actuators=new Actuators();

    public LoadSpindexer loadSpindexer;
    public LaunchGamePeace launchgamepeace;

    public LinearOpMode opmode;
    public HardwareMap hardwaremap;

    public static class Params{
        // Feed Kicker parameters
        public double FeedKicker_First=10;
        public double FeedKicker_Second=20;
        public double FeedKicker_MaxAngle=300;
        // LaunchKicker parameters
        public double LaunchKicker_First=10;
        public double LaunchKicker_Second=20;
        public double LaunchKicker_MaxAngle=300;

        // Intake Motor Parameters
        public double IntakeMotor_Power=.5;
        public double Launchmmotor_Power=.5;

        // Feed control Parameters
        public double FeedControl_Power=.5;
    }


    Params param=new Params();
    public ActuatorControl(LinearOpMode opmode) {

        this.opmode=opmode;
        this.hardwaremap= opmode.hardwareMap;
        actuators.feedcontrol=new FeedControl(hardwaremap, param.FeedControl_Power);
        actuators.FeedKicker= new AngleServo(hardwaremap,"FeedKicker",param.FeedKicker_First,param.FeedKicker_Second,param.FeedKicker_MaxAngle);
        actuators.LaunchKicker=new AngleServo(hardwaremap,"LaunchKicker",param.LaunchKicker_First,param.LaunchKicker_Second,param.LaunchKicker_MaxAngle);
        actuators.IntakeMotor = new ContinuousMotor(hardwaremap,"IntakeMotor",param.IntakeMotor_Power);
        actuators.LauncherMotor= new ContinuousMotor(hardwaremap,"LauncherMotor",param.Launchmmotor_Power);
        actuators.spindexercontrol=new SpindexerControl(hardwaremap,"Spindexer");
        loadSpindexer=new LoadSpindexer(this.opmode,actuators);
        launchgamepeace=new LaunchGamePeace(this.opmode,actuators);

    }

public void Run() {
    loadSpindexer.Run();
    launchgamepeace.Run();


}

}

