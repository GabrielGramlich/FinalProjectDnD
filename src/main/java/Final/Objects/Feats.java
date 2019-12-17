package Final.Objects;

import java.util.List;

/*******************************************************
 * This object stores the feature data for a character *
 *******************************************************/

public class Feats {
    private List<String> backgroundFeatNames;
    private List<String> backgroundFeatDescriptions;
    private List<String> classFeatNames;
    private List<String> classFeatDescriptions;
    private List<String> raceFeatNames;
    private List<String> raceFeatDescriptions;

    public Feats(List<String> backgroundFeatNames, List<String> backgroundFeatDescriptions, List<String> classFeatNames, List<String> classFeatDescriptions, List<String> raceFeatNames, List<String> raceFeatDescriptions) {
        this.backgroundFeatNames = backgroundFeatNames;
        this.backgroundFeatDescriptions = backgroundFeatDescriptions;
        this.classFeatNames = classFeatNames;
        this.classFeatDescriptions = classFeatDescriptions;
        this.raceFeatNames = raceFeatNames;
        this.raceFeatDescriptions = raceFeatDescriptions;
    }

    public List<String> getBackgroundFeatNames() { return backgroundFeatNames; }
    public List<String> getBackgroundFeatDescriptions() { return backgroundFeatDescriptions; }
    public List<String> getClassFeatNames() { return classFeatNames; }
    public List<String> getClassFeatDescriptions() { return classFeatDescriptions; }
    public List<String> getRaceFeatNames() { return raceFeatNames; }
    public List<String> getRaceFeatDescriptions() { return raceFeatDescriptions; }

    public void setBackgroundFeatNames(List<String> backgroundFeatNames) { this.backgroundFeatNames = backgroundFeatNames; }
    public void setBackgroundFeatDescriptions(List<String> backgroundFeatDescriptions) { this.backgroundFeatDescriptions = backgroundFeatDescriptions; }
    public void setClassFeatNames(List<String> classFeatNames) { this.classFeatNames = classFeatNames; }
    public void setClassFeatDescriptions(List<String> classFeatDescriptions) { this.classFeatDescriptions = classFeatDescriptions; }
    public void setRaceFeatNames(List<String> raceFeatNames) { this.raceFeatNames = raceFeatNames; }
    public void setRaceFeatDescriptions(List<String> raceFeatDescriptions) { this.raceFeatDescriptions = raceFeatDescriptions; }
}
