package org.mort11.subsystems;

import static org.mort11.config.constants.PortConstants.Climber.*;
import static org.mort11.config.constants.PhysicalConstants.Climber.*;
import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase{
    private static Climber climber;

    private Compressor compressor;

    //climber up is true climber down is false
    private boolean climberUp;


    private static DoubleSolenoid rightPiston, leftPiston;


    

    private Climber(){
        // Module type might be wrong? CTREPCM
        rightPiston = new DoubleSolenoid(RIGHT_PNEUMATIC_PORT, PneumaticsModuleType.CTREPCM, RIGHT_CLIMBER_PORT_UP,RIGHT_CLIMBER_PORT_DOWN);

        leftPiston = new DoubleSolenoid(LEFT_PNEUMATIC_PORT, PneumaticsModuleType.CTREPCM,LEFT_CLIMBER_PORT_UP,LEFT_CLIMBER_PORT_DOWN);
        // Compressor first parameter might be wrong 
        compressor = new Compressor(RIGHT_PNEUMATIC_PORT,PneumaticsModuleType.CTREPCM);
        compressor.enableDigital();
        compressor.enableAnalog(COMPRESSER_MIN_PRESSURE, COMPRESSER_MAX_PRESSURE);    
    }

	@Override
	public void periodic() {
        if(climberUp==true){
            rightPiston.set(DoubleSolenoid.Value.kForward);
            leftPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            rightPiston.set(DoubleSolenoid.Value.kReverse);
            leftPiston.set(DoubleSolenoid.Value.kReverse);
        }
	}

    public void setClimberPosition(boolean climberUp){
        this.climberUp = climberUp;
    }


    public double getPressure() {
		return compressor.getPressure();
	}

    public boolean getPosition(){
        return climberUp;
    }


    public static Climber getInstance() {
		if (climber == null) {
			climber = new Climber();
		}
		return climber;
	}
    
}
