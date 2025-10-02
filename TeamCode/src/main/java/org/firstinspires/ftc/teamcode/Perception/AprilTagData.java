package org.firstinspires.ftc.teamcode.Perception;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class AprilTagData {
    public long timestamp;
    public MovingAverage Bearing;
    public MovingAverage Range;

    public goal Red;
    public goal Blue;
    public Code DetectedCode;

   public boolean color;  // True == Red, False == Blue

    public AprilTagData(){
        Red = new goal();
        Blue = new goal();
        DetectedCode = new Code();

    }
    public void SetRed(double Range,double Bearing) {
       Red.addValue(Range, Bearing);


    }

    public void SetBlue(double Range,double Bearing) {
        Blue.addValue(Range, Bearing);


    }

    public void SetCode(String code) {
        DetectedCode.SetCode(code);


    }


   public class Code{
        String CodeID;
        boolean IsDetected = false;
       public void SetCode(String code){

           IsDetected = true;
           CodeID=code;

       }


    }

    public class goal{

        public long timestamp;
        public MovingAverage Bearing;
        public MovingAverage Range;
        public int windowsize=10;
        public goal(){

            Bearing=new MovingAverage(windowsize);
            Range=new MovingAverage(windowsize);

        }
        public void addValue(double Bearing, double Range){
            this.Bearing.addData(Bearing);
            this.Range.addData(Range);
        }
    }

}
