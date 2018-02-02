package org.usfirst.frc4904.robot.humaninput.drivers;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.humaninput.Driver;

public class PureStick extends Driver {
	public PureStick() {
		super("PureStick");
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
		return RobotMap.HumanInput.Operator.stick.getY() * RobotMap.Constant.HumanInput.Y_SPEED_SCALE;
	}

	@Override
	public double getTurnSpeed() {
		return RobotMap.HumanInput.Operator.stick.getX() * RobotMap.Constant.HumanInput.TURN_SPEED_SCALE;
	}
}
