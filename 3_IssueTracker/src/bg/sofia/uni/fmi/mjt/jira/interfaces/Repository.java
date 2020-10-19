package bg.sofia.uni.fmi.mjt.jira.interfaces;

import bg.sofia.uni.fmi.mjt.jira.issues.Issue;
import bg.sofia.uni.fmi.mjt.jira.issues.exceptions.AlreadyInArrayException;

public interface Repository {
    public void addIssue(Issue issue) throws ArrayIndexOutOfBoundsException, AlreadyInArrayException;
}
