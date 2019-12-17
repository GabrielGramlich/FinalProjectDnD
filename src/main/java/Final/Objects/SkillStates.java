package Final.Objects;

import Final.PlayerSheetGUI.State;

import java.util.List;

public class SkillStates {
    private State acrobatics = State.CLOSED;
    private State animalHandling = State.CLOSED;
    private State arcana = State.CLOSED;
    private State athletics = State.CLOSED;
    private State deception = State.CLOSED;
    private State history = State.CLOSED;
    private State insight = State.CLOSED;
    private State intimidation = State.CLOSED;
    private State investigation = State.CLOSED;
    private State medicine = State.CLOSED;
    private State nature = State.CLOSED;
    private State perception = State.CLOSED;
    private State performance = State.CLOSED;
    private State persuasion = State.CLOSED;
    private State religion = State.CLOSED;
    private State sleightOfHand = State.CLOSED;
    private State stealth = State.CLOSED;
    private State survival = State.CLOSED;
    private int any = 0;
    private int choices;
    private SkillSelections skillSelections;

    public SkillStates(List<Boolean> backgroundChoices, List<String> backgroundProficiencies, List<Boolean> classChoices, List<String> classProficiencies, List<Boolean>raceChoices, List<String> raceProficiencies, int choices) {
        skillSelections = new SkillSelections();
        setStates(classChoices, classProficiencies);
        setStates(backgroundChoices, backgroundProficiencies);
        setStates(raceChoices, raceProficiencies);
        this.choices = choices;
    }

    private void setStates(List<Boolean> choices, List<String> proficiencies) {
        for (int i = 0; i < proficiencies.size(); i++) {
            String proficiency = proficiencies.get(i);
            boolean choice = choices.get(i);
            switch (proficiency) {
                case "Acrobatics":
                    if (choice) {
                        acrobatics = State.OPEN;
                    } else {
                        acrobatics = State.FINAL;
                        skillSelections.setAcrobatics(true);
                    }
                    break;
                case "Animal Handling":
                    if (choice) {
                        animalHandling = State.OPEN;
                    } else {
                        animalHandling = State.FINAL;
                        skillSelections.setAnimalHandling(true);
                    }
                    break;
                case "Arcana":
                    if (choice) {
                        arcana = State.OPEN;
                    } else {
                        arcana = State.FINAL;
                        skillSelections.setArcana(true);
                    }
                    break;
                case "Athletics":
                    if (choice) {
                        athletics = State.OPEN;
                    } else {
                        athletics = State.FINAL;
                        skillSelections.setAthletics(true);
                    }
                    break;
                case "Deception":
                    if (choice) {
                        deception = State.OPEN;
                    } else {
                        deception = State.FINAL;
                        skillSelections.setDeception(true);
                    }
                    break;
                case "History":
                    if (choice) {
                        history = State.OPEN;
                    } else {
                        history = State.FINAL;
                        skillSelections.setHistory(true);
                    }
                    break;
                case "Insight":
                    if (choice) {
                        insight = State.OPEN;
                    } else {
                        insight = State.FINAL;
                        skillSelections.setInsight(true);
                    }
                    break;
                case "Intimidation":
                    if (choice) {
                        intimidation = State.OPEN;
                    } else {
                        intimidation = State.FINAL;
                        skillSelections.setIntimidation(true);
                    }
                    break;
                case "Investigation":
                    if (choice) {
                        investigation = State.OPEN;
                    } else {
                        investigation = State.FINAL;
                        skillSelections.setInvestigation(true);
                    }
                    break;
                case "Medicine":
                    if (choice) {
                        medicine = State.OPEN;
                    } else {
                        medicine = State.FINAL;
                        skillSelections.setMedicine(true);
                    }
                    break;
                case "Nature":
                    if (choice) {
                        nature = State.OPEN;
                    } else {
                        nature = State.FINAL;
                        skillSelections.setNature(true);
                    }
                    break;
                case "Perception":
                    if (choice) {
                        perception = State.OPEN;
                    } else {
                        perception = State.FINAL;
                        skillSelections.setPerception(true);
                    }
                    break;
                case "Performance":
                    if (choice) {
                        performance = State.OPEN;
                    } else {
                        performance = State.FINAL;
                        skillSelections.setPerformance(true);
                    }
                    break;
                case "Persuasion":
                    if (choice) {
                        persuasion = State.OPEN;
                    } else {
                        persuasion = State.FINAL;
                        skillSelections.setPersuasion(true);
                    }
                    break;
                case "Religion":
                    if (choice) {
                        religion = State.OPEN;
                    } else {
                        religion = State.FINAL;
                        skillSelections.setReligion(true);
                    }
                    break;
                case "Sleight of Hand":
                    if (choice) {
                        sleightOfHand = State.OPEN;
                    } else {
                        sleightOfHand = State.FINAL;
                        skillSelections.setSleightOfHand(true);
                    }
                    break;
                case "Stealth":
                    if (choice) {
                        stealth = State.OPEN;
                    } else {
                        stealth = State.FINAL;
                        skillSelections.setStealth(true);
                    }
                    break;
                case "Survival":
                    if (choice) {
                        survival = State.OPEN;
                    } else {
                        survival = State.FINAL;
                        skillSelections.setSurvival(true);
                    }
                    break;
                case "Any":
                    any++;
                    openProficiencies();
                    break;
            }
        }
    }

    private void openProficiencies() {
        acrobatics = openProficiency(acrobatics, getAcrobatics());
        animalHandling = openProficiency(animalHandling, getAnimalHandling());
        arcana = openProficiency(arcana, getArcana());
        athletics = openProficiency(athletics, getAthletics());
        deception = openProficiency(deception, getDeception());
        history = openProficiency(history, getHistory());
        insight = openProficiency(insight, getInsight());
        intimidation = openProficiency(intimidation, getIntimidation());
        investigation = openProficiency(investigation, getInvestigation());
        medicine = openProficiency(medicine, getMedicine());
        nature = openProficiency(nature, getNature());
        perception = openProficiency(perception, getPerception());
        performance = openProficiency(performance, getPerformance());
        persuasion = openProficiency(persuasion, getPersuasion());
        religion = openProficiency(religion, getReligion());
        sleightOfHand = openProficiency(sleightOfHand, getSleightOfHand());
        stealth = openProficiency(stealth, getStealth());
        survival = openProficiency(survival, getSurvival());
    }

    private State openProficiency(State state, State oldState) {
        if (!state.equals(State.FINAL)) {
            return State.OPEN;
        } else {
            return oldState;
        }
    }

    public State getAcrobatics() { return acrobatics; }
    public State getAnimalHandling() { return animalHandling; }
    public State getArcana() { return arcana; }
    public State getAthletics() { return athletics; }
    public State getDeception() { return deception; }
    public State getHistory() { return history; }
    public State getInsight() { return insight; }
    public State getIntimidation() { return intimidation; }
    public State getInvestigation() { return investigation; }
    public State getMedicine() { return medicine; }
    public State getNature() { return nature; }
    public State getPerception() { return perception; }
    public State getPerformance() { return performance; }
    public State getPersuasion() { return persuasion; }
    public State getReligion() { return religion; }
    public State getSleightOfHand() { return sleightOfHand; }
    public State getStealth() { return stealth; }
    public State getSurvival() { return survival; }
    public int getAny() { return any; }
    public int getChoices() { return choices; }
    public SkillSelections getSkillSelections() { return skillSelections; }

    public void setAcrobatics(State acrobatics) { this.acrobatics = acrobatics; }
    public void setAnimalHandling(State animalHandling) { this.animalHandling = animalHandling; }
    public void setArcana(State arcana) { this.arcana = arcana; }
    public void setAthletics(State athletics) { this.athletics = athletics; }
    public void setDeception(State deception) { this.deception = deception; }
    public void setHistory(State history) { this.history = history; }
    public void setInsight(State insight) { this.insight = insight; }
    public void setIntimidation(State intimidation) { this.intimidation = intimidation; }
    public void setInvestigation(State investigation) { this.investigation = investigation; }
    public void setMedicine(State medicine) { this.medicine = medicine; }
    public void setNature(State nature) { this.nature = nature; }
    public void setPerception(State perception) { this.perception = perception; }
    public void setPerformance(State performance) { this.performance = performance; }
    public void setPersuasion(State persuasion) { this.persuasion = persuasion; }
    public void setReligion(State religion) { this.religion = religion; }
    public void setSleightOfHand(State sleightOfHand) { this.sleightOfHand = sleightOfHand; }
    public void setStealth(State stealth) { this.stealth = stealth; }
    public void setSurvival(State survival) { this.survival = survival; }
    public void setAny(int any) { this.any = any; }
    public void setChoices(int choices) { this.choices = choices; }
    public void setSkillSelections(SkillSelections skillSelections) { this.skillSelections = skillSelections; }
}
