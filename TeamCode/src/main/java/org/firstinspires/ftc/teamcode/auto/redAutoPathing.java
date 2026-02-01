
package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;

@Autonomous(name = "redAutoPathing", group = "Autonomous")
@Configurable // Panels
public class redAutoPathing extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private Paths paths; // Paths defined in the Paths class

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));

        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }


    public static class Paths {
        public PathChain startShoot;
        public PathChain shootBallTop;
        public PathChain ballTopShoot;
        public PathChain shootBallMid;
        public PathChain ballMidshoot;

        public Paths(Follower follower) {
            startShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(119.496, 127.962),

                                    new Pose(116.548, 124.441)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(38), Math.toRadians(30))

                    .build();

            shootBallTop = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(116.548, 124.441),
                                    new Pose(108.598, 93.967),
                                    new Pose(63.336, 79.104),
                                    new Pose(125.860, 83.820)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(0))

                    .build();

            ballTopShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(125.860, 83.820),

                                    new Pose(116.883, 124.523)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(30))

                    .build();

            shootBallMid = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(116.883, 124.523),
                                    new Pose(90.302, 60.477),
                                    new Pose(74.870, 56.169),
                                    new Pose(126.844, 60.009)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(0))

                    .build();

            ballMidshoot = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(126.844, 60.009),
                                    new Pose(97.217, 70.975),
                                    new Pose(116.490, 124.491)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(30))

                    .build();
        }
    }


    public void autonomousPathUpdate() {
        // Add your state machine Here
        // Access paths with paths.pathName
        // Refer to the Pedro Pathing Docs (Auto Example) for an example state machine
    }
}
    