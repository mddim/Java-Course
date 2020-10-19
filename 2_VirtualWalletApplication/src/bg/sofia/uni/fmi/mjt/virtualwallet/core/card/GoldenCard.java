package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class GoldenCard extends Card {
    public GoldenCard() { }

    public GoldenCard(String name) {
        super(name);
    }

    @Override
    public boolean executePayment(double cost) {
        double payBack = 0.15*cost;
        double afterPayment = this.amount - cost + payBack;
        if(afterPayment < 0 || cost < 0) {
            return false;
        } else {
            this.amount = afterPayment;
            return true;
        }
    }
}
