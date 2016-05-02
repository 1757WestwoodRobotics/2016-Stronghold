package org.usfirst.frc.team1757.robot;

public class Autonomous {
	public static boolean isRunning = false;

	public static void doNothing(){
		if(!isRunning){
			System.out.println("Default Auto: Do nothing");
			isRunning = true;
		}

	}
	
	public static void crossLowBar(Drive autoDrive, Winch winch) {
		if(!isRunning){
			System.out.println("crossing Low Bar");	
			//winch.doAutonomousWinch(Constants.Autonomous.WINCHSPEED);
			//autoDrive.doAutoDrive(Constants.Autonomous.LOWBARSPEED_1, Constants.Autonomous.LOWBARTIME_1);
			autoDrive.doAutoDrive(Constants.Autonomous.LOWBARSPEED_2, Constants.Autonomous.LOWBARTIME_2);
			isRunning = true;
		}
	}
	public static void crossRockWall(Drive autoDrive) {
		if(!isRunning){
			autoDrive.doAutoDrive(Constants.Autonomous.DRIVEACROSSSPEED, Constants.Autonomous.DRIVEACROSSTIME);
			System.out.println("crossing Rock Wall");
			isRunning = true;
		}
	}
	public static void crossPortcullis(Drive autoDrive) {
		//TODO: Add motor instructions
		//Move breach down to height
		//Drive forward
		//Lift up to max
		if(!isRunning){
			isRunning = true;
			System.out.println("crossing Portcullis");
		}
		
	}
	public static void crossCDF(Drive autoDrive) {
		//TODO: Add motor instructions -- Not even trying
		if(!isRunning){
			isRunning = true;
			System.out.println("crossing CDF");
		}
		
		
	}
	public static void crossRoughTerrain(Drive autoDrive) {
		if(!isRunning){
			autoDrive.doAutoDrive(Constants.Autonomous.DRIVEACROSSSPEED, Constants.Autonomous.DRIVEACROSSTIME);
			System.out.println("crossing Rough Terrain");
			isRunning = true;
		}
	}
	public static void crossMoat(Drive autoDrive) {
		if(!isRunning){
			autoDrive.doAutoDrive(Constants.Autonomous.DRIVEACROSSSPEED, Constants.Autonomous.DRIVEACROSSTIME);
			System.out.println("crossing Moat");
			isRunning = true;
		}
	}
	public static void crossRamparts(Drive autoDrive) {
		if(!isRunning){
			autoDrive.doAutoDrive(Constants.Autonomous.DRIVEACROSSSPEED, Constants.Autonomous.DRIVEACROSSTIME);
			System.out.println("crossing Ramparts");
			isRunning = true;
		}
	}
	public static void crossDrawbridge(Drive autoDrive) {
		//Drive up all the way to it
		//Lower arm faster than driving back until arm is all the way down
		//Drive forward
		if(!isRunning){
			isRunning = true;
			System.out.println("crossing Drawbridge");
		}
	}
	public static void crossSallyPort(Drive autoDrive) {
		//Drive up all the way
		//Move arm down
		//Flick open 
		//knock into door
		//straighten out
		//Drive forward
		if(!isRunning){
			isRunning = true;
			System.out.println("crossing SallyPort");
		}
		
	}
	//TODO: Clean up
	public static void executeAutonomous(Defenses defense, Drive autoDrive, Winch winch) {
		switch (defense) {
		case LOW_BAR: 
			crossLowBar(autoDrive, winch); 
			break;
		case ROCK_WALL: 
			crossRockWall(autoDrive); 
			break;
		case ROUGH_TERRAIN:
			crossRoughTerrain(autoDrive);
			break;
		case PORTCULLIS:
			crossPortcullis(autoDrive);
			break;
		case CHEVAL_DE_FRISE: 
			crossCDF(autoDrive);
			break;
		case MOAT: 
			crossMoat(autoDrive);
			break;
		case RAMPARTS:
			crossRamparts(autoDrive);
			break;
		case DRAWBRIDGE:
			crossDrawbridge(autoDrive);
			break;
		case SALLY_PORT:
			crossSallyPort(autoDrive);
			break;
		default: 
			System.out.println("NO DEFENSE SELECTED");
			break; 
		}
	}
}
