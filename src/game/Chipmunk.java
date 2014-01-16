package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;
import view.Canvas;


/**
 *  Represents a shape that the player controls.
 *  
 * @author Robert C. Duvall
 */
public class Chipmunk extends Sprite
{
    // reasonable default values, size of chipmunk obtained relative to size of canvas
    public static final Dimension DEFAULT_SIZE = new Dimension((int)Canvas.SIZE.getWidth()/12,(int)((Canvas.SIZE.getWidth()/12)*0.68));
    public static final int DEFAULT_OFFSET = 50;
    public static final Vector DEFAULT_VELOCITY = new Vector(0,0);
    
    // movement
    private static final int MOVE_LEFT = KeyEvent.VK_LEFT;
    private static final int MOVE_RIGHT = KeyEvent.VK_RIGHT;
    private static final int JUMP_L1 = KeyEvent.VK_UP;
    private static final int JUMP_L2 = KeyEvent.VK_SPACE;
    private static final int JUMP_L3 = KeyEvent.VK_Q;
    private static final int MOVE_SPEED = 10;

    private static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);
    private static final Vector RIGHT_VELOCITY = new Vector(RIGHT_DIRECTION, MOVE_SPEED);
    
    // get input directly
    private Canvas myCanvas;
    private boolean canJump = false;
    private boolean isJumpUp = false;
    private boolean isJumpDown = false;
    private double jumpHeight;
    private int jumpSpeed;


    /**
     * Create a chipmunk at the given position, with the given size.
     */
    public Chipmunk (Pixmap image, Location center, Dimension size, Canvas canvas, Vector velocity)
    {
        super(image, center, size, velocity);
        myCanvas = canvas;
    }

    /**
     * Describes how to "animate" the shape by changing its state.
     *
     * Moves the chipmunk based on the keys pressed.
     */
    public void update (double elapsedTime, Dimension bounds)
    {
    	super.update(elapsedTime, bounds);
        int key = myCanvas.getLastKeyPressed();
        
        //sets downward velocity for jumping down if hit a ceiling
        if (getTop() <= jumpHeight && isJumpUp){
        	setVelocity(DOWN_DIRECTION, jumpSpeed);
        	isJumpUp = false;
        	isJumpDown = true;
        }
        
        //if chipmunk returns to ground, set velocity back to 0 and return jumping abilities to chipmunk
        if (getBottom() >= bounds.getHeight()-DEFAULT_OFFSET && isJumpDown){
        	canJump = true;
        	setVelocity(0,0);
        	isJumpDown = false;
        }
        
        if (key == MOVE_LEFT && getLeft() > 0)
        {
            translate(LEFT_VELOCITY);
            
        }
        else if (key == MOVE_RIGHT && getRight() < bounds.width)
        {
            translate(RIGHT_VELOCITY);
        }

        else if(canJump && key == JUMP_L1)
        {
        	setJump(Candy.L1_HEIGHT, 1);

        }
        else if(canJump && key == JUMP_L2)
        {
        	setJump(Candy.L2_HEIGHT+DEFAULT_SIZE.height/2, 3);

        }
        else if(canJump && key == JUMP_L3)
        {
        	setJump(Candy.L3_HEIGHT, 5);

        }
    }
    
    //set height to jump up to and how fast, set canJump to false to prevent double jumping
    public void setJump(double height, double accelFactor){
    	jumpHeight = height;
    	jumpSpeed = (int) ((int) accelFactor*jumpHeight);
       	setVelocity(UP_DIRECTION,jumpSpeed);
       	isJumpUp = true;
       	canJump = false;
    }
    
    //allows Model to turn on jumping abilities at Level 2
    public void canJump(boolean x){
    	canJump = x;
    }
}
