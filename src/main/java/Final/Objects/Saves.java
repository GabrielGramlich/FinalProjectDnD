package Final.Objects;

import java.util.List;

/********************************************************
 * This object stores the character's saving throw data *
 ********************************************************/

public class Saves {
    private boolean strengthSave = false;
    private boolean dexteritySave = false;
    private boolean constitutionSave = false;
    private boolean intelligenceSave = false;
    private boolean wisdomSave = false;
    private boolean charismaSave = false;

    public Saves(List<String> classSaves) {
        // Looping through each save, and adding selection to its associated ability.
        for (String save : classSaves) {
            switch (save) {
                case "Strength":
                    strengthSave = true;
                    break;
                case "Dexterity":
                    dexteritySave = true;
                    break;
                case "Constitution":
                    constitutionSave = true;
                    break;
                case "Intelligence":
                    intelligenceSave = true;
                    break;
                case "Wisdom":
                    wisdomSave = true;
                    break;
                case "Charisma":
                    charismaSave = true;
                    break;
            }
        }
    }

    public boolean isStrengthSave() { return strengthSave; }
    public boolean isDexteritySave() { return dexteritySave; }
    public boolean isConstitutionSave() { return constitutionSave; }
    public boolean isIntelligenceSave() { return intelligenceSave; }
    public boolean isWisdomSave() { return wisdomSave; }
    public boolean isCharismaSave() { return charismaSave; }

    public void setStrengthSave(boolean strengthSave) { this.strengthSave = strengthSave; }
    public void setDexteritySave(boolean dexteritySave) { this.dexteritySave = dexteritySave; }
    public void setConstitutionSave(boolean constitutionSave) { this.constitutionSave = constitutionSave; }
    public void setIntelligenceSave(boolean intelligenceSave) { this.intelligenceSave = intelligenceSave; }
    public void setWisdomSave(boolean wisdomSave) { this.wisdomSave = wisdomSave; }
    public void setCharismaSave(boolean charismaSave) { this.charismaSave = charismaSave; }
}
