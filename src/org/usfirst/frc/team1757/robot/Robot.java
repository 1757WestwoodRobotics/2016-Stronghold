
package org.usfirst.frc.team1757.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
		gamepad = new Gamepad(3);
		winch = new Winch(0.15);
		breach = new Breach(Constants.BreachArm.ARM_SPEED, false);
		climb = new Climb(0.2);
		drive = new Drive(0.0, Drive.driveTypes.ArcadeDrive);
		//buttonBox = new Gamepad(0);

		Constants.setConstants(Constants.GamepadTypes.Logitech_DualAction);

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
			Autonomous.crossLowBar(drive, winch);
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
		while(isEnabled() && isOperatorControl()) {
			try{
				NIVision.IMAQdxGrab(session, frame, 1);
	        	}
	        	catch(Exception e){
	        		System.out.println(e);
	        	}
		
			CameraServer.getInstance().setImage(frame);

			/**
			 * Uses both sticks, X, and B
			 */
			drive.doDrive(gamepad);

			/**
			 * Uses the triggers LB and RB (for Cycle mode and reset PID respectively)
			 */
			//breach.doBreach(gamepad);

			/**
			 * Uses the DPAD and Y button
			 */
			//climb.doClimb(gamepad);

			/**
			 * Uses DPAD and triggers
			 */
			winch.doWinch(gamepad);

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
