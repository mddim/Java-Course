package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {
    private String name;
    protected double amount;

    public Card() {
        this.name = null;
        this.amount = 0.0;
    }

    public Card(String name) {
        this.name = name;
        this.amount = 0.0;
    }

    public Card(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public abstract boolean executePayment(double cost);

    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public void feedCard(double amount) {
        this.amount += amount;
    }
}
