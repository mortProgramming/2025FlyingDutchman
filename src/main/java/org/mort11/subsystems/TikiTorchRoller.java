package org.mort11.subsystems;

import static org.mort11.config.constants.PortConstants.TikiTorchRoller.*;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TikiTorchRoller extends SubsystemBase {
    public static TikiTorchRoller tikiTorchRoller;

    public Motor tikiTorchRollerMotor;

    private double rollerSpeed;
    
    public TikiTorchRoller(){
        tikiTorchRollerMotor = new Motor(NEO550, ROLLER_MOTOR);

        rollerSpeed = 0;
    }

    @Override
    public void periodic(){
        tikiTorchRollerMotor.setVoltage(rollerSpeed * ROBOT_VOLTAGE);
    }
    
    public void setRollerSpeed(double rollerSpeed){
        this.rollerSpeed = rollerSpeed;
    }

    public Motor getRollerMotor(){
        return tikiTorchRollerMotor;
    }

    public static TikiTorchRoller getInstance() {
		if (tikiTorchRoller == null) {
			tikiTorchRoller = new TikiTorchRoller();
		}
		return tikiTorchRoller;
	}
}
