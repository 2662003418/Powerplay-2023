package org.firstinspires.ftc.teamcode;import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.Locale;


@Autonomous(name = "V3", group = "Sensor")
// Comment this out to add to the opmode list
public class V3 extends LinearOpMode {


    ColorSensor sensor_Color;
    DcMotor Front_leftDrive = null;
    DcMotor Back_rightDrive = null;
    DcMotor Front_rightDrive = null;
    DcMotor Back_leftDrive = null;

    @Override
    public void runOpMode() throws InterruptedException {
        sensor_Color = hardwareMap.get(ColorSensor.class, "sensor_color");
        Front_leftDrive = hardwareMap.get(DcMotor.class, "leftFront");
        Back_rightDrive= hardwareMap.get(DcMotor.class, "rightRear");
        Front_rightDrive= hardwareMap.get(DcMotor.class, " rightFront");
        Back_leftDrive= hardwareMap.get(DcMotor.class, " leftRear");

        Front_leftDrive.setDirection(DcMotor.Direction.FORWARD);
        Front_rightDrive.setDirection(DcMotor.Direction.REVERSE);
        Back_leftDrive.setDirection(DcMotor.Direction.FORWARD);
        Back_rightDrive.setDirection(DcMotor.Direction.REVERSE);

      waitForStart();

      while(opModeIsActive()){
          sensor_Color.enableLed(true);
          telemetry.addData("red", sensor_Color.red());
          telemetry.addData("green", sensor_Color.green());
          telemetry.addData("blue", sensor_Color.blue());
          telemetry.addData("argb", sensor_Color.argb());
          telemetry.update();

          StrafeLeft(0.5,300);
          DriveForward(0.5,1500);


          if(sensor_Color.green()> sensor_Color.blue() && sensor_Color.green()>sensor_Color.red()){
              DriveForward(0.5,800);
              StrafeLeft(0.5,1500);
          }

          else if (sensor_Color.red()> sensor_Color.blue() && sensor_Color.green()>sensor_Color.green()){
              DriveForward(0.5,1000);

          }

          else {
              DriveForward(0.5,800);
              StrafeRight(0.5,1700);
          }

      }
    }

    public void Stop(long time) throws InterruptedException {
        Front_leftDrive.setPower(0);
        Front_rightDrive.setPower(0);
        Back_leftDrive.setPower(0);
        Back_rightDrive.setPower(0);
        Thread.sleep(time);
    }

    public void DriveForward(double power,long time) throws InterruptedException {
        Front_leftDrive.setPower(power);
        Front_rightDrive.setPower(power);
        Back_leftDrive.setPower(power);
        Back_rightDrive.setPower(power);
        Thread.sleep(time);
    }


    public void StrafeLeft(double power,long time) throws InterruptedException {
        Front_leftDrive.setPower(-power);
        Front_rightDrive.setPower(power);
        Back_leftDrive.setPower(power);
        Back_rightDrive.setPower(-power);
        Thread.sleep(time);
    }

    public void StrafeRight(double power,long time) throws InterruptedException {
        Front_leftDrive.setPower(power);
        Front_rightDrive.setPower(-power);
        Back_leftDrive.setPower(-power);
        Back_rightDrive.setPower(power);
        Thread.sleep(time);
    }
}


