package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class RegularOffer implements Offer {

    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;

    public RegularOffer(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
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
        return this.price + this.shippingPrice;
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
    public boolean equalsOtherTypeOffer(Offer premiumOffer) {
        return this.productName.equalsIgnoreCase(premiumOffer.getProductName()) && this.date.equals(premiumOffer.getDate()) &&
                Double.compare(this.getTotalPrice(), premiumOffer.getTotalPrice()) == 0;
    }

}
