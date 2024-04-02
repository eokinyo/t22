//DataExchange
package t22LineFollower;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class DataExchange {

    private boolean obstacleDetected = false;
    private SampleProvider distanceMode;
    private int CMD;

    public DataExchange(EV3UltrasonicSensor ultrasonicSensor) {
        // Initialize the SampleProvider for distance mode
        this.distanceMode = ultrasonicSensor.getDistanceMode();
    }

    public void setObstacleDetected(boolean status) {
        obstacleDetected = status;
    }

    public boolean getObstacleDetected() {
        return obstacleDetected;
    }

    public SampleProvider getDistanceMode() {
        return distanceMode;
    }
    public void setCMD(int command) {
        CMD = command;
    }

	public int getCMD() {
		// TODO Auto-generated method stub
		return CMD;
	}
}
