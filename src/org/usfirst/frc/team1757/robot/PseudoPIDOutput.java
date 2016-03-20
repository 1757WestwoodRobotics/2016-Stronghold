package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class PseudoPIDOutput implements PIDOutput {
	
	public double output;
	
	public double getOutput() {
		return output;
	}
	
	public void setOutput(double output) {
		this.output = output;
	}
	
	public void pidWrite(double output) {
		this.output = output;
	}
}
