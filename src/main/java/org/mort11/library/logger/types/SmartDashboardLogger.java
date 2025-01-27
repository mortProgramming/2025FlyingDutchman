package org.mort11.library.logger.types;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.mort11.library.logger.LoggerIntf;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardLogger implements LoggerIntf {
    
    public SmartDashboardLogger() {}

    public void putDouble(String key, DoubleSupplier data) {
        SmartDashboard.putNumber(key, data.getAsDouble());
    }

    public void putBoolean(String key, BooleanSupplier data) {
        SmartDashboard.putBoolean(key, data.getAsBoolean());
    }



    public double getDouble(String key, DoubleSupplier defaultValue) {
        return SmartDashboard.getNumber(key, defaultValue.getAsDouble());
    }

    public boolean getBoolean(String key, BooleanSupplier defaultValue) {
        return SmartDashboard.getBoolean(key, defaultValue.getAsBoolean());
    }

    public double getDouble(String key, double defaultValue) {
        return SmartDashboard.getNumber(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return SmartDashboard.getBoolean(key, defaultValue);
    }
}
