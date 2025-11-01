package org.firstinspires.ftc.teamcode.Actuation.TestCode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.FeedControl;

@TeleOp(name = "FeedControl Test", group = "Test")
public class ActuoatorControlTest extends LinearOpMode {
public ActuatorControl actuatorcontrol;
    private LinearOpMode OpMode;

    @Override
    public void runOpMode() throws InterruptedException {
        actuatorcontrol =new ActuatorControl(OpMode);
        telemetry.addLine("FeedControl Test Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            actuatorcontrol.Run();

        }
    }
}
