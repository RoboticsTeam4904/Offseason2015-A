package org.usfirst.frc4904.robot;


import org.usfirst.frc4904.robot.custom.PIDOffAngleChassisController;
import org.usfirst.frc4904.robot.humaninput.drivers.HardMode;
import org.usfirst.frc4904.robot.humaninput.drivers.JoystickControl;
import org.usfirst.frc4904.robot.humaninput.drivers.Nathan;
import org.usfirst.frc4904.robot.humaninput.drivers.NathanGain;
import org.usfirst.frc4904.robot.humaninput.drivers.PureStick;
import org.usfirst.frc4904.robot.leds.OffseasonLEDs;
import org.usfirst.frc4904.standard.CommandRobotBase;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.commands.chassis.ChassisMove;
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
	private ChassisMove teleopNormal;
	private ChassisMove teleopAlign;

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
		LogKitten.setPrintMute(true);
		LogKitten.setDefaultLogLevel(LogKitten.LEVEL_DEBUG);
		LogKitten.setDefaultPrintLevel(LogKitten.LEVEL_DEBUG);
		LogKitten.setDefaultDSLevel(LogKitten.LEVEL_DEBUG);
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
		teleopNormal = new ChassisMove(RobotMap.Component.chassis, driverChooser.getSelected(), RobotMap.Constant.HumanInput.X_SPEED_SCALE, RobotMap.Constant.HumanInput.Y_SPEED_SCALE, RobotMap.Constant.HumanInput.TURN_SPEED_SCALE);
		teleopAlign = new ChassisMove(RobotMap.Component.chassis, new PIDOffAngleChassisController(driverChooser.getSelected(), RobotMap.Component.cameraPIDSource, new CustomPIDController(RobotMap.Constant.Component.AlignAngle_P, RobotMap.Constant.Component.AlignAngle_I, RobotMap.Constant.Component.AlignAngle_D, RobotMap.Component.cameraPIDSource), RobotMap.Constant.Component.AlignAngleTolerance), RobotMap.Constant.HumanInput.X_SPEED_SCALE, RobotMap.Constant.HumanInput.Y_SPEED_SCALE, RobotMap.Constant.HumanInput.TURN_SPEED_SCALE);
		teleopCommand = teleopNormal;
		teleopCommand.start();
		leds.setColor(128, 0, 0);
		LogKitten.d("Teleop Initialize");
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
		if (RobotMap.HumanInput.Driver.xbox.y.get() && (teleopCommand != teleopAlign)) {
			teleopCommand.cancel();
			teleopAlign = new ChassisMove(RobotMap.Component.chassis, new PIDOffAngleChassisController(driverChooser.getSelected(), RobotMap.Component.cameraPIDSource, new CustomPIDController(RobotMap.Constant.Component.AlignAngle_P, RobotMap.Constant.Component.AlignAngle_I, RobotMap.Constant.Component.AlignAngle_D, RobotMap.Component.cameraPIDSource), RobotMap.Constant.Component.AlignAngleTolerance), RobotMap.Constant.HumanInput.X_SPEED_SCALE, RobotMap.Constant.HumanInput.Y_SPEED_SCALE, RobotMap.Constant.HumanInput.TURN_SPEED_SCALE);
			teleopCommand = teleopAlign;
			teleopCommand.start();
			LogKitten.d("Auto Align Activated", true);
		}
		LogKitten.d("Auto Align Status: " + teleopAlign.getController().finished() + " Off Angle: " + RobotMap.Component.cameraIR.getGoalOffAngle(false), true);
		if (((!RobotMap.HumanInput.Driver.xbox.y.get()) || teleopAlign.getController().finished()) && (teleopCommand != teleopNormal)) {
			teleopCommand.cancel();
			teleopNormal = new ChassisMove(RobotMap.Component.chassis, driverChooser.getSelected(), RobotMap.Constant.HumanInput.X_SPEED_SCALE, RobotMap.Constant.HumanInput.Y_SPEED_SCALE, RobotMap.Constant.HumanInput.TURN_SPEED_SCALE);
			teleopCommand = teleopNormal;
			teleopCommand.start();
			LogKitten.d("Auto Align Deactivated", true);
		}
		leds.setColor(0, (int) (Math.abs(driverChooser.getSelected().getY()) * 128), (int) (128 - Math.abs(driverChooser.getSelected().getY() * 128)));
		leds.update();
	}
	
	@Override
	public void testInitialize() {}
	
	@Override
	public void testExecute() {}
}
