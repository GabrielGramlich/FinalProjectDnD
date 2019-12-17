package Final;

import Final.Objects.*;
import Final.Objects.Character;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Import {
    private static int iRace;
    private static int iClass;
    private static int iBackground;
    private static final String URL = "jdbc:mysql://localhost:3306/FinalProjectDB?useUnicode=true&useJDBCCompliant" +
            "TimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static final String USER = "Final";
    private static final String PASSWORD = "koolerthanjesuswasagoodsong";

    public static void main(String[] args) { }

    public static Sheet getCharacterData(int raceSelection, int classSelection, int backgroundSelection) {
        iRace = raceSelection;
        iClass = classSelection;
        iBackground = backgroundSelection;

        Character character = getCharacter();
        Feats feats = getFeats();
        Gear gear = getGear();
        Languages languages = getLanguages();
        Saves saves = getSaves();
        SkillStates skillStates = getSkillStates();
        Modifiers modifiers = getModifiers();
        Tools tools = getTools();

        return new Sheet(character, feats, gear, languages, saves, skillStates, modifiers, tools);
    }

    private static Character getCharacter() {
        String raceName = getString("SELECT * FROM Races WHERE RaceID = " + iRace, "Name");
        String className = getString("SELECT * FROM Classes WHERE ClassID = " + iClass, "Name");
        int hitPoints = getInt("SELECT * FROM Classes WHERE ClassID = " + iClass, "HitPoints");
        int skillCount = getInt("SELECT * FROM Classes WHERE ClassID = " + iClass, "Skills");
        String backgroundName = getString("SELECT * FROM Backgrounds WHERE BackgroundID = " + iBackground, "Name");

        return new Character(raceName, className, backgroundName, hitPoints, skillCount);
    }

    private static Feats getFeats() {
        List<String> backgroundFeatNames = getStringArray("SELECT Name, Description FROM FeatureBridge INNER JOIN Features ON FeatureBridge.FeatureID = Features.FeatureID WHERE BackgroundID = " + iBackground, "Name");
        List<String> backgroundFeatDescriptions = getStringArray("SELECT Name, Description FROM FeatureBridge INNER JOIN Features ON FeatureBridge.FeatureID = Features.FeatureID WHERE BackgroundID = " + iBackground, "Description");
        List<String> classFeatNames = getStringArray("SELECT Name, Description FROM FeatureBridge INNER JOIN Features ON FeatureBridge.FeatureID = Features.FeatureID WHERE ClassID = " + iClass, "Name");
        List<String> classFeatDescriptions = getStringArray("SELECT Name, Description FROM FeatureBridge INNER JOIN Features ON FeatureBridge.FeatureID = Features.FeatureID WHERE ClassID = " + iClass, "Description");
        List<String> raceFeatNames = getStringArray("SELECT Name, Description FROM FeatureBridge INNER JOIN Features ON FeatureBridge.FeatureID = Features.FeatureID WHERE RaceID = " + iRace, "Name");
        List<String> raceFeatDescriptions = getStringArray("SELECT Name, Description FROM FeatureBridge INNER JOIN Features ON FeatureBridge.FeatureID = Features.FeatureID WHERE RaceID = " + iRace, "Description");

        return new Feats(backgroundFeatNames, backgroundFeatDescriptions, classFeatNames, classFeatDescriptions, raceFeatNames, raceFeatDescriptions);
    }

    private static Gear getGear() {
        List<String> armorNames = getStringArray("SELECT Name, Base, Type FROM ArmorBridge INNER JOIN Armor ON ArmorBridge.ArmorID = Armor.ArmorID WHERE ClassID = " + iClass, "Name");
        List<Integer> armorBases = getIntArray("SELECT Name, Base, Type FROM ArmorBridge INNER JOIN Armor ON ArmorBridge.ArmorID = Armor.ArmorID WHERE ClassID = " + iClass, "Base");
        List<String> armorTypes = getStringArray("SELECT Name, Base, Type FROM ArmorBridge INNER JOIN Armor ON ArmorBridge.ArmorID = Armor.ArmorID WHERE ClassID = " + iClass, "Type");
        List<String> weaponNames = getStringArray("SELECT Name, Damage, Modifier FROM WeaponBridge INNER JOIN Weapons ON WeaponBridge.WeaponID = Weapons.WeaponID WHERE ClassID = " + iClass, "Name");
        List<String> weaponDamages = getStringArray("SELECT Name, Damage, Modifier FROM WeaponBridge INNER JOIN Weapons ON WeaponBridge.WeaponID = Weapons.WeaponID WHERE ClassID = " + iClass, "Damage");
        List<Integer> weaponModifiers = getIntArray("SELECT Name, Damage, Modifier FROM WeaponBridge INNER JOIN Weapons ON WeaponBridge.WeaponID = Weapons.WeaponID WHERE ClassID = " + iClass, "Modifier");

        return new Gear(armorNames, armorBases, armorTypes, weaponNames, weaponDamages, weaponModifiers);
    }

    private static Languages getLanguages() {
        List<String> backgroundLanguages = getStringArray("SELECT Name FROM LanguageBridge INNER JOIN Languages ON LanguageBridge.LanguageID = Languages.LanguageID WHERE BackgroundID = " + iBackground, "Name");
        List<String> raceLanguages = getStringArray("SELECT Name FROM LanguageBridge INNER JOIN Languages ON LanguageBridge.LanguageID = Languages.LanguageID WHERE RaceID = " + iRace, "Name");
        List<String> allLanguages = getStringArray("SELECT Name FROM Languages", "Name");

        return new Languages(backgroundLanguages, raceLanguages, allLanguages);
    }

    private static Saves getSaves() {
        List<String> classSaves = getStringArray("SELECT Name FROM SavingThrowBridge INNER JOIN AbilityScores ON SavingThrowBridge.AbilityScoreID = AbilityScores.AbilityScoreID WHERE ClassID = " + iClass, "Name");

        return new Saves(classSaves);
    }

    private static SkillStates getSkillStates() {
        List<Boolean> backgroundChoices = getBoolArray("SELECT Name, Choice FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE BackgroundID = " + iBackground + " AND Type = \"Skill\"", "Choice");
        List<String> backgroundProficiencies = getStringArray("SELECT Name, Choice FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE BackgroundID = " + iBackground + " AND Type = \"Skill\"", "Name");
        List<Boolean> classChoices = getBoolArray("SELECT Name, Choice FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE ClassID = " + iClass + " AND Type = \"Skill\"", "Choice");
        List<String> classProficiencies = getStringArray("SELECT Name, Choice FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE ClassID = " + iClass + " AND Type = \"Skill\"", "Name");
        List<Boolean> raceChoices = getBoolArray("SELECT Name, Choice FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE RaceID = " + iRace + " AND Type = \"Skill\"", "Choice");
        List<String> raceProficiencies = getStringArray("SELECT Name, Choice FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE RaceID = " + iRace + " AND Type = \"Skill\"", "Name");
        List<Integer> classSkills = getIntArray("SELECT Skills FROM Classes WHERE ClassID = " + iClass, "Skills");

        return new SkillStates(backgroundChoices, backgroundProficiencies, classChoices, classProficiencies, raceChoices, raceProficiencies, classSkills.get(0));
    }

    private static Modifiers getModifiers() {
        List<String> raceAbilities = getStringArray("SELECT Name, Modifier FROM AbilityScoreBridge INNER JOIN AbilityScores ON AbilityScoreBridge.AbilityScoreID = AbilityScores.AbilityScoreID WHERE RaceID = " + iRace, "Name");
        List<Integer> raceModifiers = getIntArray("SELECT Name, Modifier FROM AbilityScoreBridge INNER JOIN AbilityScores ON AbilityScoreBridge.AbilityScoreID = AbilityScores.AbilityScoreID WHERE RaceID = " + iRace, "Modifier");

        return new Modifiers(raceAbilities, raceModifiers);
    }

    private static Tools getTools() {
        List<String> backgroundProficiencies = getStringArray("SELECT Name FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE BackgroundID = " + iBackground + " AND Type = \"Tool\"", "Name");
        List<String> raceProficiencies = getStringArray("SELECT Name FROM ProficiencyBridge INNER JOIN Proficiencies ON ProficiencyBridge.ProficiencyID = Proficiencies.ProficiencyID WHERE RaceID = " + iRace + " AND Type = \"Tool\"", "Name");

        return new Tools(backgroundProficiencies, raceProficiencies);
    }

    private static String getString(String sqlStatement, String column) {
        String result = "";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet results = statement.executeQuery(sqlStatement);

            while (results.next()) {
                result = results.getString(column);
            }
            return result;
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle);
            return result;
        }
    }

    private static int getInt(String sqlStatement, String column) {
        int result = -1;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet results = statement.executeQuery(sqlStatement);

            while (results.next()) {
                result = results.getInt(column);
            }
            return result;
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle);
            return result;
        }
    }

    private static List<String> getStringArray(String sqlStatement, String column) {
        List<String> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet results = statement.executeQuery(sqlStatement);

            while (results.next()) {
                result.add(results.getString(column));
            }
            return result;
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle);
            return result;
        }
    }

    private static List<Integer> getIntArray(String sqlStatement, String column) {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet results = statement.executeQuery(sqlStatement);

            while (results.next()) {
                result.add(results.getInt(column));
            }
            return result;
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle);
            return result;
        }
    }

    private static List<Boolean> getBoolArray(String sqlStatement, String column) {
        List<Boolean> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()){

            ResultSet results = statement.executeQuery(sqlStatement);

            while (results.next()) {
                result.add(results.getBoolean(column));
            }
            return result;
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle);
            return result;
        }
    }
}
