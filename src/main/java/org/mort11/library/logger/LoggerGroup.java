package org.mort11.library.logger;

import static org.mort11.library.logger.LoggerTypeEnum.*;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class LoggerGroup implements LoggerIntf {
    public LoggerTypeEnum[] loggerTypes;
    public String subsystemName;
    public double loggerCount;

    public LoggerIntf[] loggers;

    public LoggerGroup(LoggerIntf... loggers) {
        this.loggers = loggers;
        loggerCount = loggers.length;
    }

    public LoggerGroup(LoggerTypeEnum... loggerTypes) {
        this(null, loggerTypes);
    }
    
    public LoggerGroup(String subsystemName, LoggerTypeEnum... loggerTypes) {
        this.loggerTypes = loggerTypes;
        this.subsystemName = subsystemName;
        loggerCount = loggerTypes.length;

        for(int i = 0; i < loggerCount; i++) {
            switch(loggerTypes[i]) {
                case ADVANTAGEKIT:
                    loggers[i] = new Logger(subsystemName, ADVANTAGEKIT);
                    break;

                case ADVANTAGESCOPE:
                    loggers[i] = new Logger(subsystemName, ADVANTAGESCOPE);
                    break;
                case SMARTDASHBOARD:
                    loggers[i] = new Logger(subsystemName, SMARTDASHBOARD);
                    break;
                case SHUFFLEBOARD:
                    loggers[i] = new Logger(subsystemName, SHUFFLEBOARD);
                    break;
            }
        }
    }

    public int getLoggerPosition(LoggerTypeEnum loggerType) {
        for(int i = 0; i < loggerCount; i++) {
            if(loggerType == loggerTypes[i]) {return i;}
        }
        return 0;
    }

    public boolean containsLogger(LoggerTypeEnum loggerType) {
        for(int i = 0; i < loggerCount; i++) {
            if(loggerType == loggerTypes[i]) {return true;}
        }
        return false;
    }

    public void putDouble(String key, DoubleSupplier data) {
        for(int i = 0; i < loggerCount; i++) {
            loggers[i].putDouble(key, data);
        }
    }

    public void putDouble(LoggerTypeEnum loggerType, String key, DoubleSupplier data) {
        loggers[getLoggerPosition(loggerType)].putDouble(key, data);
    }

    public void putBoolean(String key, BooleanSupplier data) {
        for(int i = 0; i < loggerCount; i++) {
            loggers[i].putBoolean(key, data);
        }
    }

    public void putBoolean(LoggerTypeEnum loggerType, String key, BooleanSupplier data) {
        loggers[getLoggerPosition(loggerType)].putBoolean(key, data);
    }



    public double getDouble(String key, DoubleSupplier defaultValue) {
        if(containsLogger(SMARTDASHBOARD)) {
            return loggers[getLoggerPosition(SMARTDASHBOARD)].getDouble(key, defaultValue);
        }
        return loggers[0].getDouble(key, defaultValue);
    }

    public double getDouble(LoggerTypeEnum loggerType, String key, DoubleSupplier defaultValue) {
        return loggers[getLoggerPosition(loggerType)].getDouble(key, defaultValue);
    }

    public boolean getBoolean(String key, BooleanSupplier defaultValue) {
        if(containsLogger(SMARTDASHBOARD)) {
            return loggers[getLoggerPosition(SMARTDASHBOARD)].getBoolean(key, defaultValue);
        }
        return loggers[0].getBoolean(key, defaultValue);
    }

    public boolean getBoolean(LoggerTypeEnum loggerType, String key, BooleanSupplier defaultValue) {
        return loggers[getLoggerPosition(loggerType)].getBoolean(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        if(containsLogger(SMARTDASHBOARD)) {
            return loggers[getLoggerPosition(SMARTDASHBOARD)].getDouble(key, defaultValue);
        }
        return loggers[0].getDouble(key, defaultValue);
    }

    public double getDouble(LoggerTypeEnum loggerType, String key, double defaultValue) {
        return loggers[getLoggerPosition(loggerType)].getDouble(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if(containsLogger(SMARTDASHBOARD)) {
            return loggers[getLoggerPosition(SMARTDASHBOARD)].getBoolean(key, defaultValue);
        }
        return loggers[0].getBoolean(key, defaultValue);
    }

    public boolean getBoolean(LoggerTypeEnum loggerType, String key, boolean defaultValue) {
        return loggers[getLoggerPosition(loggerType)].getBoolean(key, defaultValue);
    }

    public LoggerIntf getLogger(LoggerTypeEnum loggerType) {
        return loggers[getLoggerPosition(loggerType)];
    }

    public LoggerIntf getLogger(int loggerNumber) {
        return loggers[loggerNumber];
    }
}
