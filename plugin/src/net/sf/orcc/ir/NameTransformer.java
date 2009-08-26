/**
 * 
 */
package net.sf.orcc.ir;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mwipliez
 * 
 */
public class NameTransformer {

	private static Map<String, String> names = new HashMap<String, String>();

	static {
		names.put("abs", "abs_");
		names.put("index", "index_");
		names.put("getw", "getw_");
	}

	public static String transform(String name) {
		if (names.containsKey(name)) {
			return names.get(name);
		} else {
			return name;
		}
	}

}
