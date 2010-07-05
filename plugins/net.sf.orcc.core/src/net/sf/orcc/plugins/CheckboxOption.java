package net.sf.orcc.plugins;

import java.util.List;

public interface CheckboxOption extends PluginOption {

	/**
	 * Returns the options that are enabled when this checkbox is.
	 * 
	 * @return the options that are enabled when this checkbox is
	 */
	List<PluginOption> getOptions();

	/**
	 * Sets the options that are enabled when this checkbox is.
	 * 
	 * @param options
	 *            the options that are enabled when this checkbox is
	 */
	void setOptions(List<PluginOption> options);
}
