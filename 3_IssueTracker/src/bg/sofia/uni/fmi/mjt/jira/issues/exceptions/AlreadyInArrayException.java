package bg.sofia.uni.fmi.mjt.jira.issues.exceptions;

public class AlreadyInArrayException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AlreadyInArrayException(String message) {
        super(message);
    }
}
