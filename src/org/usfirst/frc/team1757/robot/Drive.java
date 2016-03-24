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
  public  ADXRS450_Gyro gyrometer;
	
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
	
	public void cycleType() {
		if (driveType == Drive.driveTypes.PIDArcadeDrive) {
			driveType = Drive.driveTypes.ArcadeDrive;
			System.out.println("ArcadeDrive in use");
			Timer.delay(1);
		} else if (driveType == Drive.driveTypes.ArcadeDrive) {
			driveType = Drive.driveTypes.PIDArcadeDrive;
			System.out.println("PIDArcade Drive in use");
			Timer.delay(1);
		} else {
			driveType = Drive.driveTypes.ArcadeDrive;
			Timer.delay(1);
		}
	}

	public void printDriveMessages(Gamepad gamepad) {
		SmartDashboard.putNumber("Left Axis", gamepad.getRawAxis(Constants.AXIS_Y));
		SmartDashboard.putNumber("Right Axis", gamepad.getRawAxis(Constants.AXIS_RSY));
		SmartDashboard.putBoolean("isDriving?", isDriving);
		SmartDashboard.putNumber("left Team", leftTeam.get());
		SmartDashboard.putNumber("right Team", rightTeam.get());
		SmartDashboard.putData("pidLeft", pidLeft);
		SmartDashboard.putData("pidRight", pidRight);
		SmartDashboard.putData("Gyro", gyrometer);
		SmartDashboard.putNumber("Angle", gyrometer.getAngle());
		SmartDashboard.putString("DriveType", driveType.toString());
		
		/*SmartDashboard.putNumber("talon0-motorCurrent", talon0.getOutputCurrent());
		SmartDashboard.putNumber("talon1-motorCurrent", talon1.getOutputCurrent());
		SmartDashboard.putNumber("talon2-motorCurrent", talon2.getOutputCurrent());
		SmartDashboard.putNumber("talon3-motorCurrent", talon3.getOutputCurrent());*/
	
	}

	public void doTankDrive(Gamepad gamepad) {
		drive.tankDrive(gamepad.getAdjAxis(Constants.AXIS_Y), gamepad.getAdjAxis(Constants.AXIS_RSY));
	}
	
	public void doArcadeDrive(Gamepad gamepad) {
		/**
		 * Calls arcade drive and gives it value of sticks, if the sticks are outside their deadzone
		 */
		double rightStick = 0;
		double leftStick = 0;

		rightStick = gamepad.getAdjAxis(Constants.AXIS_RSX);	
		leftStick = gamepad.getAdjAxis(Constants.AXIS_Y);
		//TODO: WPI LIB ERROR
		drive.arcadeDrive(-rightStick,-leftStick);
	}
	
	public void doSimpleTankDrive(Gamepad gamepad) {
		leftTeam.set(gamepad.getAdjAxis(Constants.AXIS_Y));
		rightTeam.set(gamepad.getAdjAxis(Constants.AXIS_RSY));
	}
	
	public void doSimpleDrive(Gamepad gamepad) {
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
	
	public void doPIDDrive(double outSpeed){
		//TODO: FIX THIS AND MAKE IT SAFEEE!!!
		pidRight.enable();
		pidLeft.enable();
		pidRight.setSetpoint(setpoint);
		pidLeft.setSetpoint(setpoint);
		rightTeam.set(rightOut.getOutput() + outSpeed); //TODO: INVERSION!!! Notfication
		leftTeam.set(-leftOut.getOutput() + outSpeed);
		SmartDashboard.putData("pidLeft", pidLeft);
		SmartDashboard.putData("pidRight", pidRight);
		SmartDashboard.putData("Gyro", gyrometer);
		SmartDashboard.putNumber("Angle", gyrometer.getAngle());
		SmartDashboard.putNumber("right Team", rightTeam.get());
		SmartDashboard.putNumber("left Team", leftTeam.get());
	}
	
	public void doPIDArcadeDrive(Gamepad gamepad) {
		rightTeam.set(rightOut.getOutput()+gamepad.getAdjAxis(Constants.AXIS_Y));
		leftTeam.set(-leftOut.getOutput()+gamepad.getAdjAxis(Constants.AXIS_Y));
		gamepad.getTrigger(Constants.BUTTON_LT);
		setpoint += gamepad.getAdjAxis(Constants.AXIS_RSX)*Constants.PID_.turnConstant;
		pidRight.setSetpoint(setpoint);
		pidLeft.setSetpoint(setpoint);	
	}
	
	public void resetPIDArcadeDrive(){
		gyrometer.reset();
		setpoint = 0;
		pidLeft.setSetpoint(setpoint);
		pidRight.setSetpoint(setpoint);
	}
	
	//TODO Make sure this works
	public void doAutoDrive(double speed, double time) {
		double startTime =  Timer.getFPGATimestamp();
		System.out.println("starting AutoDrive");
		while(Timer.getFPGATimestamp() < startTime + time ) {
			setpoint = 0;
			doPIDDrive(speed);
		}
		rightTeam.set(0);
		leftTeam.set(0);
		
		
	}
	
	public void doDrive(Gamepad gamepad) {
		printDriveMessages(gamepad);
		
		if (gamepad.getRawButton(Constants.BUTTON_B)) {
			cycleType();
		}
		
		if (gamepad.getRawButton(Constants.BUTTON_X)){
			resetPIDArcadeDrive();
		}
		
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
