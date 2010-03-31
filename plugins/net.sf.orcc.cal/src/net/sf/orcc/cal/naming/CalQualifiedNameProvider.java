package net.sf.orcc.cal.naming;

import java.util.Iterator;

import net.sf.orcc.cal.cal.Action;
import net.sf.orcc.cal.cal.Tag;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;

public class CalQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {

	public String qualifiedName(Action action) {
		Tag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			return getQualifiedName(tag);
		}
	}

	public String qualifiedName(Tag tag) {
		Iterator<String> it = tag.getIdentifiers().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				// builder.append('.');
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

}
