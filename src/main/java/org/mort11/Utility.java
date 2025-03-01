package org.mort11;

public final class Utility {
    
    public static final double clamp(double value, double max) {
        if(value > max) {
            return max;
        }
        else if(value < -max) {
            return -max;
        }
    
        return value;
    }
}
