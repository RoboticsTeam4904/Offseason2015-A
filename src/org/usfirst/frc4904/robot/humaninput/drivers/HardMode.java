package org.usfirst.frc4904.robot.humaninput.drivers;

import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.humaninput.Driver;

public class HardMode extends Driver {
	public HardMode() {
		super("Hard Mode");
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
		if (RobotMap.HumanInput.Driver.xbox.x.get()) {
			return (RobotMap.HumanInput.Driver.xbox.lt.getX() + RobotMap.HumanInput.Driver.xbox.rt.getX() - 1)
					* RobotMap.Constant.HumanInput.Y_SPEED_SCALE;
		} else {
			return 0;
		}
	}

	@Override
	public double getTurnSpeed() {
		if (RobotMap.HumanInput.Driver.xbox.x.get()) {
			return (RobotMap.HumanInput.Driver.xbox.lt.getX() - RobotMap.HumanInput.Driver.xbox.rt.getX())
					* RobotMap.Constant.HumanInput.TURN_SPEED_SCALE;
		} else {
			return 0;
		}
	}
}
