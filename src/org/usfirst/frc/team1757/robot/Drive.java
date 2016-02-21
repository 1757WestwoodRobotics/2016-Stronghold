package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1757.robot.Constants;

public class Drive {

	double driveSpeed;
	boolean isDriving;
	
	RobotDrive drive;

	CANTalon talon0, talon1, talon2, talon3;

	public Drive(double driveSpeed, boolean isDriving) {
		this.driveSpeed = driveSpeed;
		this.isDriving = isDriving;
		talon0 = new CANTalon(0);
		talon1 = new CANTalon(1);
		talon2 = new CANTalon(2);
		talon3 = new CANTalon(3);
		
		
		drive = new RobotDrive(talon0, talon1, talon2, talon3);
	}

	public void printDriveMessages(Joystick gamepad) {
		SmartDashboard.putNumber("Left Axis", gamepad.getRawAxis(Constants.AXIS_Y)*0.5);
		SmartDashboard.putNumber("Right Axis", gamepad.getRawAxis(Constants.AXIS_RSY)*0.5);
		SmartDashboard.putBoolean("isDriving?", isDriving);
		/*
		SmartDashboard.putNumber("talon0-motorCurrent", talon0.getOutputCurrent());
		SmartDashboard.putNumber("talon1-motorCurrent", talon1.getOutputCurrent());
		SmartDashboard.putNumber("talon2-motorCurrent", talon2.getOutputCurrent());
		SmartDashboard.putNumber("talon3-motorCurrent", talon3.getOutputCurrent());
		*/
	}

	public void doDrive(Joystick gamepad) {
		drive.tankDrive(gamepad.getRawAxis(Constants.AXIS_Y)*0.5, gamepad.getRawAxis(Constants.AXIS_RSY)*0.5);
	}
}
