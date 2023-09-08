import java.awt.Color;

/**
 * Controls Particle object
 * @author Kevin Lan
 *
 */
public class Particle extends GameObject{
	
	private int r = 255, g = 255, b = 255;
	private Color color = new Color(255, 255, 255);
	private int ticks = 0;
	private int fadeSpeed = 1;
	
	@Override
	public void act() {
		if(b < 50){
			this.setVisible(false);
		}
		if(ticks % fadeSpeed == 0){
			fade();
		}
		
		ticks++;
	}
	
	/**
	 * Fades particle by adjusting its individual r, g and b values
	 */
	private void fade(){
		if(r > 8) r -= 8;
		if(g > 4) g -= 4;
		if(b > 2) b -= 2;
		setColor(new Color(r, g, b));
	}

}
