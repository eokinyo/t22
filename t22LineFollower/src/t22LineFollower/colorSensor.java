// ColorSensor class
package t22LineFollower;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class colorSensor {
    private EV3ColorSensor colorSensor;
    private SampleProvider colorSampler;

    public colorSensor() {
        colorSensor = new EV3ColorSensor(SensorPort.S3);
        colorSampler = colorSensor.getRedMode();
    }

    public float[] getSample() {
        float[] sample = new float[colorSampler.sampleSize()];
        colorSampler.fetchSample(sample, 0);
        return sample;
    }
}