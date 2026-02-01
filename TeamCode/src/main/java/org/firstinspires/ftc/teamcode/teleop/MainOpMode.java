package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

@TeleOp(name = "Main", group = "TeleOp")
public class MainOpMode extends LinearOpMode {

    private static final double DESIRED_DISTANCE = 12.0;   // inches
    private static final double SPEED_GAIN       = 0.02;
    private static final double STRAFE_GAIN      = 0.015;
    private static final double TURN_GAIN        = 0.01;

    private static final double MAX_AUTO_SPEED   = 0.5;
    private static final double MAX_AUTO_STRAFE  = 0.5;
    private static final double MAX_AUTO_TURN    = 0.4;

    private static final boolean USE_WEBCAM      = false;

    // Tag IDs we want to toggle between
    private static final int TAG_ID_1 = 24;
    private static final int TAG_ID_2 = 20;

    // Current target tag ID (starts on TAG_ID_1)
    private int currentTagId = TAG_ID_1;

    // For edge-detect on the A button (so it toggles once per press)
    private boolean aWasPressed = false;

    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor intake;
    private DcMotor launcher;
    private DcMotor feeder;

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;

    Servo myServo;
    boolean buttonWasPressed = false;
    boolean servoExtended = false;

    @Override
    public void runOpMode() {

        boolean targetFound;
        double drive;
        double strafe;
        double turn;

        // Initialize AprilTag and camera
        initAprilTag();

        // Hardware mapping
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor   = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor  = hardwareMap.get(DcMotor.class, "backRightMotor");
        intake          = hardwareMap.get(DcMotor.class, "intake");
        feeder = hardwareMap.get(DcMotor.class, "feeder");

        feeder.setDirection(DcMotorSimple.Direction.REVERSE);

        launcher = hardwareMap.get(DcMotor.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reverse right side for mecanum
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        myServo = hardwareMap.get(Servo.class, "myServo");

        if (USE_WEBCAM) {
            setManualExposure(6, 250);
        }

        telemetry.addData(">", "Press PLAY to start");
        telemetry.addData("Current Goal", "Align to AprilTag");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // --- Toggle target tag ID with A button ---
            if (gamepad1.a && !aWasPressed) {
                if (currentTagId == TAG_ID_1) {
                    currentTagId = TAG_ID_2;
                } else {
                    currentTagId = TAG_ID_1;
                }
            }
            aWasPressed = gamepad1.a;

            if (gamepad1.b && !buttonWasPressed) {
                servoExtended = !servoExtended;
                myServo.setPosition(servoExtended ? .3 : 0.0);
            }
            buttonWasPressed = gamepad1.b;

            // --- AprilTag detection ---
            targetFound = false;
            desiredTag = null;

            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    if (detection.id == currentTagId) {
                        targetFound = true;
                        desiredTag = detection;
                        break;
                    }
                }
            }

            // --- Drive control ---
            if (gamepad1.right_bumper && targetFound) {
                // Auto-align mode
                double rangeError   = desiredTag.ftcPose.range - DESIRED_DISTANCE;
                double headingError = desiredTag.ftcPose.bearing;
                double yawError     = desiredTag.ftcPose.yaw;

                drive  = Range.clip(rangeError   * SPEED_GAIN,  -MAX_AUTO_SPEED,  MAX_AUTO_SPEED);
                turn   = Range.clip(headingError * TURN_GAIN,   -MAX_AUTO_TURN,   MAX_AUTO_TURN);
                strafe = Range.clip(-yawError    * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
            } else {
                // Manual mode
                drive  = -gamepad1.left_stick_y;
                strafe = -gamepad1.left_stick_x;
                turn   = -gamepad1.right_stick_x;
            }

            moveRobot(drive, strafe, turn);

            /*if(gamepad1.y){
                intake.setPower(gamepad1.right_trigger);
                feeder.setPower(gamepad1.right_trigger);
            }else{
                intake.setPower(-gamepad1.right_trigger);
                feeder.setPower(-gamepad1.right_trigger);

                if (gamepad1.x) {
                    intake.setPower(gamepad1.right_trigger);
                    feeder.setPower(-gamepad1.right_trigger);
                }
            }*/

            if(gamepad1.y){
                if(gamepad1.right_bumper){
                    feeder.setPower(1);
                }else if(!gamepad1.y){
                    feeder.setPower(0);
                }
                intake.setPower(gamepad1.right_trigger);
            }else {
                if(gamepad1.right_bumper){
                    feeder.setPower(-1);
                }else if(!gamepad1.y){
                    feeder.setPower(0);
                }
                intake.setPower(-gamepad1.right_trigger);
            }

            // Launcher (left trigger)
            double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();

// Base power you want at 11V (tune this if needed)
            double basePower = 1;

// Compensated power
            double compensatedPower = basePower * (11.0 / voltage);
            compensatedPower = Range.clip(compensatedPower, 0.0, 1.0);

// Launcher (left trigger)
            if (gamepad1.left_trigger > 0.1) {
                launcher.setPower(compensatedPower);
            } else {
                launcher.setPower(0.0);
            }


            // --- Telemetry ---
            telemetry.addData("Target Found", targetFound ? "yes" : "no");
            telemetry.addData("Target Tag ID", currentTagId);
            if (desiredTag != null) {
                telemetry.addData("Detected ID", desiredTag.id);
                telemetry.addData("Range",   "%.1f in", desiredTag.ftcPose.range);
                telemetry.addData("Bearing", "%.1f deg", desiredTag.ftcPose.bearing);
                telemetry.addData("Yaw",     "%.1f deg", desiredTag.ftcPose.yaw);
            }
            telemetry.update();

            sleep(10);
        }
    }

    // x = forward/back, y = strafe, yaw = turn
    public void moveRobot(double x, double y, double yaw) {
        double frontLeftPower  = x - y - yaw;
        double frontRightPower = x + y + yaw;
        double backLeftPower   = x + y - yaw;
        double backRightPower  = x - y + yaw;

        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower * 0.8);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);
    }

    private void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        if (USE_WEBCAM) {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTag)
                    .build();
        } else {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessor(aprilTag)
                    .build();
        }
    }

    private void setManualExposure(int exposureMS, int gain) {
        if (visionPortal == null) {
            return;
        }

        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting for stream");
            telemetry.update();
            while (!isStopRequested()
                    && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Streaming");
            telemetry.update();
        }

        if (!isStopRequested()) {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long) exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);

            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
}
