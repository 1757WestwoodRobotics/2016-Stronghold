package org.usfirst.frc.team1757.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.CANSpeedController;

/**
 * 
 * @author loading
 *
 */

public abstract class WinchBase {
	private CANTeamDrive m_motorOut;
	private double m_output;
	private boolean m_isRunning;
    private ArrayList<Double> m_readings;
    
    public WinchBase(CANSpeedController motor) {}
	public WinchBase(CANSpeedController motorLeft, CANSpeedController motorRight) {}	
	
	public abstract void setOutput(double Output);
	
	public abstract void changeOutput(double DeltaOutput);
	
	public abstract void enable();
	
	public abstract void disable();
	
	public abstract void stop();
	
	public abstract void run();
	
	public abstract void recordData();
	
	public abstract void recordAverageData();
	
	
	/**
	 * @return the m_motorOut
	 */
	public CANTeamDrive getM_motorOut() {
		return m_motorOut;
	}
	/**
	 * @param canTeamDrive the m_motorOut to set
	 */
	public void setM_motorOut(CANTeamDrive canTeamDrive) {
		this.m_motorOut = canTeamDrive;
	}
	/**
	 * @return the m_output
	 */
	public double getM_output() {
		return m_output;
	}
	/**
	 * @param m_output the m_output to set
	 */
	public void setM_output(double m_output) {
		this.m_output = m_output;
	}
	/**
	 * @return the m_isRunning
	 */
	public boolean isM_isRunning() {
		return m_isRunning;
	}
	/**
	 * @param m_isRunning the m_isRunning to set
	 */
	public void setM_isRunning(boolean m_isRunning) {
		this.m_isRunning = m_isRunning;
	}
	/**
	 * @return the m_readings
	 */
	public ArrayList<Double> getM_readings() {
		return m_readings;
	}
	/**
	 * @param m_readings the m_readings to set
	 */
	public void setM_readings(ArrayList<Double> m_readings) {
		this.m_readings = m_readings;
	}
}
