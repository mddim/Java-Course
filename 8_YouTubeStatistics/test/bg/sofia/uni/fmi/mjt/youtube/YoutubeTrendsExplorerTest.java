package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.YoutubeTrendsExplorer;
import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class YoutubeTrendsExplorerTest {

    TrendingVideo trendingVideo1 = TrendingVideo.createTrendingVideo("xegUPsJaUKc\t17.05.12\tWE MADE A JEWELRY LINE " +
            "// Grace Helbig\t2017-12-29T18:00:05.000Z\t\"" +
            "itsgrace|\"\"funny\"\"|\"\"comedy\"\"|\"\"vlog\"\"|\"\"grace\"\"|\"\"helbig\"\"|\"\"gracehelbig" +
            "\"\"|\"\"dailygrace\"\"|\"\"daily\"\"|\"\"tutorial\"\"|\"\"diy\"\"|\"\"lifestyle\"\"|\"\"jewelry" +
            "\"\"\"\t113748\t7451\t109\t\t\t\t\t\t\t\t\t\n");

    String testLines = "first line\n" +
            "0EQuVfP-vXs\t17.01.12\tAll The Money In The World - TV Spot featuring Christopher " +
            "Plummer\t2017-11-29T07:54:36.000Z\t\"All The Money In The World|\"\"Christopher " +
            "Plummer\"\"\"\t61225\t431\t286\t\t\t\t\t\t\t\t\t\n" +
            "xegUPsJaUKc\t17.05.12\tWE MADE A JEWELRY LINE // Grace Helbig\t2017-11-29T18:00:05.000Z\t\"" +
            "itsgrace|\"\"funny\"\"|\"\"comedy\"\"|\"\"vlog\"\"|\"\"grace\"\"|\"\"helbig\"\"|\"\"gracehelbig" +
            "\"\"|\"\"dailygrace\"\"|\"\"daily\"\"|\"\"tutorial\"\"|\"\"diy\"\"|\"\"lifestyle\"\"|\"\"jewelry" +
            "\"\"\"\t113748\t7451\t109\t\t\t\t\t\t\t\t\t\n" +
            "xegUPsJaUKc\t17.05.12\tWE MADE A JEWELRY LINE // Grace Helbig\t2017-12-29T18:00:05.000Z\t\"" +
            "itsgrace|\"\"funny\"\"|\"\"comedy\"\"|\"\"vlog\"\"|\"\"grace\"\"|\"\"helbig\"\"|\"\"gracehelbig" +
            "\"\"|\"\"dailygrace\"\"|\"\"daily\"\"|\"\"tutorial\"\"|\"\"diy\"\"|\"\"lifestyle\"\"|\"\"jewelry" +
            "\"\"\"\t113748\t7451\t109\t\t\t\t\t\t\t\t\t\n" +
            "QgOXIEhHU1Y\t18.06.06\tDiplo, French Montana & Lil Pump ft. Zhavia - Welcome To The Party " +
            "(Official Video)\t2018-05-21T14:12:22.000Z\t\"Welcome to the party|\"\"Diplo\"\"|\"\"" +
            "Lil Pump\"\"|\"\"French Montana\"\"|\"\"Zhavia\"\"|\"\"Welcome to the party audio\"\"|\"\"" +
            "welcome to the party song\"\"|\"\"wttp\"\"|\"\"welcome to the party diplo\"\"|\"\"welcome to " +
            "the party lil pump\"\"|\"\"welcome to the party french montana\"\"|\"\"welcome to the party zhavia" +
            "\"\"|\"\"wttp diplo\"\"|\"\"deadpool 2 soundtrack\"\"|\"\"diplo deadpool\"\"|\"\"diplo welcome to " +
            "the party\"\"|\"\"french montana welcome to the party\"\"|\"\"new diplo music\"\"|\"\"deadpool 2 " +
            "welcome to the party\"\"|\"\"deadpool 2 motion picture soundtrack\"\"|\"\"welcome to the party " +
            "video\"\"\"\t28091569\t718959\t19804\t\t\t\t\t\t\t\t\t";

    InputStream inputStream = new ByteArrayInputStream(testLines.getBytes(StandardCharsets.UTF_8));
    YoutubeTrendsExplorer trendsExplorer = new YoutubeTrendsExplorer(inputStream);

    private final static int BIGGEST_FREQUENCY = 2;

    @Test
    public void testFindIdOfLeastLikedVideo() {

        String s = trendsExplorer.findIdOfLeastLikedVideo();

        assertEquals("0EQuVfP-vXs", s);
    }

    @Test
    public void testFindIdOfMostLikedLeastDislikedVideo() {

        String s = trendsExplorer.findIdOfMostLikedLeastDislikedVideo();

        assertEquals("QgOXIEhHU1Y", s);
    }

    @Test
    public void testFindDistinctTitlesOfTop3VideosByViews() {

        List<String> result = new LinkedList<>();
        result.add("Diplo, French Montana & Lil Pump ft. Zhavia - Welcome To The Party (Official Video)");
        result.add("WE MADE A JEWELRY LINE // Grace Helbig");
        result.add("All The Money In The World - TV Spot featuring Christopher Plummer");

        List<String> s = trendsExplorer.findDistinctTitlesOfTop3VideosByViews();

        assertEquals(result, s);
    }

    @Test
    public void testFindIdOfMostTaggedVideo() {

        String s = trendsExplorer.findIdOfMostTaggedVideo();

        assertEquals("QgOXIEhHU1Y", s);
    }

    @Test
    public void testFindTitleOfFirstVideoTrendingBefore100KViews() {

        String s = trendsExplorer.findTitleOfFirstVideoTrendingBefore100KViews();

        assertEquals("All The Money In The World - TV Spot featuring Christopher Plummer", s);
    }

    @Test
    public void testCountFrequencies() {

        int result = trendsExplorer.countFrequencies(trendingVideo1);

        assertEquals(BIGGEST_FREQUENCY, result);
    }

    @Test
    public void testFindIdOfMostTrendingVideo() {

        String s = trendsExplorer.findIdOfMostTrendingVideo();

        assertEquals("xegUPsJaUKc", s);
    }


}