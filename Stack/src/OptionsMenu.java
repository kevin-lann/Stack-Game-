import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;


/**
 * Game option controller
 * <p>
 * Allows user to toggle on and off certain game settings
 * </p>
 * <p>
 * Saves user's preferences for any afterward launches
 * </p>
 * @author Kevin Lan
 *
 */
public class OptionsMenu {
	
	public static JFrame f;
	
	private boolean toggleAudio = true;
	private boolean toggleParticles = true;
	private boolean toggleTitle = true;
	private boolean selected;
	
	private String filename = "Settings";
	
	
	public OptionsMenu(){
		f = new JFrame("Stack Options");
		readFile();
		JButton b1 = new JButton("Back");
		JCheckBox c1 = new JCheckBox("Audio", toggleAudio);
		JCheckBox c2 = new JCheckBox("Title & W/L text", toggleParticles);
		JCheckBox c3 = new JCheckBox("Particles", toggleTitle);
		
		c1.setBounds(150, 120, 100, 30);
		c2.setBounds(150, 150, 100, 30);
		c3.setBounds(150, 180, 100, 30);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeFile();
				selected = true;
				f.setVisible(false);
			}
		});
		
		c1.addItemListener(new ItemListener() {    
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(toggleAudio) toggleAudio = false;
				else toggleAudio = true;
			}    
         });
		c2.addItemListener(new ItemListener() {    
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(toggleParticles) toggleParticles = false;
				else toggleParticles = true;
			}    
         }); 
		c3.addItemListener(new ItemListener() {    
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(toggleTitle) toggleTitle = false;
				else toggleTitle = true;
			}    
         });
		
		b1.setBounds(140, 270, 100, 30);
		c1.setBounds(140, 160, 100, 10);
		c2.setBounds(140, 180, 100, 10);
		c3.setBounds(140, 200, 100, 10);
		
		f.add(b1);
		f.add(c1);
		f.add(c2);
		f.add(c3);
		
		f.setSize(400,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
	}
	
	/**
	 * Write options preferences to setting file
	 */
	private void writeFile(){
		try{
			FileWriter writer = new FileWriter(filename + ".txt", false);
			
			if(toggleTitle) writer.write("1");
			else writer.write("0");
			
			writer.write("\r\n"); 
			if(toggleAudio) writer.write("1");
			else writer.write("0");
			
			writer.write("\r\n"); 
			if(toggleParticles) writer.write("1");
			else writer.write("0");
			
			writer.close();
		}
		catch (IOException e){
			e.printStackTrace();
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
	
	public void makeVisible(){
		f.setVisible(true);
	}
	
	public void makeInvisible(){
		f.setVisible(false);
	}
	
	public boolean getAudioState(){
		return toggleAudio;
	}
	
	public boolean getParticleState(){
		return toggleParticles;
	}
	
	public boolean getTitleState(){
		return toggleTitle;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
}
