package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotor intake;
    public Intake(HardwareMap hw) { intake = hw.get(DcMotor.class, "intake"); }
    public void on() { intake.setPower(1.0); }
    public void reverse() { intake.setPower(-1.0); }
    public void off() { intake.setPower(0.0); }
}

