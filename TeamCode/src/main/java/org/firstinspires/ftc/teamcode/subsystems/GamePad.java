package org.firstinspires.ftc.teamcode.subsystems;

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

        // MOVEMENT

        telemetry.addData("x left", gamepad1.left_stick_x);
        telemetry.addData("y left", gamepad1.left_stick_y);
        telemetry.addData("x right", gamepad1.right_stick_x);
        telemetry.addData("y right", gamepad1.right_stick_y);



        telemetry.addData("a intake", gamepad1.a); // on and off
        telemetry.addData("b intake reverse", gamepad1.b);
        telemetry.addData("x fly wheel", gamepad1.x); // on and off shooter
        telemetry.addData("y power of shooting", gamepad1.circle); // tbd is optional

        telemetry.addData("right trigger", gamepad1.right_trigger); // tbd
        telemetry.addData("left trigger", gamepad1.left_trigger); // tbd
        telemetry.addData("dpad up", gamepad1.dpad_up); // tbd
        telemetry.addData("dpad down", gamepad1.dpad_down); // tbd
        telemetry.addData("right bumper", gamepad1.right_bumper); //tbd
        telemetry.addData("left bumper", gamepad1.left_bumper);// tbd


    }
}
