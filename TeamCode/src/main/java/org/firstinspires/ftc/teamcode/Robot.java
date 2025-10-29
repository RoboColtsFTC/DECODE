package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Perception.AprilTag;
import org.firstinspires.ftc.teamcode.Perception.AprilTagData;
import org.firstinspires.ftc.teamcode.drivetrain.Drivetrain;

public class Robot {
    LinearOpMode opMode;

    static boolean auto;
    static boolean red;
    static boolean useDrive;
    static boolean useAprilTags;

    static boolean useBallLauncher;

    public Drivetrain drivetrain;
    public BallLauncher balllauncher;

    public AprilTagData TagData;
    public AprilTag AprilTagPro;

    public Robot(LinearOpMode opMode, boolean auto, boolean red, boolean useDrive,boolean useBallLauncher,boolean useAprilTags){
        this.opMode = opMode;
        Robot.auto = auto;
        Robot.red = red;
        Robot.useDrive = useDrive;
        Robot.useBallLauncher = useBallLauncher;
        Robot.useAprilTags=useAprilTags;

        TagData=new AprilTagData();

        // set color for April tags
        if (red){
            TagData.red=true;
        }
          else{
            TagData.red=false;
        }
          if(!auto && useAprilTags) {
              AprilTagPro = new AprilTag(this.opMode, TagData);
          }
        if(!auto && useDrive){
            drivetrain = new Drivetrain(this.opMode,TagData);
        }

        if(!auto && useBallLauncher) {
            balllauncher = new BallLauncher(this.opMode);
        }



    }

    public Robot(LinearOpMode opMode, boolean red){
        this(opMode, true, red, false,false,false);
    }

    public void runRobot() {
        if (!auto && useDrive) drivetrain.run();
        if (!auto && useBallLauncher)  balllauncher.run();
        if (!auto && useAprilTags) AprilTagPro.run();

    }
}
