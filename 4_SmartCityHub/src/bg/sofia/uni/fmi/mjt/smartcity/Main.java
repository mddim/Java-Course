package bg.sofia.uni.fmi.mjt.smartcity;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartCamera;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartTrafficLight;
import bg.sofia.uni.fmi.mjt.smartcity.hub.SmartCityHub;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        SmartDevice sc = new SmartCamera("iaaa", 2.5, time);
        System.out.println(sc.getId());

        LocalDateTime time2 = LocalDateTime.now();
        SmartDevice sc2 = new SmartTrafficLight("sas", 2.5, time);
        System.out.println(sc2.getId());

        LocalDateTime time3 = LocalDateTime.now();
        SmartDevice sc3 = new SmartTrafficLight("skjjas", 2.5, time);
        System.out.println(sc3.getId());

        SmartCityHub hub = new SmartCityHub();
        hub.register(sc);
        hub.register(sc2);
        hub.getTopNDevicesByPowerConsumption(5);
        //hub.getDeviceById(sc2.getId());
    }
}
