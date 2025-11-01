package org.firstinspires.ftc.teamcode.Perception;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class ColorDetector{
    NormalizedColorSensor colorSensor, colorSensor2;
    public enum DetColor {
        PURPLE,
        GREEN,
        UNKNOWN

    }
    /** The relativeLayout field is used to aid in providing interesting visual feedback
     * in this sample application; you probably *don't* need this when you use a color sensor on your
     * robot. Note that you won't see anything change on the Driver Station, only on the Robot Controller. */
    View relativeLayout;
    final float[] hsvValues = new float[3];
    HardwareMap hardwaremap;
    //ColorSensor colorSensor;

    DetColor finalDetectedColor;
    public LinearOpMode opmode;

    public ColorDetector(LinearOpMode opmode) {
        this.opmode=opmode;
        this.hardwaremap = opmode.hardwareMap;
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color1");
        colorSensor2 = hardwareMap.get(NormalizedColorSensor.class, "sensor_color2");

        // Get a reference to the RelativeLayout so we can later change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
//
//        try {
//            runSample(); // actually execute the sample
//        } finally {
//            // On the way out, *guarantee* that the background is reasonable. It doesn't actually start off
//            // as pure white, but it's too much work to dig out what actually was used, and this is good
//            // enough to at least make the screen reasonable again.
//            // Set the panel back to the default color
//            relativeLayout.post(new Runnable() {
//                public void run() {
//                    relativeLayout.setBackgroundColor(Color.WHITE);
//                }
//            });
//        }
    }

    protected void Run() {
        float gain = 8;
        // Once per loop, we will update this hsvValues array. The first element (0) will contain the
        // hue, the second element (1) will contain the saturation, and the third element (2) will
        // contain the value. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
        // for an explanation of HSV color.

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color1");
        colorSensor2 = hardwareMap.get(NormalizedColorSensor.class, "sensor_color2");

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        if (colorSensor2 instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor2).enableLight(true);
        }

        // Wait for the start button to be pressed.

        colorSensor.setGain(gain);
        colorSensor2.setGain(gain);

        // Loop until we are asked to stop


        GetColor();


    }

    public Color GetColor() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        NormalizedRGBA colors2 = colorSensor2.getNormalizedColors();

        /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
         * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
         * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
         * for an explanation of HSV color. */

        // Update the hsvValues array by passing it to Color.colorToHSV()
        Color.colorToHSV(colors.toColor(), hsvValues);
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(hsvValues));
            }
        });

        float normBlue, normGreen, normRed;
        float normBlue2, normGreen2, normRed2;
        SensorColor.DetColor detectedColor1;
        SensorColor.DetColor detectedColor2;
        SensorColor.DetColor finalDetectedColor;
        /* If this color sensor also has a distance sensor, display the measured distance.
         * Note that the reported distance is only useful at very close range, and is impacted by
         * ambient light and surface reflectivity. */
        if (colorSensor instanceof DistanceSensor) {
            opmode.telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
        }
        normRed = colors.red / colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        normRed2 = colors2.red / colors2.alpha;
        normGreen2 = colors2.green / colors2.alpha;
        normBlue2 = colors2.blue / colors2.alpha;

        if (normRed < normGreen && normBlue > normGreen) {
            detectedColor1 = SensorColor.DetColor.PURPLE;
        } else if (normGreen > normRed && normGreen > normBlue && normGreen > .06) {
            detectedColor1 = SensorColor.DetColor.GREEN;
        } else {
            detectedColor1 = SensorColor.DetColor.UNKNOWN;
        }

        if (normRed2 < normGreen2 && normBlue2 > normGreen2) {
            detectedColor2 = SensorColor.DetColor.PURPLE;
        } else if (normGreen2 > normRed2 && normGreen2 > normBlue2 && normGreen2 > .06) {
            detectedColor2 = SensorColor.DetColor.GREEN;
        } else {
            detectedColor2 = SensorColor.DetColor.UNKNOWN;
        }

        if (detectedColor1.equals(detectedColor2)) {
            finalDetectedColor = detectedColor1;
        } else {
            finalDetectedColor = SensorColor.DetColor.UNKNOWN;
        }

        //telemetry.addData("NormRed", normRed);
        //telemetry.addData("NormGreen", normGreen);
        //telemetry.addData("NormBlue", normBlue);
        //telemetry.addData("NormRed2", normRed2);
        //telemetry.addData("NormGreen2", normGreen2);
        //telemetry.addData("NormBlue2", normBlue2);
        //telemetry.addData("gain", gain);
        //telemetry.addLine("Detected Color1: " + detectedColor1);
        //telemetry.addLine("Detected Color2: " + detectedColor2);
        opmode.telemetry.addLine("Final Detected Color: " + finalDetectedColor);
        opmode.telemetry.update();
        return finalDetectedColor;
    }



    public boolean colordetected(Color finalDetectedColor) {
        boolean detected =false;
        switch(finalDetectedColor){
            case GREEN:
                detected =true;
                break;
            case PURPLE:
                detected =true;
            default:
                detected =false;
        }
        opmode.telemetry.addData("Detected Boolean: ", detected);
        opmode.telemetry.update();
        return detected;
    }

}


