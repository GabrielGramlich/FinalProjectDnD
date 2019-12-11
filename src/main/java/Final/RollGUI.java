package Final;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RollGUI extends JFrame {
    private JTextPane instructionsTextPane;
    private JPanel mainPanel;
    private JButton rollButton;
    private JButton generateCharacterSheetsButton;
    private JLabel rollOneOneLabel;
    private JLabel rollOneTwoLabel;
    private JLabel rollOneThreeLabel;
    private JLabel rollOneFourLabel;
    private JLabel rollOneTotalLabel;
    private JLabel rollTwoOneLabel;
    private JLabel rollTwoTwoLabel;
    private JLabel rollTwoThreeLabel;
    private JLabel rollTwoFourLabel;
    private JLabel rollTwoTotalLabel;
    private JLabel rollThreeOneLabel;
    private JLabel rollThreeTwoLabel;
    private JLabel rollThreeThreeLabel;
    private JLabel rollThreeFourLabel;
    private JLabel rollThreeTotalLabel;
    private JLabel rollFourOneLabel;
    private JLabel rollFourTwoLabel;
    private JLabel rollFourThreeLabel;
    private JLabel rollFourFourLabel;
    private JLabel rollFourTotalLabel;
    private JLabel rollFiveOneLabel;
    private JLabel rollFiveTwoLabel;
    private JLabel rollFiveThreeLabel;
    private JLabel rollFiveFourLabel;
    private JLabel rollFiveTotalLabel;
    private JLabel rollSixOneLabel;
    private JLabel rollSixTwoLabel;
    private JLabel rollSixThreeLabel;
    private JLabel rollSixFourLabel;
    private JLabel rollSixTotalLabel;

    private int rollCount = 0;
    List<Rolls> allRolls = new ArrayList<>();
    List<Integer> finalRolls = new ArrayList<>();

    RollGUI() {
        this.setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Allows form to pop up in center of the screen.
        setVisible(true);
        setSize(375, 275);
        generateCharacterSheetsButton.setEnabled(false);

        rollButton.addActionListener(event -> {
            roll();
        });

        generateCharacterSheetsButton.addActionListener(event -> {
            getFinalRolls();
            PlayerSheetGUI playerSheetGUI = new PlayerSheetGUI(finalRolls);
            this.dispose();
        });
    }

    private void getFinalRolls() {
        for (Rolls roll : allRolls) {
            finalRolls.add(roll.getTotal());
        }
    }

    private void roll() {
        roll4d6();
        switchRollLabels(rollCount, allRolls.get(rollCount));
        updateRollButton();
    }

    private void switchRollLabels(int count, Rolls roll) {
        switch (count) {
            case 0:
                updateRollLabels(rollOneOneLabel, rollOneTwoLabel, rollOneThreeLabel, rollOneFourLabel, rollOneTotalLabel, roll);
                break;
            case 1:
                updateRollLabels(rollTwoOneLabel, rollTwoTwoLabel, rollTwoThreeLabel, rollTwoFourLabel, rollTwoTotalLabel, roll);
                break;
            case 2:
                updateRollLabels(rollThreeOneLabel, rollThreeTwoLabel, rollThreeThreeLabel, rollThreeFourLabel, rollThreeTotalLabel, roll);
                break;
            case 3:
                updateRollLabels(rollFourOneLabel, rollFourTwoLabel, rollFourThreeLabel, rollFourFourLabel, rollFourTotalLabel, roll);
                break;
            case 4:
                updateRollLabels(rollFiveOneLabel, rollFiveTwoLabel, rollFiveThreeLabel, rollFiveFourLabel, rollFiveTotalLabel, roll);
                break;
            case 5:
                updateRollLabels(rollSixOneLabel, rollSixTwoLabel, rollSixThreeLabel, rollSixFourLabel, rollSixTotalLabel, roll);
                break;
        }
    }

    private void roll4d6() {
        Random rnd = new Random();
        int first = rnd.nextInt(6) + 1;
        int second = rnd.nextInt(6) + 1;
        int third = rnd.nextInt(6) + 1;
        int fourth = rnd.nextInt(6) + 1;

        List<Integer> tempRolls = new ArrayList<>();
        tempRolls.add(first);
        tempRolls.add(second);
        tempRolls.add(third);
        tempRolls.add(fourth);
        Collections.sort(tempRolls);

        int totalRolls = 0;
        for (int i = tempRolls.size(); i > 1; i--) {
            int roll = tempRolls.get(i - 1);
            totalRolls += roll;
        }

        Rolls rolls = new Rolls(tempRolls.get(3), tempRolls.get(2), tempRolls.get(1), tempRolls.get(0), totalRolls);
        allRolls.add(rolls);
    }

    private void updateRollLabels(JLabel first, JLabel second, JLabel third, JLabel fourth, JLabel total, Rolls rolls) {
        first.setText(Integer.toString(rolls.getFirst()));
        second.setText(Integer.toString(rolls.getSecond()));
        third.setText(Integer.toString(rolls.getThird()));
        fourth.setText(Integer.toString(rolls.getFourth()));
        total.setText("Total: " + rolls.getTotal());
    }

    private void updateRollButton() {
        rollCount++;
        if (rollCount < 6) {
            rollButton.setText("Roll #" + (rollCount + 1));
        } else {
            int totals = getTotals();
            if (totals <= 67) {
                rollButton.setText("Rolls too low. Roll again.");
                Rolls roll = new Rolls(0,0,0,0,0);
                for (int i = 0; i < 6; i++) {
                    switchRollLabels(i, roll);
                }
                rollCount = 0;
                allRolls.clear();
            } else {
                rollButton.setText("All rolls finished.");
                rollButton.setEnabled(false);
                generateCharacterSheetsButton.setEnabled(true);
            }
        }
    }

    private int getTotals() {
        int totalRolls = 0;
        for (Rolls roll : allRolls) {
            totalRolls += roll.getTotal();
        }

        return totalRolls;
    }
}
