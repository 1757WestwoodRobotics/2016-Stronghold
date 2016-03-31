/**
 * 
 */
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
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
		talon5.setInverted(true);
		}

	public void doClimb(Joystick gamepad) {
		/**
		 * TODO: FIX BUTTONS ACCORDING TO DRIVER PREFERENCE
		 * TODO: CHANGE SPEED TO A CONSTANT VALUE AND USE CONSTANTS FOR OTHERS AS WELL
		 * START WITH BREACHING ARM VALUE
		 
		 This is speed adjustment
		if (gamepad.getPOV(0) == 180) {
			climbSpeed -= 0.01;
			System.out.println("Decrementing climbSpeed...");
			Timer.delay(.1);
			climbSpeed = Math.max(-1, climbSpeed);
		}	else if (gamepad.getPOV(0) == 0) { 
			climbSpeed += 0.01;
			System.out.println("Incrementing climbSpeed...");
			Timer.delay(.1); 
			climbSpeed = Math.min(1, climbSpeed);
		} else if (gamepad.getPOV(0) == 90) {
			climbSpeed = .5;
			System.out.println("climbSpeed is .5...");
			Timer.delay(.1); 
			climbSpeed = Math.min(1, climbSpeed);

		} else if (gamepad.getPOV(0) == 270) {
			climbSpeed = -.25;
			System.out.println("climbSpeed is -.25...");
			Timer.delay(.1); 
			climbSpeed = Math.min(1, climbSpeed);

		}
		**/
		
		if (gamepad.getRawButton(Constants.BUTTON_Y)) {
			talon5.set(climbSpeed);
			isClimbing = true;
			//HOLD to MOVE
		} else {
			talon5.set(0);
			isClimbing = false;
		}	
		
		SmartDashboard.putNumber("ClimbArm-ClimbSpeed" , climbSpeed);
		SmartDashboard.putBoolean("ClimbArm-isClimbing?", isClimbing);
	}
}