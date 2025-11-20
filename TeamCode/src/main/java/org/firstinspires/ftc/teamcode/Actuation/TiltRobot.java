package org.firstinspires.ftc.teamcode.Actuation;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
public class TiltRobot {

    public ServoImplEx servo;

    public double incrementangle=1;

    public double Position1;

    public double Position2;

    public double StartingAngle=.5;
    public double MaxAngle= 360;

    public double currentangle;



    public TiltRobot(HardwareMap hardwareMap, String ServoName,double StartingAngle, double Position1, double Position2){
        servo = (ServoImplEx)hardwareMap.get(Servo.class, ServoName);
        servo.setPwmRange(new PwmControl.PwmRange(800, 2200));
        this.StartingAngle=StartingAngle;
        this.Position1=Position1;
        this.Position2=Position2;


        }

        public void tiltPosition1(){
            servo.setPosition(Position1);

        }
        public void tiltPosition2(){
            servo.setPosition(Position2);

        }
            public void ReturntoZero () {

                servo.setPosition(StartingAngle);


            }
            // todo add robot tilting code
        }
