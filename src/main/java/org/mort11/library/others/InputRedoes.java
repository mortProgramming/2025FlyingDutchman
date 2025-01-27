package org.mort11.library.others;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class InputRedoes {

    public String key;

    public double currentNumber, lastNumber;
    
    public InputRedoes(String key, double startValue) {
        SmartDashboard.putNumber(key, startValue);

        currentNumber = startValue;
        lastNumber = startValue;
    }

    public Boolean getReset() {
        currentNumber = SmartDashboard.getNumber(key, 0);

        if (currentNumber != lastNumber) {
            lastNumber = currentNumber;
            return true;
        }

        return false;
    }

    public double getNumber () {
        return SmartDashboard.getNumber(key, currentNumber);
    }
}
