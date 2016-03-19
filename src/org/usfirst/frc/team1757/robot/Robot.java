
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * TODO: FIX BUTTONS ACCORDING TO DRIVER'S PREFERENCE!!!
 * TODO: WORK ON AUTONOMOUS MODE
 * TODO: WORK ON STRAIGHT DRIVING 
 * TODO: TEST PID (OR MASTER-SLAVE MODE)
 * TODO: CHECK VALUES OF SERVO, STRINGPOT, AND ARMS
 * !!!!: Ensure robot application calls Set() on each Talon at least once per loop
 */

public class Robot extends IterativeRobot {

	boolean isRunning;

	Joystick gamepad;

	Winch winch; 
	Breach breach;
	Climb climb;

	Drive drive;


	public void robotInit() {
		isRunning = true;
		gamepad = new Joystick(0);
/*

		winch = new Winch(0.0, false, Winch.winchTypes.DirectWinch);
		breach = new Breach(Constants.BreachArm.ARM_SPEED, false);
		climb = new Climb(0.0, false);
		
*/

		drive = new Drive(0.0, false, Drive.driveTypes.ArcadeDrive);

		Constants.setConstants(Constants .GamepadTypes.Xbox360);

	}

	public void autonomousInit() {
		System.out.println("AUTO mode has started.");
		drive.setDriveType(Drive.driveTypes.PIDArcadeDrive); 
		drive.pidLeft.enable();
		drive.pidRight.enable();
		
	}
	public void autonomousPeriodic() {
		Autonomous.crossLowBar(drive);
	}
	public void teleopInit() {
		isRunning = true;
		drive.setDriveType(Drive.driveTypes.ArcadeDrive);
	}
	public void teleopPeriodic() {
		while(isEnabled() && isOperatorControl()) {
			SmartDashboard.putBoolean("Robot-isRunning?", isRunning);
			SmartDashboard.putString("DriveType", drive.driveType.toString());
			//drive.doPIDArcadeDrive(gamepad);
//Change these to bindings
			if (gamepad.getRawButton(Constants.BUTTON_B)) {
				if (drive.driveType == Drive.driveTypes.ArcadeDrive) {
					drive.driveType = Drive.driveTypes.PIDArcadeDrive;
				} else if (drive.driveType == Drive.driveTypes.PIDArcadeDrive) {
					drive.driveType = Drive.driveTypes.ArcadeDrive;
				} else {
					drive.driveType = Drive.driveTypes.ArcadeDrive;
				}
			}
			if (gamepad.getRawButton(Constants.BUTTON_X)){
				drive.resetPIDArcadeDrive();
			}

			if (isRunning) {
				/*drive.printDriveMessages(gamepad);
				drive.doDrive(gamepad);*/

				breach.doBreach(gamepad);
				/*climb.doClimb(gamepad);
				winch.doWinch(gamepad);*/
			}

			/** Teleop Commands to Get Over Obstables using button inputs
			 * 
			 */
			//TODO: Use Teleop Commands -- runnable so you can cancel it during execution
		}
	}

	public void testInit() {

	}

	public void testPeriodic() {

	}
}
