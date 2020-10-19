package bg.sofia.uni.fmi.mjt.jira;

import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Filter;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Repository;
import bg.sofia.uni.fmi.mjt.jira.issues.Issue;
import bg.sofia.uni.fmi.mjt.jira.issues.exceptions.AlreadyInArrayException;

public class Jira implements Filter, Repository {

    private Issue[] issues;
    private int totalNumberOfIssues;

    public Jira() {
        Issue[] issues = new Issue[100];
        totalNumberOfIssues = 0;
    }

    public void addActionToIssue(Issue issue, WorkAction action, String actionDescription) throws ArrayIndexOutOfBoundsException {
        issue.addAction(action, actionDescription);
    }

    public void resolveIssue(Issue issue, IssueResolution resolution) {
        issue.resolve(resolution);
    }

    @Override
    public Issue find(String issueID) {

        if (issueID == null) {
            return null;
        }

        if(issues == null) {
            return null;
        }

        for (Issue issue : issues) {
            if ((issue.getIssueID()).equals(issueID)) {
                return issue;
            }
        }
        return null;
    }

    @Override
    public void addIssue(Issue issue) throws ArrayIndexOutOfBoundsException, AlreadyInArrayException {

        if (issues != null && issue != null) {
            boolean isInIssues = false;
            for (Issue i : issues) {
                if ((i.getIssueID()).equals(issue.getIssueID())) {
                    isInIssues = true;
                    break;
                }
            }
            if (totalNumberOfIssues >= 100) {
                throw new ArrayIndexOutOfBoundsException("The limit is 100 issues.");
            } else if (isInIssues) {
                throw new AlreadyInArrayException("Issue " + issue + " is already added to the array.");
            } else {
                issues[totalNumberOfIssues] = issue;
                totalNumberOfIssues++;
            }
        }
    }
}
