import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Game instructions GUI
 * @author Kevin Lan
 *
 */
public class InstructionPanel {
	
	public static JFrame f;
	private boolean selected;
	
	public InstructionPanel(){
		f = new JFrame("Stack Instructions");
		
		JLabel text = new JLabel();
		text.setText("<html>"
				+ "<style>"
				+ "p {text-align: center;}"
				+ "</style>"
				+ "<p>Press space to place block</p>"
				+ "<br><p>Stack your tower to the top.</p>"
				+ "<br><p>Try to keep your tower as wide as possible.</p>"
				+ "</html>");
		text.setBounds(70, 0, 300, 300);
		f.add(text);
		
		JButton b1 = new JButton("Back");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = true;
				makeInvisible();
			}
		});
		
		b1.setBounds(140, 270, 100, 30);
		f.add(b1);
		
		f.setSize(400,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
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
}
