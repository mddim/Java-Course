package bg.sofia.uni.fmi.mjt.christmas;

import java.util.Random;

public class Kid implements Runnable {

    private static final int MAX_WAITING_TIME = 5000;
    private static Random random = new Random();
    private Workshop workshop;
    private Gift gift;

    public Kid(Workshop workshop) {
        this.workshop = workshop;
    }

    public void setGift() {
        this.gift = Gift.getGift();
        workshop.postWish(this.gift);
    }

    @Override
    public void run() {
        try {
            System.out.println("Kid is choosing it's present.");
            Thread.sleep(random.nextInt(MAX_WAITING_TIME));
        } catch (InterruptedException e) {
            System.out.println("Process of choosing a gift was interrupted.");
        }
        setGift();
        System.out.println("Kid choose a " + gift.getType() + " for Christmas.");
    }
}