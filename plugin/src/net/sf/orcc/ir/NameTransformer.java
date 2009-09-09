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

	public static Map<String, String> names = new HashMap<String, String>();

	public static String transform(String name) {
		if (names.containsKey(name)) {
			return names.get(name);
		} else {
			return name;
		}
	}

}
