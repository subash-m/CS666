
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import util.Consts;
 
public class Qlearning {

	/* 
	 * states Open, Red_Ball, Blue_Ball, Yellow_Ball, Corner_A, Corner_B, Corner_C,
	 * Corner_D, Battery, Wall, Object
	 * e.g. 
	 * 		The Goal is to put the color balls in their respective corner
	 * __________
	 * |A|    |B|
	 * |_|    |_|
	 * |        |
	 * |_      _|
	 * |C|    |D|
	 * |_|____|_|
	 * 
	 */

	private final DecimalFormat df;
    private final int[] states;
    private final int[] actions;
    private final int [][] R;
    private double[][] Q;
    private String[] stateNames;
    public GarbageAgentPC agent;
    private static Logger logger;
    private StatesHelper statesHelper;
    private int state;
    
    public Qlearning() {

    	df = new DecimalFormat("#.##");
    	
    	states = new int[]{Consts.OPEN, Consts.RED_BALL, Consts.GREEN_BALL, Consts.YELLOW_BALL, Consts.CORNER_A, Consts.CORNER_B,
        		Consts.CORNER_C, Consts.CORNER_E, Consts.BATTERY, Consts.WALL, Consts.OBJECT};
    	
    	actions = new int[]{ Consts.GOTO_A, Consts.GOTO_B, Consts.GOTO_C, Consts.GOTO_D, Consts.FORWARD, Consts.BACKWARD,
        		Consts.LEFT, Consts.RIGHT, Consts.GOTO_CENTER, Consts.GRAB};
    	
    	stateNames = new String[] {"Open", "Red Ball", "Blue Ball", "Yellow Ball", "Corner A", "Corner B", "Corner C", "Corner D", 
    			"Battery", "Wall", "Object"};
    	
    	Q = new double[Consts.STATES_COUNT][Consts.ACTIONS_COUNT]; 	// Q Values
    	
    	R = new int[][] {{-1, -1, -1, -5, 1, 0,	0,	0,	0,	-2},	// Reward Lookup
    					 {0, 0, 0, -3, -3, -3 , -3,	-3,	-3,	-2},
    					 {0, 0, 0, -3, -3, -3 , -3,	-3,	-3,	-2},
    					 {0, 0, 0, -3, -3, -3 , -3,	-3,	-3,	-2},
    					 {-1, -1, -1, -5, -5, -5, 2, 2, 1, -2},
    					 {-5, -5, -5, 5, -5, -5, -5, -5, -5, -2},
    					 {-5, -5, -5, -5, -3, 1, -3, 1, 3, -2},
    					 {-5, -5, -5, -5, -3, 1	,1	,-3	,3	,-2},
    					 {-5, -5, -5, -5, 1	,-3	,-3	,1	,3	,-2},
    					 {-5, -5, -5, -5, 1	,-3	,1	,-3	,3	,-2},
    					 {-5, -5 ,-5, -5, -5,-5	,-5	,-5	,-5	,5}};
    					 
    	agent = new GarbageAgentPC();
    	
    	state = Consts.CORNER_E;		//Assumption : Robot is left at the Charging station
    	
    	logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    	try {
		    Handler fileHandler = new FileHandler("logs/QLearning.log", 2000, 5);
		    
		    //setting custom filter for FileHandler
		    logger.addHandler(fileHandler);
		    logger.setUseParentHandlers(false);
		    SimpleFormatter formatter = new SimpleFormatter();
		    fileHandler.setFormatter(formatter);

			logger.log(Level.INFO, "QLearning Initialised");
	    } catch (SecurityException | IOException e) {
	        e.printStackTrace();
	    }
    }
    
    public static void main(String[] args) {
        long BEGIN = System.currentTimeMillis();
 
        Qlearning obj = new Qlearning();
 
        obj.run();
 
        long END = System.currentTimeMillis();
        
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
    }
 
    public void run() {
        /*
         * 1. Set parameter, environment and reward matrix R 
         * 2. Initialize matrix Q as zero matrix 
         * 3. For each episode: Select random initial state 
         * 	  Do while not reach goal state o 
         *   	Select one among all possible actions for the current state o 
         *   	Using this possible action, consider to go to the next state o 
         *   	Get maximum Q value of this next state based on all possible actions o 
         *   	Compute o Set the next state as the current state
         */
 
        // For each episode
        Random rand = new Random();
        
        while(true) {
        	
        	int action = getArgMax(state);
        	
        	if(rand.nextDouble() <= Consts.EPSILON)
        	{
        		int rAction = rand.nextInt(Consts.ACTIONS_COUNT);
        		doAction(rAction);
        	}
        	else {
        		doAction(action);
        	}
                // Action outcome is set to deterministic in this example
                // Transition probability is 1
            int nextState = statesHelper.getState(state); // data structure
 
		    // Using this possible action, consider to go to the next state
            double q = Q(state, action);
            double maxQ = getArgMax(nextState);
            int r = R(state, action);
 
            double value = q + Consts.ALPHA * (r + Consts.GAMMA * maxQ - q);
            setQ(state, action, value);
 
            // Set the next state as the current state
		    state = nextState;
        }
    }
    
    public void doAction(int action){

    	try{
	    	switch(action){
	    		case 0:
	    			agent.goToCorner('A');
	    			//Implement to wait till the action finishes
	    			state = Consts.CORNER_A;
	    			break;
	    		case 1:
	    			agent.goToCorner('B');
	    			state = Consts.CORNER_B;
	    			break;
	    		case 2:
	    			agent.goToCorner('C');
	    			state = Consts.CORNER_C;
	    			break;
	    		case 3:
	    			agent.goToCorner('E');
	    			state = Consts.CORNER_E;
	    			break;
	    		case 4:
	    			agent.moveForward();
	    			break;
	    		case 5:
	    			agent.moveBackward();
	    			break;
	    		case 6:
	    			agent.turnLeft();
	    			break;
	    		case 7:
	    			agent.turnRight();
	    			break;
	    		case 8:
	    			//To Call Goto_Center method
	    			break;
	    		case 9:
	    			agent.pickObject();
	    			break;
	    	}
    	}catch(IOException ie){
    		System.out.println("NXT Communication Exception");
    	}
    }

    public int getArgMax(int state) {
    	
    	double max;
    	int action;
    	
    	max = Q[state][0];
    	action = 0;
    	
    	for(int i=1; i < Consts.ACTIONS_COUNT; i++)
    	{
    		if(Q[state][i] > max)
    		{
    			max = Q[state][i];
    			action = i;
    		}
    	}
    	return action;
    }
 
    public double Q(int s, int a) {
        return Q[s][a];
    }
 
    public void setQ(int s, int a, double value) {
        Q[s][a] = value;
    }
 
    public int R(int s, int a) {
    	if((s >= Consts.RED_BALL && s <= Consts.YELLOW_BALL) && ( a >=Consts.GOTO_A && a <= Consts.GOTO_C))
    		return checkMatch(s);
    	else
    		return R[s][a];
    }
    
    public int checkMatch(int state){

    	int reward = -5;
    	try {
			int floorColor = agent.getColorFloor();
			logger.log(Level.INFO, "State: Red=1, Green=2, Yellow=3");
			logger.log(Level.INFO, "Floor: Red=0, Yellow=1, Green=2");
			logger.log(Level.INFO, "Reward for "+state+" in "+floorColor+" floor.");
			switch(floorColor){
				case 0:					//Red Corner
					if(state == Consts.RED_BALL)
						reward = 2;
					break;
				case 1:					//Yellow Corner
					if(state == Consts.YELLOW_BALL)
						reward = 2;
					break;
				case 2:					//Green Corner
					if(state == Consts.GREEN_BALL)
						reward = 2;
					break;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
    	return reward;
    }
    
    public int getState(int oldState){
		logger.log(Level.INFO, "oldState - "+oldState);
		try {
			//Battery energy High Priority
			if(agent.getEnergy() <= Consts.ENERGY_THRES){
				return Consts.BATTERY;
			}
			if(agent.getDist() <= Consts.DIST_THRES){
				agent.stop();
				return Consts.WALL;
			}
			if(agent.getColorObjectInt() <= Consts.OBJECT_THRES){
				return Consts.OBJECT;
			}
			if(agent.getClawStatus() == 1){			// Claw is open
				return Consts.OPEN;
			}else if(agent.getClawStatus() == 0){	// Claw is closed
				int objColor = agent.getColorObject();
				switch(objColor){
					case 0:
						return Consts.RED_BALL;
					case 1:
						return Consts.YELLOW_BALL;
					case 2:
						return Consts.GREEN_BALL;
				}
			}
			if(oldState >= Consts.CORNER_A && oldState <= Consts.CORNER_E){
				return oldState;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}