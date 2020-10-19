public class FunnelChecker {
    public static boolean isFunnel(String str1, String str2) {
        int i = 0;
        int j = 0;
        int counter = 0;
        if (str1.length() != str2.length() + 1) {
            return false;
        }
        while(i < str1.length() & j < str2.length()) {
            if (str1.charAt(i) == str2.charAt(j)) {
                i++;
                j++;
            }
            else {
                counter++;
                i++;
            }
        }
        return counter == 1 || i < str1.length();
    }
    public static void main(String[] args) {
        System.out.println(isFunnel("alets", "lets"));
    }
}
