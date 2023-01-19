/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Basic: FullTest OpMode", group="Linear Opmode")
//@Disabled
public class BasicOpMode_fullTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftRear = null;
    private DcMotor rightRear = null;
    private DcMotor lift = null;
    private Servo claw = null;
    //declaring the motor and servo I will be using
    private static final double MID_SERVO       =  0.5 ;

    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.02 ;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear  = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);


        lift = hardwareMap.get(DcMotor.class, "lift");
        claw = hardwareMap.get(Servo.class, "claw");
        lift.setDirection(DcMotor.Direction.FORWARD);  // link code with revhub and set direction of motor
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
// motor will brake when it is not spinning to hold lift in place
        claw.setPosition(MID_SERVO);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y); // r is magnitude / speed of robot in a particular direction
            double theta = Math.atan2(gamepad1.left_stick_x, -gamepad1.left_stick_y); // this is angle from the positive x axis, in radians
            double sin = Math.sin(theta-Math.PI/4);  // we are subtracting theta by pi/4 to rotate the axis of reference,
            // now the four motors only have to move
            // in either x or y axis, but not both
            double cos = Math.cos(theta-Math.PI/4);
            double horizontalStrafe = gamepad1.right_stick_x;
            double v1  = r*cos+horizontalStrafe;
            double v2 = -r*sin-horizontalStrafe;
            double v3 = -r*sin+horizontalStrafe;
            double v4 = r*cos-horizontalStrafe;
            double d1 =Math.abs(v1);
            double d2 = Math.abs(v2);
            double d3 = Math.abs(v3);
            double d4= Math.abs(v4);

            if (gamepad1.a) {

                double denominator = Math.max(d1 + d2 + d3 + d4, 1) ;

                leftFront.setPower(v1 / denominator);
                rightFront.setPower(v2 / denominator);
                leftRear.setPower(v3 / denominator);
                rightRear.setPower(v4 / denominator);

            } else {

                double denominator = Math.max(d1 + d2 + d3 + d4, 1) * 1.2;

                leftFront.setPower(v1 / denominator);
                rightFront.setPower(v2 / denominator);
                leftRear.setPower(v3 / denominator);
                rightRear.setPower(v4 / denominator);

            }


            if (gamepad1.left_trigger >0) {
                lift.setPower(0.85);
            } else if (gamepad1.right_trigger>0) {
                lift.setPower(-0.85);
            } else{
                lift.setPower(0);
            }

            clawOffset = Range.clip(clawOffset, -0.9, 0.9);

            if (gamepad1.right_bumper) {
                clawOffset += CLAW_SPEED;
                claw.setPosition(MID_SERVO + clawOffset);
            }
            else if (gamepad1.left_bumper) {
                clawOffset -= CLAW_SPEED;

                claw.setPosition(MID_SERVO + clawOffset);            }

            // Move both servos to new position.  Assume servos are mirror image of each other.



            // controlling claw motion
// motor power is just magnitude of hypotnuse multiplied by angle


        }
    }
}
