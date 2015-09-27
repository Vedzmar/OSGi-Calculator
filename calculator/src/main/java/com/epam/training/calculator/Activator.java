package com.epam.training.calculator;

import com.epam.training.helper.OperationHolder;
import com.epam.training.helper.operations.Operation;
import org.osgi.framework.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Dictionary;


public class Activator implements BundleActivator, Runnable {

    private static final String OPERATION_KEY = "operationClass";
    private CalculatorWindow calculator;
    private BundleContext context;
    private OperationHolder operationHolder;

    public void start(final BundleContext bundleContext) throws Exception {
        context = bundleContext;
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            try {
                SwingUtilities.invokeAndWait(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        context.addBundleListener(onBundleEvent);
        for (Bundle bundle : context.getBundles()){
            onBundleEvent.bundleChanged(
                    new BundleEvent(BundleEvent.STARTING, bundle)
            );
        }

    }

    public void stop(BundleContext bundleContext) throws Exception {
        final JFrame frame = calculator;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        context.removeBundleListener(onBundleEvent);
    }

    public void run() {
        calculator = new CalculatorWindow();
        operationHolder = calculator.getOperationHolder();

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

    private SynchronousBundleListener onBundleEvent = new SynchronousBundleListener() {
        public void bundleChanged(BundleEvent event) {

            switch (event.getType()){
                case BundleEvent.STARTING :
                    checkAndAddOperation(event.getBundle());
                    break;

                case BundleEvent.STOPPING :
                    checkAndRemoveOperation(event.getBundle());
                    break;
            }
        }
    };

    private void checkAndAddOperation(Bundle bundle) {
        Operation operation = getOperation(bundle);
        if (operation == null ) return;
        operationHolder.add(new PersistedOperation(operation));

    }

    private void checkAndRemoveOperation(Bundle bundle) {
        Operation operation = getOperation(bundle);
        if (operation == null ) return;
        operationHolder.remove( new PersistedOperation(operation) );
    }

    private Operation getOperation(Bundle bundle) {
        Dictionary<String, String> properties = bundle.getHeaders();
        String operationClass = properties.get(OPERATION_KEY);
        if (operationClass == null) return null;

        try {
            return  (Operation) bundle.loadClass(operationClass).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
