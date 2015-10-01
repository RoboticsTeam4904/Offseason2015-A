package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.custom.controllers.XboxController;

public class TankXbox extends XboxController implements Controller {
	public TankXbox(final int port) {
		super(port);
	}
	
	public double getAxis(int axis) {
		switch (axis) {
			case 0:
				return 0; // No X movement for Tank Drive
			case 1:
				return this.rt.getX() - this.lt.getX();
			case 2:
				return this.leftStick.getX();
			default:
				return super.getRawAxis(axis);
		}
	}
}
