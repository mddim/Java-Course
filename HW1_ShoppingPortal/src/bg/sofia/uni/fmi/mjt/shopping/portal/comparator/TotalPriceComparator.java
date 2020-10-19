package bg.sofia.uni.fmi.mjt.shopping.portal.comparator;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.util.Comparator;

public class TotalPriceComparator implements Comparator<Offer> {

    public int compare(Offer firstOffer, Offer secondOffer) {
        return Double.compare(firstOffer.getTotalPrice(), secondOffer.getTotalPrice());
    }
}
