package Final;

public class Main {
    private static String name;

    public static void main(String[] args) {
        RollGUI rollGUI = new RollGUI();
    }

    static void setName(String entry) { name = entry; }
    static String getName() { return name; }
}
