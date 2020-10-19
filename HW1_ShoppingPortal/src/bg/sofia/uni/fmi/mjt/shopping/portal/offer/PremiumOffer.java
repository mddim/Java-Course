package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class PremiumOffer implements Offer {

    private final static double DISCOUNT_HELPER = 100.00;

    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;
    private double discount;

    public PremiumOffer(String productName, LocalDate date, String description,
                        double price, double shippingPrice, double discount) {

        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
        this.discount = discount;
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public double getShippingPrice() {
        return this.shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        this.discount = Math.round(this.discount * DISCOUNT_HELPER) / DISCOUNT_HELPER;
        return (this.price + this.shippingPrice) -
                (this.price + this.shippingPrice) * (this.discount / DISCOUNT_HELPER);
    }

    public double getDiscount() {
        return this.discount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Offer)) {
            return false;
        }

        Offer other = (Offer) o;

        return this.productName.equalsIgnoreCase(other.getProductName()) && this.date.equals(other.getDate()) &&
                Double.compare(this.getTotalPrice(), other.getTotalPrice()) == 0;
    }

    @Override
    public boolean equalsOtherTypeOffer(Offer regularOffer) {
        return this.productName.equalsIgnoreCase(regularOffer.getProductName()) && this.date.equals(regularOffer.getDate()) &&
                Double.compare(this.getTotalPrice(), regularOffer.getTotalPrice()) == 0;
    }
}
