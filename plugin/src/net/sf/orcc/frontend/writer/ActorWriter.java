package net.sf.orcc.frontend.writer;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.RVCCalASTParser;
import net.sf.orcc.ir.actor.Actor;

public class ActorWriter {

	private Actor actor;

	/**
	 * Creates an actor writer on the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public ActorWriter(Actor actor) {
		this.actor = actor;
	}

	public void write(String outputDir) throws OrccException {
		actor.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OrccException {
		Actor actor = new RVCCalASTParser(args[0]).parse();
		new ActorWriter(actor).write(args[1]);
	}

}
