package org.usfirst.frc.team1757.robot;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.tables.ITable;
/**
 * 
 * @author loading
 *
 */

@SuppressWarnings("unused")
public class CANTeamDrive implements CANSpeedController, PIDOutput {
    private final Set<CANSpeedController> motorControllers = new HashSet<>();
    private float inversion = 1.0f;
    
    /** 
     * Structure: takes an array of CANSpeedControllers as constructor argument and puts them in a vector
     * for ease of use throughout.
     * 
     * Purpose: facilitates drive by separating wheel sides and allowing interaction per side
     * Programmers should no longer need to access individual motor CANTalon controllers for the drivetrain
     * 
     * The constructor must take an array of CANSpeedControllers with at least one member. 
     * */
    public CANTeamDrive(final Collection<CANSpeedController> controllers) {
        Objects.requireNonNull(controllers);
        motorControllers.addAll(controllers);
    }
    
    /**
     * Create a new SpeedControllerGroup instance.
     *
     * @param controllers a varargs list or array of SpeedController objects. May be
     *                    all the same type, or may be mixed.
     */
    public CANTeamDrive(final CANSpeedController... controllers) {
        this(Arrays.asList(controllers));
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
     * Invert all PWM values for this SpeedControllerGroup.
     *
     * @param inverted whether the PWM values should be inverted or not
     * @return this SpeedControllerGroup instance
     */
    public void setInversion(final boolean isinverted) {
    	if (isinverted)
    		inversion = -1.0f;
    	else
    		inversion = 1.0f;
    }
    
    /**
     * @return Average get() value for all elements in the CANSpeedController Team - value variable on the CANSpeedController controlmode
     */
    public double get() {
        return inversion * motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.get()))
                .orElse(0.0);
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
	 * @return Integer size of the motor team
	 */
	public int getTeamSize() {
		return motorControllers.size();
	}

	/**
	 * @return getInverted() value for the first element of CANSpeedController Team
	 */
	public boolean getInverted() {
		return (inversion > 0) ? false : true;
	}
	
	public void setPID(double p, double i, double d) {
		for (CANSpeedController controller:motorControllers) {
			controller.setPID(p, i, d);
		}
	}

	@Override
	public double getP() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getP()))
                .orElse(0.0);
	}

	@Override
	public double getI() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getI()))
                .orElse(0.0);
	}

	@Override
	public double getD() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getD()))
                .orElse(0.0);
	}

	@Override
	public void setSetpoint(double setpoint) {
		for(CANSpeedController controller:motorControllers) {
			controller.setSetpoint(setpoint);
		}
	}

	@Override
	public double getSetpoint() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getSetpoint()))
                .orElse(0.0);
	}

	@Override
	public double getError() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getError()))
                .orElse(0.0);
	}

	@Override
	public void enable() {
		for(CANSpeedController controller:motorControllers) {
			controller.enable();
		}
	}

	@Override
	public boolean isEnabled() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.isEnabled()))
                .orElse(false);
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
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getTable()))
                .orElse(null);
	}

	@Override
	public String getSmartDashboardType() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getSmartDashboardType()))
                .orElse("Error getting SmartDashboardType");
	}

	@Override
	public ControlMode getControlMode() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getControlMode()))
                .orElse(CANTalon.TalonControlMode.PercentVbus);
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
    
    public double getBusVoltage() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getBusVoltage()))
                .orElse(0.0);
    }
    
    public double getOutputVoltage() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getOutputVoltage()))
                .orElse(0.0);
    }
    
    public double getOutputCurrent() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getOutputCurrent()))
                .orElse(0.0);
    }

    public double getTemperature() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getTemperature()))
                .orElse(0.0);
    }
	
    public double getPosition() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getPosition()))
                .orElse(0.0);
    }
	
    public double getSpeed() {
        return motorControllers.stream().findFirst()
                .flatMap(controller -> Optional.ofNullable(controller.getSpeed()))
                .orElse(0.0);
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