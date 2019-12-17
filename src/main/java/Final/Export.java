package Final;

import Final.Objects.*;
import Final.Objects.Character;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class Export {
    static void export(Sheet sheet) {
        NameGUI nameGUI = new NameGUI(sheet);
    }

    static void nextStep(Sheet sheet) {
        String name = Main.getName();

        getFileData(sheet, name);
    }

    private static void getFileData(Sheet sheet, String name) {
        String filename = name + "'s Character'.txt";
        String file = name + "'s Character:\n";

        Character character = sheet.getCharacter();
        Modifiers modifiers = sheet.getModifiers();
        Saves saves = sheet.getSaves();
        SkillSelections skillSelections = sheet.getSkillStates().getSkillSelections();
        Feats feats = sheet.getFeats();
        Languages languages = sheet.getLanguages();
        Gear gear = sheet.getGear();
        Tools tools = sheet.getTools();

        file = getCharacterData(file, character);
        file = getFeatData(file, feats);
        file = getGearData(file, gear);
        file = getLanguageData(file, languages);
        file = getModifierData(file, modifiers);
        file = getSaveData(file, saves);
        file = getSkillData(file, skillSelections);
        file = getToolData(file, tools);

        writeFile(filename, file);
    }

    private static String getCharacterData(String file, Character character) {
        file += "Race: " + character.getRaceName() + "\n";
        file += "Class: " + character.getClassName() + "\n";
        file += "Background: " + character.getBackgroundName() + "\n";
        return file;
    }

    private static String getModifierData(String file, Modifiers modifiers) {
        return file;
    }

    private static String getSaveData(String file, Saves saves) {
        return file;
    }

    private static String getSkillData(String file, SkillSelections skillSelections) {
        return file;
    }

    private static String getFeatData(String file, Feats feats) {
        return file;
    }

    private static String getLanguageData(String file, Languages languages) {
        return file;
    }

    private static String getGearData(String file, Gear gear) {
        return file;
    }

    private static String getToolData(String file, Tools tools) {
        return file;
    }

    private static void writeFile(String filename, String file) {
        // Creates writer objects and opens the fun time writing stuff.
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filename))) {
            bufWriter.write(file);
        } catch (IOException ioe){
            System.out.println("Couldn't save it. Boohoo.");
        }
        // Closes the file when the try-catch closes.
    }
}
