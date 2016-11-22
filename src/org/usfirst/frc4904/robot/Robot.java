package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.humaninput.drivers.JoystickControl;
import org.usfirst.frc4904.robot.humaninput.drivers.Nathan;
import org.usfirst.frc4904.robot.humaninput.drivers.NathanGain;
import org.usfirst.frc4904.robot.humaninput.drivers.PureStick;
import org.usfirst.frc4904.standard.CommandRobotBase;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMove;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends CommandRobotBase {
	RobotMap map = new RobotMap();
	
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
		RobotMap.Component.backLeds.disable();
		RobotMap.Component.frontLeds.disable();
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
		teleopCommand = new ChassisMove(RobotMap.Component.chassis, driverChooser.getSelected());
		teleopCommand.start();
		RobotMap.Component.backLeds.enable();
		RobotMap.Component.frontLeds.enable();
	}
	
	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInitialize() {
		RobotMap.Component.backLeds.disable();
		RobotMap.Component.frontLeds.disable();
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopExecute() {
		RobotMap.Component.backLeds.setValue((int) (Math.abs(driverChooser.getSelected().getY()) * 96));
		RobotMap.Component.frontLeds.setValue((int) (Math.abs(driverChooser.getSelected().getY()) * 96));
		LogKitten.e("" + RobotMap.Component.leftEncoder.get() + " " + RobotMap.Component.rightEncoder.get());
	}
	
	@Override
	public void testInitialize() {}
	
	@Override
	public void testExecute() {}
	
	@Override
	public void alwaysExecute() {
		RobotMap.Component.backLeds.update();
		RobotMap.Component.frontLeds.update();
	}
}
