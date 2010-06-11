package net.sf.orcc.util;

public class Rational implements Comparable<Rational> {
	private static Rational zero = new Rational(0, 1);

	public static int gcd(int m, int n) {
		if (m < 0)
			m = -m;
		if (n < 0)
			n = -n;
		if (0 == n) {
			return m;
		} else {
			return gcd(n, m % n);
		}
	}

	public static int lcm(int m, int n) {
		if (m < 0)
			m = -m;
		if (n < 0)
			n = -n;
		return m * (n / gcd(m, n));
	}

	private int denominator;

	private int numerator;

	public Rational(int numerator, int denominator) {

		int g = gcd(numerator, denominator);
		this.numerator = numerator / g;
		this.denominator = denominator / g;
		if (this.denominator < 0) {
			this.denominator = -this.denominator;
			this.numerator = -this.numerator;
		}
	}

	public int compareTo(Rational b) {
		Rational a = this;
		int lhs = a.numerator * b.denominator;
		int rhs = a.denominator * b.numerator;
		if (lhs < rhs)
			return -1;
		if (lhs > rhs)
			return +1;
		return 0;
	}

	// return a / b
	public Rational divides(Rational b) {
		return this.times(b.reciprocal());
	}

	public boolean equals(Object y) {
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		Rational b = (Rational) y;
		return compareTo(b) == 0;
	}

	public int getDenominator() {
		return denominator;
	}

	public int getNumerator() {
		return numerator;
	}

	// return a - b
	public Rational minus(Rational b) {
		return this.plus(b.negate());
	}

	// return -a
	public Rational negate() {
		return new Rational(-numerator, denominator);
	}

	public Rational plus(Rational b) {
		if (this.compareTo(zero) == 0) {
			return b;
		}
		if (b.compareTo(zero) == 0) {
			return this;
		}

		// Find gcd of numerators and denominators
		int f = gcd(numerator, b.numerator);
		int g = gcd(denominator, b.denominator);

		// add cross-product terms for numerator
		Rational s = new Rational((numerator / f) * (b.denominator / g)
				+ (b.numerator / f) * (denominator / g), lcm(denominator,
				b.denominator));

		// multiply back in
		s.numerator *= f;
		return s;
	}

	public Rational reciprocal() {
		return new Rational(denominator, numerator);
	}

	public Rational times(Rational b) {
		// reduce p1/q2 and p2/q1, then multiply, where a = p1/q1 and b = p2/q2
		Rational c = new Rational(numerator, b.denominator);
		Rational d = new Rational(b.numerator, denominator);
		return new Rational(c.numerator * d.numerator, c.denominator
				* d.denominator);
	}

	public String toString() {
		if (denominator == 1)
			return numerator + "";
		else
			return numerator + "/" + denominator;
	}
}
