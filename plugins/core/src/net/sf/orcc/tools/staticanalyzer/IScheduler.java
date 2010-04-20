package net.sf.orcc.tools.staticanalyzer;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Network;

public interface IScheduler {

	public Schedule schedule(Network network) throws OrccException;
}
