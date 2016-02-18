package org.usfirst.frc.team1757.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1757.robot.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

@SuppressWarnings("unused")
public class Robot extends IterativeRobot {
	
    Command autonomousCommand;
    SendableChooser chooser;
    ExampleArmAction ex1;
    
    CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    PIDController pidLeft, pidRight;
    TeamDrive leftTeam, rightTeam;
    
    Joystick gamepad;
    Joystick attack3;
    ADXL362 accel;
    ADXRS450_Gyro gyro;
    
    MultiCam cameraServer;
    USBCamera cam1, cam2;
    
    double setpoint = 0;
    double initialTurn = 10;
        
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    
    boolean simpleDrive = false;
    boolean _gamepad = true;

    public void robotInit() {
        gamepad = new Joystick(Constants.Gamepad_LogitechDual.PORT);
        attack3 = new Joystick(1);
		backLeftMotor = new CANTalon(Constants.CAN_.MOTORBACKLEFT);
		backRightMotor = new CANTalon(Constants.CAN_.MOTORBACKRIGHT);
		frontLeftMotor = new CANTalon(Constants.CAN_.MOTORFRONTLEFT);
		frontRightMotor = new CANTalon(Constants.CAN_.MOTORFRONTRIGHT);
		
		CANTalon leftArray[] = {frontLeftMotor, backLeftMotor};
		CANTalon rightArray[] = {frontRightMotor, backRightMotor};
		
		leftTeam = new TeamDrive(leftArray);
		rightTeam = new TeamDrive(rightArray);
		
		rightTeam.setInverted(true);
		leftTeam.setInverted(true);
		
		gyro = new ADXRS450_Gyro();
		accel = new ADXL362(Accelerometer.Range.k8G);
		
		pidLeft = new PIDController(0, Constants.PID_.Kp, Constants.PID_.Ki, Constants.PID_.Kd, Constants.PID_.Kf, gyro, leftTeam);
		pidRight = new PIDController(0, Constants.PID_.Kp, Constants.PID_.Ki, Constants.PID_.Kd, Constants.PID_.Kf, gyro, rightTeam);
		
		cam1 = new USBCamera("cam0");
		cam2 = new USBCamera("cam1");
		ArrayList<USBCamera> cams = new ArrayList<USBCamera>();
		cams.add(cam1); cams.add(cam2);
		
		ArrayList<String> camnames = new ArrayList<String>();
		camnames.add("cam0"); camnames.add("cam1");
		cameraServer = new MultiCam(cams, camnames, Constants.Camera.CURRCAM, Constants.Camera.MAX_FPS, Constants.Camera.QUALITY, Constants.Camera.SLEEP_TIME);
		
		
		cameraServer.camInit();
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard.
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
    	while (isOperatorControl() && isEnabled()){    		
        	SmartDashboard.putData("Gyro", gyro);
    		SmartDashboard.putData("Accelerometer", accel);
    		SmartDashboard.putData("pidLeft", pidLeft);
    		SmartDashboard.putData("pidRight", pidRight);
      		
    		SmartDashboard.putNumber("rightTeam", rightTeam.get());
    		SmartDashboard.putNumber("leftTeam", leftTeam.get());
    		
    		SmartDashboard.putNumber("Right Joystick", gamepad.getRawAxis(3)*Constants.Gamepad_LogitechDual.SENSITIVITY);
    		SmartDashboard.putNumber("Left Joystick", gamepad.getY()*Constants.Gamepad_LogitechDual.SENSITIVITY);
    		
    		SmartDashboard.putNumber("Attack3 Tilt Forward", attack3.getRawAxis(1)*Constants.Logitech_ATK3.SENSITIVITY);
    		SmartDashboard.putNumber("Attack3 Tilt Left", attack3.getRawAxis(0)*-Constants.Logitech_ATK3.SENSITIVITY);
    		
    		SmartDashboard.putNumber("Angle", gyro.getAngle());	
    		
    		pidLeft.setInverted(true);
    		pidRight.setInverted(false);
    		
    		cameraServer.runCam();
    		
    		if (_gamepad) {
    			pidLeft.setDrive(gamepad.getY()*Constants.Gamepad_LogitechDual.SENSITIVITY);
        		pidRight.setDrive(gamepad.getY()*Constants.Gamepad_LogitechDual.SENSITIVITY);
        		
        		if (gamepad.getRawAxis(3) > .1) {
        			setpoint += gamepad.getRawAxis(3)*Constants.PID_.turnConstant;
        			pidLeft.setSetpoint(setpoint);
        			pidRight.setSetpoint(setpoint);
        		}
        		
        		if (gamepad.getRawAxis(3) < -.1) {
        			setpoint += gamepad.getRawAxis(3)*Constants.PID_.turnConstant;
        			pidLeft.setSetpoint(setpoint);
        			pidRight.setSetpoint(setpoint);
        		}
    		}
    		else {
    			pidLeft.setDrive(attack3.getRawAxis(Constants.Logitech_ATK3.AXIS_Y)*Constants.Logitech_ATK3.SENSITIVITY);
        		pidRight.setDrive(attack3.getRawAxis(Constants.Logitech_ATK3.AXIS_Y)*Constants.Logitech_ATK3.SENSITIVITY);
        		
        		if (attack3.getRawAxis(Constants.Logitech_ATK3.AXIS_X) > .1) {
        			setpoint += attack3.getRawAxis(Constants.Logitech_ATK3.AXIS_X)*Constants.PID_.turnConstant;
        			pidLeft.setSetpoint(setpoint);
        			pidRight.setSetpoint(setpoint);
        		}
        		if (attack3.getRawAxis(Constants.Logitech_ATK3.AXIS_X) < -.1) {
        			setpoint += attack3.getRawAxis(Constants.Logitech_ATK3.AXIS_X)*Constants.PID_.turnConstant;
        			pidLeft.setSetpoint(setpoint);
        			pidRight.setSetpoint(setpoint);
        		}
    		}
    		
    		
    		if (gamepad.getRawButton(Constants.Gamepad_LogitechDual.BUTTON_X)) {
    			gyro.reset();
    		}
    		if (gamepad.getRawButton(Constants.Gamepad_LogitechDual.BUTTON_B)) {
    			pidRight.disable();
    			pidLeft.disable();
    			simpleDrive = true;
    		}
    		if (gamepad.getRawButton(Constants.Gamepad_LogitechDual.BUTTON_Y)) {
    			cameraServer.switchCamera();
    		}
    		if (simpleDrive){
    			leftTeam.set(gamepad.getRawAxis(3)*-Constants.Gamepad_LogitechDual.SENSITIVITY);
        		rightTeam.set(gamepad.getY()*Constants.Gamepad_LogitechDual.SENSITIVITY);
    		}
    		if (gamepad.getRawButton(Constants.Gamepad_LogitechDual.BUTTON_A)){
    			_gamepad = false;
    		}
    		
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
