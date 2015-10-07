package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import org.usfirst.frc4904.standard.subsystems.chassis.TankDriveShifting;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
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
	public static TankDriveShifting chassis;
	
	public RobotMap() {
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
		chassis = new TankDriveShifting("OffseasonChassis", leftWheelA, leftWheelB, rightWheelA, rightWheelB, shifter);
	}
}
