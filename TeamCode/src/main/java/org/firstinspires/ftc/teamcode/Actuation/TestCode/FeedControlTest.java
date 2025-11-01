package org.firstinspires.ftc.teamcode.Actuation.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.FeedControl;

@TeleOp(name = "FeedControl Test", group = "Test")
public class FeedControlTest extends LinearOpMode {
    private FeedControl feedControl;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize FeedControl
        feedControl = new FeedControl(hardwareMap, 1.0);

        telemetry.addLine("FeedControl Test Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //Map buttons for testing
            if (gamepad1.x) {
                feedControl.Forward();
                feedControl.startFeed();

                telemetry.addLine("Feed started");
            } else if (gamepad1.y) {
               //  sleep(5000);
                feedControl.StopFeed();
                telemetry.addLine("Feed Stopped");
            } else if (gamepad1.b) {
                feedControl.Reverse();
                feedControl.startFeed();
                telemetry.addLine("Feed Reversed");
            }

            telemetry.update();
        }
    }
}
