package org.mort11.library.logger.types;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.mort11.library.logger.LoggerIntf;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ShuffleboardLogger implements LoggerIntf {
    public String subsystemName;
    
    public ShuffleboardLogger(String subsystemName) {
        if(subsystemName == null) {
            this.subsystemName = "";
        }

        else {
            this.subsystemName = subsystemName;
        }
    }

    public void putDouble(String key, DoubleSupplier data) {
        Shuffleboard.getTab(subsystemName).addNumber(key, data);
    }

    public void putBoolean(String key, BooleanSupplier data) {
        Shuffleboard.getTab(subsystemName).addBoolean(key, data);
    }



    public double getDouble(String key, DoubleSupplier defaultValue) {
        return defaultValue.getAsDouble();
    }

    public boolean getBoolean(String key, BooleanSupplier defaultValue) {
        return defaultValue.getAsBoolean();
    }

    public double getDouble(String key, double defaultValue) {
        return defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return defaultValue;
    }
}
