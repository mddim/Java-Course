package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class NoOfferFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoOfferFoundException(String message) {
        super(message);
    }

}