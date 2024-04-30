// LineFollower class (updated)
package t22LineFollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;/*
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;*/

//Just a comment to see how the codeowners file works

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
                    dataExchange.setCMD(2); // Send command to resume line following
                }
            } else if (dataExchange.getCMD() == 2) {
                dataExchange.setCMD(1); // Reset command to continue line following
                // Increment lap counter or perform any necessary actions
            } else if (dataExchange.getCMD() == 3) {
                // Stop the robot
                Motor.B.stop();
                Motor.A.stop();
            }
        }
    }
    /*private int getSpeedFromWebsite() {
        // Code to fetch speed value from website
        // You need to implement this part using HTTP requests to your website
        // Here's a basic example using JAX-RS client:
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://127.0.0.1:8080/rest/t22RestfulProject/addrobot/speed");
        Builder request = target.request();
        String speedStr = request.get(String.class);
        int speed = Integer.parseInt(speedStr);
        return speed;
    }*/
    private void avoidObstacle() {
        // Implement obstacle avoidance logic here
        // For example, turn the robot to avoid the obstacle
        Motor.A.forward(); // Turn left
        Motor.B.backward();
        // Adjust motor speeds based on your robot's design
        // Wait for the robot to turn sufficiently to avoid the obstacle
        Delay.msDelay(300); 
        Motor.A.setSpeed(150);
        Motor.B.setSpeed(150);
        Motor.B.forward();
        Motor.A.forward();
        Delay.msDelay(2200);
        Motor.B.forward(); // Turn right
        Motor.A.backward();
        Delay.msDelay(500); 
        Motor.A.setSpeed(150);
        Motor.B.setSpeed(150);
        Motor.B.forward();
        Motor.A.forward();
        Delay.msDelay(2400);
        Motor.B.forward(); // Turn right
        Motor.A.backward();
        Delay.msDelay(500); 
        Motor.A.setSpeed(150);
        Motor.B.setSpeed(150);
        Motor.B.forward();
        Motor.A.forward();
        Delay.msDelay(1600);
        Motor.A.forward(); // Turn left
        Motor.B.backward();
        Delay.msDelay(300); 
        
    }
}
