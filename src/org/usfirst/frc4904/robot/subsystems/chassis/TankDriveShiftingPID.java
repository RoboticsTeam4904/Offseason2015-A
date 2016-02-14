package org.usfirst.frc4904.robot.subsystems.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.NavX;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;

public class TankDriveShiftingPID extends TankDriveShifting implements PIDOutput {
	private NavX ahrs;
	public static PIDController pid;
	private double ySpeed;
	private final double maxDegreesPerSecond;
	private double targetYaw;
	private double count;
	private double lastUpdate;
	
	public TankDriveShiftingPID(String name, Motor leftWheel, Motor rightWheel, SolenoidShifters shifter, double Kp, double Ki, double Kd, double maxDegreesPerSecond) {
		super(name, leftWheel, rightWheel, shifter);
		ahrs = new NavX(SerialPort.Port.kMXP);
		ahrs.reset();
		ahrs.resetDisplacement();
		ahrs.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PIDController(Kp, Ki, Kd, ahrs, this);
		ySpeed = 0;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		pid.setInputRange(-180.0f, 180.0f);
		pid.setOutputRange(-1.0f, 1.0f);
		pid.setContinuous(true);
		LogKitten.setPrintMute(true);
		LogKitten.w("chassis created", true);
		pid.enable();
		targetYaw = ahrs.getYaw();
		count = 0;
	}
	
	public void reset() {
		targetYaw = ahrs.getYaw();
		pid.reset();
		pid.enable();
		count = 0;
	}
	
	/**
	 * Overrides the "move2dc" function to implement PID and drive straight
	 * 
	 * @param xSpeed
	 *        Not used since this does not move sideways
	 * @param ySpeed
	 * @param turnSpeed
	 * 		
	 */
	@Override
	public void move2dc(double xSpeed, double ySpeed, double turnSpeed) {
		this.ySpeed = ySpeed;
		targetYaw = targetYaw + ((turnSpeed * maxDegreesPerSecond) * ((System.currentTimeMillis() * 1000) - lastUpdate));
		lastUpdate = System.currentTimeMillis() * 1000;
		if (targetYaw > 180) {
			targetYaw = -180 + (Math.abs(targetYaw) - 180);
		} else if (targetYaw < -180) {
			targetYaw = 180 - (Math.abs(targetYaw) - 180);
		}
		LogKitten.w("Target Yaw: " + targetYaw, true);
		pid.setSetpoint(targetYaw);
	}
	
	@Override
	public void pidWrite(double pidOutput) {
		count += 1;
		double pidresult = pidOutput;
		pidresult = Math.max(Math.min(pidresult, 1), -1);
		if (count >= 3) {
			LogKitten.w("PID Out: " + pidresult + ", Setpoint: " + pid.getSetpoint() + ", Error: " + pid.getError() + "\n", true);
			count = 0;
		}
		super.move2dc(0.0, this.ySpeed, pidresult);
	}
}
