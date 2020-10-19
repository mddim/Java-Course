public class WordAnalyzer {
    public static String getSharedLetters(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        int[] letters1 = new int[26];
        int[] letters2 = new int[26];
        for (int i = 0; i < word1.length(); i++) {
            letters1[word1.charAt(i) - 'a'] = 1;
        }
        for (int i = 0; i < word2.length(); i++) {
            letters2[word2.charAt(i) - 'a'] = 1;
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 26; i++) {
            if(letters1[i] == 1 && letters2[i] == 1) {
                result.append((char)(i + 'a'));
            }
        }
        return String.valueOf(result);
    }
    public static void main(String[] args) {
        System.out.println(getSharedLetters("Micky", "mouse"));
    }
}
