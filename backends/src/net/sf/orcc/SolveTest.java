package net.sf.orcc;

import jp.ac.kobe_u.cs.cream.DefaultSolver;
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import jp.ac.kobe_u.cs.cream.Solution;

public class SolveTest {

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();

		Solution solution = null;
		IntVariable unknown = null;

		for (int i = 0; i < 1; i++) {
			Network network = new Network();
			unknown = new IntVariable(network, -32768 * 16, 32767, "unknown");

			unknown.bitand(65536 * 16).equals(0); // >= 0
			unknown.bitand(2048).equals(0);
			unknown.bitand(1024).equals(0);
			unknown.bitand(2).notEquals(0);
			unknown.bitand(4).notEquals(0);
			unknown.bitand(512).notEquals(0);
			unknown.bitand(256).notEquals(0);

			// unknown.multiply(4).notEquals(8);

			DefaultSolver solver = new DefaultSolver(network);
			solver.setChoice(DefaultSolver.ENUM);
			solution = solver.findFirst();
		}

		if (solution == null) {
			System.out.println("no solution");
		} else {
			long t2 = System.currentTimeMillis();
			int x = solution.getIntValue(unknown);
			System.out.println(x);
			System.out.println("found in " + (t2 - t1) + " ms");
		}
	}

}
