package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.issues.exceptions.ActionLogException;

public class Feature extends Issue {

    public Feature(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) throws ActionLogException {
        if(!isInActionLog("Design") || !isInActionLog("Implementation") || !isInActionLog("Tests")) {
            throw new ActionLogException("\"Design\", \"Implementation\" and \"Tests\" should be in the action log.");
        } else {
            setResolution(resolution);
        }
    }
}
