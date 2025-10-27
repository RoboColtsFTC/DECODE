package org.firstinspires.ftc.teamcode.Actuation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Perception.DetectColor.Color;

public class LoadSpindexer {
public enum State {
    Empty,
    LoadOne,
    OneLoaded,
    LoadTwo,
    TwoLoaded,
    LoadThree,
    ThreeLoaded,
    Loaded

}

public Color ColorState;
public OpMode opmode;


State Currentstate;

    public LoadSpindexer(LinearOpMode opmmode){ // Cunstructor
        this.opmode=opmmode;
        Currentstate=State.Empty;
    }

    public void Run(){
// State machine to load Spindexer

    switch(Currentstate){
        case Empty:
     if(opmode.gamepad1.a){
         Currentstate = State.LoadOne;
     }
            break;
        case LoadOne:

            break;
        case OneLoaded:
            break;
        case LoadTwo:
            break;
        case TwoLoaded:
            break;
        case LoadThree:
            break;
        case ThreeLoaded:
        case Loaded:
            break;

        }

    }



}
