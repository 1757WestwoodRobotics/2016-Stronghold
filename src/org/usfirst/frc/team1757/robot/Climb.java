/**
 * 
 */
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1757.robot.Constants;

/**
 * @author Larry Tseng
 *
 */
public class Climb {
	
	double climbSpeed;
	boolean isClimbing;

	static CANTalon talon5;

	public Climb(double climbSpeed) {
		this.climbSpeed = climbSpeed;
		
		talon5 = new CANTalon(5);
		talon5.enableBrakeMode(true);
		}

	public void doClimb(Gamepad gamepad) {
	
		if (gamepad.getTrigger(Constants.BUTTON_LT)) {
			climbSpeed = .2;
			System.out.println("Speed set to: " + climbSpeed);
		}
		else if (gamepad.getTrigger(Constants.BUTTON_RT)) {
			climbSpeed = .6;
			System.out.println("Speed set to: " + climbSpeed);
		}
		if (gamepad.getRawButton(Constants.BUTTON_Y)) {
			talon5.set(climbSpeed);	
		}
		else if (gamepad.getRawButton(Constants.BUTTON_A))
			talon5.set(-climbSpeed);
		else {
			talon5.set(0);
			isClimbing = false;
		}	
		
		SmartDashboard.putNumber("ClimbArm-ClimbSpeed" , climbSpeed);
		SmartDashboard.putBoolean("ClimbArm-isClimbing?", isClimbing);
	}
}