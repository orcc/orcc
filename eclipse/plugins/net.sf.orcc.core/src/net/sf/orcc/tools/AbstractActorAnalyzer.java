package net.sf.orcc.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRParser;

public abstract class AbstractActorAnalyzer implements ActorAnalyzer {

	/**
	 * 
	 * @param args
	 */
	public static void main(Class<? extends AbstractActorAnalyzer> clasz,
			String[] args) {
		if (args.length == 3) {
			String inputFile = args[0];
			String vtlFolder = args[1];
			String outputFolder = args[2];

			try {
				AbstractActorAnalyzer analyzer = clasz.newInstance();
				analyzer.setOutputFolder(outputFolder);
				analyzer.analyzeFunctionalUnit(null, inputFile, vtlFolder);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err.println("Usage: " + clasz.getSimpleName()
					+ " <input functionnal unit> <VTL folder> <output folder>");
		}
	}

	/**
	 * the process that launched this analyzer
	 */
	private OrccProcess process;

	/**
	 * Analyze the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract public void analyze(Actor actor) throws OrccException;

	@Override
	public void analyzeFunctionalUnit(OrccProcess process, String inputFile,
			String vtlFolder) throws OrccException {
		this.process = process;

		write("Parsing Actor...\n");

		Actor actor;

		File file = new File(vtlFolder + inputFile);
		if (!file.exists()) {
			throw new OrccException("Actor \"" + inputFile
					+ "\" not found! Did you compile the VTL?");
		}

		try {
			InputStream in = new FileInputStream(file);
			actor = new IRParser().parseActor(in);
		} catch (OrccException e) {
			throw new OrccException("Could not parse instance \"" + inputFile
					+ "\" because: " + e.getLocalizedMessage(), e);
		} catch (FileNotFoundException e) {
			throw new OrccException("Actor \"" + inputFile
					+ "\" not found! Did you compile the VTL?", e);
		}

		if (isCanceled()) {
			return;
		}

		doFunctionnalUnitAnalyzer(actor);
	}

	abstract protected void doFunctionnalUnitAnalyzer(Actor actor);

	/**
	 * Returns true if this process has been canceled.
	 * 
	 * @return true if this process has been canceled
	 */
	protected boolean isCanceled() {
		if (process == null) {
			return false;
		} else {
			return process.getProgressMonitor().isCanceled();
		}
	}

	/**
	 * Transforms the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract public void transform(Actor actor) throws OrccException;

	/**
	 * Writes the given text to the process's normal output.
	 * 
	 * @param text
	 *            a string
	 */
	final public void write(String text) {
		if (process != null) {
			process.write(text);
		}
	}

}
