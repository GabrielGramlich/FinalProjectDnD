package Final;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private JComboBox languagesLeftComboBox;
    private JComboBox languagesKnownComboBox;
    private JButton chooseLanguageButton;
    private JComboBox toolsLeftComboBox;
    private JComboBox toolsKnownComboBox;
    private JComboBox weaponsComboBox;
    private JComboBox armorComboBox;
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

    private List<String> rolls;
    private List<Integer> oldRolls;
    private Map<Integer, String> selectedRolls;
    private int currentSheet = 0;
    private String selection;
    private boolean listenGo = true;
    private boolean passGo = true;
    private int index;
    private final int proficiencyModifier = 2;
    private final String UNSELECT = "Unselect";
    private final String NEGATIVE = "-1";
    private List<Sheet> sheets = new ArrayList<>();

    public enum State {
        FINAL,
        YES,
        NO
    };

    PlayerSheetGUI(List<Integer> oldRolls) {
        rolls = new ArrayList<>();
        this.oldRolls = oldRolls;
        this.setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Allows form to pop up in center of the screen.
        setVisible(true);
        lastButton.setEnabled(false);

        getNewCharacter();
        startRolls();
        resetSelections();
        addActionListeners();
    }

    private void getNewCharacter() {
    }

    private void startRolls() {
        oldRolls.sort(Integer::compareTo);
        createRolls(oldRolls);
        initializeSelectedRolls();
        propagateRolls();
    }

    private void createRolls(List<Integer> oldRolls) {
        rolls.clear();

        for (int i = 0; i < 6; i++) {
            String addition = (i + 1) + ". " + oldRolls.get(i);
            rolls.add(addition);
        }
    }

    private void initializeSelectedRolls() {
        selectedRolls = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            selectedRolls.put(i, NEGATIVE);
        }
    }

    private void propagateRolls() {
        index = 0;
        propagateRoll(strengthComboBox, 0);
        index = 1;
        propagateRoll(dexterityComboBox, 1);
        index = 2;
        propagateRoll(constitutionComboBox, 2);
        index = 3;
        propagateRoll(intelligenceComboBox,3);
        index = 4;
        propagateRoll(wisdomComboBox,4);
        index = 5;
        propagateRoll(charismaComboBox,5);
    }

    private void propagateRoll(JComboBox<String> comboBox, int index) {
        String selection = selectedRolls.get(index);

        if (selection.equals(NEGATIVE)) {
            for (String roll : rolls) {
                if (notSelected(roll)) {
                    comboBox.addItem(roll);
                }
            }
        } else {
            comboBox.removeAllItems();
            comboBox.addItem(selection);
            comboBox.addItem(UNSELECT);
        }
    }

    private boolean notSelected(String testRoll) {
        for (String roll : selectedRolls.values()) {
            if (roll.equals(testRoll)) {
                return false;
            }
        }
        return true;
    }

    private void resetSelections() {
        for (int i = 0; i < 6; i++) {
            String selection = selectedRolls.get(i);
            switch(i) {
                case 0:
                    setSelection(selection, strengthComboBox);
                    break;
                case 1:
                    setSelection(selection, dexterityComboBox);
                    break;
                case 2:
                    setSelection(selection, constitutionComboBox);
                    break;
                case 3:
                    setSelection(selection, intelligenceComboBox);
                    break;
                case 4:
                    setSelection(selection, wisdomComboBox);
                    break;
                case 5:
                    setSelection(selection, charismaComboBox);
                    break;
            }
        }
    }

    private void setSelection(String selection, JComboBox<String> comboBox) {
        if (selection.equals(NEGATIVE)) {
            comboBox.setSelectedIndex(-1);
        } else if (selection.equals(UNSELECT)) {
            comboBox.removeAllItems();
            propagateRoll(comboBox, index);
        } else {
            comboBox.setSelectedItem(selection);
        }
    }

    private void addActionListeners() {
        comboBoxActionListeners(0, strengthComboBox);
        comboBoxActionListeners(1, dexterityComboBox);
        comboBoxActionListeners(2, constitutionComboBox);
        comboBoxActionListeners(3, intelligenceComboBox);
        comboBoxActionListeners(4, wisdomComboBox);
        comboBoxActionListeners(5, charismaComboBox);

        saveCheckBoxActionListeners(strengthSaveCheckBox, strengthModifierLabel);
        saveCheckBoxActionListeners(dexteritySaveCheckBox, dexterityModifierLabel);
        saveCheckBoxActionListeners(constitutionSaveCheckBox, constitutionModifierLabel);
        saveCheckBoxActionListeners(intelligenceSaveCheckBox, intelligenceModifierLabel);
        saveCheckBoxActionListeners(wisdomSaveCheckBox, wisdomModifierLabel);
        saveCheckBoxActionListeners(charismaSaveCheckBox, charismaModifierLabel);

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
            comboBoxAction(index, comboBox);
        });
    }

    private void comboBoxAction(int i, JComboBox<String> comboBox) {
        if (passGo && listenGo) {
            listenGo = false;
            index = i;

            updateComboBoxes(comboBox);
            resetComboBoxes();
            propagateRolls();
            resetSelections();
            resetModifiers();

            listenGo = true;
        }
    }

    private void updateComboBoxes(JComboBox<String> comboBox) {
        selectedRolls.remove(index);
        selection = String.valueOf(comboBox.getSelectedItem());
        updateModifier();
        if (selection.isEmpty()) {
            selectedRolls.put(index, NEGATIVE);
        } else if (selection.equals(UNSELECT)){
            selectedRolls.put(index, NEGATIVE);
        } else {
            selectedRolls.put(index, selection);
        }
    }

    private void resetComboBoxes() {
        strengthComboBox.removeAllItems();
        dexterityComboBox.removeAllItems();
        constitutionComboBox.removeAllItems();
        intelligenceComboBox.removeAllItems();
        wisdomComboBox.removeAllItems();
        charismaComboBox.removeAllItems();
    }

    private void updateModifier() {
        int abilityScore = 0;
        if (!selection.equals(NEGATIVE) && !selection.equals(UNSELECT)) {
            abilityScore = Integer.parseInt(selection.substring(3));
        }
        int modifier = 0;
        String plusOrMinus = "";

        switch (abilityScore) {
            case 0:
                break;
            case 3:
                modifier = 4;
                plusOrMinus = "-";
                break;
            case 4:
            case 5:
                modifier = 3;
                plusOrMinus = "-";
                break;
            case 6:
            case 7:
                modifier = 2;
                plusOrMinus = "-";
                break;
            case 8:
            case 9:
                modifier = 1;
                plusOrMinus = "-";
                break;
            case 10:
            case 11:
                break;
            case 12:
            case 13:
                modifier = 1;
                plusOrMinus = "+";
                break;
            case 14:
            case 15:
                modifier = 2;
                plusOrMinus = "+";
                break;
            case 16:
            case 17:
                modifier = 3;
                plusOrMinus = "+";
                break;
            case 18:
                modifier = 4;
                plusOrMinus = "+";
                break;
        }

        switch (index) {
            case 0:
                strengthModifierLabel.setText(plusOrMinus + modifier);
                break;
            case 1:
                dexterityModifierLabel.setText(plusOrMinus + modifier);
                break;
            case 2:
                constitutionModifierLabel.setText(plusOrMinus + modifier);
                break;
            case 3:
                intelligenceModifierLabel.setText(plusOrMinus + modifier);
                break;
            case 4:
                wisdomModifierLabel.setText(plusOrMinus + modifier);
                break;
            case 5:
                charismaModifierLabel.setText(plusOrMinus + modifier);
                break;
        }
    }

    private void resetModifiers() {
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
        setCursor(WAIT_CURSOR);
        checkbox.setVisible(false);
        checkbox.doClick();
        checkbox.doClick();
        checkbox.setVisible(true);
        setCursor(DEFAULT_CURSOR);
    }

    private void saveCheckBoxActionListeners(JCheckBox checkBox, JLabel label) {
        checkBox.addActionListener(event -> {
            saveCheckBoxAction(label, checkBox);
        });

    }

    private void saveCheckBoxAction(JLabel modifierLabel, JCheckBox saveCheckBox) {
        if (passGo) {
            System.out.println("TEST");
            String modifier = modifierLabel.getText();
            if (modifier.equals("0")) {
                int mod = 0;
                setCheckBox(mod, saveCheckBox);
            } else {
                String addSub = modifier.substring(0, 1);
                int mod = Integer.parseInt(modifier.substring(1));
                if (addSub.equals("-")) {
                    setCheckBox(-mod, saveCheckBox);
                } else {
                    setCheckBox(mod, saveCheckBox);
                }
            }
        }
    }

    private void setCheckBox(int mod, JCheckBox checkBox) {
        if (checkBox.isSelected()) {
            checkBox.setText(String.valueOf(mod + proficiencyModifier));
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
            label.setText(String.valueOf(mod + proficiencyModifier));
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
    }

    private void saveSheet() {
        if (sheets.size() <= currentSheet) {
            // TODO update this with real values
            sheets.add(new Sheet(new Abilities(), new Character(), new Gear(),new Languages(), new Saves(), new Skills(), new Tools()));
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

        languagesKnownComboBox.removeAllItems();
        languagesLeftComboBox.removeAllItems();
        toolsKnownComboBox.removeAllItems();
        weaponsComboBox.removeAllItems();
        armorComboBox.removeAllItems();

        featuresTextPane.setText("This is where Racial, Class and Background feats will go.");
    }

    private void updateSheet() {
        startRolls();
        deselectRolls();
        getNewCharacter();
    }

    private void deselectRolls() {
        strengthComboBox.setSelectedIndex(-1);
        dexterityComboBox.setSelectedIndex(-1);
        constitutionComboBox.setSelectedIndex(-1);
        intelligenceComboBox.setSelectedIndex(-1);
        wisdomComboBox.setSelectedIndex(-1);
        charismaComboBox.setSelectedIndex(-1);
    }
}
