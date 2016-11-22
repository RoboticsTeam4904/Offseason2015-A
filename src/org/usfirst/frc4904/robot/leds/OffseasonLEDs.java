package org.usfirst.frc4904.robot.leds;


import org.usfirst.frc4904.standard.custom.CustomNeoPixels;

public class OffseasonLEDs extends CustomNeoPixels {
	
	public OffseasonLEDs(int id) {
		super("LEDs", id);
	}
	
	public void enable() {
		super.setMode(1);
	}
	
	public void disable() {
		super.setMode(0);
	}
}
