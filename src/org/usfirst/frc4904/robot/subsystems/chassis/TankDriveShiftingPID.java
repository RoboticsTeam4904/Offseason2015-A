package org.usfirst.frc4904.robot.subsystems.chassis;


import org.usfirst.frc4904.logkitten.LogKitten;
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
	private PIDController pid;
	private double ySpeed;
	private final double maxDegreesPerSecond;
	private final double navxUpdateRate;
	
	public TankDriveShiftingPID(String name, Motor leftWheel, Motor rightWheel, SolenoidShifters shifter, double Kp, double Ki, double Kd, double maxDegreesPerSecond) {
		super(name, leftWheel, rightWheel, shifter);
		navxUpdateRate = 60.0;
		ahrs = new NavX(SerialPort.Port.kMXP);
		ahrs.reset();
		ahrs.resetDisplacement();
		ahrs.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PIDController(Kp, Ki, Kd, ahrs, this);
		ySpeed = 0;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		pid.setInputRange(-360.0f / navxUpdateRate, 360.0f / navxUpdateRate);
		pid.setOutputRange(-360.0f / navxUpdateRate, 360.0f / navxUpdateRate);
		LogKitten.setPrintMute(true);
		LogKitten.w("chassis created", true);
		pid.enable();
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
		double targetDegreesPerSecond = (turnSpeed * maxDegreesPerSecond) / navxUpdateRate;
		pid.setSetpoint(targetDegreesPerSecond);
	}
	
	@Override
	public void pidWrite(double pidOutput) {
		double pidresult = (pidOutput / maxDegreesPerSecond) * navxUpdateRate;
		pidresult = Math.max(Math.min(pidresult, 1), -1);
		LogKitten.w("PID Out: " + pidresult + ", Raw PID Out: " + pidOutput + ", Direct pidGet(): " + ahrs.pidGet() + ", Setpoint: " + pid.getSetpoint() + ", Error: " + pid.getError(), true);
		super.move2dc(0.0, ySpeed, pidresult);
	}
}
