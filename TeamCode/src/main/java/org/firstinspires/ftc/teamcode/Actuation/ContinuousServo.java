package org.firstinspires.ftc.teamcode.Actuation;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class ContinuousServo {
    public CRServo servo;
    public String ServoName;
    public double MaxPower=1;
    public double MinPower=0;
    public double Power=0;
    public boolean State=false; //True equals running False = not running

    public ContinuousServo(HardwareMap hardwaremap, String ServoName) {

        servo = hardwareMap.get(CRServo.class, ServoName);
        servo.setDirection(CRServo.Direction.FORWARD);
        servo.setPower(0);

    }

    public void setPower(double Power) {


        if (Power > MaxPower) {
            Power = MaxPower;

        } else if (Power < MinPower) {

            Power = MinPower;

        }


        this.Power=Power;


        if(State) servo.setPower(Power);


    }
    public void Forward(){

        servo.setDirection(CRServo.Direction.FORWARD);

    }
    public void Reverse(){

        servo.setDirection(CRServo.Direction.REVERSE);

    }
    public void Start(){

        servo.setPower(Power);
        State=true;

    }

    public void Stop(){

        servo.setPower(0);
        State=false;

    }

}

