package view;

import game.Model;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.Timer;

import util.Location;
import util.Pixmap;


/**
 * Creates an area of the screen in which the game will be drawn that supports:
 * <UL>
 *   <LI>animation via the Timer
 *   <LI>mouse input via the MouseListener
 *   <LI>keyboard input via the KeyListener
 * </UL>
 * 
 * @author Xu Rui
 */
public class Canvas extends JComponent {
    public static final Dimension SIZE = new Dimension(1200, 800);
    // default serialization ID
    private static final long serialVersionUID = 1L;
    // animate 25 times per second if possible
    public static final int FRAMES_PER_SECOND = 25;
    // better way to think about timed events (in milliseconds)
    public static final int ONE_SECOND = 1000;
    public static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;
    // input state
    public static final int NO_KEY_PRESSED = -1;

    // drives the animation
    private Timer myTimer;
    // game to be animated
    private Model myGame;
    // input state
    private int myLastKeyPressed;
    private Point myLastMousePosition;
    
    private boolean gameStart;
    private static final Pixmap BACKGROUND_IMAGE = new Pixmap("splash.png");

    /**
     * Create a panel so that it knows its size
     */
    public Canvas (Dimension size) {
        // set size (a bit of a pain)
        setPreferredSize(size);
        setSize(size);
        // prepare to receive input
        setFocusable(true);
        requestFocus();
        setInputListeners();
    }

    /**
     * Paint the contents of the canvas.
     * 
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be
     * displayed (i.e., creation, uncovering, change in status)
     * 
     * @param pen used to paint shape on the screen
     */
    @Override
    public void paintComponent (Graphics pen) {
    	
        // SPLASH SCREEN
        if (!gameStart){
        	BACKGROUND_IMAGE.paint((Graphics2D) pen, new Location(Canvas.SIZE.getWidth()/2,Canvas.SIZE.getHeight()/2), Canvas.SIZE,0);
        }
        //if player presses Enter, exist splash screen and start game
		if (myLastKeyPressed == KeyEvent.VK_ENTER){
			gameStart = true;

		}
        if (myGame != null && gameStart == true) {
            myGame.paint((Graphics2D) pen);
            
        }
    }

    /**
     * Returns last key pressed by the user.
     */

    public int getLastKeyPressed () {
        return myLastKeyPressed;
    }

    /**
     * Returns last position of the mouse in the canvas.
     */
    public Point getLastMousePosition () {
        return myLastMousePosition;
    }

    /**
     * Start the animation.
     */
    public void start () {
        final int stepTime = DEFAULT_DELAY;
        // create a timer to animate the canvas
        Timer timer = new Timer(stepTime, 
            new ActionListener() {
                public void actionPerformed (ActionEvent e) {
                    myGame.update((double) stepTime / ONE_SECOND);
                    repaint();
                }
            });
        // start animation
        myGame = new Model(this);
        timer.start();
    }

    /**
     * Stop the animation.
     */
    public void stop () {
        myTimer.stop();
    }

    /**
     * Create listeners that will update state based on user input.
     */
    private void setInputListeners () {
        // initialize input state
        myLastKeyPressed = NO_KEY_PRESSED;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                myLastKeyPressed = e.getKeyCode();
            }
            @Override
            public void keyReleased (KeyEvent e) {
                myLastKeyPressed = NO_KEY_PRESSED;
            }
        });
        myLastMousePosition = new Point();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                myLastMousePosition = e.getPoint();
            }
        });
    }
}
