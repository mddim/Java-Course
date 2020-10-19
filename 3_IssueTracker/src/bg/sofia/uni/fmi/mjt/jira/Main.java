package bg.sofia.uni.fmi.mjt.jira;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.issues.Component;
import bg.sofia.uni.fmi.mjt.jira.issues.Issue;
import bg.sofia.uni.fmi.mjt.jira.issues.Task;

public class Main {
    public static void main(String[] args) {
        IssuePriority ip = IssuePriority.TRIVIAL;
        Component c = new Component("name", "short name");
        Issue i = new Task(ip, c, "descr");
        Jira jira = new Jira();
        jira.addIssue(i);
        jira.addIssue(i);
    }
}
