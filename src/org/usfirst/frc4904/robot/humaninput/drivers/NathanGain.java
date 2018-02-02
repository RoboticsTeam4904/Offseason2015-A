package org.usfirst.frc4904.robot.humaninput.drivers;

import org.usfirst.frc4904.standard.humaninput.Driver;

public class NathanGain extends Driver {
	public NathanGain() {
		super("NathanGain");
	}

	protected double scaleGain(double input, double gain, double exp) {
		return Math.pow(input, exp) * gain * Math.signum(input);
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public double getTurnSpeed() {
		return 0;
	}

	@Override
	public void bindCommands() {
		// TODO Auto-generated method stub

	}
}
