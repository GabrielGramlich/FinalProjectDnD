package Final;

import Final.Objects.*;
import Final.Objects.Character;

import javax.swing.*;
import java.util.*;

import static Final.Import.getCharacterData;

// TODO fix proficiencies
// TODO skills left drop when selected
// TODO choose languages
// TODO languages left drop when selected
// TODO figure out cause of extra choices -- blank out before moving
// TODO comment code
// TODO organize it
// TODO maybe correct unarmored defense

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
    private JButton chooseCharacterAndExportButton;
    private JTextArea featuresTextArea;

    private List<String> rolls;
    private List<Integer> oldRolls;
    private List<String> knownLanguages = new ArrayList<>();
    private int currentSheet = 0;
    private int abilityIndex;
    private boolean listenGo = true;
    private boolean passGo = true;

    private final int PROFICIENCY_MODIFIER = 2;
    private final String UNSELECT = "Unselect";
    private final int NEGATIVE = -1;

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
    };

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

        getNewCharacter();
        getNewCharacterData();
        propagateSheetData();
        startRolls();
        propagateRolls();
        addActionListeners();
    }

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

    private void propagateSheetData() {
        propagateCharacterData();
        propagateFeatData();
        propagateGearData();
        changeLanguageButton();
        propagateSaveData();
        propagateSkillData();
        propagateTools();
    }

    private void propagateCharacterData() {
        raceLabel.setText(character.getRaceName());
        classLabel.setText(character.getClassName());
        backgroundLabel.setText(character.getBackgroundName());
        propagateHitPoints();
        proficienciesLeftLabel.setText("Proficiencies Left: " + character.getSkillCount());
    }

    private void propagateHitPoints() {
        int conAbilityScore = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
        int conAbilityMod = Integer.parseInt(getAbilityScoreModifier(conAbilityScore));
        int hitPoints = character.getHitPoints() + conAbilityMod;
        maxHitPointsLabel.setText(String.valueOf(hitPoints));
    }

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

    private String addToFeatString(List<String> featNames, String title, List<String> featDescriptions, String featString) {
        if (featNames.size() > 0) {
            featString += title + "\n";
            for (int i = 0; i < featNames.size(); i++) {
                featString += featNames.get(i) + " - " + featDescriptions.get(i) + "\n";
            }
            featString += "\n";
        }

        return featString;
    }

    private void propagateGearData() {
        List<String> armorNames = gear.getArmorNames();
        List<Integer> armorBases = gear.getArmorBases();
        List<String> armorTypes = gear.getArmorTypes();
        updateArmor(armorNames, armorBases, armorTypes);
        List<String> weaponNames = gear.getWeaponNames();
        List<String> weaponDamages = gear.getWeaponDamages();
        List<Integer> weaponModifiers = gear.getWeaponModifiers();
        updateWeapons(weaponNames, weaponDamages, weaponModifiers);
    }

    private void updateArmor(List<String> names, List<Integer> bases, List<String> types) {
        armorComboBox.setEnabled(true);
        armorComboBox.removeAllItems();

        for (String armor : names) {
            armorComboBox.addItem(armor);
        }

        if (names.size() == 0) {
            armorComboBox.setEnabled(false);
        }

        int base = 0;
        for (int i = 0; i < bases.size(); i++) {
            String type = types.get(i);
            int dexScore = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
            int dexMod = Integer.parseInt(getAbilityScoreModifier(dexScore));

            switch (type) {
                case "Light":
                    base += bases.get(i);
                    base += dexMod;
                    break;
                case "Medium":
                    base += bases.get(i);
                    base += Math.min(dexMod, 2);
                    break;
                case "Heavy":
                case "Shield":
                    base += bases.get(i);
                    break;
            }
        }

        armorClassLabel.setText(String.valueOf(base));
    }

    private void updateWeapons(List<String> names, List<String> damages, List<Integer> weaponModifiers) {
        List<String> weapons = new ArrayList<>();
        int baseModifier = PROFICIENCY_MODIFIER;

        for (int i = 0; i < names.size(); i++) {
            String weapon = names.get(i) + ", To Hit: ";
            int strengthScore = Integer.parseInt(getAbilityScoreModifier(modifiers.getStrengthScore()));
            int dexScore = Integer.parseInt(getAbilityScoreModifier(modifiers.getDexterityScore()));
            int strengthMod = strengthScore + modifiers.getStrengthRaceModifier();
            int dexMod = dexScore + modifiers.getDexterityRaceModifier();
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
        String plusOrMinus = "";
        if (mod >= 0) {
            plusOrMinus = " + ";
        } else if (mod < 0) {
            plusOrMinus = " - ";
        }
        return plusOrMinus;
    }

    private void changeLanguageButton() {
        if (languages.getLanguagesLeft() == 0) {
            languagesLeftComboBox.removeAllItems();
            chooseLanguageButton.setEnabled(false);
            languagesLeftComboBox.setEnabled(false);
            propagateLanguageData(false);
        } else {
            languagesLeftComboBox.removeAllItems();
            chooseLanguageButton.setEnabled(true);
            languagesLeftComboBox.setEnabled(true);
            propagateLanguageData(true);
        }
    }

    private void propagateLanguageData(boolean go) {
        languagesLeftLabel.setText("Languages Left: " + languages.getLanguagesLeft());

        knownLanguages = new ArrayList<>();
        List<String> allLanguages = languages.getAllLanguages();

        addLanguages(languages.getBackgroundLanguages());
        addLanguages(languages.getRaceLanguages());
        addLanguages(languages.getChosenLanguages());

        for (String language : knownLanguages) {
            languagesKnownComboBox.addItem(language);
            allLanguages.remove(language);
        }

        if (go) {
            for (String language : allLanguages) {
                if (!language.equals("Any")) {
                    languagesLeftComboBox.addItem(language);
                }
            }
        }
    }

    private void addLanguages(List<String> languages) {
        if (languages != null) {
            for (String language : languages) {
                if (!knownLanguages.contains(language) && !language.equals("Any")) {
                    knownLanguages.add(language);
                }
            }
        }
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

    private void addActionListeners() {
        comboBoxActionListeners(0, strengthComboBox);
        comboBoxActionListeners(1, dexterityComboBox);
        comboBoxActionListeners(2, constitutionComboBox);
        comboBoxActionListeners(3, intelligenceComboBox);
        comboBoxActionListeners(4, wisdomComboBox);
        comboBoxActionListeners(5, charismaComboBox);

        saveCheckBoxActionListeners(0, strengthSaveCheckBox);
        saveCheckBoxActionListeners(1, dexteritySaveCheckBox);
        saveCheckBoxActionListeners(2, constitutionSaveCheckBox);
        saveCheckBoxActionListeners(3, intelligenceSaveCheckBox);
        saveCheckBoxActionListeners(4, wisdomSaveCheckBox);
        saveCheckBoxActionListeners(5, charismaSaveCheckBox);

        skillCheckBoxActionListeners(athleticsStrCheckBox, strengthModifierLabel, athleticsLabel);
        skillCheckBoxActionListeners(acrobaticsDexCheckBox, dexterityModifierLabel, acrobaticsLabel);
        skillCheckBoxActionListeners(sleightOfHandDexCheckBox, dexterityModifierLabel, sleightOfHandLabel);
        skillCheckBoxActionListeners(stealthDexCheckBox, dexterityModifierLabel, stealthLabel);
        skillCheckBoxActionListeners(arcanaIntCheckBox, intelligenceModifierLabel, arcanaLabel);
        skillCheckBoxActionListeners(historyIntCheckBox, intelligenceModifierLabel, historyLabel);
        skillCheckBoxActionListeners(investigationIntCheckBox, intelligenceModifierLabel, investigationLabel);
        skillCheckBoxActionListeners(natureIntCheckBox, intelligenceModifierLabel, natureLabel);
        skillCheckBoxActionListeners(religionIntCheckBox, intelligenceModifierLabel, religionLabel);
        skillCheckBoxActionListeners(animalHandlingWisCheckBox, wisdomModifierLabel, animalHandlingLabel);
        skillCheckBoxActionListeners(insightWisCheckBox, wisdomModifierLabel, insightLabel);
        skillCheckBoxActionListeners(medicineWisCheckBox, wisdomModifierLabel, medicineLabel);
        skillCheckBoxActionListeners(perceptionWisCheckBox, wisdomModifierLabel, perceptionLabel);
        skillCheckBoxActionListeners(survivalWisCheckBox, wisdomModifierLabel, survivalLabel);
        skillCheckBoxActionListeners(deceptionChaCheckBox, charismaModifierLabel, deceptionLabel);
        skillCheckBoxActionListeners(intimidationChaCheckBox, charismaModifierLabel, intimidationLabel);
        skillCheckBoxActionListeners(performanceChaCheckBox, charismaModifierLabel, performanceLabel);
        skillCheckBoxActionListeners(persuasionChaCheckBox, charismaModifierLabel, persuasionLabel);

        perceptionWisCheckBox.addActionListener(event -> {
            skillCheckBoxAction(wisdomModifierLabel, perceptionWisCheckBox, perceptionLabel);
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
                case 0:
                    modifiers.setStrengthIndex(NEGATIVE);
                    modifiers.setStrengthScore(0);
                    updateModifierLabel(strengthModifierLabel);
                    break;
                case 1:
                    modifiers.setDexterityIndex(NEGATIVE);
                    modifiers.setDexterityScore(0);
                    updateModifierLabel(dexterityModifierLabel);
                    break;
                case 2:
                    modifiers.setConstitutionIndex(NEGATIVE);
                    modifiers.setConstitutionScore(0);
                    updateModifierLabel(constitutionModifierLabel);
                    break;
                case 3:
                    modifiers.setIntelligenceIndex(NEGATIVE);
                    modifiers.setIntelligenceScore(0);
                    updateModifierLabel(intelligenceModifierLabel);
                    break;
                case 4:
                    modifiers.setWisdomIndex(NEGATIVE);
                    modifiers.setWisdomScore(0);
                    updateModifierLabel(wisdomModifierLabel);
                    break;
                case 5:
                    modifiers.setCharismaIndex(NEGATIVE);
                    modifiers.setCharismaScore(0);
                    updateModifierLabel(charismaModifierLabel);
                    break;
            }
        } else {
            choice = getChoice(comboBox);
            switch (abilityIndex) {
                case 0:
                    modifiers.setStrengthIndex(rolls.indexOf(choice));
                    modifiers.setStrengthScore(getChoiceMod(choice));
                    updateModifierLabel(strengthModifierLabel);
                    break;
                case 1:
                    modifiers.setDexterityIndex(rolls.indexOf(choice));
                    modifiers.setDexterityScore(getChoiceMod(choice));
                    updateModifierLabel(dexterityModifierLabel);
                    break;
                case 2:
                    modifiers.setConstitutionIndex(rolls.indexOf(choice));
                    modifiers.setConstitutionScore(getChoiceMod(choice));
                    updateModifierLabel(constitutionModifierLabel);
                    break;
                case 3:
                    modifiers.setIntelligenceIndex(rolls.indexOf(choice));
                    modifiers.setIntelligenceScore(getChoiceMod(choice));
                    updateModifierLabel(intelligenceModifierLabel);
                    break;
                case 4:
                    modifiers.setWisdomIndex(rolls.indexOf(choice));
                    modifiers.setWisdomScore(getChoiceMod(choice));
                    updateModifierLabel(wisdomModifierLabel);
                    break;
                case 5:
                    modifiers.setCharismaIndex(rolls.indexOf(choice));
                    modifiers.setCharismaScore(getChoiceMod(choice));
                    updateModifierLabel(charismaModifierLabel);
                    break;
            }
        }
        switch (abilityIndex) {
            case 0:
                propagateGearData();
                break;
            case 1:
                updateInitiative();
                propagateGearData();
                break;
            case 2:
                propagateHitPoints();
                break;
            case 4:
                updatePassive();
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
            case 0:
                abilityScore = modifiers.getStrengthScore() + modifiers.getStrengthRaceModifier();
                break;
            case 1:
                abilityScore = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
                break;
            case 2:
                abilityScore = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
                break;
            case 3:
                abilityScore = modifiers.getIntelligenceScore() + modifiers.getIntelligenceRaceModifier();
                break;
            case 4:
                abilityScore = modifiers.getWisdomScore() + modifiers.getWisdomRaceModifier();
                break;
            case 5:
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
        System.out.println(wisScore);
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
                case 0:
                    updateModifierLabel(strengthModifierLabel);
                    break;
                case 1:
                    updateModifierLabel(dexterityModifierLabel);
                    break;
                case 2:
                    updateModifierLabel(constitutionModifierLabel);
                    break;
                case 3:
                    updateModifierLabel(intelligenceModifierLabel);
                    break;
                case 4:
                    updateModifierLabel(wisdomModifierLabel);
                    break;
                case 5:
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

        resetModifier(athleticsStrCheckBox);
        resetModifier(acrobaticsDexCheckBox);
        resetModifier(sleightOfHandDexCheckBox);
        resetModifier(stealthDexCheckBox);
        resetModifier(arcanaIntCheckBox);
        resetModifier(historyIntCheckBox);
        resetModifier(investigationIntCheckBox);
        resetModifier(natureIntCheckBox);
        resetModifier(religionIntCheckBox);
        resetModifier(animalHandlingWisCheckBox);
        resetModifier(insightWisCheckBox);
        resetModifier(medicineWisCheckBox);
        resetModifier(perceptionWisCheckBox);
        resetModifier(survivalWisCheckBox);
        resetModifier(deceptionChaCheckBox);
        resetModifier(intimidationChaCheckBox);
        resetModifier(performanceChaCheckBox);
        resetModifier(persuasionChaCheckBox);
    }

    private void resetModifier(JCheckBox checkbox) {
        boolean go = true;

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
    }

    private void saveCheckBoxActionListeners(int index, JCheckBox checkBox) {
        checkBox.addActionListener(event -> {
            abilityIndex = index;
            saveCheckBoxAction(checkBox);
        });
    }

    private void saveCheckBoxAction(JCheckBox saveCheckBox) {
        if (passGo) {
            int modifier = 0;
            switch(abilityIndex) {
                case 0:
                    modifier = modifiers.getStrengthScore() + modifiers.getStrengthRaceModifier();
                    break;
                case 1:
                    modifier = modifiers.getDexterityScore() + modifiers.getDexterityRaceModifier();
                    break;
                case 2:
                    modifier = modifiers.getConstitutionScore() + modifiers.getConstitutionRaceModifier();
                    break;
                case 3:
                    modifier = modifiers.getIntelligenceScore() + modifiers.getIntelligenceRaceModifier();
                    break;
                case 4:
                    modifier = modifiers.getWisdomScore() + modifiers.getWisdomRaceModifier();
                    break;
                case 5:
                    modifier = modifiers.getCharismaScore() + modifiers.getCharismaRaceModifier();
                    break;
            }
            int abilityScoreModifier = Integer.parseInt(getAbilityScoreModifier(modifier));

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

    private void skillCheckBoxActionListeners(JCheckBox checkBox, JLabel modifierLabel, JLabel skillLabel) {
        checkBox.addActionListener(event -> {
            skillCheckBoxAction(modifierLabel, checkBox, skillLabel);
        });
    }

    private void skillCheckBoxAction(JLabel modifierLabel, JCheckBox checkBox, JLabel label) {
        if (passGo) {
            String modifier = modifierLabel.getText();
            if (modifier.equals("0")) {
                int mod = 0;
                setLabel(mod, checkBox, label);
            } else {
                String addSub = modifier.substring(0, 1);
                int mod = Integer.parseInt(modifier.substring(1));
                if (addSub.equals("-")) {
                    setLabel(-mod, checkBox, label);
                } else {
                    setLabel(mod, checkBox, label);
                }
            }
        }
    }

    private void setLabel(int mod, JCheckBox checkBox, JLabel label) {
        if (checkBox.isSelected()) {
            label.setText(String.valueOf(mod + PROFICIENCY_MODIFIER));
        } else {
            label.setText(String.valueOf(mod));
        }
    }

    private void setSheet() {
        passGo = false;
        saveSheet();
        clearSheet();
        updateSheet();
        passGo = true;
        resetModifiers();
    }

    private void saveSheet() {
        saveModifiers();
        saveSkills();

        sheet.setLanguages(languages);
        sheet.setModifiers(modifiers);
        skillStates.setSkillSelections(skillSelections);
        sheet.setSkillStates(skillStates);
    }

    private void saveModifiers() {
        modifiers.setStrengthIndex(strengthComboBox.getSelectedIndex());
        modifiers.setDexterityIndex(dexterityComboBox.getSelectedIndex());
        modifiers.setConstitutionIndex(constitutionComboBox.getSelectedIndex());
        modifiers.setIntelligenceIndex(intelligenceComboBox.getSelectedIndex());
        modifiers.setWisdomIndex(wisdomComboBox.getSelectedIndex());
        modifiers.setCharismaIndex(charismaComboBox.getSelectedIndex());
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
        athleticsStrCheckBox.setSelected(false);
        acrobaticsDexCheckBox.setSelected(false);
        sleightOfHandDexCheckBox.setSelected(false);
        stealthDexCheckBox.setSelected(false);
        arcanaIntCheckBox.setSelected(false);
        historyIntCheckBox.setSelected(false);
        investigationIntCheckBox.setSelected(false);
        natureIntCheckBox.setSelected(false);
        religionIntCheckBox.setSelected(false);
        animalHandlingWisCheckBox.setSelected(false);
        insightWisCheckBox.setSelected(false);
        medicineWisCheckBox.setSelected(false);
        perceptionWisCheckBox.setSelected(false);
        survivalWisCheckBox.setSelected(false);
        deceptionChaCheckBox.setSelected(false);
        intimidationChaCheckBox.setSelected(false);
        performanceChaCheckBox.setSelected(false);
        persuasionChaCheckBox.setSelected(false);

        athleticsLabel.setText("0");
        acrobaticsLabel.setText("0");
        sleightOfHandLabel.setText("0");
        stealthLabel.setText("0");
        arcanaLabel.setText("0");
        historyLabel.setText("0");
        investigationLabel.setText("0");
        natureLabel.setText("0");
        religionLabel.setText("0");
        animalHandlingLabel.setText("0");
        insightLabel.setText("0");
        medicineLabel.setText("0");
        perceptionLabel.setText("0");
        survivalLabel.setText("0");
        deceptionLabel.setText("0");
        intimidationLabel.setText("0");
        performanceLabel.setText("0");
        persuasionLabel.setText("0");

        passivePerceptionLabel.setText("0");

        strengthSaveCheckBox.setSelected(false);
        dexteritySaveCheckBox.setSelected(false);
        constitutionSaveCheckBox.setSelected(false);
        intelligenceSaveCheckBox.setSelected(false);
        wisdomSaveCheckBox.setSelected(false);
        charismaSaveCheckBox.setSelected(false);

        strengthSaveCheckBox.setText("0");
        dexteritySaveCheckBox.setText("0");
        constitutionSaveCheckBox.setText("0");
        intelligenceSaveCheckBox.setText("0");
        wisdomSaveCheckBox.setText("0");
        charismaSaveCheckBox.setText("0");

        strengthModifierLabel.setText("0");
        dexterityModifierLabel.setText("0");
        constitutionModifierLabel.setText("0");
        intelligenceModifierLabel.setText("0");
        wisdomModifierLabel.setText("0");
        charismaModifierLabel.setText("0");

        strengthComboBox.removeAllItems();
        dexterityComboBox.removeAllItems();
        constitutionComboBox.removeAllItems();
        intelligenceComboBox.removeAllItems();
        wisdomComboBox.removeAllItems();
        charismaComboBox.removeAllItems();

        raceLabel.setText("None");
        classLabel.setText("None");
        backgroundLabel.setText("None");
        maxHitPointsLabel.setText("0");
        armorClassLabel.setText("0");
        initiativeLabel.setText("0");
        proficienciesLeftLabel.setText("Proficiencies Left: 0");

        languagesKnownComboBox.removeAllItems();
        languagesLeftComboBox.removeAllItems();
        toolsKnownComboBox.removeAllItems();
        weaponsComboBox.removeAllItems();
        armorComboBox.removeAllItems();

        featuresTextArea.setText("This is where Racial, Class and Background feats will go.");
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
    }
}
