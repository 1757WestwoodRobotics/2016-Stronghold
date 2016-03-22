
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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

	Gamepad gamepad;
	Winch winch; 	
	Breach breach;
	Climb climb;
	Drive drive;


	public void robotInit() {
		isRunning = true;
		gamepad = new Gamepad(1, );

		//winch = new Winch(0.0, false, Winch.winchTypes.DirectWinch);
		breach = new Breach(Constants.BreachArm.ARM_SPEED, false);
		climb = new Climb(0.0, false);

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
		
		SmartDashboard.putNumber("PID drive right", drive.pidRight.get());
		SmartDashboard.putNumber("PID drive left", drive.pidLeft.get());
		SmartDashboard.putNumber("Front right motor", Drive.frontRightMotor.get());
		SmartDashboard.putNumber("Front left motor", Drive.frontLeftMotor.get());
		SmartDashboard.putNumber("back right motor", Drive.backRightMotor.get());
		SmartDashboard.putNumber("back left motor", Drive.backLeftMotor.get());
	}
	public void teleopInit() {
		isRunning = true;
		drive.setDriveType(Drive.driveTypes.ArcadeDrive);
	}
	public void teleopPeriodic() {
		while(isEnabled() && isOperatorControl()) {
			SmartDashboard.putBoolean("Robot-isRunning?", isRunning);
			SmartDashboard.putString("DriveType", drive.driveType.toString());
			//TODO: Change these to bindings
			//TODO: TESTING CODE
			
			if (gamepad.getRawButton(Constants.BUTTON_B)) {
				if (drive.driveType == Drive.driveTypes.PIDArcadeDrive) {
					drive.driveType = Drive.driveTypes.PIDDrive;
					System.out.println("PIDDrive in use");
				} else if (drive.driveType == Drive.driveTypes.PIDDrive) {
					drive.driveType = Drive.driveTypes.PIDArcadeDrive;
					System.out.println("PIDArcade Drive in use");
				} else {
					drive.driveType = Drive.driveTypes.PIDArcadeDrive;
				}
				Timer.delay(.5);
			}
			if (gamepad.getRawButton(Constants.BUTTON_X)){
				drive.resetPIDArcadeDrive();
			}
			
			if (isRunning) {
				drive.printDriveMessages(gamepad);
				drive.doDrive(gamepad);
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
