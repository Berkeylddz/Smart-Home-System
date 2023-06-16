import java.time.LocalDateTime;
/**
 A SmartCamera is a type of SmartDevice that allows for surveillance and recording of video.
 */
class SmartCamera extends SmartDevice {
    private double megabytesPerMinute;
    private boolean isOn;
    private double storageUsed;
    private LocalDateTime lastSwitchtime;
    /**
     Creates a new SmartCamera object with the given name and default values for megabytesPerMinute, isOn, and storageUsed.
     @param name the name of the SmartCamera
     @param megabytesPerMinute the number of megabytes used by the SmartCamera per minute of recording
     */
    public SmartCamera(String name, double megabytesPerMinute) {
        super(name);
        this.megabytesPerMinute = megabytesPerMinute;
        this.isOn = false;
        this.storageUsed = 0;
    }
    /**
     Creates a new SmartCamera object with the given name and megabytesPerMinute, and sets the isOn property to the given value.
     @param name the name of the SmartCamera
     @param megabytesPerMinute the number of megabytes used by the SmartCamera per minute of recording
     @param isOn the initial state of the SmartCamera
     */
    public SmartCamera(String name, double megabytesPerMinute, boolean isOn) {
        super(name);
        this.megabytesPerMinute = megabytesPerMinute;
        this.isOn = isOn;
        this.storageUsed = 0;
    }

    // Getters and setters for properties

    /**
     Returns the time the SmartCamera was last switched on or off.
     @return the time the SmartCamera was last switched on or off
     */
    public LocalDateTime getLastSwitchtime() {
        return lastSwitchtime;
    }
    /**
     Sets the time the SmartCamera was last switched on or off.
     @param lastSwitchtime the new time the SmartCamera was last switched on or off
     */
    public void setLastSwitchtime(LocalDateTime lastSwitchtime) {
        this.lastSwitchtime = lastSwitchtime;
    }
    /**
     Returns the name of the SmartCamera.
     @return the name of the SmartCamera
     */
    public String getName() {
        return super.getName();
    }
    /**

     Sets the name of the camera.
     @param name the new name of the camera
     @return the new name of the camera
     */
    public String setName(String name) {
        super.setName(name);
        return name;
    }
    /**
     Returns the number of megabytes used by the SmartCamera per minute of recording.
     @return the number of megabytes used by the SmartCamera per minute of recording
     */
    public double getMegabytesPerMinute() {
        return megabytesPerMinute;
    }

    /**
     Returns the current state of the SmartCamera.
     @return true if the SmartCamera is on, false otherwise
     */
    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     Sets the state of the SmartCamera.
     @param isOn the new state of the SmartCamera
     @return the new state of the SmartCamera
     */
    @Override
    public boolean setOn(boolean isOn) {
        this.isOn = isOn;
        return isOn;
    }
    /**
     Returns the amount of storage used by the SmartCamera.
     @return the amount of storage used by the SmartCamera
     */
    public double getStorageUsed() {
        return storageUsed;
    }

    /**
     Sets the amount of storage used by the camera.
     @param integer the new amount of storage used by the camera
     */
    public void setStorageUsed(Integer integer) {

        this.storageUsed = integer;
    }


}
