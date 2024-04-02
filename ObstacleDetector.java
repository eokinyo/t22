package t22LineFollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class ObstacleDetector {
    private EV3UltrasonicSensor ultrasonicSensor;
    private final float obstacleDistanceThreshold = 0.15f; // Distance threshold to detect obstacles

    public ObstacleDetector(EV3UltrasonicSensor ultrasonicSensor) {
        this.ultrasonicSensor = ultrasonicSensor;
    }

    public void avoidObstacle() {
        SampleProvider ultrasonicSampleProvider = ultrasonicSensor.getDistanceMode();
        float[] ultrasonicSample = new float[ultrasonicSampleProvider.sampleSize()];
        ultrasonicSampleProvider.fetchSample(ultrasonicSample, 0);

        float distance = ultrasonicSample[0];

        if (distance <= obstacleDistanceThreshold) {
            // Obstacle detected, avoid it
            Motor.B.setSpeed(200); // Reduce speed to turn while avoiding obstacle
            Motor.A.setSpeed(200);
            // Rotate the robot to avoid the obstacle
            Motor.B.forward();
            Motor.A.backward();
        }
    }
}
