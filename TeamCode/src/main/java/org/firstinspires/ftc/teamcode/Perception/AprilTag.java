package org.firstinspires.ftc.teamcode.Perception;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTag {
    private LinearOpMode opMode;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private final Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private final YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, 0, 0, 0);
    private AprilTagData Data;

    public AprilTag(LinearOpMode opMode, AprilTagData Data) {
        this.opMode = opMode;
        this.Data = Data;
        initAprilTag();
    }

    public void run() {
            ProcessAprilTags();
            // Push telemetry to the Driver Station.
             opMode.telemetry.update();

    }


    private void initAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                .setDrawAxes(false)
                //.setDrawCubeProjection(false)
                .setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .setCameraPose(cameraPosition, cameraOrientation)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //      **************** Camera Calibration results *************************
                //      Image Resolution: 640 x 480 pixels
                //      /*  Focals (pixels) - Fx: 543.913 Fy: 543.913
                //        Optical center - Cx: 321.431 Cy: 223.814
                //        Radial distortion (Brown's Model)
                //        K1: 0.0457029 K2: -0.10388 K3: 0.0854565
                //        P1: -0.00442754 P2: 0.00173836
                //        Skew: 0*/

                .setLensIntrinsics(543.913, 543.913, 321.431, 223.814)
                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        //aprilTag.setDecimation(3);
        // **-- YOUR CALIBRATION VALUES GO HERE --**


        // Create the vision portal by using a builder.
        visionPortal = new VisionPortal.Builder()
                .setCamera(opMode.hardwareMap.get(WebcamName.class, "Webcam 1")) // Get camera from hardware map
                .setCameraResolution(new Size(640, 480)) // Setting resolution
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG) // Set stream format to MJPEG for higher FPS
                .enableLiveView(true) // Enable live view on Robot Controller screen
                .setAutoStopLiveView(true) // Automatically stop live view when OpMode is stopped
                .addProcessor(aprilTag)    // Set and enable the processor.
                .build(); // Build the VisionPortal

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);
        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()

    // Process Detections and storing in AprilTagData to be used with other classes
    private void ProcessDetections(AprilTagDetection detection, AprilTagData Data) {
        // Step through the list of detections and display info for each one.


        switch (detection.id) {
            case 20:  //Blue April Tag ID

                Data.SetBlue(detection.ftcPose.range, detection.ftcPose.bearing);
                opMode.telemetry.addData("Rangeavg", "%5.1f inches",Data.Blue.Range.Average);
                opMode.telemetry.addData("Bearingavg", "%3.0f degrees", Data.Blue.Bearing.Average);

                break; // Exits the switch statement
            case 24: //Red April Tag ID
                Data.SetRed(detection.ftcPose.range, detection.ftcPose.bearing);
                opMode.telemetry.addData("Rangeavg", "%5.1f inches",Data.Red.Range.Average);
                opMode.telemetry.addData("Bearingavg", "%3.0f degrees", Data.Red.Bearing.Average);
                break;
            // ... more cases
            default:
                // Code to execute if no case matches (optional)
                break;
        }
    }

    private void ProcessAprilTags() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        opMode.telemetry.addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            ProcessDetections(detection, Data);
            telemetryAprilTag(detection);
        }


//        // Add "key" information to telemetry
//        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");

    }

    // function that displays telemetry on the drivers station
    private void telemetryAprilTag(AprilTagDetection detection) {

        if (detection.metadata != null) {
            opMode.telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
            // Only use tags that don't have Obelisk in them
            if (!detection.metadata.name.contains("Obelisk")) {
                opMode.telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)",
                        detection.rawPose.x,
                        detection.rawPose.y,
                        detection.rawPose.z));
                opMode.telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)",
                        detection.robotPose.getOrientation().getPitch(AngleUnit.DEGREES),
                        detection.robotPose.getOrientation().getRoll(AngleUnit.DEGREES),
                        detection.robotPose.getOrientation().getYaw(AngleUnit.DEGREES)));
                opMode.telemetry.addData("Range", "%5.1f inches", detection.ftcPose.range);
                opMode.telemetry.addData("Bearing", "%3.0f degrees", detection.ftcPose.bearing);
            }
        } else {
            opMode.telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
            opMode.telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
        }


    }   // end method telemetryAprilTag()
}// end aprilTag class