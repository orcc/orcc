package net.sf.orcc.backends.c.quasistatic.scheduler.model.util;

import net.sf.orcc.OrccException;

public interface BtypeChangesListener {
	public void btypeHasChanged() throws OrccException;
}
