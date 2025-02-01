package org.mort11.subsystems;

import static org.mort11.config.constants.PortConstants.Climber.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Climber {

    private Climber masterClimber;

    private Climber followClimber;

    private Compressor compressor;

    private static Pneumatic pneumatic;

    private static DoubleSolenoid masterPiston;

    private static DoubleSolenoid followPiston;

    private Climber(){
        // Module type might be wrong? CTREPCM
        masterPiston = new DoubleSolenoid(PNEUMATIC_PORT, PneumaticsModuleType.CTREPCM, CLIMBER_DOWN_POSITION,CLIMBER_UP_POSITION);
        followClimber = new DoubleSolenoid()

        compressor = new Compressor(PNEUMATIC_CONTROL_MODULE,PneumaticsModuleType.CTREPCM);
        compressor.enableDigital();
    }

    public void setCompressorAnalog(){
        compressor.enableAnalog(COMPRESSER_MIN_PRESSURE, COMPRESSER_MAX_PRESSURE);
    }

    public double getPressure() {
		return compressor.getPressure();
	}

	@Override
	public void periodic() {
	}

    public static Pneumatic getInstance() {
		if (pneumatic == null) {
			pneumatic = new Pneumatic();
		}
		return pneumatic;
	}
    
}
