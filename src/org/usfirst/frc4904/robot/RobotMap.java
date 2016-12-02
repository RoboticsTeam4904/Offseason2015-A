package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.leds.OffseasonLEDs;
import org.usfirst.frc4904.standard.custom.controllers.CustomJoystick;
import org.usfirst.frc4904.standard.custom.controllers.CustomXbox;
import org.usfirst.frc4904.standard.custom.sensors.CANEncoder;
import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.custom.sensors.NavX;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.AccelerationCap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotMap {
	public static class Port {
		public static class Motors {
			public static class CAN {
				public static int leftDriveA = 1;
				public static int leftDriveB = 2;
				public static int rightDriveA = 3;
				public static int rightDriveB = 4;
			}
			
			public static class PWM {
				public static int turrent = 0;
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
			public static final int rightEncoder = 0x610;
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
		public static Motor leftWheel;
		public static CustomEncoder leftEncoder;
		public static Motor rightWheel;
		public static CustomEncoder rightEncoder;
		public static Motor turrentMotor;
		public static SolenoidShifters shifter;
		public static TankDriveShifting chassis;
		public static PDP pdp;
		public static NavX navx;
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
		Component.pdp = new PDP();
		Component.shifter = new SolenoidShifters(Port.Pneumatics.solenoidUp, Port.Pneumatics.solenoidDown);
		Component.leftWheel = new Motor("LeftWheel", false, new AccelerationCap(Component.pdp), new CANTalon(Port.Motors.CAN.leftDriveA), new CANTalon(Port.Motors.CAN.leftDriveB));
		Component.rightWheel = new Motor("RightWheel", false, new AccelerationCap(Component.pdp), new CANTalon(Port.Motors.CAN.rightDriveA), new CANTalon(Port.Motors.CAN.rightDriveB));
		Component.leftEncoder = new CANEncoder(Port.CAN.leftEncoder);
		Component.rightEncoder = new CANEncoder(Port.CAN.rightEncoder);
		double inchesPerPulse = 2 * Math.PI * Constant.Chassis.WHEEL_RADIUS_INCHES / Constant.Chassis.WHEEL_PULSES_PER_REVOLUTION;
		Component.leftEncoder.setDistancePerPulse(inchesPerPulse);
		Component.rightEncoder.setDistancePerPulse(inchesPerPulse);
		Component.chassis = new TankDriveShifting("OffseasonChassis", Component.leftWheel, Component.rightWheel, Component.shifter);
		Component.turrentMotor = new Motor("Turrent", false, new VictorSP(Port.Motors.PWM.turrent));
		HumanInput.Driver.xbox = new CustomXbox(Port.HumanInput.xboxController);
		HumanInput.Operator.stick = new CustomJoystick(Port.HumanInput.joystick);
		Component.navx = new NavX(SerialPort.Port.kMXP);
		HumanInput.Driver.xbox.setDeadZone(Constant.HumanInput.XBOX_MINIMUM_THRESHOLD);
		Component.backLeds = new OffseasonLEDs(Port.CAN.backLeds);
		Component.frontLeds = new OffseasonLEDs(Port.CAN.frontLeds);
		Component.leftEncoder = new CANEncoder("LeftEncoder", Port.CAN.leftEncoder, false);
		Component.rightEncoder = new CANEncoder("RightEncoder", Port.CAN.rightEncoder, false);
	}
}
