package org.usfirst.frc4904.sovereignty;


import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AligningCamera implements PIDSource {
	
	public static final String TABLE_NAME = "GRIP/myCountoursReport";
	public static final String FIELD_DEGREES = "centerX";
	public static final String FIELD_DISTANCE = "centerY";
	public static final String FIELD_VISIBLE = "visible";
	protected NetworkTable cameraTable;
	private PIDSourceType sourceType;
	
	public AligningCamera(PIDSourceType sourceType, String cameraTableName) {
		this.sourceType = sourceType;
		cameraTable = NetworkTable.getTable(cameraTableName);
	}
	
	public AligningCamera(PIDSourceType sourceType) {
		this(sourceType, AligningCamera.TABLE_NAME);
	}
	
	public float getDegrees() {
		double[] data = cameraTable.getNumberArray(AligningCamera.FIELD_DEGREES, new double[] {});
		if (data.length == 2) {
			return (float) (data[0] + data[1]) / 2;
		} else if (data.length == 1) {
			return (float) data[0];
		} else {
			return 0.0f;
		}
	}
	
	public float getDistance() {
		double[] data = cameraTable.getNumberArray(AligningCamera.FIELD_DISTANCE, new double[] {});
		if (data.length == 2) {
			return (float) (data[0] + data[1]) / 2;
		} else if (data.length == 1) {
			return (float) data[0];
		} else {
			return 0.0f;
		}
	}
	
	public boolean isVisible() {
		return cameraTable.getBoolean(AligningCamera.FIELD_VISIBLE, getDegrees() == Float.NaN || getDistance() == Float.NaN);
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return sourceType;
	}
	
	@Override
	public double pidGet() {
		return getDegrees();
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType sourceType) {
		this.sourceType = sourceType;
	}
}
