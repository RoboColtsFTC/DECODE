package org.firstinspires.ftc.teamcode.Actuation;

import static android.icu.lang.UProperty.MATH;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class ContinuousMotor {
    private double Power = 0;
    private DcMotorEx motor;
    private boolean isrunning = false;

    public ContinuousMotor(HardwareMap hardwaremap, String MotorName, double power) {

        motor = hardwareMap.get(DcMotorEx.class, MotorName);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.Power = norm(power);
    }

    public void StartMotor() {
        motor.setPower(Power);
        isrunning = true;
    }

    public void StopMotor() {
        motor.setPower(0);
        isrunning = false;
    }

    public void SetPower(double power) {
        if (isrunning) {
            this.Power = norm(power);
            motor.setPower(norm(power));

        } else {
            this.Power = norm(power);
        }

    }

    public void SetForward(){
           motor.setDirection(DcMotorSimple.Direction.FORWARD);
}
    public void SetReverse(){
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public double norm(double value) {

        if (Math.abs(value) > 1) {
            value = 1;
        } else if (Math.abs(value) < 0) {
            value = 0;
        }
        return value;


    }


}
