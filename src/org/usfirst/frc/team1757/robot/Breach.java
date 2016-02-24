package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

/**

 */
public class Breach {
	
	double breachSpeed;
	boolean isBreaching;
	AnalogPotentiometer stringPot;
	
	static CANTalon talon4;
	
	
	public Breach(double breachSpeed, boolean isBreaching) {
		this.breachSpeed = breachSpeed;
		this.isBreaching = isBreaching;

		talon4 = new CANTalon(4);
		talon4.setInverted(false);
		stringPot = new AnalogPotentiometer(0);
	}
	

	public void printBreachMessages(Joystick gamepad) {
		SmartDashboard.putNumber("breachSpeed", breachSpeed);
		SmartDashboard.putBoolean("isBreaching?", isBreaching);
		SmartDashboard.putNumber("String Pot", stringPot.get());
		//SmartDashboard.putNumber("Breach-motorCurrent", talon4.getOutputCurrent());
	}

	public void doBreach(Joystick gamepad) {
		if (gamepad.getRawAxis(Constants.BUTTON_LT) > .2) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				talon4.set(breachSpeed);
				isBreaching = true;
			}
			
		} else if (gamepad.getRawAxis(Constants.BUTTON_RT) > .2) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				talon4.set(-breachSpeed);
				isBreaching = true;
			}
		}
		/*
		    breachSpeed -= 0.01;
			System.out.println("Decrementing breachSpeed..." + breachSpeed);
			Timer.delay(0.05);
			breachSpeed = Math.max(-1, breachSpeed);
			breachSpeed += 0.01;
			System.out.println("Incrementing breachSpeed..." + breachSpeed);
			Timer.delay(0.05);
			breachSpeed = Math.min(1, breachSpeed);
		 */
		else {
			talon4.set(0);
			isBreaching = false;
		}
	}
}