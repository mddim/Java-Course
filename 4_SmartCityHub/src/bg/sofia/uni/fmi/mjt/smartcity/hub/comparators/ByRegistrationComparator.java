package bg.sofia.uni.fmi.mjt.smartcity.hub.comparators;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;

import java.util.Comparator;

public class ByRegistrationComparator implements Comparator<SmartDevice> {

    @Override
    public int compare(SmartDevice firstDevice, SmartDevice secondDevice) {
        return firstDevice.getRegistrationDateTime().compareTo(secondDevice.getRegistrationDateTime());
    }
}
