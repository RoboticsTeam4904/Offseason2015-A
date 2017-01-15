package org.usfirst.frc4904.robot.commands;


import java.util.ArrayList;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;

public class ControlAgitate implements ChassisController {
	protected static final double PRE_WAIT_TIME_MILLIS = 3000;
	protected static final double AGITATE_TIME_MILLIS = 150;
	protected final IMU imu;
	public final ArrayList<ControlAgitatePoint> pointlist;
	protected double startTime;
	
	public ControlAgitate(IMU imu) {
		this.imu = imu;
		pointlist = new ArrayList<ControlAgitatePoint>();
		startTime = -1;
	}
	
	public class ControlAgitatePoint {
		public double speed;
		public double sensorValue;
		
		public ControlAgitatePoint(double speed, double sensorValue) {
			this.speed = speed;
			this.sensorValue = sensorValue;
		}
	}
	
	protected double timeSinceInitialized() {
		if (startTime == -1) {
			startTime = System.currentTimeMillis();
		}
		return System.currentTimeMillis() - startTime;
	}
	
	@Override
	public double getX() {
		return 0;
	}
	
	@Override
	public double getY() {
		return 0;
	}
	
	@Override
	public double getTurnSpeed() {
		double t = timeSinceInitialized();
		double speed;
		if (t < ControlAgitate.PRE_WAIT_TIME_MILLIS) {
			speed = 0;
		} else if (t > ControlAgitate.PRE_WAIT_TIME_MILLIS && t < ControlAgitate.PRE_WAIT_TIME_MILLIS + ControlAgitate.AGITATE_TIME_MILLIS) {
			speed = 1;
		} else {
			speed = 0;
		}
		double sensorValue = imu.getYaw();
		pointlist.add(new ControlAgitatePoint(speed, sensorValue));
		return speed;
	}
}
