package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;

/**
 * This class should be removed.
 * It provides a temporary workaround for a problem in deep
 * copying of actors. The problem is that Patterns inside
 * actions are copied incorrectly; some patterns become 
 * duplicated. This class removes the duplicates.
 * 
 * @author Jani Boutellier
 * 
 */

public class DuplicateRemover {

	private class PatternEntry {
		public int hash;
		public ArrayList<Integer> indices;
	}
	
	private ArrayList<PatternEntry> patternList;
	
	public void remove(Actor actor) {
		for (Action action : actor.getActions()) {
			boolean done = false;
			while (!done) { 
				patternList = new ArrayList<PatternEntry>();
				addToPatternList(action.getInputPattern());
				done = deleteDuplicates(action.getInputPattern());
			}
			done = false;
			while (!done) { 
				patternList = new ArrayList<PatternEntry>();
				addToPatternList(action.getOutputPattern());
				done = deleteDuplicates(action.getOutputPattern());
			}
		}
	}

	private void addToPatternList(Pattern pattern) {
		for (int i = 0; i < pattern.getPorts().size(); i++) {
			Port port = pattern.getPorts().get(i);
			addPatternListEntry(port, i);
		}
	}
	
	private boolean deleteDuplicates(Pattern pattern) {
		for (int i = 0; i < patternList.size(); i++) {
			if (patternList.get(i).indices.size() > 1) {
				pattern.getPorts().remove(patternList.get(i).indices.get(1).intValue());
				return false;
			} 
		}
		return true;
	}
	
	private int findHashCode(int hashCode) {
		for (int i = 0; i < patternList.size(); i++) {
			if (patternList.get(i).hash == hashCode) {
				return i;
			}
		}
		return -1;
	}
	
	private void addPatternListEntry(Port port, int index) {
		int i;
		int hash;
		hash = port.hashCode();
		i = findHashCode(hash);
		if (i < 0) {
			PatternEntry patternEntry = new PatternEntry();
			patternEntry.hash = hash;
			patternEntry.indices = new ArrayList<Integer>();
			patternEntry.indices.add(new Integer(index));
			patternList.add(patternEntry);
		} else {
			PatternEntry patternEntry = patternList.get(i);
			patternEntry.indices.add(new Integer(index));
		}
	}
}
