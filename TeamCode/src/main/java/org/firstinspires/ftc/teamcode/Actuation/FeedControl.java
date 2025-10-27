package org.firstinspires.ftc.teamcode.Actuation;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class FeedControl {
    private ContinuousServo servo1;
    private ContinuousServo servo2;

    public double power;

    public FeedControl(HardwareMap hardware, double power){
        this.power=power;
        servo1 = new ContinuousServo(hardware,"servo1",this.power);
        servo2 = new ContinuousServo(hardware,"servo2",this.power);
        servo2.Reverse();

    }

public void startFeed(){
    servo1.Start();
    servo2.Start();

}

    public void StopFeed(){
        servo1.Stop();
        servo2.Stop();

    }
}
