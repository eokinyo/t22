// HarisRobot class (updated)
package t22LineFollower;

import lejos.ev3.*;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class HarisRobot {
    private static DataExchange DE;
    private static LineFollower LFObj;
    private static ObstacleDetector ODObj;
    private static MusicPlayer musicPlayer;
    private static int lapCounter = 1;
    private static long lapStartTime = 0; // Variable to store lap start time
    private static boolean buttonPressed = false; // Flag to track button press

    public static void main(String[] args) {
        try {
            EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
            DE = new DataExchange(ultrasonicSensor);
            ODObj = new ObstacleDetector(DE);
            LFObj = new LineFollower(DE);
            musicPlayer = new MusicPlayer();

            ODObj.start();
            LFObj.start();
            musicPlayer.start();
            DE.setCMD(0); // Set initial command to 0 (idle state)

            LCD.clear();
            LCD.drawString("Press the middle", 0, 0);
            LCD.drawString("button to start", 0, 1);
            LCD.refresh();

            while (!Button.ESCAPE.isDown()) {
                if (!buttonPressed && Button.ENTER.isDown()) { // Check if middle button is pressed and not previously pressed
                    DE.setCMD(1); // Start robot movement
                    lapStartTime = System.currentTimeMillis(); // Start lap timer
                    buttonPressed = true; // Set button press flag
                }

                if (DE.getObstacleDetected() && DE.getCMD() == 1) {
                    if (lapStartTime != 0) { // Stop timer only if it's started
                        long lapTime = System.currentTimeMillis() - lapStartTime;
                        lapStartTime = 0; // Reset the lap start time
                        LCD.clear();
                        LCD.drawString("Lap\tTime", 0, 0);
                        LCD.drawString(String.format("%d\t%.1f Min", lapCounter++, lapTime / 60000.0), 0, 1);
                        LCD.refresh();
                    }
                }
            }

            LCD.clear();
            LCD.drawString("Finished", 0, 7);
            LCD.refresh();
        } catch (Exception e) {
            LCD.clear();
            LCD.drawString("Error occurred:", 0, 0);
            LCD.drawString("Time: " + System.currentTimeMillis(), 0, 1);
            LCD.refresh();
        } finally {
            LCD.refresh();
            System.exit(0);
        }
    }
}
