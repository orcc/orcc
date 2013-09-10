package net.sf.orcc.backends.promela.transform;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;

public class ControlTokenLinkModel {
	private Connection connection;
	private ControlTokenActorModel source, target;
	private boolean controlLink = false;

	public ControlTokenLinkModel(Connection c) {
		this.connection = c;
	}

	public Connection getConnection() {
		return connection;
	}

	public ControlTokenActorModel getSource() {
		return this.source;
	}

	public Port getSourcePort() {
		return this.connection.getSourcePort();
	}

	public ControlTokenActorModel getTarget() {
		return target;
	}

	public Port getTargetPort() {
		return this.connection.getTargetPort();
	}

	public boolean isControlLink() {
		return controlLink;
	}

	public void setControlLink(boolean carriesControl) {
		this.controlLink = carriesControl;
	}

	public void setSource(ControlTokenActorModel source) {
		this.source = source;
	}

	public void setTarget(ControlTokenActorModel target) {
		this.target = target;
	}
}
