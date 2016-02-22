package org.usfirst.frc.team1757.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author loading
 *
 */

public class DirectWinch extends WinchBase {
	private CANTeamDrive m_motorOut;
	private double m_output;
	private boolean m_isRunning;
    private ArrayList<Double> m_readings;
	
	public DirectWinch(CANSpeedController motor) {
		super(motor);
	}
	
	public DirectWinch(CANSpeedController motorLeft, CANSpeedController motorRight) {
		super(motorLeft,motorRight);
	}
	
	public void enable() {
		m_isRunning = true;
	}
	
	public void disable() {
		m_isRunning = false;
		
	}
	
	public void stop() {
		m_output = 0.0;
	}
	
	public void run() {
		if (m_isRunning) {
			m_motorOut.set(m_output);
			recordData();
		}
		else
			System.out.println("Winch is not enabled");
	}

	public void setOutput(double Output) {
		m_output = Output;
	}

	public void changeOutput(double DeltaOutput) {
		m_output += DeltaOutput;
	}

	@Override
	public void recordData() {
		m_readings.add(m_motorOut.getOutputCurrent());
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
