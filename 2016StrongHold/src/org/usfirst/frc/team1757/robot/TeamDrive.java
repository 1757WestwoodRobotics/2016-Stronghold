package org.usfirst.frc.team1757.robot;

import java.util.Enumeration;
import java.util.Vector;
import edu.wpi.first.wpilibj.SpeedController;

public class TeamDrive implements SpeedController {
	private Vector<SpeedController> motorControllers;
    private float sign = 1.0f;
    
    /** 
     * Structure: takes an array of SpeedControllers as constructor argument and puts them in a vector
     * for ease of use throughout.
     * 
     * Purpose: facilitates drive by separating wheel sides and allowing interaction per side
     * Programmers should no longer need to access individual motor CANTalon controllers for the drivetrain
     * 
     * The constructor must take an array of SpeedControllers with at least one member. 
     * */
    public TeamDrive(final SpeedController[] controllerArray) {
        motorControllers = new Vector<SpeedController>(controllerArray.length);
        for (int i = 0; i < controllerArray.length; i++) {
            motorControllers.addElement(controllerArray[i]);
        }
    }
    
    /**
     * Setter function for the sign coefficient
     */
    public void setInverted(final boolean doInvert) {
        if (doInvert) {
            sign = -1.0f;
        } else {
            sign = 1.0f;
        }
    }
    
    /**
     * Each of the SpeedControllers in the vector should be the same, so we'll use the first one
     * Outputs the speed value of this SpeedController -1 to 1
	 */
    public double get() {
        final SpeedController controller = (SpeedController) motorControllers.firstElement();
        return sign * controller.get();
    }
    
    /**
     * Sets the value of each SpeedController to the given double value
     */
    public void set(double value) {
        final Enumeration<SpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) { 
            final SpeedController controller = (SpeedController) controllers.nextElement();
            controller.set(sign * value);
        }
    }
    
    /**
     * Disables motor group
     */
    public void disable() {
    	final Enumeration<SpeedController> controllers = motorControllers.elements(); 
    	while (controllers.hasMoreElements()) { //Must be traversed similar to an iterator
            final SpeedController controller = (SpeedController) controllers.nextElement();
            controller.disable();
        }
    }
    
    /**
     * Sets the value of each SpeedController to the given double value
     * Must be traversed similar to an iterator
     */
    public void set(double value, byte syncGroup){ 
        final Enumeration<SpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) {
            final SpeedController controller = (SpeedController) controllers.nextElement();
            controller.set(sign * value);
        }
    }
    
    /**
     * Sets the value of each SpeedController to the given double value
     * Enum must be traversed similar to an iterator
     */
	public void pidWrite(double output) {
        final Enumeration<SpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) {
            final SpeedController controller = (SpeedController) controllers.nextElement();
            controller.pidWrite(sign * output);
        }
	}
	
	/**
	 * @param Numeric index of speedcontroller vector
	 * Maximum is vector length -1, minumim is 0
	 * @return Speedcontroller object instance
	 */
	public SpeedController getController(int index)
	{
		int i = 0;
        final Enumeration<SpeedController> controllers = motorControllers.elements();
        SpeedController controller = null; //Janky null instantiation
        while (controllers.hasMoreElements() && (i < index)) {
            controller = (SpeedController) controllers.nextElement();
            ++index;
        }
        return controller;
	}

	/**
	 * @param
	 * @return Returns boolean value whether or not the teams are inverted
	 */
	public boolean getInverted() {
		if (sign > 0)
			return true;
		else
			return false;
	}
}