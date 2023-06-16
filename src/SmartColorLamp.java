/**

 A subclass of SmartLamp that adds support for color changing capabilities.
 */
class SmartColorLamp extends SmartLamp {
    private boolean colorMode;
    private int colorCode;
    private int kelvin = 4000;
    private int brightness = 100;
    /**
     Constructor for a SmartColorLamp object with the specified name.
     The lamp will be turned off by default, and set to white color with 4000 kelvin temperature.
     @param name the name of the lamp
     */
    public SmartColorLamp(String name) {
        super(name);
        this.colorCode = 0xFFFFFF;
        colorMode = false;
    }
    /**
     Constructor for a SmartColorLamp object with the specified name and power state.
     The lamp will be set to white color with 4000 kelvin temperature by default.
     @param name the name of the lamp
     @param isOn the initial power state of the lamp
     */
    public SmartColorLamp(String name, boolean isOn) {
        super(name, isOn);
        this.kelvin = 4000;
        // default color code is white

    }
    /**
     Constructor for a SmartColorLamp object with the specified name, power state, kelvin temperature, brightness, and color mode.
     @param name the name of the lamp
     @param isOn the initial power state of the lamp
     @param kelvin the initial kelvin temperature of the lamp
     @param brightness the initial brightness level of the lamp
     @param colorMode the initial color mode of the lamp
     */

    public SmartColorLamp(String name, boolean isOn, int kelvin, int brightness, boolean colorMode) {
        super(name, isOn);
        this.kelvin = kelvin;
        this.brightness = brightness;
        this.colorMode = colorMode;
    }

    /**
     Constructor for a SmartColorLamp object with the specified name, power state, color code, and brightness.
     The lamp will be set to color mode with the specified color code.
     @param name the name of the lamp
     @param isOn the initial power state of the lamp
     @param colorCode the initial color code of the lamp
     @param brightness the initial brightness level of the lamp
     */
    public SmartColorLamp(String name, boolean isOn, int colorCode, int brightness) {
        super(name, isOn);
        this.colorCode = colorCode;
        this.colorMode = true;
        this.brightness = brightness;

        if (colorMode) {
            kelvin = 0; // set kelvin to 0 when color mode is activated
        }
    }
    /**
     Returns whether the lamp is currently in color mode.
     @return true if the lamp is in color mode, false otherwise
     */
    public boolean isColorMode() {
        return colorMode;
    }
    /**
     Sets the color mode of the lamp.
     @param colorMode the new color mode of the lamp
     */
    public void setColorMode(boolean colorMode) {
        this.colorMode = colorMode;
        /*if (colorMode) {
            this.kelvin = 0; // set kelvin to 0 when color mode is activated
        }*/
    }
    /**
     Returns the color code of the lamp in hexadecimal format.
     @return the color code of the lamp in hexadecimal format
     */
    public String getColorCode() {
        String colorHex = String.format("%06X", colorCode);
        String hexValue = "0x" + colorHex.toUpperCase();
        return hexValue;
    }
    /**

     Sets the color code of the SmartColorLamp to the specified value.
     @param colorCode the color code value to set
     */
    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    /**
     Sets the kelvin value of the SmartColorLamp to the specified value.
     @param kelvin the kelvin value to set
     */
    @Override
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }

    /**
     Returns the current kelvin value of the SmartColorLamp.
     @return the kelvin value of the SmartColorLamp
     */
    @Override
    public int getKelvin() {
        return kelvin;
    }

    /**
     Returns the current brightness value of the SmartColorLamp.
     @return the brightness value of the SmartColorLamp
     */
    @Override
    public int getBrightness() {
        return brightness;
    }

    /**
     Sets the brightness value of the SmartColorLamp to the specified value.
     @param brightness the brightness value to set
     */
    @Override
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
