package net.sf.orcc.cal.naming;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.orcc.cal.cal.Action;
import net.sf.orcc.cal.cal.ForeachStatement;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Tag;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;

public class CalQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {

	private Map<ForeachStatement, Integer> foreachCount = new HashMap<ForeachStatement, Integer>();

	private Map<Generator, Integer> generatorCount = new HashMap<Generator, Integer>();

	public String qualifiedName(Action action) {
		Tag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			return getQualifiedName(tag);
		}
	}

	public String qualifiedName(ForeachStatement statement) {
		Integer count = foreachCount.get(statement);
		if (count == null) {
			foreachCount.put(statement, 0);
			count = 0;
		} else {
			count++;
			foreachCount.put(statement, count);
		}
		
		return "foreach." + count;
	}

	public String qualifiedName(Generator generator) {
		Integer count = generatorCount.get(generator);
		if (count == null) {
			generatorCount.put(generator, 0);
			count = 0;
		} else {
			count++;
			generatorCount.put(generator, count);
		}
		
		return "generator." + count;
	}

	public String qualifiedName(Tag tag) {
		Iterator<String> it = tag.getIdentifiers().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append('.');
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

}
