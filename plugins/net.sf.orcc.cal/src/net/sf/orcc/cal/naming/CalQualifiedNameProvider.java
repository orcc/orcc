package net.sf.orcc.cal.naming;

import java.util.Iterator;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstForeachStatement;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstTag;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;

public class CalQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {
	
	public String qualifiedName(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return "(untagged)" + action.hashCode();
		} else {
			return getQualifiedName(tag);
		}
	}

	public String qualifiedName(AstForeachStatement foreach) {
		return "foreach." + foreach.hashCode();
	}

	public String qualifiedName(AstGenerator generator) {
		return "generator." + generator.hashCode();
	}

	public String qualifiedName(AstTag tag) {
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
