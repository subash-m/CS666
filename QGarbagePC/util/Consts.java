package util;

public class Consts {
	public static final int DEFAULT_SPEED = 150;
	public static final int CH = 3; //charing center 
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int BT = 2, USB = 1; //connection mode
	
	//to do need to change to correct value from nxt sensor
	public static final int GREEN = 2;
	public static final int RED = 0;
	public static final int BLUE = 3;
	public static final int YELLOW = 1;
	
	public static final float ENERGY_THRES = 20.0f;
	public static final int DIST_THRES = 16;
	public static final int OBJECT_THRES = 50;
	public static final int CLAW_OPEN = 1;
	public static final int CLAW_CLOSE = 0;
	
	public static final double ALPHA = 0.1;
	public static final double GAMMA = 0.9;
	public static final double EPSILON = 0.01;
	
	//The following are different states of the Robot
	public static final int OPEN = 0;
	public static final int RED_BALL = 1;
	public static final int GREEN_BALL = 2;
	public static final int YELLOW_BALL = 3;
	public static final int CORNER_A = 4;
	public static final int CORNER_B = 5;
	public static final int CORNER_C = 6;
	public static final int CORNER_E = 7;
	public static final int BATTERY = 8;
	public static final int WALL = 9;
	public static final int OBJECT = 10;
 
	public static final int STATES_COUNT = 11;
	
	//The following are different actions of the Robots
	public static final int GOTO_A = 0;
	public static final int GOTO_B = 1;
	public static final int GOTO_C = 2;
	public static final int GOTO_D = 3;
	public static final int FORWARD = 4;
	public static final int BACKWARD = 5;
	public static final int LEFT = 6;
	public static final int RIGHT = 7;
	public static final int GOTO_CENTER = 8;
	public static final int GRAB = 9;
    
	public static final int ACTIONS_COUNT = 10;
}