package Final.Objects;

import java.util.List;

/************************************************************
 * This object stores the character's weapon and armor data *
 ************************************************************/

public class Gear {
    private List<String> armorNames;
    private List<Integer> armorBases;
    private List<String> armorTypes;
    private List<String> weaponNames;
    private List<String> weaponDamages;
    private List<Integer> weaponModifiers;

    public Gear(List<String> armorNames, List<Integer> armorBases, List<String> armorTypes, List<String> weaponNames, List<String> weaponDamages, List<Integer> weaponModifiers) {
        this.armorNames = armorNames;
        this.armorBases = armorBases;
        this.armorTypes = armorTypes;
        this.weaponNames = weaponNames;
        this.weaponDamages = weaponDamages;
        this.weaponModifiers = weaponModifiers;
    }

    public List<String> getArmorNames() { return armorNames; }
    public List<Integer> getArmorBases() { return armorBases; }
    public List<String> getArmorTypes() { return armorTypes; }
    public List<String> getWeaponNames() { return weaponNames; }
    public List<String> getWeaponDamages() { return weaponDamages; }
    public List<Integer> getWeaponModifiers() { return weaponModifiers; }

    public void setArmorNames(List<String> armorNames) { this.armorNames = armorNames; }
    public void setArmorBases(List<Integer> armorBases) { this.armorBases = armorBases; }
    public void setArmorTypes(List<String> armorTypes) { this.armorTypes = armorTypes; }
    public void setWeaponNames(List<String> weaponNames) { this.weaponNames = weaponNames; }
    public void setWeaponDamages(List<String> weaponDamages) { this.weaponDamages = weaponDamages; }
    public void setWeaponModifiers(List<Integer> weaponModifiers) { this.weaponModifiers = weaponModifiers; }
}
