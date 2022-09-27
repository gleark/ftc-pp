
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Robot: Teleop Tank", group="Robot")
@Disabled
public class Teleop_Single_Stick_Drive_v1 extends OpMode{

    /* Declare OpMode members. */
    public DcMotor  leftFrontDrive   = null;
    public DcMotor  rightFrontDrive  = null;
    public DcMotor  leftBackDrive    = null;
    public DcMotor  rightBackDrive   = null;

    @Override
    public void init() {
        // Define and Initialize Motors
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "FL");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "FR");
        leftBackDrive    = hardwareMap.get(DcMotor.class, "BL");
        rightBackDrive    = hardwareMap.get(DcMotor.class, "BR");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {

        double leftFrontPower  = 0;
        double rightFrontPower = 0;
        double leftBackPower   = 0;
        double rightBackPower  = 0;
        double driveSpeed      = 1.0;

        // Read joysticks once
        double left_x  = gamepad1.left_stick_x;  // strafe
        double left_y  =  gamepad1.left_stick_y; // front-back
        double right_x = gamepad1.right_stick_x; // turn

        // Drive speed is use to throttle down power as a multiplier
        // applied power = joystick power * driveSpeed
        if(gamepad1.right_trigger > 0) {
            driveSpeed = 1 - gamepad1.right_trigger;
            if(driveSpeed < 0.3) {
                driveSpeed = 0.3;
            }
        }

        //Calculate joystick power for each motor
        leftFrontPower  = Range.clip(left_y - left_x - (right_x * 0.75), -1.0, 1.0);
        rightFrontPower = Range.clip(left_y + left_x + (right_x * 0.75), -1.0, 1.0);
        leftBackPower   = Range.clip(left_y + left_x - (right_x * 0.75), -1.0, 1.0);
        rightBackPower  = Range.clip(left_y - left_x + (right_x * 0.75), -1.0, 1.0);

        //set applied power for each motor
        leftFrontDrive.setPower(leftFrontPower * driveSpeed);
        rightFrontDrive.setPower(rightFrontPower * driveSpeed);
        leftBackDrive.setPower(leftBackPower * driveSpeed);
        rightBackDrive.setPower(rightBackPower * driveSpeed);

        // Send telemetry to help track inputs
        telemetry.addData("leftFrontPower",  "%.2f", leftFrontPower);
        telemetry.addData("leftBackPower", "%.2f", leftBackPower);
        telemetry.addData("rightFrontPower", "%.2f", rightFrontPower);
        telemetry.addData("rightBackPower", "%.2f", rightBackPower);
        telemetry.addData("driveSpeed", "%.2f", driveSpeed);

    }

    @Override
    public void stop() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
