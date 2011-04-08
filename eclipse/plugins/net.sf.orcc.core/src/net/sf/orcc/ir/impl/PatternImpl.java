/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Pattern</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class PatternImpl extends EObjectImpl implements Pattern {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Var, Port> inverseVariableMap;

	private Map<Port, Integer> numTokensMap;

	private Map<Port, Var> peekedMap;

	private List<Port> ports;

	private Map<Port, Var> variableMap;

	protected PatternImpl() {
		super();
		ports = new ArrayList<Port>();
		numTokensMap = new LinkedHashMap<Port, Integer>();
		peekedMap = new LinkedHashMap<Port, Var>();
		variableMap = new LinkedHashMap<Port, Var>();
		inverseVariableMap = new LinkedHashMap<Var, Port>();
	}

	/**
	 * Checks if the given port is present in the {@link #ports} list, and adds
	 * it if necessary.
	 * 
	 * @param port
	 *            a port
	 */
	private void checkPortPresence(Port port) {
		if (!ports.contains(port)) {
			ports.add(port);
		}
	}

	/**
	 * Clears this pattern.
	 */
	public void clear() {
		ports.clear();
		numTokensMap.clear();
		peekedMap.clear();
		variableMap.clear();
		inverseVariableMap.clear();
	}

	/**
	 * Returns <code>true</code> if this pattern contains the given port.
	 * 
	 * @param port
	 *            a port
	 * @return <code>true</code> if this pattern contains the given port
	 */
	public boolean contains(Port port) {
		return ports.contains(port);
	}

	/**
	 * Returns <code>true</code> if this pattern contains the given variable.
	 * 
	 * @param var
	 *            a variable
	 * @return <code>true</code> if this pattern contains the given variable
	 */
	public boolean contains(Var var) {
		return inverseVariableMap.containsKey(var);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PatternImpl) {
			return ((PatternImpl) obj).getNumTokensMap().equals(numTokensMap);
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.PATTERN;
	}

	/**
	 * Returns the inverse variable map.
	 * 
	 * @return the inverse variable map
	 */
	public Map<Var, Port> getInverseVariableMap() {
		return inverseVariableMap;
	}

	/**
	 * Returns the number of tokens produced (or consumed) by the given port.
	 * 
	 * @return the number of tokens produced (or consumed) by the given port
	 */
	public Integer getNumTokens(Port port) {
		return numTokensMap.get(port);
	}

	/**
	 * Returns the number of tokens map.
	 * 
	 * @return the number of tokens map
	 */
	public Map<Port, Integer> getNumTokensMap() {
		return numTokensMap;
	}

	/**
	 * Returns the variable that contains the tokens peeked by the given port.
	 * May be <code>null</code> if the port is not peeked.
	 * 
	 * @return the variable that contains the tokens peeked by the given port
	 */
	public Var getPeeked(Port port) {
		return peekedMap.get(port);
	}

	/**
	 * Returns the peeked map.
	 * 
	 * @return the peeked map
	 */
	public Map<Port, Var> getPeekedMap() {
		return peekedMap;
	}

	/**
	 * Returns the ports of this pattern.
	 * 
	 * @return the ports of this pattern
	 */
	public List<Port> getPorts() {
		return ports;
	}

	/**
	 * Returns the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @return the variable that contains tokens produced (or consumed) by the
	 *         given port
	 */
	public Var getVariable(Port port) {
		return variableMap.get(port);
	}

	/**
	 * Returns the variable map.
	 * 
	 * @return the variable map
	 */
	public Map<Port, Var> getVariableMap() {
		return variableMap;
	}

	/**
	 * Returns <code>true</code> if this pattern is empty.
	 * 
	 * @return <code>true</code> if this pattern is emptyS
	 */
	public boolean isEmpty() {
		return ports.isEmpty();
	}

	/**
	 * Returns <code>true</code> if this pattern is a superset of the given
	 * pattern. This can be used to determine time-dependent behavior, which
	 * occurs when an action reads inputs not read by a higher-priority action.
	 * 
	 * @param other
	 *            another pattern
	 * @return <code>true</code> if this pattern is a subset of the given
	 *         pattern
	 */
	public boolean isSupersetOf(Pattern other) {
		if (this.getNumTokensMap().keySet()
				.containsAll(other.getNumTokensMap().keySet())) {
			// OK we read from at least the same ports as the other pattern

			// let's check the consumption
			for (Entry<Port, Integer> entry : other.getNumTokensMap()
					.entrySet()) {
				// if this pattern consumes less than the other pattern then
				// this pattern is not a superset
				if (this.getNumTokens(entry.getKey()) < entry.getValue()) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes the given port from this pattern.
	 * 
	 * @param port
	 *            a port
	 */
	public void remove(Port port) {
		ports.remove(port);
		numTokensMap.remove(port);
		Var peek = peekedMap.remove(port);
		Var var = variableMap.remove(port);

		// Remove peek and variable entry from inverseVariableMap
		inverseVariableMap.remove(peek);
		inverseVariableMap.remove(var);
	}

	/**
	 * Sets the number of tokens produced (or consumed) by the given port.
	 * 
	 * @param port
	 *            a port
	 * @param numTokens
	 *            number of tokens produced (or consumed) by the given port
	 */
	public void setNumTokens(Port port, int numTokens) {
		checkPortPresence(port);
		numTokensMap.put(port, numTokens);
	}

	/**
	 * Sets the variable in which tokens are peeked from the given port.
	 * 
	 * @param port
	 *            a port
	 * @param peeked
	 *            a variable that contains tokens peeked by the given port
	 */
	public void setPeeked(Port port, Var peeked) {
		checkPortPresence(port);
		peekedMap.put(port, peeked);
		inverseVariableMap.put(peeked, port);
	}

	/**
	 * Sets the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @param port
	 *            a port
	 * @param var
	 *            the variable that contains tokens produced (or consumed) by
	 *            the given port
	 */
	public void setVariable(Port port, Var var) {
		checkPortPresence(port);
		variableMap.put(port, var);
		inverseVariableMap.put(var, port);
	}

	@Override
	public String toString() {
		return numTokensMap.toString();
	}

	/**
	 * Update production/consumption of the pattern with the given pattern
	 * 
	 * @param pattern
	 *            : the reference pattern
	 */
	public void updatePattern(Pattern pattern) {
		for (Entry<Port, Integer> entry : pattern.getNumTokensMap().entrySet()) {
			// Get number of tokens comsuption/production
			Port port = entry.getKey();
			Integer numTokens = entry.getValue();

			if (contains(port)) {
				if (numTokens < getNumTokens(port)) {
					// Don't update pattern
					continue;
				}
			}

			// Update the pattern
			setNumTokens(port, numTokens);
		}
	}

} // PatternImpl
