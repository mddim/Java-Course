package bg.sofia.uni.fmi.mjt.jira.issues;

public class Component {
    private String name;
    private String shortName;

    public Component(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    boolean equals(Component other) {
        return this.name.equals(other.name) & this.shortName.equals(other.shortName);
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
