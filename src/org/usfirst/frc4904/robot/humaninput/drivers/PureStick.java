package org.usfirst.frc4904.robot.humaninput.drivers;


import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import org.usfirst.frc4904.standard.humaninput.Driver;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;

public class PureStick extends Driver {
	public PureStick() {
		super("PureStick");
	}
	
	public void bindCommands() {
		RobotMap.HumanInput.Operator.stick.button1.whenPressed(new ChassisShift(RobotMap.Component.chassis.getShifter(), SolenoidShifters.ShiftState.UP));
		RobotMap.HumanInput.Operator.stick.button2.whenPressed(new ChassisShift(RobotMap.Component.chassis.getShifter(), SolenoidShifters.ShiftState.DOWN));
	}
	
	public double getX() {
		return 0;
	}
	
	public double getY() {
		return RobotMap.HumanInput.Operator.stick.getY();
	}
	
	public double getTurnSpeed() {
		return RobotMap.HumanInput.Operator.stick.getX();
	}
}
