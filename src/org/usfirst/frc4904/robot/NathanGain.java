package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.standard.commands.Kill;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import org.usfirst.frc4904.standard.humaninterface.Driver;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;

public class NathanGain extends Driver {
	public NathanGain() {
		super("NathanGain");
	}
	
	public void bindCommands() {
		DriverStationMap.xbox.back.whenPressed(new Kill(new ChassisIdle(RobotMap.chassis)));
		DriverStationMap.xbox.a.whenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.DOWN));
		DriverStationMap.xbox.b.whenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.UP));
	}
	
	public double getX() {
		return 0;
	}
	
	public double getY() {
		double speed = DriverStationMap.xbox.rt.getX() - DriverStationMap.xbox.lt.getX();
		if (speed < 0) {
			speed = Math.pow(speed, DriverStationMap.SPEED_EXP);
			speed *= -DriverStationMap.SPEED_GAIN;
		} else {
			speed = Math.pow(speed, DriverStationMap.SPEED_EXP);
			speed *= DriverStationMap.SPEED_GAIN;
		}
		return speed;
	}
	
	public double getTurnSpeed() {
		double turnSpeed = DriverStationMap.xbox.leftStick.getX();
		if (turnSpeed < 0) {
			turnSpeed = Math.pow(turnSpeed, DriverStationMap.TURN_EXP);
			turnSpeed *= -DriverStationMap.TURN_GAIN;
		} else {
			turnSpeed = Math.pow(turnSpeed, DriverStationMap.TURN_EXP);
			turnSpeed *= DriverStationMap.TURN_GAIN;
		}
		return turnSpeed;
	}
}
