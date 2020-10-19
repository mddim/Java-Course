package bg.sofia.uni.fmi.mjt.jira.issues.exceptions;

public class ActionLogException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ActionLogException(String message) {
        super(message);
    }
}