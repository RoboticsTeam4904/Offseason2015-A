package org.usfirst.frc4904.robot.humaninput.drivers;


import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.Kill;
import org.usfirst.frc4904.standard.commands.KittenCommand;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMotionProfileDistance;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMove;
import org.usfirst.frc4904.standard.commands.chassis.ChassisShift;
import org.usfirst.frc4904.standard.commands.chassis.ChassisTurnAbsolute;
import org.usfirst.frc4904.standard.humaninput.Driver;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

public class NathanGain extends Driver {
	public NathanGain() {
		super("NathanGain");
	}

	protected double scaleGain(double input, double gain, double exp) {
		return Math.pow(input, exp) * gain * Math.signum(input);
	}

	@Override
	public void bindCommands() {
		Command normalDrive = new ChassisMove(RobotMap.Component.chassis, this);
		RobotMap.HumanInput.Driver.xbox.back.whenPressed(new Kill(new ChassisIdle(RobotMap.Component.chassis)));
		RobotMap.HumanInput.Driver.xbox.a.whenPressed(new ChassisShift(RobotMap.Component.chassis.getShifter(), SolenoidShifters.ShiftState.DOWN));
		RobotMap.HumanInput.Driver.xbox.b.whenPressed(new ChassisShift(RobotMap.Component.chassis.getShifter(), SolenoidShifters.ShiftState.UP));
		// D-Pad align
		RobotMap.HumanInput.Driver.xbox.dPad.up.whenPressed(new ChassisTurnAbsolute(RobotMap.Component.chassis, 0, RobotMap.Component.navx, RobotMap.Component.chassisMC));
		RobotMap.HumanInput.Driver.xbox.dPad.up.whenReleased(normalDrive);
		RobotMap.HumanInput.Driver.xbox.dPad.down.whenPressed(new ChassisTurnAbsolute(RobotMap.Component.chassis, 180, RobotMap.Component.navx, RobotMap.Component.chassisMC));
		RobotMap.HumanInput.Driver.xbox.dPad.down.whenReleased(normalDrive);
		RobotMap.HumanInput.Driver.xbox.dPad.left.whenPressed(new ChassisTurnAbsolute(RobotMap.Component.chassis, 90, RobotMap.Component.navx, RobotMap.Component.chassisMC));
		RobotMap.HumanInput.Driver.xbox.dPad.left.whenReleased(normalDrive);
		RobotMap.HumanInput.Driver.xbox.dPad.right.whenPressed(new ChassisTurnAbsolute(RobotMap.Component.chassis, 270, RobotMap.Component.navx, RobotMap.Component.chassisMC));
		RobotMap.HumanInput.Driver.xbox.dPad.right.whenReleased(normalDrive);
		// Auto drive 2 feet
		RobotMap.HumanInput.Driver.xbox.rb.whenPressed(new ChassisMotionProfileDistance(RobotMap.Component.chassis, 800, RobotMap.Component.chassisEncoderMC, new KittenCommand("Drive Encoder Error", LogKitten.LEVEL_WTF), RobotMap.Component.leftEncoder, RobotMap.Component.rightEncoder));
		RobotMap.HumanInput.Driver.xbox.rb.whenReleased(normalDrive);
		RobotMap.HumanInput.Driver.xbox.lb.whenPressed(new ChassisMotionProfileDistance(RobotMap.Component.chassis, -800, RobotMap.Component.chassisEncoderMC, new KittenCommand("Drive Encoder Error", LogKitten.LEVEL_WTF), RobotMap.Component.leftEncoder, RobotMap.Component.rightEncoder));
		RobotMap.HumanInput.Driver.xbox.lb.whenReleased(normalDrive);
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		double rawSpeed = RobotMap.HumanInput.Driver.xbox.rt.getX() - RobotMap.HumanInput.Driver.xbox.lt.getX();
		double speed = scaleGain(rawSpeed, RobotMap.Constant.HumanInput.SPEED_GAIN, RobotMap.Constant.HumanInput.SPEED_EXP) * RobotMap.Constant.HumanInput.Y_SPEED_SCALE;
		return speed;
	}

	@Override
	public double getTurnSpeed() {
		double rawTurnSpeed = RobotMap.HumanInput.Driver.xbox.leftStick.getX();
		double turnSpeed = scaleGain(rawTurnSpeed, RobotMap.Constant.HumanInput.TURN_GAIN, RobotMap.Constant.HumanInput.TURN_EXP) * RobotMap.Constant.HumanInput.TURN_SPEED_SCALE;
		return turnSpeed;
	}
}
