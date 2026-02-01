
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

@Autonomous(name = "blueAutoPathing", group = "Autonomous")
@Configurable // Panels
public class blueAutoPathing extends OpMode {
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
                                    new Pose(25.981, 130.305),

                                    new Pose(29.637, 126.784)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(150))

                    .build();

            shootBallTop = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(29.637, 126.784),
                                    new Pose(88.361, 83.529),
                                    new Pose(40.969, 81.873),
                                    new Pose(17.647, 83.607)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(180))

                    .build();

            ballTopShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(17.647, 83.607),

                                    new Pose(29.546, 126.440)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(150))

                    .build();

            shootBallMid = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(29.546, 126.440),
                                    new Pose(62.183, 48.335),
                                    new Pose(45.900, 59.364),
                                    new Pose(16.501, 59.583)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(180))

                    .build();

            ballMidshoot = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(16.501, 59.583),
                                    new Pose(53.761, 61.389),
                                    new Pose(29.792, 126.195)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(150))

                    .build();
        }
    }


    public void autonomousPathUpdate() {
        // Add your state machine Here
        // Access paths with paths.pathName
        // Refer to the Pedro Pathing Docs (Auto Example) for an example state machine
    }
}
    