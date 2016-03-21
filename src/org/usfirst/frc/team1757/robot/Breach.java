package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

/**

 */
public class Breach {
	
	double breachSpeed;
	boolean isBreaching;
	double angleSetpoint;
	AnalogPotentiometer stringPot;
	PIDController breachPID;
	PseudoPIDOutput pidOut;
	
	static CANTalon canBreach;
	
	public Breach(double breachSpeed, boolean isBreaching) {
		this(breachSpeed, isBreaching, new CANTalon(4));
	}
	
	public Breach(double breachSpeed, boolean isBreaching, CANTalon canBreach) {
		this.breachSpeed = breachSpeed;
		this.isBreaching = isBreaching;
		this.canBreach = canBreach;
		this.canBreach.setInverted(false);
		stringPot = new AnalogPotentiometer(0);
		pidOut = new PseudoPIDOutput();
		breachPID = new PIDController(Constants.BreachArm.Kp, Constants.BreachArm.Ki, Constants.BreachArm.Kd, stringPot, pidOut);
	}
	
	/**
	 * NEEDS FIXING, AND TUNING!!! (DRIVER OVERRIDE?)
	 */

	public void doBreach(Joystick gamepad) {
		
		//TODO Add proper breach mode switching
		if (gamepad.getRawAxis(Constants.BUTTON_LT) > Constants.TRIGGERZONE) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				canBreach.set(-breachSpeed);
				isBreaching = true;
			}
			
		} else if (gamepad.getRawAxis(Constants.BUTTON_RT) > Constants.TRIGGERZONE) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				canBreach.set(breachSpeed);
				isBreaching = true;
			}
		}
		else {
			canBreach.set(0);
			isBreaching = false;
		}
		
		if (gamepad.getRawAxis(Constants.BUTTON_LT) > Constants.TRIGGERZONE) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				angleSetpoint += Constants.BreachArm.ANGLE_ADJUST;
				breachPID.setSetpoint(angleSetpoint);
				isBreaching = true;
			}
			
		} else if (gamepad.getRawAxis(Constants.BUTTON_RT) > Constants.TRIGGERZONE) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				angleSetpoint += Constants.BreachArm.ANGLE_ADJUST;
				breachPID.setSetpoint(angleSetpoint);
				isBreaching = true;
			}
		}
		else {
			canBreach.set(pidOut.getOutput());
			isBreaching = false;
		}
		
		SmartDashboard.putNumber("Breach-breachSpeed", breachSpeed);
		SmartDashboard.putBoolean("Breach-isBreaching?", isBreaching);
		SmartDashboard.putNumber("String Pot", stringPot.get());
	}
	
	public void enablePID(boolean isEnabled) {
		if (isEnabled)
			breachPID.enable();
		else
			breachPID.disable();
	}
	
	public void moveToAngle(double angle) {
		
	}
	
	public void moveToHeight(double height) {
		
	}
}
