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
import org.usfirst.frc4904.standard.commands.healthchecks.PressureValveClosedTest;
import org.usfirst.frc4904.standard.custom.PIDChassisController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends CommandRobotBase {
	// Even static objects need initializers
	RobotMap map = new RobotMap();
	OffseasonLEDs leds = new OffseasonLEDs(0x600);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		super.robotInit(new PressureValveClosedTest(new Compressor(0), 2.0, 2.0));
		System.out.println("CommandRobotBase init complete");
		// Configure autonomous command chooser
		autoChooser.addDefault(new ChassisIdle(RobotMap.Component.chassis));
		// Configure driver command chooser
		driverChooser.addDefault(new NathanGain());
		driverChooser.addObject(new Nathan());
		driverChooser.addObject(new JoystickControl());
		driverChooser.addObject(new PureStick());
		driverChooser.addObject(new HardMode());
		// Display choosers on SmartDashboard
		displayChoosers();
		SmartDashboard.putData(Scheduler.getInstance());
	}
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		driverChooser.getSelected().bindCommands();
		teleopCommand = new ChassisMove(RobotMap.Component.chassis, new PIDChassisController(driverChooser.getSelected(), RobotMap.Component.navx, RobotMap.Constant.Chassis.TURN_P, RobotMap.Constant.Chassis.TURN_I, RobotMap.Constant.Chassis.TURN_D, RobotMap.Constant.Chassis.MAX_DEGREES_PER_SECOND), RobotMap.Constant.HumanInput.X_SPEED_SCALE, RobotMap.Constant.HumanInput.Y_SPEED_SCALE, RobotMap.Constant.HumanInput.TURN_SPEED_SCALE);
		teleopCommand.start();
		leds.setColor(128, 0, 0);
	}
	
	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		super.disabledInit();
		if (teleopCommand != null) {
			teleopCommand.cancel();
		}
		leds.setColor(128, 0, 0);
		for (int i = 0; i < 10; i++) {
			leds.update();
		}
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		leds.setColor(0, (int) (Math.abs(driverChooser.getSelected().getY()) * 128), (int) (128 - Math.abs(driverChooser.getSelected().getY() * 128)));
		leds.update();
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
