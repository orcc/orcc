package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

public class SDFEdge {
	protected SDFVertex target;
	protected SDFVertex source;
	protected int delay;
	
	public SDFEdge(SDFVertex source, SDFVertex target) {
		super();
		this.target = target;
		this.source = source;
	}
	
	public SDFVertex getTarget() {
		return target;
	}
	public void setTarget(SDFVertex target) {
		this.target = target;
	}
	public SDFVertex getSource() {
		return source;
	}
	public void setSource(SDFVertex source) {
		this.source = source;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof SDFEdge)) return false;
		SDFEdge other = (SDFEdge)o;
		
		return this.getSource().equals(other.getSource()) &&
			   this.getTarget().equals(other.getTarget());
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}
