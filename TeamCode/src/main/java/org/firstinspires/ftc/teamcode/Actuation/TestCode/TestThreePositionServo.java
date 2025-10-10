package org.firstinspires.ftc.teamcode.Actuation.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Actuation.ThreePositionServo;

@TeleOp(name="TestThreePositionServo", group="Test")
public class TestThreePositionServo extends LinearOpMode {


    public ThreePositionServo servo;
    @Override
    public void runOpMode() throws InterruptedException {
        servo= new ThreePositionServo(hardwareMap,"Test",10,50,250,300,0);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_up) {
                servo.setPosition(1);
            }else if(gamepad1.dpad_down) {
                servo.setPosition(2);
            }else if(gamepad1.dpad_left) {
                servo.setPosition(3);
            }
        }
    }
}
