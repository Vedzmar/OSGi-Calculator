package com.epam.training.calculator;

import org.osgi.framework.*;

/**
 * Demonstrate how to safely write a bundle that commits suicide by stopping
 * itself.
 *
 * @author Peter Kriens
 *
 */
public class DepressedActivator extends Thread implements BundleActivator {
    BundleContext context;
    boolean       quit;

    public void start(BundleContext context) throws Exception {
        this.context = context;
        start();
    }

    public synchronized void stop(BundleContext context)
            throws Exception {
        quit = true;
        interrupt();
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(10 - i);
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        safeStop();
    }

    public synchronized void safeStop() {
        if (quit) {
            // safeStop after early return
            return;
        }

        final Thread activator = this;
        new Thread() {
            public void run() {
                try {
                    try {
                        activator.join(10000);
                    } catch (InterruptedException e) {}
                    context.getBundle().stop();
                } catch (BundleException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
