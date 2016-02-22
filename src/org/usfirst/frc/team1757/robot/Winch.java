/**
 * 
 */
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1757.robot.Constants;

/**
 * @author Larry Tseng
 *
 */
public class Winch {

	double winchSpeed;
	boolean isWinching;
	
	static CANTalon talon6, talon7;
	//static Servo servo;
	
	DirectWinch directWinch;
	PIDWinch pidWinch;
	WinchBase winch;
	
	public Winch(double winchSpeed, boolean isWinching, winchTypes winchType) {
		this.winchSpeed = winchSpeed;
		this.isWinching = isWinching;
		
		talon6 = new CANTalon(6);
		talon7 = new CANTalon(7);
		
		talon6.setInverted(true);
		talon6.enableBrakeMode(false);
		//servo = new Servo(1);
		
		directWinch = new DirectWinch(talon6, talon7);
		pidWinch = new PIDWinch(talon6, talon7, 0, .04, 0.0, 0.0); //TODO Modify PID constants
		setWinchType(winchType);
	}
	
	public enum winchTypes {
		DirectWinch, PIDWinch;
	}
	
	public void printWinchMessages(Joystick gamepad) {
		SmartDashboard.putBoolean("Winch-GoingUp?", isWinching);
		SmartDashboard.putNumber("winchSpeed", winchSpeed);
		SmartDashboard.putNumber("Winch-leftMotorCurrent", talon6.getOutputCurrent());
		SmartDashboard.putNumber("Winch-rightMotorCurrent", talon7.getOutputCurrent());
	}
	
	public void setWinchType(winchTypes winchType) {
		switch (winchType) {
		case DirectWinch:
			winch = directWinch;
			break;
		case PIDWinch:
			winch = pidWinch;
			break;
		default:
			winch = directWinch;
			break;
		}
	}
	
	public void doWinch(Joystick gamepad) {
		
		if (gamepad.getRawButton(Constants.BUTTON_BACK)) {
			winch.changeOutput(-.01);
			System.out.println("Decrementing winchSpeed...");
			Timer.delay(0.1);
			Math.max(-1, winchSpeed);
		} else if (gamepad.getRawButton(Constants.BUTTON_START)) {
			winch.changeOutput(.01);
			System.out.println("Incrementing winchSpeed...");
			Timer.delay(0.1);
			Math.min(1, winchSpeed);
		}

		if (gamepad.getRawButton(Constants.BUTTON_X)) {
			winch.enable();
			winch.run();
		} else {
			winch.stop();
			winch.disable();
		}
	}
}
//servo.set((gamepad.getRawAxis(AXIS_X)+1)/2);