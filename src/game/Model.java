package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import util.Location;
import util.Pixmap;
import util.Sound;
import util.Text;
import util.ValueText;
import view.Canvas;


/**
 * Represents a simple game of breakout.
 * 
 * It enforces the rules of the game each moment in the game as well as award points and
 * change levels.
 * 
 * @author Robert C. Duvall
 */
public class Model {
	// images used in the model
	private static final Pixmap BACKGROUND_IMAGE = new Pixmap("background.png");
	private static final Pixmap BACKGROUND2_IMAGE = new Pixmap("background2.jpg");
	private static final Pixmap WIN_IMAGE = new Pixmap("winBackground.png");
	private static final Pixmap LOSE_IMAGE = new Pixmap("loseBackground.png");
	private static final Pixmap L2_IMAGE = new Pixmap("level2background.gif");
	private static final Pixmap CHIPMUNK_IMAGE = new Pixmap("chipmunk1.gif");

	//sound used in model
	private static final Sound song = new Sound("raindrops song.aiff");

	// game play values
	private static final int STARTING_LIVES = 15;
	private static final int SUGAR_METER = 0;
	private static final int NUM_CLOUDS = 6;
	private static final int NUM_CANDY = 4;
	private static final int NUM_ICE = 2;
	private static final int NUM_SNOW = 4;
	private static final int NUM_POOP = 3;
	private static final int ADD_SUGAR = 5;
	private static final int ADD_SUGAR_L2 = 2;

	// heads up display values
	private static final int LABEL_X_OFFSET = 80;
	private static final int LABEL_Y_OFFSET = 25;
	private static final Color LABEL_COLOR = Color.DARK_GRAY;
	private static final String SCORE_LABEL = "Sugar Meter: " ;
	private static final String LIVES_LABEL = "Health";
	private static final String LEVELS_LABEL = "Level";

	private static final Text L2InstLine1 = new Text("Congratulations and welcome to Level 2!");
	private static final Text L2InstLine2 = new Text("Move sideways with the left and right arrow keys to catch candy. Avoid snowflakes and penguins.");
	private static final Text L3InstLine3 = new Text("Press up arrow for low jump, space bar for mid jump and Q for high jump.");
	private static final Text L2InstLine4 = new Text("Press Enter to proceed.");

	// bounds and input for game
	private Canvas myView;
	private double bottom;
	private static final int PROCEED = KeyEvent.VK_ENTER;

	//CHEAT//Press to increase sugar meter
	private static final int SUGAR_RUSH = KeyEvent.VK_2;
	//CHEAT//Press to increase number of lives
	private static final int HEALTH_BOOST = KeyEvent.VK_3;

	// game resources
	private ArrayList<Cloud> myClouds;
	private LinkedList<Candy> candyList;
	private LinkedList<Candy> pList;
	private LinkedList<Candy> snowList;
	private Chipmunk myChipmunk;

	//game state
	private ValueText myScore;
	private ValueText myLives;
	private ValueText myLevel;
	private ArrayList<Text> myInstructions;
	private boolean gameEnd; 
	private boolean gameWon;
	private boolean gamePlay; //pauses game if false
	private int level = 1;

	/**
	 * Create a game of the given size with the given display for its shapes.
	 */

	public void update (double elapsedTime) {
		int key = myView.getLastKeyPressed();
		Dimension bounds = myView.getSize();
		if (key == PROCEED){
			gamePlay = true;

		}

		if (key == SUGAR_RUSH){ //CHEAT CODE, increases sugar meter
			myScore.updateValue(5);
		}
		
		if (key == HEALTH_BOOST){
			myLives.updateValue(1);
		}

		if (gameEnd == false){
			updateSprites(elapsedTime, bounds);
			intersectSprites(bounds);
			checkRules();
		}
	}

	public Model (Canvas canvas) {
		myView = canvas;
		initStatus();
		initSprites(canvas.getSize());
	}

	/**
	 * Draw all elements of the game.
	 */
	public void paint (Graphics2D pen) {
		if (gamePlay == false && !gameEnd ){ //game paused but not over => proceed to level 2
			if (level == 2){ 
				L2_IMAGE.paint(pen, new Location(Canvas.SIZE.getWidth()/2,Canvas.SIZE.getHeight()/2), Canvas.SIZE,0);    		
				int instLine = (int)Canvas.SIZE.getHeight()/2-100;
				for (Text line: myInstructions){
					line.paint(pen, new Point((int)Canvas.SIZE.getWidth()/2, instLine) , LABEL_COLOR);
					instLine += 50;
				}
			}
		}

		else{
			if (!gameEnd){ //game in process
				if (level== 1){
					BACKGROUND_IMAGE.paint(pen, new Location(Canvas.SIZE.getWidth()/2,Canvas.SIZE.getHeight()/2), Canvas.SIZE,0);
					for (Candy candy: candyList){
						candy.paint(pen);
					}
					for(Candy poop: pList){
						poop.paint(pen);
					}

					for (Cloud b : myClouds) {
						b.paint(pen);
					}
				}
				if (level == 2){
					BACKGROUND2_IMAGE.paint(pen, new Location(Canvas.SIZE.getWidth()/2,Canvas.SIZE.getHeight()/2), Canvas.SIZE,0);
					for (Candy snow: snowList){
						snow.paint(pen);
					}

					for (Candy penguin: pList){
						penguin.paint(pen);
					}

					for (Candy candy: candyList){
						candy.paint(pen);
					}
				}
				paintStatus(pen);
				myChipmunk.paint(pen);
			}
			else{ //game won/ lost
				paintEnd(pen);
			}
		}
	}


	/**
	 * Create chipmunk, cloud, candy, poop/penguin according to game level
	 */
	private void initSprites (Dimension bounds) {
		candyList = new LinkedList<Candy>();
		pList = new LinkedList<Candy>();
		myChipmunk = new Chipmunk(CHIPMUNK_IMAGE, 
				new Location(bounds.width / 2, bounds.height-Chipmunk.DEFAULT_OFFSET - 30),
				Chipmunk.DEFAULT_SIZE,
				myView, Chipmunk.DEFAULT_VELOCITY);
		if (level == 1){
			myClouds = new ArrayList<Cloud>();
			Cloud.createList(myClouds, bounds, NUM_CLOUDS); 
			VertCandy.createList(candyList, NUM_CANDY);
			Poop.createList(pList, NUM_POOP, Poop.getPoopImage());
		}
		if (level == 2){
			snowList = new LinkedList<Candy>();
			Poop.createList(snowList, NUM_SNOW, Poop.getSnowImage());	
			HoriCandy.createList(candyList, NUM_ICE);
			Penguin.createList(pList, NUM_POOP);
			myChipmunk.canJump(true);

		}
		bottom = bounds.getHeight();
		song.play();
	}

	/**
	 * Create "heads up display", i.e., status labels that will appear in the game.
	 */
	private void initStatus () {
		myScore = new ValueText(SCORE_LABEL, SUGAR_METER);
		myLives = new ValueText(LIVES_LABEL, STARTING_LIVES);
		myLevel = new ValueText(LEVELS_LABEL, level);
		myInstructions = new ArrayList<Text>();
		if (level == 2){ //build L2 instructions
			buildL2Inst(myInstructions);     
		}
	}

	/**
	 * Build instructions; customize font style and size
	 */
	private void buildL2Inst(ArrayList<Text> list){
		addInst(list, L2InstLine1, Font.SANS_SERIF, 24);
		addInst(list, L2InstLine2, Font.SANS_SERIF, 16);
		addInst(list, L3InstLine3, Font.SANS_SERIF, 16);
		addInst(list, L2InstLine4, Font.SANS_SERIF, 16);
	}

	//helper to buildL2Inst
	private void addInst(ArrayList<Text> list, Text text, String font, int size){
		list.add (text);
		text.setFont(font, size);
	}


	/**
	 * Update sprites based on time since the last update.
	 */
	private void updateSprites (double elapsedTime, Dimension bounds) {
		if (gamePlay){
			if (level == 1){
				for (Candy poop: pList){
					poop.update(elapsedTime, bounds);
				}
				for (Candy candy: candyList){
					candy.update(elapsedTime, bounds);
				}
			}
			if (level == 2){
				for (Candy candy: candyList){
					candy.update(elapsedTime, bounds);
				}
				for (Candy penguin: pList){
					penguin.update(elapsedTime, bounds);
				}
				for (Candy snow: snowList){
					snow.update(elapsedTime,bounds);
				}
			}
			myChipmunk.update(elapsedTime, bounds);
		}
	}

	/**
	 * Check for intersections between sprites
	 */
	private void intersectSprites (Dimension bounds) {
		if (level == 1){
			candyLoop(bounds, pList, myLives, -1, new Poop(Poop.getPoopImage(), Poop.getRandomLocation(), Poop.DEFAULT_SIZE_M, Poop.randomVelocity()));
			candyLoop(bounds, candyList, myScore, ADD_SUGAR, new VertCandy(VertCandy.randomImage(), VertCandy.getRandomLocation(), Candy.DEFAULT_SIZE_S, VertCandy.randomVelocity()));
		}
		if (level == 2){
			candyLoop(bounds, pList, myLives, -1, new Penguin(Penguin.getImage(), Penguin.getRandomLocation(), Candy.DEFAULT_SIZE_M, Candy.DEFAULT_VELOCITY_HORI));
			candyLoop(bounds, candyList, myScore, ADD_SUGAR_L2, new HoriCandy(HoriCandy.randomImage(), HoriCandy.getRandomLocation(), HoriCandy.DEFAULT_SIZE_M, Candy.DEFAULT_VELOCITY_HORI));
			candyLoop(bounds, snowList, myLives, -1, new Poop(Poop.getSnowImage(), Poop.getRandomLocation(), Poop.DEFAULT_SIZE_M, Poop.randomVelocity()));
		}
	}

	//helper for intersectSprites, check for intersection, update scores and lives, remove affected sprite and add new one to list
	private void candyLoop(Dimension bounds, LinkedList<Candy> q, ValueText score, int updateVal, Candy candy){//ValueText score, Pixmap image, Location loc, Dimension size, Vector velocity){
		Iterator<Candy> iter = q.iterator();
		while (iter.hasNext()) {
			Candy c = iter.next();
			if (c.intersects(myChipmunk)) { //if sprite intersect chipmunk, update scores (myLives/myScore) accordingly
				q.poll();
				score.updateValue(updateVal);
				q.add(candy);
				break;
			}
			if (c.getBottom() >= bottom) { //if vertical sprite hits bottom of screen, remove from sight and create new one
				q.poll();
				q.add(candy);
				break;
			}  
			if (c.getLeft() >= bounds.getWidth()) { //if horizontal sprite hits side of screen, remove from sight and create new one
				q.poll();
				q.add(candy);
				break;
			}  
		}        	
	}


	/**
	 * Check that if the game is won or lost.
	 */
	private void checkRules () {
		//If all lives lost, announce game is lost
		if (myLives.getValue() <= 0){
			gameEnd = true;
			gamePlay = false;
		}

		//if sugar meter is full, go to next level or announce game victory
		if (myScore.getValue() >= 100){
			if (level == 1){
				//proceed to next level
				gamePlay = false;
				level++;
				myLevel.updateValue(1);
				initStatus();
				myChipmunk.canJump(true);
				initSprites(myView.getSize());
			}
			else{
				gameWon = true;
				gameEnd = true;
				gamePlay = false;
			}
		}
	}

	public void paintEnd (Graphics2D pen) {
		//if all lives lost, paint lose background
		if (level== 1 || (level == 2 &&!gameWon)){
			LOSE_IMAGE.paint(pen, new Location(Canvas.SIZE.getWidth()/2,Canvas.SIZE.getHeight()/2), Canvas.SIZE,0);
			song.stop();
		}
		//if game is won, paint win background
		if (level == 2 && gameWon){
			WIN_IMAGE.paint(pen, new Location(Canvas.SIZE.getWidth()/2,Canvas.SIZE.getHeight()/2), Canvas.SIZE,0);
		}
	}

	/**
	 * Display labels on screen
	 */
	private void paintStatus (Graphics2D pen) {
		myScore.paint(pen, new Point(LABEL_X_OFFSET, LABEL_Y_OFFSET), LABEL_COLOR);
		myLives.paint(pen, new Point(myView.getSize().width - LABEL_X_OFFSET, LABEL_Y_OFFSET), LABEL_COLOR);  
		myLevel.paint(pen,  new Point(LABEL_X_OFFSET+200, LABEL_Y_OFFSET), LABEL_COLOR);
	}
}
