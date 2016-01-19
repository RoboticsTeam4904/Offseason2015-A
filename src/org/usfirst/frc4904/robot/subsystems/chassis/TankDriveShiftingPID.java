package org.usfirst.frc4904.robot.subsystems.chassis;


import org.usfirst.frc4904.logkitten.LogKitten;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;

public class TankDriveShiftingPID extends TankDriveShifting implements PIDOutput {
	AHRS ahrs;
	private PIDController pid;
	private double ySpeed;
	private double maxDegreesPerSecond;
	
	public TankDriveShiftingPID(String name, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB, SolenoidShifters shifter, double Kp, double Ki, double Kd, double maxDegreesPerSecond) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB, shifter);
		ahrs = new AHRS(SerialPort.Port.kMXP);
		pid = new PIDController(Kp, Ki, Kd, ahrs, this);
		ySpeed = 0;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		pid.setInputRange(-360.0f, 360.0f);
		pid.setOutputRange(-360.0f, 360.0f);
	}
	
	/**
	 * Overrides the "move2dc" function to implament Pid and drive straight
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
		double targetDegreesPerSecond = turnSpeed * maxDegreesPerSecond;
		pid.setSetpoint(targetDegreesPerSecond);
		ahrs.setPIDSourceType(PIDSourceType.kRate);
		pid.enable();
		LogKitten.d("pid enabled");
	}
	
	/**
	 * Returns the pid value and moves after the pid is done
	 * 
	 * @return PidValue and moves the motor
	 */
	@Override
	public void pidWrite(double PidResult) {
		LogKitten.d("pid complete");
		double turnSpeed = PidResult / maxDegreesPerSecond;
		move2dp(ySpeed, 0.0, turnSpeed);
	}
}
