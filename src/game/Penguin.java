package game;

import java.awt.Dimension;
import java.util.LinkedList;
import util.Location;
import util.Pixmap;
import util.Vector;



/**
 * Represents a shape that bounces around within the canvas.
 * 
 * @author Robert C. Duvall
 */
public class Penguin extends HoriCandy {    
	
    private static final Pixmap PENGUIN_IMAGE = new Pixmap("penguin.gif");

    /**
     * Create a ball at the given position, with the given velocity and size.
     */
    public Penguin (Pixmap image, Location center, Dimension size, Vector velocity) {
        super(image, center, size, velocity);
    }

	/**
     * Moves the shape according to its velocity and keeps it within the given bounds.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
    }
    
    public static Pixmap getImage(){
    	return PENGUIN_IMAGE;
    }
    
    //add specified number of penguins to list
    public static void createList(LinkedList<Candy> list, int number){
    	for (int i=0; i< number; i++){
    		list.add(new Penguin(PENGUIN_IMAGE, getRandomLocation(), DEFAULT_SIZE_M, HoriCandy.DEFAULT_VELOCITY_HORI));
    	}
    }
}
    
