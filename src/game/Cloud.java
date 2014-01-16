package game;

import java.awt.Dimension;
import java.util.ArrayList;

import util.Location;
import util.Pixmap;
import util.Sprite;


/**
 * Represents a shape that the player tries to break.
 * 
 * @author Robert C. Duvall
 */
public class Cloud extends Sprite {
    // reasonable default values
    public static final int DEFAULT_HEIGHT = 100;
    public static final int DEFAULT_OFFSET = 125;
    private static final Pixmap CLOUD_IMAGE = new Pixmap("cloud.png");
    // state

    /**
     * Construct a shape at the given position, with the given size, and color.
     */
    public Cloud (Pixmap image, Location center, Dimension size) {
        super(image, center, size);
    }
    
    //create clouds
    public static void createList(ArrayList<Cloud> myClouds, Dimension bounds, int NUM_CLOUDS){
		int width = bounds.width / NUM_CLOUDS;
		for (int j = 0; j < NUM_CLOUDS; j++) {
			Cloud b = new Cloud(CLOUD_IMAGE,
					new Location(width / 2 + j * width, Cloud.DEFAULT_OFFSET),
					new Dimension(width, Cloud.DEFAULT_HEIGHT));
			myClouds.add(b);
		}
    }
}
