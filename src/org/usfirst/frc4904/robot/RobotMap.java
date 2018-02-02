package org.usfirst.frc4904.robot;

import org.usfirst.frc4904.standard.custom.CustomCAN;
import org.usfirst.frc4904.standard.custom.controllers.CustomJoystick;
import org.usfirst.frc4904.standard.custom.controllers.CustomXbox;
import org.usfirst.frc4904.standard.custom.sensors.PDP;

public class RobotMap {
	public static class Port {
		public static class Motors {
			public static class CAN {
				public static int leftDriveA = 1;
				public static int leftDriveB = 2;
				public static int rightDriveA = 3;
				public static int rightDriveB = 4;
			}
		}

		public static class Pneumatics {
			public static int solenoidUp = 0;
			public static int solenoidDown = 1;
		}

		public static class HumanInput {
			public static final int joystick = 0;
			public static final int xboxController = 1;
		}

		public static class CAN {
			public static final int backLeds = 0x601;
			public static final int frontLeds = 0x602;
			public static final int leftEncoder = 0x611;
			public static final int rightEncoder = 0x612;
		}
	}

	public static class Constant {
		public static class HumanInput {
			public static final double Y_SPEED_SCALE = 1;
			public static final double TURN_SPEED_SCALE = 1;
			public static final double XBOX_MINIMUM_THRESHOLD = 0.1;
			public static final double SPEED_GAIN = 1;
			public static final double SPEED_EXP = 2;
			public static final double TURN_GAIN = 1;
			public static final double TURN_EXP = 2;
		}

		public static class Chassis {
			public static double TURN_P = 0.02;
			public static double TURN_I = 0.001;
			public static double TURN_D = 0.3;
			public static final double MAX_DEGREES_PER_SECOND = 120;
			public static final double WHEEL_RADIUS_INCHES = 8;
			public static final double WHEEL_PULSES_PER_REVOLUTION = 1024;
		}
	}

	public static class Component {
		public static CustomCAN testCAN;

		public static PDP pdp;
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
		Component.pdp = new PDP();
		Component.testCAN = new CustomCAN("testCAN", 0x654);

	}
}
