package Final.Objects;

import java.util.List;

/****************************************************
 * This object stores the character's language data *
 ****************************************************/

public class Languages {
    private List<String> backgroundLanguages;
    private List<String> raceLanguages;
    private List<String> chosenLanguages;
    private int languagesLeft = 0;
    private List<String> allLanguages;

    public Languages(List<String> backgroundLanguages, List<String> raceLanguages, List<String> allLanguages) {
        this.backgroundLanguages = backgroundLanguages;
        this.raceLanguages = raceLanguages;
        this.allLanguages = allLanguages;

        // Database stores choosable language as "Any" so these compile total user can pick.
        for (String language : backgroundLanguages) {
            if (language.equals("Any")) {
                languagesLeft++;
            }
        }

        for (String language : raceLanguages) {
            if (language.equals("Any")) {
                languagesLeft++;
            }
        }
    }

    public List<String> getBackgroundLanguages() { return backgroundLanguages; }
    public List<String> getRaceLanguages() { return raceLanguages; }
    public List<String> getChosenLanguages() { return chosenLanguages; }
    public int getLanguagesLeft() { return languagesLeft; }
    public List<String> getAllLanguages() { return allLanguages; }

    public void setBackgroundLanguages(List<String> backgroundLanguages) { this.backgroundLanguages = backgroundLanguages; }
    public void setRaceLanguages(List<String> raceLanguages) { this.raceLanguages = raceLanguages; }
    public void setChosenLanguages(List<String> chosenLanguages) { this.chosenLanguages = chosenLanguages; }
    public void setLanguagesLeft(int languagesLeft) { this.languagesLeft = languagesLeft; }
    public void setAllLanguages(List<String> allLanguages) { this.allLanguages = allLanguages; }
}
