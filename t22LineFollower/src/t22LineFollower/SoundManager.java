
//SoundManager

package t22LineFollower;

import java.util.concurrent.ThreadLocalRandom;

import lejos.hardware.Sound;
import lejos.utility.Delay;

//SoundManager class
public class SoundManager {
    public static void playObstacleDetectedSound() {
        Sound.beepSequence(); // Play a beep sequence when obstacle detected
    }

    public static void playLapMusic() {
        int[] notes = {440, 494, 523, 587, 659, 698, 784, 880}; // Frequencies for a C major scale
        int[] durations = {500, 500, 500, 500, 500, 500, 500, 500}; // Durations for each note
        for (int i = 0; i < notes.length; i++) {
            Sound.playTone(notes[i], durations[i]); // Play each note
            Delay.msDelay(durations[i]); // Wait for the duration of the note
        }
    }

    private static void playRandomBeats(int numberOfBeats) {
        for (int i = 0; i < numberOfBeats; i++) {
            int frequency = ThreadLocalRandom.current().nextInt(100, 1000); // Generate a random frequency
            int duration = ThreadLocalRandom.current().nextInt(100, 1000); // Generate a random duration
            Sound.playTone(frequency, duration); // Play a tone with random frequency and duration
            Delay.msDelay(500); // Wait for a short duration between beats
        }
    }
}