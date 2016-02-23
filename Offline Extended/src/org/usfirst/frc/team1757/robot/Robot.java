
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author Larry Tseng
 * 
 */
public class Robot extends IterativeRobot {

	boolean isRunning;
	
	Joystick gamepad;
	edu.wpi.first.wpilibj.CANTalon talon;

	//Winch winch; 
	//Breach breach;
	Climb climb;
	
	Drive drive;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		isRunning = true;
		gamepad = new Joystick(0);
		
		talon = new edu.wpi.first.wpilibj.CANTalon(1);
		
		//winch = new Winch(0.0, false, Winch.winchTypes.DirectWinch);
		//breach = new Breach(0.0, false);
		climb = new Climb(0.0, false);
		
		drive = new Drive(0.0, false, Drive.driveTypes.ArcadeDrive);
		
		Constants.setConstants(Constants.GamepadTypes.Xbox360);
		
	}

	public void autonomousInit() {
		System.out.println("AUTO mode has started.");
		//drive.setDriveType(Drive.driveTypes.);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		while(isEnabled() && isAutonomous()) {
			//Autonomous.executeAutonomous(Defenses.LOW_BAR, drive);
			System.out.println("Robot is autonomously driving");
		}
	}

	/**
	 * This function is called 
	 */
	public void teleopInit() {
		isRunning = true;
		drive.setDriveType(Drive.driveTypes.ArcadeDrive);
	}
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		while(isEnabled() && isOperatorControl()) {
			SmartDashboard.putBoolean("Robot-isRunning?", isRunning);
			//drive.doPIDArcadeDrive(gamepad);

			/* if (gamepad.getRawButton(Constants.BUTTON_START)) {
				isRunning = !isRunning;
				System.out.println("button START has been pressed - wait 1 second.");
				Timer.delay(1);
				
				if (isRunning) {
					System.out.println("Ready.");
				} else {
					didStop();
					System.out.println("Robot didStop()... Press 'START' to re-enable.");
				}
			} */
			
		/* 	if (gamepad.getRawButton(Constants.BUTTON_LB)) {
				if (drive.driveType == Drive.driveTypes.ArcadeDrive) {
					drive.driveType = Drive.driveTypes.PIDArcadeDrive;
				} else if (drive.driveType == Drive.driveTypes.PIDArcadeDrive) {
					drive.driveType = Drive.driveTypes.ArcadeDrive;
				} else {
					drive.driveType = Drive.driveTypes.ArcadeDrive;
				}
			} */
			
			if (isRunning) {
				drive.printDriveMessages(gamepad);
				drive.doDrive(gamepad);
				//breach.printBreachMessages(gamepad);
				//breach.doBreach(gamepad);
				
				climb.printClimbMessages(gamepad);
				climb.doClimb(gamepad);
				
				//winch.printWinchMessages(gamepad);
				//winch.doWinch(gamepad);
				
				/* if (gamepad.getRawButton(Constants.BUTTON_B)) {
					didStop();
					System.out.println("Button B has been pressed. Press START to re-enable.");
				} */
			}
		}
	}

	public void testInit() {
		
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	public void didStop() {
		//isRunning = false;
		
		/*winch.winchSpeed = 0;
		winch.isWinching = false;
		Winch.talon6.set(0);
		Winch.talon7.set(0);*/
		
		/*
		breach.breachSpeed = 0;
		breach.isBreaching = false;
		Breach.talon4.set(0);
		*/
		
		climb.climbSpeed = 0;
		climb.isClimbing = false;
		Climb.talon5.set(0);
		
		drive.driveSpeed = 0;
		drive.isDriving = false;
		Drive.frontLeftMotor.set(0);
		Drive.backRightMotor.set(0);
		Drive.frontRightMotor.set(0);
		Drive.backLeftMotor.set(0);
		
		System.out.println("Robot didStop()...");
		Timer.delay(1);
	}
}
