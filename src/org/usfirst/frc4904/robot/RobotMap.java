package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.subsystems.chassis.TankDriveShiftingPID;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.AccelMotor;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.MotorGroup;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class RobotMap {
	// *** PHYSICAL COMPONENTS *** //
	// VictorSP-controlled motors
	public static CANTalon LEFT_WHEEL_MOTOR_B;
	public static CANTalon RIGHT_WHEEL_MOTOR_A;
	public static CANTalon RIGHT_WHEEL_MOTOR_B;
	public static CANTalon LEFT_WHEEL_MOTOR_A;
	// Solenoids
	public static Solenoid LEFT_SOLENOID_UP;
	public static Solenoid LEFT_SOLENOID_DOWN;
	public static Solenoid RIGHT_SOLENOID_UP;
	public static Solenoid RIGHT_SOLENOID_DOWN;
	public static SolenoidShifters shifter;
	// Motors
	public static Motor leftWheelA;
	public static Motor leftWheelB;
	public static Motor rightWheelA;
	public static Motor rightWheelB;
	public static Motor leftWheel;
	public static Motor rightWheel;
	public static TankDriveShifting chassis;
	public static PDP pdp;
	
	public RobotMap() {
		pdp = new PDP();
		LEFT_WHEEL_MOTOR_A = new CANTalon(1);
		LEFT_WHEEL_MOTOR_B = new CANTalon(2);
		RIGHT_WHEEL_MOTOR_A = new CANTalon(3);
		RIGHT_WHEEL_MOTOR_B = new CANTalon(4);
		LEFT_SOLENOID_UP = new Solenoid(7);
		LEFT_SOLENOID_DOWN = new Solenoid(6);
		RIGHT_SOLENOID_UP = new Solenoid(7);
		RIGHT_SOLENOID_DOWN = new Solenoid(6);
		shifter = new SolenoidShifters(LEFT_SOLENOID_UP, LEFT_SOLENOID_DOWN, RIGHT_SOLENOID_UP, RIGHT_SOLENOID_DOWN);
		leftWheelA = new Motor("First left wheel", LEFT_WHEEL_MOTOR_A);
		leftWheelB = new Motor("Second left wheel", LEFT_WHEEL_MOTOR_B);
		rightWheelA = new Motor("First right wheel", RIGHT_WHEEL_MOTOR_A, true);
		rightWheelB = new Motor("Second right wheel", RIGHT_WHEEL_MOTOR_B, true);
		leftWheel = new AccelMotor("Left wheel accel", new MotorGroup("Left wheel", leftWheelA, leftWheelB), pdp);
		rightWheel = new AccelMotor("Right wheel accel", new MotorGroup("Right wheel", rightWheelA, rightWheelB), pdp);
		chassis = new TankDriveShiftingPID("OffseasonChassis", leftWheelA, leftWheelB, rightWheelA, rightWheelB, shifter, DriverStationMap.CHASSIS_P, DriverStationMap.CHASSIS_I, DriverStationMap.CHASSIS_D);
	}
}
