package Final;

public class Sheet {
    private Abilities abilities;
    private Character character;
    private Gear gear;
    private Languages languages;
    private Saves saves;
    private Skills skills;
    private Tools tools;

    Sheet(Abilities abilities, Character character, Gear gear, Languages languages, Saves saves, Skills skills, Tools tools) {
        this.abilities = abilities;
        this.character = character;
        this.gear = gear;
        this.languages = languages;
        this.saves = saves;
        this.skills = skills;
        this.tools = tools;
    }

    public Abilities getAbilities() { return abilities; }
    public Character getCharacter() { return character; }
    public Gear getGear() { return gear; }
    public Languages getLanguages() { return languages; }
    public Saves getSaves() { return saves; }
    public Skills getSkills() { return skills; }
    public Tools getTools() { return tools; }
    public void setAbilities(Abilities abilities) { this.abilities = abilities; }
    public void setCharacter(Character character) { this.character = character; }
    public void setGear(Gear gear) { this.gear = gear; }
    public void setLanguages(Languages languages) { this.languages = languages; }
    public void setSaves(Saves saves) { this.saves = saves; }
    public void setSkills(Skills skills) { this.skills = skills; }
    public void setTools(Tools tools) { this.tools = tools; }
}
