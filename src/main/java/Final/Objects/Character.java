package Final.Objects;

public class Character {
    private String raceName;
    private String className;
    private String backgroundName;
    private int hitPoints;
    private int skillCount;

    public Character(String raceName, String className, String backgroundName, int hitPoints, int skillCount) {
        this.raceName = raceName;
        this.className = className;
        this.backgroundName = backgroundName;
        this.hitPoints = hitPoints;
        this.skillCount = skillCount;
    }

    public String getRaceName() { return raceName; }
    public String getClassName() { return className; }
    public String getBackgroundName() { return backgroundName; }
    public int getHitPoints() { return hitPoints; }
    public int getSkillCount() { return skillCount; }

    public void setRaceName(String raceName) { this.raceName = raceName; }
    public void setClassName(String className) { this.className = className; }
    public void setBackgroundName(String backgroundName) { this.backgroundName = backgroundName; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public void setSkillCount(int skillCount) { this.skillCount = skillCount; }
}
