package Final.Objects;

public class Sheet {
    private Character character;
    private Feats feats;
    private Gear gear;
    private Languages languages;
    private Saves saves;
    private SkillStates skillStates;
    private Modifiers modifiers;
    private Tools tools;

    public Sheet(Character character, Feats feats, Gear gear, Languages languages, Saves saves, SkillStates skillStates, Modifiers modifiers, Tools tools) {
        this.feats = feats;
        this.character = character;
        this.gear = gear;
        this.languages = languages;
        this.saves = saves;
        this.skillStates = skillStates;
        this.modifiers = modifiers;
        this.tools = tools;
    }

    public Character getCharacter() { return character; }
    public Feats getFeats() { return feats; }
    public Gear getGear() { return gear; }
    public Languages getLanguages() { return languages; }
    public Saves getSaves() { return saves; }
    public SkillStates getSkillStates() { return skillStates; }
    public Modifiers getModifiers() { return modifiers; }
    public Tools getTools() { return tools; }

    public void setCharacter(Character character) { this.character = character; }
    public void setFeats(Feats feats) { this.feats = feats; }
    public void setGear(Gear gear) { this.gear = gear; }
    public void setLanguages(Languages languages) { this.languages = languages; }
    public void setSaves(Saves saves) { this.saves = saves; }
    public void setSkillStates(SkillStates skillStates) { this.skillStates = skillStates; }
    public void setModifiers(Modifiers modifiers) { this.modifiers = modifiers; }
    public void setTools(Tools tools) { this.tools = tools; }
}
