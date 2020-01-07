package net.sf.orcc.cal.generator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.GeneratorContext;

public class CalGeneratorContext extends GeneratorContext {
	
	private IProject project;
	private ResourceSet rs;
	
	public CalGeneratorContext(IProject project, ResourceSet rs) {
		this.project = project;
		this.rs = rs;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public ResourceSet getResourceSet() {
		return rs;
	}
	
}