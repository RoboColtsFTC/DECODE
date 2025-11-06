package org.firstinspires.ftc.teamcode.Actuation.Actuators;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DualMotor {

    private double Power = 0;
    private DcMotorEx motor1,motor2;
    private boolean isrunning = false;

    public DualMotor(HardwareMap hardwareMap, String MotorName1,String MotorName2, double power) {

        motor1 = hardwareMap.get(DcMotorEx.class, MotorName1);
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2 = hardwareMap.get(DcMotorEx.class, MotorName2);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);


        this.Power = norm(power);
    }

    public void StartMotor() {
        motor1.setPower(Power);
        motor2.setPower(Power);
        isrunning = true;
    }

    public void StopMotor() {
        motor1.setPower(0);
        motor2.setPower(0);
        isrunning = false;
    }

    public void SetPower(double power) {
        if (isrunning) {
            this.Power = norm(power);
            motor1.setPower(norm(power));

        } else {
            this.Power = norm(power);
        }

    }

    public void SetForward(){
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void SetReverse(){
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setDirection(DcMotorSimple.Direction.FORWARD);
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

