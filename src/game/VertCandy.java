package game;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import util.Location;
import util.Pixmap;
import util.Vector;
import view.Canvas;

public class VertCandy extends Candy {
	

	private static final Pixmap CANDY_IMAGE = new Pixmap("candy.png");
	private static final Pixmap CANDY2_IMAGE = new Pixmap("candy2.png");
	private static final Pixmap CANDY3_IMAGE = new Pixmap("candy4.png");

   
	public VertCandy (Pixmap image, Location center, Dimension size, Vector velocity) {
        super(image, center, size, velocity);

    }

	/**
     * Moves the shape according to its velocity and keeps it within the given bounds.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
    }
    
    //get random vertical location amongst the clouds
    public static Location getRandomLocation(){
    	Random rgen = new Random();
    	int randomInt = rgen.nextInt((int) Canvas.SIZE.getWidth()-50) + 20;
    	Location x = new Location(randomInt, 200);
    	return x;
    }
    
    //get random downward-falling candy image
    public static Pixmap randomImage(){
    	ArrayList<Pixmap> imageList = new ArrayList<Pixmap>();
        imageList.add(CANDY_IMAGE);
        imageList.add(CANDY2_IMAGE);
        imageList.add(CANDY3_IMAGE);
    	int num = imageList.size();
    	Random rgen = new Random();
    	int randNum = rgen.nextInt(num);
    	return imageList.get(randNum);
    }
    
    //add specified number of candy to list
    public static void createList(LinkedList<Candy> list, int number){
    	for (int i=0; i< number; i++){
    		list.add(new VertCandy(randomImage(), getRandomLocation(), DEFAULT_SIZE_S, randomVelocity()));
    	}
    }
    
  
}
