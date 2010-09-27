package com.yobny.opensource.osgi.karafview.shell.commands;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

@Command(scope = "karafui", name = "listService", description = "List service")
public class GetServiceListCommand extends OsgiCommandSupport {

	@Override
	protected Object doExecute() throws Exception {
		Bundle[] bundles = getBundleContext().getBundles();
		ServiceReference[] refs = null;
		
		for (int bundleIdx = 0; bundleIdx < bundles.length; bundleIdx++) {
			refs = bundles[bundleIdx].getRegisteredServices();
			System.out.println("Bundle Id:" + bundleIdx);
			
			for (int refIdx = 0; (refs != null) && (refIdx < refs.length); refIdx++) {
				String[] objectClass = (String[]) refs[refIdx].getProperty("objectClass");
				System.out.println(refs[refIdx].getProperty("objectClass"));
				Bundle[] usingBundles = refs[refIdx].getUsingBundles();
				
				for (int Idx = 0; (usingBundles != null) && (Idx < usingBundles.length); Idx++) {
					System.out.println("Used By:" + usingBundles[Idx].getBundleId());
				}
				/*StringBuffer m_sb = new StringBuffer();
				for (int i = 0; i < objectClass.length; i++) {
					if (i != 0) {
						m_sb.append(", ");
					}
					m_sb.append(objectClass[i].toString());
				}
				System.out.println(m_sb.toString());*/
			}
		}
		return null;
	}
}
