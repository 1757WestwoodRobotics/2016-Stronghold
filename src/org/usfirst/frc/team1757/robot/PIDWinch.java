package org.usfirst.frc.team1757.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author loading
 *
 */

public class PIDWinch extends WinchBase {
	private CANTeamDrive m_pidOutput;
	private PIDSource m_pidSource;
	private double m_output;
	private boolean m_isRunning;
    private ArrayList<Double> m_readings;
    private PIDController m_PIDController;

    /**
     * Assumes Potentiometer on Analog Port 0
     * Defaults PIDController to .4, 0.0, 0.0
     * @param motor
     */
	public PIDWinch(CANSpeedController motor) {
		super(motor);
		Potentiometer stringPot = new AnalogPotentiometer(0, 360, 0);
		m_pidSource = stringPot;
		m_PIDController = new PIDController(.4, 0.0, 0.0, 0.0, m_pidSource, m_pidOutput);
	}

    /**
     * Assumes Potentiometer on Analog Port 0
     * Defaults PIDController to .4, 0.0, 0.0
     * @param motorLeft, motorRight
     */
	public PIDWinch(CANSpeedController motorLeft, CANSpeedController motorRight) {
		super(motorLeft, motorRight);
		Potentiometer stringPot = new AnalogPotentiometer(0, 360, 0);
		m_pidSource = stringPot;
		m_PIDController = new PIDController(.4, 0.0, 0.0, 0.0, m_pidSource, m_pidOutput);
	}

	/**
	 * Defaults PIDController to .4, 0.0, 0.0
	 * @param motor
	 * @param pidSource
	 */
	public PIDWinch(CANSpeedController motor, PIDSource pidSource) {
		super(motor);
		m_PIDController = new PIDController(.4, 0.0, 0.0, 0.0, pidSource, m_pidOutput);
	}

	/**
	 * Defaults PIDController to .4, 0.0, 0.0
	 * @param motorLeft
	 * @param motorRight
	 * @param pidSource
	 */
	public PIDWinch(CANSpeedController motorLeft, CANSpeedController motorRight, PIDSource pidSource) {
		super(motorLeft, motorRight);
		m_PIDController = new PIDController(.4, 0.0, 0.0, 0.0, pidSource, m_pidOutput);
	}
	
	/**
	 * Defaults PIDController to .4, 0.0, 0.0
	 * @param motorLeft
	 * @param motorRight
	 * @param AnalogPort
	 */
	public PIDWinch(CANSpeedController motorLeft, CANSpeedController motorRight, int AnalogPort) {
		super(motorLeft, motorRight);
		Potentiometer stringPot = new AnalogPotentiometer(0, 360, 0);
		m_pidSource = stringPot;
		m_PIDController = new PIDController(.4, 0.0, 0.0, 0.0, m_pidSource, m_pidOutput);
	}
	
	/**
	 * Complete constructor
	 * @param motorLeft
	 * @param motorRight
	 * @param AnalogPort
	 * @param kP
	 * @param kI
	 * @param kD
	 */
	public PIDWinch(CANSpeedController motorLeft, CANSpeedController motorRight, int AnalogPort, double kP, double kI, double kD) {
		super(motorLeft, motorRight);
		Potentiometer stringPot = new AnalogPotentiometer(0, 360, 0);
		m_pidSource = stringPot;
		m_PIDController = new PIDController(kP, kI, kI, kD, m_pidSource, m_pidOutput);
	}
	
	@Override
	public void setOutput(double Output) {
		m_output = Output;
	}

	@Override
	public void changeOutput(double DeltaOutput) {
		m_output += DeltaOutput;
		
	}
	
	@Override
	public void enable() {
		m_isRunning = true;
		m_PIDController.enable();
	}
	
	@Override
	public void disable() {
		m_isRunning = false;
		m_PIDController.disable();
	}
	
	@Override
	public void stop() {
		m_output = 0.0;
	}
	
	@Override
	public void run() {
		if (m_isRunning) {
			m_PIDController.setSetpoint(m_output);
			recordData();
		}
		else
			System.out.println("Winch is not enabled");
	}

	@Override
	public void recordData() {
		m_readings.add(m_pidOutput.getOutputCurrent());
	}

	@Override
	public void recordAverageData() {
		double sum = 0;
		if(!m_readings.isEmpty()) {
			for (double reading: m_readings) {
				sum += reading;
				}
			SmartDashboard.putNumber("Average", (sum / m_readings.size()));
		}
	}
}
