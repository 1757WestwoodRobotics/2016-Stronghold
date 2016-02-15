/**
 * Huge thanks to kmodos on Chief Delphi for the camera switching code!
 */

package org.usfirst.frc.team1757.robot;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class MultiCam {
	/**
	 * The image to push to the CameraServer
	 */
	private Image frame;

	/**
	 * The list of all attached cameras; list of attached camera names
	 */
	private ArrayList<USBCamera> cams;
	private ArrayList<String> camNames;

	/**
	 * The index of the current camera we are looking at
	 */
	private int currCam;

	/**
	 * The current camera we are viewing.
	 */
	private USBCamera cam;

	/**
	 * The maximum fps for all of the cameras
	 */
	private int MAX_FPS;

	/**
	 * The quality of image to push back to the driver station. Lower numbers save more bandwidth (0-100)
	 */
	private int QUALITY;
		
	/**
	 * Time to sleep after changing camera views. This is to prevent errors as USBCamera.startCapture() returns before it is ready to be seen 
	 */
	private long SLEEP_TIME;
	
	public MultiCam(ArrayList<USBCamera> CAMS, ArrayList<String> CAMNAMES, int CURRCAM, int _MAX_FPS, int _QUALITY, int _SLEEP_TIME) {
		cams = CAMS;
		camNames = CAMNAMES;
		currCam = CURRCAM;
		MAX_FPS = _MAX_FPS;
		QUALITY = _QUALITY;
		SLEEP_TIME = _SLEEP_TIME;
	}
	
	public MultiCam(int CURRCAM, int _MAX_FPS, int _QUALITY, int _SLEEP_TIME) {
		cams = new ArrayList<USBCamera>();
		camNames = new ArrayList<String>();
		currCam = CURRCAM;
		MAX_FPS = _MAX_FPS;
		QUALITY = _QUALITY;
		SLEEP_TIME = _SLEEP_TIME;
	}
	
	public MultiCam() {
		cams = new ArrayList<USBCamera>();
		camNames = new ArrayList<String>();
		MAX_FPS = 30;
		QUALITY = 10;
		SLEEP_TIME = 100;
	}
	
	public void camInit() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		currCam = 0;
		CameraServer.getInstance().setQuality(QUALITY);
		/*
		for(int i = 0; i < camNames.size(); i++) {
			addCamera(camNames.get(i));
		}
		*/
		cam = cams.get(currCam);
		cam.openCamera();
		cam.startCapture();
	}
	
	public void runCam() {
		cams.get(currCam).getImage(frame);
		CameraServer.getInstance().setImage(frame);
	}
	
	/**
	 * Adds the camera to our list to switch between and sets the FPS max
	 * @param camName The name of the camera
	 */
	public void addCamera(String camName){
		USBCamera temp = new USBCamera(camName);
		temp.setFPS(MAX_FPS);
		cams.add(temp);
		temp = null;
		camNames.add(camName);
	}
	
	
	/**
	 * Switch to the next camera in our ArrayList
	 */
	public void switchCamera(){
		try{
			cam.stopCapture();
			cam.closeCamera();
			currCam++;
			currCam %= cams.size();
			cam = cams.get(currCam);
			cam.openCamera();
			cam.startCapture();
			Thread.sleep(SLEEP_TIME);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	
}
