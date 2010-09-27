package com.yobny.opensource.osgi.karafview.sample.bundlea;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import com.yobny.opensource.osgi.karafview.sample.bundleb.ISampleService;
import com.yobny.opensource.osgi.karafview.sample.bundleb.SampleService;

public class BundleA implements BundleActivator, ServiceListener {

	@Override
	public void start(BundleContext arg0) throws Exception {
		arg0.registerService(ISampleService.class.getName(), new SampleService(),new Properties()); 
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serviceChanged(ServiceEvent arg0) {
		if (arg0.getType() == ServiceEvent.REGISTERED) {
			System.out.println("ISampleService registered");
		}
		
	}

}
