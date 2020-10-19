package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.GoldenCard;

public class Main {
    public static void main(String[] args) {
        Card c1 = new GoldenCard("abc");
        VirtualWallet VW = new VirtualWallet();
        VW.registerCard(c1);
        VW.feed(c1, 500);

        Card cc = VW.getCardByName("abc");
        System.out.println(cc.getName());

        System.out.println(VW.registerCard(cc));

        Card c = new GoldenCard();
        Card ccc = null;
        System.out.println(VW.registerCard(null));
    }
}
