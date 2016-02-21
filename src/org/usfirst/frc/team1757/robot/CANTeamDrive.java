package org.usfirst.frc.team1757.robot;

import java.util.Enumeration;
import java.util.Vector;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.tables.ITable;

public class CANTeamDrive implements CANSpeedController {
	private Vector<CANSpeedController> motorControllers;
    private float sign = 1.0f;
    private int teamSize;
    
    /** 
     * Structure: takes an array of CANSpeedControllers as constructor argument and puts them in a vector
     * for ease of use throughout.
     * 
     * Purpose: facilitates drive by separating wheel sides and allowing interaction per side
     * Programmers should no longer need to access individual motor CANTalon controllers for the drivetrain
     * 
     * The constructor must take an array of CANSpeedControllers with at least one member. 
     * */
    public CANTeamDrive(final CANSpeedController[] controllerArray) {
        motorControllers = new Vector<CANSpeedController>(controllerArray.length);
        for (int i = 0; i < controllerArray.length; i++) {
            motorControllers.addElement(controllerArray[i]);
            teamSize = i;
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
     * Each of the CANSpeedControllers in the vector should be the same, so we'll use the first one
     * Outputs the speed value of this CANSpeedController -1 to 1
	 */
    public double get() {
        final CANSpeedController controller = (CANSpeedController) motorControllers.firstElement();
        return sign * controller.get();
    }
    
    /**
     * Sets the value of each CANSpeedController to the given double value
     */
    public void set(double value) {
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) { 
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.set(sign * value);
        }
    }
    
    /**
     * Disables motor group
     */
    public void disable() {
    	final Enumeration<CANSpeedController> controllers = motorControllers.elements(); 
    	while (controllers.hasMoreElements()) { //Must be traversed similar to an iterator
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.disable();
        }
    }
    
    /**
     * Sets the value of each CANSpeedController to the given double value
     * Must be traversed similar to an iterator
     */
    public void set(double value, byte syncGroup){ 
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) {
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.set(sign * value);
        }
    }
    
    /**
     * Sets the value of each CANSpeedController to the given double value
     * Enum must be traversed similar to an iterator
     */
	public void pidWrite(double output) {
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) {
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.pidWrite(sign * output);
        }
	}
	
	/**
	 * @param Numeric index of CANSpeedController vector
	 * Maximum is vector length -1, minumim is 0
	 * @return CANSpeedController object instance
	 */
	public CANSpeedController getController(int index)	{
		int i = 0;
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        CANSpeedController controller = null; //Janky null instantiation
        while (controllers.hasMoreElements() && (i < index)) {
            controller = (CANSpeedController) controllers.nextElement();
            ++index;
        }
        return controller;
	}
	
	/**
	 * @return Integer size of the motor team
	 */
	public int getTeamSize() {
		return teamSize + 1;
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

	@Override
	public void setPID(double p, double i, double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getI() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getD() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSetpoint(double setpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSetpoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getError() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void enable() {
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) { 
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.enable();
        }
	}

	@Override
	public boolean isEnabled() {
        final CANSpeedController controller = (CANSpeedController) motorControllers.firstElement();
        return controller.isEnabled();
	}

	@Override
	public void reset() {
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) { 
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.reset();
        }
		
	}

	@Override
	public void updateTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initTable(ITable subtable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ITable getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ControlMode getControlMode() {
        final CANSpeedController controller = (CANSpeedController) motorControllers.firstElement();
        return controller.getControlMode();
	}

	@Override
	public void setControlMode(int mode) {
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) { 
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.setControlMode(mode);
        }
	}

	@Override
	public void setP(double p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setI(double i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setD(double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBusVoltage() {
        final CANSpeedController controller = (CANSpeedController) motorControllers.firstElement();
        return controller.getBusVoltage();
	}

	@Override
	public double getOutputVoltage() {
        final CANSpeedController controller = (CANSpeedController) motorControllers.firstElement();
        return controller.getOutputVoltage();
	}

	@Override
	public double getOutputCurrent() {
        double sum = 0;
        for (CANSpeedController controller: motorControllers) {
        	sum += controller.getOutputCurrent();
        }
        return sum/teamSize;
    }

	@Override
	public double getTemperature() {
        final CANSpeedController controller = (CANSpeedController) motorControllers.firstElement();
        return controller.getTemperature();
	}

	@Override
	public double getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVoltageRampRate(double rampRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopMotor() {
        final Enumeration<CANSpeedController> controllers = motorControllers.elements();
        while (controllers.hasMoreElements()) { 
            final CANSpeedController controller = (CANSpeedController) controllers.nextElement();
            controller.stopMotor();
        }
	}
}