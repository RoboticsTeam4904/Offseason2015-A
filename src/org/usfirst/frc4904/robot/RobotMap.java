package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.standard.custom.controllers.CustomJoystick;
import org.usfirst.frc4904.standard.custom.controllers.CustomXbox;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.AccelerationCap;
import edu.wpi.first.wpilibj.CANTalon;

public class RobotMap {
	public static class Port {
		public static class Motors {
			public static class CAN {
				public static int rightDriveA = 1;
				public static int rightDriveB = 2;
				public static int leftDriveA = 3;
				public static int leftDriveB = 4;
			}
		}
		
		public static class Pneumatics {
			public static int solenoidUp = 7;
			public static int solenoidDown = 6;
		}
		
		public static class HumanInput {
			public static final int joystick = 0;
			public static final int xboxController = 1;
		}
	}
	
	public static class Constant {
		public static class HumanInput {
			public static final double X_SPEED_SCALE = 1;
			public static final double Y_SPEED_SCALE = 1;
			public static final double TURN_SPEED_SCALE = 1;
			public static final double XBOX_MINIMUM_THRESHOLD = 0.1;
			public static final double SPEED_GAIN = 1;
			public static final double SPEED_EXP = 2;
			public static final double TURN_GAIN = 1;
			public static final double TURN_EXP = 2;
		}
	}
	
	public static class Component {
		public static Motor leftWheel;
		public static Motor rightWheel;
		public static SolenoidShifters shifter;
		public static TankDriveShifting chassis;
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
		Component.shifter = new SolenoidShifters(Port.Pneumatics.solenoidUp, Port.Pneumatics.solenoidDown);
		Component.leftWheel = new Motor("LeftWheel", false, new AccelerationCap(Component.pdp), new CANTalon(Port.Motors.CAN.leftDriveA), new CANTalon(Port.Motors.CAN.leftDriveB));
		Component.rightWheel = new Motor("RightWheel", true, new AccelerationCap(Component.pdp), new CANTalon(Port.Motors.CAN.rightDriveA), new CANTalon(Port.Motors.CAN.rightDriveB));
		Component.chassis = new TankDriveShifting("OffseasonChassis", Component.leftWheel, Component.rightWheel, Component.shifter);
		HumanInput.Driver.xbox = new CustomXbox(Port.HumanInput.xboxController);
		HumanInput.Operator.stick = new CustomJoystick(Port.HumanInput.joystick);
	}
}
