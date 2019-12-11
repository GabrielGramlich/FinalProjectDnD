//import javax.swing.*;
//
//public class BadCode extends  JFrame {
//    private void addActionListeners() {
//        comboBoxActionListeners(0, strengthComboBox);
//        comboBoxActionListeners(1, dexterityComboBox);
//        comboBoxActionListeners(2, constitutionComboBox);
//        comboBoxActionListeners(3, intelligenceComboBox);
//        comboBoxActionListeners(4, wisdomComboBox);
//        comboBoxActionListeners(5, charismaComboBox);
//
//        saveCheckBoxActionListeners(strengthSaveCheckBox, strengthModifierLabel);
//        saveCheckBoxActionListeners(dexteritySaveCheckBox, dexterityModifierLabel);
//        saveCheckBoxActionListeners(constitutionSaveCheckBox, constitutionModifierLabel);
//        saveCheckBoxActionListeners(intelligenceSaveCheckBox, intelligenceModifierLabel);
//        saveCheckBoxActionListeners(wisdomSaveCheckBox, wisdomModifierLabel);
//        saveCheckBoxActionListeners(charismaSaveCheckBox, charismaModifierLabel);
//
//        skillCheckBoxActionListeners(athleticsStrCheckBox, strengthModifierLabel, athleticsLabel);
//        skillCheckBoxActionListeners(acrobaticsDexCheckBox, dexterityModifierLabel, dexterityModifierLabel);
//        skillCheckBoxActionListeners(sleightOfHandDexCheckBox, dexterityModifierLabel, sleightOfHandLabel);
//        skillCheckBoxActionListeners(stealthDexCheckBox, dexterityModifierLabel, stealthLabel);
//        skillCheckBoxActionListeners(arcanaIntCheckBox, intelligenceModifierLabel, arcanaLabel);
//        skillCheckBoxActionListeners(historyIntCheckBox, intelligenceModifierLabel, historyLabel);
//        skillCheckBoxActionListeners(investigationIntCheckBox, intelligenceModifierLabel, investigationLabel);
//        skillCheckBoxActionListeners(natureIntCheckBox, intelligenceModifierLabel, natureLabel);
//        skillCheckBoxActionListeners(religionIntCheckBox, intelligenceModifierLabel, religionLabel);
//        skillCheckBoxActionListeners(animalHandlingWisCheckBox, wisdomModifierLabel, animalHandlingLabel);
//        skillCheckBoxActionListeners(insightWisCheckBox, wisdomModifierLabel, insightLabel);
//        skillCheckBoxActionListeners(medicineWisCheckBox, wisdomModifierLabel, medicineLabel);
//        skillCheckBoxActionListeners(perceptionWisCheckBox, wisdomModifierLabel, perceptionLabel);
//        skillCheckBoxActionListeners(survivalWisCheckBox, wisdomModifierLabel, survivalLabel);
//        skillCheckBoxActionListeners(deceptionChaCheckBox, charismaModifierLabel, deceptionLabel);
//        skillCheckBoxActionListeners(intimidationChaCheckBox, charismaModifierLabel, intimidationLabel);
//        skillCheckBoxActionListeners(performanceChaCheckBox, charismaModifierLabel, performanceLabel);
//        skillCheckBoxActionListeners(persuasionChaCheckBox, charismaModifierLabel, persuasionLabel);
//
//        perceptionWisCheckBox.addActionListener(event -> {
//            skillCheckBoxAction(wisdomModifierLabel, perceptionWisCheckBox, wisdomModifierLabel);
//            updatePassive();
//        });
//    }
//
//    private void skillCheckBoxAction(JLabel modifierLabel, JCheckBox checkBox, JLabel label) {
//        String modifier = modifierLabel.getText();
//        if (modifier.equals("0")) {
//            int mod = 0;
//            setLabel(mod, checkBox, label);
//        } else {
//            String addSub = modifier.substring(0,1);
//            int mod = Integer.parseInt(modifier.substring(1));
//            if (addSub.equals("-")) {
//                setLabel(-mod, checkBox, label);
//            } else {
//                setLabel(mod, checkBox, label);
//            }
//        }
//    }
//
//    private void setLabel(int mod, JCheckBox checkBox, JLabel label) {
//        if (checkBox.isSelected()) {
//            label.setText(String.valueOf(mod + proficiencyModifier));
//        } else {
//            label.setText(String.valueOf(mod));
//        }
//    }
//
//    private void updatePassive() {
//        String modifier = perceptionLabel.getText();
//        if (modifier.equals("0")) {
//            int mod = 0;
//            passivePerceptionLabel.setText("10");
//        } else {
//            String addSub = modifier.substring(0,1);
//            int mod = Integer.parseInt(modifier.substring(1));
//            if (addSub.equals("-")) {
//                passivePerceptionLabel.setText(String.valueOf(-mod + 10));
//            } else {
//                passivePerceptionLabel.setText(String.valueOf(mod + 10));
//            }
//        }
//    }
//
//
//}
