import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * Controls game restarting
 * <p>
 * Restarts by creating a new Stack
 * </P>
 * <p>
 * Ensures all setting preferences are continuously used as game restarts
 * </p>
 * @author Kevin Lan
 *
 */
public class Restarter implements KeyListener{
	
	private boolean pressed = false;
	
	//setting preferences
	private boolean toggleAudio;
	private boolean toggleParticles;
	private boolean toggleTitle;
	
	public Restarter(boolean a, boolean b, boolean c){
		toggleAudio = a;
		toggleParticles = b;
		toggleTitle = c;
	}
	
	public boolean getPressed(){
		return pressed;
	}
	
	/**
	 * Restarts game via new instance of Stack
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			pressed = true;
			Stack stack = new Stack(toggleAudio, toggleTitle, toggleParticles);
			stack.setVisible(true);
			stack.initComponents();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
