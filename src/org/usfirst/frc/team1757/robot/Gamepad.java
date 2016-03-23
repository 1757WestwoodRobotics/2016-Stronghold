package org.usfirst.frc.team1757.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Gamepad extends Joystick {

	private double triggerZone;
	private double deadZone;
	private double sensitivity;
	
	protected Gamepad(final int port) {
		super(port);
	}
	
	protected Gamepad(int port, int numAxisTypes, int numButtonTypes) {
		super(port, numAxisTypes, numButtonTypes);
		this.sensitivity = Constants.SENSITIVITY;
	}
	
	protected Gamepad(final int port, double triggerZone, double deadZone) {
		super(port);
		this.triggerZone = triggerZone;
		this.deadZone = deadZone;
		this.sensitivity = Constants.SENSITIVITY;
	}
	
	protected Gamepad(int port, int numAxisTypes, int numButtonTypes, double triggerZone, double deadZone, double sensitivity) {
		super(port, numAxisTypes, numButtonTypes);
		this.triggerZone = triggerZone;
		this.deadZone = deadZone;
		this.sensitivity = sensitivity;
	}
	
	
	public boolean getTrigger(int triggerAxis) {
		if (getRawAxis(triggerAxis) > triggerZone)
			return true;
		else
			return false;
	}
	
	public boolean getTrigger(int triggerAxis, double tZone) {
		if (getRawAxis(triggerAxis) > tZone)
			return true;
		else
			return false;
	}
	
	public double getDeadAxis(int axis) {
		if (getRawAxis(axis)>Constants.DEADZONE || getRawAxis(axis)<-Constants.DEADZONE) {
			return getRawAxis(axis);
		}
		else
			return 0;
	}
	
	/**Returns the adjusted Axis output, accounting for both deadzone and Sensitivity
	 * @param Controller axis index
	 * @return Adjusted output
	 */
	public double getAdjAxis(int axis) {
		if (getRawAxis(axis)>Constants.DEADZONE || getRawAxis(axis)<-Constants.DEADZONE) {
			return getRawAxis(axis)*Constants.SENSITIVITY;
			
		}
		else
			return 0;
		
	}
}
