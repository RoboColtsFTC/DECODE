package org.firstinspires.ftc.teamcode.Actuation.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Actuation.AngleServo;

@TeleOp(name="TestAngleServo", group="Test")
public class TestAngleServo extends LinearOpMode {
public AngleServo servo;
    @Override
    public void runOpMode() throws InterruptedException {
        servo= new AngleServo(this.hardwareMap,"Test",0,95,1800);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_up) {
                servo.SetFirst();
            }else if(gamepad1.dpad_down) {
                servo.SetSecond();
            }
        }
    }
}
