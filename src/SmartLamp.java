/**
 * The SmartLamp class represents a smart lamp that extends from the SmartDevice class.
 * It has the functionality to control the lamp's on/off state, kelvin value, and brightness level.
 */
class SmartLamp extends SmartDevice {
    /**
     * isOn represents the current state of the lamp.
     * kelvin represents the kelvin value of the lamp.
     * brightness represents the brightness level of the lamp.
     */
    private boolean isOn;
    private int kelvin;
    private int brightness;

    /**
     * Constructs a new SmartLamp object with the specified name.
     * The default values for isOn, kelvin and brightness are false, 4000, and 100, respectively.
     * @param name the name of the smart lamp
     */
    public SmartLamp(String name) {
        super(name);
        this.isOn = false; // default value
        this.kelvin = 4000; // default value
        this.brightness = 100; // default value
    }

    /**
     * Constructs a new SmartLamp object with the specified name and isOn state.
     * The default values for kelvin and brightness are 4000 and 100, respectively.
     * @param name the name of the smart lamp
     * @param isOn the initial state of the smart lamp
     */
    public SmartLamp(String name, boolean isOn) {
        super(name);
        this.isOn = isOn;
        this.kelvin = 4000;
        this.brightness = 100;
    }

    /**
     * Constructs a new SmartLamp object with the specified name, isOn state, kelvin value, and brightness level.
     * @param name the name of the smart lamp
     * @param isOn the initial state of the smart lamp
     * @param kelvin the initial kelvin value of the smart lamp
     * @param brightness the initial brightness level of the smart lamp
     */
    public SmartLamp(String name, boolean isOn, int kelvin, int brightness) {
        this(name, isOn);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }

    /**
     * Returns the name of the smart lamp.
     * @return the name of the smart lamp
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Sets the name of the smart lamp.
     * @param name the new name of the smart lamp
     * @return the new name of the smart lamp
     */
    public String setName(String name) {
        super.setName(name);
        return name;
    }

    /**
     * Returns the kelvin value of the smart lamp.
      @return the kelvin value of the smart lamp
     */
    public int getKelvin() {
        return kelvin;
    }

    /**
     * Sets the kelvin value of the smart lamp.
     * @param kelvin the new kelvin value of the smart lamp
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }
    /**
     Returns the brightness level of the smart lamp.
     @return the brightness level of the smart lamp
     */
    public int getBrightness() {
        return brightness;
    }
    /**
     Sets the brightness level of the smart lamp.
     @param brightness the new brightness level of the smart lamp
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    /**
     Returns the current state of the smart lamp.
     @return true if the smart lamp is on, false otherwise
     */
    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     Sets the state of the smart lamp.
     @param isOn the new state of the smart lamp
     @return the new state of the smart lamp
     */
    @Override
    public boolean setOn(boolean isOn) {
        this.isOn = isOn;
        return isOn;
    }


}
