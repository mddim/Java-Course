package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PriceStatistic {

    private LocalDate date;
    private List<Offer> offers;

    public PriceStatistic(LocalDate date, LinkedList<Offer> offers) {
        this.date = date;
        this.offers = offers;
    }

    /**
     * Returns the date for which the statistic is
     * collected.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the lowest total price from the offers
     * for this product for the specific date.
     */
    public double getLowestPrice() {
        double lowestPrice = Double.MAX_VALUE;
        for (Offer offerIter : offers) {
            if (offerIter.getTotalPrice() < lowestPrice) {
                lowestPrice = offerIter.getTotalPrice();
            }
        }
        return lowestPrice;
    }

    /**
     * Return the average total price from the offers
     * for this product for the specific date.
     */
    public double getAveragePrice() {
        double totalPrice = 0.0;
        for (Offer offerIter : offers) {
            totalPrice += offerIter.getTotalPrice();
        }
        return totalPrice / offers.size();
    }

}
