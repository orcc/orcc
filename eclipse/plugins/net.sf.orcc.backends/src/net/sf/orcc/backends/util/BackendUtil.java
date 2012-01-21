package net.sf.orcc.backends.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

/**
 * @author Herve Yviquel
 * 
 */
public class BackendUtil {

	public static void computeMapping(Network network,
			Map<String, String> mapping,
			Map<String, List<Instance>> targetToInstancesMap,
			List<Instance> unmappedInstances) {
		for (Instance instance : network.getInstances()) {
			String name = new String();
			if (instance.isActor()) {
				name = instance.getHierarchicalName();
			} else if (instance.isBroadcast()) {
				// Broadcasts are mapped with their source
				name = ((Instance) instance.getBroadcast().getPredecessors()
						.get(0)).getHierarchicalName();
			}

			// Map each instance to its associate component
			String component = mapping.get(name);
			if (component != null) {
				List<Instance> list = targetToInstancesMap.get(component);
				if (list == null) {
					list = new ArrayList<Instance>();
					targetToInstancesMap.put(component, list);
				}
				list.add(instance);
			} else {
				unmappedInstances.add(instance);
			}
		}
	}

	public static void startExec(final AbstractBackend executingBackend, String[] cmd)
			throws IOException {
		Runtime run = Runtime.getRuntime();
		final Process process = run.exec(cmd);

		// Output error message
		new Thread() {
			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					try {
						String line = reader.readLine();
						if (line != null) {
							executingBackend.write("Generation error :" + line + "\n");
						}
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
