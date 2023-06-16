import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileController {
    private File path;
    public FileController(File path){
        this.path=path;
    }

    /**
     Reads an input file containing information about smart devices and their usage times.
     The method parses the input file and creates SmartDevice objects based on the input data.
     It also keeps track of the total time for which the smart devices are used.
     @throws Exception if the input file is not found or cannot be read
     */
    public void readInputFile(FileWriter fileWriter) throws Exception{

        Scanner sc = new Scanner(path);
        boolean flag = false;
        LocalDateTime Initialtime = null;
        ArrayList<SmartDevice> smartDevices = new ArrayList<>();
        ArrayList<String> smartDeviceNames = new ArrayList<>();
        int totalTime=0;


        /**
         * Reads the first line of the input file and sets the initial time for the simulation.
         * If the format of the initial date is incorrect or the first command in the file is not "SetInitialTime",
         * the program terminates with an error message.
         *
         * @param sc the Scanner object used to read the input file
         * @param flag a boolean value indicating if an error occurred while reading the input file
         * @param Initialtime the LocalDateTime object used to store the initial time for the simulation
         */
        while (true){
            String firstLine = sc.nextLine().trim();
            String[] firstLineInfo = firstLine.split("\t");

            if(firstLine.isEmpty()){
                continue;
            }

            else if (firstLineInfo[0].equals("SetInitialTime")) {
                String time=null;
                try{
                    time = (firstLineInfo[1]);
                }
                catch (Exception e){
                    flag=true;
                    fileWriter.write("COMMAND: "+firstLine+"\n");
                    fileWriter.write("ERROR: First command must be set initial time! Program is going to terminate!");
                    break;
                }

                try{
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd_H:m:s");
                    LocalDateTime dateTime = LocalDateTime.parse(time, formatter1);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");


                    String hour = String.valueOf(dateTime.getHour());
                    String minute = String.valueOf(dateTime.getMinute());
                    String second = String.valueOf(dateTime.getSecond());

                    if (minute.length() == 1) {
                        minute = "0" + minute;
                    }

                    if (second.length() == 1) {
                        second = "0" + second;
                    }

                    String outputDate = String.format("%04d-%02d-%02d_%02d:%02d:%02d",
                            dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                            dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());

                    Initialtime = LocalDateTime.parse(outputDate, formatter);


                }
                catch (Exception e){
                    flag=true;
                    fileWriter.write("COMMAND: "+firstLine+"\n");
                    fileWriter.write("ERROR: Format of the initial date is wrong! Program is going to terminate!"+"\n");
                    break;
                }

                fileWriter.write("COMMAND: "+firstLine+"\n");
                fileWriter.write("SUCCESS: Time has been set to "+time+"!"+"\n");
                break;
            }
            else{
                flag = true;
                fileWriter.write("COMMAND: "+firstLine+"\n");
                fileWriter.write("ERROR: First command must be set initial time! Program is going to terminate!"+"\n");
                break;
            }
        }



        LocalDateTime lastSwitchTime = Initialtime;

        List<SmartDevice> sortedDevices = null;
        String lastCommand=null;

        while (sc.hasNextLine() && flag==false){

            String line = sc.nextLine();
            String[] infos = line.split("\t");
            try{
                /**
                 * Performs the Add operation if the first element of the given array of Strings is "Add".
                 * The method checks if the second element is "SmartPlug", "SmartCamera", or "SmartLamp" and calls
                 * the corresponding constructor of the respective device class. If the smart device with the same name
                 * already exists, then it prints an error message. If the command contains erroneous or invalid data,
                 * it prints an error message as well.
                 * @param infos the array of Strings that contains the information about the command and the smart device
                 */
                if(infos[0].equals("Add")){
                    lastCommand = "Remove";

                    if (infos[1].equals("SmartPlug")){
                        if (infos.length == 3){
                            if (!smartDeviceNames.contains(infos[2])){
                                SmartDevice smartPlug = new SmartPlug(infos[2]);
                                smartDevices.add(smartPlug);
                                fileWriter.write("COMMAND: " + line+"\n");
                                smartDeviceNames.add(infos[2]);
                            }
                            else {
                                fileWriter.write("COMMAND: " + line+"\n");
                                fileWriter.write("ERROR: There is already a smart device with same name!\n");
                            }
                        }

                        else if(infos.length == 4 && infos[3].equals("On")){
                            if (!smartDeviceNames.contains(infos[2])){
                                SmartDevice smartPlug = new SmartPlug(infos[2],true);
                                smartDevices.add(smartPlug);
                                fileWriter.write("COMMAND: " + line+"\n");
                                smartDeviceNames.add(infos[2]);
                            }
                            else {
                                fileWriter.write("COMMAND: " + line+"\n");
                                fileWriter.write("ERROR: There is already a smart device with same name!\n");
                            }
                        }
                        else if(infos.length == 4 && infos[3].equals("Off")){
                            if (!smartDeviceNames.contains(infos[2])){
                                SmartDevice smartPlug = new SmartPlug(infos[2],false);
                                smartDevices.add(smartPlug);
                                fileWriter.write("COMMAND: " + line+"\n");
                                smartDeviceNames.add(infos[2]);
                            }
                            else {
                                fileWriter.write("COMMAND: " + line+"\n");
                                fileWriter.write("ERROR: There is already a smart device with same name!\n");
                            }
                        }
                        else if(infos.length == 5){

                            double amperage = Double.parseDouble(infos[4]);
                            if(amperage>0){
                                if (!smartDeviceNames.contains(infos[2])){
                                    SmartDevice smartPlug = new SmartPlug(infos[2],infos[3].equalsIgnoreCase("On"),amperage);
                                    smartDevices.add(smartPlug);
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    smartDeviceNames.add(infos[2]);
                                }
                                else {
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    fileWriter.write("ERROR: There is already a smart device with same name!\n");
                                }
                            }
                            else {
                                fileWriter.write("COMMAND: " + line+"\n");
                                fileWriter.write("ERROR: Ampere value must be a positive number!\n");
                            }

                        }
                    }


                    else if(infos[1].equals("SmartCamera")){
                        if (infos.length>3){
                            if(infos.length==4){
                                Integer status = Integer.parseInt(infos[3]);
                                if (status > 0){
                                    SmartDevice smartCamera = new SmartCamera(infos[2],status);
                                    smartDevices.add(smartCamera);
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    lastCommand = "Add";
                                    smartDeviceNames.add(infos[2]);
                                }
                                else{
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    fileWriter.write("ERROR: Megabyte value must be a positive number!\n");
                                }
                            }
                            else if(infos.length==5){
                                Double status = Double.parseDouble(infos[3]);
                                if (status>0){
                                    SmartDevice smartCamera = new SmartCamera(infos[2],status,infos[4].equalsIgnoreCase("On"));
                                    smartDevices.add(smartCamera);
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    lastCommand = "Add";
                                    smartDeviceNames.add(infos[2]);
                                }
                            }
                        }
                        else{
                            fileWriter.write("COMMAND: " + line+"\n");
                            fileWriter.write("ERROR: Erroneous command!\n");
                        }
                    }

                    else if(infos[1].equals("SmartLamp")){
                        if (!smartDeviceNames.contains(infos[2])){

                            if (infos.length == 3){
                                SmartDevice smartLamp = new SmartLamp(infos[2]);
                                smartDevices.add(smartLamp);
                                fileWriter.write("COMMAND: " + line+"\n");
                                lastCommand = "Add";
                                smartDeviceNames.add(infos[2]);
                            }
                            else if (infos.length==4) {
                                if (infos[3].equals("On") || infos[3].equals("Off")){
                                    SmartDevice smartLamp = new SmartLamp(infos[2],infos[3].equalsIgnoreCase("On"));
                                    smartDevices.add(smartLamp);
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    lastCommand = "Add";
                                    smartDeviceNames.add(infos[2]);
                                }else {
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    fileWriter.write("ERROR: Erroneous command!\n");
                                }
                            }
                            else if (infos.length==6) {
                                Integer kelvin = Integer.parseInt(infos[4]);

                                if (kelvin>=2000 && kelvin<=6500){
                                    Integer percantage = Integer.parseInt(infos[5]);
                                    if (percantage>=0 && percantage<=100){
                                        SmartDevice smartLamp = new SmartLamp(infos[2],infos[3].equalsIgnoreCase("On"),kelvin,percantage);
                                        smartDevices.add(smartLamp);
                                        fileWriter.write("COMMAND: " + line+"\n");
                                        lastCommand = "Add";
                                        smartDeviceNames.add(infos[2]);
                                    }else {
                                        fileWriter.write("COMMAND: " + line+"\n");
                                        fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                    }
                                }else {
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    fileWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                }
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: " + line+"\n");
                            fileWriter.write("ERROR: There is already a smart device with same name!\n");
                        }
                    }

                    else {
                        if (!smartDeviceNames.contains(infos[2])){
                            if (infos.length == 3){
                                SmartDevice smartClamp = new SmartColorLamp(infos[2]);
                                smartDevices.add(smartClamp);
                                fileWriter.write("COMMAND: " + line+"\n");
                                lastCommand = "Add";
                                smartDeviceNames.add(infos[2]);

                            }

                            else if (infos.length == 4) {
                                SmartDevice smartClamp = new SmartColorLamp(infos[2],infos[3].equalsIgnoreCase("On"));
                                smartDevices.add(smartClamp);
                                fileWriter.write("COMMAND: " + line+"\n");
                                lastCommand = "Add";
                                smartDeviceNames.add(infos[2]);

                            }

                            else if (infos.length == 6) {
                                char value = infos[4].charAt(0);
                                String number = String.valueOf(value);

                                if (number.equals("0")){
                                    if(infos[4].length()==8){
                                        String hexa =infos[4].substring(2,8);
                                        boolean flag2=false;
                                        for (int i = 0; i < hexa.length(); i++) {
                                            char c = hexa.charAt(i);
                                            if (!Character.isDigit(c) && (c < 'A' || c > 'F')) {
                                                fileWriter.write("COMMAND: " + line+"\n");
                                                fileWriter.write("ERROR: Erroneous command!\n");
                                                flag2=true;
                                                break;
                                            }
                                        }
                                        if(flag2==false){
                                            int code = Integer.parseInt(infos[4].substring(2), 16);
                                            if(code >= 0 && code < 16777215){
                                                Integer brightness = Integer.parseInt(infos[5]);

                                                SmartDevice smartClamp = new SmartColorLamp(infos[2],infos[3].equalsIgnoreCase("On"),code,brightness);
                                                smartDevices.add(smartClamp);
                                                fileWriter.write("COMMAND: " + line+"\n");
                                                lastCommand = "Add";
                                                smartDeviceNames.add(infos[2]);
                                            }

                                            else {
                                                fileWriter.write("COMMAND: " + line+"\n");
                                                fileWriter.write("ERROR: Erroneous command!\n");
                                            }
                                        }

                                    }
                                    else {
                                        fileWriter.write("COMMAND: " + line+"\n");
                                        fileWriter.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                                    }
                                }

                                else{
                                    Integer kelvin = Integer.parseInt(infos[4]);
                                    if (kelvin>=2000 && kelvin<=6500){
                                        Integer brightness = Integer.parseInt(infos[5]);
                                        if (brightness>=0 && brightness<=100){
                                            SmartDevice smartCLamp = new SmartColorLamp(infos[2],infos[3].equalsIgnoreCase("On"),kelvin,brightness,false);
                                            smartDevices.add(smartCLamp);
                                            fileWriter.write("COMMAND: " + line+"\n");
                                            lastCommand = "Add";
                                            smartDeviceNames.add(infos[2]);
                                        }
                                        else {
                                            fileWriter.write("COMMAND: " + line+"\n");
                                            fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                        }
                                    }
                                    else {
                                        fileWriter.write("COMMAND: " + line+"\n");
                                        fileWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                    }
                                }
                            }
                        }

                        else {
                            fileWriter.write("COMMAND: " + line+"\n");
                            fileWriter.write("ERROR: There is already a smart device with same name!\n");
                        }
                    }
                }

                /**
                 Removes a smart device from the list of smart devices.
                 @param line a String representing the command line entered by the user.
                 @param smartDevices a List of SmartDevice objects representing the current list of smart devices.
                 @param smartDeviceNames a Set of Strings representing the names of the smart devices in the current list.
                 */
                else if (infos[0].equals("Remove")) {
                    lastCommand = "Remove";

                    Iterator<SmartDevice> iterator = smartDevices.iterator();

                    while (iterator.hasNext()) {
                        SmartDevice device = iterator.next();
                        if (device.getName().equals(infos[1])) {
                            smartDeviceNames.remove(infos[1]);
                            if(device instanceof SmartColorLamp){
                                SmartColorLamp lamp = (SmartColorLamp) device;

                                smartDevices.remove(lamp);
                                fileWriter.write("COMMAND: "+line+"\n");
                                lastCommand = "Remove";
                                fileWriter.write("SUCCESS: Information about removed smart device is as follows:\n");
                                fileWriter.write("Smart Color Lamp " + infos[1] + " is off and its color value is "
                                        + lamp.getKelvin() + "K with " + lamp.getBrightness() + "% brightness, and its " +
                                        "time to switch its status is null.\n");
                            }else if(device instanceof SmartLamp){
                                SmartLamp lamp = (SmartLamp) device;
                                smartDevices.remove(lamp);
                                fileWriter.write("COMMAND: "+line+"\n");
                                lastCommand = "Remove";
                                fileWriter.write("SUCCESS: Information about removed smart device is as follows:\n");
                                fileWriter.write("Smart Lamp " + infos[1] + " is off and its kelvin value is "
                                        + lamp.getKelvin() + "K with " + lamp.getBrightness() + "% brightness, and its time to switch its status is null.\n");
                            }else if(device instanceof SmartPlug){
                                SmartPlug lamp = (SmartPlug) device;
                                smartDevices.remove(lamp);
                                lamp.setOn(false);
                                lamp.setLastSwitchTime(Initialtime);


                                Duration duration = Duration.between(lastSwitchTime,lamp.getLastSwitchTime());

                                double value=((int)lamp.getAmperage()*lamp.getVoltage()*(int)duration.toMinutes())/60;
                                lamp.setUsage(value);
                                lastSwitchTime=Initialtime;

                                fileWriter.write("COMMAND: "+line+"\n");
                                lastCommand = "Remove";
                                fileWriter.write("SUCCESS: Information about removed smart device is as follows:\n");
                                fileWriter.write("Smart Plug " + infos[1] + " is off and consumed "
                                        + String.format("%.2f",lamp.getUsage()) + "W so far (excluding current device)," +
                                        " and its time to switch its status is null.\n");
                            }else if(device instanceof SmartCamera){
                                SmartCamera lamp = (SmartCamera) device;
                                smartDevices.remove(lamp);
                                lamp.setOn(false);
                                lamp.setLastSwitchtime(Initialtime);
                                Duration duration = Duration.between(lastSwitchTime, (lamp.getLastSwitchtime()));


                                lamp.setStorageUsed((int) (duration.toMinutes() * lamp.getMegabytesPerMinute()));

                                fileWriter.write("COMMAND: "+line+"\n");
                                lastCommand = "Remove";
                                fileWriter.write("SUCCESS: Information about removed smart device is as follows:\n");
                                fileWriter.write("Smart Camera " + infos[1] + " is off and used " + String.format("%.2f",lamp.getStorageUsed())+ " MB of storage so far (excluding current status), and its time to switch its status is null.\n");
                            }
                            break;
                        }
                    }
                }

                /**
                 This method switches on or off a smart device based on the command received in the input string.
                 If the device name is not found in the list of smart devices, it returns an error message.
                 If the device is already in the requested state, it returns an error message.
                 If the device is a SmartCamera or SmartPlug and is switched off, it sets the last switch time to the current time.
                 @param line The input string containing the command to switch on or off a smart device.
                 */
                else if (infos[0].equals("Switch")) {
                    lastCommand = "Remove";

                    if(smartDeviceNames.contains(infos[1])){
                        Iterator<SmartDevice> iterator = smartDevices.iterator();
                        while (iterator.hasNext()) {
                            SmartDevice device = iterator.next();
                            if(device.getName().equals(infos[1])){
                                if (infos[2].equals("On") && device.isOn()){
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    fileWriter.write("ERROR: This device is already switched on!\n");

                                } else if (infos[2].equals("On") && device.isOn()==false){
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    lastCommand = "Switch";
                                    device.setOn(true);

                                }else if (infos[2].equals("Off") && device.isOn()){
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    lastCommand = "Switch";
                                    device.setOn(false);
                                    if(device instanceof SmartCamera){
                                        ((SmartCamera) device).setLastSwitchtime(Initialtime);
                                    }
                                    else if(device instanceof SmartPlug){
                                        ((SmartPlug) device).setLastSwitchTime(Initialtime);
                                    }
                                }
                                else {
                                    fileWriter.write("COMMAND: " + line+"\n");
                                    fileWriter.write("ERROR: This device is already switched off!\n");
                                }
                            }
                        }

                    }
                    else{
                        fileWriter.write("COMMAND: " + line+"\n");
                        fileWriter.write("ERROR: There is not such a device!\n");

                    }
                }

                /**
                 * This method checks if the first element in the `infos` array is equal to the string "PlugIn". If this condition is true, the method performs the following actions:
                 * 1. It parses the third element in the `infos` array as an integer and assigns it to the variable `amper`.
                 * 2. It assigns the second element in the `infos` array to the variable `name`.
                 * 3. It initializes a `SmartDevice` object called `device` to null.
                 * 4. It enters a `for` loop that iterates over the collection of `SmartDevice` objects called `smartDevices` and searches for a device with a name that matches `name`. If it finds a match, it assigns the device to the `device` variable and exits the loop.
                 * 5. If `device` is still null after the loop exits, it prints an error message indicating that the device is not a smart plug.
                 * 6. If `device` is a `SmartPlug`, it checks if the `amper` value is greater than 0. If so, it checks if the plug is already in use. If it is not in use, it sets the plug state to plugged in, sets the amperage, and prints a confirmation message. If the plug is already in use, it prints an error message indicating that there is already an item plugged in.
                 * 7. If `amper` is less than or equal to 0, it prints an error message indicating that the ampere value must be a positive number.
                 * 8. If `device` is not a `SmartPlug`, it prints an error message indicating that the device is not a smart plug.
                 *
                 * @param infos an array of strings containing information about the command to execute
                 * @param smartDevices a collection of `SmartDevice` objects to search for the device to plug in
                 */
                else if (infos[0].equals("PlugIn")){
                    lastCommand = "Remove";

                    Integer amper = Integer.parseInt(infos[2]);
                    String name = infos[1];
                    SmartDevice device = null;

                    for (SmartDevice smartDevice : smartDevices) {
                        if (smartDevice.getName().equals(name)) {
                            device = smartDevice;
                            break;
                        }
                    }
                    if(device == null){
                        fileWriter.write("COMMAND: "+line + "\n" );
                        fileWriter.write("ERROR: This device is not a smart plug!\n");
                    }
                    else {

                        if(device instanceof SmartPlug){
                            if(amper>0){
                                if (((SmartPlug) device).getplugIn() == false){
                                    ((SmartPlug) device).setplugIn(true);
                                    ((SmartPlug) device).setAmperage(amper);
                                    fileWriter.write("COMMAND: "+line + "\n" );
                                    lastCommand = "PlugIn";
                                } else if (((SmartPlug) device).getplugIn()==true) {
                                    fileWriter.write("COMMAND: "+line + "\n" );
                                    fileWriter.write("ERROR: There is already an item plugged in to that plug!\n");
                                }
                            }
                            else {
                                fileWriter.write("COMMAND: "+line + "\n" );
                                fileWriter.write("ERROR: Ampere value must be a positive number!\n");
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line + "\n" );
                            fileWriter.write("ERROR: This device is not a smart plug!\n");
                        }
                    }

                }

                /**
                 * This method checks if the first element in the `infos` array is equal to the string "PlugOut". If this condition is true, the method performs the following actions:
                 * 1. It creates an iterator to iterate over a collection of `SmartDevice` objects called `smartDevices`.
                 * 2. It enters a `while` loop that continues iterating over the collection as long as there are more elements to process.
                 * 3. For each `SmartDevice` in the collection, it checks if the device's name matches the second element in the `infos` array.
                 * 4. If the device is a `SmartPlug` and is currently plugged in, it sets the plug state to unplugged and prints a confirmation message.
                 * 5. If the device is a `SmartPlug` but is not currently plugged in, it prints an error message indicating that there is nothing to unplug.
                 * @param infos an array of strings containing information about the command to execute
                 * @param smartDevices a collection of `SmartDevice` objects to search for the device to unplug
                 */
                else if(infos[0].equals("PlugOut")){
                    lastCommand = "Remove";

                    Iterator<SmartDevice> iterator = smartDevices.iterator();
                    while (iterator.hasNext()) {
                        SmartDevice device = iterator.next();
                        if(device.getName().equals(infos[1])){

                            if(device instanceof SmartPlug){
                                if(((SmartPlug) device).getplugIn()==true){
                                    ((SmartPlug) device).setplugIn(false);
                                    fileWriter.write("COMMAND: "+line+"\n");
                                    lastCommand = "PlugIn";
                                }
                                else {
                                    fileWriter.write("COMMAND: "+line+"\n");
                                    fileWriter.write("ERROR: This plug has no item to plug out from that plug!\n");
                                }
                            }
                        }

                    }
                }

                /**
                 Sets the color temperature in Kelvin of a smart lamp or smart color lamp specified by the command line input.
                 @param line the command line input containing the device name and the desired Kelvin value
                 @param smartDevices the list of available smart devices
                 */
                else if(infos[0].equals("SetKelvin")){
                    lastCommand = "Remove";
                    String name = infos[1].substring(0,4);
                    if (name.equals("Lamp")){
                        Integer kelvin = Integer.parseInt(infos[2]);
                        if(kelvin>=2000 && kelvin<=6500){
                            Iterator<SmartDevice> iterator = smartDevices.iterator();
                            while (iterator.hasNext()) {
                                SmartDevice device = iterator.next();
                                if(device.getName().equals(infos[1]) &&  device instanceof SmartLamp){
                                    ((SmartLamp) device).setKelvin(kelvin);
                                    fileWriter.write("COMMAND: "+line+"\n");
                                    lastCommand = "PlugIn";
                                }
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        }


                    }
                    else if(name.equals("CLam")){
                        Integer kelvin = Integer.parseInt(infos[2]);
                        if(kelvin>=2000 && kelvin<=6500){
                            Iterator<SmartDevice> iterator = smartDevices.iterator();
                            while (iterator.hasNext()) {
                                SmartDevice device = iterator.next();
                                if(device.getName().equals(infos[1]) &&  device instanceof SmartColorLamp){
                                    ((SmartColorLamp) device).setKelvin(kelvin);
                                    fileWriter.write("COMMAND: "+line+"\n");
                                    lastCommand = "PlugIn";
                                }
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        }
                    }
                    else {
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: This device is not a smart lamp!\n");
                    }

                }

                /**
                 Sets the brightness of a smart lamp or color lamp based on the provided command.
                 If the provided device name does not match a smart lamp or color lamp, an error message will be printed.
                 If the provided brightness value is outside the valid range, an error message will be printed.
                 @param line a string representing the command to set the brightness of a device, in the format "SetBrightness deviceName brightnessValue"
                 @throws NumberFormatException if the provided brightness value cannot be parsed as an Integer
                 */
                else if(infos[0].equals("SetBrightness")){
                    lastCommand = "Remove";
                    String name = infos[1].substring(0,4);
                    if (name.equals("Lamp")){
                        Integer brightness = Integer.parseInt(infos[2]);
                        if(brightness>=0 && brightness<=100){
                            Iterator<SmartDevice> iterator = smartDevices.iterator();
                            while (iterator.hasNext()) {
                                SmartDevice device = iterator.next();
                                if(device.getName().equals(infos[1]) &&  device instanceof SmartLamp){
                                    ((SmartLamp) device).setBrightness(brightness);
                                    fileWriter.write("COMMAND: "+line +"\n");
                                    lastCommand = "PlugIn";
                                }
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line +"\n");
                            fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                        }


                    }
                    else if(name.equals("CLam")){
                        Integer brightness = Integer.parseInt(infos[2]);
                        if(brightness>=0 && brightness<=100){
                            Iterator<SmartDevice> iterator = smartDevices.iterator();
                            while (iterator.hasNext()) {
                                SmartDevice device = iterator.next();
                                if(device.getName().equals(infos[1]) &&  device instanceof SmartColorLamp){
                                    ((SmartColorLamp) device).setBrightness(brightness);
                                    fileWriter.write("COMMAND: "+line +"\n");
                                    lastCommand = "PlugIn";
                                }
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line +"\n");
                            fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                        }
                    }
                    else {
                        fileWriter.write("COMMAND: "+line +"\n");
                        fileWriter.write("ERROR: This device is not a smart lamp!\n");
                    }

                }

                /**
                 * Sets the color code of a smart color lamp device.
                 * @param line the command string to be executed
                 * @param smartDevices the list of smart devices available
                 * @throws NumberFormatException if the color code is not in the correct hexadecimal format
                 * @throws IllegalArgumentException if the color code is not within the range of 0 to 16777215
                 * @throws NoSuchElementException if no smart color lamp device with the given name is found in the list of smart devices
                 * @throws IllegalStateException if the device with the given name is not a smart color lamp device
                 */
                else if(infos[0].equals("SetColorCode")){
                    lastCommand = "Remove";
                    String name = infos[1].substring(0,4);
                    if(name.equals("CLam")){
                        String hexa =infos[2].substring(2,8);
                        for (int i = 0; i < hexa.length(); i++) {
                            char c = hexa.charAt(i);
                            // Check if the character is a valid hexadecimal digit
                            if (!Character.isDigit(c) && (c < 'A' || c > 'F')) {
                                fileWriter.write("COMMAND: "+line +"\n");
                                fileWriter.write("ERROR: Erroneous command!\n");
                                break;
                            }
                        }
                        int code = Integer.parseInt(infos[2].substring(2), 16);
                        if(code >= 0 && code < 16777215){
                            Iterator<SmartDevice> iterator = smartDevices.iterator();
                            while (iterator.hasNext()) {
                                SmartDevice device = iterator.next();
                                if(device.getName().equals(infos[1]) &&  device instanceof SmartColorLamp){
                                    ((SmartColorLamp) device).setColorCode(code);
                                    fileWriter.write("COMMAND: "+line +"\n");
                                    lastCommand = "PlugIn";
                                }
                            }
                        }

                    }
                    else {
                        fileWriter.write("COMMAND: "+line +"\n");
                        fileWriter.write("ERROR: This device is not a smart color lamp!\n");
                    }
                }

                /**
                 Sets the Kelvin and brightness values of a smart lamp or smart color lamp.
                 If the device name matches a smart lamp or smart color lamp, sets the kelvin and brightness values and prints
                 the command. Otherwise, prints an error message.
                 @param line the command line
                 */
                else if(infos[0].equals("SetWhite")){
                    String name = infos[1].substring(0,4);
                    lastCommand = "Remove";
                    if (name.equals("Lamp")){
                        Integer kelvin = Integer.parseInt(infos[2]);
                        Integer brigtness = Integer.parseInt(infos[3]);
                        if(kelvin<=6500 && kelvin>=2000){

                            if(brigtness<=100 && brigtness>=0){
                                Iterator<SmartDevice> iterator = smartDevices.iterator();
                                while (iterator.hasNext()) {
                                    SmartDevice device = iterator.next();
                                    if(device.getName().equals(infos[1]) &&  device instanceof SmartLamp){

                                        ((SmartLamp) device).setKelvin(kelvin);
                                        ((SmartLamp) device).setBrightness(brigtness);
                                        fileWriter.write("COMMAND: "+line+"\n");
                                        lastCommand = "PlugIn";

                                    }
                                }
                            }
                            else {
                                fileWriter.write("COMMAND: "+line+"\n");
                                fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        }

                    }
                    else if (name.equals("CLam")) {
                        Integer kelvin = Integer.parseInt(infos[2]);
                        Integer brigtness = Integer.parseInt(infos[3]);

                        if(kelvin<=6500 && kelvin>=2000){

                            if(brigtness<=100 && brigtness>=0){
                                Iterator<SmartDevice> iterator = smartDevices.iterator();
                                while (iterator.hasNext()) {
                                    SmartDevice device = iterator.next();
                                    if(device.getName().equals(infos[1]) &&  device instanceof SmartColorLamp){

                                        ((SmartColorLamp) device).setKelvin(kelvin);
                                        ((SmartColorLamp) device).setBrightness(brigtness);
                                        fileWriter.write("COMMAND: "+line+"\n");
                                        lastCommand = "PlugIn";

                                    }
                                }
                            }
                            else {
                                fileWriter.write("COMMAND: "+line+"\n");
                                fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                            }
                        }
                        else {
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        }
                    }
                    else {
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: This device is not a smart lamp!\n");
                    }
                }

                /**
                 This method sets the color of a SmartColorLamp device based on the command input.
                 @param line the command string to be executed
                 */
                else if(infos[0].equals("SetColor")){
                    lastCommand = "Remove";
                    String name = infos[1].substring(0,4);

                    if(name.equals("CLam")){
                        String hexa =infos[2].substring(2,8);
                        boolean flag1 = false;
                        for (int i = 0; i < hexa.length(); i++) {
                            char c = hexa.charAt(i);
                            // Check if the character is a valid hexadecimal digit
                            if (!Character.isDigit(c) && (c < 'A' || c > 'F')) {
                                fileWriter.write("COMMAND: "+line+"\n");
                                fileWriter.write("ERROR: Erroneous command!\n");
                                flag1 = true;
                                break;
                            }
                        }
                        if(flag1==false){
                            Integer brightness = Integer.parseInt(infos[3]);
                            if(brightness>=0 && brightness<=100){
                                int code = Integer.parseInt(infos[2].substring(2), 16);
                                if(code >= 0 && code <= 16777215){
                                    Iterator<SmartDevice> iterator = smartDevices.iterator();
                                    while (iterator.hasNext()) {
                                        SmartDevice device = iterator.next();
                                        if(device.getName().equals(infos[1]) &&  device instanceof SmartColorLamp){
                                            ((SmartColorLamp) device).setColorCode(code);
                                            ((SmartColorLamp) device).setBrightness(brightness);
                                            ((SmartColorLamp) device).setColorMode(true);
                                            fileWriter.write("COMMAND: "+line+"\n");
                                            lastCommand = "PlugIn";
                                            break;
                                        }
                                    }
                                }
                            }
                            else {
                                fileWriter.write("COMMAND: "+line+"\n");
                                fileWriter.write("ERROR: Brightness must be in range of 0%-100%!\n");
                            }
                        }

                    }
                    else {
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: This device is not a smart color lamp!\n");
                    }
                }

                /**
                 Sets the time of the system and turns on/off smart devices scheduled before the new time.
                 @param line the input command line
                 */

                else if(infos[0].equals("SetTime")){
                    String newTime = infos[1];
                    lastCommand = "Remove";
                    LocalDateTime newTime2=null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    try{
                        newTime2 = LocalDateTime.parse(newTime,formatter);
                    }
                    catch (Exception e){
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: Time format is not correct!\n");
                    }

                    if(newTime2.equals(Initialtime)){
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: There is nothing to change!\n");
                        lastCommand = "Remove";

                    }
                    else {
                        if(Initialtime.isAfter(newTime2)){
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Time cannot be reversed!\n");

                        }
                        else {
                            Initialtime = LocalDateTime.parse(newTime, formatter);
                            fileWriter.write("COMMAND: " + line + "\n");
                            lastCommand = "PlugIn";

                            //Collections.sort(smartDevices, Comparator.comparing(SmartDevice::getSwitchLocalTime, Comparator.nullsLast(Comparator.naturalOrder())));

                            LocalDateTime closestSwitchTime = smartDevices.get(0).getSwitchLocalTime();

                            sortedDevices = new ArrayList<>(smartDevices);

                            for (SmartDevice device : smartDevices) {
                                LocalDateTime switchTime = device.getSwitchLocalTime();
                                if (switchTime != null && switchTime.compareTo(Initialtime) <= 0) {

                                    if (device instanceof SmartPlug) {
                                        SmartPlug plug = (SmartPlug) device;
                                        if (plug.isOn()) {
                                            plug.setOn(false);
                                        } else {
                                            plug.setOn(true);
                                        }
                                        device.setSwitchTime(null);
                                    } else if (device instanceof SmartColorLamp) {
                                        SmartColorLamp colorLamp = (SmartColorLamp) device;
                                        if (colorLamp.isOn()) {
                                            colorLamp.setOn(false);
                                        } else {
                                            colorLamp.setOn(true);
                                        }
                                        device.setSwitchTime(null);
                                    } else if (device instanceof SmartLamp) {
                                        SmartLamp lamp = (SmartLamp) device;
                                        if (lamp.isOn()) {
                                            lamp.setOn(false);
                                        } else {
                                            lamp.setOn(true);
                                        }
                                        device.setSwitchTime(null);
                                    } else if (device instanceof SmartCamera) {
                                        SmartCamera camera = (SmartCamera) device;
                                        if(camera.isOn()) {
                                            camera.setOn(false);
                                        }
                                        else {
                                            camera.setOn(true);
                                        }
                                        device.setSwitchTime(null);
                                    }
                                    sortedDevices.sort(Comparator.comparing(SmartDevice::getSwitchTime, Comparator.nullsLast(Comparator.naturalOrder())));



                                }
                            }

                        }
                    }
                }

                /**

                 Sets the initial time of the smart home system.
                 This command is not supported and will print an error message.
                 @param line the input command line
                 */
                else if (infos[0].equals("SetInitialTime")) {
                    lastCommand = "Remove";
                    fileWriter.write("COMMAND: "+line+"\n");
                    fileWriter.write("ERROR: Erroneous command!\n");
                }

                /**
                 This method is used to skip the time by the specified number of minutes.
                 If the input command is erroneous or the specified time is negative, an error message is printed.
                 If the specified time is zero, it means there is nothing to skip.
                 @param line the input command as a String
                 @param smartDevices a list of SmartDevice objects
                 @param smartDeviceNames a list of SmartDevice names
                 @param Initialtime the initial LocalDateTime object representing the starting time
                 */
                else if (infos[0].equals("SkipMinutes")){
                    lastCommand = "Remove";

                    Integer minutes = null;
                    try{
                        minutes= Integer.parseInt(infos[1]);
                    }catch (Exception e){
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: Erroneous command!\n");
                        lastCommand = "Remove";

                    }

                    if(minutes instanceof Integer){
                        if(minutes<0){
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Time cannot be reversed!\n");
                        }
                        else if(minutes==0){
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: There is nothing to skip!\n");
                        } else if (infos.length>2) {
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Erroneous command!\n");
                        }else if(minutes>0){
                            Initialtime = Initialtime.plusMinutes(minutes);
                            fileWriter.write("COMMAND: "+line+"\n");
                            lastCommand = "PlugIn";

                        }
                    }

                }

                /**
                 * Changes the name of a smart device.
                 * @param line the command line to be executed
                 * @param smartDevices the list of all smart devices
                 * @param smartDeviceNames the list of all smart device names
                 */
                else if (infos[0].equals("ChangeName")) {
                    lastCommand = "Remove";

                    if(infos.length==2){
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: Erroneous command!\n");
                    }
                    else {
                        if(infos[1].equals(infos[2])){
                            fileWriter.write("COMMAND: "+line+"\n");
                            fileWriter.write("ERROR: Both of the names are the same, nothing changed!\n");
                        }
                        else{
                            if(smartDeviceNames.contains(infos[2])){
                                fileWriter.write("COMMAND: "+line+"\n");
                                fileWriter.write("ERROR: There is already a smart device with same name!\n");
                            }
                            else {
                                Iterator<SmartDevice> iterator = smartDevices.iterator();
                                while (iterator.hasNext()) {
                                    SmartDevice device = iterator.next();
                                    if(device.getName().equals(infos[1])){
                                        device.setName(infos[2]);
                                        fileWriter.write("COMMAND: "+line+"\n");
                                        lastCommand = "PlugIn";
                                        for (int i = 0; i < smartDeviceNames.size(); i++) {
                                            String name = smartDeviceNames.get(i);
                                            if (name.equals(infos[1])) {
                                                smartDeviceNames.set(i, infos[2]);
                                                break;
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }
                }

                /**
                 Generates a Z report of all smart devices in the system.
                 This report includes the current date and time, the status and usage of each device,
                 and the time at which each device is scheduled to switch status.
                 @param line the command line string entered by the user
                 */
                else if(infos[0].equals("ZReport")){
                    String time = Initialtime.toString();
                    LocalDateTime localDateTime = LocalDateTime.parse(time);
                    String formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

                    fileWriter.write("COMMAND: "+line+"\n");
                    fileWriter.write("Time is:\t" + formattedDateTime+"\n");
                    lastCommand = "ZReport";

                    try{
                        Collections.sort(smartDevices, Comparator.comparing(SmartDevice::getSwitchTime, Comparator.nullsLast(Comparator.naturalOrder())));
                        sortedDevices.sort(Comparator.comparing(SmartDevice::getSwitchTime, Comparator.nullsLast(Comparator.naturalOrder())));
                    }
                    catch (Exception e){
                        sortedDevices = smartDevices;
                    }


                    for (SmartDevice device : sortedDevices) {
                        if (device instanceof SmartPlug) {
                            SmartPlug plug = (SmartPlug) device;
                            fileWriter.write("Smart Plug "+plug.getName()+" is "+(plug.isOn() ? "on":"off")
                                    +" and consumed "+String.format("%.2f",plug.getUsage())+"W so far " +
                                    "(excluding current device), and its time to switch its status is "+device.getSwitchTime()+".\n");
                        } else if (device instanceof SmartColorLamp) {
                            if (((SmartColorLamp) device).isColorMode()) {
                                SmartColorLamp colorLamp = (SmartColorLamp) device;
                                fileWriter.write("Smart Color Lamp "+colorLamp.getName()+" is "+
                                        (colorLamp.isOn() ? "on": "off")+" and its color value is "+colorLamp.getColorCode()+
                                        " with "+colorLamp.getBrightness()+"% brightness, and its time to switch its status is "+device.getSwitchTime()+".\n");
                            } else {
                                SmartColorLamp colorLamp = (SmartColorLamp) device;
                                fileWriter.write("Smart Color Lamp "+colorLamp.getName()+" is "+
                                        (colorLamp.isOn() ? "on" : "off")+" and its color value is "+colorLamp.getKelvin()
                                        +"K with "+colorLamp.getBrightness()+"% brightness, and its time to switch its status is "+device.getSwitchTime()+".\n");
                            }
                        } else if (device instanceof SmartLamp) {
                            SmartLamp lamp = (SmartLamp) device;
                            fileWriter.write("Smart Lamp "+lamp.getName()+" is "+(lamp.isOn() ? "on" : "off")+" and its kelvin value is "+
                                    lamp.getKelvin()+"K with "+lamp.getBrightness()+
                                    "% brightness, and its time to switch its status is "+device.getSwitchTime()+".\n");

                            } else if (device instanceof SmartCamera) {
                            SmartCamera camera = (SmartCamera) device;
                            fileWriter.write("Smart Camera "+camera.getName()+" is "+ (camera.isOn() ? "on" : "off")
                            +" and used "+String.format("%.2f",camera.getStorageUsed())+
                                    " MB of storage so far (excluding current status), and its time to switch its status is "+device.getSwitchTime()+".\n");
                        }
                    }
                }

                /**
                 * Executes the "Nop" command. This command sets the initial time to the time of the next scheduled device switch,
                 * and then switches all devices whose scheduled switch time is equal to the next scheduled switch time.
                 * If there are no scheduled device switches, an error message is displayed.
                 * @param line the command line input as a string
                 */
                else if(infos[0].equals("Nop")){

                    fileWriter.write("COMMAND: "+line+"\n");
                    lastCommand = "PlugIn";
                    LocalDateTime systemTime=null;
                    LocalDateTime closestSwitchTime = null;
                    for (SmartDevice device : smartDevices) {
                        LocalDateTime switchTime = device.getSwitchLocalTime();
                        if (switchTime != null && (closestSwitchTime == null || switchTime.isBefore(closestSwitchTime))) {
                            closestSwitchTime = switchTime;
                        }
                    }
                    if (closestSwitchTime != null) {
                        systemTime = closestSwitchTime;
                        for (SmartDevice device : smartDevices) {
                            LocalDateTime switchTime = device.getSwitchLocalTime();
                            if (switchTime != null && switchTime.equals(closestSwitchTime)) {
                                //device.switchStatus();
                                Initialtime = closestSwitchTime;
                                device.setSwitchTime(null);
                                if(device instanceof SmartCamera){
                                    if(device.isOn()==true){
                                        device.setOn(false);
                                    }
                                    else {
                                        device.setOn(true);
                                    }
                                }
                                else if(device instanceof SmartPlug){
                                    if(device.isOn()==true){
                                        device.setOn(false);
                                    }
                                    else {
                                        device.setOn(true);
                                    }
                                }
                                else if(device instanceof SmartColorLamp){
                                    if(device.isOn()==true){
                                        device.setOn(false);
                                    }
                                    else {
                                        device.setOn(true);
                                    }
                                }
                                else if(device instanceof SmartLamp){
                                    if(device.isOn()==true){
                                        device.setOn(false);
                                    }
                                    else {
                                        device.setOn(true);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        fileWriter.write("ERROR: There is nothing to switch!\n");
                    }

                }

                /**

                 Sets the switch time for a specified smart device.
                 The command format should be "SetSwitchTime <device_name> <switch_time>".
                 The switch time should be in the format "yyyy-MM-dd_HH:mm:ss".
                 If the device name is not found, an error message is printed.
                 @param line the command line input
                 */
                else if (infos[0].equals("SetSwitchTime")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    LocalDateTime time = LocalDateTime.parse(infos[2],formatter);
                    lastCommand = "Remove";

                    if(time.isAfter(Initialtime) || time.isEqual(Initialtime)){
                        Iterator<SmartDevice> iterator = smartDevices.iterator();
                        while (iterator.hasNext()) {
                            SmartDevice device = iterator.next();

                            if(device.getName().equals(infos[1])){
                                if(time.equals(Initialtime)){

                                    fileWriter.write("COMMAND: "+line+"\n");
                                    if(device.isOn()){
                                        device.setOn(false);
                                    }
                                    else {
                                        device.setOn(true);
                                    }
                                    device.setSwitchTime(null);
                                    break;
                                }
                            }


                            if(device.getName().equals(infos[1])){
                                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                                fileWriter.write("COMMAND: "+line+"\n");
                                lastCommand = "PlugIn";
                                LocalDateTime localDateTime = LocalDateTime.parse(infos[2],formatter);
                                device.setSwitchTime(localDateTime);

                            }
                        }
                    }
                    else {
                        fileWriter.write("COMMAND: "+line+"\n");
                        fileWriter.write("ERROR: Switch time cannot be in the past!\n");
                    }

                }

                else if(infos[0].isEmpty()){
                    ;
                }

                else{
                    fileWriter.write("COMMAND: "+line+"\n");
                    fileWriter.write("ERROR: Erroneous command!\n");
                }

                /**
                 Calculates and updates the storage used and usage for each SmartCamera and SmartPlug device respectively,
                 based on their last switch time and current on/off state.
                 @param smartDevices an ArrayList of SmartDevice objects containing SmartCamera and SmartPlug devices.
                 @param lastSwitchTime the LocalDateTime object representing the last switch time.
                 @param Initialtime the LocalDateTime object representing the initial time.
                 @param totalTime the integer variable representing the total time.
                 */

                for(SmartDevice smartDevice: smartDevices){
                    if(smartDevice instanceof SmartCamera){
                        if(smartDevice.isOn()==false){
                            SmartCamera camera =(SmartCamera) smartDevice;
                            Duration duration = Duration.between(lastSwitchTime,(camera.getLastSwitchtime()));
                            ((SmartCamera) smartDevice).setStorageUsed((int) (duration.toMinutes()*((SmartCamera) smartDevice).getMegabytesPerMinute()));
                        }
                    }

                    else if(smartDevice instanceof SmartPlug){
                        if(smartDevice.isOn()==false && ((SmartPlug) smartDevice).getLastSwitchTime()!= null && ((SmartPlug) smartDevice).getplugIn()){
                            SmartPlug plug = (SmartPlug) smartDevice;
                            Duration duration = Duration.between(lastSwitchTime,plug.getLastSwitchTime());
                            totalTime+=duration.toMinutes();
                            double value=(plug.getAmperage()*plug.getVoltage()*totalTime)/60;
                            plug.setUsage(value);
                            lastSwitchTime=Initialtime;

                        }
                    }
                }


            }
            catch (Exception e){
                ;
            }



        }
        try{
            if(!lastCommand.equals("ZReport")){
                String time = Initialtime.toString();
                LocalDateTime localDateTime = LocalDateTime.parse(time);
                String formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

                fileWriter.write("ZReport:\n");
                fileWriter.write("Time is:\t" + formattedDateTime+"\n");


                try{
                    Collections.sort(smartDevices, Comparator.comparing(SmartDevice::getSwitchTime, Comparator.nullsLast(Comparator.naturalOrder())));
                    sortedDevices.sort(Comparator.comparing(SmartDevice::getSwitchTime, Comparator.nullsLast(Comparator.naturalOrder())));
                }
                catch (Exception e){
                    sortedDevices = smartDevices;
                }


                for (SmartDevice device : sortedDevices) {
                    if (device instanceof SmartPlug) {
                        SmartPlug plug = (SmartPlug) device;
                        fileWriter.write("Smart Plug "+plug.getName()+" is "+(plug.isOn() ? "on":"off")
                                +" and consumed "+String.format("%.2f",plug.getUsage())+"W so far " +
                                "(excluding current device), and its time to switch its status is "+device.getSwitchTime()+".\n");
                    } else if (device instanceof SmartColorLamp) {
                        if (((SmartColorLamp) device).isColorMode()) {
                            SmartColorLamp colorLamp = (SmartColorLamp) device;
                            fileWriter.write("Smart Color Lamp "+colorLamp.getName()+" is "+
                                    (colorLamp.isOn() ? "on": "off")+" and its color value is "+colorLamp.getColorCode()+
                                    " with "+colorLamp.getBrightness()+"% brightness, and its time to switch its status is "+device.getSwitchTime()+".\n");
                        } else {
                            SmartColorLamp colorLamp = (SmartColorLamp) device;
                            fileWriter.write("Smart Color Lamp "+colorLamp.getName()+" is "+
                                    (colorLamp.isOn() ? "on" : "off")+" and its color value is "+colorLamp.getKelvin()
                                    +"K with "+colorLamp.getBrightness()+"% brightness, and its time to switch its status is "+device.getSwitchTime()+".\n");
                        }
                    } else if (device instanceof SmartLamp) {
                        SmartLamp lamp = (SmartLamp) device;
                        fileWriter.write("Smart Lamp "+lamp.getName()+" is "+(lamp.isOn() ? "on" : "off")+" and its kelvin value is "+
                                lamp.getKelvin()+"K with "+lamp.getBrightness()+
                                "% brightness, and its time to switch its status is "+device.getSwitchTime()+".\n");

                    } else if (device instanceof SmartCamera) {
                        SmartCamera camera = (SmartCamera) device;
                        fileWriter.write("Smart Camera "+camera.getName()+" is "+ (camera.isOn() ? "on" : "off")
                                +" and used "+String.format("%.2f",camera.getStorageUsed())+
                                " MB of storage so far (excluding current status), and its time to switch its status is "+device.getSwitchTime()+".\n");
                    }
                }
            }
        }
        catch (Exception e){
            ;
        }




    }
}

