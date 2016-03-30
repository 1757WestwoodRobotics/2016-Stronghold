package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
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
		this(breachSpeed, isBreaching, new CANTalon(4), breachTypes.limitBreach);
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
		this.canBreach.enableBrakeMode(true);
	}
	
	/*
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
		//angleSetpoint = Math.toDegrees(Math.asin(height/Constants.BreachArm.ARM_LENGTH));
	}
	
	public enum breachTypes {
		rawBreach, pidBreach, limitBreach;
	}
	
	/**Set the breaching type
	 * @param breachType
	 */
	public void setBreachType(breachTypes breachType) {
		this.breachType = breachType;
	}
	
	public void doBreachToPoint(double point){
		System.out.println("Breaching to point: " + point);
		if (point > stringPot.get()) {
			while(stringPot.get() < Constants.BreachArm.STRINGPOT_MIN){
				if(stringPot.get() >= point){
					break;
				}
				canBreach.set(-breachSpeed);
				isBreaching = true;
			}
			canBreach.set(0);
			isBreaching = false;
			
		} else if (point < stringPot.get()) {
			while(stringPot.get() > Constants.BreachArm.STRINGPOT_MAX) {
				if(stringPot.get() <= point){
					break;
				}
				canBreach.set(breachSpeed);
				isBreaching = true;
			}
		}
		else {
			canBreach.set(0);
			isBreaching = false;
		}
	}
	
	public void doRawBreach(Gamepad gamepad) {
		//SIMPLE BREACH w/o LIMITS w/o POT  
		if (gamepad.getTrigger(Constants.BUTTON_LT)) {
			canBreach.set(-breachSpeed);
			isBreaching = true;
		} else if (gamepad.getTrigger(Constants.BUTTON_RT)) {
			canBreach.set(breachSpeed);
			isBreaching = true;
		}
		else {
			canBreach.set(0);
			isBreaching = false;
		}
	}

	//TODO Stringpot MIN AND MAX Calibreation
	public void doLimitBreach(Gamepad gamepad) {
		enablePID(false);
	
		if (gamepad.getTrigger(Constants.BUTTON_LT)) {
			if (stringPot.get() < Constants.BreachArm.STRINGPOT_MIN) {
				canBreach.set(-breachSpeed);
				isBreaching = true;
			}
			else{
				canBreach.set(0);
				isBreaching = false;
			}
			
		} else if (gamepad.getTrigger(Constants.BUTTON_RT)) {
			if (stringPot.get() > Constants.BreachArm.STRINGPOT_MAX) {
				canBreach.set(breachSpeed);
				isBreaching = true;
			}
			else {
				canBreach.set(0);
				isBreaching = false;
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
			if (stringPot.get() > Constants.BreachArm.STRINGPOT_MIN) {
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
		if (gamepad.getRawButton(Constants.BUTTON_LB)) {
			cycleType();
		}
		if (gamepad.getRawButton(Constants.BUTTON_RB)){
			resetPIDBreach();
		}
		
		printBreachMessages();
		
		switch (breachType) {
		case rawBreach: 
			doRawBreach(gamepad); 
			break;
		case pidBreach: 
			doPIDBreach(gamepad); 
			break;
		case limitBreach:
			doLimitBreach(gamepad);
			break;
		default: System.out.println("Drive type not selected");
			break; 
		}
	}

	private void printBreachMessages() {
		SmartDashboard.putString("Breach Type", breachType.toString());
		
	}

	private void resetPIDBreach() {
		//setpoint = 0;
		//PID
		
	}
	
	public void cycleType() {
		if (breachType == Breach.breachTypes.limitBreach) {
			breachType = Breach.breachTypes.rawBreach;
			System.out.println("RawBreach in use");
			Timer.delay(1);
		} else if (breachType == Breach.breachTypes.rawBreach) {
			breachType = Breach.breachTypes.limitBreach;
			System.out.println("LimitBreach in use");
			Timer.delay(1);
		} else {
			breachType = Breach.breachTypes.rawBreach;
			Timer.delay(1);
		}
	}

	
}
