package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

/**
 * @author Larry Tseng
 *
 */
public class Breach {
	
	double breachSpeed;
	boolean isBreaching;
	
	static CANTalon talon4;
	
	public Breach(double breachSpeed, boolean isBreaching) {
		this.breachSpeed = breachSpeed;
		this.isBreaching = isBreaching;

		talon4 = new CANTalon(4);
		//talon4.setInverted(true); //MAYBE?
	}
	
	
	public void printBreachMessages(Joystick gamepad) {
		SmartDashboard.putNumber("breachSpeed", breachSpeed);
		SmartDashboard.putBoolean("isBreaching?", isBreaching);
		//SmartDashboard.putNumber("Breach-motorCurrent", talon4.getOutputCurrent());
	}
	
	
	//TODO This is hardcoded for the LogitechDualAction
	public void doBreach(Joystick gamepad) {
		if (gamepad.getRawButton(Constants.BUTTON_RT)) {
			breachSpeed -= 0.01;
			System.out.println("Decrementing breachSpeed...");
			Timer.delay(0.1);
			breachSpeed = Math.max(-1, breachSpeed);
		} else if (gamepad.getRawButton(Constants.BUTTON_LT)) {
			breachSpeed += 0.01;
			System.out.println("Incrementing breachSpeed...");
			Timer.delay(0.1);
			breachSpeed = Math.min(1, breachSpeed);
		}
		
		if (gamepad.getRawButton(Constants.BUTTON_X)) {
			talon4.set(breachSpeed);
			isBreaching = true;
		} else {
			talon4.set(0);
			isBreaching = false;
		}
	}
}
