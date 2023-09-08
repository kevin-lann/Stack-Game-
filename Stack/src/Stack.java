import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * RESOURCES USED -----------------------------------------------------------------
 * 
 * JLabel customization:
	https://stackhowto.com/how-to-change-font-size-and-font-style-of-a-jlabel/
	
	Add font
	https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
	
	Add image to jframe
	https://www.codespeedy.com/how-to-add-an-image-in-jframe/
	
	Sound
	https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	
	KeyListener
	https://www.javatpoint.com/java-keylistener
	https://www.delftstack.com/howto/java/java-key-listener/
	
	
	JButton
	https://www.javatpoint.com/java-jbutton
	
	Multiple JButton ActionListeners
	https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing
	
	Deleting an object
	https://stackoverflow.com/questions/5757552/deleting-an-object-in-java
	
	
	Deleting text from a file
	https://www.geeksforgeeks.org/java-program-delete-certain-text-file/
	https://docs.oracle.com/javase/7/docs/api/java/io/OutputStreamWriter.html
	https://tutorialspoint.com/java/io/writer_flush.htm
	https://www.quora.com/How-do-I-clear-a-file-in-Java-before-writing-to-it-again?share=1 
	-------------------------------------------------------------------------------
 */

/*
 * Kevin Lan
 * June 18th, 2022
 * ICS4U - Period 4 - Mr. Benum
 * Stack Final Project
 */

/**
 * The stack game controller.
 * <p>
 * This class handles the actions the game performs throughout the game play
 * </p>
 * @author Kevin Lan
 *
 */

public class Stack extends Game{
	
	//Block related fields
	private int height;
	private final int DEFAULT_WIDTH = 300;
	private int blockHeight = 12;
	private int blockWidth = DEFAULT_WIDTH;
	private int cutLen = 0;
	private int cutThres = 6;		//Max amount of cutLen in px that counts as a "perfect placement"
	private int numBlocks = 60;
	private Block[] blocks;	//Stores all the block objects
	
	private static final Color[] colors = {Color.WHITE, new Color(220, 220, 220)}; 
	
	//Tick and speed related fields
	private final int DEFAULT_SPEED = 6;
	private final int DEFAULT_TICKS = (int)((960 * 0.3) / DEFAULT_SPEED);
	private int ticks = 0;
	private int i = 1;
	
	private int speed = DEFAULT_SPEED;
	private int speedFactor = DEFAULT_TICKS;
	private int incSpeedFactor = 12;		//represents how often speed is increased (once every n blocks)
	
	//Game related fields (implement if have time)
	private static enum Difficulty{EASY, MEDIUM, HARD};
	private Difficulty gameDiff;
	private static enum Status {WIN, LOSE};
	private Status gameStatus;
	
	//Labels and images
	private Font customFont;
	private Font customFontLarge;
	private Font customFontSmall;
	private JLabel points = new JLabel();
	private JLabel bigTitle = new JLabel();
	private JLabel perfect = new JLabel();
	
	//Sound
	private Sound snd = new Sound();
	
	//options
	private boolean toggleAudio;
	private boolean toggleParticles;
	private boolean toggleTitle;
	
	private boolean titleState = false;
	
	/**
	 * Default no args constructor. Sets all settings to on.
	 */
	public Stack(){
		toggleAudio = true;
		toggleParticles = true;
		toggleTitle = true;
	}
	/**
	 * Adjustable settings constructor
	 * @param a
	 * @param b
	 * @param c
	 */
	public Stack(boolean a, boolean b,  boolean c){
		toggleAudio = a;
		toggleTitle = b;
		toggleParticles = c;
	}

	@Override
	public void act(){
		
		//check for win
		if(checkWin()){
			win();
		}
		
		//check loss via player not stopping block
		if(checkLossViaNotStopped()){
			loss();
		}
		
		//add next block every interval (determined by speedFactor field)
		if(ticks % speedFactor == 0 && i < numBlocks && !blocks[i-1].isActive()){
			nextBlock();
		}
		
		//particles
		if(toggleParticles && ticks % 2 == 0){
			doParticles();
		}
		
		//check for loss via not placing block
		if(i > 1 && ticks % (2 * speedFactor) == 0 && blocks[i].isActive()){
			loss();
		}
		
		//Cut block and stop block if key pressed
		if(ZKeyPressed() && ticks % 5 == 0 ) {
			if(i > 1 && blocks[i-1].isActive()){
				cutBlock();
				
				//check if block placement is perfect
				if(blocks[i-1].getX() == blocks[i-2].getX() && cutLen <= cutThres ){
						perfectPlaced();
				}
			}
			blocks[i-1].stop();
			//check loss after placement
			if(i > 2 && !blocks[i-1].isActive() && !blocks[i-2].isActive() && checkLoss()){
				loss();
			}
			else{
				points.setText((i - 1) + "");
				points.repaint();
			}
		}
		
		
		//get rid of perfect
		if(perfect.isShowing() && ticks % 200 == 0){
			perfect.setVisible(false);
			perfect.repaint();
		}
		
		//update JLabels
		if(toggleTitle){
			animateTitle(75);
			bigTitle.repaint();
		}
		
		incSpeed();
		
		ticks++;
	}
	
	/**
	 * Checks if block placement misses
	 * If so, return a loss (true)
	 * @return whether loss occurs
	 */
	private boolean checkLoss(){
		if(blocks[i-1].getX() + blockWidth < blocks[i-2].getX() || blocks[i-1].getX() > blocks[i-2].getX() + blockWidth){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a loss occurred via a block exceeding game boundaries
	 * @return whether loss via not stopped occurs
	 */
	private boolean checkLossViaNotStopped(){
		if((i-1) % 2 == 0 && blocks[i-1].getX() < -blockWidth){
			return true;
		}
		else if (blocks[i-1].getX() > getFieldWidth()){
			return true;
		}
		return false;
	}
	
	/**
	 * Loss procedure is performed
	 */
	private void loss(){
		if(toggleAudio){
			try {
				Sound.loseSound();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JLabel text = WinLossText.lose();
		JLabel text2 = WinLossText.restart();
		add(text);
		add(text2);
		HighScore.writeScore(i - 2);
		snd.turnOff();
		setDelay(10000);
		
		text.repaint();
		text2.repaint();
		
		Restarter r = new Restarter(toggleAudio, toggleParticles, toggleTitle);
		this.addKeyListener(r);
		
	}
	
	/**
	 * Check if number of blocks placed reaches the max number of blocks
	 * @return if win occurs
	 */
	private boolean checkWin(){
		return i == numBlocks;
	}
	
	/**
	 * Win procedure is performed
	 */
	private void win(){
		JLabel text = WinLossText.win();
		JLabel text2 = WinLossText.restart();
		add(text);
		add(text2);
		HighScore.writeScore(i - 2);
		setDelay(10000);
	}
	
	/**
	 * Animates title JLabel 
	 * 
	 * @param delay
	 */
	private void animateTitle(int delay){
		if(ticks % delay == 0){
			if(!titleState){
				bigTitle.setBounds(70, -350, getFieldWidth() - 300, getFieldHeight() - 100);
				titleState = true;
			}
			else if(titleState){
				bigTitle.setBounds(70, -360, getFieldWidth() - 300, getFieldHeight() - 100);
				titleState = false;
			}
		}
	}
	
	/**
	 *  Increase block size by a random margain and set it to the 
	 *  center of the tower after resizing
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 */
	private void perfectPlaced() {
			try {
				Sound.perfectSound();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			blockWidth += DEFAULT_WIDTH / 2;	
			blocks[i-1].changeSize(blockWidth, blockHeight);
			blocks[i-1].setX(blocks[i-1].getX() - (blocks[i-1].getWidth() - blocks[i-2].getWidth()) / 2);
			
			if((i-1) % 2 == 0){
				perfect.setBounds(blocks[i-1].getX() - 100, blocks[i-1].getY() - 2 * blockHeight, 100, 50);
			}
			else{
				perfect.setBounds(blocks[i-1].getX() + blockWidth + 50, blocks[i-1].getY() - 2 * blockHeight, 100, 50);
			}
			perfect.setVisible(true);
			add(perfect);
			perfect.repaint();
	}
	
	/**
	 * Increases the block speed at certain intervals in the game
	 */
	private void incSpeed(){
		// Note: In the below if elses, I didnt use incrementors (such as speed++).
		// This is bc the speed would increase more than once each time due to the
		// act method repeating multiple times over the duration of each value of i.
		if(i == numBlocks / incSpeedFactor) 					speed = DEFAULT_SPEED + 1;
		else if(i == 2 * numBlocks / incSpeedFactor) 	speed = DEFAULT_SPEED + 2;
		else if(i == 3 * numBlocks / incSpeedFactor) 	speed = DEFAULT_SPEED + 3;
	}
	
	/**
	 * Initiates fields of next block and activates it
	 * @throws InterruptedException 
	 */
	private void nextBlock() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		blocks[i].setSize(blockWidth, blockHeight);
		blocks[i].setSpeed(speed);
		add(blocks[i]);
		blocks[i].activate(i);
		i++;
	}
	
	/**
	 * Calculates and makes the appropriate cut to the current block
	 */
	private void cutBlock(){
		cutLen = 0;
		try {
			Sound.blockSound();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(blocks[i-1].getX() > blocks[i-2].getX() && blocks[i-1].getX() < blocks[i-2].getX() + blockWidth){
			cutLen = blocks[i-1].getX() - blocks[i-2].getX();
			blocks[i-1].changeSize(blockWidth - cutLen, blockHeight);
			blockWidth -= cutLen;
		}
		if(blocks[i-1].getX() < blocks[i-2].getX() && blocks[i-1].getX() + blockWidth > blocks[i-2].getX()){
			cutLen = blocks[i-2].getX() - blocks[i-1].getX();
			blocks[i-1].changeSize(blockWidth - cutLen, blockHeight);
			blocks[i-1].setX(blocks[i-2].getX());
			blockWidth -= cutLen;
		}
		
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates and positions particles
	 */
	private void doParticles(){
		Particle particle = new Particle();
		particle.setSize(blockWidth,blockHeight);
		particle.setY((int)(blocks[i-1].getY()));
		if(i % 2 == 0){
			particle.setX(blocks[i-1].getX());
		}
		else{
			particle.setX(blocks[i-1].getX());
		}
		add(particle);
		if(particle.isVisible() == false){
			particle = null;
		}
	}
	
	/**
	 * Creates the custom font
	 */
	public void initiateFont(){
		try {
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File("PublicPixel-0W5Kv.ttf")).deriveFont(50f);
		    customFontLarge = Font.createFont(Font.TRUETYPE_FONT, new File("PublicPixel-0W5Kv.ttf")).deriveFont(120f);
		    customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File("PublicPixel-0W5Kv.ttf")).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(customFont);
		    ge.registerFont(customFontLarge);
		    ge.registerFont(customFontSmall);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}
	
	@Override
	public void setup() {
		
		height = getFieldHeight() - blockHeight;
		blocks = new Block[numBlocks];
		
		
		//Set up blocks in launch positions just outside of screen		
		for(int i = 0; i < blocks.length; i++){
			blocks[i] = new Block();
			blocks[i].setColor(colors[(int)(Math.random() * colors.length)]);
			
			if(i % 2 == 0){
				blocks[i].setX(getFieldWidth());
			}
			else{
				blocks[i].setX(0 - blockWidth);
			}
			
			blocks[i].setY(height);

			height -= blockHeight;
		}
		
		initiateFont();
		
		//JLabels ------------------------------------------------------------------
		points.setForeground(Color.WHITE);
		points.setBounds(getFieldWidth() - 150, 50, 100, 50);
		points.setFont(customFont);
		points.setText("0");
		add(points);
		
		
		if(toggleTitle){
			Color darkBlue = new Color(0, 66, 129);
			bigTitle.setForeground(darkBlue);
			bigTitle.setBounds(70, 10, getFieldWidth() - 300, getFieldHeight() - 100);
			bigTitle.setFont(customFontLarge);
			bigTitle.setText("stack");
			add(bigTitle);
		}
		
		
		perfect.setForeground(Color.WHITE);
		perfect.setFont(customFontSmall);
		perfect.setText("Perfect");
		// --------------------------------------------------------------------------
		
		snd.turnOn();
		
		if(!toggleAudio){
			snd.turnOff();
		}
		
		//set game "speed"
		setDelay(10);
	}
}
