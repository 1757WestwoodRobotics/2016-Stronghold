package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

public class Drive {

	double driveSpeed;
	boolean isDriving;
	
	RobotDrive drive;

    static CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    PIDController pidController;
    CANTeamDrive leftTeam, rightTeam, rightTeamInverted, pidTeam;

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
		
		
		//TODO Figure out inversion with this
		frontLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
		backLeftMotor.changeControlMode(TalonControlMode.Follower);
		frontRightMotor.changeControlMode(TalonControlMode.PercentVbus);
		backRightMotor.changeControlMode(TalonControlMode.Follower);
		
		
		leftTeam = new CANTeamDrive(new CANTalon[] {frontLeftMotor, backLeftMotor});
		rightTeam = new CANTeamDrive(new CANTalon[] {frontRightMotor, backRightMotor});
		rightTeamInverted = new CANTeamDrive(new CANTalon[] {frontRightMotor, backRightMotor});
		rightTeamInverted.setInverted(true);
		
		
		gyrometer = new ADXRS450_Gyro();
		gyrometer.reset();

		pidTeam = new CANTeamDrive(new CANSpeedController[] {leftTeam, rightTeamInverted});
		pidController = new PIDController(driveSpeed, .04, 0.0, 0.0, 0.0, gyrometer, pidTeam);
		
		drive = new RobotDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
	}

	public enum driveTypes {
		ArcadeDrive, TankDrive, PIDArcadeDrive, SimpleDrive, AutonomousDrive;
	}
	
	public void setDriveType(driveTypes driveType) {
		this.driveType = driveType;
	}

	public void printDriveMessages(Joystick gamepad) {
		SmartDashboard.putNumber("Left Axis", gamepad.getRawAxis(Constants.AXIS_Y)*0.5);
		SmartDashboard.putNumber("Right Axis", gamepad.getRawAxis(Constants.AXIS_RSY)*0.5);
		SmartDashboard.putBoolean("isDriving?", isDriving);
	
		/*SmartDashboard.putNumber("talon0-motorCurrent", talon0.getOutputCurrent());
		SmartDashboard.putNumber("talon1-motorCurrent", talon1.getOutputCurrent());
		SmartDashboard.putNumber("talon2-motorCurrent", talon2.getOutputCurrent());
		SmartDashboard.putNumber("talon3-motorCurrent", talon3.getOutputCurrent());*/
	
	}

	public void doTankDrive(Joystick gamepad) {
		drive.tankDrive(gamepad.getRawAxis(Constants.AXIS_Y)*Constants.SENSITIVITY, gamepad.getRawAxis(Constants.AXIS_RSY)*Constants.SENSITIVITY);
	}
	
	public void doArcadeDrive(Joystick gamepad) {
		drive.arcadeDrive(gamepad.getRawAxis(Constants.AXIS_Y)*Constants.SENSITIVITY, gamepad.getRawAxis(Constants.AXIS_X)*Constants.SENSITIVITY);
	}
	
	public void doSimpleDrive(Joystick gamepad) {
		frontLeftMotor.set(gamepad.getY());
		backLeftMotor.set(gamepad.getY());
		frontRightMotor.set(-gamepad.getY());
		backRightMotor.set(-gamepad.getY());
	}
	
	public void doPIDArcadeDrive(Joystick gamepad) {
		pidController.setDrive(gamepad.getY()*Constants.SENSITIVITY);
		
		if (gamepad.getRawButton(Constants.Gamepad_LogitechDual.BUTTON_B)) {
			pidController.disable();
			driveType = driveTypes.ArcadeDrive;
		}
		
		//TODO Utilize wrapper implementation of deadzone
		if (gamepad.getRawAxis(Constants.AXIS_Y) > .1) {
			setpoint += gamepad.getRawAxis(3)*Constants.PID_.turnConstant;
			pidController.setSetpoint(setpoint);
		}
		
		if (gamepad.getRawAxis(Constants.AXIS_Y) < -.1) {
			setpoint += gamepad.getRawAxis(3)*Constants.PID_.turnConstant;
			pidController.setSetpoint(setpoint);
		}
	}
	
	public void doAutoDrive(double speed, double time) {
		setpoint = 0;
		double _currentTime = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() < (_currentTime + time)) {
			pidController.setDrive(speed);
		}
		System.out.println("doing AutoDrive");
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
			doPIDArcadeDrive(gamepad); 
			break;
		case SimpleDrive:
			doSimpleDrive(gamepad);
			break;
		default: System.out.println("Drive type not selected");
			break; 
		}
	}
}
