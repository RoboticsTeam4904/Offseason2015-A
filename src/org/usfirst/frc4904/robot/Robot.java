package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.humaninterface.drivers.HardMode;
import org.usfirst.frc4904.robot.humaninterface.drivers.JoystickControl;
import org.usfirst.frc4904.robot.humaninterface.drivers.Nathan;
import org.usfirst.frc4904.robot.humaninterface.drivers.NathanGain;
import org.usfirst.frc4904.robot.humaninterface.drivers.PureStick;
import org.usfirst.frc4904.robot.leds.OffseasonLEDs;
import org.usfirst.frc4904.robot.subsystems.chassis.TankDriveShiftingPID;
import org.usfirst.frc4904.standard.CommandRobotBase;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMove;
import org.usfirst.frc4904.standard.commands.healthchecks.PressureValveClosedTest;
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
	double Constant_P;
	double Constant_I;
	double Constant_D;
	// Even static objects need initializers
	RobotMap map = new RobotMap();
	DriverStationMap dsMap = new DriverStationMap();
	OffseasonLEDs leds = new OffseasonLEDs(0x600);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		super.robotInit(new PressureValveClosedTest(new Compressor(0), 2.0, 2.0));
		System.out.println("CommandRobotBase init complete");
		// Configure autonomous command chooser
		autoChooser.addDefault(new ChassisIdle(RobotMap.chassis));
		// Configure driver command chooser
		driverChooser.addObject(new NathanGain());
		driverChooser.addDefault(new Nathan());
		driverChooser.addObject(new JoystickControl());
		driverChooser.addObject(new PureStick());
		driverChooser.addObject(new HardMode());
		// Display choosers on SmartDashboard
		displayChoosers();
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putNumber("P", 0.0);
		SmartDashboard.putNumber("I", 0.0);
		SmartDashboard.putNumber("D", 0.0);
		LogKitten.setDefaultPrintLevel(LogKitten.LEVEL_DEBUG);
		LogKitten.setDefaultDSLevel(LogKitten.LEVEL_DEBUG);
		LogKitten.setPrintMute(true);
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
		teleopCommand = new ChassisMove(RobotMap.chassis, driverChooser.getSelected(), DriverStationMap.X_SPEED_SCALE, DriverStationMap.Y_SPEED_SCALE, DriverStationMap.TURN_SPEED_SCALE);
		teleopCommand.start();
		LogKitten.setDefaultPrintLevel(LogKitten.LEVEL_WARN);
		Constant_P = SmartDashboard.getNumber("P", 0.0);
		Constant_I = SmartDashboard.getNumber("I", 0.0);
		Constant_D = SmartDashboard.getNumber("D", 0.0);
		TankDriveShiftingPID.pid.setPID(Constant_P, Constant_I, Constant_D);
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
