/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

/**
@brief Implementation of class SuperInstance
@author Jerome Gorin
@file SuperInstance.cpp
@version 1.0
@date 24/12/2010
*/

//------------------------------
#include <map>
#include <list>
#include <math.h>
#include <sstream>
#include <cstdlib>

#include "Rational.h"
//------------------------------

using namespace std;


Rational::Rational(long n, long d) {
  if (d == 0L) {
    cerr << "Division by Zero" << endl;
    exit(1);
  }
  if (d < 0L) { n = -n; d = -d; }
  if (n == 0L) {
    num = 0L;   den = 1L;
  } else {
    long g = gcd(n, d);
    num = n/g; den = d/g;
  }
}

const Rational& Rational::operator+=(const Rational& rhs) {
  long g1 = gcd(den, rhs.den);
  if (g1 == 1L) {  // 61% probability!
    num = num*rhs.den + den*rhs.num;
    den = den*rhs.den;
  } else {
    long t = num * (rhs.den/g1) + (den/g1)*rhs.num;
    long g2 = gcd(t, g1);
    num = t/g2;
    den = (den/g1) * (rhs.den/g2);
  }
  return *this;
}
 
const Rational& Rational::operator+=(long rhs) {
  num = num + den*rhs;
  return *this;
}

const Rational& Rational::operator-=(const Rational& rhs) {
  long g1 = gcd(den, rhs.den);
  if (g1 == 1L) {  
    num = num*rhs.den - den*rhs.num;
    den = den*rhs.den;
  } else {
    long t = num * (rhs.den/g1) - (den/g1)*rhs.num;
    long g2 = gcd(t, g1);
    num = t/g2;
    den = (den/g1) * (rhs.den/g2);
  }
  return *this;
}

const Rational& Rational::operator-=(long rhs) {
  num = num - den*rhs;
  return *this;
}

const Rational& Rational::operator*=(const Rational& rhs) {
  long g1 = gcd(num, rhs.den);
  long g2 = gcd(den, rhs.num);
  num = (num/g1) * (rhs.num/g2);
  den = (den/g2) * (rhs.den/g1);
  return *this;
}

const Rational& Rational::operator*=(long rhs) {
  long g = gcd(den, rhs);
  num *= rhs/g;
  den /= g;
  return *this;
}

const Rational& Rational::operator/=(const Rational& rhs) {
  if (rhs == 0) {
    cerr << "Division by Zero" << endl;
    exit(1);
  }
  long g1 = gcd(num, rhs.num);
  long g2 = gcd(den, rhs.den);
  num = (num/g1) * (rhs.den/g2);
  den = (den/g2) * (rhs.num/g1);
  if (den < 0L) { num = -num; den = -den; }
  return *this;
}

const Rational& Rational::operator/=(long rhs) {
  if (rhs == 0L) {
    cerr << "Division by Zero" << endl;
    exit(1);
  }
  long g = gcd(num, rhs);
  num /= g;
  den *= rhs/g;
  if (den < 0L) { num = -num; den = -den; }
  return *this;
}


const Rational& Rational::operator++() {
  return (*this += 1);
}

const Rational Rational::operator++(int) {
  Rational oldVal = *this;
  ++(*this);
  return oldVal;
}

const Rational& Rational::operator--() {
  return (*this -= 1);
}

const Rational Rational::operator--(int) {
  Rational oldVal = *this;
  --(*this);
  return oldVal;
}

long Rational::gcd(long u, long v) {
  long a = labs(u);
  long b = labs(v);
  long tmp;
  
  if (b > a) {
    tmp = a; a = b; b = tmp;
  }
  for(;;) {
    if (b == 0L)
      return a;
    else if (b == 1L)
      return b;
    else {
      tmp = b; b = a % b; a = tmp;
    }
  }
}

const Rational operator+(const Rational& lhs, const Rational& rhs) {
  return Rational(lhs) += rhs;
}

const Rational operator-(const Rational& lhs, const Rational& rhs) {
  return Rational(lhs) -= rhs;
}

const Rational operator*(const Rational& lhs, const Rational& rhs) {
  return Rational(lhs) *= rhs;
}

const Rational operator/(const Rational& lhs, const Rational& rhs) {
  return Rational(lhs) /= rhs;
}

Rational rabs(const Rational& r) {
  if (r.numerator() < 0) return -r; else return r;
}

bool operator==(const Rational& lhs, const Rational& rhs) {
  return (lhs.numerator() == rhs.numerator() &&
          lhs.denominator() == rhs.denominator());
}

bool operator!=(const Rational& lhs, const Rational& rhs) {
  return (lhs.numerator() != rhs.numerator() ||
          lhs.denominator() != rhs.denominator());
}

bool operator<(const Rational& lhs, const Rational& rhs) {
  return (toDouble(lhs) < toDouble(rhs));
}

bool operator>(const Rational& lhs, const Rational& rhs) {
  return (toDouble(lhs) > toDouble(rhs));
}

bool operator<=(const Rational& lhs, const Rational& rhs) {
  return ((lhs < rhs) || (lhs == rhs));
}

bool operator>=(const Rational& lhs, const Rational& rhs) {
  return ((lhs > rhs) || (lhs == rhs));
}

ostream& operator<< (ostream& ostr, const Rational& r) {
  if (r.denominator() == 1L)
    ostr << r.numerator();
  else {
    ostringstream buf;
    buf.flags(ostr.flags());        
    buf.fill(ostr.fill());         

    buf << r.numerator() << "/" << r.denominator();

    ostr << buf.str();
  }
  return ostr;
}

istream& operator>> (istream& istr, Rational& r) {
  long n = 0, d = 1;

  istr >> n;
  if ( istr.peek() == '/' ) {
    istr.ignore(1);
    istr >> d;
  }
  if ( istr ) r = Rational(n,d);
  return istr;
}

static Rational toRational(double x, double limit, int iterations) {
  double intpart;
  double fractpart = modf(x, &intpart);
  double d = 1.0 / fractpart;
  long left = long(intpart);
  if ( d > limit || iterations == 0 ) {
    return Rational(left);
  } else {
    return Rational(left) + toRational(d, limit * 0.1, --iterations).invert();
  }
}

Rational toRational(double x, int iterations) {
    int sign = x < 0.0 ? -1 : 1;
    return sign * toRational(sign * x, 1.0e12, iterations);
}
