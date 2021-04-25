package com.paydaran.Restaurant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;


public class SerialComm implements SerialPortEventListener {
SerialPort serialPort;
    /** The port we're normally going to use. */
private static final String PORT_NAMES[] = {                  "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyUSB0", // Linux
        "COM1", // Windows
};
private InputStream input;
private OutputStream output;
private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 19200;
Database db = new Database();

public void initialize() {
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

    //First, Find an instance of serial port as set in PORT_NAMES.
    while (portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if (currPortId.getName().equals(portName)) {
                portId = currPortId;
                break;
            }
        }
    }
    if (portId == null) {
        System.out.println("Could not find COM port.");
        return;
    }

    try {
        serialPort = (SerialPort) portId.open(this.getClass().getName(),
                TIME_OUT);
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // open the streams
        input = serialPort.getInputStream();
        output = serialPort.getOutputStream();

        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    } catch (Exception e) {
        System.err.println(e.toString());
    }
}


public synchronized void close() {
    if (serialPort != null) {
        serialPort.removeEventListener();
        serialPort.close();
    }
}
public synchronized void sendData(byte [] data) {
        try {
	output.write(data);
        output.flush();
    } catch (IOException e) {
	e.printStackTrace();
	}
    
}

public synchronized void serialEvent(SerialPortEvent oEvent) {
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
        try {
            byte[] buffer = new byte[32];
            if (input.read(buffer)!=  -1) {
                /*System.out.println("7E:‌"+buffer[0]);
                System.out.println("id:‌"+ buffer[1]);
                System.out.println("type:‌"+ buffer[2]);
                System.out.println("2:‌"+ buffer[3]);
                System.out.println("answer:‌"+ buffer[4]);
                System.out.println("qnum:‌"+ buffer[5]);
                System.out.println("qnum:‌"+ buffer[6]);
                System.out.println("qnum:‌"+ buffer[7]);
                System.out.println("qnum:‌"+ buffer[8]);*/
                if(buffer[2] == 7){
                    switch (buffer[1]){
                        case 1:
                            RestaurantMain.id[0]++;
                            System.out.println("id 1 recieved");
                            break;
                        case 2:
                            RestaurantMain.id[1]++;
                            break;
                        case 3:
                            RestaurantMain.id[2]++;
                        case 4:
                            RestaurantMain.id[3]++;
                        case 5:
                            RestaurantMain.id[4]++;
                    }
                    
                }else if(buffer[2] == 10){
                    db.openDb();
                    String query = "INSERT INTO PAYDARAN.TABLE_ANSWER (Q_ID, ANSWER, A_KIND) VALUES ("+(int)buffer[5]+", "+(int)buffer[4]+", "+(int)buffer[2]+")"; 
                    if(db.insertDb(query)){
			System.out.println("added...!");		
                    }else{
			System.out.println("ooooops...!");
                    }
                    db.closeDb();
                    
                    
                    
                    
                /*Writetodb ser = new Writetodb(buffer);
                Thread t2 = new Thread(ser);
                t2.start();*/
                }else if(buffer[2] == 9){
                    for(int i = 5 ; i < 31 ; i+=2){
                        //int L = toWrite[3] + 4;
                        db.openDb();
                        String query = "INSERT INTO PAYDARAN.TABLE_ANSWER(Q_ID, S_ID, ANSWER, A_KIND) VALUES ("+(int)buffer[i]+", "+(int)buffer[4]+", "+(int)buffer[i+1]+", "+(int)buffer[2]+")"; 
                        if(db.insertDb(query)){
                            System.out.println("added...!");		
			}else{
                            System.out.println("oooooops...!");
			}
                        db.closeDb();  
                        if(buffer[i+2] == 0)
                            break;
                        }
                            //break;
                }else if(buffer[2] == 4){
                    for(int i = 5 ; i < 31 ; i+=2){
                        //int L = toWrite[3] + 4;
                        db.openDb();
                        String query = "INSERT INTO PAYDARAN.TABLE_ANSWER(Q_ID, S_ID, ANSWER, A_KIND) VALUES ("+(int)buffer[i]+", "+(int)buffer[4]+", "+(int)buffer[i+1]+", "+(int)buffer[2]+")"; 
                        if(db.insertDb(query)){
                            System.out.println("added...!");		
			}else{
                            System.out.println("oooooops...!");
			}
                        db.closeDb();  
                        if(buffer[i+2] == 0)
                            break;
                        }
                            //break;
                }
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    // Ignore all the other eventTypes, but you should consider the other ones.
}

    public static class Writetodb implements Runnable{

            Database db = new Database();
            byte[] toWrite = new byte[32];
            
            public Writetodb ( byte [] buff )
        {
            this.toWrite = buff;
        }
            
        @Override
        public void run() {
           if(toWrite[2] == 10){
                            //System.out.println("7E:‌"+toWrite[0]);
                           // System.out.println("id:‌"+ toWrite[1]);
                            //System.out.println("type:‌"+ toWrite[2]);
                            //System.out.println("2:‌"+ toWrite[3]);
                           // System.out.println("qnum:‌"+ toWrite[4]);
                            //System.out.println("answer:‌"+ toWrite[5]);   
                            db.openDb();
                            String query = "INSERT INTO PAYDARAN.TABLE_ANSWER (Q_ID, ANSWER, A_KIND) VALUES ("+(int)toWrite[5]+", "+(int)toWrite[4]+", "+(int)toWrite[2]+")"; 
                            if(db.insertDb(query)){
				System.out.println("added...!");		
				}else{
				System.out.println("ooooops...!");
				}
                            db.closeDb();
                                                    
                            //break;
                        }else if(toWrite[2] == 9){
                            for(int i = 5 ; i < 31 ; i+=2){
                                //int L = toWrite[3] + 4;
                                db.openDb();
                                String query = "INSERT INTO PAYDARAN.TABLE_ANSWER(Q_ID, S_ID, ANSWER, A_KIND) VALUES ("+(int)toWrite[i]+", "+(int)toWrite[4]+", "+(int)toWrite[i+1]+", "+(int)toWrite[2]+")"; 
                                if(db.insertDb(query)){
                                    System.out.println("added...!");		
				}else{
                                    System.out.println("oooooops...!");
				}
                                db.closeDb();  
                                if(toWrite[i+2] == 0)
                                    break;
                                }
                            //break;
                        }
        }
            
        }

       /* public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            //System.out.println("SerialReader is run...!!");
            //byte [] shortmessage = new byte[6];
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                //int j = 0;
                while ( ( len = this.in.read(buffer)) != -1 )
                {                   
                    if(buffer[0] != 0){                        
                        System.out.println("7E:‌"+buffer[0]);
                        System.out.println("id:‌"+ buffer[1]);
                        System.out.println("type:‌"+ buffer[2]);
                        System.out.println("2:‌"+ buffer[3]);
                        System.out.println("answer:‌"+ buffer[4]);
                        System.out.println("qnum:‌"+ buffer[5]); 
                        Writetodb ser = new Writetodb(buffer);
                        Thread t2 = new Thread(ser);
                        //t2.start();                    
                        //break;
                    }
             
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }*/
}
