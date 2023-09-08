import java.awt.*;    
import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main menu GUI and functionality.
 * <p>
 * Provides options to choose from when game starts
 * </p>
 * <p>
 * May initiate other menus as well as the Stack game
 * </p>
 * <p>
 * Ran before all the other classes
 * </p>
 * @author Kevin Lan
 *
 */
public class MainMenu implements ActionListener {
	
	//graphical
	public JFrame f;
	private String[] options = {"Play", "Options", "High Scores", "How to play"};
	private int optionSelected;
	private String filename = "Settings";
	
	//options
	private static boolean toggleAudio;
	private static boolean toggleParticles;
	private static boolean toggleTitle;
	private boolean selected = false;
	
	public MainMenu(boolean a, boolean b, boolean c){
		f = new JFrame("Stack");
		Container cont = f.getContentPane(); //Gets the content layer
		toggleAudio = a;
		toggleParticles = b;
		toggleTitle = c;
        JLabel label = new JLabel(); //JLabel Creation
        label.setIcon(new ImageIcon("Stack_Logo.PNG")); //Sets the image to be displayed as an icon
        label.setBounds(125, 30, 125, 120); //Sets the location of the image
 
        cont.add(label); //Adds objects to the container
		
		JButton b1 = new JButton(options[0]);
		JButton b2 = new JButton(options[1]);
		JButton b3 = new JButton(options[2]);
		JButton b4 = new JButton(options[3]);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionSelected =0;
				selected = true;
				System.out.println("b0 pressed");
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionSelected = 1;
				selected = true;
				System.out.println("b1 pressed");
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionSelected = 2;
				selected = true;
				System.out.println("b2 pressed");
			}
		});
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionSelected = 3;
				selected = true;
				System.out.println("b3 pressed");
			}
		});
		
		
		b1.setBounds(140, 180, 100, 30);
		b2.setBounds(140, 220, 100, 30);
		b3.setBounds(120, 260, 140, 30);
		b4.setBounds(120, 300, 140, 30);
		
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		
		f.setSize(400,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
		
		
		makeVisible();
		
		select();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public void makeVisible(){
		f.setVisible(true);
	}
	
	public void makeInvisible(){
		f.setVisible(false);
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public int getOptionSelected(){
		return optionSelected;
	}
	
	/**
	 * Process option selection
	 */
	public void select(){
		while(!selected){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("selection made");
		switch(optionSelected){
			case 0:
				readFile();
				Stack stack = new Stack(toggleAudio, toggleParticles, toggleTitle);
				//Stack stack = new Stack();
				stack.setVisible(true);
				stack.initComponents();
				System.out.println("stack");
				this.makeInvisible();
				break;
			case 1:
				OptionsMenu options = new OptionsMenu();
				options.makeVisible();
				while(options.isSelected() == false){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				readFile();
				
				options = null;
				new MainMenu(toggleAudio, toggleParticles, toggleTitle);
				this.makeInvisible();
				break;
			case 2:
				HighScoreMenu highScores = new HighScoreMenu();
				highScores.makeVisible();
				while(highScores.isSelected() == false){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				highScores = null;
				new MainMenu(toggleAudio, toggleParticles, toggleTitle);
				this.makeInvisible();
				break;
			case 3:
				InstructionPanel instruc = new InstructionPanel();
				instruc.makeVisible();
				while(instruc.isSelected() == false){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				instruc = null;
				new MainMenu(toggleAudio, toggleParticles, toggleTitle);
				break;
		}
	}
	
	/**
	 * Reads file
	 */
	private void readFile(){
		File textFile = new File(filename +".txt"); 
		//check if file exists
		if (textFile.exists()){
			System.out.println("File \""+ textFile.getName() +"\" exists."); 
		} 
		else{
			System.out.println("Could not find the file.");
			System.exit(0);
		}
		
		Scanner input = null;
    	try{
    		input = new Scanner(textFile);
    	}
    	catch (FileNotFoundException e){
    	}
    	
    	int i = 1;
    	int temp;
    	while(i <= 3){		
    		temp = input.nextInt();
    		switch(i){
	    		case 1:
	    			if(temp == 1) toggleTitle = true;
	    			else if(temp == 0) toggleTitle = false;
	    			break;
	    		case 2:
	    			if(temp == 1) toggleAudio = true;
	    			else if(temp == 0) toggleAudio = false;
	    			break;
	    		case 3:
	    			if(temp == 1) toggleParticles = true;
	    			else if(temp == 0) toggleParticles = false;
	    			break;
    		}
    		i++;
    	}
	}
	
	public static void main(String[] args){
		MainMenu menu = new MainMenu(true, true, true);
	}
}
