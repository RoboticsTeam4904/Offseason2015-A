package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.subsystems.chassis.TankDriveShiftingPID;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.MotorFactory;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class RobotMap {
	// *** PHYSICAL COMPONENTS *** //
	// VictorSP-controlled motors
	// Solenoids
	public static Solenoid SOLENOID_UP;
	public static Solenoid SOLENOID_DOWN;
	public static SolenoidShifters shifter;
	// Motors
	public static Motor leftWheel;
	public static Motor rightWheel;
	public static TankDriveShifting chassis;
	public static PDP pdp;
	public static final double maxDegreesPerSecond = 250;
	
	public RobotMap() {
		pdp = new PDP();
		SOLENOID_UP = new Solenoid(7);
		SOLENOID_DOWN = new Solenoid(6);
		shifter = new SolenoidShifters(SOLENOID_UP, SOLENOID_DOWN, SOLENOID_UP, SOLENOID_DOWN);
		leftWheel = MotorFactory.getAccelMotorGroup("LeftWheel", false, pdp, new CANTalon(1), new CANTalon(2));
		rightWheel = MotorFactory.getAccelMotorGroup("RightWheel", true, pdp, new CANTalon(3), new CANTalon(4));
		chassis = new TankDriveShiftingPID("OffseasonChassis", leftWheel, rightWheel, shifter, DriverStationMap.CHASSIS_P, DriverStationMap.CHASSIS_I, DriverStationMap.CHASSIS_D, maxDegreesPerSecond);
	}
}
