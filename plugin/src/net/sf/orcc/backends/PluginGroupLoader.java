/**
 * 
 */
package net.sf.orcc.backends;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.orcc.ui.OrccActivator;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateGroupInterface;
import org.antlr.stringtemplate.StringTemplateGroupLoader;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

/**
 * @author mwipliez
 * 
 */
public class PluginGroupLoader implements StringTemplateGroupLoader {

	public PluginGroupLoader() {
		StringTemplateGroup.registerGroupLoader(this);
	}

	@Override
	public StringTemplateGroup loadGroup(String groupName) {
		return loadGroup(groupName, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public StringTemplateGroup loadGroup(String groupName, Class templateLexer,
			StringTemplateGroup superGroup) {
		StringTemplateGroup group = null;

		try {
			InputStream is;
			if (OrccActivator.getDefault() == null) {
				is = new FileInputStream("templates/" + groupName + ".stg");
			} else {
				Bundle bundle = OrccActivator.getDefault().getBundle();
				IPath file = new Path("templates/" + groupName + ".stg");
				is = FileLocator.openStream(bundle, file, false);
			}

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			group = new StringTemplateGroup(br, templateLexer, null, superGroup);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return group;
	}

	@Override
	public StringTemplateGroup loadGroup(String groupName,
			StringTemplateGroup superGroup) {
		return loadGroup(groupName, DefaultTemplateLexer.class, superGroup);
	}

	@Override
	public StringTemplateGroupInterface loadInterface(String interfaceName) {
		throw new UnsupportedOperationException();
	}

}
