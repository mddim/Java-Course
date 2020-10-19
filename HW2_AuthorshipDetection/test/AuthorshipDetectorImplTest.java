import bg.sofia.uni.fmi.mjc.authorship.detection.AuthorshipDetectorImpl;
import bg.sofia.uni.fmi.mjc.authorship.detection.FeatureType;
import bg.sofia.uni.fmi.mjc.authorship.detection.LinguisticSignature;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AuthorshipDetectorImplTest {

    private static final double PRECISION = 0.0;
    private static final double COUNT_OF_ALL_WORDS = 9.0;
    private static final double COUNT_OF_ALL_SENTENCES = 3.0;
    private static final double COUNT_OF_ALL_PHRASES = 7.0;
    private static final double AVERAGE_WORD_LENGTH = 3.2222222222222223;
    private static final double TYPE_TOKEN_RATIO = 0.7777777777777778;
    private static final double HEPAX_LEGOMENA_RATIO = 0.5555555555555556;
    private static final double AVERAGE_SENTENCE_LENGTH = 3;
    private static final double AVERAGE_SENTENCE_COMPLEXITY = 2.3333333333333333;
    private static final double SIMILARITY = 12.0;
    String textToTestDataStoring = "";
    Map<String, Integer> words = new HashMap<>();
    InputStream textInputStream;
    InputStream signaturesInputStream = null;
    private final static double[] WEIGHTS = {11, 33, 50, 0.4, 4};

    AuthorshipDetectorImpl authorshipDetector;

    public AuthorshipDetectorImplTest() {
        textToTestDataStoring = "Hi, hi, \n " +
                "Reeves. How are You? Okay;;; bye, bye!";
        words.put("hi", 2);
        words.put("reeves", 1);
        words.put("how", 1);
        words.put("are", 1);
        words.put("you", 1);
        words.put("okay", 1);
        words.put("bye", 2);

        textInputStream = new ByteArrayInputStream(textToTestDataStoring.getBytes(StandardCharsets.UTF_8));
        File file = new File("./sources/knownSignatures.txt");
        try {
            signaturesInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        authorshipDetector = new AuthorshipDetectorImpl(signaturesInputStream, WEIGHTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStoreDataFromInputExceptionThrow() {
        authorshipDetector.storeDataFromInput(null);
    }

    @Test
    public void testStoreDataFromInputWordsInAMap() {
        String assertMessage = "Store all words in a map.";
        authorshipDetector.storeDataFromInput(textInputStream);

        assertEquals(assertMessage, authorshipDetector.getWords(), words);
    }

    @Test
    public void testStoreDataFromInputWordsCount() {
        authorshipDetector.storeDataFromInput(textInputStream);

        assertEquals(authorshipDetector.getCountOfAllWords(), COUNT_OF_ALL_WORDS, PRECISION);
    }

    @Test
    public void testStoreDataFromInputSentencesCount() {
        authorshipDetector.storeDataFromInput(textInputStream);

        assertEquals(authorshipDetector.getCountOfAllSentences(), COUNT_OF_ALL_SENTENCES, PRECISION);
    }

    @Test
    public void testStoreDataFromInputPhrasesCount() {
        authorshipDetector.storeDataFromInput(textInputStream);

        assertEquals(authorshipDetector.getCountOfAllPhrases(), COUNT_OF_ALL_PHRASES, PRECISION);
    }

    @Test
    public void testCalculateAverageWordLength() {
        authorshipDetector.storeDataFromInput(textInputStream);
        double expectedAverageWordLength = authorshipDetector.calculateAverageWordLength();

        assertEquals(expectedAverageWordLength, AVERAGE_WORD_LENGTH, PRECISION);
    }

    @Test
    public void testCalculateTypeTokenRatio() {
        authorshipDetector.storeDataFromInput(textInputStream);
        double expectedTypeTokenRatio = authorshipDetector.calculateTypeTokenRatio();

        assertEquals(expectedTypeTokenRatio, TYPE_TOKEN_RATIO, PRECISION);
    }

    @Test
    public void testCalculateHapaxLegomenaRatio() {
        authorshipDetector.storeDataFromInput(textInputStream);
        double expectedTypeTokenRatio = authorshipDetector.calculateHapaxLegomenaRatio();

        assertEquals(expectedTypeTokenRatio, HEPAX_LEGOMENA_RATIO, PRECISION);
    }

    @Test
    public void testCalculateAverageSentenceLength() {
        authorshipDetector.storeDataFromInput(textInputStream);
        double expectedAverageSentenceLength = authorshipDetector.calculateAverageSentenceLength();

        assertEquals(expectedAverageSentenceLength, AVERAGE_SENTENCE_LENGTH, PRECISION);
    }

    @Test
    public void testCalculateAverageSentenceComplexity() {
        authorshipDetector.storeDataFromInput(textInputStream);
        double expectedAverageSentenceComplexity = authorshipDetector.calculateAverageSentenceComplexity();

        assertEquals(expectedAverageSentenceComplexity, AVERAGE_SENTENCE_COMPLEXITY, PRECISION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureException() {
        authorshipDetector.calculateSignature(null);
    }

    @Test
    public void testCalculateSignature() {
        authorshipDetector.storeDataFromInput(textInputStream);
        Map<FeatureType, Double> signatureMap = new HashMap<>();
        signatureMap.put(FeatureType.AVERAGE_WORD_LENGTH, AVERAGE_WORD_LENGTH);
        signatureMap.put(FeatureType.TYPE_TOKEN_RATIO, TYPE_TOKEN_RATIO);
        signatureMap.put(FeatureType.HAPAX_LEGOMENA_RATIO, HEPAX_LEGOMENA_RATIO);
        signatureMap.put(FeatureType.AVERAGE_SENTENCE_LENGTH, AVERAGE_SENTENCE_LENGTH);
        signatureMap.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, AVERAGE_SENTENCE_COMPLEXITY);

        assertEquals(authorshipDetector.calculateSignature(textInputStream).getFeatures(), signatureMap);
    }

    @Test
    public void testCalculateSimilarity() {
        authorshipDetector.storeDataFromInput(textInputStream);
        LinguisticSignature[] linguisticSignatures = new LinguisticSignature[2];
        int i = 0;
        for (Map.Entry<String, LinguisticSignature> entry : authorshipDetector.getSignaturesMap().entrySet()) {
            linguisticSignatures[i] = entry.getValue();
            i++;
        }
        authorshipDetector.getSignaturesMap();

        DecimalFormat df = new DecimalFormat("#.##");
        double expectedSimilarity = Double.parseDouble(df.format(
                authorshipDetector.calculateSimilarity(linguisticSignatures[0], linguisticSignatures[1])));

        assertEquals(expectedSimilarity, SIMILARITY, PRECISION);
    }

    @Test
    public void testFindAuthor() {
        String expectedAuthor = authorshipDetector.findAuthor(textInputStream);
        LinguisticSignature[] linguisticSignatures = new LinguisticSignature[2];
        int i = 0;
        for (Map.Entry<String, LinguisticSignature> entry : authorshipDetector.getSignaturesMap().entrySet()) {
            linguisticSignatures[i] = entry.getValue();
            i++;
        }
        String correctAuthor = "";
        double similarityWithFirstAuthor = authorshipDetector.calculateSimilarity(
                authorshipDetector.calculateSignature(textInputStream), linguisticSignatures[0]);
        double similarityWithSecondAuthor = authorshipDetector.calculateSimilarity(
                authorshipDetector.calculateSignature(textInputStream), linguisticSignatures[1]);
        if (similarityWithFirstAuthor < similarityWithSecondAuthor) {
            correctAuthor = "first";
        } else {
            correctAuthor = "second";
        }

        assertEquals(expectedAuthor, correctAuthor);
    }
}