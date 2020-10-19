package bg.sofia.uni.fmi.mjt.shopping.portal.comparator;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.util.Comparator;

public class OfferDateAscendingOrderComparator implements Comparator<Offer> {

    public int compare(Offer firstOffer, Offer secondOffer) {
        return firstOffer.getDate().compareTo(secondOffer.getDate());
    }
}
