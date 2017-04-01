import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import util.Consts;

public class StatesHelper {

	public GarbageAgentPC agent;
	private static Logger logger;
	
	public StatesHelper(GarbageAgentPC agent) {
		this.agent = agent;
		
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    	try {
		    Handler fileHandler = new FileHandler("logs/StatesHelper.log", 2000, 5);
		    
		    //setting custom filter for FileHandler
		    logger.addHandler(fileHandler);
		    logger.setUseParentHandlers(false);
		    SimpleFormatter formatter = new SimpleFormatter();
		    fileHandler.setFormatter(formatter);

			logger.log(Level.INFO, "StatesHelper Initialised");
	    } catch (SecurityException | IOException e) {
	        e.printStackTrace();
	    }
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