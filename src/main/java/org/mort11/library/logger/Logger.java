package org.mort11.library.logger;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.mort11.library.logger.types.ShuffleboardLogger;
import org.mort11.library.logger.types.SmartDashboardLogger;

public class Logger implements LoggerIntf {
    public LoggerTypeEnum loggerType;
    public String subsystemName;

    public LoggerIntf logger;

    public Logger(LoggerIntf logger) {
        this.logger = logger;
    }

    public Logger(LoggerTypeEnum loggerType) {
        this(null, loggerType);
    }
    
    public Logger(String subsystemName, LoggerTypeEnum loggerType) {
        this.loggerType = loggerType;
        this.subsystemName = subsystemName;

        //TODO advantagekit
        switch(loggerType) {
            // case ADVANTAGEKIT:
            //     logger = new AdvantageKitLogger();
            //     break;
            // case ADVANTAGESCOPE:
            //     logger = new AdvantageKitScope();
            //     break;
            case SMARTDASHBOARD:
                logger = new SmartDashboardLogger();
                break;
            case SHUFFLEBOARD:
                logger = new ShuffleboardLogger(subsystemName);
                break;
        }
    }

    public void putDouble(String key, DoubleSupplier data) {
        logger.putDouble(key, data);
    }

    public void putBoolean(String key, BooleanSupplier data) {
        logger.putBoolean(key, data);
    }



    public double getDouble(String key, DoubleSupplier defaultValue) {
        return logger.getDouble(key, defaultValue);
    }

    public boolean getBoolean(String key, BooleanSupplier defaultValue) {
        return logger.getBoolean(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        return logger.getDouble(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return logger.getBoolean(key, defaultValue);
    }
}
