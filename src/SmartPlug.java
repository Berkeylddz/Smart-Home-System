import java.time.Duration;
import java.time.LocalDateTime;
/**
 * SmartPlug class represents a smart plug device that can be turned on or off and measures the usage of electricity.
 * It extends the abstract SmartDevice class.
 */
class SmartPlug extends SmartDevice{
    private boolean isOn = false;
    private int voltage = 220;
    private double amperage;
    private boolean plugIn = false;
    private LocalDateTime lastSwitchTime;
    private double usage=0.00;

    /**
     * Creates a new SmartPlug instance with the specified name and default values.
     * @param name The name of the smart plug.
     */
    public SmartPlug(String name){
        super(name);
        this.isOn = false;
        this.amperage = 0.00;
    }
    /**
     * Creates a new SmartPlug instance with the specified name and on/off state.
     * @param name The name of the smart plug.
     * @param isOn The on/off state of the smart plug.
     */
    public SmartPlug(String name,boolean isOn){
        super(name);
        this.isOn=isOn;
    }
    /**
     * Creates a new SmartPlug instance with the specified name, on/off state, and amperage.
     * @param name The name of the smart plug.
     * @param isOn The on/off state of the smart plug.
     * @param amperage The amperage of the smart plug.
     */
    public SmartPlug(String name, boolean isOn , double amperage){
        super(name);
        this.isOn=isOn;
        this.amperage=amperage;
    }
    /**
     * Returns the name of the smart plug.
     * @return The name of the smart plug.
     */
    public String getName(){
        return super.getName();
    }

    /**
     * Sets the name of the smart plug.
     * @param name The new name of the smart plug.
     * @return The new name of the smart plug.
     */
    public String setName(String name) {
        super.setName(name);
        return name;
    }

    /**
     * Returns the time when the smart plug was last switched on or off.
     * @return The time when the smart plug was last switched on or off.
     */
    public LocalDateTime getLastSwitchTime() {
        return lastSwitchTime;
    }

    /**
     * Sets the time when the smart plug was last switched on or off.
     * @param lastSwitchTime The time when the smart plug was last switched on or off.
     */
    public void setLastSwitchTime(LocalDateTime lastSwitchTime) {
        this.lastSwitchTime = lastSwitchTime;
    }

    /**
     * Returns the current on/off state of the smart plug.
     * @return The current on/off state of the smart plug.
     */
    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * Sets the on/off state of the device.
     * @param isOn true to turn the device on, false to turn it off.
     * @return The new state of the device.
     */
    @Override
    public boolean setOn(boolean isOn) {
        this.isOn = isOn;
        return isOn;
    }
    /**
     * Returns the current usage of the device.
     * @return The usage of the device.
     */
    public double getUsage() {
        return usage;
    }

    /**
     * Sets the current usage of the device.
     * @param usage The new usage of the device.
     */
    public void setUsage(double usage) {
        this.usage = usage;
    }

    /**
     * Returns the voltage of the device.
     * @return The voltage of the device.
     */
    public int getVoltage() {
        return voltage;
    }

    /**
     * Returns the amperage of the device.
     * @return The amperage of the device.
     */
    public double getAmperage() {
        return amperage;
    }

    /**
     * Sets the amperage of the device.
     * @param amperage The new amperage of the device.
     */
    public void setAmperage(double amperage) {
        this.amperage = amperage;
    }

    /**
     * Returns true if the device is currently plugged in, false otherwise.
     * @return Whether the device is currently plugged in.
     */
    public boolean getplugIn(){
        return this.plugIn;
    }

    /**
     * Sets whether the device is currently plugged in.
     * @param plug Whether the device is currently plugged in.
     * @return The new plugged-in state of the device.
     */
    public boolean setplugIn(boolean plug){
        this.plugIn=plug;
        return plugIn;
    }

}
