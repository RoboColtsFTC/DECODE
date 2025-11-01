package org.firstinspires.ftc.teamcode.Perception.Test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.app.Activity;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;
import org.firstinspires.ftc.teamcode.Perception.ColorDetector;

@TeleOp(name = "TestColorDetection ", group = "Test")
public class TestColorDetection extends LinearOpMode {

    public ColorDetector colordetector;
    private LinearOpMode OpMode;

    @Override
    public void runOpMode() throws InterruptedException {
        colordetector = new ColorDetector(OpMode);
        // Get a reference to the RelativeLayout so we can later change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        waitForStart();

        while (opModeIsActive()) {

            try {
                colordetector.Run(); // actually execute the sample
            } finally {
                // On the way out, *guarantee* that the background is reasonable. It doesn't actually start off
                // as pure white, but it's too much work to dig out what actually was used, and this is good
                // enough to at least make the screen reasonable again.
                // Set the panel back to the default color
                relativeLayout.post(new Runnable() {
                    public void run() {
                        relativeLayout.setBackgroundColor(Color.WHITE);
                    }
                });
            }
        }
    }
}
