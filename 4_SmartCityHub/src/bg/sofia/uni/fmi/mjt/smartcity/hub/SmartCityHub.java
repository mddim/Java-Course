package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;
import bg.sofia.uni.fmi.mjt.smartcity.hub.comparators.ByPowerConsumptionComparator;
import bg.sofia.uni.fmi.mjt.smartcity.hub.comparators.ByRegistrationComparator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SmartCityHub {

    private Set<SmartDevice> smartDeviceSet;

    public SmartCityHub() {
        smartDeviceSet = new TreeSet<>(new ByRegistrationComparator());
    }

    public boolean isInHub(SmartDevice device) {
        for (SmartDevice smartDevice : smartDeviceSet) {
            if (device.getId().equals(smartDevice.getId())) {
                return true;
            }
        }
        return false;
    }

    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        if (device == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        if (isInHub(device)) {
            throw new DeviceAlreadyRegisteredException("Device: " + device.getId() + " is already registered.");
        }
        device.setRegistrationDateTime(LocalDateTime.now());
        smartDeviceSet.add(device);
    }

    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        if (device == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        if (!isInHub(device)) {
            throw new DeviceNotFoundException("Device: " + device.getId() + " is not found.");
        }
        smartDeviceSet.remove(device);
    }

    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        for (SmartDevice hubIterator : smartDeviceSet) {
            if (id.equals(hubIterator.getId())) {
                return hubIterator;
            }
        }
        throw new DeviceNotFoundException("Device with id: " + id + " was not found.");
    }

    public int getDeviceQuantityPerType(DeviceType type) {
        if (type == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        int numberOfDevicesWithGivenType = 0;
        for (SmartDevice hubIterator : smartDeviceSet) {
            if (type.equals(hubIterator.getType())) {
                numberOfDevicesWithGivenType++;
            }
        }
        return numberOfDevicesWithGivenType;
    }

    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument is null.");
        }

        TreeSet<SmartDevice> byPowerConsumption = new TreeSet<>(new ByPowerConsumptionComparator());
        TreeSet<String> topNDevicesByPowerConsumption = new TreeSet<>();
        byPowerConsumption.addAll(smartDeviceSet);

        for (SmartDevice hubIterator : smartDeviceSet) {
            topNDevicesByPowerConsumption.add(hubIterator.getId());
        }

        while (topNDevicesByPowerConsumption.size() > n) {
            topNDevicesByPowerConsumption.pollLast();
        }
        return topNDevicesByPowerConsumption;
    }

    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument is null.");
        }

        TreeSet<SmartDevice> firstNDevicesByRegistration = new TreeSet<>(new ByRegistrationComparator());
        Iterator<SmartDevice> hubIterator = smartDeviceSet.iterator();

        int i = 0;
        while (hubIterator.hasNext() && i < n) {
            firstNDevicesByRegistration.add(hubIterator.next());
        }
        return firstNDevicesByRegistration;
    }
}