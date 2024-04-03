// Class LineFollower
package t22LineFollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollower extends Thread {
    private DataExchange dataExchange;
    private EV3ColorSensor colorSensor;
    private final double colorPattern = 0.27;

    public LineFollower(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
        colorSensor = new EV3ColorSensor(SensorPort.S3);
    }
    
    @Override
    public void run() {
        final SampleProvider sp = colorSensor.getRedMode();
        while (true) {
            if (dataExchange.getCMD() == 1) {
                float[] sample = new float[sp.sampleSize()];
                sp.fetchSample(sample, 0);

                if (!dataExchange.getObstacleDetected()) {
                    // No obstacle detected, continue line following
                    if (sample[0] < colorPattern) {
                        Motor.B.setSpeed(130);
                        Motor.A.setSpeed(260);
                        Motor.B.forward();
                        Motor.A.forward();
                    } else {
                        Motor.B.setSpeed(260);
                        Motor.A.setSpeed(130);
                        Motor.B.forward();
                        Motor.A.forward();
                    }
                } else {
                    // Obstacle detected, stop the robot
                    Motor.B.stop();
                    Motor.A.stop();
                    // Avoid the obstacle
                    avoidObstacle();
                    // After avoiding obstacle, resume line following
                }
            } else {
                Motor.B.stop();
                Motor.A.stop();
            }
        }
    }

    private void avoidObstacle() {
        // Implement obstacle avoidance logic here
        // For example, turn the robot to avoid the obstacle
        Motor.A.forward(); // Turn left
        Motor.B.backward();
        // Adjust motor speeds based on your robot's design
        // Wait for the robot to turn sufficiently to avoid the obstacle
        Delay.msDelay(500); 
        Motor.B.forward();
        Motor.A.forward();
         /*  // Turn left
        Motor.A.backward();
        Motor.B.forward();
        Delay.msDelay(700);
        Motor.B.forward();
        Motor.A.forward();
        Motor.A.backward();
        Motor.B.forward();
        Delay.msDelay(700);
        Motor.B.forward();
        Motor.A.forward();
        Motor.B.forward();
        Motor.A.forward();*/
    }

}