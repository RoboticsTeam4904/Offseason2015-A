package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.standard.humaninterface.Driver;
import org.usfirst.frc4904.standard.commands.Kill;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;

public class Nathan extends Driver {
	public Nathan() {
		super("Nathan"); // supernathan!
	}

	public void bindCommands() {
		DriverStationMap.xbox.back.toggleWhenPressed(new Kill(new ChassisIdle(RobotMap.chassis)));
		DriverStationMap.xbox.lb.toggleWhenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.DOWN));
		DriverStationMap.xbox.rb.toggleWhenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.UP));
	}
}
