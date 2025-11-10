package org.firstinspires.ftc.teamcode.Actuation.Actuators;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DualMotor {

    //private double Power = 0;
    private double velocity;
    private double maxvelocity;
    private DcMotorEx motor1,motor2;
    private boolean isrunning = false;

    public DualMotor(HardwareMap hardwareMap, String MotorName1,String MotorName2, double velocity) {

        motor1 = hardwareMap.get(DcMotorEx.class, MotorName1);
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2 = hardwareMap.get(DcMotorEx.class, MotorName2);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);


        this.velocity = norm(velocity);
    }


    public boolean isMotorAtVelocity(){
        double upper = velocity+5;
        double lower= velocity-5;



        return ((motor2.getVelocity()>=lower && motor2.getVelocity()<=upper )
                && (motor1.getVelocity()>=lower && motor1.getVelocity()<=upper ));


    }
    public void SetVelocity(double velocity){
        this.velocity=velocity;
        motor1.setVelocity(velocity);
        motor2.setVelocity(velocity);
    }

    public void StartMotor() {
        motor1.setVelocity(velocity);
        motor2.setVelocity(velocity);
        isrunning = true;
    }

    public void StopMotor() {
        motor1.setVelocity(0);
        motor2.setVelocity(0);
        isrunning = false;
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

