package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Actuation.LaunchGamePeace;
import org.firstinspires.ftc.teamcode.Actuation.LoadSpindexer;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.drivetrain.MecanumDrive;
import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;
@Autonomous(name="BlueAuto", group="Robot")
public final class BlueAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Robot robot = new Robot(this,false);

        waitForStart();

        /* -------------------------------------------------------------------------------------- */

        //beginPose = new Pose2d(0, 0, Math.toRadians(0.00));
        drive.localizer.setPose(beginPose);
        Actions.runBlocking(drive.actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(-5 ,-5), Math.toRadians(-90))
                .turn(-60)
                .afterTime(0, robot.actuatorcontrol.launchgamepeace.Launch_Auto())
                .strafeToLinearHeading(new Vector2d(-5 ,-28), Math.toRadians(0))

                .build());


    }
}