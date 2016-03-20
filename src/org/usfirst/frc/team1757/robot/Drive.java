package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

public class Drive {

	double driveSpeed;
	boolean isDriving;
	
	RobotDrive drive;

    static CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    PIDController pidRight, pidLeft;
    CANTeamDrive leftTeam, rightTeam, pidTeam;
    PseudoPIDOutput leftOut, rightOut;

    double setpoint, initialTurn;
    
    driveTypes driveType;
    ADXRS450_Gyro gyrometer;
	
	public Drive(double driveSpeed, boolean isDriving, driveTypes driveType) {
		this.driveSpeed = driveSpeed;
		this.isDriving = isDriving;
		
		setpoint = 0.0;
		initialTurn = 10;
		this.driveType = driveType;
		
		frontLeftMotor = new CANTalon(0);
		backLeftMotor = new CANTalon(1);
		frontRightMotor = new CANTalon(2);
		backRightMotor = new CANTalon(3);
		frontLeftMotor.enableBrakeMode(false); 
		backLeftMotor.enableBrakeMode(false);
		frontRightMotor.enableBrakeMode(false); 
		backRightMotor.enableBrakeMode(false);
		
		leftTeam = new CANTeamDrive(frontLeftMotor, backLeftMotor);
		rightTeam = new CANTeamDrive(frontRightMotor, backRightMotor);
		drive = new RobotDrive(leftTeam, rightTeam);
		drive.setSafetyEnabled(false);
		leftTeam.setInverted(false);
		rightTeam.setInverted(true);
		
		gyrometer = new ADXRS450_Gyro();
		gyrometer.reset();

		leftOut = new PseudoPIDOutput();
		rightOut = new PseudoPIDOutput();
		
		pidRight = new PIDController(Constants.PID_.Kp, Constants.PID_.Ki,Constants.PID_.Kd, Constants.PID_.Kf, gyrometer, rightOut);
		pidLeft = new PIDController( Constants.PID_.Kp, Constants.PID_.Ki,Constants.PID_.Kd, Constants.PID_.Kf, gyrometer, leftOut);
	}

	public enum driveTypes {
		ArcadeDrive, TankDrive, PIDArcadeDrive, PIDDrive, SimpleDrive, SimpleTankDrive, AutonomousDrive;
	}
	
	public void setDriveType(driveTypes driveType) {
		this.driveType = driveType;
	}

	public void printDriveMessages(Joystick gamepad) {
		SmartDashboard.putNumber("Left Axis", gamepad.getRawAxis(Constants.AXIS_Y));
		SmartDashboard.putNumber("Right Axis", gamepad.getRawAxis(Constants.AXIS_RSY));
		SmartDashboard.putBoolean("isDriving?", isDriving);
		SmartDashboard.putNumber("left Team", leftTeam.get());
		SmartDashboard.putNumber("right Team", rightTeam.get());
		System.out.println("left Team " + leftTeam.get());
		System.out.println("right Team" + rightTeam.get());
		SmartDashboard.putData("pidLeft", pidLeft);
		SmartDashboard.putData("pidRight", pidRight);
		SmartDashboard.putData("Gyro", gyrometer);
		SmartDashboard.putNumber("Angle", gyrometer.getAngle());
		
		/*SmartDashboard.putNumber("talon0-motorCurrent", talon0.getOutputCurrent());
		SmartDashboard.putNumber("talon1-motorCurrent", talon1.getOutputCurrent());
		SmartDashboard.putNumber("talon2-motorCurrent", talon2.getOutputCurrent());
		SmartDashboard.putNumber("talon3-motorCurrent", talon3.getOutputCurrent());*/
	
	}

	public void doTankDrive(Joystick gamepad) {
		
		drive.tankDrive(gamepad.getRawAxis(Constants.AXIS_Y)*Constants.SENSITIVITY, gamepad.getRawAxis(Constants.AXIS_RSY)*Constants.SENSITIVITY);
	}
	
	public void doArcadeDrive(Joystick gamepad) {
		/**
		 * Calls arcade drive and gives it value of sticks, if the sticks are outside their deadzone
		 */
		double rightStick = 0;
		double leftStick = 0;
		if (gamepad.getRawAxis(Constants.AXIS_RSX)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_RSX)<-Constants.DEADZONE ) {
			rightStick = gamepad.getRawAxis(Constants.AXIS_RSX)*Constants.SENSITIVITY;
			
		}
		if (gamepad.getRawAxis(Constants.AXIS_Y)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_Y)<-Constants.DEADZONE ) {
			leftStick = gamepad.getRawAxis(Constants.AXIS_Y)*Constants.SENSITIVITY;
			
		}
		drive.arcadeDrive(rightStick, leftStick);
		
	}
	
	public void doSimpleTankDrive(Joystick gamepad) {
		if (gamepad.getRawAxis(Constants.AXIS_RSX)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_RSY)<-Constants.DEADZONE ) {
			rightTeam.set(gamepad.getRawAxis(Constants.AXIS_RSY));
			
		}
		else {
			rightTeam.set(0);
		}
		if (gamepad.getRawAxis(Constants.AXIS_Y)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_Y)<-Constants.DEADZONE ) {
			leftTeam.set(gamepad.getY());
			
		}
		else{
			leftTeam.set(0);
		}
		
		
	}
	
	public void doSimpleDrive(Joystick gamepad) {
		frontLeftMotor.set(gamepad.getY());
		backLeftMotor.set(gamepad.getY());
		frontRightMotor.set(gamepad.getY());
		backRightMotor.set(gamepad.getY());
	}
	public void doPIDDrive(){
		pidRight.setSetpoint(setpoint);
		pidLeft.setSetpoint(setpoint);
		rightTeam.set(rightOut.getOutput()); //TODO: INVERSION!!! Notfication
		leftTeam.set(-leftOut.getOutput());
	}
	
	public void doPIDArcadeDrive(Joystick gamepad) {//TODO: Only settting while axis input
		if (gamepad.getRawAxis(Constants.AXIS_RSX)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_RSY)<-Constants.DEADZONE ) {
			rightTeam.set(rightOut.getOutput()+gamepad.getY()*Constants.SENSITIVITY);
		}
		else {
			rightTeam.set(0);
		}
		if (gamepad.getRawAxis(Constants.AXIS_Y)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_Y)<-Constants.DEADZONE ) {
			leftTeam.set(-leftOut.getOutput()+gamepad.getY()*Constants.SENSITIVITY); //TODO: INVERSION ALERT
			//
		}
		else{
			leftTeam.set(0);
		}
		
		
		if (gamepad.getRawAxis(Constants.AXIS_RSX) > .1 || gamepad.getRawAxis(Constants.AXIS_RSX) < -.1) {
			setpoint += gamepad.getRawAxis(Constants.AXIS_RSX)*Constants.PID_.turnConstant;
			pidRight.setSetpoint(setpoint);
			pidLeft.setSetpoint(setpoint);
		}
	}
	
	public void resetPIDArcadeDrive(){
		gyrometer.reset();
		setpoint = 0;
		pidLeft.setSetpoint(0);
		pidRight.setSetpoint(0);
	}
	
	public void doAutoDrive(double speed, double time) {
		setpoint = 0;
		System.out.println("starting AutoDrive");
		leftTeam.set(speed+leftOut.getOutput());
		rightTeam.set(speed+rightOut.getOutput());
		Timer.delay(time);
		rightTeam.set(0);
		leftTeam.set(0);
	}

	
	public void doDrive(Joystick gamepad) {
		switch (driveType) {
		case ArcadeDrive: 
			doArcadeDrive(gamepad); 
			break;
		case TankDrive: 
			doTankDrive(gamepad); 
			break;
		case PIDArcadeDrive: 
			pidLeft.enable();
			pidRight.enable();
			doPIDArcadeDrive(gamepad); 
			break;
		case PIDDrive:
			pidLeft.enable();
			pidRight.enable();
			doPIDDrive();
		case SimpleDrive:
			doSimpleDrive(gamepad);
			break;
		case SimpleTankDrive:
			doSimpleTankDrive(gamepad);
			break;
		default: System.out.println("Drive type not selected");
			break; 
		}
	}
}
