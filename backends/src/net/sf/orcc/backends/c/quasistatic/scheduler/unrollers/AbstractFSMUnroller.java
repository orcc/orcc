package net.sf.orcc.backends.c.quasistatic.scheduler.unrollers;

import java.util.List;

import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.Graph;


public interface AbstractFSMUnroller {
	
	public List<Graph> unroll() throws QuasiStaticSchedulerException;
	
}
