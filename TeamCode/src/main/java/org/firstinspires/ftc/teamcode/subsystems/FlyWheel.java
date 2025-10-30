package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlyWheel {
    private DcMotorEx fly;
    private boolean on = false;
    public FlyWheel(HardwareMap hw) { fly = hw.get(DcMotorEx.class, "flywheel"); }
    public void setOn(boolean v){ on=v; fly.setMode(DcMotor.RunMode.RUN_USING_ENCODER); fly.setPower(on?0.8:0.0); }
    public boolean isOn(){ return on; }
}

