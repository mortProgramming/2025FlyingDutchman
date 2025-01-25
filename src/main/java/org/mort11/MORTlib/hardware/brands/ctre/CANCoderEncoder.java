package org.mort11.MORTlib.hardware.brands.ctre;

import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.math.geometry.Rotation2d;
import org.mort11.MORTlib.hardware.encoder.EncoderIntf;

public class CANCoderEncoder implements EncoderIntf {

    public int ID;

    public CANcoder encoder;

    public CANCoderEncoder(int ID) {
        this.ID = ID;

        encoder = new CANcoder(ID);
    }

    public void setCanivore(String canivore) {
        encoder = new CANcoder(ID, canivore);
    }

    public Rotation2d getPosition() {
        return Rotation2d.fromRotations(encoder.getPosition().getValueAsDouble());
    }

    public double getVelocityRotations() {
        return encoder.getVelocity().getValueAsDouble();
    }

    public CANcoder getEncoder() {
        return encoder;
    }
}
