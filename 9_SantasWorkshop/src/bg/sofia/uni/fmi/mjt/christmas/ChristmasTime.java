package bg.sofia.uni.fmi.mjt.christmas;

public class ChristmasTime {

    private Workshop workshop;
    private int numberOfKids;
    private int christmasTime;

    public ChristmasTime(Workshop workshop, int numberOfKids, int christmasTime) {
        this.workshop = workshop;
        this.numberOfKids = numberOfKids;
        this.christmasTime = christmasTime;
    }

    public static void main(String[] args) {
        int numberOfKids = 10;
        int christTime = 4000;

        ChristmasTime christmasTime = new ChristmasTime(new Workshop(), numberOfKids, christTime);
        christmasTime.doTheWork();
    }

    public void doTheWork() {
        Thread[] kids = new Thread[numberOfKids];

        for (int i = 0; i < kids.length; i++) {
            kids[i] = new Thread(new Kid(workshop));
            kids[i].start();
        }

        try {
            Thread.sleep(christmasTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (workshop) {
            workshop.setChristmasTime();
        }

        for (int i = 0; i < kids.length; i++) {
            try {
                kids[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
