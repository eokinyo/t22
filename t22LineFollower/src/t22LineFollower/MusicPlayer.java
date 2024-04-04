//MusicPlayer class
package t22LineFollower;

import lejos.hardware.Sound;
import lejos.utility.Delay;

public class MusicPlayer extends Thread {
 @Override
 public void run() {
     while (true) {
         SoundManager.playLapMusic(); // Play music during the lap
         Delay.msDelay(100); // Adjust the delay between playing music
     }
 }
}