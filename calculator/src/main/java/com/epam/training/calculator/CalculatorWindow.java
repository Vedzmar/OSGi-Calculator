package com.epam.training.calculator;


import com.epam.training.helper.ExpressionHelper;
import com.epam.training.helper.OperationHolder;
import com.epam.training.helper.expressions.ExpressionException;
import com.epam.training.helper.operations.Operation;
import org.osgi.framework.BundleException;
import org.osgi.service.component.ComponentContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CalculatorWindow extends JFrame {

    private final JPanel panel;
    private final JTextField expressonText;
    private final JLabel resultLabel;
    private final JLabel errorLabel;
    private boolean closing;

    private OperationHolder operationHolder;
    private ComponentContext context;

    public CalculatorWindow(){
        super("Calculator");
        this.setSize(420, 150);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

        this.operationHolder = ExpressionHelper.operationHolder;
        closing = false;
        this.addWindowListener(onClose);
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

    public OperationHolder getOperationHolder() {
        return this.operationHolder;
    }

    public void bindOperation(Operation operation){
        operationHolder.add(new PersistedOperation(operation));
    }

    public void unbindOperation(Operation operation){
        operationHolder.remove(new PersistedOperation(operation));
    }

    public void activate(ComponentContext context){
        this.context = context;

    }

    public void deactivate(ComponentContext context){
        if (!CalculatorWindow.this.closing) {
            CalculatorWindow.this.closing = true;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    CalculatorWindow.this.setVisible(false);
                    CalculatorWindow.this.dispose();
                }
            });
        }
    }
    WindowAdapter onClose = new WindowAdapter() {
        public void windowClosing(WindowEvent evt) {
            if (!CalculatorWindow.this.closing){
                CalculatorWindow.this.closing = true;
                try {
                    context.getBundleContext().getBundle(0).stop();
                } catch (BundleException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
