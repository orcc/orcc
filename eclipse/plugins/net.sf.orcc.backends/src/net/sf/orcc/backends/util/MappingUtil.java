package net.sf.orcc.backends.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

/**
 * @author Herve Yviquel
 * 
 */
public class MappingUtil {

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

}
