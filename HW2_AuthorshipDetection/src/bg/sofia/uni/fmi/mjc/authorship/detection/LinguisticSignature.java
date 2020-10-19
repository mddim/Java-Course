package bg.sofia.uni.fmi.mjc.authorship.detection;

import java.util.Map;

public class LinguisticSignature {

    private Map<FeatureType, Double> features;

    public LinguisticSignature(Map<FeatureType, Double> features) {
        this.features = features;
    }

    public Map<FeatureType, Double> getFeatures() {
        return features;
    }
}
