package org.usfirst.frc4904.robot.humaninput.drivers;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.humaninput.Driver;

public class JoystickControl extends Driver {
	public JoystickControl() {
		super("JoystickControl");
	}

	@Override
	public void bindCommands() {

	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return RobotMap.HumanInput.Driver.xbox.leftStick.getY() * RobotMap.Constant.HumanInput.Y_SPEED_SCALE;
	}

	@Override
	public double getTurnSpeed() {
		return RobotMap.HumanInput.Driver.xbox.rightStick.getX() * RobotMap.Constant.HumanInput.TURN_SPEED_SCALE;
	}
}
