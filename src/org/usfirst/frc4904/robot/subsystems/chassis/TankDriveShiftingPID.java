package org.usfirst.frc4904.robot.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import com.kauailabs.navx_mxp.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SerialPort;

public class TankDriveShiftingPID extends TankDriveShifting implements PIDOutput {
	AHRS ahrs;
	private PIDController pid;
	private double ySpeed;
	
	public TankDriveShiftingPID(String name, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB, SolenoidShifters shifter, double Kp, double Ki, double Kd) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB, shifter);
		ahrs = new AHRS(new SerialPort(57600, SerialPort.Port.kMXP));
		pid = new PIDController(Kp, Ki, Kd, ahrs, this);
		ySpeed = 0;
		pid.enable();
		pid.setInputRange(-180.0f, 180.0f);
		pid.setOutputRange(-1.0, 1.0);
		pid.setContinuous(true);
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
		pid.setSetpoint(turnSpeed);
	}
	
	/**
	 * Returns the pid value and moves after the pid is done
	 * 
	 * @return PidValue and moves the motor
	 */
	@Override
	public void pidWrite(double turnSpeed) {
		move2dp(ySpeed, 0.0, turnSpeed);
	}
}
