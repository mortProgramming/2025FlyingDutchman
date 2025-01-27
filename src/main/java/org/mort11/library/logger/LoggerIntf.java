package org.mort11.library.logger;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public interface LoggerIntf {
    public void putDouble(String key, DoubleSupplier data);

    public void putBoolean(String key, BooleanSupplier data);



    public double getDouble(String key, DoubleSupplier defaultValue);

    public boolean getBoolean(String key, BooleanSupplier defaultValue);

    public double getDouble(String key, double defaultValue);

    public boolean getBoolean(String key, boolean defaultValue);
}
