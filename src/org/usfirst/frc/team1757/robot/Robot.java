/*
 * TODO: ADD HOTKEYS FOR CONTROLLER CLIMBSPEED (POV for presets: +.5, +.25, -.25, -.5)
 */
package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	CANTalon talon0, talon1, talon2, talon3, talon4, talon5, talon6, talon7;

	RobotDrive drive;

	double climbSpeed;
	boolean isRunning;
	//boolean previouslyYPressed;

	Joystick gamepad;
	public static final int
	BUTTON_A = 2, BUTTON_B = 3, BUTTON_X = 1,
	BUTTON_Y = 4, BUTTON_LB = 5, BUTTON_RB = 6,
	BUTTON_LT = 7, BUTTON_RT = 8,
	BUTTON_BACK = 9, BUTTON_START = 10,
	AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 2, AXIS_RSY = 3;

	//Servo servo;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		isRunning = false;
		climbSpeed = 0;

		talon0 = new CANTalon(0);
		talon1 = new CANTalon(1);
		talon2 = new CANTalon(2);
		talon3 = new CANTalon(3);
		talon4 = new CANTalon(4);
		talon5 = new CANTalon(5);
		talon6 = new CANTalon(6);
		talon7 = new CANTalon(7);

		talon5.enableBrakeMode(true);
		talon5.setInverted(true);

		//drive = new RobotDrive(talon0, talon1, talon2, talon3);
		gamepad = new Joystick(0);
		//previouslyYPressed = false;

		//servo = new Servo(1);
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
		//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
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
		while(isEnabled() && isOperatorControl()) {

			//System.out.println("Teleop enabled");

			//SmartDashboard.putNumber("Left Axis", gamepad.getRawAxis(AXIS_Y));
			//SmartDashboard.putNumber("Right Axis", gamepad.getRawAxis(AXIS_RSY));
			SmartDashboard.putBoolean("Left Trigger", gamepad.getRawButton(BUTTON_LT));
			SmartDashboard.putBoolean("Right Trigger", gamepad.getRawButton(BUTTON_RT));
			SmartDashboard.putNumber("ClimbSpeed" , climbSpeed);
			SmartDashboard.putBoolean("Going Up?", gamepad.getRawButton(BUTTON_Y));
			SmartDashboard.putBoolean("isRunning?", isRunning);
			//SmartDashboard.putBoolean("PreviouslyYPressed?", previouslyYPressed);

			//drive.tankDrive(gamepad.getRawAxis(AXIS_Y)*0.5, gamepad.getRawAxis(AXIS_RSY)*0.5);

			if (gamepad.getRawButton(BUTTON_A)) {
				isRunning = !isRunning;
				System.out.println("button A has been pressed - wait 1 second.");
				Timer.delay(1);
				if (isRunning) {
					System.out.println("Ready.");
				} else if (!isRunning) {
					System.out.println("Press 'A' to re-enable.");
				}
			}

			if (isRunning) {
				if (gamepad.getRawButton(BUTTON_RT)) {
					System.out.println("Incrementing climbSpeed.");
					climbSpeed += 0.01;
					Timer.delay(.1);
					climbSpeed = Math.min(1, climbSpeed); //This prevents climbSpeed from going above 1.0
				} else if (gamepad.getRawButton(BUTTON_LT)) {
					climbSpeed -= 0.01;
					System.out.println("Decrementing climbSpeed.");
					Timer.delay(.1);
					//climbSpeed = Math.max(0, climbSpeed); //This prevents climbSpeed from going below 0.0 
				}

				if (gamepad.getRawButton(BUTTON_Y)) {
					talon5.set(climbSpeed);
					//HOLD to MOVE/
				} else {
					talon5.set(0);
				}
				
				if (gamepad.getRawButton(BUTTON_X)) {
					climbSpeed = -0.1;
				}

			} else {
				talon5.set(0);
				climbSpeed = 0;
			}

			if (gamepad.getRawButton(BUTTON_B)) {
				climbSpeed = 0;
				isRunning = false;
				System.out.println("Button B has been pressed.");
				Timer.delay(1);
			}

			//servo.set((gamepad.getRawAxis(AXIS_X)+1)/2);
			//previouslyYPressed = gamepad.getRawButton(BUTTON_Y);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
