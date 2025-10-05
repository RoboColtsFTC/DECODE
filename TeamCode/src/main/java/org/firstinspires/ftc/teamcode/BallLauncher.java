package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class BallLauncher {

    private LinearOpMode opMode;
    private  Gamepad gamepad2;

    DcMotor motor;
    double Power = 0 ;
    double Increment = .01;
    double MaxPower = 1;
    boolean dpad_up_Debounce = false;
    boolean dpad_down_Debounce = false;

public BallLauncher(LinearOpMode opMode){

    this.opMode=opMode;
    this.gamepad2=opMode.gamepad2;
    motor = opMode.hardwareMap.get(DcMotorEx.class, "launcher_motor");



}
public void run(){

        if (gamepad2.dpad_up & !dpad_up_Debounce) {

            Power += Increment;
        }
        dpad_up_Debounce = gamepad2.dpad_up; // Debouncing Game Controller

        if (gamepad2.dpad_down & !dpad_down_Debounce) {

            Power -= Increment;
        }
        dpad_down_Debounce = gamepad2.dpad_down; // Debouncing Game Controller

        if (Math.abs(Power) > MaxPower) {
            if (Power > 0) {
                Power = MaxPower;
            } else {
                Power = -MaxPower;
            }

        }

        motor.setPower(Power);


}

public void UpdateTelemetry() {
    telemetry.addData(">", "Press DPadUp to Increase Press DPadDown to Degrease.");
    telemetry.addData("Motor Power", "%5.2f",Power);
    telemetry.addData("Motor Power Measured", "%5.2f",motor.getPower());
    telemetry.addData(">", "Press Stop to end test." );
    telemetry.update();
}

}
