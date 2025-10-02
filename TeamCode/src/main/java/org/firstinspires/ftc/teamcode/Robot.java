package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Perception.AprilTag;
import org.firstinspires.ftc.teamcode.Perception.AprilTagData;
import org.firstinspires.ftc.teamcode.drivetrain.Drivetrain;

public class Robot {
    LinearOpMode opMode;

    static boolean auto;
    static boolean red;
    static boolean useDrive;

    static boolean useBallLauncher;

    public Drivetrain drivetrain;
    public BallLauncher balllauncher;

    public AprilTagData TagData;
    public AprilTag AprilTagPro;

    public Robot(LinearOpMode opMode, boolean auto, boolean red, boolean useDrive,boolean useBallLauncher){
        this.opMode = opMode;
        Robot.auto = auto;
        Robot.red = red;
        Robot.useDrive = useDrive;
        Robot.useBallLauncher = useBallLauncher;
        TagData=new AprilTagData();
        if (red){
            TagData.color=true;
        }
          else{
            TagData.color=false;
        }
        AprilTagPro = new AprilTag(this.opMode,TagData);

        if(!auto && useDrive){
            drivetrain = new Drivetrain(this.opMode,TagData);
        }

        if(!auto && useBallLauncher) {
            balllauncher = new BallLauncher(this.opMode);
        }



    }

    public Robot(LinearOpMode opMode, boolean red){
        this(opMode, true, red, false,false);
    }

    public void runRobot() {
        if (!auto && useDrive) drivetrain.run();
        if (!auto && useBallLauncher)  balllauncher.run();
    }
}
