package bg.sofia.uni.fmi.mjt.jira;

public class IDGenerator {

    private static int id;

    public static int generate() {
        return ++id;
    }
}
