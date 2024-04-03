//HarisRobot
package t22LineFollower;

import lejos.ev3.*; // Importing necessary EV3 classes
import lejos.hardware.Button; // Importing Button class for button input
import lejos.hardware.lcd.LCD; // Importing LCD class for display
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class HarisRobot { // Declaring HarisRobot class

    private static DataExchange DE; // Declaring instance variable for DataExchange
    private static LineFollower LFObj; // Declaring instance variable for LineFollower
    private static ObstacleDetector ODObj; // Declaring instance variable for ObstacleDetector
    private static int lapCounter = 1;

    public static void main(String[] args) { // Main method
    	EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2); // Assuming your ultrasonic sensor is connected to port S1
    	DataExchange DE = new DataExchange(ultrasonicSensor);

        //DE = new DataExchange(); // Creating new DataExchange object
        ODObj = new ObstacleDetector(DE); // Creating new ObstacleDetector object with DataExchange parameter
        LFObj = new LineFollower(DE); // Creating new LineFollower object with DataExchange parameter
        ODObj.start(); // Starting the ObstacleDetector thread
        LFObj.start(); // Starting the LineFollower thread
        DE.setCMD(1); // or any other value based on your logic


        while (!Button.ESCAPE.isDown()) { // Waiting for ESCAPE button to be pressed
        	// Start timer when obstacle is detected for the first time
            if (DE.getObstacleDetected() && DE.getCMD() == 1) {
                DE.startTimer(); // Start lap timer
            }

            // Check if obstacle detected for the second time
            if (DE.getObstacleDetected() && DE.getCMD() == 1) {
                long lapTime = DE.stopTimer(); // Stop lap timer and calculate lap time
                // Display lap time
                LCD.clear();
                LCD.drawString("Lap\tTime", 0, 0);
                LCD.drawString(String.format("%d\t%.1f Min", lapCounter++/*There is an error here: lapCounter cannot be resolved to a variable. Fix, please*/, lapTime / 60000.0), 0, 1); // Convert milliseconds to minutes
                LCD.refresh();
            }

        }

        LCD.drawString("Finished", 0, 7); // Displaying "Finished" message on LCD
        LCD.refresh(); // Refreshing the LCD display
        System.exit(0); // Exiting the program
    }
}
