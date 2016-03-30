package org.usfirst.frc.team1757.robot;

public class DrawBridgeCommand implements Runnable{

	Drive drive;
	Breach breach;
	String threadName;
	Thread t;
	
	public DrawBridgeCommand (Drive drive, Breach breach, String name){
		this.drive = drive;
		this.breach = breach;
		threadName = name;
		
	}

	@Override
	 public void run() {
	      System.out.println("Running " +  threadName );
	      try {
	    	 drive.doAutoDrive(Constants.Autonomous.LOWBARSPEED_2, Constants.Autonomous.LOWBARTIME_1);
	    	//TODO: breach.moveToAngle(120);
	    	 Thread.sleep(50);
	     } catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	     }
	     System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
	 public void start ()
	   {
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

	
}
