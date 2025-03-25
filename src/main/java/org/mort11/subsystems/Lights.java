package org.mort11.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static org.mort11.config.constants.PortConstants.Lights.*;
import static org.mort11.config.constants.PhysicalConstants.Lights.*;

public class Lights extends SubsystemBase{
    private static Lights lights;

    private static Spark leds;

    public Lights() {
        leds = new Spark(LEDS_PORT);
    }

    public void setLightsGreen() {
        leds.set(GREEN);
    }

    public void setLightsRed() {
        leds.set(RED);
    }
 
    public void setLightsBlue() {
        leds.set(BLUE);
    }

    public void setLightsGold() {
        leds.set(GOLD);
    }

    public void setLightsPurple() {
        leds.set(PURPLE);
    }

    public void setLightsDarkBlue() {
        leds.set(DARK_BLUE);
    }

    public void setLightsPink() {
        leds.set(PINK);
    }

    public void setLightsWhite() {   
        leds.set(WHITE);
    }

    public void setLightsBlack() {
        leds.set(BLACK);
    }

    public void setLightsYellow(){
        leds.set(YELLOW);
    }

    public void setLightsOrange(){
        leds.set(ORANGE);
    }

    public void setLights(double color) {
        leds.set(color);
    }

    public static Lights getInstance() {
        if (lights == null) {
            lights = new Lights();
        }
        return lights;
    }
}
