/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.features;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;

/**
 * <p>
 * This class should be used if for some reason a CustomFeature can be long to
 * execute. It runs {@link #execute(ICustomContext, IProgressMonitor)} in a
 * Command on top of current TransactionalEditingDomain. This command itself is
 * run in a Job, and can use the associated IProgressMonitor
 * </p>
 * <p>
 * This is useful to indicate user the job is running, but eclipse is not
 * crashing
 * </p>
 * 
 * @author Antoine Lorence
 * 
 */
public abstract class AbstractTimeConsumingCustomFeature extends
		AbstractCustomFeature {

	public AbstractTimeConsumingCustomFeature(IFeatureProvider fp) {
		super(fp);
	}

	/**
	 * Concrete code to execute. Sub-classes should use correctly given monitor:
	 * create tasks (and eventually sub-tasks), notify for worked and done tasks
	 * and check if user cancelled the task.
	 * 
	 * @param context
	 *            The CustomFeature context
	 * @param monitor
	 *            The monitor used to manage progress bar and Job cancellation
	 * @return The execution status
	 */
	protected abstract void execute(ICustomContext context,
			IProgressMonitor monitor);

	/**
	 * Callback executed just before job scheduling, in the Feature execution
	 * Thread. Default implementation is empty.
	 */
	protected void beforeJobExecution() {
	}

	/**
	 * Callback launched immediately after job execution in the Job Thread.
	 * Default implementation is empty.
	 */
	protected void afterJobExecution() {
	}

	/**
	 * Initialize the Job.
	 * 
	 * @param context
	 *            The CustomContext that will be given to
	 *            {@link #execute(ICustomContext, IProgressMonitor)}.
	 * @return The Job instance
	 */
	protected Job initializeJob(final ICustomContext context) {
		return new Job(getName()) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {

				final TransactionalEditingDomain editDomain = TransactionUtil
						.getEditingDomain(getDiagram());

				final RecordingCommand command = new RecordingCommand(
						editDomain, getName()) {

					private IStatus result = null;

					@Override
					protected void doExecute() {
						try {
							AbstractTimeConsumingCustomFeature.this.execute(
									context, monitor);
							result = Status.OK_STATUS;
						} catch (OperationCanceledException e) {
							result = Status.CANCEL_STATUS;
						}
					}

					@Override
					public Collection<?> getResult() {
						return result == null ? Collections.EMPTY_LIST
								: Collections.singletonList(result);
					}
				};

				// Execute (synchrnously) the defined command in a proper EMF
				// transaction
				editDomain.getCommandStack().execute(command);

				// Update the diagram dirtiness state
				getDiagramBehavior().getDiagramContainer().updateDirtyState();

				// Callback
				afterJobExecution();

				return (IStatus) command.getResult().iterator().next();
			}
		};
	}

	/**
	 * Initialize parameters of the given Job
	 * 
	 * @param job
	 *            The Job instance to configure
	 */
	protected void configureJob(Job job) {
		job.setUser(true);
		job.setPriority(Job.LONG);
	}

	// Prevent sub-classes from overriding this method
	@Override
	final public void execute(IContext context) {
		super.execute(context);
	}

	// Prevent sub-classes from overriding this method
	@Override
	final public void execute(ICustomContext context) {

		final Job job = initializeJob(context);
		configureJob(job);

		// Callback
		beforeJobExecution();

		// Job is run
		job.schedule();
	}

	// Prevent sub-classes from overriding this method
	@Override
	final public boolean hasDoneChanges() {
		return false;
	}
}
