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
                Main.setName(name);
                Export.nextStep(sheet);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name.");
            }
        });
    }

    private static boolean validateName(String name) {
        // TODO add more validation for the name
        if (name != null) {
            return name.length() != 0;
        } else {
            return true;
        }
    }
}
