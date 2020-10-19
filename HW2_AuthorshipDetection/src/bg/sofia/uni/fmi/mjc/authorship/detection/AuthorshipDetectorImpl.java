package bg.sofia.uni.fmi.mjc.authorship.detection;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthorshipDetectorImpl implements AuthorshipDetector {

    public static final int COUNT_OF_FEATURES = 4;
    private Map<String, Integer> words = new HashMap<>();
    private double countOfAllWords;
    private double countOfAllSentences;
    private double countOfAllPhrases;
    private double[] weights;

    private Map<String, LinguisticSignature> signaturesMap;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {

        this.weights = weights;
        signaturesMap = new HashMap<>();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(signaturesDataset));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Map<FeatureType, Double> features = new HashMap<>();
                String[] separated = line.split(", ");
                for (int i = 0; i < COUNT_OF_FEATURES + 1; i++) {
                    features.put(FeatureType.values()[i], Double.parseDouble(separated[i + 1]));
                }
                signaturesMap.put(separated[0], new LinguisticSignature(features));
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Exception while reading input.");
            e.printStackTrace();
        }
    }

    public LinguisticSignature calculateSignature(InputStream mysteryText) {

        if (mysteryText == null) {
            throw new IllegalArgumentException("The given input stream is null!");
        }

        Map<FeatureType, Double> signatureMap = new HashMap<>();

        signatureMap.put(FeatureType.AVERAGE_WORD_LENGTH, calculateAverageWordLength());
        signatureMap.put(FeatureType.TYPE_TOKEN_RATIO, calculateTypeTokenRatio());
        signatureMap.put(FeatureType.HAPAX_LEGOMENA_RATIO, calculateHapaxLegomenaRatio());
        signatureMap.put(FeatureType.AVERAGE_SENTENCE_LENGTH, calculateAverageSentenceLength());
        signatureMap.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, calculateAverageSentenceComplexity());

        return new LinguisticSignature(signatureMap);
    }

    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {
        double sum = 0.0;
        for (int i = 0; i < COUNT_OF_FEATURES + 1; i++) {
            sum += Math.abs(firstSignature.getFeatures().get(FeatureType.values()[i]) -
                    secondSignature.getFeatures().get(FeatureType.values()[i])) * weights[i];
        }
        return sum;
    }

    public String findAuthor(InputStream mysteryText) {
        storeDataFromInput(mysteryText);
        LinguisticSignature linguisticSignature = calculateSignature(mysteryText);
        double closestSignatureSimilarity = Double.MAX_VALUE;
        String closestSignatureAuthor = "";
        for (Map.Entry<String, LinguisticSignature> entry : signaturesMap.entrySet()) {
            if (calculateSimilarity(entry.getValue(), linguisticSignature) < closestSignatureSimilarity) {
                closestSignatureSimilarity = calculateSimilarity(entry.getValue(), linguisticSignature);
                closestSignatureAuthor = entry.getKey();
            }
        }
        return closestSignatureAuthor;
    }

    /**
     * Collects all of the words from the text in a map<String, Integer>.
     * String - the word
     * Integer - number of times the word is used
     *
     * @throws IllegalArgumentException if @mysteryText is null
     */
    public void storeDataFromInput(InputStream mysteryText) {

        if (mysteryText == null) {
            throw new IllegalArgumentException("The given input stream is null!");
        }

        Scanner scanner = new Scanner(mysteryText);
        scanner.useDelimiter("[!?.]");

        while (scanner.hasNext()) {
            String currentSentence = scanner.next();
            if (!currentSentence.equals("")) {
                String[] phrases = currentSentence.split("[,:;]");
                for (String phrase : phrases) {
                    if (!phrase.equals("")) {
                        countOfAllPhrases++;
                    }
                }
                countOfAllSentences++;
                currentSentence = cleanUp(currentSentence);
                String[] wordsInSentence = currentSentence.split("\\s+");
                for (int i = 0; i < wordsInSentence.length; i++) {
                    if (words.containsKey(wordsInSentence[i])) {
                        words.put(wordsInSentence[i], words.get(wordsInSentence[i]) + 1);
                        countOfAllWords++;
                    } else if (!wordsInSentence[i].equals("")) {
                        words.put(wordsInSentence[i], 1);
                        countOfAllWords++;
                    }
                }
            }
        }
    }

    /**
     * Returns the Average Word Complexity.
     */
    public double calculateAverageWordLength() {

        double allWordsLength = 0.0;

        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            allWordsLength += entry.getKey().length() * entry.getValue();
        }

        return allWordsLength / countOfAllWords;
    }

    /**
     * Returns the Type Token Ratio.
     */
    public double calculateTypeTokenRatio() {

        return words.size() / countOfAllWords;
    }

    /**
     * Returns the Hapax Legomena Ratio.
     */
    public double calculateHapaxLegomenaRatio() {

        double countOfNonDuplicatedWords = 0.0;

        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            if (entry.getValue() == 1) {
                countOfNonDuplicatedWords++;
            }
        }

        return countOfNonDuplicatedWords / countOfAllWords;
    }

    /**
     * Returns the Average Sentence Length.
     */

    public double calculateAverageSentenceLength() {
        return countOfAllWords / countOfAllSentences;
    }

    /**
     * Returns the Average Sentence Complexity.
     */
    public double calculateAverageSentenceComplexity() {
        return countOfAllPhrases / countOfAllSentences;
    }

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\)\\n\\t\\\\]+" +
                        "|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\)\\n\\t\\\\]", "");
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public double getCountOfAllWords() {
        return countOfAllWords;
    }

    public double getCountOfAllSentences() {
        return countOfAllSentences;
    }

    public double getCountOfAllPhrases() {
        return countOfAllPhrases;
    }

    public Map<String, LinguisticSignature> getSignaturesMap() {
        return signaturesMap;
    }

}
