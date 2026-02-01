package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Simple Auto", group = "Autonomous")
public class SimpleAuto extends LinearOpMode {

    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    @Override
    public void runOpMode() {

        // Map the motors
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor   = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor  = hardwareMap.get(DcMotor.class, "backRightMotor");

        // Same directions as your TeleOp
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Optional: brake when power = 0
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Ready to run Simple Auto");
        telemetry.update();

        // Wait for the start button
        waitForStart();

        if (opModeIsActive()) {
            // Move forward for 1 second
            double power = 0.5;

            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);
            backLeftMotor.setPower(power);
            backRightMotor.setPower(power);

            sleep(1500); // 1000 ms = 1 second

            // Stop
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);

            // Sit still until the autonomous period ends
            while (opModeIsActive()) {
                telemetry.addData("Status", "Auto complete, motors stopped");
                telemetry.update();
                sleep(50);
            }
        }
    }
}
