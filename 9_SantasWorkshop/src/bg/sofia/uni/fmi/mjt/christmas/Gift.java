package bg.sofia.uni.fmi.mjt.christmas;

import java.util.Random;

public enum Gift {

    /**
     * Bike object of type Bicycle and needed time to craft 50 milliseconds
     * Car object of type Car and needed time to craft 10 milliseconds
     */
    BIKE("Bicycle", 50), CAR("Car", 10),
    /**
     * Doll object of type Barbie doll and needed time to craft 6 milliseconds
     * Car object of type Puzzle and needed time to craft 15 milliseconds
     */
    DOLL("Barbie doll", 6), PUZZLE("Puzzle", 15);

    private final String type;
    private final int craftTime;

    private static Gift[] gifts = Gift.values();

    private static Random giftRand = new Random();

    private Gift(String type, int craftTime) {
        this.type = type;
        this.craftTime = craftTime;
    }

    /**
     * @return the type of the gift
     */
    public String getType() {
        return type;
    }

    /**
     * @return the craft time of the gift
     */
    public int getCraftTime() {
        return craftTime;
    }

    /**
     * @return random gift
     */
    public static Gift getGift() {
        return gifts[giftRand.nextInt(gifts.length)];
    }

}