// ObstacleDetector class
package t22LineFollower;

import lejos.robotics.SampleProvider;

public class ObstacleDetector extends Thread {
    private DataExchange dataExchange;
    private final float obstacleDistanceThreshold = 0.1f;
    private boolean initialObstacleDetected = false; // Flag to track initial obstacle detection
    private int obstacleCount = 0; // Track the number of obstacles detected

    public ObstacleDetector(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
    }

    @Override
    public void run() {
        SampleProvider ultrasonicSampleProvider = dataExchange.getDistanceMode();
        float[] ultrasonicSample = new float[ultrasonicSampleProvider.sampleSize()];
        
        while (true) {
            ultrasonicSampleProvider.fetchSample(ultrasonicSample, 0);
            float distance = ultrasonicSample[0];

            if (distance <= obstacleDistanceThreshold) {
                if (!initialObstacleDetected) {
                    dataExchange.setObstacleDetected(true);
                    initialObstacleDetected = true; // Set the flag for initial detection
                    obstacleCount++; // Increment obstacle count
                }
            } else {
                if (initialObstacleDetected) {
                    dataExchange.setObstacleDetected(false);
                    initialObstacleDetected = false; // Clear the flag for initial detection
                }
            }
            
            // Check if the obstacle is detected for the second time
            if (obstacleCount >= 2) {
                dataExchange.setCMD(3); // Send command to stop the robot
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
