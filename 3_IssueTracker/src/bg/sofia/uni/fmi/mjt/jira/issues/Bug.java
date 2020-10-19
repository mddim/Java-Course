package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.issues.exceptions.ActionLogException;

public class Bug extends Issue {

    public Bug(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) throws ActionLogException {
        if(!isInActionLog("Fix") || !isInActionLog("Tests")) {
            throw new ActionLogException("\"Fix\" and \"Tests\" should be in the action log.");
        } else {
            setResolution(resolution);
        }
    }
}