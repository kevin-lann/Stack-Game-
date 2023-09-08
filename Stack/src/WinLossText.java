import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

/**
 * Win / Loss text GUI
 * @author Kevin Lan
 *
 */
public class WinLossText {
	
	private static JLabel win;	
	private static JLabel lose;
	private static JLabel restart;
	private static Font customFont, customFontSmall;

	/**
	 * Initiate GUI 
	 */
	public static void initiate(){
		try {
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File("PublicPixel-0W5Kv.ttf")).deriveFont(40f);
		    customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File("PublicPixel-0W5Kv.ttf")).deriveFont(20f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(customFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		win = new JLabel("You won!");
		win.setForeground(new Color(220, 220, 220));
		win.setBounds(320, 500, 400, 60);
		win.setFont(customFont);
		
		lose = new JLabel("You Lost");
		lose.setForeground(new Color(220, 220, 220));
		lose.setBounds(320, 500, 400, 60);
		lose.setFont(customFont);
		
		restart = new JLabel("Space to restart");
		restart.setForeground(new Color(220, 220, 220));
		restart.setBounds(320, 550, 400, 60);
		restart.setFont(customFontSmall);
	}
	
	public static JLabel win(){
		initiate();
		return win;
	}
	
	public static JLabel lose(){
		initiate();
		return lose;
	}
	
	public static JLabel restart(){
		initiate();
		return restart;
	}
}
