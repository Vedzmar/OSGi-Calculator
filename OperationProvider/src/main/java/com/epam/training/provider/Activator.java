package com.epam.training.provider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

public class Activator implements BundleActivator {
    public void start(BundleContext ctx) throws Exception {
        ctx.addBundleListener(onBundleEvent);
    }

    public void stop(BundleContext ctx) throws Exception {
        ctx.removeBundleListener(onBundleEvent);
    }

    private SynchronousBundleListener onBundleEvent = new SynchronousBundleListener() {
        public void bundleChanged(BundleEvent event) {
            String type = null;
            switch (event.getType()){
                case BundleEvent.STARTING : type = "STARTING"; break;
                case BundleEvent.STOPPING : type = "STOPPING"; break;
            }

            if (type == null) return;
            System.out.printf("Bundle %s is %s\n", event.getBundle().getSymbolicName(), type);
        }
    };
}
