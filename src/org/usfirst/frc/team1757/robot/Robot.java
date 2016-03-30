
package org.usfirst.frc.team1757.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * TODO: FIX BUTTONS ACCORDING TO DRIVER'S PREFERENCE!!!
 * TODO: WORK ON AUTONOMOUS MODE
 * TODO: WORK ON STRAIGHT DRIVING 
 * TODO: TEST PID (OR MASTER-SLAVE MODE)
 * TODO: CHECK VALUES OF SERVO, STRINGPOT, AND ARMS
 * !!!!: Ensure robot application calls Set() on each Talon at least once per loop
 */

public class Robot extends IterativeRobot {
	Gamepad gamepad;
	Winch winch; 	
	Breach breach;
	Climb climb;
	Drive drive;
	Commands commands;
	//Gamepad buttonBox;

	int session;
	Image frame;

	final String defaultAuto = "Default";
	final String lowbarAuto = "Cross Low Bar";
	final String sallyportAuto = "Sally Port Auto";
	final String rockwallAuto = "Rock Wall Auto";
	final String moatAuto = "Moat Auto";
	final String drawbridgeAuto = "Drawbridge Auto";
	final String portcullisAuto = "Portcullis Auto";
	String autoSelected;
	SendableChooser chooser;


	public void robotInit() {
		gamepad = new Gamepad(1);

		//winch = new Winch(0.0, false, Winch.winchTypes.DirectWinch);
		breach = new Breach(Constants.BreachArm.ARM_SPEED, false);
		//climb = new Climb(0.0, false);
		drive = new Drive(0.0, false, Drive.driveTypes.ArcadeDrive);
		//buttonBox = new Gamepad(0);

		Constants.setConstants(Constants.GamepadTypes.Xbox360);

		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("Low Bar Auto", lowbarAuto);
		chooser.addObject("Sally Port Auto", sallyportAuto);
		chooser.addObject("Rock Wall Auto", rockwallAuto);
		chooser.addObject("Moat Auto", moatAuto);
		chooser.addObject("Drawbridge Auto", drawbridgeAuto);
		chooser.addObject("Portcullis Auto", portcullisAuto);
		SmartDashboard.putData("Auto choices", chooser);

		commands = new Commands(drive, breach);

		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera("cam2", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
	}

	public void autonomousInit() {
		System.out.println("AUTO mode has started.");
		drive.setDriveType(Drive.driveTypes.PIDArcadeDrive); 

		autoSelected = (String) chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);

		drive.gyrometer.reset();
		
		Autonomous.isRunning = false;

	}

	public void autonomousPeriodic() {

		
		autoSelected = (String) chooser.getSelected();
		switch(autoSelected) {
		case lowbarAuto:
			Autonomous.crossLowBar(drive, breach);
			break;
		case defaultAuto:
			Autonomous.doNothing();
			break;
		case rockwallAuto:
			Autonomous.crossRockWall(drive);
			break;
		default:
			System.out.println("No Auto Selected");
			break;
		}
	}

	public void teleopInit() {
		drive.setDriveType(Drive.driveTypes.ArcadeDrive);
	}

	public void teleopPeriodic() {
	NIVision.IMAQdxStartAcquisition(session);
		//Move to teleop init??
		while(isEnabled() && isOperatorControl()) {
			NIVision.IMAQdxGrab(session, frame, 1);
			CameraServer.getInstance().setImage(frame);

			/**
			 * Uses both sticks, X, and B
			 */
			drive.doDrive(gamepad);

			/**
			 * Uses the triggers LB and RB (for Cycle mode and reset PID respectively)
			 */
			breach.doBreach(gamepad);

			/**
			 * Uses the DPAD and Y button
			 */
			//climb.doClimb(gamepad);

			/**
			 * Uses Start, Back, and A
			 */
			//winch.doWinch(gamepad);

			/**
			 * Uses button 4 for drawbridge, 
			 **/
			//commands.doCommand(buttonBox);
		}
	NIVision.IMAQdxStopAcquisition(session);
	}

	public void testInit() {

	}

	public void testPeriodic() {

	}

}
