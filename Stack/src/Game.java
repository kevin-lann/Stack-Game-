/*
 * This code is protected under the Gnu General Public License (Copyleft), 2005 by
 * IBM and the Computer Science Teachers of America organization. It may be freely
 * modified and redistributed under educational fair use.
 */


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

/**
 * An abstract Game class which can be built into Pong.<br>
 * <br>
 * The default controls are for "Player 1" to move left and right with the 
 * 'Z' and 'X' keys, and "Playr 2" to move left and right with the 'N' and
 * 'M' keys.<br>
 * <br>
 * Before the Game begins, the <code>setup</code> method is executed. This will
 * allow the programmer to add any objects to the game and set them up. When the
 * game begins, the <code>act</code> method is executed every millisecond. This
 * will allow the programmer to check for user input and respond to it.
 * 
 *  @see GameObject 
 */
public abstract class Game extends JFrame {
	private boolean _isSetup = false;
	private boolean _initialized = false;
	private ArrayList _ObjectList = new ArrayList();
	private Timer _t;
	
	/**
	 * <code>true</code> if the 'Z' key is being held down
	 */
	private boolean p1Left = false;
	
	/**
	 * <code>true</code> if the 'X' key is being held down.
	 */
	private boolean p1Right = false;
	
	/**
	 * <code>true</code> if the 'N' key is being held down.
	 */
	private boolean p2Left = false;
	
	/**
	 * <code>true</code> if the 'M' key is being held down.
	 */
	private boolean p2Right = false;
	
	/**
	 * Returns <code>true</code> if the 'Z' key is being pressed down
	 * 
	 * @return <code>true</code> if the 'Z' key is being pressed down
	 */
	public boolean ZKeyPressed() {
		return p1Left;
	}
	
	/**
	 * Returns <code>true</code> if the 'X' key is being pressed down
	 * 
	 * @return <code>true</code> if the 'X' key is being pressed down
	 */
	public boolean XKeyPressed() {
		return p1Right;
	}
	
	/**
	 * Returns <code>true</code> if the 'N' key is being pressed down
	 * 
	 * @return <code>true</code> if the 'N' key is being pressed down
	 */
	public boolean NKeyPressed() {
		return p2Left;
	}
	
	/**
	 * Returns <code>true</code> if the 'M' key is being pressed down
	 * 
	 * @return <code>true</code> if the 'M' key is being pressed down
	 */
	public boolean MKeyPressed() {
		return p2Right;
	}
	
	/**
	 * When implemented, this will allow the programmer to initialize the game
	 * before it begins running
	 * 
	 * Adding objects to the game and setting their initial positions should be
	 * done here.
	 * 
	 * @see GameObject
	 */
	public abstract void setup();
	
	/**
	 * When the game begins, this method will automatically be executed every
	 * millisecond
	 * 
	 * This may be used as a control method for checking user input and 
	 * collision between any game objects
	 */
	public abstract void act();
	
	/**
	 * Sets up the game and any objects.
	 *
	 * This method should never be called by anything other than a <code>main</code>
	 * method after the frame becomes visible.
	 */
	public void initComponents() {
		getContentPane().setBackground(new Color(0, 13, 36));
		setup();
		for (int i = 0; i < _ObjectList.size(); i++) {
				GameObject o = (GameObject)_ObjectList.get(i);
				o.repaint();
		}
		_t.start();
	}
	
	/**
	 * Adds a game object to the screen
	 * 
	 * Any added objects will have their <code>act</code> method called every
	 * millisecond
	 * 
	 * @param o		the <code>GameObject</code> to add.
	 * @see	GameObject#act()
	 */
	public void add(GameObject o) {
		_ObjectList.add(o);
		getContentPane().add(o);
	}
	
	/**
	 * Removes a game object from the screen
	 * 
	 * @param o		the <code>GameObject</code> to remove
	 * @see	GameObject
	 */
	public void remove(GameObject o) {
		_ObjectList.remove(o);
		getContentPane().remove(o);
	}
	
	/**
	 * Sets the millisecond delay between calls to <code>act</code> methods.
	 * 
	 * Increasing the delay will make the game run "slower." The default delay
	 * is 1 millisecond.
	 * 
	 * @param delay	the number of milliseconds between calls to <code>act</code>
	 * @see Game#act()
	 * @see GameObject#act()
	 */
	public void setDelay(int delay) {
		_t.setDelay(delay);
	}
	
	/**
	 * Sets the background color of the playing field
	 * 
	 * The default color is black
	 * 
	 * @see java.awt.Color
	 */
	public void setBackground(Color c) {
		getContentPane().setBackground(c);
	}
	
	/**
	 * The default constructor for the game.
	 * 
	 * The default window size is 400x400
	 */
	public Game() {
		setSize(960, 1040);
		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuFileExit = new JMenuItem("Exit");
        menuBar.add(menuFile);
        menuFile.add(menuFileExit);
        setJMenuBar(menuBar);
        setTitle("Stack");
               
        // Add window listener.
        addWindowListener (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
       menuFileExit.addActionListener( 
       		new ActionListener() {
       			public void actionPerformed(ActionEvent e) {
       				System.exit(0);
       			}
       		}
       	);
       _t = new Timer(1, new ActionListener() {
       		public void actionPerformed(ActionEvent e) {
   				act();
   				for (int i = 0; i < _ObjectList.size(); i++) {
   					GameObject o = (GameObject)_ObjectList.get(i);
   					o.act();
   				}
       		}
       });
       addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
	
			public void keyPressed(KeyEvent e) {
				char pressed = Character.toUpperCase(e.getKeyChar());
				switch (pressed) {
					case ' ' : p1Left = true; break;
					case 'X' : p1Right = true; break;
					case 'N' : p2Left = true; break;
					case 'M' : p2Right = true; break;
				}
			}
	
			public void keyReleased(KeyEvent e) {
				char released = Character.toUpperCase(e.getKeyChar());
				switch (released) {
					case ' ' : p1Left = false; break;
					case 'X' : p1Right = false; break;
					case 'N' : p2Left = false; break;
					case 'M' : p2Right = false; break;
				}
			}
       }); 
    }
	
	/**
	 * Starts updates to the game
	 *
	 * The game should automatically start.
	 * 
	 * @see Game#stopGame()
	 */
	public void startGame() {
		_t.start();
	}
	
	/**
	 * Stops updates to the game
	 *
	 * This can act like a "pause" method
	 * 
	 * @see Game#startGame()
	 */
	public void stopGame() {
		_t.stop();
	}
	
	/**
	 * Displays a dialog that says "Player 1 Wins!"
	 *
	 */
	public void p1Wins() {
		_WinDialog d = new _WinDialog(this, "Player 1 Wins!");
		d.setVisible(true);
	}
	
	/**
	 * Displays a dialog that says "Player 2 Wins!"
	 *
	 */
	public void p2Wins() {
		_WinDialog d = new _WinDialog(this, "Player 2 Wins!");
		d.setVisible(true);	
	}
	
	/**
	 * Gets the pixel width of the visible playing field
	 * 
	 * @return	a width in pixels
	 */
	public int getFieldWidth() {
		return getContentPane().getBounds().width;
	}
	
	/**
	 * Gets the pixel height of the visible playing field
	 * 
	 * @return a height in pixels
	 */
	public int getFieldHeight() {
		return getContentPane().getBounds().height;
	}
	
	class _WinDialog extends JDialog {
		JButton ok = new JButton("OK");
		_WinDialog(JFrame owner, String title) {
			super(owner, title);
			Rectangle r = owner.getBounds();
			setSize(200, 100);
			setLocation(r.x + r.width / 2 - 100, r.y + r.height / 2 - 50);
			getContentPane().add(ok);
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					_WinDialog.this.setVisible(false);
				}
			});
		}		
	}
}