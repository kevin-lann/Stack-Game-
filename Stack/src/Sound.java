import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Controls the audio for the game
 * <p>
 * Allows certain sounds to be player and allows certain sounds to be stopped
 * </p>
 * @author Kevin Lan
 *
 */
public class Sound {
	
	private static boolean on = true;
	
	//Master volume off
	public void turnOff (){
		on = false;
	}
	
	//Master volume on
	public void turnOn (){
		on = true;
	}
	
	/**
	 * Play block placed sound
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public static void blockSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		if(on){
		 File bp = new File("blockPlaced.wav");
		    try{
		        Clip clip = AudioSystem.getClip();
		        clip.open(AudioSystem.getAudioInputStream(bp));
		        clip.start();
		    } catch (Exception e){
		        e.printStackTrace();
		    }
		}
	 }
	
	/**
	 * Play perfect placement sound
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public static void perfectSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		 if(on){
			File bp = new File("perfectPlaced.wav");
			    try{
			        Clip clip = AudioSystem.getClip();
			        clip.open(AudioSystem.getAudioInputStream(bp));
			        clip.start();
			    } catch (Exception e){
			        e.printStackTrace();
			    }
		 }
	 }
	
	
	/**
	 * Play lose game sound
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public static void loseSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		if(on){ 
			File bp = new File("loseSound.wav");
			    try{
			        Clip clip = AudioSystem.getClip();
			        clip.open(AudioSystem.getAudioInputStream(bp));
			        clip.start();
			    } catch (Exception e){
			        e.printStackTrace();
			    }
		}
	 }
}
