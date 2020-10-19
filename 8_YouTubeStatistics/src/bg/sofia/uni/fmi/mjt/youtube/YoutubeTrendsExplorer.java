package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class YoutubeTrendsExplorer {

    LinkedList<TrendingVideo> trendingVideos = new LinkedList<>();

    private final static int TOP_VIDEOS_BY_VIEWS = 3;
    private final static int VIEWS_LIMIT = 100_000;

    public YoutubeTrendsExplorer(InputStream dataInput) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(dataInput);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                TrendingVideo trendingVideo = TrendingVideo.createTrendingVideo(line);
                trendingVideos.add(trendingVideo);
            }
        } catch (Exception ex) {
            System.out.println("Can't open file!");
            ex.getStackTrace();
        }
    }

    public Collection<TrendingVideo> getTrendingVideos() {
        return this.trendingVideos;
    }

    public String findIdOfLeastLikedVideo() {
        Optional<String> idOfLeastLikedVideo = trendingVideos.stream()
                .sorted((a, b) -> (int)(a.getLikes() - b.getLikes()))
                .map(TrendingVideo::getId)
                .findFirst();
        return idOfLeastLikedVideo.orElse(null);
    }

    public String findIdOfMostLikedLeastDislikedVideo() {
        Optional<String> idOfMostLikedLeastDislikedVideo = trendingVideos.stream()
                .sorted((a, b) -> (int)((b.getLikes() - b.getDislikes()) - (a.getLikes() - a.getDislikes())))
                .map(TrendingVideo::getId)
                .findFirst();
        return idOfMostLikedLeastDislikedVideo.orElse(null);
    }


    public List<String> findDistinctTitlesOfTop3VideosByViews() {
        return trendingVideos.stream()
                .sorted((a, b) -> (int)(b.getViews() - a.getViews()))
                .map(TrendingVideo::getTitle)
                .distinct()
                .limit(TOP_VIDEOS_BY_VIEWS)
                .collect(Collectors.toList());
    }

    public String findIdOfMostTaggedVideo() {
        Optional<String> idOfMostTaggedVideo = trendingVideos.stream()
                .sorted((a, b) -> (b.getTags().size() - a.getTags().size()))
                .map(TrendingVideo::getId)
                .findFirst();
        return idOfMostTaggedVideo.orElse(null);
    }

    public String findTitleOfFirstVideoTrendingBefore100KViews() {
        Optional<String> titleOfFirstVideoTrendingBefore100KViews = trendingVideos.stream()
                .filter(a -> a.getViews() < VIEWS_LIMIT)
                .sorted(Comparator.comparing(TrendingVideo::getPublishDate))
                .map(TrendingVideo::getTitle)
                .findFirst();
        return titleOfFirstVideoTrendingBefore100KViews.orElse(null);
    }

    public int countFrequencies(TrendingVideo trendingVideo)
    {
        Map<String, Integer> hashMap = new HashMap<>();

        for (TrendingVideo i : trendingVideos) {
            Integer j = hashMap.get(i.getTitle());
            hashMap.put(i.getTitle(), (j == null) ? 1 : j + 1);
        }

        return hashMap.get(trendingVideo.getTitle());
    }

    public String findIdOfMostTrendingVideo() {
        Optional<String> idOfMostTrendingVideo = trendingVideos.stream()
                .sorted((a, b) -> countFrequencies(b) - countFrequencies(a))
                .map(TrendingVideo::getId)
                .findFirst();
        return idOfMostTrendingVideo.orElse(null);
    }

}
