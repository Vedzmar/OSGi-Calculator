package com.dzianis.eductation;


import com.epam.training.ExpressionHelper;
import com.epam.training.expressions.ExpressionException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CalculatorWindow extends JFrame {

    private final JPanel panel;
    private final JTextField expressonText;
    private final JLabel resultLabel;
    private final JLabel errorLabel;

    CalculatorWindow(){
        super("Calculator");
        this.setSize(420, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Enter expression here");
        userLabel.setBounds(10, 10, 130, 25);
        panel.add(userLabel);

        resultLabel = new JLabel();
        resultLabel.setVisible(false);
        resultLabel.setBounds(360, 10, 30, 25);
        panel.add(resultLabel);

        expressonText = new JTextField(20);
        expressonText.setBounds(150, 10, 210, 25);
        panel.add(expressonText);

        errorLabel = new JLabel();
        errorLabel.setVisible(false);
        errorLabel.setForeground(Color.red);
        errorLabel.setBounds(10, 30, 410, 45);
        panel.add(errorLabel);

        JButton registerButton = new JButton("calculate");
        registerButton.setBounds(210, 80, 180, 25);
        registerButton.addActionListener(this.onClick);
        panel.add(registerButton);

        this.add(panel);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    private ActionListener onClick = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            String expression = CalculatorWindow.this.expressonText.getText().trim();
            if (expression.isEmpty()) return;
            try {
                long result = ExpressionHelper.createExpression(expression).calculate();
                CalculatorWindow.this.showResult(result);
            } catch (ExpressionException e){
                CalculatorWindow.this.showError(e.getMessage());
            }
        }
    };

    private void showError(String message) {
        this.errorLabel.setVisible(true);
        this.resultLabel.setVisible(false);
        this.errorLabel.setText(message);
    }

    private void showResult(long result) {
        this.errorLabel.setVisible(false);
        this.resultLabel.setVisible(true);
        this.resultLabel.setText(String.format(" = %s", result) );
    }
}
