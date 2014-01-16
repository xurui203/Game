package game;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Random;

import util.Location;
import util.Pixmap;
import util.Vector;
import view.Canvas;


/**
 * Represents a shape that bounces around within the canvas.
 * 
 * @author Robert C. Duvall
 */
public class Poop extends Candy {    
	
	// two different types of falling objects to avoid
	private static final Pixmap POOP_IMAGE = new Pixmap("poop.gif");
	private static final Pixmap SNOW_IMAGE = new Pixmap("snow.gif");

    /**
     * Create a ball at the given position, with the given velocity and size.
     */
    public Poop (Pixmap image, Location center, Dimension size, Vector velocity) {
        super(image, center, size, velocity);
    }

	/**
     * Moves the shape according to its velocity and keeps it within the given bounds.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
    }
    
    public static Pixmap getSnowImage(){
    	return SNOW_IMAGE;
    }
    
    public static Pixmap getPoopImage(){
    	return POOP_IMAGE;
    }
    
    //get random location from which to fall
    public static Location getRandomLocation(){
    	Random rgen = new Random();
    	int randomInt = rgen.nextInt( (int) Canvas.SIZE.getWidth()-50) + 20;
    	Location x = new Location(randomInt, 200);
    	return x;
    }
    
    //add specified number of poop to list
    public static void createList(LinkedList<Candy> list, int number, Pixmap image){
    	for (int i=0; i< number; i++){
    		list.add(new Poop(image, getRandomLocation(), DEFAULT_SIZE_M, randomVelocity()));
    	}
    }
}

    

