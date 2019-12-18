package Final;

import Final.Objects.*;
import Final.Objects.Character;

import javax.swing.*;
import java.util.*;

import static Final.Import.getCharacterData;

// TODO skills unselectedable when remaining == 0
// TODO when going back to sheet to remove chosen language, figure out why it isn't showing up in all languages
// TODO comment code
// TODO organize it

/******************************************************************************
 * This class does a lot. For the sake of brevity, I'll say it deals with *****
 * the GUI components related to the player sheet; but other comments like ****
 * these explain it in better detail as it relates to their following methods *
 ******************************************************************************/

public class PlayerSheetGUI extends JFrame {
    private JPanel mainPanel;
    private JComboBox<String> strengthComboBox;
    private JComboBox<String> dexterityComboBox;
    private JComboBox<String> constitutionComboBox;
    private JComboBox<String> intelligenceComboBox;
    private JComboBox<String> wisdomComboBox;
    private JComboBox<String> charismaComboBox;
    private JCheckBox athleticsStrCheckBox;
    private JCheckBox acrobaticsDexCheckBox;
    private JCheckBox sleightOfHandDexCheckBox;
    private JCheckBox stealthDexCheckBox;
    private JCheckBox arcanaIntCheckBox;
    private JCheckBox historyIntCheckBox;
    private JCheckBox investigationIntCheckBox;
    private JCheckBox natureIntCheckBox;
    private JCheckBox religionIntCheckBox;
    private JCheckBox animalHandlingWisCheckBox;
    private JCheckBox insightWisCheckBox;
    private JCheckBox medicineWisCheckBox;
    private JCheckBox perceptionWisCheckBox;
    private JCheckBox survivalWisCheckBox;
    private JCheckBox deceptionChaCheckBox;
    private JCheckBox intimidationChaCheckBox;
    private JCheckBox performanceChaCheckBox;
    private JCheckBox persuasionChaCheckBox;
    private JCheckBox strengthSaveCheckBox;
    private JCheckBox dexteritySaveCheckBox;
    private JCheckBox constitutionSaveCheckBox;
    private JCheckBox intelligenceSaveCheckBox;
    private JCheckBox wisdomSaveCheckBox;
    private JCheckBox charismaSaveCheckBox;
    private JComboBox<String> languagesLeftComboBox;
    private JComboBox<String> languagesKnownComboBox;
    private JButton chooseLanguageButton;
    private JComboBox toolsLeftComboBox;
    private JComboBox<String> toolsKnownComboBox;
    private JComboBox<String> weaponsComboBox;
    private JComboBox<String> armorComboBox;
    private JButton lastButton;
    private JButton nextButton;
    private JTextPane featuresTextPane;
    private JLabel raceLabel;
    private JLabel classLabel;
    private JLabel backgroundLabel;
    private JLabel maxHitPointsLabel;
    private JLabel armorClassLabel;
    private JLabel initiativeLabel;
    private JLabel athleticsLabel;
    private JLabel acrobaticsLabel;
    private JLabel sleightOfHandLabel;
    private JLabel stealthLabel;
    private JLabel arcanaLabel;
    private JLabel historyLabel;
    private JLabel investigationLabel;
    private JLabel natureLabel;
    private JLabel religionLabel;
    private JLabel animalHandlingLabel;
    private JLabel insightLabel;
    private JLabel medicineLabel;
    private JLabel perceptionLabel;
    private JLabel survivalLabel;
    private JLabel deceptionLabel;
    private JLabel intimidationLabel;
    private JLabel performanceLabel;
    private JLabel persuasionLabel;
    private JLabel passivePerceptionLabel;
    private JLabel proficienciesLeftLabel;
    private JLabel strengthModifierLabel;
    private JLabel dexterityModifierLabel;
    private JLabel constitutionModifierLabel;
    private JLabel intelligenceModifierLabel;
    private JLabel wisdomModifierLabel;
    private JLabel charismaModifierLabel;
    private JLabel languagesLeftLabel;
    private JButton exportButton;
    private JTextArea featuresTextArea;

    private List<String> rolls;
    private List<Integer> oldRolls;
    private List<String> knownLanguages;
    private List<String> chosenLanguages;
    private List<String> allLanguages;
    private List<JCheckBox> skillCheckBoxes;

    private int currentSheet = 0;
    private int abilityIndex;
    private int selectedSkills = 0;
    private boolean listenGo = true;
    private boolean passGo = true;
    private boolean modGo = true;

    private final int PROFICIENCY_MODIFIER = 2;
    private final String UNSELECT = "Unselect";
    private final String REMOVE = "Remove option above";
    private final int NEGATIVE = -1;

    private final int STRENGTH = 0;
    private final int DEXTERITY = 1;
    private final int CONSTITUTION = 2;
    private final int INTELLIGENCE = 3;
    private final int WISDOM = 4;
    private final int CHARISMA = 5;

    private List<Sheet> sheets = new ArrayList<>();
    private static Sheet sheet;
    private Character character;
    private Feats feats;
    private Gear gear;
    private Languages languages;
    private Saves saves;
    private SkillSelections skillSelections;
    private SkillStates skillStates;
    private Modifiers modifiers;
    private Tools tools;

    public enum State {
        FINAL,
        OPEN,
        CLOSED
    }

    PlayerSheetGUI(List<Integer> oldRolls) {
        rolls = new ArrayList<>();
        this.oldRolls = oldRolls;

        this.setContentPane(mainPanel);
        setTitle("Player Sheet Generator");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Allows form to pop up in center of the screen.
        setSize(875, 1250);
        setVisible(true);
        lastButton.setEnabled(false);

        // The following method calls enact the bigger picture of the class.
        createSkillList();
        getNewCharacter();
        getNewCharacterData();
        propagateSheetData();
        startRolls();
        propagateRolls();
        addActionListeners();
    }

    private void createSkillList() {
        // There are a lot of check boxes in this. Some would say too many. While most of the time there are other
        //  method call attached to them, there are a few instances where all that needed is the skill checkboxes,
        //  so I threw them into a list to simply those times.
        skillCheckBoxes = new ArrayList<>();
        skillCheckBoxes.add(acrobaticsDexCheckBox);
        skillCheckBoxes.add(animalHandlingWisCheckBox);
        skillCheckBoxes.add(arcanaIntCheckBox);
        skillCheckBoxes.add(athleticsStrCheckBox);
        skillCheckBoxes.add(deceptionChaCheckBox);
        skillCheckBoxes.add(historyIntCheckBox);
        skillCheckBoxes.add(insightWisCheckBox);
        skillCheckBoxes.add(intimidationChaCheckBox);
        skillCheckBoxes.add(investigationIntCheckBox);
        skillCheckBoxes.add(medicineWisCheckBox);
        skillCheckBoxes.add(natureIntCheckBox);
        skillCheckBoxes.add(perceptionWisCheckBox);
        skillCheckBoxes.add(performanceChaCheckBox);
        skillCheckBoxes.add(persuasionChaCheckBox);
        skillCheckBoxes.add(religionIntCheckBox);
        skillCheckBoxes.add(sleightOfHandDexCheckBox);
        skillCheckBoxes.add(stealthDexCheckBox);
        skillCheckBoxes.add(survivalWisCheckBox);
    }

    /*******************************************************
     * The following adds data from database to the sheet. *
     *******************************************************/

    // This method grabs random integers with upperbounds representing the total of races, classes and backgrounds
    //  available, and grabs a character sheet from the database with the associated data.
    private void getNewCharacter() {
        Random rnd = new Random();
        int TOTAL_RACES = 5;
        int raceSelection = rnd.nextInt(TOTAL_RACES) + 1;
        int TOTAL_CLASSES = 6;
        int classSelection = rnd.nextInt(TOTAL_CLASSES) + 1;
        int TOTAL_BACKGROUNDS = 5;
        int backgroundSelection = rnd.nextInt(TOTAL_BACKGROUNDS) + 1;

        sheets.add(getCharacterData(raceSelection, classSelection, backgroundSelection));
    }

    // This class takes that sheet and grabs the objects from it with data about the character build.
    private void getNewCharacterData() {
        sheet = sheets.get(currentSheet);
        character = sheet.getCharacter();
        feats = sheet.getFeats();
        gear = sheet.getGear();
        languages = sheet.getLanguages();
        saves = sheet.getSaves();
        skillSelections = sheet.getSkillStates().getSkillSelections();
        skillStates = sheet.getSkillStates();
        modifiers = sheet.getModifiers();
        tools = sheet.getTools();
    }

    // This is another simple method calling other methods to do lots of fun.
    private void propagateSheetData() {
        propagateCharacterData();
        propagateFeatData();
        propagateGearData();
        propagateLanguageData();
        propagateSaveData();
        propagateSkillData();
        propagateTools();
    }

    // Here we're just adding some of the more basic data to the gui.
    private void propagateCharacterData() {
        raceLabel.setText(character.getRaceName());
        classLabel.setText(character.getClassName());
        backgroundLabel.setText(character.getBackgroundName());
        propagateHitPoints();
        proficienciesLeftLabel.setText("Proficiencies Left: " + character.getSkillCount());
    }

    // Hit points at first level are fixed, but with a buff from the constitution modifier. This just adds that.
    private void propagateHitPoints() {
        int conAbilityScore = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
        int conAbilityMod = Integer.parseInt(getAbilityScoreModifier(conAbilityScore));
        int hitPoints = character.getHitPoints() + conAbilityMod;
        maxHitPointsLabel.setText(String.valueOf(hitPoints));
    }

    // This grabs all the feats from the race, class and background, and sends them to a method to build a string
    //  that includes all of them, then adds it to the text area.
    private void propagateFeatData() {
        String featString = "";
        List<String> raceFeatNames = feats.getRaceFeatNames();
        List<String> raceFeatDescriptions = feats.getRaceFeatDescriptions();
        List<String> classFeatNames = feats.getClassFeatNames();
        List<String> classFeatDescriptions = feats.getClassFeatDescriptions();
        List<String> backgroundFeatNames = feats.getBackgroundFeatNames();
        List<String> backgroundFeatDescriptions = feats.getBackgroundFeatDescriptions();

        featString = addToFeatString(raceFeatNames, "Race Feats:", raceFeatDescriptions, featString);
        featString = addToFeatString(classFeatNames, "Class Feats:", classFeatDescriptions, featString);
        featString = addToFeatString(backgroundFeatNames, "Background Feats:", backgroundFeatDescriptions, featString);

        featuresTextArea.setText(featString);
    }

    // This is basically just formatting. It takes it feature sent to it, puts the name first, then the description,
    //  then adds than to a running string to be displayed to the user.
    private String addToFeatString(List<String> featNames, String title, List<String> featDescriptions, String featString) {
        if (featNames.size() > 0) {
            featString += title + "\n";
            StringBuilder featStringBuilder = new StringBuilder(featString);
            for (int i = 0; i < featNames.size(); i++) {
                featStringBuilder.append(featNames.get(i)).append(" - ").append(featDescriptions.get(i)).append("\n");
            }
            featString = featStringBuilder.toString();
            featString += "\n";
        }

        return featString;
    }

    // This is another method that grabs data, and sends it to the next method for processing. Not exciting, but makes
    //  the code more manageable. Don't judge me.
    private void propagateGearData() {
        List<String> armorNames = gear.getArmorNames();
        List<Integer> armorBases = gear.getArmorBases();
        List<String> armorTypes = gear.getArmorTypes();
        updateArmor(armorNames);
        updateArmorClass(armorBases, armorTypes);
        List<String> weaponNames = gear.getWeaponNames();
        List<String> weaponDamages = gear.getWeaponDamages();
        List<Integer> weaponModifiers = gear.getWeaponModifiers();
        updateWeapons(weaponNames, weaponDamages, weaponModifiers);
    }

    // This basically adds all armor available for the character to the combo box if there is armor, and blanks the
    //  combo box out if there's not.
    private void updateArmor(List<String> names) {
        armorComboBox.setEnabled(true);
        armorComboBox.removeAllItems();

        for (String armor : names) {
            armorComboBox.addItem(armor);
        }

        if (names.size() == 0) {
            armorComboBox.setEnabled(false);
        }
    }

    // Okay, this method is a little more fun. It updates the armor class, which is basically how hard it is to hit
    //  the character, but there's more to calculate so let me explain further...
    private void updateArmorClass(List<Integer> bases, List<String> types) {
        // All AC's deal with dex, so it grabs that modifier.
        int base = 0;
        int dexScore = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
        int dexMod = Integer.parseInt(getAbilityScoreModifier(dexScore));

        // Then monks and barbarians have a unique AC if they're unarmored, so the next two statements deal with that.
        if (character.getClassName().equals("Barbarian")) {
            // A Barbarian's unarmored defense come from constitution, so it gets the con modifier, adds it to the dex
            //  mod plus 10, and there's the AC.
            int conScore = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
            int conMod = Integer.parseInt(getAbilityScoreModifier(conScore));
            base = 10 + dexMod + conMod;

            armorClassLabel.setText(String.valueOf(base));
        } else if (character.getClassName().equals("Monk")) {
            // A Monk is the same as a Barbarian, but with Wisdom instead of con.
            int wisScore = modifiers.getWisdomScore() + modifiers.getWisdomRaceModifier();
            int wisMod = Integer.parseInt(getAbilityScoreModifier(wisScore));
            base = 10 + dexMod + wisMod;

            armorClassLabel.setText(String.valueOf(base));
        } else {
            // If the character is one of the other 10 classes, it's a bit different. There's no set AC, it depends on
            //  the armor.
            for (int i = 0; i < bases.size(); i++) {
                String type = types.get(i);

                switch (type) {
                    case "Light":
                        // If the armor is light, it's the armor's AC plus dex.
                        base += bases.get(i);
                        base += dexMod;
                        break;
                    case "Medium":
                        // If the armor is medium, it's the armor's AC plus dex to a max of 2.
                        base += bases.get(i);
                        base += Math.min(dexMod, 2);
                        break;
                    case "Heavy":
                    case "Shield":
                        // If it's heavy armor or a shield, dex doesn't matter, it's just that piece's AC.
                        base += bases.get(i);
                        break;
                }
            }

            armorClassLabel.setText(String.valueOf(base));
        }
    }

    private void updateWeapons(List<String> names, List<String> damages, List<Integer> weaponModifiers) {
        List<String> weapons = new ArrayList<>();
        int baseModifier = PROFICIENCY_MODIFIER;    // Because this program is basic, the database only includes weapons
                                                    //  the user is proficient with.

        for (int i = 0; i < names.size(); i++) {
            String weapon = names.get(i) + ", To Hit: +";
            // Some weapons deal with strength, some with dexterity, some with either, so we grab both modifiers.
            int strengthScore = modifiers.getStrengthScore() + modifiers.getStrengthRaceModifier();
            int strengthMod = Integer.parseInt(getAbilityScoreModifier(strengthScore));
            int dexScore = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
            int dexMod = Integer.parseInt(getAbilityScoreModifier(dexScore));
            String plusOrMinusStrength = plusOrMinus(strengthMod);
            String plusOrMinusDex = plusOrMinus(dexMod);

            switch (weaponModifiers.get(i)) {
                case 0:
                    baseModifier += strengthMod;
                    weapon += baseModifier + ", Damage: " + damages.get(i) + plusOrMinusStrength + strengthMod;
                    break;
                case 1:
                    baseModifier += dexMod;
                    weapon += baseModifier + ", Damage: " + damages.get(i) + plusOrMinusDex + dexMod;
                    break;
                case 2:
                    if (dexMod > strengthMod) {
                        baseModifier += dexMod;
                        weapon += baseModifier + ", Damage: " + damages.get(i) + plusOrMinusDex + dexMod;
                    } else {
                        baseModifier += strengthMod;
                        weapon += baseModifier + ", Damage: " + damages.get(i) + plusOrMinusStrength + strengthMod;
                    }
                    break;
            }
            weapons.add(weapon);
        }

        weaponsComboBox.removeAllItems();

        for (String weapon : weapons) {
            weaponsComboBox.addItem(weapon);
        }
    }

    private String plusOrMinus(int mod) {
        // Adding the appropriate sign to the modifier for the user.
        String plusOrMinus;
        if (mod > 0) {
            plusOrMinus = " + ";
        } else if (mod < 0) {
            plusOrMinus = " - ";
        } else {
            plusOrMinus = " ";
        }
        return plusOrMinus;
    }

    private void propagateLanguageData() {
        // Initiating each List.
        knownLanguages = new ArrayList<>();
        chosenLanguages = new ArrayList<>();
        allLanguages = new ArrayList<>();

        languagesLeftLabel.setText("Languages Left: " + languages.getLanguagesLeft());

        // Adding chosen languages to the list if they exist.
        allLanguages = languages.getAllLanguages();
        if (languages.getChosenLanguages() != null && !languages.getChosenLanguages().isEmpty()) {
            chosenLanguages = languages.getChosenLanguages();
        }
        addLanguagesKnown(languages.getBackgroundLanguages());
        addLanguagesKnown(languages.getRaceLanguages());

        // Adding chosen languages to the list of known languages if they were previously selected.
        List<String> tempLanguages = new ArrayList<>();
        if (chosenLanguages != null && !chosenLanguages.isEmpty()) {
            for (String language : chosenLanguages) {
                tempLanguages.add(language);
                tempLanguages.add(REMOVE);
            }
        }
        addLanguagesKnown(tempLanguages);

        // Adding every known to the appropriate list.
        for (String language : knownLanguages) {
            languagesKnownComboBox.addItem(language);
        }

        updateLanguageButton();
    }

    private void addLanguagesKnown(List<String> languages) {
        // Adding languages known to the list and the ability to remove them.
        if (languages != null) {
            for (String language : languages) {
                if (language.equals(REMOVE)) {
                    knownLanguages.add(language);
                } else if (!knownLanguages.contains(language) && !language.equals("Any")) {
                    knownLanguages.add(language);
                }
            }
        }
    }

    private void updateLanguageButton() {
        // Changing the buttons if languages to selects changes.
        if (languages.getLanguagesLeft() == 0) {
            languagesLeftComboBox.removeAllItems();
            languagesLeftComboBox.setEnabled(false);
            chooseLanguageButton.setEnabled(false);
        } else {
            languagesLeftComboBox.removeAllItems();
            languagesLeftComboBox.setEnabled(true);
            chooseLanguageButton.setEnabled(true);
            addLanguagesLeft();
        }
    }

    private void addLanguagesLeft() {
        for (String language : allLanguages) {
            if (!language.equals("Any")) {
                if (validChosen(language)){
                    languagesLeftComboBox.addItem(language);
                }
            }
        }
    }

    private boolean validChosen(String language) {
        if (chosenLanguages == null) {
            return true;
        } else return !chosenLanguages.contains(language);
    }

    private void propagateSaveData() {
        if (saves.isStrengthSave()) {
            updateSave(getSaveMod(modifiers.getStrengthScore()), strengthSaveCheckBox);
        }
        if (saves.isDexteritySave()) {
            updateSave(getSaveMod(modifiers.getDexterityScore()), dexteritySaveCheckBox);
        }
        if (saves.isConstitutionSave()) {
            updateSave(getSaveMod(modifiers.getConstitutionScore()), constitutionSaveCheckBox);
        }
        if (saves.isIntelligenceSave()) {
            updateSave(getSaveMod(modifiers.getIntelligenceScore()), intelligenceSaveCheckBox);
        }
        if (saves.isWisdomSave()) {
            updateSave(getSaveMod(modifiers.getWisdomScore()), wisdomSaveCheckBox);
        }
        if (saves.isCharismaSave()) {
            updateSave(getSaveMod(modifiers.getCharismaScore()), charismaSaveCheckBox);
        }
    }

    private int getSaveMod(int abilityModifier) {
        return PROFICIENCY_MODIFIER + abilityModifier;
    }

    private void updateSave(int saveMod, JCheckBox saveCheckBox) {
        saveCheckBox.setSelected(true);
        saveCheckBox.setText(getAbilityScoreModifier(saveMod));
    }

    private void propagateSkillData() {
        setSkill(skillStates.getAcrobatics(), skillSelections.isAcrobatics(), acrobaticsDexCheckBox, acrobaticsLabel, modifiers.getDexterityScore());
        setSkill(skillStates.getAnimalHandling(), skillSelections.isAnimalHandling(), animalHandlingWisCheckBox, animalHandlingLabel, modifiers.getWisdomScore());
        setSkill(skillStates.getArcana(), skillSelections.isArcana(), arcanaIntCheckBox, arcanaLabel, modifiers.getIntelligenceScore());
        setSkill(skillStates.getAthletics(), skillSelections.isAthletics(), athleticsStrCheckBox, athleticsLabel, modifiers.getStrengthScore());
        setSkill(skillStates.getDeception(), skillSelections.isDeception(), deceptionChaCheckBox, deceptionLabel, modifiers.getCharismaScore());
        setSkill(skillStates.getHistory(), skillSelections.isHistory(), historyIntCheckBox, historyLabel, modifiers.getIntelligenceScore());
        setSkill(skillStates.getInsight(), skillSelections.isInsight(), insightWisCheckBox, insightLabel, modifiers.getWisdomScore());
        setSkill(skillStates.getIntimidation(), skillSelections.isIntimidation(), intimidationChaCheckBox, intimidationLabel, modifiers.getCharismaScore());
        setSkill(skillStates.getInvestigation(), skillSelections.isInvestigation(), investigationIntCheckBox, investigationLabel, modifiers.getIntelligenceScore());
        setSkill(skillStates.getMedicine(), skillSelections.isMedicine(), medicineWisCheckBox, medicineLabel, modifiers.getWisdomScore());
        setSkill(skillStates.getNature(), skillSelections.isNature(), natureIntCheckBox, natureLabel, modifiers.getIntelligenceScore());
        setSkill(skillStates.getPerception(), skillSelections.isPerception(), perceptionWisCheckBox, perceptionLabel, modifiers.getWisdomScore());
        setSkill(skillStates.getPerformance(), skillSelections.isPerformance(), performanceChaCheckBox, performanceLabel, modifiers.getCharismaScore());
        setSkill(skillStates.getPersuasion(), skillSelections.isPersuasion(), persuasionChaCheckBox, persuasionLabel, modifiers.getCharismaScore());
        setSkill(skillStates.getReligion(), skillSelections.isReligion(), religionIntCheckBox, religionLabel, modifiers.getIntelligenceScore());
        setSkill(skillStates.getSleightOfHand(), skillSelections.isSleightOfHand(), sleightOfHandDexCheckBox, sleightOfHandLabel, modifiers.getDexterityScore());
        setSkill(skillStates.getStealth(), skillSelections.isStealth(), stealthDexCheckBox, stealthLabel, modifiers.getDexterityScore());
        setSkill(skillStates.getSurvival(), skillSelections.isSurvival(), survivalWisCheckBox, survivalLabel, modifiers.getWisdomScore());
    }

    private void setSkill(PlayerSheetGUI.State state, boolean selected, JCheckBox checkBox, JLabel label, int modifier) {
        if (state.equals(PlayerSheetGUI.State.FINAL)) {
            checkBox.setSelected(true);
            checkBox.setEnabled(false);

            updateSkillModifier(checkBox, label, modifier);
        } else if (state.equals(PlayerSheetGUI.State.CLOSED)) {
            if (selected) {
                checkBox.setSelected(true);
            }
            checkBox.setEnabled(false);
        } else if (state.equals(PlayerSheetGUI.State.OPEN)) {
            if (selected) {
                checkBox.setSelected(true);
            }
            checkBox.setEnabled(true);
        }
    }

    private void updateSkillModifier(JCheckBox checkBox, JLabel label, int modifier) {
        if (checkBox.isSelected()) {
            label.setText(String.valueOf(modifier + PROFICIENCY_MODIFIER));
        } else {
            label.setText(String.valueOf(modifier));
        }
    }

    private void propagateTools() {
        toolsKnownComboBox.setEnabled(true);
        List<String> toolProficiencies = tools.getToolProficiencies();

        for (String proficiency : toolProficiencies) {
            toolsKnownComboBox.addItem(proficiency);
        }

        if (toolProficiencies.size() == 0) {
            toolsKnownComboBox.setEnabled(false);
        }
    }

    /**********************************************
     * The following deals with the user's rolls. *
     **********************************************/

    private void startRolls() {
        oldRolls.sort(Integer::compareTo);
        rolls.clear();

        for (int i = 0; i < 6; i++) {
            String addition = (i + 1) + ". " + oldRolls.get(i);
            rolls.add(addition);
        }
    }

    private void propagateRolls() {
        listenGo = false;
        propagateRoll(strengthComboBox, modifiers.getStrengthIndex(), modifiers.getStrengthScore(), modifiers.getStrengthRaceModifier());
        propagateRoll(dexterityComboBox, modifiers.getDexterityIndex(), modifiers.getDexterityScore(), modifiers.getDexterityRaceModifier());
        propagateRoll(constitutionComboBox, modifiers.getConstitutionIndex(), modifiers.getConstitutionScore(), modifiers.getConstitutionRaceModifier());
        propagateRoll(intelligenceComboBox, modifiers.getIntelligenceIndex(), modifiers.getIntelligenceScore(), modifiers.getIntelligenceRaceModifier());
        propagateRoll(wisdomComboBox, modifiers.getWisdomIndex(), modifiers.getWisdomScore(), modifiers.getWisdomRaceModifier());
        propagateRoll(charismaComboBox, modifiers.getCharismaIndex(), modifiers.getCharismaScore(), modifiers.getCharismaRaceModifier());
        listenGo = true;
    }

    private void propagateRoll(JComboBox<String> comboBox, int index, int modifier, int raceModifier) {
        comboBox.removeAllItems();
        if (index == NEGATIVE) {
            for (String roll : rolls) {
                if (notSelected(roll)) {
                    String rollString = roll;
                    if (raceModifier > 0) {
                        rollString += " + " + raceModifier;
                    }
                    comboBox.addItem(rollString);
                }
            }
            comboBox.setSelectedIndex(index);
        } else {
            comboBox.addItem(String.valueOf(modifier + raceModifier));
            comboBox.addItem(UNSELECT);
            comboBox.setSelectedIndex(0);
        }
    }

    private boolean notSelected(String testRoll) {
        List<String> selectedRolls = new ArrayList<>();

        if (modifiers.getStrengthIndex() != NEGATIVE) {
            selectedRolls.add(rolls.get(modifiers.getStrengthIndex()));
        }
        if (modifiers.getDexterityIndex() != NEGATIVE) {
            selectedRolls.add(rolls.get(modifiers.getDexterityIndex()));
        }
        if (modifiers.getConstitutionIndex() != NEGATIVE) {
            selectedRolls.add(rolls.get(modifiers.getConstitutionIndex()));
        }
        if (modifiers.getIntelligenceIndex() != NEGATIVE) {
            selectedRolls.add(rolls.get(modifiers.getIntelligenceIndex()));
        }
        if (modifiers.getWisdomIndex() != NEGATIVE) {
            selectedRolls.add(rolls.get(modifiers.getWisdomIndex()));
        }
        if (modifiers.getCharismaIndex() != NEGATIVE) {
            selectedRolls.add(rolls.get(modifiers.getCharismaIndex()));
        }

        return !selectedRolls.contains(testRoll);
    }

    /*********************************************************
     * The following updates information via event handlers. *
     *********************************************************/

    private void addActionListeners() {
        comboBoxActionListeners(STRENGTH, strengthComboBox);
        comboBoxActionListeners(DEXTERITY, dexterityComboBox);
        comboBoxActionListeners(CONSTITUTION, constitutionComboBox);
        comboBoxActionListeners(INTELLIGENCE, intelligenceComboBox);
        comboBoxActionListeners(WISDOM, wisdomComboBox);
        comboBoxActionListeners(CHARISMA, charismaComboBox);

        saveCheckBoxActionListeners(STRENGTH, strengthSaveCheckBox);
        saveCheckBoxActionListeners(DEXTERITY, dexteritySaveCheckBox);
        saveCheckBoxActionListeners(CONSTITUTION, constitutionSaveCheckBox);
        saveCheckBoxActionListeners(INTELLIGENCE, intelligenceSaveCheckBox);
        saveCheckBoxActionListeners(WISDOM, wisdomSaveCheckBox);
        saveCheckBoxActionListeners(CHARISMA, charismaSaveCheckBox);

        skillCheckBoxActionListeners(STRENGTH, athleticsStrCheckBox, athleticsLabel);
        skillCheckBoxActionListeners(DEXTERITY, acrobaticsDexCheckBox, acrobaticsLabel);
        skillCheckBoxActionListeners(DEXTERITY, sleightOfHandDexCheckBox, sleightOfHandLabel);
        skillCheckBoxActionListeners(DEXTERITY, stealthDexCheckBox, stealthLabel);
        skillCheckBoxActionListeners(INTELLIGENCE, arcanaIntCheckBox, arcanaLabel);
        skillCheckBoxActionListeners(INTELLIGENCE, historyIntCheckBox, historyLabel);
        skillCheckBoxActionListeners(INTELLIGENCE, investigationIntCheckBox, investigationLabel);
        skillCheckBoxActionListeners(INTELLIGENCE, natureIntCheckBox, natureLabel);
        skillCheckBoxActionListeners(INTELLIGENCE, religionIntCheckBox, religionLabel);
        skillCheckBoxActionListeners(WISDOM, animalHandlingWisCheckBox, animalHandlingLabel);
        skillCheckBoxActionListeners(WISDOM, insightWisCheckBox, insightLabel);
        skillCheckBoxActionListeners(WISDOM, medicineWisCheckBox, medicineLabel);
        skillCheckBoxActionListeners(WISDOM, survivalWisCheckBox, survivalLabel);
        skillCheckBoxActionListeners(CHARISMA, deceptionChaCheckBox, deceptionLabel);
        skillCheckBoxActionListeners(CHARISMA, intimidationChaCheckBox, intimidationLabel);
        skillCheckBoxActionListeners(CHARISMA, performanceChaCheckBox, performanceLabel);
        skillCheckBoxActionListeners(CHARISMA, persuasionChaCheckBox, persuasionLabel);

        perceptionWisCheckBox.addActionListener(event -> {
            abilityIndex = WISDOM;
            skillCheckBoxAction(perceptionWisCheckBox, perceptionLabel);
            updatePassive();
        });

        nextButton.addActionListener(event -> {
            currentSheet++;
            lastButton.setEnabled(true);
            if (currentSheet == 2) {
                nextButton.setEnabled(false);
            }

            setSheet();
        });

        lastButton.addActionListener(event -> {
            currentSheet--;
            nextButton.setEnabled(true);
            if (currentSheet == 0) {
                lastButton.setEnabled(false);
            }

            setSheet();
        });

        chooseLanguageButton.addActionListener(event -> {
            String choice = languagesLeftComboBox.getItemAt(languagesLeftComboBox.getSelectedIndex());
            languagesKnownComboBox.addItem(choice);
            languagesKnownComboBox.addItem(REMOVE);
            chosenLanguages.add(choice);
            languagesLeftComboBox.removeItem(choice);
            languages.setLanguagesLeft(languages.getLanguagesLeft() - 1);
            languagesLeftLabel.setText("Languages Left: " + languages.getLanguagesLeft());
            updateLanguageButton();
        });

        languagesKnownComboBox.addActionListener(event -> {
            if (languagesKnownComboBox.getSelectedIndex() != -1) {
                if (languagesKnownComboBox.getItemAt(languagesKnownComboBox.getSelectedIndex()).equals(REMOVE)) {
                    int index = languagesKnownComboBox.getSelectedIndex();
                    String choice = languagesKnownComboBox.getItemAt(index - 1);
                    chosenLanguages.remove(choice);
                    languagesKnownComboBox.removeItemAt(index);
                    languagesKnownComboBox.removeItem(choice);
                    languages.setLanguagesLeft(languages.getLanguagesLeft() + 1);
                    languagesLeftLabel.setText("Languages Left: " + languages.getLanguagesLeft());
                    updateLanguageButton();
                }
            }
        });

        exportButton.addActionListener(event -> {
            saveSheet();
            Export.export(sheet);
            this.dispose();
        });
    }

    private void comboBoxActionListeners(int index, JComboBox<String> comboBox) {
        comboBox.addActionListener(event -> {
            this.abilityIndex = index;
            comboBoxAction(comboBox);
        });
    }

    private void comboBoxAction(JComboBox<String> comboBox) {
        if (passGo && listenGo) {
            listenGo = false;

            updateModifier(comboBox);
            propagateRolls();
            listenGo = false;
            resetModifiers();

            listenGo = true;
        }
    }

    private void updateModifier(JComboBox<String> comboBox) {
        String choice = comboBox.getItemAt(comboBox.getSelectedIndex());
        if (choice.equals(UNSELECT)) {
            switch (abilityIndex) {
                case STRENGTH:
                    modifiers.setStrengthIndex(NEGATIVE);
                    modifiers.setStrengthScore(0);
                    updateModifierLabel(strengthModifierLabel);
                    break;
                case DEXTERITY:
                    modifiers.setDexterityIndex(NEGATIVE);
                    modifiers.setDexterityScore(0);
                    updateModifierLabel(dexterityModifierLabel);
                    break;
                case CONSTITUTION:
                    modifiers.setConstitutionIndex(NEGATIVE);
                    modifiers.setConstitutionScore(0);
                    updateModifierLabel(constitutionModifierLabel);
                    break;
                case INTELLIGENCE:
                    modifiers.setIntelligenceIndex(NEGATIVE);
                    modifiers.setIntelligenceScore(0);
                    updateModifierLabel(intelligenceModifierLabel);
                    break;
                case WISDOM:
                    modifiers.setWisdomIndex(NEGATIVE);
                    modifiers.setWisdomScore(0);
                    updateModifierLabel(wisdomModifierLabel);
                    break;
                case CHARISMA:
                    modifiers.setCharismaIndex(NEGATIVE);
                    modifiers.setCharismaScore(0);
                    updateModifierLabel(charismaModifierLabel);
                    break;
            }
        } else {
            choice = getChoice(comboBox);
            switch (abilityIndex) {
                case STRENGTH:
                    modifiers.setStrengthIndex(rolls.indexOf(choice));
                    modifiers.setStrengthScore(getChoiceMod(choice));
                    updateModifierLabel(strengthModifierLabel);
                    break;
                case DEXTERITY:
                    modifiers.setDexterityIndex(rolls.indexOf(choice));
                    modifiers.setDexterityScore(getChoiceMod(choice));
                    updateModifierLabel(dexterityModifierLabel);
                    break;
                case CONSTITUTION:
                    modifiers.setConstitutionIndex(rolls.indexOf(choice));
                    modifiers.setConstitutionScore(getChoiceMod(choice));
                    updateModifierLabel(constitutionModifierLabel);
                    break;
                case INTELLIGENCE:
                    modifiers.setIntelligenceIndex(rolls.indexOf(choice));
                    modifiers.setIntelligenceScore(getChoiceMod(choice));
                    updateModifierLabel(intelligenceModifierLabel);
                    break;
                case WISDOM:
                    modifiers.setWisdomIndex(rolls.indexOf(choice));
                    modifiers.setWisdomScore(getChoiceMod(choice));
                    updateModifierLabel(wisdomModifierLabel);
                    break;
                case CHARISMA:
                    modifiers.setCharismaIndex(rolls.indexOf(choice));
                    modifiers.setCharismaScore(getChoiceMod(choice));
                    updateModifierLabel(charismaModifierLabel);
                    break;
            }
        }

        List<Integer> bases = gear.getArmorBases();
        List<String> types = gear.getArmorTypes();
        switch (abilityIndex) {
            case STRENGTH:
                propagateGearData();
                break;
            case DEXTERITY:
                updateInitiative();
                propagateGearData();
                updateArmorClass(bases, types);
                break;
            case CONSTITUTION:
                propagateHitPoints();
                updateArmorClass(bases, types);
                break;
            case WISDOM:
                updatePassive();
                updateArmorClass(bases, types);
                break;
        }
    }

    private String getChoice(JComboBox<String> comboBox) {
        String choice = comboBox.getItemAt(comboBox.getSelectedIndex());
        int last = choice.lastIndexOf(" ");
        int first = choice.indexOf(" ");
        if (first != last) {
            choice = choice.substring(0, last - 2);
        }

        return choice;
    }

    private int getChoiceMod(String choice) {
        choice = choice.substring(choice.indexOf(" ") + 1);
        return Integer.parseInt(choice);
    }

    private void updateModifierLabel(JLabel label) {
        int abilityScore = 0;
        switch (abilityIndex) {
            case STRENGTH:
                abilityScore = modifiers.getStrengthScore() + modifiers.getStrengthRaceModifier();
                break;
            case DEXTERITY:
                abilityScore = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
                break;
            case CONSTITUTION:
                abilityScore = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
                break;
            case INTELLIGENCE:
                abilityScore = modifiers.getIntelligenceScore() + modifiers.getIntelligenceRaceModifier();
                break;
            case WISDOM:
                abilityScore = modifiers.getWisdomScore() + modifiers.getWisdomRaceModifier();
                break;
            case CHARISMA:
                abilityScore = modifiers.getCharismaScore() + modifiers.getCharismaRaceModifier();
                break;
        }

        String abilityScoreModifier = getAbilityScoreModifier(abilityScore);

        label.setText(abilityScoreModifier);
    }

    private void updateInitiative() {
        int dexScore = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
        initiativeLabel.setText(getAbilityScoreModifier(dexScore));
    }

    private void updatePassive() {
        int wisScore = modifiers.getWisdomScore() + modifiers.getWisdomRaceModifier();
        int wisMod = Integer.parseInt(getAbilityScoreModifier(wisScore));
        if (perceptionWisCheckBox.isSelected()) {
            wisMod += PROFICIENCY_MODIFIER;
        }
        int passive = 10 + wisMod;
        passivePerceptionLabel.setText(String.valueOf(passive));
    }

    private String getAbilityScoreModifier(int abilityScore) {
        int modifier = 0;
        String plusOrMinus = "";

        switch (abilityScore) {
            case 3:
                plusOrMinus = "-";
                modifier = 4;
                break;
            case 4:
            case 5:
                plusOrMinus = "-";
                modifier = 3;
                break;
            case 6:
            case 7:
                plusOrMinus = "-";
                modifier = 2;
                break;
            case 8:
            case 9:
                plusOrMinus = "-";
                modifier = 1;
                break;
            case 0:
            case 10:
            case 11:
                break;
            case 12:
            case 13:
                plusOrMinus = "+";
                modifier = 1;
                break;
            case 14:
            case 15:
                plusOrMinus = "+";
                modifier = 2;
                break;
            case 16:
            case 17:
                plusOrMinus = "+";
                modifier = 3;
                break;
            case 18:
            case 19:
                plusOrMinus = "+";
                modifier = 4;
                break;
            case 20:
                plusOrMinus = "+";
                modifier = 5;
                break;
        }
        return plusOrMinus + modifier;
    }

    private void resetModifiers() {
        for (int i = 0; i < 6; i++) {
            abilityIndex = i;
            switch (abilityIndex) {
                case STRENGTH:
                    updateModifierLabel(strengthModifierLabel);
                    break;
                case DEXTERITY:
                    updateModifierLabel(dexterityModifierLabel);
                    break;
                case CONSTITUTION:
                    updateModifierLabel(constitutionModifierLabel);
                    break;
                case INTELLIGENCE:
                    updateModifierLabel(intelligenceModifierLabel);
                    break;
                case WISDOM:
                    updateModifierLabel(wisdomModifierLabel);
                    break;
                case CHARISMA:
                    updateModifierLabel(charismaModifierLabel);
                    break;
            }
        }

        resetModifier(strengthSaveCheckBox);
        resetModifier(dexteritySaveCheckBox);
        resetModifier(constitutionSaveCheckBox);
        resetModifier(intelligenceSaveCheckBox);
        resetModifier(wisdomSaveCheckBox);
        resetModifier(charismaSaveCheckBox);

        for (JCheckBox checkBox : skillCheckBoxes) {
            resetModifier(checkBox);
        }
    }

    private void resetModifier(JCheckBox checkbox) {
        // Commenting on this one is necessary though. Basically, some checkboxes were available for the user to select
        //  some weren't. I wanted to keep those the same while updating their values. I can explain this better when
        //  I'm sober, but whatever. It works. Also, the doClick() is used because apparently setting them as selected
        //  doesnt mean anything to java, which is weird, so you actually have to click them, but that means you have
        //  to show all those clicks, which is why they're set to invisible. Anyways... yeah.
        boolean go = true;

        modGo = false;
        setCursor(WAIT_CURSOR);
        checkbox.setVisible(false);
        if (checkbox.isEnabled()) {
            go = false;
        }
        if (go) {
            checkbox.setEnabled(true);
        }
        checkbox.doClick();
        checkbox.doClick();
        if (go) {
            checkbox.setEnabled(false);
        }
        checkbox.setVisible(true);
        setCursor(DEFAULT_CURSOR);
        modGo = true;
    }

    private void saveCheckBoxActionListeners(int index, JCheckBox checkBox) {
        checkBox.addActionListener(event -> {
            abilityIndex = index;
            saveCheckBoxAction(checkBox);
        });
    }

    private void saveCheckBoxAction(JCheckBox saveCheckBox) {
        if (passGo) {
            int abilityScoreModifier = getModifier();

            if (saveCheckBox.isSelected()) {
                abilityScoreModifier += PROFICIENCY_MODIFIER;
                setCheckBox(abilityScoreModifier, saveCheckBox);
            } else {
                setCheckBox(abilityScoreModifier, saveCheckBox);
            }
        }
    }

    private void setCheckBox(int mod, JCheckBox checkBox) {
        if (mod > 0) {
            checkBox.setText("+" + mod);
        } else {
            checkBox.setText(String.valueOf(mod));
        }
    }

    private int getModifier() {
        int modifier = 0;
        switch(abilityIndex) {
            case STRENGTH:
                modifier = modifiers.getStrengthScore() + modifiers.getStrengthRaceModifier();
                break;
            case DEXTERITY:
                modifier = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
                break;
            case CONSTITUTION:
                modifier = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
                break;
            case INTELLIGENCE:
                modifier = modifiers.getIntelligenceScore() + modifiers.getIntelligenceRaceModifier();
                break;
            case WISDOM:
                modifier = modifiers.getWisdomScore() + modifiers.getWisdomRaceModifier();
                break;
            case CHARISMA:
                modifier = modifiers.getCharismaScore() + modifiers.getCharismaRaceModifier();
                break;
        }

        return Integer.parseInt(getAbilityScoreModifier(modifier));
    }

    private void skillCheckBoxActionListeners(int modifierIndex, JCheckBox checkBox, JLabel skillLabel) {
        checkBox.addActionListener(event -> {
            abilityIndex = modifierIndex;
            skillCheckBoxAction(checkBox, skillLabel);
        });
    }

    private void skillCheckBoxAction(JCheckBox checkBox, JLabel label) {
        if (passGo) {
            int abilityScoreModifier = getModifier();

            if (checkBox.isSelected()) {
                abilityScoreModifier += PROFICIENCY_MODIFIER;
                setLabel(abilityScoreModifier, label);
            } else {
                setLabel(abilityScoreModifier, label);
            }

            processChoices();
        }
    }

    private void processChoices() {
        getSkillsSelected();
        int skillsLeft = skillStates.getChoices() - selectedSkills;
        proficienciesLeftLabel.setText("Proficiencies Left: " + skillsLeft);

        if (modGo) {
            if (skillsLeft == 0) {
                JOptionPane.showMessageDialog(this, "You have chosen all skill proficiencies" +
                        " available to you.");
            } else if (skillsLeft < 0) {
                JOptionPane.showMessageDialog(this, "You have exceed the maximum number of " +
                        "proficiencies available to you. Please deselect " + (0 - skillsLeft) + " before " +
                        "continuing.");
            }
        }
//            if (skillsLeft == 0) {
//                closeSkills();
//            } else {
//                openSkills();
//            }
    }

    private void setLabel(int mod, JLabel label) {
        if (mod > 0) {
            label.setText("+" + mod);
        } else {
            label.setText(String.valueOf(mod));
        }
    }

    private void getSkillsSelected() {
        selectedSkills = 0;

        for (JCheckBox checkBox : skillCheckBoxes) {
            skillSelected(checkBox);
        }
    }

    private void skillSelected(JCheckBox checkBox) {
        if (checkBox.isEnabled() && checkBox.isSelected()) {
            selectedSkills++;
        }
    }

    /*****************************************************
     * The following deals with movement to a new sheet. *
     *****************************************************/

    private void setSheet() {
        passGo = false;
        saveSheet();
        clearSheet();
        updateSheet();
        passGo = true;
        resetModifiers();
    }

    private void saveSheet() {
        saveSkills();

        languages.setChosenLanguages(chosenLanguages);
        sheet.setLanguages(languages);
        sheet.setModifiers(modifiers);
        skillStates.setSkillSelections(skillSelections);
        sheet.setSkillStates(skillStates);
    }

    private void saveSkills() {
        if (athleticsStrCheckBox.isSelected()) {
            skillSelections.setAthletics(true);
        }
        if (acrobaticsDexCheckBox.isSelected()) {
            skillSelections.setAcrobatics(true);
        }
        if (sleightOfHandDexCheckBox.isSelected()) {
            skillSelections.setSleightOfHand(true);
        }
        if (stealthDexCheckBox.isSelected()) {
            skillSelections.setStealth(true);
        }
        if (arcanaIntCheckBox.isSelected()) {
            skillSelections.setArcana(true);
        }
        if (historyIntCheckBox.isSelected()) {
            skillSelections.setHistory(true);
        }
        if (investigationIntCheckBox.isSelected()) {
            skillSelections.setInvestigation(true);
        }
        if (natureIntCheckBox.isSelected()) {
            skillSelections.setNature(true);
        }
        if (religionIntCheckBox.isSelected()) {
            skillSelections.setReligion(true);
        }
        if (animalHandlingWisCheckBox.isSelected()) {
            skillSelections.setAnimalHandling(true);
        }
        if (insightWisCheckBox.isSelected()) {
            skillSelections.setInsight(true);
        }
        if (medicineWisCheckBox.isSelected()) {
            skillSelections.setMedicine(true);
        }
        if (perceptionWisCheckBox.isSelected()) {
            skillSelections.setPerception(true);
        }
        if (survivalWisCheckBox.isSelected()) {
            skillSelections.setSurvival(true);
        }
        if (deceptionChaCheckBox.isSelected()) {
            skillSelections.setDeception(true);
        }
        if (intimidationChaCheckBox.isSelected()) {
            skillSelections.setIntimidation(true);
        }
        if (performanceChaCheckBox.isSelected()) {
            skillSelections.setPerformance(true);
        }
        if (persuasionChaCheckBox.isSelected()) {
            skillSelections.setPersuasion(true);
        }
    }

    private void clearSheet() {
        for (JCheckBox checkBox : skillCheckBoxes) {
            checkBox.setSelected(false);
        }

        strengthSaveCheckBox.setSelected(false);
        dexteritySaveCheckBox.setSelected(false);
        constitutionSaveCheckBox.setSelected(false);
        intelligenceSaveCheckBox.setSelected(false);
        wisdomSaveCheckBox.setSelected(false);
        charismaSaveCheckBox.setSelected(false);

        strengthComboBox.removeAllItems();
        dexterityComboBox.removeAllItems();
        constitutionComboBox.removeAllItems();
        intelligenceComboBox.removeAllItems();
        wisdomComboBox.removeAllItems();
        charismaComboBox.removeAllItems();

        initiativeLabel.setText("0");

        languagesKnownComboBox.removeAllItems();
        languagesLeftComboBox.removeAllItems();
        toolsKnownComboBox.removeAllItems();
        weaponsComboBox.removeAllItems();
        armorComboBox.removeAllItems();
    }

    private void updateSheet() {
        if (sheets.size() == currentSheet) {
            getNewCharacter();
        }
        getNewCharacterData();
        propagateSheetData();
        listenGo = false;
        propagateRolls();
        listenGo = true;
        updateLanguageButton();
    }
}
