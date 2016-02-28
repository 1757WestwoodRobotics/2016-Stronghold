package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
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
		
		leftTeam = new CANTeamDrive(new CANTalon[] {frontLeftMotor, backLeftMotor});
		rightTeam = new CANTeamDrive(new CANTalon[] {frontRightMotor, backRightMotor});
			
		//leftTeam = new CANTeamDrive(new CANTalon[] {frontLeftMotor}); no grindy
		//rightTeam= new CANTeamDrive(new CANTalon[] {frontRightMotor});
		
		gyrometer = new ADXRS450_Gyro();
		gyrometer.reset();

		pidRight = new PIDController(driveSpeed, Constants.PID_.Kp, Constants.PID_.Ki,Constants.PID_.Kd, Constants.PID_.Kf, gyrometer, rightTeam);
		pidLeft = new PIDController(driveSpeed, Constants.PID_.Kp, Constants.PID_.Ki,Constants.PID_.Kd, Constants.PID_.Kf, gyrometer, leftTeam);
		
		drive = new RobotDrive(leftTeam, rightTeam);
		drive.setSafetyEnabled(false);
		rightTeam.setInverted(true);
	}

	public enum driveTypes {
		ArcadeDrive, TankDrive, PIDArcadeDrive, SimpleDrive, SimpleArcadeDrive, AutonomousDrive;
	}
	
	public void setDriveType(driveTypes driveType) {
		this.driveType = driveType;
	}

	public void printDriveMessages(Joystick gamepad) {
		SmartDashboard.putNumber("Left Axis", gamepad.getRawAxis(Constants.AXIS_Y)*0.5);
		SmartDashboard.putNumber("Right Axis", gamepad.getRawAxis(Constants.AXIS_RSY)*0.5);
		//SmartDashboard.putString("DriveType", this.driveType.toString());
		SmartDashboard.putBoolean("isDriving?", isDriving);
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
		
		drive.tankDrive(gamepad.getRawAxis(Constants.AXIS_Y)*Constants.SENSITIVITY, -gamepad.getRawAxis(Constants.AXIS_RSY)*Constants.SENSITIVITY);
	}
	
	public void doArcadeDrive(Joystick gamepad) {
		/**
		 * Calls arcade drive and gives it value of sticks, if the sticks are outside their deadzone
		 */
		double rightStick = 0;
		double leftStick = 0;
		if (gamepad.getRawAxis(Constants.AXIS_RSX)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_RSX)<-Constants.DEADZONE ) {
			rightStick = -gamepad.getRawAxis(Constants.AXIS_RSX)*Constants.SENSITIVITY;
			
		}
		if (gamepad.getRawAxis(Constants.AXIS_Y)>Constants.DEADZONE || gamepad.getRawAxis(Constants.AXIS_Y)<-Constants.DEADZONE ) {
			leftStick = -gamepad.getRawAxis(Constants.AXIS_Y)*Constants.SENSITIVITY;
			
		}
		drive.arcadeDrive(rightStick, leftStick);
		
	}
	
	public void doSimpleArcadeDrive(Joystick gamepad) {
		leftTeam.set(gamepad.getY());
		rightTeam.set(-gamepad.getY());
		
		leftTeam.set(-gamepad.getRawAxis(Constants.AXIS_RSX));
		rightTeam.set(gamepad.getRawAxis(Constants.AXIS_RSX));
	}
	
	public void doSimpleDrive(Joystick gamepad) {
		frontLeftMotor.set(gamepad.getY());
		backLeftMotor.set(gamepad.getY());
		frontRightMotor.set(-gamepad.getY());
		backRightMotor.set(-gamepad.getY());
	}
	
	public void doPIDArcadeDrive(Joystick gamepad) {
		pidLeft.setInverted(true);
		pidRight.setDrive(-gamepad.getY()*Constants.SENSITIVITY);
		pidLeft.setDrive(gamepad.getY()*Constants.SENSITIVITY);
		
		//TODO Utilize wrapper implementation of deadzone
		if (gamepad.getRawAxis(Constants.AXIS_RSX) > .1) {
			setpoint += gamepad.getRawAxis(3)*Constants.PID_.turnConstant;
			pidRight.setSetpoint(setpoint);
			pidLeft.setSetpoint(setpoint);
		}
		
		if (gamepad.getRawAxis(Constants.AXIS_RSX) < -.1) {
			setpoint += gamepad.getRawAxis(3)*Constants.PID_.turnConstant;
			pidRight.setSetpoint(setpoint);
			pidLeft.setSetpoint(setpoint);
		}
	}
	
	public void resetPIDArcadeDrive(){
		gyrometer.reset();
		setpoint = 0;
		//TODO: not necessary fuck
		pidLeft.setSetpoint(0);
		pidRight.setSetpoint(0);
		
	}
	
	public void doAutoDrive(double speed, double time) {
		setpoint = 0;
		System.out.println("starting AutoDrive");
		pidRight.setDrive(-speed);
		pidLeft.setDrive(speed);
		Timer.delay(time);
		pidRight.setDrive(0);
		pidLeft.setDrive(0);
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
		case SimpleDrive:
			doSimpleDrive(gamepad);
			break;
		case SimpleArcadeDrive:
			doSimpleArcadeDrive(gamepad);
			break;
		default: System.out.println("Drive type not selected");
			break; 
		}
	}
}
