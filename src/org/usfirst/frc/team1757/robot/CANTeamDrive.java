package org.usfirst.frc.team1757.robot;

import java.util.Vector;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.tables.ITable;
/**
 * 
 * @author loading
 *
 */

public class CANTeamDrive implements CANSpeedController, PIDOutput {
	private Vector<CANSpeedController> motorControllers;
    
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
        for (CANSpeedController controller:controllerArray) {
            motorControllers.addElement(controller);
        }
    }
    
    /**
     * Implements setInverted method for each element of the CANSpeedController array
     */
    public void setInverted(final boolean doInvert) {
    	for (CANSpeedController controller:motorControllers) {
    		controller.setInverted(doInvert);
    	}
    }
    

    /**
     * @param Index of CANSpeedController in CANSpeedController Team
     * @return get() value for index element in the CANSpeedController Team - value variable on the CANSpeedController controlmode
     */
    public double get(int index) {
    	return motorControllers.get(index).get();
    }
    
    /**
     * @return Average get() value for all elements in the CANSpeedController Team - value variable on the CANSpeedController controlmode
     */
    public double get() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.get();
    		count++;
    	}
    	return sum/count;
    }
    
    /**
     * Sets the value of each CANSpeedController to the given double value
     */
    public void set(double speed) {
        for (CANSpeedController controller:motorControllers) {
        	controller.set(speed);
        }
    }
    
    /**
     * Disables motor group
     */
    public void disable() {
        for (CANSpeedController controller:motorControllers) {
        	controller.disable();
        }
    }
    
    /**
     * 
     */
    public void set(double speed, byte syncGroup){ 
        for (CANSpeedController controller:motorControllers) {
        	controller.set(speed, syncGroup);
        }
    }
    
    /**
     * 
     */
	public void pidWrite(double output) {
        for (CANSpeedController controller:motorControllers) {
        	controller.pidWrite(output);
        }
	}
	
	/**
	 * @param Numeric index of CANSpeedController vector
	 * Maximum is vector length -1, minumim is 0
	 * @return CANSpeedController object instance
	 */
	public CANSpeedController getController(int index)	{
		return motorControllers.get(index);
	}
	
	/**
	 * @return Integer size of the motor team
	 */
	public int getTeamSize() {
		return motorControllers.size();
	}

	/**
	 * @return getInverted() value for the first element of CANSpeedController Team
	 */
	public boolean getInverted() {
		return motorControllers.firstElement().getInverted();
	}
	
	public void setPID(double p, double i, double d) {
		for (CANSpeedController controller:motorControllers) {
			controller.setPID(p, i, d);
		}
	}

	@Override
	public double getP() {
		return motorControllers.firstElement().getP();
	}

	@Override
	public double getI() {
		return motorControllers.firstElement().getI();
	}

	@Override
	public double getD() {
		return motorControllers.firstElement().getD();
	}

	@Override
	public void setSetpoint(double setpoint) {
		for(CANSpeedController controller:motorControllers) {
			controller.setSetpoint(setpoint);
		}
	}

	@Override
	public double getSetpoint() {
		return motorControllers.firstElement().getSetpoint();
	}

	@Override
	public double getError() {
		return motorControllers.firstElement().getError();
	}

	@Override
	public void enable() {
		for(CANSpeedController controller:motorControllers) {
			controller.enable();
		}
	}

	@Override
	public boolean isEnabled() {
		return motorControllers.firstElement().isEnabled();
	}

	@Override
	public void reset() {
		for(CANSpeedController controller:motorControllers) {
			controller.reset();
		}
	}

	@Override
	public void updateTable() {
		for(CANSpeedController controller:motorControllers) {
			controller.updateTable();
		}
	}

	@Override
	public void startLiveWindowMode() {
		for(CANSpeedController controller:motorControllers) {
			controller.startLiveWindowMode();
		}
	}

	@Override
	public void stopLiveWindowMode() {
		for(CANSpeedController controller:motorControllers) {
			controller.stopLiveWindowMode();
		}
	}

	@Override
	public void initTable(ITable subtable) {
		for(CANSpeedController controller:motorControllers) {
			controller.initTable(subtable);
		}
	}

	@Override
	public ITable getTable() {
		return motorControllers.firstElement().getTable();
	}

	@Override
	public String getSmartDashboardType() {
		return motorControllers.firstElement().getSmartDashboardType();
	}

	@Override
	public ControlMode getControlMode() {
		return motorControllers.firstElement().getControlMode();
	}

	@Override
	public void setControlMode(int mode) {
		for(CANSpeedController controller:motorControllers) {
			controller.setControlMode(mode);
		}
	}

	@Override
	public void setP(double p) {
		for(CANSpeedController controller:motorControllers) {
			controller.setP(p);
		}
	}

	@Override
	public void setI(double i) {
		for(CANSpeedController controller:motorControllers) {
			controller.setI(i);
		}
	}

	@Override
	public void setD(double d) {
		for(CANSpeedController controller:motorControllers) {
			controller.setD(d);
		}
	}

    public double getBusVoltage(int index) {
    	return motorControllers.get(index).getBusVoltage();
    }
    
    public double getBusVoltage() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.getBusVoltage();
    		count++;
    	}
    	return sum/count;
    }

    public double getOutputVoltage(int index) {
    	return motorControllers.get(index).getOutputVoltage();
    }
    
    public double getOutputVoltage() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.getOutputVoltage();
    		count++;
    	}
    	return sum/count;
    }

    public double getOutputCurrent(int index) {
    	return motorControllers.get(index).getOutputCurrent();
    }
    
    public double getOutputCurrent() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.getOutputCurrent();
    		count++;
    	}
    	return sum/count;
    }

    public double getTemperature(int index) {
    	return motorControllers.get(index).getTemperature();
    }
    
    public double getTemperature() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.getTemperature();
    		count++;
    	}
    	return sum/count;
    }
	
    public double getPosition(int index) {
    	return motorControllers.get(index).getPosition();
    }
    
    public double getPosition() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.getPosition();
    		count++;
    	}
    	return sum/count;
    }
	
    public double getSpeed(int index) {
    	return motorControllers.get(index).getSpeed();
    }
    
    public double getSpeed() {
        double sum = 0, count = 0;
    	for (CANSpeedController controller:motorControllers) {
    		sum += controller.getSpeed();
    		count++;
    	}
    	return sum/count;
    }
	
    public void setVoltageRampRate(double rampRate) {
		for(CANSpeedController controller:motorControllers) {
			controller.setVoltageRampRate(rampRate);
		}
    }

	@Override
	public void stopMotor() {
		for(CANSpeedController controller:motorControllers) {
			controller.stopMotor();
		}
	}
}