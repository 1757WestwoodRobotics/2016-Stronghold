package org.usfirst.frc.team1757.robot;

public class Constants {
	private Constants(){}
	
	public static int PORT;
	public static float DEADZONE, INVERTED, TRIGGERZONE, SENSITIVITY;
	public static String MODE;
			
	public static int 
	BUTTON_A, BUTTON_B, BUTTON_X, BUTTON_Y,
	BUTTON_LB, BUTTON_RB, BUTTON_LT, BUTTON_RT, 
	BUTTON_BACK, BUTTON_START, BUTTON_LS, BUTTON_RS,
	AXIS_X, AXIS_Y, AXIS_RSX, AXIS_RSY;
	
	public enum GamepadTypes {
		Logitech_DualAction, Logitech_F310, Xbox360, Gamecube
	}
	
	public static void setConstants(GamepadTypes type) {
		if (type == GamepadTypes.Logitech_DualAction) {
			PORT = Constants.Gamepad_LogitechDual.PORT;
			DEADZONE = Constants.Gamepad_LogitechDual.DEADZONE;
			INVERTED = Constants.Gamepad_LogitechDual.INVERTED;
			TRIGGERZONE = Constants.Gamepad_LogitechDual.TRIGGERZONE;
			SENSITIVITY = Constants.Gamepad_LogitechDual.SENSITIVITY;
			MODE = Constants.Gamepad_LogitechDual.MODE;
					
			BUTTON_A = Constants.Gamepad_LogitechDual.BUTTON_A;
			BUTTON_B = Constants.Gamepad_LogitechDual.BUTTON_B;
			BUTTON_X = Constants.Gamepad_LogitechDual.BUTTON_X;
			BUTTON_Y = Constants.Gamepad_LogitechDual.BUTTON_Y;
			BUTTON_LB = Constants.Gamepad_LogitechDual.BUTTON_LB;
			BUTTON_RB = Constants.Gamepad_LogitechDual.BUTTON_RB;
			BUTTON_LT = Constants.Gamepad_LogitechDual.BUTTON_LT;
			BUTTON_RT = Constants.Gamepad_LogitechDual.BUTTON_RT;
			BUTTON_BACK = Constants.Gamepad_LogitechDual.BUTTON_BACK;
			BUTTON_START = Constants.Gamepad_LogitechDual.BUTTON_START;
			BUTTON_LS = Constants.Gamepad_LogitechDual.BUTTON_LS;
			BUTTON_RS = Constants.Gamepad_LogitechDual.BUTTON_RS;
			AXIS_X = Constants.Gamepad_LogitechDual.AXIS_X;
			AXIS_Y = Constants.Gamepad_LogitechDual.AXIS_Y;
			AXIS_RSX = Constants.Gamepad_LogitechDual.AXIS_RSX;
			AXIS_RSY = Constants.Gamepad_LogitechDual.AXIS_RSY;
		}
		else if (type == GamepadTypes.Logitech_F310) {
			PORT = Constants.Gamepad_LogitechF310.PORT;
			DEADZONE = Constants.Gamepad_LogitechF310.DEADZONE;
			INVERTED = Constants.Gamepad_LogitechF310.INVERTED;
			TRIGGERZONE = Constants.Gamepad_LogitechF310.TRIGGERZONE;
			SENSITIVITY = Constants.Gamepad_LogitechF310.SENSITIVITY;
			MODE = Constants.Gamepad_LogitechF310.MODE;
					
			BUTTON_A = Constants.Gamepad_LogitechF310.BUTTON_A;
			BUTTON_B = Constants.Gamepad_LogitechF310.BUTTON_B;
			BUTTON_X = Constants.Gamepad_LogitechF310.BUTTON_X;
			BUTTON_Y = Constants.Gamepad_LogitechF310.BUTTON_Y;
			BUTTON_LB = Constants.Gamepad_LogitechF310.BUTTON_LB;
			BUTTON_RB = Constants.Gamepad_LogitechF310.BUTTON_RB;
			BUTTON_LT = Constants.Gamepad_LogitechF310.BUTTON_LT;
			BUTTON_RT = Constants.Gamepad_LogitechF310.BUTTON_RT;
			BUTTON_BACK = Constants.Gamepad_LogitechF310.BUTTON_BACK;
			BUTTON_START = Constants.Gamepad_LogitechF310.BUTTON_START;
			BUTTON_LS = Constants.Gamepad_LogitechF310.BUTTON_LS;
			BUTTON_RS = Constants.Gamepad_LogitechF310.BUTTON_RS;
			AXIS_X = Constants.Gamepad_LogitechF310.AXIS_X;
			AXIS_Y = Constants.Gamepad_LogitechF310.AXIS_Y;
			AXIS_RSX = Constants.Gamepad_LogitechF310.AXIS_RSX;
			AXIS_RSY = Constants.Gamepad_LogitechF310.AXIS_RSY;
		}
		else if (type == GamepadTypes.Gamecube) {
			PORT = Constants.Gamepad_Gamecube.PORT;
			DEADZONE = Constants.Gamepad_Gamecube.DEADZONE;
			INVERTED = Constants.Gamepad_Gamecube.INVERTED;
			TRIGGERZONE = Constants.Gamepad_Gamecube.TRIGGERZONE;
			SENSITIVITY = Constants.Gamepad_Gamecube.SENSITIVITY;
			MODE = Constants.Gamepad_Gamecube.MODE;
					
			BUTTON_A = Constants.Gamepad_Gamecube.BUTTON_A;
			BUTTON_B = Constants.Gamepad_Gamecube.BUTTON_B;
			BUTTON_X = Constants.Gamepad_Gamecube.BUTTON_X;
			BUTTON_Y = Constants.Gamepad_Gamecube.BUTTON_Y;
			//BUTTON_LB = Constants.Gamepad_Gamecube.BUTTON_LB;
			//BUTTON_RB = Constants.Gamepad_Gamecube.BUTTON_RB;
			BUTTON_LT = Constants.Gamepad_Gamecube.BUTTON_LT;
			BUTTON_RT = Constants.Gamepad_Gamecube.BUTTON_RT;
			//BUTTON_BACK = Constants.Gamepad_Gamecube.BUTTON_BACK;
			BUTTON_START = Constants.Gamepad_Gamecube.BUTTON_START;
			//BUTTON_LS = Constants.Gamepad_Gamecube.BUTTON_LS;
			//BUTTON_RS = Constants.Gamepad_Gamecube.BUTTON_RS;
			AXIS_X = Constants.Gamepad_Gamecube.AXIS_X;
			AXIS_Y = Constants.Gamepad_Gamecube.AXIS_Y;
			AXIS_RSX = Constants.Gamepad_Gamecube.AXIS_RSX;
			AXIS_RSY = Constants.Gamepad_Gamecube.AXIS_RSY;
		}
		else if (type == GamepadTypes.Xbox360) {
			PORT = Constants.Gamepad_Xbox360.PORT;
			DEADZONE = Constants.Gamepad_Xbox360.DEADZONE;
			INVERTED = Constants.Gamepad_Xbox360.INVERTED;
			TRIGGERZONE = Constants.Gamepad_Xbox360.TRIGGERZONE;
			SENSITIVITY = Constants.Gamepad_Xbox360.SENSITIVITY;
			MODE = Constants.Gamepad_Xbox360.MODE;
					
			BUTTON_A = Constants.Gamepad_Xbox360.BUTTON_A;
			BUTTON_B = Constants.Gamepad_Xbox360.BUTTON_B;
			BUTTON_X = Constants.Gamepad_Xbox360.BUTTON_X;
			BUTTON_Y = Constants.Gamepad_Xbox360.BUTTON_Y;
			BUTTON_LB = Constants.Gamepad_Xbox360.BUTTON_LB;
			BUTTON_RB = Constants.Gamepad_Xbox360.BUTTON_RB;
			//BUTTON_LT = Constants.Gamepad_Xbox360.BUTTON_LT;
			//BUTTON_RT = Constants.Gamepad_Xbox360.BUTTON_RT;
			BUTTON_BACK = Constants.Gamepad_Xbox360.BUTTON_BACK;
			BUTTON_START = Constants.Gamepad_Xbox360.BUTTON_START;
			BUTTON_LS = Constants.Gamepad_Xbox360.BUTTON_LS;
			BUTTON_RS = Constants.Gamepad_Xbox360.BUTTON_RS;
			AXIS_X = Constants.Gamepad_Xbox360.AXIS_X;
			AXIS_Y = Constants.Gamepad_Xbox360.AXIS_Y;
			AXIS_RSX = Constants.Gamepad_Xbox360.AXIS_RSX;
			AXIS_RSY = Constants.Gamepad_Xbox360.AXIS_RSY;
		}
	}
	
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
		BUTTON_LT = 7, BUTTON_RT = 8, BUTTON_BACK = 9, 
		BUTTON_START = 10, BUTTON_LS = 11, BUTTON_RS = 12,
		AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 2, AXIS_RSY = 3;
	}
	
	public static final class Gamepad_Gamecube {
		private Gamepad_Gamecube() {}
		
		public static final int PORT = 1;
		public static final float DEADZONE = 0.08f, INVERTED = 0.0f, TRIGGERZONE = 0.0f, SENSITIVITY = 0.4f;
		public static final String MODE = "GAMECUBE";
		
		public static final int
    	BUTTON_A = 2, BUTTON_B = 3, BUTTON_X = 1,
    	BUTTON_Y = 4, BUTTON_Z = 8,
    	BUTTON_RT = 6, BUTTON_LT = 5, BUTTON_START = 10,
    	AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 5, AXIS_RSY = 2,
    	AXIS_LT = 3, AXIS_RT = 4;
	}
	
	public static final class Gamepad_LogitechF310 {
		private Gamepad_LogitechF310(){}
        public static final int PORT = 1;
        public static final float DEADZONE = 0.08f, INVERTED = 0.0f, TRIGGERZONE = 0.0f, SENSITIVITY = 0.4f;
        public static final String MODE = "F310";

    	// Constant variables for the button codes on the F310 gamepad
    	public static final int 
    	BUTTON_A = 1, BUTTON_B = 2, BUTTON_X = 3,
    	BUTTON_Y = 4, BUTTON_LB = 5, BUTTON_RB = 6,
    	BUTTON_BACK = 7, BUTTON_START = 8, BUTTON_LS = 9, 
    	BUTTON_RS = 10, BUTTON_LT = -1, BUTTON_RT = -1,
    	AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 4, AXIS_RSY = 5, //TODO These are not correct
    	AXIS_RTRIGGER = 3, AXIS_LTRIGGER = 2;
    	
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
	
	public static final class Gamepad_Xbox360 {
		private Gamepad_Xbox360() {}
		
		public static final int PORT = 1;
		public static final float DEADZONE = 0.08f, INVERTED = 0.0f, TRIGGERZONE = 0.0f, SENSITIVITY = 0.4f;
		public static final String MODE = "Xbox360";
		
		public static final int
    	BUTTON_A = 1, BUTTON_B = 2, BUTTON_X = 3,
    	BUTTON_Y = 4, BUTTON_LB = 5, BUTTON_RB = 6,
    	BUTTON_START = 10, BUTTON_BACK = 9, 
    	BUTTON_LS = 11, BUTTON_RS = 12, 
    	AXIS_X = 0, AXIS_Y = 1, AXIS_RSX = 2, AXIS_RSY = 5,
    	AXIS_LT = 2, AXIS_RT = 3;
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