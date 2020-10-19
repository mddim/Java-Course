package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class StandardCard extends Card {
    public StandardCard() { }

    public StandardCard(String name) {
        super(name);
    }

    @Override
    public boolean executePayment(double cost) {
        if(this.amount - cost < 0 || cost < 0) {
            return false;
        } else {
            this.amount -= cost;
            return true;
        }
    }
}
