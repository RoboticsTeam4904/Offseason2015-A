package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.cmdbased.Driver;
import org.usfirst.frc4904.cmdbased.commands.Kill;
import org.usfirst.frc4904.cmdbased.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.cmdbased.commands.chassis.ChassisShift;
import org.usfirst.frc4904.cmdbased.subsystems.chassis.SolenoidShifters;

public class Nathan extends Driver {
	public Nathan() {
		super("Nathan"); // supernathan!
		DriverStationMap.xbox.back.toggleWhenPressed(new Kill(new ChassisIdle(RobotMap.chassis)));
		DriverStationMap.xbox.lb.toggleWhenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.DOWN));
		DriverStationMap.xbox.rb.toggleWhenPressed(new ChassisShift(RobotMap.chassis.getShifter(), SolenoidShifters.ShiftState.UP));
	}
}
