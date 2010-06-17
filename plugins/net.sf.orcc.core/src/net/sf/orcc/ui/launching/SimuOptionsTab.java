/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.ui.launching;

import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_KEEP;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_TRACES;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.KEEP_INTERMEDIATE;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class SimuOptionsTab extends OptionsTab {

	private Button enableTraces;

	@Override
	protected void createGroup(Font font, Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(font);
		group.setText("&Options:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		debugMode = new Button(group, SWT.CHECK);
		createButton(font, debugMode, "Debug mode",
				"Activates debug mode: IR is pretty-printed, "
						+ "and actors are not cached.");
		keepIntermediate = new Button(group, SWT.CHECK);
		createButton(font, keepIntermediate, "Keep intermediate files",
				"When set, files will be printed after each step of "
						+ "code generation.");
		dotCfg = new Button(group, SWT.CHECK);
		createButton(font, dotCfg, "Print CFG information",
				"Prints DOT files showing CFG information.");
		enableTraces = new Button(group, SWT.CHECK);
		createButton(font, enableTraces, "Enable traces",
				"Activates traces when simulating.");
		createFifoSize(font, group);
	}

	@Override
	public Image getImage() {
		return OrccActivator.getImage("icons/orcc_simu.gif");
	}

	@Override
	public String getName() {
		return "Simulation options";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			boolean selected = configuration.getAttribute(DEBUG_MODE,
					DEFAULT_DEBUG);
			debugMode.setSelection(selected);

			selected = configuration.getAttribute(KEEP_INTERMEDIATE,
					DEFAULT_KEEP);
			keepIntermediate.setSelection(selected);

			selected = configuration.getAttribute(DOT_CFG, DEFAULT_DOT_CFG);
			dotCfg.setSelection(selected);

			selected = configuration
					.getAttribute(ENABLE_TRACES, DEFAULT_TRACES);
			enableTraces.setSelection(selected);

			int size = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
			fifoSize.setText(Integer.toString(size));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (isValid(configuration)) {
			boolean selected = debugMode.getSelection();
			configuration.setAttribute(DEBUG_MODE, selected);

			selected = keepIntermediate.getSelection();
			configuration.setAttribute(KEEP_INTERMEDIATE, selected);

			selected = dotCfg.getSelection();
			configuration.setAttribute(DOT_CFG, selected);

			selected = enableTraces.getSelection();
			configuration.setAttribute(ENABLE_TRACES, selected);

			String text = fifoSize.getText();
			configuration.setAttribute(FIFO_SIZE, Integer.parseInt(text));
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DEBUG_MODE, DEFAULT_DEBUG);
		configuration.setAttribute(KEEP_INTERMEDIATE, DEFAULT_KEEP);
		configuration.setAttribute(DOT_CFG, DEFAULT_DOT_CFG);
		configuration.setAttribute(ENABLE_TRACES, DEFAULT_TRACES);
		configuration.setAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
	}

}
