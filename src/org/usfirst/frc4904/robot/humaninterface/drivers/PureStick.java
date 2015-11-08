package org.usfirst.frc4904.robot.humaninterface.drivers;


import org.usfirst.frc4904.robot.DriverStationMap;
import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import org.usfirst.frc4904.standard.humaninterface.Driver;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;

public class PureStick extends Driver {
	public PureStick() {
		super("PureStick");
	}
	
	public void bindCommands() {
		DriverStationMap.stick.button1.whenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.UP));
		DriverStationMap.stick.button2.whenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.DOWN));
	}
	
	public double getX() {
		return 0;
	}
	
	public double getY() {
		return DriverStationMap.stick.getY();
	}
	
	public double getTurnSpeed() {
		return DriverStationMap.stick.getX();
	}
}
