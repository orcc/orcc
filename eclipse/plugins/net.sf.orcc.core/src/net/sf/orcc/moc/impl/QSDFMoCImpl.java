/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Port;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.QSDFMoC;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>QSDF Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class QSDFMoCImpl extends MoCImpl implements QSDFMoC {

	private Map<Action, MoC> configurations;
	private List<Port> configurationPorts;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected QSDFMoCImpl() {
		super();
		configurations = new LinkedHashMap<Action, MoC>();
		configurationPorts = new ArrayList<Port>();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MocPackage.Literals.QSDF_MO_C;
	}

	@Override
	public Set<Action> getActions() {
		return configurations.keySet();
	}

	@Override
	public String getShortName() {
		return "QSDF";
	}

	@Override
	public List<Port> getConfigurationPorts() {
		return configurationPorts;
	}

	@Override
	public Map<Action, MoC> getConfigurations() {
		return configurations;
	}

	@Override
	public MoC getMoC(Action action) {
		return configurations.get(action);
	}

	@Override
	public boolean isQuasiStatic() {
		return true;
	}

	@Override
	public void setMoC(Action action, MoC moc) {
		configurations.put(action, moc);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QSDF configurations: [");
		for (Entry<Action, MoC> entry : configurations.entrySet()) {
			builder.append(entry.getKey().getName());
			builder.append(": ");
			builder.append(entry.getValue());
			builder.append(" ");
		}
		builder.append("]");
		return builder.toString();
	}

} // QSDFMoCImpl
