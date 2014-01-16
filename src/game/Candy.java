package game;

import java.awt.Dimension;
import java.util.Random;

import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;
import view.Canvas;


/**
 * Represents a shape that bounces around within the canvas.
 * 
 * @author Robert C. Duvall
 */
public class Candy extends Sprite {    
    // reasonable default values
    public static final Dimension DEFAULT_SIZE_S = new Dimension(40, 40);
    public static final Dimension DEFAULT_SIZE_M = new Dimension(45, 50);
    public static final Dimension DEFAULT_SIZE_L = new Dimension(70, 70);
    // speed is in pixels per second
    public static final Vector DEFAULT_VELOCITY_SLOW = new Vector(-270, 80);
    public static final Vector DEFAULT_VELOCITY_FAST = new Vector(-270, 120);
    public static final Vector DEFAULT_VELOCITY_HORI = new Vector(0, 100);
    
    public static final int L1_HEIGHT = (int) (Canvas.SIZE.getWidth()/4 + Canvas.SIZE.getWidth()/4);
    public static final int L2_HEIGHT = (int) (Canvas.SIZE.getWidth()/4 + Canvas.SIZE.getWidth()/8);
    public static final int L3_HEIGHT = (int) (Canvas.SIZE.getWidth()/4);

    /**
     * Create a ball at the given position, with the given velocity and size.
     */
    public Candy (Pixmap image, Location center, Dimension size, Vector velocity) {
        super(image, center, size, velocity);
    }

	/**
     * Moves the shape according to its velocity and keeps it within the given bounds.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
    }
    
    //randomize velocity of falling objects
    public static Vector randomVelocity(){
    	Random rgen = new Random();
    	int randomInt = rgen.nextInt(50) + 100;
    	Vector v = new Vector(90, randomInt);
    	return v;
    }
    
    //randomize location of falling objects
    public static Location getRandomLocation(){
    	Location x = new Location(0,0);
    	return x;
    }
}
