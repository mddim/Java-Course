package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.comparator.OfferDateAscendingOrderComparator;
import bg.sofia.uni.fmi.mjt.shopping.portal.comparator.StatisticDateDescendingOrderComparator;
import bg.sofia.uni.fmi.mjt.shopping.portal.comparator.TotalPriceComparator;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.*;

public class ShoppingDirectoryImpl implements ShoppingDirectory {

    private final static int THIRTY_DAYS = 30;

    private Map<String, LinkedList<Offer>> products = new HashMap<>();

    private boolean isProductInProductsMap(String productName) {

        return products.get(productName) != null;
    }

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Passed argument (productName) is null.");
        }
        String name = productName.toLowerCase();
        if (!isProductInProductsMap(name)) {
            throw new ProductNotFoundException("There is no product with name " + productName);
        }
        LinkedList<Offer> offersList = products.get(name);
        LinkedList<Offer> offersInTheLast30Days = new LinkedList<>();
        LocalDate today = LocalDate.now();
        for (Offer offerIter : offersList) {
            if (offerIter.getDate().compareTo(today) <= 0 &&
                    offerIter.getDate().compareTo(today.plusDays(-THIRTY_DAYS)) > 0) {
                offersInTheLast30Days.add(offerIter);
            }
        }
        offersInTheLast30Days.sort(new TotalPriceComparator());

        return offersInTheLast30Days;
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Passed argument (productName) is null.");
        }
        String name = productName.toLowerCase();
        if (findAllOffers(name).isEmpty()) {
            throw new NoOfferFoundException("No offer submitted in the last 30 days " +
                    "for the product with given name: " + productName);
        }
        if (!isProductInProductsMap(name)) {
            throw new ProductNotFoundException("There is no product with name " + productName);
        }

        return findAllOffers(name).iterator().next();
    }

    private HashMap<LocalDate, LinkedList<Offer>> map(LinkedList<Offer> offersForProduct) {
        HashMap<LocalDate, LinkedList<Offer>> temp = new HashMap<>();
        for (Offer offer : offersForProduct) {
            if (!temp.containsKey(offer.getDate())) {
                LinkedList<Offer> list = new LinkedList<>();
                list.add(offer);
                temp.put(offer.getDate(), list);
            } else {
                LinkedList<Offer> tempList = temp.get(offer.getDate());
                tempList.add(offer);
                temp.put(offer.getDate(), tempList);
            }
        }

        return temp;
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Passed argument (productName) is null.");
        }
        String name = productName.toLowerCase();
        if (!isProductInProductsMap(name)) {
            throw new ProductNotFoundException("There is no product with name " + productName);
        }

        LinkedList<Offer> listOfOffersForProduct = products.get(name);
        HashMap<LocalDate, LinkedList<Offer>> mappedOffersByDate = map(listOfOffersForProduct);
        LinkedList<PriceStatistic> productStatistics = new LinkedList<>();

        for (LocalDate key : mappedOffersByDate.keySet()) {
            productStatistics.add(new PriceStatistic(key, mappedOffersByDate.get(key)));
        }
        productStatistics.sort(new StatisticDateDescendingOrderComparator());

        return productStatistics;
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        if (offer == null) {
            throw new IllegalArgumentException("Passed argument (offer) is null.");
        }
        //if a list for current key exists
        LinkedList<Offer> offersList = products.get(offer.getProductName());

        if (offersList != null) {
            for (Offer offerIter : offersList) {
                if (offer.equals(offerIter) || offer.equalsOtherTypeOffer(offerIter)) {
                    throw new OfferAlreadySubmittedException("This offer has already been submitted.");
                }
            }
        }
        //if not create one and put it in the map
        if (offersList == null) {
            offersList = new LinkedList<>();
            products.put(offer.getProductName().toLowerCase(), offersList);
        }

        offersList.add(offer);
        offersList.sort(new OfferDateAscendingOrderComparator());
    }
}
