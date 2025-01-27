package org.mort11.library.hardware.brands.rev;

import org.mort11.library.hardware.encoder.EncoderIntf;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class ThroughBoreEncoder implements EncoderIntf{
    public int ID;

    public DutyCycleEncoder encoder;

    public ThroughBoreEncoder(int ID) {
        this.ID = ID;

        encoder = new DutyCycleEncoder(ID);
    }

    public void setCanivore(String canivore) {
        System.out.println("wow");
    }

    public Rotation2d getPosition() {
        return Rotation2d.fromRotations(encoder.get());
    }

    public double getVelocityRotations() {
        return 0;
    }

    public DutyCycleEncoder getEncoder() {
        return encoder;
    }
}
