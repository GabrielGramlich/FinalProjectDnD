package Final;

import Final.Objects.Sheet;

import javax.swing.*;

public class NameGUI extends JFrame {
    private JTextField nameTextField;
    private JPanel mainPanel;
    private JButton okayButton;
    private Sheet sheet;

    NameGUI(Sheet sheet) {
        this.sheet = sheet;

        this.setContentPane(mainPanel);
        setTitle("User's Name");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Allows form to pop up in center of the screen.
        setSize(250, 175);
        setVisible(true);

        addActionListener();
    }

    private void addActionListener() {
        okayButton.addActionListener(event -> {
            String name = nameTextField.getText();
            if (validateName(name)) {
                // Jumps back to export with the user's name.
                Export.nextStep(sheet, name);
                this.dispose();
            } else {
                // Tells the user to stop being dumb, and then makes them.
                JOptionPane.showMessageDialog(this, "Please enter a valid name.");
                nameTextField.setText("");
            }
        });
    }

    private static boolean validateName(String name) {
        // Checks to make sure there's something entered, and that it's good. Validation, I think it's called.
        if (name == null || name.length() == 0) {
            return false;
        } else if (hasBadCharacters(name)){
            return false;
        } else {
            return true;
        }
    }

    private static boolean hasBadCharacters(String name) {
        String[] badCharacters = {"#", "%", "&", "{", "}", "\\", "/", "<", ">", "*", "?", " ", "$", "!", "\'", "\"", ":", "@", "+", "`", "|", "="};
        boolean bad = false;

        // Checks if input contains a character that'll fork with a filename.
        for (String character : badCharacters) {
            if (name.contains(character)) {
                bad = true;
            }
        }

        return bad;
    }


}
