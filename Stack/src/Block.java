import java.awt.Color;

/**
 * Controls Block object 
 * @author Kevin Lan
 *
 */
public class Block extends GameObject{
	private int vx = 0;
	private int defaultSpeed = 3;
	private int ticks = 0;
	private static final Color[] colors = {Color.WHITE, new Color(220, 220, 220)}; 
	private int currentColor = 0;
	
	@Override
	public void act() {
		setX(getX() + vx);
		if(ticks % 30 == 0 && isActive()){
			setColor(colors[currentColor]);
			if(currentColor == 0 ){
				currentColor = 1;
			}
			else{
				currentColor = 0;
			}
			
		}
		ticks++;
	}
	
	/**
	 * Activates the block (start moving)
	 * @param i
	 */
	public void activate(int i){
		if(i % 2 != 0){
			vx = defaultSpeed;
		}
		else{
			vx = -defaultSpeed;
		}
	}
	
	public boolean isActive(){
		return vx != 0;
	}
	
	/**
	 * Changes block x velocity to 0
	 */
	public void stop(){
		vx = 0;
	}
	
	/**
	 * Change size of block
	 * @param width
	 * @param height
	 */
	public void changeSize(int width, int height){
		setSize(width, height);
	}
	
	public void setSpeed(int s){
		defaultSpeed = s;
	}
	

}
