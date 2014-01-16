package game;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import util.Location;
import util.Pixmap;
import util.Vector;
import view.Canvas;

public class HoriCandy extends Candy {
	
	//lits of horizontal candy pictures
	private static final Pixmap SHAKE_IMAGE = new Pixmap("milkshake.png");
	private static final Pixmap ICE_IMAGE = new Pixmap("icecream3.gif");
	private static final Pixmap ICE2_IMAGE = new Pixmap("icecream.gif");
	
    public HoriCandy (Pixmap image, Location center, Dimension size, Vector velocity) {
        super(image, center, size, velocity);
    }

	/**
     * Moves the shape according to its velocity and keeps it within the given bounds.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
    }
    
    //get random horizontal location
    public static Location getRandomLocation(){
    	Random rgen = new Random();
    	int randomY = rgen.nextInt(3);
    	if (randomY == 0){
    		return getHoriLocation();
    	}
    	if (randomY == 1){
    		return getHoriLocation2(); 
    	}
    	else{
    		return getHoriLocation3(); 
    	}
    }
    
    //get random image candy
    public static Pixmap randomImage(){
    	ArrayList<Pixmap> imageList = new ArrayList<Pixmap>();
    	imageList.add(SHAKE_IMAGE);
        imageList.add(ICE_IMAGE);
        imageList.add(ICE2_IMAGE);
    	int num = imageList.size();
    	Random rgen = new Random();
    	int randNum = rgen.nextInt(num);
    	return imageList.get(randNum);
    }
    
    //Top row position
    public static Location getHoriLocation(){
    	Random rgen = new Random();
    	int randomX = rgen.nextInt((int)Canvas.SIZE.getWidth()/5);
    	Location x = new Location(randomX, L3_HEIGHT);
    	return x;
    }
    
    //Middle row position
    public static Location getHoriLocation2(){
    	Random rgen = new Random();
    	int randomX = rgen.nextInt((int)Canvas.SIZE.getWidth()/3);
    	Location x = new Location(randomX, L2_HEIGHT);
    	return x;
    }
    
    //Bottom row position
    public static Location getHoriLocation3(){
    	Random rgen = new Random();
    	int randomX = rgen.nextInt((int)Canvas.SIZE.getWidth()/2);
    	Location x = new Location(randomX,L3_HEIGHT);
    	return x;
    }
    //Add specified number of candy to list
    public static void createList(LinkedList<Candy> list, int number){
    	for (int i=0; i< number; i++){
    		list.add(new HoriCandy(randomImage(), getRandomLocation(), DEFAULT_SIZE_M, HoriCandy.DEFAULT_VELOCITY_HORI));
    	}
    }
}
