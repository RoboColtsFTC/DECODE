package org.firstinspires.ftc.teamcode.Perception.Actuation;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AngleServo {
    public Servo servo;
    public String ServoName;
    public double InitialAngle=0;
    public double FinalAngle=10;
    public double RampIncrement=2;

    public boolean RampUp =false;
    public boolean RampDown=false;

    public double MaxAngle=180;  // Based on Servo's Max Range of Motion.

    public boolean State=false; //True equals running False = not running

    public AngleServo(HardwareMap hardwaremap, String ServoName,double MaxAngle) {

        servo = hardwareMap.get(Servo.class, ServoName);
        this.MaxAngle=MaxAngle;
        servo.setPosition(InitialAngle*1/this.MaxAngle);

    }

    public void SetInitial() {

        if(RampUp && servo.getPosition()!=FinalAngle/MaxAngle) {
            for (double n=(InitialAngle/MaxAngle);n<=(FinalAngle/MaxAngle);n+=(RampIncrement/MaxAngle)) {
                servo.setPosition(n);
            }
        } else if(!RampUp){
            servo.setPosition(InitialAngle * 1 / MaxAngle);
        }

    }

    public void SetFinal() {
        if(RampDown && (servo.getPosition()!=InitialAngle/MaxAngle)) {
            for (double k=(InitialAngle/MaxAngle);k>=(InitialAngle/MaxAngle);k-=(RampIncrement/MaxAngle)) {
                servo.setPosition(k);
            }
        } else if(!RampDown){

            servo.setPosition(InitialAngle * 1 / MaxAngle);
        }

    }
}
