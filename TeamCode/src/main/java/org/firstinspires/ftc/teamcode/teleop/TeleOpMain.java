
/*package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "TeleOp PRO – Drive+Intake+Flywheel", group = "PRO")
public class TeleOpMain extends LinearOpMode {

    // --- Drive motors (mecanum) ---
    private DcMotor frontLeft, backLeft, frontRight, backRight;

    // --- Mechanisms ---
    private DcMotor intake;          // simple on/off/reverse
    private DcMotorEx flywheel;      // use Ex for future velocity PIDF
    private Servo indexer;           // OPTIONAL: feeder; null if not present

    // --- Toggles / edge detection ---
   // private boolean intakeToggle = false;
   // private boolean flywheelToggle = false;
   // private boolean lastA = false, lastX = false;

    @Override
    public void runOpMode() { // =================== INIT / HARDWARE MAP ===================
        // Drive
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRight = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");
        intake     = hardwareMap.get(DcMotor.class,  "intake");
        flywheel   = hardwareMap.get(DcMotorEx.class,"flywheel");
        }
 */
