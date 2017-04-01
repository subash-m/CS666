import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import robotcontroller.StandardRobot;


public class GarbageAgentNXT {
	private StandardRobot me;
	public GarbageAgentNXT(){
		me = new StandardRobot();
	}
	@SuppressWarnings("deprecation")
	public void run() throws IOException, ClassNotFoundException, InterruptedException{
		char terminate = 0;
		int buttonID;
		
		String connected = "Connected";
        String waiting = "Waiting....";
        String closing = "Closing...";
        DataInputStream dis = null;
 		DataOutputStream dos = null;
 		BTConnection btc = null;
 		USBConnection conn = null;
 		
 		 if(terminate!='@'){
 			LCD.drawString(waiting,0,0);
			LCD.refresh();

			//start| usb connection mode added
			LCD.clear();
			LCD.drawString("Mode of Connect:",0,0);
			LCD.drawString("Left: Bluetooth",0,1);
			LCD.drawString("Right: USB",0,2);
			while(true) {
				
				buttonID = Button.waitForAnyPress();
				
				if(buttonID == Button.ID_LEFT) {
					LCD.clear();
					LCD.drawString(waiting,0,0);
					btc = Bluetooth.waitForConnection();
					dis = btc.openDataInputStream();
					dos = btc.openDataOutputStream();
					break;
				} else if(buttonID == Button.ID_RIGHT){
					LCD.clear();
					LCD.drawString(waiting,0,0);
					conn = USB.waitForConnection();
					dis = conn.openDataInputStream();
					dos = conn.openDataOutputStream();
					break;
				}
				LCD.drawString("invalid!",0,0);
			}
	        //end | usb connection mode added
			

			LCD.clear();
			LCD.drawString(connected,0,0);
			LCD.refresh();	
			LCD.drawString("Stream Created",0,4);
			while(terminate!='@'){
				
				String Msg	=	"";
				char temp	=	0;
				int flag	=	0;
				int i		=	0;
				int res		=	0;
				
				try{
					temp = dis.readChar();
				}
				catch(Exception e)
				{
					LCD.clear();
					LCD.drawString("Disconnected",0,0);
					Thread.sleep(5000);
					flag = 100;
					break;
				}
				if(temp == '@'){
					terminate='@';
					break;
				} else if(temp == '!'){
					char opcode = dis.readChar();
					switch(opcode){
					
					case '0':
						me.moveForward();
						break;
						
					case '1':
						me.moveBackward();
						break;
						
					case '2':
						me.turnRight();
						break;
						
					case '3':
						me.turnLeft();
						break;
					case '4':
						me.pickObject();
						break;
					case '5':
						me.dropObject();
						break;
					}
					
				}else if(temp == '>'){
					char opcode = dis.readChar();
					switch(opcode){
					
					case 'A':
						me.goToCorner('A');
						break;
						
					case 'B':
						me.goToCorner('B');;
						break;
						
					case 'C':
						me.goToCorner('C');
						break;
						
					case 'E':
						me.goToCorner('E');
						break;
					
					}
					
				} else if(temp == '<'){
					char opcode = dis.readChar();
					switch(opcode){
					
					case 'E':
						float en = me.getEnergy();
						dos.writeFloat(en);
						LCD.drawString("V: "+String.valueOf(en),0,4);
						dos.flush();
						break;
						
					case 'O':
						boolean ob = me.isObstableFront();
						dos.writeBoolean(ob);
						LCD.drawString("V: "+String.valueOf(ob),0,4);
						dos.flush();
						break;
						
					case 'C':
						int col = me.getColorObject();
						dos.writeInt(col);
						LCD.drawString("V: "+String.valueOf(col),0,4);
						dos.flush();
						break;
						
					case 'F':
						int colf = me.getColorFloor();
						dos.writeInt(colf);
						LCD.drawString("V: "+String.valueOf(colf),0,4);
						dos.flush();
						break;
					case 'c':
						int coli = me.getColorObjectInt();
						dos.writeInt(coli);
						LCD.drawString("V: "+String.valueOf(coli),0,4);
						dos.flush();
						break;
						
					case 'f':
						int colfi = me.getColorFloorInt();
						dos.writeInt(colfi);
						LCD.drawString("V: "+String.valueOf(colfi),0,4);
						dos.flush();
						break;
					case 'R':
						int cor = me.getCorner();
						dos.writeInt(cor);
						LCD.drawString("V: "+String.valueOf(cor),0,4);
						dos.flush();
						break;
					case 'U':
						float dist = me.getUltraSonicValue();
						dos.writeFloat(dist);
						LCD.drawString("V: "+String.valueOf(dist),0,4);
						dos.flush();
						break;
					case 'M':
						float comp = me.getCompassValue();
						dos.writeFloat(comp);
						LCD.drawString("V: "+String.valueOf(comp),0,4);
						dos.flush();
						break;
					case 'W':
						int claw = me.getClawStatus();
						dos.writeInt(claw);
						LCD.drawString("V: "+String.valueOf(claw),0,4);
						dos.flush();
						break;
					}
					
				}
				
			}
		}
 		Button.waitForAnyPress();
 		LCD.clear();
		LCD.drawString(closing,0,5);
		LCD.refresh();
		
	}
	
	public static void main(String[] args) throws Exception {
		GarbageAgentNXT agent = new GarbageAgentNXT();
		agent.run();
	 	
	} // end main
}
