package bg.sofia.uni.fmi.mjt.shopping;

public class ItemNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException() {
        super("Item does not exist");
    }

}
