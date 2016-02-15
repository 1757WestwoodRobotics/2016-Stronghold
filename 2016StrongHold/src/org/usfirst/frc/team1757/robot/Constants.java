package org.usfirst.frc.team1757.robot;

public class Constants {
	private Constants(){}
	
	public static final class Config {
		private Config(){}
		
	}
	
	public static final class CAN_ {
		private CAN_(){}
		
		public static final int
		MOTORFRONTLEFT = 3, MOTORFRONTRIGHT = 4, MOTORBACKLEFT = 1, MOTORBACKRIGHT = 2, PDP = 0;
		//MOTORARM
	}
	
	public static final class PCM_ {
		private PCM_(){}	
	}
	
	public static final class PID_ {
		private PID_(){}
	    
		public static final double
		Kp = 0.04, Ki = 0.00, Kd = 0.00, Kf = 0, turnConstant = 0.8;
	    ;
	}
	
	public static final class AIO_ {
		private AIO_(){}
		
		//public static final int
		//TRANSDUCER, GYROMETER;
	}
	
	public static final class DIO_ {
		private DIO_(){}
		
		//public static final int
		//STOPSWITCH;
	}
	
	public static final class Camera {
		private Camera(){}
		public static final int 
		CURRCAM = 0, MAX_FPS = 30, QUALITY = 100, SLEEP_TIME = 100;
	}
	
	public static final class Gamepad_LogitechDual {
		private Gamepad_LogitechDual(){}
		
		public static final int PORT = 0;
		public static final float DEADZONE = 0.08f, INVERTED = 0.0f, TRIGGERZONE = 0.0f, SENSITIVITY = 0.4f;
		public static final String MODE = "DUALACTION";
		
		public static final int
    	BUTTON_A = 2, BUTTON_B = 3, BUTTON_X = 1,
    	BUTTON_Y = 4, BUTTON_LB = 5, BUTTON_RB = 6,
    	BUTTON_RT = 7, BUTTON_LT = 8, 
    	BUTTON_BACK = 9, BUTTON_START = 10,
    	AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 2, AXIS_RSY = 3;
	}
	
	public static final class Gamepad_LogitechF310 {
		private Gamepad_LogitechF310(){}
        public static final int PORT = 1;
        public static final float DEADZONE = 0.08f;
        public static final float INVERTED = 0.0f;

    	// Constant variables for the button codes on the F310 gamepad
    	public static final int 
    	BUTTON_A = 1, BUTTON_B = 2, BUTTON_X = 3,
    	BUTTON_Y = 4, BUTTON_LB = 5, BUTTON_RB = 6,
    	BUTTON_BACK = 7, BUTTON_START = 8, BUTTON_LS = 9, 
    	BUTTON_RS = 10, AXIS_RTRIGGER = 3, AXIS_LTRIGGER = 3;
    	
    	/**
    	 * Analog (raw)Axis codes
    	 * Left Stick Up/Dn = 2 Axis (Dn+)
    	 * Left Stick L/R = Axis 1 (L-)
    	 * Right Stick Up/Dn = Axis 5 (Dn+)
    	 * Right Stick L/R = Axis 4 (L-)
    	 * Left Trigger = Axis 3 (+)
    	 * Right Trigger = Axis 3 (-)
    	 * Gamepad Up = Axis 2(-)
    	 * Gamepad Dn = Axis 2(+)Gamepad L = Axis 6(-)
    	 * Gamepad R = Axis 6(+)
    	 * 
    	 * NOTE: Mode switch changes Axis 2 between Left Stick Up/Dn and Gamepad Up/Dn 
	*/
	}
	public static final class Logitech_ATK3 {
        private Logitech_ATK3(){}
        public static final int PORT = 2;
        public static final float 
        DEADZONE = 0.08f, SENSITIVITY = 0.4f;
        
        public static final int
        BUTTON_TRIGGER = 1, AXIS_X = 0, AXIS_Y = 1;
        //All other buttons are labeled correctly
        
    }
}