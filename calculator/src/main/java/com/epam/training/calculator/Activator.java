package com.epam.training.calculator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Activator implements BundleActivator, Runnable {

    private JFrame calculator;
    private BundleContext context;

    public void start(final BundleContext bundleContext) throws Exception {
        context = bundleContext;
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            try {
                javax.swing.SwingUtilities.invokeAndWait(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
        final JFrame frame = calculator;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    public void run() {
        calculator = new CalculatorWindow();
        calculator.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        calculator.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    context.getBundle(0).stop();
                } catch (BundleException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
