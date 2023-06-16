import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 Abstract class that represents a smart device.
 A smart device has a name and can be turned on or off.
 It also keeps track of the time it was last switched on or off.
 */
public abstract class SmartDevice {
    private String name;
    private LocalDateTime switchTime = null;

    /**
     Returns the formatted string representation of the last switch time of the device.
     @return The formatted string representation of the last switch time of the device, or null if the switch time has not been set.
     */
    public String getSwitchTime(){
        String formattedTime = null;
        try {
            String switchTime = this.switchTime.toString();
            LocalDateTime localDateTime1 = LocalDateTime.parse(switchTime);
            formattedTime = localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        }
        catch (Exception e){
            ;
        }
        
        return formattedTime;
    }

    /**
     Returns the local date and time of the last switch of the device.
     @return The local date and time of the last switch of the device, or null if the switch time has not been set.
     */
    public LocalDateTime getSwitchLocalTime(){
        return switchTime;
    }

    /**
     Sets the switch time of the device.
     @param switchTime The switch time of the device.
     */
    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    /**
     Returns whether the device is currently on or not.
     @return True if the device is on, false otherwise.
     */
    public abstract boolean isOn();
    /**
     Sets the state of the device to on or off.
     @param isOn True if the device should be on, false if it should be off.
     @return True if the device state was set successfully, false otherwise.
     */
    public abstract boolean setOn(boolean isOn);

    /**
     Constructs a new SmartDevice with the given name.
     @param name The name of the SmartDevice.
     */
    public SmartDevice(String name) {
        this.name = name;
    }

    /**
     Returns the name of the SmartDevice.
     @return The name of the SmartDevice.
     */
    public String getName() {
        return name;
    }

    /**
     Sets the name of the SmartDevice.
     @param name The new name of the SmartDevice.
     @return The new name of the SmartDevice.
     */
    public String setName(String name){
        this.name = name;
        return name;
    }


}


