package bg.sofia.uni.fmi.mjt.shopping.portal.comparator;

import bg.sofia.uni.fmi.mjt.shopping.portal.PriceStatistic;

import java.util.Comparator;

public class StatisticDateDescendingOrderComparator implements Comparator<PriceStatistic> {

    public int compare(PriceStatistic firstStatistic, PriceStatistic secondStatistic) {
        return secondStatistic.getDate().compareTo(firstStatistic.getDate());
    }
}
