package org.mort11.subsystems;

import static org.mort11.config.constants.EvanEffectorConstants.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Pretty much carbon copy of modern mortasaurus

public class Pneumatic extends SubsystemBase{

    private Compressor compressor;

    private static Pneumatic pneumatic;


    public Pneumatic() {
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
    

