package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import edu.wpi.first.wpilibj.CameraServer;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import org.usfirst.frc.team1757.robot.*;

public class Robot extends IterativeRobot {
	
    Command autonomousCommand;
    SendableChooser chooser;
    ExampleArmAction ex1;
    
    CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    PIDController pidLeft, pidRight;
    TeamDrive leftTeam, rightTeam;
    
    Joystick gamepad;
    ADXL362 accel;
    ADXRS450_Gyro gyro;
    
    CameraServer camera;
   
    int session;
    Image frame;
    
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    
    double Kp = 0.04;
    double Ki = 0.00;
    double Kd = 0.00;
    double Kf = 0;
    double sensitivity = .4;
    double setpoint = 0;
    double initialTurn = 10;
    double turnConstant = 0.8;
    
    boolean simpleDrive;

    public void robotInit() {
        gamepad = new Joystick(0);
		backLeftMotor = new CANTalon(1);
		backRightMotor = new CANTalon(2);
		frontLeftMotor = new CANTalon(3);
		frontRightMotor = new CANTalon(4);
		
		CANTalon leftArray[] = {frontLeftMotor, backLeftMotor};
		CANTalon rightArray[] = {frontRightMotor, backRightMotor};
		
		leftTeam = new TeamDrive(leftArray);
		rightTeam = new TeamDrive(rightArray);
		
		rightTeam.setInverted(true);
		leftTeam.setInverted(true);
		
		gyro = new ADXRS450_Gyro();
		accel = new ADXL362(Accelerometer.Range.k8G);
		
		pidLeft = new PIDController(0, Kp, Ki, Kd, Kf, gyro, leftTeam);
		pidRight = new PIDController(0, Kp, Ki, Kd, Kf, gyro, rightTeam);
		
		gyro.reset();
		
		camera = CameraServer.getInstance();
        camera.setQuality(50);
        camera.startAutomaticCapture("cam0");
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
    		
    		SmartDashboard.putNumber("Right Joystick", gamepad.getRawAxis(3)*sensitivity );
    		SmartDashboard.putNumber("Left Joystick", gamepad.getY()*sensitivity);
    		
    		SmartDashboard.putNumber("Angle", gyro.getAngle());	
    		
    		pidLeft.setInverted(true);
    		pidRight.setInverted(false);
    		
    		pidLeft.setDrive(gamepad.getY()*sensitivity);
    		pidRight.setDrive(gamepad.getY()*sensitivity);
    		
    		if (gamepad.getRawAxis(3) > .1) {
    			setpoint += gamepad.getRawAxis(3)*turnConstant;
    			pidLeft.setSetpoint(setpoint);
    			pidRight.setSetpoint(setpoint);
    		}
    		
    		if (gamepad.getRawAxis(3) < -.1) {
    			setpoint += gamepad.getRawAxis(3)*turnConstant;
    			pidLeft.setSetpoint(setpoint);
    			pidRight.setSetpoint(setpoint);
    		}
    		
    		if (gamepad.getRawButton(1)) {
    			gyro.reset();
    		}
    		if (gamepad.getRawButton(3)) {
    			pidRight.disable();
    			pidLeft.disable();
    			simpleDrive = true;
    		}
    		if (gamepad.getRawButton(2)){
    			frontLeftMotor.enableBrakeMode(true);
    		}
    		if (simpleDrive){
    			leftTeam.set(gamepad.getRawAxis(3)*-sensitivity);
        		rightTeam.set(gamepad.getY()*sensitivity);
    		}
    	
    		
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
