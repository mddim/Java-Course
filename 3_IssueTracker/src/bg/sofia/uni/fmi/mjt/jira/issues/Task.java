package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.issues.exceptions.ActionLogException;

import java.time.LocalDateTime;

public class Task extends Issue {
    public Task(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void addAction(WorkAction action, String description) throws ArrayIndexOutOfBoundsException, ActionLogException {
        if(totalNumberOfActions >= 20) {
            throw new ArrayIndexOutOfBoundsException();
        }
        else if(action.equals(WorkAction.FIX) || action.equals(WorkAction.IMPLEMENTATION) || action.equals(WorkAction.TESTS)) {
           throw new ActionLogException("Action " + action + " cannot be added to the array.");
        }
        else {
            actionLog[totalNumberOfActions] = (action+": ").toLowerCase() + description;
            totalNumberOfActions++;
        }
        setLastModifiedOn(LocalDateTime.now());
    }

    public void resolve(IssueResolution resolution) {
        setResolution(resolution);
    }
}
