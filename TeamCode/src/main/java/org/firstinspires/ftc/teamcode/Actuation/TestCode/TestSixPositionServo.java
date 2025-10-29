package org.firstinspires.ftc.teamcode.Actuation.TestCode;

import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Actuation.SpindexerControl.Positions;
import org.firstinspires.ftc.teamcode.Actuation.SpindexerControl;

@Config
@TeleOp(name="TestSixPositionServo", group="Test")
public class TestSixPositionServo extends LinearOpMode {


    public SpindexerControl servo;
    public Positions positions;


    @Override
    public void runOpMode() throws InterruptedException {

        positions= new Positions();
        servo= new SpindexerControl(hardwareMap,"Test",positions);


        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_up) {
                servo.setPosition(1);
            }else if(gamepad1.dpad_down) {
                servo.setPosition(2);
            }else if(gamepad1.dpad_left) {
                servo.setPosition(3);
            }else if(gamepad1.a) {
                servo.setPosition(4);
            }else if(gamepad1.x) {
                servo.setPosition(5);
            }else if(gamepad1.b) {
                servo.setPosition(6);
            }

        }
    }
}
