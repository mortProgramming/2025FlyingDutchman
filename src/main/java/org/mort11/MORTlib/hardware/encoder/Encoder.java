package org.mort11.MORTlib.hardware.encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import org.mort11.MORTlib.hardware.brands.ctre.CANCoderEncoder;
import org.mort11.MORTlib.hardware.brands.rev.ThroughBoreEncoder;

public class Encoder implements EncoderIntf {

    public EncoderTypeEnum encoderType;
    public int ID;

    public EncoderIntf encoder;
    
    public Encoder(EncoderTypeEnum encoderType, int ID) {
        this.encoderType = encoderType;
        this.ID = ID;

        switch (encoderType) {
            case CANCODER:
                encoder = new CANCoderEncoder(ID);
                break;
            case THROUGHBORE:
                encoder = new ThroughBoreEncoder(ID);
                break;
        }
    }

    public void setCanivore(String canivore) {
        encoder.setCanivore(canivore);
    }

    public Rotation2d getPosition() {
        return encoder.getPosition();
    }

    public double getVelocityRotations() {
        return encoder.getVelocityRotations();
    }

    public EncoderTypeEnum getEncoderType() {
        return encoderType;
    }

}