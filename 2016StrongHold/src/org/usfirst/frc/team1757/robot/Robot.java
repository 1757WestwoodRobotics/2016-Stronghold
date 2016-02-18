package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {    
    /**
	* Declare any variables here including both fields and instances, but do not define (unless it is constant, in which case it should go in a separate file).
	* This space is the first code executed by the robot, we declare here to avoid any accessibility issues as this area is accessible to all parts of the file below
	* Although you can initialize volatile variables here, do it in robotInit for good practice.
	* You can declare and define constant variables here, but for real projects we put them in the "Constants" file
	* Remember that if you need any more global variables, you have to declare here and define elsewhere
	*/
	
	//The most basic things you can have, a Joystick object and an object for each motor
	Joystick gamepad;
	CANTalon motorLeft, motorRight;
    
	//If you want to play around some and find out if motor inversion carries over between classes, try out the TeamDrive/ CANTeamDrive classes.
	//If you don't just keep them commented out
	//CANTeamDrive motorTeam;
    
    double motorOutput, averageCurrent;
	boolean isRunning, isRecording;
	ArrayList<Double> motorCurrentReadings; //You're gonna have to import this; I haven't memorized the package
	
	//See how I said not to do this above? We'll I'm doing it anyway to make things easier to work with
	//These are the button codes for the Logitech gamepad while it is in "DualAction" mode (which is what we are almost always in), you can find them in the DriverStation USB tab
	public static final int
    BUTTON_A = 2, BUTTON_B = 3, BUTTON_X = 1,
    BUTTON_Y = 4, BUTTON_LB = 5, BUTTON_RB = 6,
    BUTTON_RT = 7, BUTTON_LT = 8, 
    BUTTON_BACK = 9, BUTTON_START = 10,
    AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 2, AXIS_RSY = 3;

    public void robotInit() {
		/**
		* I'm sure you can figure it out by the name, but this is called when the code is loaded (before enabling) which is meant for initializing. 
        * There is definitely a proper standard for these comments that I should be following, but I think I'm just gonna use whichever I please
		* You should also look up the standards for camel case because I sure as hell haven't
		*/
		gamepad = new Joystick(0);
		motorLeft = new CANTalon(1);
		motorRight = new CANTalon(2);
		motorLeft.setInverted(true);
		//motorTeam = new TeamDrive(new CANTalon[] {motorLeft, motorRight}); //Getting fancy with an array initialization as a parameter. I know, I'm cool
		
		motorCurrentReadings = new ArrayList<>();
		
		//Here's that volatile variable initialization I was talking about
		motorOutput = 0.0;
		averageCurrent = 0.0;
		isRunning = false;
		isRecording = false;
	}
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard.
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	while (isOperatorControl() && isEnabled()){
			/**
			* Believe it or not, "teleopPeriodic" is called "periodically" during "teleop" mode; however, it is easier to control how it works (and make it operate more safely or something) with this here while loop.
			* Not much else to say here, but you should try fooling around with the SmartDashboard. It operates with either an SQL database or something really similar to an SQL database, so entries are given an identifying key (string) and a value.
			* I'm doing this from memory and I don't remember the keys that I had in the original code, so try to remove the dead parts of the livewindow.
			* 
			* I'm kind of curious to how the average current of the left side will compare to that of the right side and find out if one motor is more efficient than the other. If you have time try it out.
			*/
			
    		SmartDashboard.putNumber("motorOutput", motorOutput);
    		SmartDashboard.putNumber("motorLeftCurrent", motorLeft.getOutputCurrent());
			SmartDashboard.putNumber("motorRightCurrent", motorRight.getOutputCurrent());
			SmartDashboard.putNumber("motorCurrentAverage", averageCurrent);
			SmartDashboard.putBoolean("isRunning", isRunning);
			SmartDashboard.putBoolean("isRecording", isRecording);
    		
			if (isRunning) {
				motorLeft.set(motorOutput);
				motorRight.set(motorOutput);
				
			}
			
			if (isRecording) {
				//Adds to the ArrayList the average output current between the two motors. The 'D' at the end of the "2.0" is to make sure it is interpreted as a double and not an integer
				motorCurrentReadings.add((motorLeft.getOutputCurrent()+motorRight.getOutputCurrent())/2.0D);
				double sum = 0.0;
				for (double reading : motorCurrentReadings) {
					sum += reading;
				}
				averageCurrent = sum/motorCurrentReadings.size();
			}
    		
    		if (gamepad.getRawButton(BUTTON_A)) {
				//A button should toggle motors between on and off
				isRunning = !isRunning;
    		}
			
    		if (gamepad.getRawButton(BUTTON_B)) {
				//B button should toggle recording the motor current readings
				isRecording = !isRecording;
    		}
			
			if (gamepad.getRawButton(BUTTON_Y)){
				//Y button should stop the motors
				motorOutput = 0;
			}
			
			if (gamepad.getRawButton(BUTTON_LT)){
				//Left trigger should lower speed
				//This while loop goes so fast that this will be called a lot, that's why the decrement is so low
				motorOutput -= .001;
			}
			
			if (gamepad.getRawButton(BUTTON_RT)){
				//Right trigger should raise speed
				motorOutput += .001;
			}
			
			/*
			Congratulations, if you have made it this far then you are now hacker pro. Go tool around with stuff until you think you know what you are doing then find the almighty NullPointerException and debug for 20 minutes before trashing everything
			If long, boring and tedious things really tickle your fancy, check out my TeamDrive class, get a handle on what it's doing, then you can try to finish the CANTeamDrive implementation based off how the CANTalon class works.
			*/
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
