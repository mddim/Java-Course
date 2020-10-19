package bg.sofia.uni.fmi.mjc.authorship.detection;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\FMI\\JavaCourse\\" +
                "HW2_AuthorshipDetection\\src\\bg\\sofia\\uni\\fmi\\mjc\\authorship\\detection\\knownSignatures.txt");
        double[] weights = {11, 33, 50, 0.4, 4};
        AuthorshipDetector authorshipDetector = new AuthorshipDetectorImpl(inputStream, weights);

        String textToTestDataStoring = "Hi, hi, \n " +
                "Reeves. How are You? Okay; bye, bye!";
        InputStream textInputStream = new ByteArrayInputStream(textToTestDataStoring.getBytes(StandardCharsets.UTF_8));
        System.out.println(authorshipDetector.findAuthor(textInputStream));
    }
}
