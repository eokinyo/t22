// LineFollower class (updated)
package t22LineFollower;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//Just a comment to see how the codeowners file works

public class LineFollower extends Thread {
    private DataExchange dataExchange;
    private EV3ColorSensor colorSensor;
    private double color = 0.27; //Default value for color
    private final double colorPattern = color;
    private int speed = 260; //Default value for speed
    private double angle = 2; //Default value for angle

    public LineFollower(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
        colorSensor = new EV3ColorSensor(SensorPort.S3);
    }
    
    @Override
    public void run() {
        final SampleProvider sp = colorSensor.getRedMode();
        while (true) {
        	getData();
            if (dataExchange.getCMD() == 1) {
                float[] sample = new float[sp.sampleSize()];
                sp.fetchSample(sample, 0);

                if (!dataExchange.getObstacleDetected()) {
                    // No obstacle detected, continue line following
                    if (sample[0] < colorPattern) {
                        Motor.B.setSpeed((int) (speed/angle));
                        Motor.A.setSpeed(speed);
                        Motor.B.forward();
                        Motor.A.forward();
                    } else {
                        Motor.B.setSpeed(speed);
                        Motor.A.setSpeed((int) (speed/angle));
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
    public void getData() {
    	URL url = null;
  		HttpURLConnection conn = null;
  		InputStreamReader isr = null;
  		BufferedReader br=null;

  		String s=null;
		try {
			url = new URL("http://192.168.101.218:8080/rest/t22RestfulProject/readrobots");
			conn = (HttpURLConnection)url.openConnection();
  			//System.out.println(conn.toString()); 
			InputStream is=null;
			try {
				is=conn.getInputStream();
			}
			catch (Exception e) {
	  			System.out.println("Exception conn.getInputSteam()");
	  			e.printStackTrace();
	            System.out.println("Cannot get InputStream!");
			}
			isr = new InputStreamReader(is);
      		br=new BufferedReader(isr);
			String data = br.readLine();
			String values[]=data.split("#");
			speed = (int)Double.parseDouble(values[1]);
			angle = (int)Double.parseDouble(values[3]);
			color = Double.parseDouble(values[4]);
		}
  		catch(Exception e) {
  			e.printStackTrace();
            System.out.println("Some problem!");
  		}
    }
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