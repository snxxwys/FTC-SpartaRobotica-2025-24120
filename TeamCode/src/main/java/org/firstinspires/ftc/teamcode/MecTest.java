package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "MecTest AutoAlign", group = "TeleOp")
public class MecTest extends LinearOpMode {

    // ← typo fixed here
    private static final int TARGET_TAG_ID = 24;

    // vision stuff
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    // simple P turn constants
    private static final double kP_TURN = 0.03;
    private static final double MAX_TURN_POWER = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        // motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor intake = hardwareMap.dcMotor.get("intake");

        // reverse right side
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // start vision (make sure your webcam name matches RC config)
        initVision();

        telemetry.addLine("Ready");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // normal driving inputs
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            // when you hold RB, try to auto-face tag
            if (gamepad1.right_bumper) {
                Double autoTurn = getAutoTurnPowerForTargetTag();
                if (autoTurn != null) {
                    rx = autoTurn;
                } else {
                    // no tag seen; you could leave manual rx or 0
                    rx = 0;
                }
            }

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower  = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower  = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            // intake on right trigger (yours was negative, I’ll keep it simple)
            intake.setPower(-gamepad1.right_trigger);

            telemetry.addData("Align", gamepad1.right_bumper ? "ON" : "OFF");
            telemetry.update();
        }
    }

    // ---------------- helper methods go BELOW runOpMode ----------------

    private void initVision() {
        aprilTag = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1")) // change name if needed
                .addProcessor(aprilTag)
                .build();
    }

    /**
     * If target tag is seen, returns turn power. Else returns null.
     */
    private Double getAutoTurnPowerForTargetTag() {
        List<AprilTagDetection> detections = aprilTag.getDetections();
        if (detections == null || detections.isEmpty()) {
            return null;
        }

        AprilTagDetection target = null;
        for (AprilTagDetection d : detections) {
            if (d.id == TARGET_TAG_ID) {
                target = d;
                break;
            }
        }
        if (target == null) return null;

        // ftcPose.yaw is in degrees
        double yawDeg = target.ftcPose.yaw;
        telemetry.addData("Tag", target.id);
        telemetry.addData("Yaw", yawDeg);

        double turn = yawDeg * kP_TURN;
        turn = Range.clip(turn, -MAX_TURN_POWER, MAX_TURN_POWER);

        // deadzone so it stops wiggling
        if (Math.abs(yawDeg) < 1.5) {
            turn = 0;
        }

        return turn;
    }
}
