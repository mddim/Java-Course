package bg.sofia.uni.fmi.mjt.virtualwallet.core;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;

public class VirtualWallet implements VirtualWalletAPI {
    private Card[] wallet;
    int totalNumberOfCards;

    public VirtualWallet() {
        wallet = new Card[5];
        totalNumberOfCards = 0;
    }

    public boolean registerCard(Card card) {
        if(card == null) {
            return false;
        }
        if (totalNumberOfCards >= 5 || card.getName() == null || isCardRegistered(card)) {
            return false;
        } else {
            wallet[totalNumberOfCards] = card;
            totalNumberOfCards++;
            return true;
        }
    }

    private boolean isCardRegistered(Card card) {
        if (totalNumberOfCards == 0 || card == null)
            return false;

        int i = 0;
        while(i < totalNumberOfCards) {
            if (card.getName().equals(wallet[i].getName())) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean executePayment(Card card, PaymentInfo paymentInfo) {
        if(isCardRegistered(card) && card.executePayment(paymentInfo.getCost()))
            return true;
        else
            return false;
    }

    public boolean feed(Card card, double amount) {
        if(!isCardRegistered(card) || amount < 0) {
            return false;
        } else {
            card.feedCard(amount);
            return true;
        }
    }

    public Card getCardByName(String name) {
        if(name == null || totalNumberOfCards == 0) {
            return null;
        }

        int i = 0;
        while(i < totalNumberOfCards) {
            if (name.equals(wallet[i].getName())) {
                return wallet[i];
            }
            i++;
        }
        return null;
    }

    public int getTotalNumberOfCards() {
        return totalNumberOfCards;
    }
}
