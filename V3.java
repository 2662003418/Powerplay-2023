package org.firstinspires.ftc.teamcode;import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.Locale;


@Autonomous(name = "V3", group = "Sensor")
// Comment this out to add to the opmode list
public class V3 extends LinearOpMode {


    ColorSensor sensor_Color;

    @Override
    public void runOpMode() {
        sensor_Color = hardwareMap.get(ColorSensor.class, "sensor_color");


      waitForStart();

      while(opModeIsActive()){
          sensor_Color.enableLed(true);
          telemetry.addData("red", sensor_Color.red());
          telemetry.update();

          
      }
    }

}
