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
            // Empty loop
        }

        LCD.drawString("Finished", 0, 7); // Displaying "Finished" message on LCD
        LCD.refresh(); // Refreshing the LCD display
        System.exit(0); // Exiting the program
    }
}
