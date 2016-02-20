/**
 * 
 */
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Larry Tseng
 *
 */
public class Winch {
	
	double winchSpeed;
	boolean winchRunning;
	
	public void winchInit() {
		winchSpeed = 0;
	}
	
	public void winchMessages(Joystick gamepad) {
		gamepad.getRawButton(Robot.BUTTON_A);
		SmartDashboard.putBoolean("Winch-LeftTrigger", Robot.gamepad.getRawButton(Robot.BUTTON_LT));
		SmartDashboard.putBoolean("Winch-RightTrigger", Robot.gamepad.getRawButton(Robot.BUTTON_RT));
		SmartDashboard.putNumber("motorLeftCurrent", Robot.talon6.getOutputCurrent());
		SmartDashboard.putNumber("motorRightCurrent", Robot.talon7.getOutputCurrent());
		SmartDashboard.putBoolean("Winch-GoingUp?", Robot.gamepad.getRawButton(Robot.BUTTON_X));
		SmartDashboard.putNumber("winchSpeed", winchSpeed);
	}
	
	public void winchPower(Joystick gamepad) {
		
		if (gamepad.getRawButton(Robot.BUTTON_LT)) {
			winchSpeed -= 0.1;
			System.out.println("Decrementing winchSpeed...");
			Timer.delay(0.1);
			Math.max(-1, winchSpeed);
		} else if (gamepad.getRawButton(Robot.BUTTON_RT)) {
			winchSpeed += 0.1;
			System.out.println("Incrementing winchSpeed...");
			Timer.delay(0.1);
			Math.min(1, winchSpeed);
		}
		winchSet();
	}
	
	public void winchSet() {
		if (Robot.gamepad.getRawButton(Robot.BUTTON_X)) {
			Robot.talon6.set(winchSpeed);
			Robot.talon7.set(winchSpeed);
		} else {
			Robot.talon6.set(0);
			Robot.talon7.set(0);
		}
	}
}
