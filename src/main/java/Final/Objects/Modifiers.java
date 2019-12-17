package Final.Objects;

import java.util.List;

/**********************************************************
 * This object stores the character's ability score data **
 * Index is of combo box selection ************************
 * Score is the roll selected for the ability *************
 * Race modifier is a potential bonus from generated race *
 **********************************************************/

public class Modifiers {
    private int strengthIndex = -1;
    private int strengthScore = 0;
    private int strengthRaceModifier = 0;
    private int dexterityIndex = -1;
    private int dexterityScore = 0;
    private int dexterityRaceModifier = 0;
    private int constitutionIndex = -1;
    private int constitutionScore = 0;
    private int constitutionRaceModifier = 0;
    private int intelligenceIndex = -1;
    private int intelligenceScore = 0;
    private int intelligenceRaceModifier = 0;
    private int wisdomIndex = -1;
    private int wisdomScore = 0;
    private int wisdomRaceModifier = 0;
    private int charismaIndex = -1;
    private int charismaScore = 0;
    private int charismaRaceModifier = 0;

    public Modifiers(List<String> raceAbilities, List<Integer> raceModifiers) {
        // Looping through the list and adding the modifier to it's associated ability.
        for (int i = 0; i < raceAbilities.size(); i++) {
            String ability = raceAbilities.get(i);
            int modifier = raceModifiers.get(i);
            switch (ability) {
                case "Strength":
                    strengthRaceModifier = modifier;
                    break;
                case "Dexterity":
                    dexterityRaceModifier = modifier;
                    break;
                case "Constitution":
                    constitutionRaceModifier = modifier;
                    break;
                case "Intelligence":
                    intelligenceRaceModifier = modifier;
                    break;
                case "Wisdom":
                    wisdomRaceModifier = modifier;
                    break;
                case "Charisma":
                    charismaRaceModifier = modifier;
                    break;
            }
        }
    }

    public int getStrengthIndex() { return strengthIndex; }
    public int getStrengthScore() { return strengthScore; }
    public int getStrengthRaceModifier() { return strengthRaceModifier; }
    public int getDexterityIndex() { return dexterityIndex; }
    public int getDexterityScore() { return dexterityScore; }
    public int getDexterityRaceModifier() { return dexterityRaceModifier; }
    public int getConstitutionIndex() { return constitutionIndex; }
    public int getConstitutionScore() { return constitutionScore; }
    public int getConstitutionRaceModifier() { return constitutionRaceModifier; }
    public int getIntelligenceIndex() { return intelligenceIndex; }
    public int getIntelligenceScore() { return intelligenceScore; }
    public int getIntelligenceRaceModifier() { return intelligenceRaceModifier; }
    public int getWisdomIndex() { return wisdomIndex; }
    public int getWisdomScore() { return wisdomScore; }
    public int getWisdomRaceModifier() { return wisdomRaceModifier; }
    public int getCharismaIndex() { return charismaIndex; }
    public int getCharismaScore() { return charismaScore; }
    public int getCharismaRaceModifier() { return charismaRaceModifier; }

    public void setStrengthIndex(int strengthIndex) { this.strengthIndex = strengthIndex; }
    public void setStrengthScore(int strengthScore) { this.strengthScore = strengthScore; }
    public void setStrengthRaceModifier(int strengthRaceModifier) { this.strengthRaceModifier = strengthRaceModifier; }
    public void setDexterityIndex(int dexterityIndex) { this.dexterityIndex = dexterityIndex; }
    public void setDexterityScore(int dexterityScore) { this.dexterityScore = dexterityScore; }
    public void setDexterityRaceModifier(int dexterityRaceModifier) { this.dexterityRaceModifier = dexterityRaceModifier; }
    public void setConstitutionIndex(int constitutionIndex) { this.constitutionIndex = constitutionIndex; }
    public void setConstitutionScore(int constitutionScore) { this.constitutionScore = constitutionScore; }
    public void setConstitutionRaceModifier(int constitutionRaceModifier) { this.constitutionRaceModifier = constitutionRaceModifier; }
    public void setIntelligenceIndex(int intelligenceIndex) { this.intelligenceIndex = intelligenceIndex; }
    public void setIntelligenceScore(int intelligenceScore) { this.intelligenceScore = intelligenceScore; }
    public void setIntelligenceRaceModifier(int intelligenceRaceModifier) { this.intelligenceRaceModifier = intelligenceRaceModifier; }
    public void setWisdomIndex(int wisdomIndex) { this.wisdomIndex = wisdomIndex; }
    public void setWisdomScore(int wisdomScore) { this.wisdomScore = wisdomScore; }
    public void setWisdomRaceModifier(int wisdomRaceModifier) { this.wisdomRaceModifier = wisdomRaceModifier; }
    public void setCharismaIndex(int charismaIndex) { this.charismaIndex = charismaIndex; }
    public void setCharismaScore(int charismaScore) { this.charismaScore = charismaScore; }
    public void setCharismaRaceModifier(int charismaRaceModifier) { this.charismaRaceModifier = charismaRaceModifier; }
}
