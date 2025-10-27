package org.firstinspires.ftc.teamcode.Perception;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DetectColor {

    public enum Color {
        PURPLE,
        GREEN,
        UNKNOWN

    }

    HardwareMap hardwaremap;
    ColorSensor colorSensor;

    Color detectedcolor;

    public DetectColor(HardwareMap hardwaremap) {

        this.hardwaremap = hardwaremap;
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    }

    public Color GetColor() {
        // Put color detection
        return detectedcolor;
    }
}


