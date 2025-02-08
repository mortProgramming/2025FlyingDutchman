package org.mort11.subsystems;

import static org.mort11.config.constants.PortConstants.AlgaeRoller.*;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeRoller extends SubsystemBase {
    public static AlgaeRoller algaeRoller;

    public Motor algaeRollerMotor;

    private double rollerSpeed;
    
    public AlgaeRoller(){
        algaeRollerMotor = new Motor(NEO550, ROLLER_MOTOR);

        rollerSpeed = 0;
    }

    @Override
    public void periodic(){
        algaeRollerMotor.setVoltage(rollerSpeed * ROBOT_VOLTAGE);
    }
    
        public void setRollerSpeed(double rollerSpeed){
            this.rollerSpeed = rollerSpeed;
        }

    public Motor getRollerMotor(){
        return algaeRollerMotor;
    }

    public static AlgaeRoller getInstance() {
		if (algaeRoller == null) {
			algaeRoller = new AlgaeRoller();
		}
		return algaeRoller;
	}
}
