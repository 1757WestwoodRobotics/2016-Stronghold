/**
 * TODO: Finish this
 */

package org.usfirst.frc.team1757.robot;

public class Autonomous {

	public static void printAutoMessages() {
		
	}
	public static void crossLowBar(Drive autoDrive) {
		autoDrive.doAutoDrive(.3, 2);
	}
	public static void crossRockWall(Drive autoDrive) {

	}
	public static void crossPortcullis(Drive autoDrive) {
		
	}
	public static void crossCDF(Drive autoDrive) {
		
	}
	public static void crossMoat(Drive autoDrive) {
		
	}
	public static void crossRamparts(Drive autoDrive) {
		
	}
	public static void crossDrawbridge(Drive autoDrive) {
		
	}
	public static void crossSallyPort(Drive autoDrive) {
		
	}
	
	public static void executeAutonomous(Defenses defense, Drive autoDrive) {
		
		switch (defense) {
		case LOW_BAR: crossLowBar(autoDrive);
		case ROCK_WALL: crossRockWall(autoDrive);
		default: System.out.println("NO DEFENSE SELECTED");
			break; 
		}
	}
}
