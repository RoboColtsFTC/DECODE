package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Actuation:Launch Ball", group = "Actuation")
public class IntakeBall extends LinearOpMode {

    DcMotor motor;
    double Power = 0 ;
    double Increment = .01;
    double MaxPower = 1;





    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "intake_motor");
        // Debounce Switches
        boolean dpad_up_Debounce = false;
        boolean dpad_down_Debounce = false;

        motor.setPower(Power);
        // Wait for the start button
        telemetry.addData(">", "Press Start to start Ball Launcher.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() ) {

            if (gamepad2.dpad_up & !dpad_up_Debounce) {

                Power += Increment;
            }
            dpad_up_Debounce=gamepad2.dpad_up; // Debouncing Game Controller

            if (gamepad2.dpad_down & !dpad_down_Debounce  ) {

                Power -= Increment;
            }
            dpad_down_Debounce=gamepad2.dpad_down; // Debouncing Game Controller

            if (Math.abs(Power)>MaxPower){
                if (Power>0) {
                    Power = MaxPower;
                }
                else {
                    Power=-MaxPower;
                }

            }

            motor.setPower(Power);

            telemetry.addData(">", "Press DPadUp to Increase Press DPadDown to Degrease.");
            telemetry.addData("Motor Power", "%5.2f",Power);
            telemetry.addData("Motor Power Measured", "%5.2f",motor.getPower());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
        }


        }




    }



