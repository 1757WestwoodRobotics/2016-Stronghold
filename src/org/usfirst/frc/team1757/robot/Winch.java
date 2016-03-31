/**
 * 
 */
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch {

	double winchSpeed;
	boolean isWinching;
	
	static CANTalon talon6, talon7;
	
	CANTeamDrive winchTeam;
	
	
	public Winch(double winchSpeed) {
		this.winchSpeed = winchSpeed;
		
		talon6 = new CANTalon(6);
		talon7 = new CANTalon(7);
		talon6.setInverted(true);
		talon6.enableBrakeMode(false);
		
		winchTeam = new CANTeamDrive(talon6, talon7);
	}

	
	public void doWinch(Joystick gamepad) {
		/**
		 * On the x-box controller, the dpad is a "POV". 
		 * It functions exactly how the doc says using the method call which does not require an argument. 
		 * Up is 0, right is 90, down is 180, left is 270, and if its unpressed, its -1. 
		 * You do get the 45 degree intervals between those I listed as well.
		 */
		if(gamepad.getPOV(0) == 0){
			winchTeam.set(winchSpeed);
		}
		else if(gamepad.getPOV(0) == 180){
			winchTeam.set(-winchSpeed);
		}
		else{
			winchTeam.set(0);
		}
		
		
		SmartDashboard.putBoolean("Winch-GoingUp?", isWinching);
		SmartDashboard.putNumber("winchSpeed", winchSpeed);
		SmartDashboard.putNumber("Winch-leftMotorCurrent", talon6.getOutputCurrent());
		SmartDashboard.putNumber("Winch-rightMotorCurrent", talon7.getOutputCurrent());
		winchSpeed = SmartDashboard.getNumber("winchSpeed");
		
	}
	
	
}
//servo.set((gamepad.getRawAxis(AXIS_X)+1)/2);