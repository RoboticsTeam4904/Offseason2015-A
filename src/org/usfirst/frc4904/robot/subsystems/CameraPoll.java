package org.usfirst.frc4904.robot.subsystems;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public class CameraPoll extends Command {
	public static final double MINIMUM_POLL_BREAK = 30;
	protected Long lastTime;
	protected URL cameraURL;
	protected final Camera camera;
	
	public CameraPoll(String name, Camera camera) {
		super(name);
		requires(camera);
		this.camera = camera;
		try {
			cameraURL = new URL(camera.getCameraProtocol(), camera.getCameraIP(), camera.getCameraPort(), camera.getCameraPath());
		}
		catch (MalformedURLException e) {
			LogKitten.ex(e);
		}
		setRunWhenDisabled(true);
		setInterruptible(false);
	}
	
	public CameraPoll(Camera camera) {
		this("CameraPoll", camera);
	}
	
	@Override
	protected void initialize() {
		LogKitten.v("Camera is starting to poll...");
		lastTime = System.currentTimeMillis();
	}
	int i = 0;

	@Override
	protected void execute() {
		i++;
		LogKitten.d("CameraPoll execute start " + i, true);
		double total = System.currentTimeMillis() - lastTime;
		if (total < CameraPoll.MINIMUM_POLL_BREAK) {
			return;
		}
		LogKitten.d("A" + i, true);
		try {
			HttpURLConnection connection = (HttpURLConnection) cameraURL.openConnection();
			connection.setRequestMethod(RobotMap.Constant.Network.CONNECTION_METHOD_GET);
			connection.setRequestProperty("UserAgent", "CAMERA-SUBSYSTEM");
			LogKitten.d("B" + i, true);
			int responseCode = connection.getResponseCode();
			LogKitten.d("C" + i + "|" + responseCode, true);
			LogKitten.v("Camera poll request: " + cameraURL);
			LogKitten.v("Camera poll response (code): " + responseCode);
			if (responseCode == 200) {
				camera.setCameraStatus(Camera.CameraStatus.CONNECTED);
				BufferedReader connectionReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer responseBuffer = new StringBuffer();
				String input;
				while ((input = connectionReader.readLine()) != null) {
					responseBuffer.append(input);
				}
				connectionReader.close();
				String response = responseBuffer.toString().trim();
				LogKitten.v("Camera poll response (total): " + response);
				camera.setCameraData(response);
				String[] cameraVariables = response.split("::");
				if (cameraVariables.length == 3) {
					boolean canSeeGoal = cameraVariables[0].equals(RobotMap.Constant.Network.PI_IR_STATUS_GOOD) ? true : false;
					LogKitten.v("Got camera vision status of " + canSeeGoal);
					Double degreesToTurn = Math.toDegrees(Double.parseDouble(cameraVariables[1]));
					LogKitten.v("Got radians to turn of " + cameraVariables[1] + " and degrees to turn of " + degreesToTurn);
					Double distanceToMove = Double.parseDouble(cameraVariables[2]);
					LogKitten.v("Got distance to move of " + cameraVariables[2] + " and rounded of " + distanceToMove);
					camera.setCameraCanSeeGoal(canSeeGoal);
					camera.setGoalOffAngle(degreesToTurn);
					camera.setGoalOffDistance(distanceToMove);
				} else {
					LogKitten.e("Got " + cameraVariables.length + " parameters from IR Camera, expected 3", true);
					LogKitten.e("Camera Data: " + response, true);
					LogKitten.e("Camera Variables: " + cameraVariables, true);
				}
			} else {
				camera.setCameraStatus(Camera.CameraStatus.DISCONNECTED);
				camera.setCameraData(RobotMap.Constant.Network.CONNECTION_ERROR_MESSAGE);
				LogKitten.d("D" + i, true);
				LogKitten.e("Could not establish connection to camera using request '" + cameraURL.toString() + "'", true);
			}
		}
		catch (MalformedURLException e) {
			LogKitten.ex(e);
			LogKitten.d("E" + i, true);
			LogKitten.d("MalformedURLException", true);
		}
		catch (IOException e) {
			LogKitten.e("URL is " + cameraURL.toString(), true);
			LogKitten.ex(e, true);
			LogKitten.d("F" + i, true);
			LogKitten.d("IOException", true);
		}
		lastTime = System.currentTimeMillis();
		LogKitten.d("CameraPoll execute end", true);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}
}
