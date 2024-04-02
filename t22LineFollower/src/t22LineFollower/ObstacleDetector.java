// Class ObstacleDetector
package t22LineFollower;

import lejos.hardware.motor.Motor;
import lejos.robotics.SampleProvider;

public class ObstacleDetector extends Thread {
    private DataExchange dataExchange;
    private final float obstacleDistanceThreshold = 0.1f; // Distance threshold to detect obstacles

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
                // Obstacle detected, notify DataExchange
                dataExchange.setObstacleDetected(true);
            } else {
                // No obstacle detected
                dataExchange.setObstacleDetected(false);
            }
            
            try {
                // Wait for a short duration before checking again
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
