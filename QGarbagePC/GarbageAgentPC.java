import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import util.BTSend;
import util.Consts;

public class GarbageAgentPC {
	BTSend tonxt;
	String msg = "";
	static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public GarbageAgentPC(){
		tonxt = new BTSend();
	
		try {
		    Handler fileHandler = new FileHandler("logs/logger.log", 2000, 5);
		    
		    //setting custom filter for FileHandler
		    logger.addHandler(fileHandler);
		    logger.setUseParentHandlers(false);
		    SimpleFormatter formatter = new SimpleFormatter();
		    fileHandler.setFormatter(formatter);
			if(!tonxt.NXTconnect(Consts.USB)) {
				System.out.println("failed-nxt");
				logger.log(Level.INFO, "failed-nxt");
				System.exit(1);
			}
	    } catch (SecurityException | IOException e) {
	        e.printStackTrace();
	    }
	}
	public void moveForward() throws IOException {
		logger.log(Level.INFO, "in move forward");
		msg="!0";
		tonxt.send(tonxt.dos, msg);
	}
	public void moveBackward() throws IOException{
		logger.log(Level.INFO, "in move backward");
		msg="!1";
		tonxt.send(tonxt.dos, msg);
	}
	public void turnRight()throws IOException{
		logger.log(Level.INFO, "in turnRight");
		msg="!2";
		tonxt.send(tonxt.dos, msg);
	}
	public void turnLeft()throws IOException{
		logger.log(Level.INFO, "in  turnLeft");
		msg="!3";
		tonxt.send(tonxt.dos, msg);
	}
	
	public void pickObject()throws IOException{
		logger.log(Level.INFO, "in pickObject");
		msg="!4";
		tonxt.send(tonxt.dos, msg);
	}
	
	public void dropObject()throws IOException{
		logger.log(Level.INFO, "in dropObject");
		msg="!5";
		tonxt.send(tonxt.dos, msg);
	}
	public void goToCorner(char Loc)throws IOException{
		logger.log(Level.INFO, "in goToCorner");
		msg=">"+Loc;
		tonxt.send(tonxt.dos, msg);
	}
	
	public float getEnergy()throws IOException{
		logger.log(Level.INFO, "in getEnergy");
		msg="<E";
		tonxt.send(tonxt.dos, msg);
		float en = tonxt.recvF(tonxt.dis);
		logger.log(Level.INFO, "return getEnergy "+en);
		return en;
	}
	
	public boolean isObstableFront()throws IOException{
		logger.log(Level.INFO, "in isObstableFront");
		msg="<O";
		tonxt.send(tonxt.dos, msg);
		boolean ob = tonxt.recvB(tonxt.dis);
		logger.log(Level.INFO, "return isObstableFront "+ob);
		return ob;
		//return false;
	}
	
	public int getColorObject()throws IOException{
		logger.log(Level.INFO, "in getColorObject");
		msg="<C";
		tonxt.send(tonxt.dos, msg);
		int col = tonxt.recvI(tonxt.dis);
		logger.log(Level.INFO, "return getColorObject "+col);
		return col;
	}
	
	public int getColorFloor()throws IOException{
		logger.log(Level.INFO, "in getColorFloor");
		msg="<F";
		tonxt.send(tonxt.dos, msg);
		int col = tonxt.recvI(tonxt.dis);
		logger.log(Level.INFO, "return getColorFloor "+col);
		return col;
	}
	public int getColorObjectInt()throws IOException{
		logger.log(Level.INFO, "in getColorObjectInt");
		msg="<c";
		tonxt.send(tonxt.dos, msg);
		int col = tonxt.recvI(tonxt.dis);
		logger.log(Level.INFO, "return getCologetColorObjectIntrObject "+col);
		return col;
	}
	
	public int getColorFloorInt()throws IOException{
		logger.log(Level.INFO, "in getColorFloorInt");
		msg="<f";
		tonxt.send(tonxt.dos, msg);
		int col = tonxt.recvI(tonxt.dis);
		logger.log(Level.INFO, "return getColorFloorInt "+col);
		return col;
	}
	
	public int getCorner()throws IOException{
		logger.log(Level.INFO, "in getCorner");
		msg="<R";
		tonxt.send(tonxt.dos, msg);
		int cor = tonxt.recvI(tonxt.dis);
		logger.log(Level.INFO, "return getCorner "+cor);
		return cor;
	}
	
	public int getClawStatus()throws IOException{
		logger.log(Level.INFO, "in getClawStatus");
		msg="<W";
		tonxt.send(tonxt.dos, msg);
		int claw = tonxt.recvI(tonxt.dis);
		logger.log(Level.INFO, "return getClawStatus "+claw);
		return claw;
	}
	public void stop()throws IOException{
		logger.log(Level.INFO, "in stop");
		msg="@";
		tonxt.send(tonxt.dos, msg);
	}
	
	public float getDist()throws IOException {
		logger.log(Level.INFO, "in getDist");
		
		msg="<U";
		tonxt.send(tonxt.dos, msg);
		float dist = tonxt.recvF(tonxt.dis);
		logger.log(Level.INFO, "return getDist "+dist);
		return dist;
		
	}
	
	public float getComp()throws IOException {
		logger.log(Level.INFO, "in getComp");
		
		msg="<M";
		tonxt.send(tonxt.dos, msg);
		float comp = tonxt.recvF(tonxt.dis);
		logger.log(Level.INFO, "return getComp "+comp);
		return comp;
		
	}
	public static void main(String [] args)  throws Exception {
		
		GarbageAgentPC agent = new GarbageAgentPC();
		System.out.println("corner"+ agent.getCorner());
		System.out.println("isObstableFront" + agent.isObstableFront());
		System.out.println("getColorFloor" + agent.getColorFloor());
	}
}
