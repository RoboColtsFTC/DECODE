package org.firstinspires.ftc.teamcode.Perception;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorDetector {

    public enum Color {
        PURPLE,
        GREEN,
        UNKNOWN

    }

    HardwareMap hardwaremap;
    ColorSensor colorSensor;

    Color detectedcolor;

    public ColorDetector(HardwareMap hardwaremap) {

        this.hardwaremap = hardwaremap;
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    }

    public Color GetColor() {
        // Put color detection
        return detectedcolor;
    }

    public boolean colordetected() {
        boolean detected =false;
        // Put color detection
        switch(detectedcolor){
            case GREEN:
                detected =true;
                break;
            case PURPLE:
                detected =true;
            default:
                detected =false;
        }

        return detected;
    }


}


