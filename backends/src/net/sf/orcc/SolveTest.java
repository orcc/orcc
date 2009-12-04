package net.sf.orcc;

import jp.ac.kobe_u.cs.cream.DefaultSolver;
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import jp.ac.kobe_u.cs.cream.Solution;

public class SolveTest {

	public static void main(String[] args) {
		Network network = new Network();
		IntVariable unknown = new IntVariable(network, 0, 4095, "unknown");

		unknown.bitand(2048).equals(0);
		unknown.bitand(1024).equals(0);
		unknown.bitand(2).notEquals(0);

		unknown.add(0).equals(512 + 15);

		DefaultSolver solver = new DefaultSolver(network);
		solver.setChoice(DefaultSolver.RANDOM);
		Solution solution = solver.findFirst();
		if (solution == null) {
			System.out.println("no solution");
		} else {
			int x = solution.getIntValue(unknown);
			System.out.println(x);
		}
	}

}
