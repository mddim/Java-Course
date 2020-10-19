package bg.sofia.uni.fmi.mjt.christmas;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class Workshop {

    private final static int MAX_ELVES = 100;
    private static final int NUMBER_OF_ELVES = 20;
    private boolean isChristmasTime = false;
    private Queue<Gift> giftQueue = new PriorityBlockingQueue<>();
    private int wishCount = 0;
    private Elf[] elves;

    public Workshop() {
        startElves();
    }

    private void startElves() {
        this.elves = new Elf[NUMBER_OF_ELVES];
        for (int i = 0; i < NUMBER_OF_ELVES; i++) {
            elves[i] = new Elf(i, this);
            elves[i].start();
        }
    }

    /**
     * Adds a gift to the elves' backlog.
     **/
    public synchronized void postWish(Gift gift) {
        giftQueue.add(gift);
        wishCount++;

        this.notify();
    }

    /**
     * @return an array of the elves working in Santa's workshop.
     **/
    public Elf[] getElves() {
        return elves;
    }

    /**
     * @return the next gift from the elves' backlog that has to be manufactured.
     **/
    public synchronized Gift nextGift() {
        while (!isChristmasTime && giftQueue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Waiting for gift was interrupted.");
            }
        }

        return isChristmasTime ? null : giftQueue.poll();
    }

    /**
     * @return the total number of wishes sent to Santa's workshop by the kids.
     **/
    public int getWishCount() {
        return wishCount;
    }

    public Queue<Gift> getGiftQueue() {
        return giftQueue;
    }

    /**
     * sets isChristmasTime to true and notifies all of the threads
     */
    public synchronized void setChristmasTime() {
        this.isChristmasTime = true;
        this.notifyAll();
    }
}
