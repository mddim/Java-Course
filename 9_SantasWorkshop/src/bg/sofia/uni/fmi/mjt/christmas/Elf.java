package bg.sofia.uni.fmi.mjt.christmas;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Elf extends Thread {

    private int id;
    private Workshop workshop;
    private List<Gift> craftedGifts = new CopyOnWriteArrayList<>();


    /**
     * Elf class constructor
     *
     * @param id       id of the elf
     * @param workshop the workshop where the elf is crafting
     */
    public Elf(int id, Workshop workshop) {
        this.id = id;
        this.workshop = workshop;
    }

    /**
     * @return the id of the elf.
     */
    public int getIdOfElf() {
        return id;
    }

    /**
     * Gets a wish from the backlog and creates the wanted gift.
     **/
    public synchronized void craftGift() {
        Gift wantedGift = null;

        while ((wantedGift = workshop.nextGift()) != null) {
            this.craftedGifts.add(wantedGift);
            try {
                Thread.sleep(wantedGift.getCraftTime());
                System.out.println("The wanted gift has been created.");
            } catch (InterruptedException e) {
                System.out.println("Creating the wanted gift was interrupted.");
                e.printStackTrace();
            }
        }
        System.out.println("Elf " + id + " crafted " + getTotalGiftsCrafted() + " gifts.");
    }

    /**
     * @return the total number of gifts that the given elf has crafted.
     **/
    public int getTotalGiftsCrafted() {
        return craftedGifts.size();
    }

    @Override
    public void run() {
        craftGift();
    }
}