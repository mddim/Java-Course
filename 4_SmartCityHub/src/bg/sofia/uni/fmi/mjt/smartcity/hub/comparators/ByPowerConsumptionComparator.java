package bg.sofia.uni.fmi.mjt.smartcity.hub.comparators;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class ByPowerConsumptionComparator implements Comparator<SmartDevice> {

    @Override
    public int compare(SmartDevice firstDevice, SmartDevice secondDevice) {
        long activeHoursOfDevice1 = ChronoUnit.HOURS.between(firstDevice.getInstallationDateTime(), LocalDateTime.now());
        double totalPowerConsumptionOfDevice1 = activeHoursOfDevice1 * firstDevice.getPowerConsumption();

        long activeHoursOfDevice2 = ChronoUnit.HOURS.between(secondDevice.getInstallationDateTime(), LocalDateTime.now());
        double totalPowerConsumptionOfDevice2 = activeHoursOfDevice2 * secondDevice.getPowerConsumption();

        return (int) Math.ceil(totalPowerConsumptionOfDevice1 - totalPowerConsumptionOfDevice2);
    }
}
