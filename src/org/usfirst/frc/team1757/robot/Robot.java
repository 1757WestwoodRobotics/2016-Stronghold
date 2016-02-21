
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1757.robot.Constants;

/**
 * @author Larry Tseng
 * 
 */
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser chooser;

	boolean isRunning;
	
	Joystick gamepad;
	
	Winch winch; 
	Breach breach;
	Climb climb;
	Drive drive;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		isRunning = false;

		gamepad = new Joystick(0);
		
		winch = new Winch(0.0, false);
		breach = new Breach(0.0, false);
		climb = new Climb(0.0, false);
		drive = new Drive(0.0, false);
		
		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
	}


	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {

		autoSelected = (String) chooser.getSelected();
		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		switch(autoSelected) {
		case customAuto:
			//Put custom auto code here   
			break;
		case defaultAuto:
		default:
			//Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		//
		while(isEnabled() && isOperatorControl()) {

			if (gamepad.getRawButton(Constants.BUTTON_A)) {
				isRunning = !isRunning;
				System.out.println("button A has been pressed - wait 1 second.");
				Timer.delay(1);
				
				if (isRunning) {
					System.out.println("Ready.");
				} else {
					didStop();
					System.out.println("Robot didStop()... Press 'A' to re-enable.");
				}
			}
			
			if (isRunning) {
				
				drive.doDrive(gamepad);
				breach.doBreach(gamepad);
				climb.doClimb(gamepad);
				winch.doWinch(gamepad);
				
				printRobotMessages();
	
				if (gamepad.getRawButton(Constants.BUTTON_B)) {
					didStop();
					System.out.println("Button B has been pressed. Press A to re-enable.");
				}
			}
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}
	
	public void printRobotMessages() {
		SmartDashboard.putBoolean("Robot-isRunning?", isRunning);
		SmartDashboard.putString("Winch", "LT: Down, RT: Up, X: Act");
		SmartDashboard.putString("Breach", "BACK: Up, START: Down, LS: Act");
		SmartDashboard.putString("Climb", "POV0: Up, POV180: Down, Y: Act");
		SmartDashboard.putString("Drive", "STICK_Y: Down, RT: STICK_RSY");
		breach.printBreachMessages(gamepad);
		climb.printClimbMessages(gamepad);
		winch.printWinchMessages(gamepad);
		drive.printDriveMessages(gamepad);
	}
	
	public void didStop() {
		isRunning = false;
		
		winch.winchSpeed = 0;
		winch.isWinching = false;
		winch.talon6.set(0);
		winch.talon7.set(0);

		breach.breachSpeed = 0;
		breach.isBreaching = false;
		breach.talon4.set(0);
		
		climb.climbSpeed = 0;
		climb.isClimbing = false;
		climb.talon5.set(0);
		
		drive.driveSpeed = 0;
		drive.isDriving = false;
		drive.talon0.set(0);
		drive.talon1.set(0);
		drive.talon2.set(0);
		drive.talon3.set(0);
		
		printRobotMessages();
		
		System.out.println("Robot didStop()...");
		Timer.delay(1);
	}
}
