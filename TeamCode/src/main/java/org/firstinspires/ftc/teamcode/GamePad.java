package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GamePad extends OpMode {

    @Override
    public void init() {


    }

    @Override
    public void loop() {
        //runs 50x* a second

        double speedForward = -gamepad1.left_stick_y/ 2.0; // making it go to the opposite direction and divide it by two for having the full power
        double diffXJoysticks = gamepad1.right_stick_x - gamepad1.left_stick_x;
        double sumYJoysticks = gamepad1.right_stick_y + gamepad1.left_stick_y;

        telemetry.addData("x left", gamepad1.left_stick_x);
        telemetry.addData("y left", gamepad1.left_stick_y);

        telemetry.addData("x right", gamepad1.right_stick_x);
        telemetry.addData("y right", gamepad1.right_stick_y);

        telemetry.addData("a button", gamepad1.a);
        telemetry.addData("b button", gamepad1.b);
        telemetry.addData("triangle", gamepad1.triangle);
        telemetry.addData("square button", gamepad1.square);

        telemetry.addData("right trigger", gamepad1.right_trigger);
        telemetry.addData("left trigger", gamepad1.left_trigger);

    }
}
