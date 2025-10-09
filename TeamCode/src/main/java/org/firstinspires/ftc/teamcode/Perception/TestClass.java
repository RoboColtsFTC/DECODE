package org.firstinspires.ftc.teamcode.Perception;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Actuation.AngleServo;

@TeleOp(name="Red Teleop", group="Linear OpMode")
public class TestClass extends LinearOpMode {
public AngleServo servo;
    @Override
    public void runOpMode() throws InterruptedException {
        servo= new AngleServo(hardwareMap,"Test",360);
        servo.InitialAngle=20;
        servo.FinalAngle=100;

        waitForStart();

        while (opModeIsActive()) {
            servo.SetInitial();
            servo.SetFinal();

        }
    }
}
