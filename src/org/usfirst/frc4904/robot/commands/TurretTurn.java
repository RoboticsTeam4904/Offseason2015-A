package org.usfirst.frc4904.robot.commands;


import org.usfirst.frc4904.robot.RobotMap;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorControl;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

public class TurretTurn extends MotorControl {
	protected final int TURRET_MAX = 11100;
	protected final int TURRET_MIN = 0;
	protected final int SAFETY_MARGIN = 100;

	public TurretTurn(Motor motor, Controller controller, int axis) {
		super(motor, controller, axis, 0.65);
	}
	
	@Override
	protected void execute() {
		double desiredSpeed = controller.getAxis(axis) * scale;
		double safetyMargin = 500.0;
		LogKitten.e("TurretTurn executing: " + desiredSpeed + " " + RobotMap.Component.turretEncoder.get() + " " + safetyMargin);
		if (RobotMap.Component.turretEncoder.get() >= TURRET_MAX - safetyMargin) {
			if (desiredSpeed > 0) {
				motor.set(desiredSpeed);
			} else {
				motor.set(0);
			}
		} else if (RobotMap.Component.turretEncoder.get() <= TURRET_MIN + safetyMargin) {
			if (desiredSpeed < 0) {
				motor.set(desiredSpeed);
			} else {
				motor.set(0);
			}
		} else {
			motor.set(desiredSpeed);
		}
	}
}
