package Final.Objects;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    private List<String> toolProficiencies = new ArrayList<>();

    public Tools(List<String> backgroundProficiencies, List<String> raceProficiencies) {
        toolProficiencies.addAll(backgroundProficiencies);
        toolProficiencies.addAll(raceProficiencies);
    }

    public List<String> getToolProficiencies() { return toolProficiencies; }
    public void setToolProficiencies(List<String> toolProficiencies) { this.toolProficiencies = toolProficiencies; }
}
