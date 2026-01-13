package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "Advanced Auto BLUE", group = "Autonomous")
public class AdvancedAutoBlue extends LinearOpMode {

    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private DcMotor launcher;
    private DcMotor intake;

    @Override
    public void runOpMode() {

        // Map the motors
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor   = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor  = hardwareMap.get(DcMotor.class, "backRightMotor");

        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        launcher = hardwareMap.get(DcMotor.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);

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
            /*double power = 0.5;

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
            }*/

            double power = 0.5;

            frontLeftMotor.setPower(-power);
            frontRightMotor.setPower(-power);
            backLeftMotor.setPower(-power);
            backRightMotor.setPower(-power);

            sleep(300);

            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(-power);

            sleep(500);

            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);

            double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();

// Base power you want at 11V (tune this if needed)
            double basePower = 1.0;

// Compensated power
            double compensatedPower = basePower * (11.0 / voltage);
            compensatedPower = Range.clip(compensatedPower, 0.0, 1.0);

            launcher.setPower(compensatedPower);

            sleep(1000);

            intake.setPower(1);

            sleep(5000);

            intake.setPower(0);
            launcher.setPower(0);

            frontLeftMotor.setPower(-power);
            frontRightMotor.setPower(-power);
            backLeftMotor.setPower(-power);
            backRightMotor.setPower(-power);

            sleep(1000);

            frontLeftMotor.setPower(-power);
            backLeftMotor.setPower(power);
            backRightMotor.setPower(-power);
            frontRightMotor.setPower(power);

            sleep(2500);

            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);

            while (opModeIsActive()) {
                telemetry.addData("Status", "Auto complete, motors stopped");
                telemetry.update();
                sleep(50);
            }
        }
    }
}
