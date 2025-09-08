package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drivetrain.Drivetrain;

public class Robot {
    LinearOpMode opMode;

    static boolean auto;
    static boolean red;
    static boolean useDrive;

    public Drivetrain drivetrain;

    public Robot(LinearOpMode opMode, boolean auto, boolean red, boolean useDrive){
        this.opMode = opMode;
        Robot.auto = auto;
        Robot.red = red;
        Robot.useDrive = useDrive;

        if(!auto && useDrive){
            drivetrain = new Drivetrain(this.opMode);
        }
    }

    public Robot(LinearOpMode opMode, boolean red){
        this(opMode, true, red, false);
    }

    public void runRobot(){
        if(!auto && useDrive){
            drivetrain.run();
        }
    }
}
