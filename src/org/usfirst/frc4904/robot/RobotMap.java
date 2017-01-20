package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.leds.OffseasonLEDs;
import org.usfirst.frc4904.standard.custom.controllers.CustomJoystick;
import org.usfirst.frc4904.standard.custom.controllers.CustomXbox;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import com.ctre.CANTalon;

public class RobotMap {
	public static class Port {
		public static class Motors {
			public static class CAN {
				public static int leftDriveA = 3;
				public static int leftDriveB = 4;
				public static int rightDriveA = 3;
				public static int rightDriveB = 4;
			}
		}

		public static class HumanInput {
			public static final int joystick = 0;
			public static final int xboxController = 1;
		}
		
		public static class CAN {
			public static final int backLeds = 0x601;
			public static final int frontLeds = 0x602;
		}
	}
	
	public static class Component {
		public static Motor flywheel;
		public static Motor elevator;
		public static OffseasonLEDs backLeds;
		public static OffseasonLEDs frontLeds;
	}
	
	public static class HumanInput {
		public static class Driver {
			public static CustomXbox xbox;
		}
		
		public static class Operator {
			public static CustomJoystick stick;
		}
	}
	
	public RobotMap() {
		Component.flywheel = new Motor("Flywheel", false, new CANTalon(Port.Motors.CAN.rightDriveA), new CANTalon(Port.Motors.CAN.rightDriveB));
		Component.elevator = new Motor("Elevator", false, new CANTalon(Port.Motors.CAN.leftDriveA), new CANTalon(Port.Motors.CAN.leftDriveB));
		HumanInput.Driver.xbox = new CustomXbox(Port.HumanInput.xboxController);
		HumanInput.Operator.stick = new CustomJoystick(Port.HumanInput.joystick);
		Component.backLeds = new OffseasonLEDs(Port.CAN.backLeds);
		Component.frontLeds = new OffseasonLEDs(Port.CAN.frontLeds);
	}
}
