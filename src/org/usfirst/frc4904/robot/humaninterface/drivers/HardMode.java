package org.usfirst.frc4904.robot.humaninterface.drivers;

import org.usfirst.frc4904.robot.DriverStationMap;
import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.commands.Kill;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import org.usfirst.frc4904.standard.humaninterface.Driver;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;

public class HardMode extends Driver {
	
	public HardMode() {
		super("Hard Mode");
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
		return DriverStationMap.xbox.lt.getX() + DriverStationMap.xbox.rt.getX() - 1;
	}
	
	public double getTurnSpeed() {
		return DriverStationMap.xbox.lt.getX() - DriverStationMap.xbox.rt.getX();
	}

}
