package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;

@Autonomous(name="Auto")
public class Auto extends LinearOpMode {
    DcMotorEx lf, rf, lb, rb;
    FlyWheel fly;

    @Override public void runOpMode() {
        lf = hardwareMap.get(DcMotorEx.class, "lf");
        rf = hardwareMap.get(DcMotorEx.class, "rf");
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rb = hardwareMap.get(DcMotorEx.class, "rb");

        fly = new FlyWheel(hardwareMap);

        // Direcciones y modos (ejemplo)
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.REVERSE);

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        if (isStopRequested()) return;

        // 1) turns flywheel and waits to go up
        fly.setOn(true);
        sleep(1500);

        // 2) (If we have indexor) push N times
        // feedOnce(); feedOnce(); feedOnce();

        // 3) Goes forward in a certain distance with encoders (example)
        driveForward(800, 0.5);

        // 4) Turns off flywheels
        fly.setOn(false);
    }

    void driveForward(int ticks, double p){
        // Configura objetivos
        lf.setTargetPosition(ticks);
        rf.setTargetPosition(ticks);
        lb.setTargetPosition(ticks);
        rb.setTargetPosition(ticks);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lf.setPower(p); rf.setPower(p); lb.setPower(p); rb.setPower(p);

        // Wait for them to get there (loop of simple blocking)
        while (opModeIsActive() &&
                (lf.isBusy() || rf.isBusy() || lb.isBusy() || rb.isBusy())) {
            telemetry.addData("lf", lf.getCurrentPosition());
            telemetry.update();
        }

        // breaks and continue using the normal encoder mode
        lf.setPower(0); rf.setPower(0); lb.setPower(0); rb.setPower(0);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

