package org.firstinspires.ftc.teamcode.Actuation;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Actuation.Actuators.AngleServo;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.ContinuousMotor;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.FeedControl;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.SpindexerControl;

// This class is used for initializing all the actuators used in the Decode Game.
// It allows them to be initialized once and shared between different classes
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

    public LinearOpMode opmode;
    public HardwareMap hardwaremap;
    public ActuatorControl(LinearOpMode opmode) {

        this.opmode=opmode;
        this.hardwaremap= opmode.hardwareMap;
        actuators.feedcontrol=new FeedControl(hardwaremap, .5);
        actuators.FeedKicker= new AngleServo(hardwaremap,"FeedKicker",10,20,300);
        actuators.LaunchKicker=new AngleServo(hardwaremap,"FeedKicker",10,20,300);
        actuators.IntakeMotor = new ContinuousMotor(hardwaremap,"IntakeMotor",0);
        actuators.LauncherMotor= new ContinuousMotor(hardwaremap,"LauncherMotor",0);
        actuators.spindexercontrol=new SpindexerControl(hardwaremap,"Spindexer");
        loadSpindexer=new LoadSpindexer(this.opmode,actuators);

    }

public void run() {
    loadSpindexer.Run();

}

}

