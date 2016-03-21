package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.humaninput.drivers.HardMode;
import org.usfirst.frc4904.robot.humaninput.drivers.JoystickControl;
import org.usfirst.frc4904.robot.humaninput.drivers.Nathan;
import org.usfirst.frc4904.robot.humaninput.drivers.NathanGain;
import org.usfirst.frc4904.robot.humaninput.drivers.PureStick;
import org.usfirst.frc4904.robot.leds.OffseasonLEDs;
import org.usfirst.frc4904.standard.CommandRobotBase;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMove;
import org.usfirst.frc4904.standard.custom.PIDChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.CustomPIDController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends CommandRobotBase {
	RobotMap map = new RobotMap();
	OffseasonLEDs leds = new OffseasonLEDs(0x600);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void initialize() {
		// Configure autonomous command chooser
		autoChooser.addDefault(new ChassisIdle(RobotMap.Component.chassis));
		// Configure driver command chooser
		driverChooser.addDefault(new NathanGain());
		driverChooser.addObject(new Nathan());
		driverChooser.addObject(new JoystickControl());
		driverChooser.addObject(new PureStick());
		driverChooser.addObject(new HardMode());
	}

	@Override
	public void disabledExecute() {}

	@Override
	public void autonomousInitialize() {}
	
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousExecute() {}

	@Override
	public void teleopInitialize() {
		teleopCommand = new ChassisMove(RobotMap.Component.chassis, new PIDChassisController(driverChooser.getSelected(), RobotMap.Component.navx, new CustomPIDController(RobotMap.Constant.Chassis.TURN_P, RobotMap.Constant.Chassis.TURN_I, RobotMap.Constant.Chassis.TURN_D, RobotMap.Component.navx), RobotMap.Constant.Chassis.MAX_DEGREES_PER_SECOND));
		teleopCommand.start();
		leds.setColor(128, 0, 0);
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInitialize() {
		leds.setColor(128, 0, 0);
		for (int i = 0; i < 10; i++) {
			leds.update();
		}
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopExecute() {
		leds.setColor(0, (int) (Math.abs(driverChooser.getSelected().getY()) * 128), (int) (128 - Math.abs(driverChooser.getSelected().getY() * 128)));
		leds.update();
	}
	
	@Override
	public void testInitialize() {}
	
	@Override
	public void testExecute() {}
}
