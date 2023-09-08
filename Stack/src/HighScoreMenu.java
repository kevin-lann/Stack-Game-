import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * High score menu GUI
 * @author Kevin Lan
 *
 */
public class HighScoreMenu {
	
	public static JFrame fr;
	private boolean selected;
	
	public HighScoreMenu(){
		fr = new JFrame("High score");
		
		JButton b1 = new JButton("Back");
		
		JLabel text1 = new JLabel();
		JLabel text2 = new JLabel();
		DecimalFormat d = new DecimalFormat("##.#");
		
		text1.setText("Your high score: " + HighScore.getHighScore());
		text1.setBounds(140, 120, 200, 50);
		text2.setText("Your average score: " + d.format(HighScore.getAverageScore()));
		text2.setBounds(140, 140, 200, 50);
		fr.add(text1);
		fr.add(text2);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = true;
				makeInvisible();
			}
		});
		
		b1.setBounds(140, 270, 100, 30);
		fr.add(b1);
		
		fr.setSize(400,400);
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setLayout(null);
	}
	
	public void makeVisible(){
		fr.setVisible(true);
	}
	
	public void makeInvisible(){
		fr.setVisible(false);
	}
	
	public boolean isSelected(){
		return selected;
	}
}
