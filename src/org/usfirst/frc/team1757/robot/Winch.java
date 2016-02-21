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
	
	CANTalon talon6, talon7;
	//static Servo servo;
	
	public Winch(double winchSpeed, boolean isWinching) {
		this.winchSpeed = winchSpeed;
		this.isWinching = isWinching;
		
		talon6 = new CANTalon(6);
		talon7 = new CANTalon(7);
		
		talon6.setInverted(true);
		talon6.enableBrakeMode(false);
		//servo = new Servo(1);
	}
	
	public void printWinchMessages(Joystick gamepad) {
		SmartDashboard.putBoolean("Winch-GoingUp?", isWinching);
		SmartDashboard.putNumber("winchSpeed", winchSpeed);
		SmartDashboard.putNumber("Winch-leftMotorCurrent", talon6.getOutputCurrent());
		SmartDashboard.putNumber("Winch-rightMotorCurrent", talon7.getOutputCurrent());
	}
	
	public void doWinch(Joystick gamepad) {
		
		if (gamepad.getRawButton(Constants.BUTTON_LT)) {
			winchSpeed -= 0.01;
			System.out.println("Decrementing winchSpeed...");
			Timer.delay(0.1);
			Math.max(-1, winchSpeed);
		} else if (gamepad.getRawButton(Constants.BUTTON_RT)) {
			winchSpeed += 0.01;
			System.out.println("Incrementing winchSpeed...");
			Timer.delay(0.1);
			Math.min(1, winchSpeed);
		}

		if (gamepad.getRawButton(Constants.BUTTON_X)) {
			talon6.set(winchSpeed);
			talon7.set(winchSpeed);
			isWinching = true;
		} else {
			talon6.set(0);
			talon7.set(0);
			isWinching = false;
		}
	}
}
//servo.set((gamepad.getRawAxis(AXIS_X)+1)/2);