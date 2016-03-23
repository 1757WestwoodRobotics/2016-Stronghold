package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author 17rmarten
 *
 */
public class Commands {
	Drive drive;
	Breach breach;
	
	public Commands (Drive drive, Breach breach){
		this.drive = drive;
		this.breach = breach;
	}
	
	DrawBridgeCommand drawbridge = new DrawBridgeCommand(drive, breach, "drawbridge Thread");
	
	
	public void doCommand(Joystick buttonBox){
		if (buttonBox.getRawButton(1)==true){
			System.out.println("Button 1 pressed:Starting Drawbridge Sequence");
			drawbridge.start();
			Timer.delay(2);
		}else if (buttonBox.getRawButton(2)==true){
			System.out.println("Button 2 pressed: Star ting ___ Sequence");
		}else if (buttonBox.getRawButton(4)==true){
			System.out.println("Button 4 pressed: Canceled");
			drawbridge.start();
			Timer.delay(2);
		}else if (buttonBox.getRawButton(5)==true){
			System.out.println("Button 5 pressed: Starting ___ Sequence");
		}
	}
}
