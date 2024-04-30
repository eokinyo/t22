package t22LineFollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollower extends Thread {
    private DataExchange dataExchange;
    private EV3ColorSensor colorSensor;
    private final double colorPattern = 0.27; // Threshold for line detection

    // Constructor to initialize data exchange and color sensor
    public LineFollower(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
        this.colorSensor = new EV3ColorSensor(SensorPort.S3);
    }

    // Main run method for line following behavior
    @Override
    public void run() {
        // Sample provider for the color sensor
        final SampleProvider sp = colorSensor.getRedMode();
        
        // Create an instance of httptest to read data from the server
        httptest httpTest = new httptest();

        // Infinite loop for line following behavior
        while (true) {
            // Check the current command
            if (dataExchange.getCMD() == 1) {
                // Fetch the sample from the color sensor
                float[] sample = new float[sp.sampleSize()];
                sp.fetchSample(sample, 0);

                // Fetch data from the server (provide the correct URL)
                String urlString = "http://<server-ip>:<port>/<endpoint>";
                String jsonResponse = httpTest.readDataFromUrl(urlString);

                // Parse the response for speed adjustment (without using org.json)
                double speed = extractSpeedFromResponse(jsonResponse);

                // Check for obstacle detection
                if (!dataExchange.getObstacleDetected()) {
                    // Continue line following
                    adjustMotorSpeedsForLineFollowing(sample[0], speed);
                } else {
                    // Obstacle detected, stop the robot
                    Motor.B.stop();
                    Motor.A.stop();
                    // Avoid the obstacle
                    avoidObstacle();
                    // After avoiding the obstacle, resume line following
                    dataExchange.setCMD(2); // Send command to resume line following
                }
            } else if (dataExchange.getCMD() == 2) {
                // Reset command to continue line following
                dataExchange.setCMD(1);
            } else if (dataExchange.getCMD() == 3) {
                // Stop the robot
                Motor.B.stop();
                Motor.A.stop();
            }
        }
    }

    // Method to adjust motor speeds for line following based on color sample and speed
    private void adjustMotorSpeedsForLineFollowing(float colorSample, double speed) {
        if (colorSample < colorPattern) {
            // Turn left if sample is below color pattern threshold
            Motor.B.setSpeed((int)(speed / 2)); // Slow down Motor B
            Motor.A.setSpeed((int)speed); // Keep Motor A at full speed
            Motor.B.forward();
            Motor.A.forward();
        } else {
            // Turn right if sample is above color pattern threshold
            Motor.B.setSpeed((int)speed); // Keep Motor B at full speed
            Motor.A.setSpeed((int)(speed / 2)); // Slow down Motor A
            Motor.B.forward();
            Motor.A.forward();
        }
    }

    // Method to extract speed from the server response (without using org.json)
    private double extractSpeedFromResponse(String response) {
        // Look for the speed value in the response (e.g., "speed":150)
        int startIndex = response.indexOf("\"speed\":") + 8;
        int endIndex = response.indexOf(',', startIndex);
        if (endIndex == -1) {
            endIndex = response.indexOf('}', startIndex);
        }
        
        // Extract the speed value and convert it to a double
        String speedStr = response.substring(startIndex, endIndex);
        return Double.parseDouble(speedStr);
    }

    // Method to avoid obstacles
    private void avoidObstacle() {
        // Implement obstacle avoidance logic here
        // For example, you can turn the robot to avoid the obstacle

        // Turn left
        Motor.A.forward();
        Motor.B.backward();
        Delay.msDelay(300);
        
        // Move forward
        Motor.A.setSpeed(150);
        Motor.B.setSpeed(150);
        Motor.B.forward();
        Motor.A.forward();
        Delay.msDelay(2200);
        
        // Turn right
        Motor.B.forward();
        Motor.A.backward();
        Delay.msDelay(500);
        
        // Move forward
        Motor.A.setSpeed(150);
        Motor.B.setSpeed(150);
        Motor.B.forward();
        Motor.A.forward();
        Delay.msDelay(2400);
        
        // Turn right
        Motor.B.forward();
        Motor.A.backward();
        Delay.msDelay(500);
        
        // Move forward
        Motor.A.setSpeed(150);
        Motor.B.setSpeed(150);
        Motor.B.forward();
        Motor.A.forward();
        Delay.msDelay(1600);
        
        // Turn left
        Motor.A.forward();
        Motor.B.backward();
        Delay.msDelay(300);
    }
}
