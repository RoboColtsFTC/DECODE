package org.firstinspires.ftc.teamcode.Actuation.TestCode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Actuation.ActuatorControl;
import org.firstinspires.ftc.teamcode.Actuation.Actuators.FeedControl;

@TeleOp(name = "ActuoatorControlTest", group = "Test")
public class ActuoatorControlTest extends LinearOpMode {
public ActuatorControl actuatorcontrol;


    @Override
    public void runOpMode() throws InterruptedException {
        actuatorcontrol =new ActuatorControl(this);
        telemetry.addLine("FeedControl Test Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            actuatorcontrol.run();
            telemetry.update();


        }
    }
}
