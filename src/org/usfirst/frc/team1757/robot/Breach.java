package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

public class Breach {
	
	double breachSpeed;
	boolean isBreaching;
	double angleSetpoint;
	breachTypes breachType;
	AnalogPotentiometer stringPot;
	PIDController breachPID;
	PseudoPIDOutput pidOut;
	
	static CANTalon canBreach;
	
	public Breach(double breachSpeed, boolean isBreaching) {
		this(breachSpeed, isBreaching, new CANTalon(4), breachTypes.pidBreach);
	}
	
	public Breach(double breachSpeed, boolean isBreaching, CANTalon canBreach, breachTypes breachType) {
		this.breachSpeed = breachSpeed;
		this.isBreaching = isBreaching;
		this.canBreach = canBreach;
		this.canBreach.setInverted(false);
		this.breachType = breachType;
		//stringPot = new AnalogPotentiometer(0, Constants.BreachArm.FULLRANGE, Constants.BreachArm.OFFSET);
		stringPot = new AnalogPotentiometer(0);
		pidOut = new PseudoPIDOutput();
		breachPID = new PIDController(Constants.BreachArm.Kp, Constants.BreachArm.Ki, Constants.BreachArm.Kd, stringPot, pidOut);
	}
	
	/**
	 * NEEDS FIXING, AND TUNING!!! (DRIVER OVERRIDE?)
	 */
	
	/**Set PID controller setpoint
	 * @param angle setpoint
	 * Will only work when PIDBreach mode is enabled
	 */
	public void moveToAngle(double angle) {
		angleSetpoint = angle;
	}
	
	/**Set the PID controller angle setpoint based on desired height
	 * @param height in ft
	 * Will only work when PIDBreach mode is enabled
	 */
	public void moveToHeight(double height) {
		//Sin(theta) = height/length
		angleSetpoint = Math.toDegrees(Math.asin(height/Constants.BreachArm.ARM_LENGTH));
	}
	
	public enum breachTypes {
		rawBreach, pidBreach;
	}
	
	/**Set the breaching type
	 * @param breachType
	 */
	public void setBreachType(breachTypes breachType) {
		this.breachType = breachType;
	}
	

	public void doRawBreach(Gamepad gamepad) {
		enablePID(false);
		if (gamepad.getTrigger(Constants.BUTTON_LT)) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				canBreach.set(-breachSpeed);
				isBreaching = true;
			}
			
		} else if (gamepad.getTrigger(Constants.BUTTON_RT)) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				canBreach.set(breachSpeed);
				isBreaching = true;
			}
		}
		else {
			canBreach.set(0);
			isBreaching = false;
		}
		
		SmartDashboard.putNumber("Breach-breachSpeed", breachSpeed);
		SmartDashboard.putBoolean("Breach-isBreaching?", isBreaching);
		SmartDashboard.putNumber("String Pot", stringPot.get());
	}
	
	public void doPIDBreach(Gamepad gamepad) {
		enablePID(true);
		if (gamepad.getTrigger(Constants.BUTTON_LT)) {
			if ((stringPot.get() < Constants.BreachArm.STRINGPOT_MAX) && (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN)) {
				angleSetpoint += Constants.BreachArm.ANGLE_ADJUST;
				breachPID.setSetpoint(angleSetpoint);
				isBreaching = true;
			}
			
		} else if (gamepad.getTrigger(Constants.BUTTON_RT)) {
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
	
	public void doBreach(Gamepad gamepad) {
		switch (breachType) {
		case rawBreach: 
			doRawBreach(gamepad); 
			break;
		case pidBreach: 
			doPIDBreach(gamepad); 
			break;
		default: System.out.println("Drive type not selected");
			break; 
		}
	}
}
