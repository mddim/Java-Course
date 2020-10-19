package bg.sofia.uni.fmi.mjt.shopping.item;

public class Chocolate implements Item {

    private String name = "";
    private String description = "";
    private double price = 0.0;

    public Chocolate(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

}
