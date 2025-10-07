package org.firstinspires.ftc.teamcode.drivetrain;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.ChassisSpeeds;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveWheelSpeeds;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Perception.AprilTag;
import org.firstinspires.ftc.teamcode.Perception.AprilTagData;
import org.firstinspires.ftc.teamcode.drivetrain.PinPointIMU.GoBildaPinpointDriver;

public class Drivetrain {
    MecanumDrive drive;

    LinearOpMode opMode;
    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    double width = .3429;
    double length = .3429;

    Translation2d m_frontLeftLocation = new Translation2d(width/2.0, length/2.0);
    Translation2d m_frontRightLocation = new Translation2d(width/2.0, -length/2.0);
    Translation2d m_backLeftLocation = new Translation2d(-width/2.0, length/2.0);
    Translation2d m_backRightLocation = new Translation2d(-width/2.0, -length/2.0);

    // Creating my kinematics object using the wheel locations.
    MecanumDriveKinematics m_kinematics = new MecanumDriveKinematics(
                    m_frontLeftLocation, m_frontRightLocation,
                    m_backLeftLocation, m_backRightLocation);

//    public static BNO055IMU imu;
//    //    public static IMU imu;
//    public static BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//
//    RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
//    RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
//    RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);


    Gamepad driver;

    double maxSpeed = 1;

    PIDController controller = new PIDController(.025,.0025,0);

    public AprilTagData TagData;
    public Drivetrain(LinearOpMode opMode, AprilTagData TagData){
        this.opMode = opMode;

        this.driver = opMode.gamepad1;
        this.TagData = TagData;
        drive = new MecanumDrive(this.opMode.hardwareMap, new Pose2d(0,0,0));

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        odo = opMode.hardwareMap.get(GoBildaPinpointDriver.class,"odo");

        /*
        Set the odometry pod positions relative to the point that the odometry computer tracks around.
        The X pod offset refers to how far sideways from the tracking point the
        X (forward) odometry pod is. Left of the center is a positive number,
        right of center is a negative number. the Y pod offset refers to how far forwards from
        the tracking point the Y (strafe) odometry pod is. forward of center is a positive number,
        backwards is a negative number.
         */
        //todo: Update offsets based on frame
        odo.setOffsets(-84.0, -168.0); //these are tuned for 3110-0002-0001 Product Insight #1

        /*
        Set the kind of pods used by your robot. If you're using goBILDA odometry pods, select either
        the goBILDA_SWINGARM_POD, or the goBILDA_4_BAR_POD.
        If you're using another kind of odometry pod, uncomment setEncoderResolution and input the
        number of ticks per mm of your odometry pod.
         */
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        //odo.setEncoderResolution(13.26291192);


        /*
        Set the direction that each of the two odometry pods count. The X (forward) pod should
        increase when you move the robot forward. And the Y (strafe) pod should increase when
        you move the robot to the left.
         */
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);


        /*
        Before running the robot, recalibrate the IMU. This needs to happen when the robot is stationary
        The IMU will automatically calibrate when first powered on, but recalibrating before running
        the robot is a good idea to ensure that the calibration is "good".
        resetPosAndIMU will reset the position to 0,0,0 and also recalibrate the IMU.
        This is recommended before you run your autonomous, as a bad initial calibration can cause
        an incorrect starting value for x, y, and heading.
         */
        //odo.recalibrateIMU();
        odo.resetPosAndIMU();

        opMode.telemetry.addData("Status", "Initialized");
        opMode. telemetry.addData("X offset", odo.getXOffset());
        opMode.telemetry.addData("Y offset", odo.getYOffset());
        opMode.telemetry.addData("Device Version Number:", odo.getDeviceVersion());
        opMode.telemetry.addData("Device Scalar", odo.getYawScalar());
        opMode.telemetry.update();


//        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
//        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.loggingEnabled      = false;
//
//        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
//        imu.initialize(parameters);
    }

    public void run(){
        ChassisSpeeds speeds;

        double thetaPower;
        Pose2D pos = odo.getPosition();
        if(driver.a) {
            thetaPower = controller.calculate(pos.getHeading(AngleUnit.DEGREES), -45 + 180);
        } else if(driver.x){
            thetaPower = controller.calculate(pos.getHeading(AngleUnit.DEGREES), 90);
        } else if(driver.b) {
            thetaPower = controller.calculate(pos.getHeading(AngleUnit.DEGREES), -90);
        } else if (driver.y && !TagData.color){
            thetaPower = controller.calculate(pos.getHeading(AngleUnit.DEGREES), TagData.Blue.Bearing.Average);
            opMode.telemetry.addData("IMU Reading", "%5.1f inches",pos.getHeading(AngleUnit.DEGREES));
        } else if (driver.y && TagData.color){
            thetaPower = controller.calculate(pos.getHeading(AngleUnit.DEGREES), TagData.Red.Bearing.Average);
            opMode.telemetry.addData("IMU Reading", "%5.1f inches",pos.getHeading(AngleUnit.DEGREES));
        } else {
            thetaPower = -driver.right_stick_x * Math.PI;
        }

        speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                driver.left_stick_y * maxSpeed,
                driver.left_stick_x * maxSpeed,
                thetaPower,
                Rotation2d.fromDegrees(pos.getHeading(AngleUnit.DEGREES) + 180)
        );

        MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);

        wheelSpeeds.normalize(maxSpeed);

        drive.setWheelPowers(new double[]{
                wheelSpeeds.frontLeftMetersPerSecond,
                wheelSpeeds.rearLeftMetersPerSecond,
                wheelSpeeds.rearRightMetersPerSecond,
                wheelSpeeds.frontRightMetersPerSecond
        });

//        if(driver.back){
//            drive.localizer.setPose(new Pose2d(0,0,0));
//            imu.initialize(parameters);
//        }
    }
}
