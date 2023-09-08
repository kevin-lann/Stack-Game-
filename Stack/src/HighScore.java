import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * High score controller. Performs the calculations of the user's high score and average score
 * @author Kevin Lan
 *
 */
public class HighScore {

	private static String filename = "Level1";
	
	/**
	 * Returns the highest integer within the file
	 * @return highest score
	 */
	public static int getHighScore(){
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
    	
    	int highscore = 0;
    	int score;
    	
    	//read lines 
    	//check which line has the highest score
    	while(input.hasNextLine()){		
    		score = input.nextInt();
    		if(score > highscore){
    			highscore = score;
    		}
    	}
    	
    	return highscore;
	}
	
	/**
	 * Calculates and returns the average score
	 * @return returns avg score
	 */
	public static double getAverageScore(){
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
    	
    	int total = 0;
    	int score = 0;
    	int n = 0;
    	
    	//read lines 
    	//check which line has the highest score
    	while(input.hasNextLine()){		
    		score = input.nextInt();
    		total += score;
    		n++;
    	}
    	
    	return total /(double) n; 
	}
	
	/*
	 * Writes a given integer on a new line in the file
	 */
	public static void writeScore(int score){
		try{
			FileWriter writer = new FileWriter(filename + ".txt", true);
			writer.write("\r\n"); 
			writer.write(score + "");
			writer.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
