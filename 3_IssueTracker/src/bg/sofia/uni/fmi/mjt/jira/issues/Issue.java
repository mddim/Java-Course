package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.IDGenerator;
import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

import java.time.LocalDateTime;

public abstract class Issue {
    private String issueID;
    private IssuePriority priority;
    private Component component;
    private String description;
    private IssueResolution resolution;
    private IssueStatus status;
    private LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;
    String[] actionLog;
    int totalNumberOfActions;

    public Issue(IssuePriority priority, Component component, String description) {
        actionLog = new String[20];
        this.priority = priority;
        this.component = component;
        this.description = description;
        int totalNumberOfActions = 0;
        this.issueID = component.getShortName() + "-" + IDGenerator.generate();
        setCreatedOn(LocalDateTime.now());
        setLastModifiedOn(LocalDateTime.now());
        this.status = IssueStatus.OPEN;
        this.resolution = IssueResolution.UNRESOLVED;
    }

    public String getIssueID() { return issueID; }
    public String getDescription() { return description; }
    public IssuePriority getPriority() { return priority; }
    public IssueResolution getResolution() { return resolution; }
    public IssueStatus getStatus() { return status; }
    public Component getComponent() { return component; }
    public LocalDateTime getCreatedOn() { return createdOn; }
    public LocalDateTime getLastModifiedOn() { return lastModifiedOn; }
    public String[] getActionLog() { return actionLog; }
    public int getTotalNumberOfActions() { return totalNumberOfActions; }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
        setLastModifiedOn(LocalDateTime.now());
    }

    public void setComponent(Component component) {
        this.component = component;
        setLastModifiedOn(LocalDateTime.now());
    }

    public void setResolution(IssueResolution resolution) {
        this.resolution = resolution;
        setLastModifiedOn(LocalDateTime.now());
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
        setLastModifiedOn(LocalDateTime.now());
    }

    void setLastModifiedOn(LocalDateTime lastModifiedAt) {
        this.lastModifiedOn = lastModifiedAt;
    }

    private void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void addAction(WorkAction action, String description) throws ArrayIndexOutOfBoundsException {
        if(totalNumberOfActions >= 20) {
            throw new ArrayIndexOutOfBoundsException("The limit is 20 actions.");
        }
        else {
            actionLog[totalNumberOfActions] = (action+": ").toLowerCase() + description;
            totalNumberOfActions++;
        }
        setLastModifiedOn(LocalDateTime.now());
    }

    public boolean isInActionLog(String action) {
        if (totalNumberOfActions == 0 || action == null)
            return false;

        int i = 0;
        while(i < totalNumberOfActions) {
            String separatedAction = actionLog[i].split(":")[0];
            if (action.equalsIgnoreCase(separatedAction)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public abstract void resolve(IssueResolution resolution);

}
