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
@brief Description of the Rational class interface
@author Jerome Gorin
@file Merger.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef RATIONAL_H
#define RATIONAL_H

#include <iostream>
//------------------------------

/**
 * @brief  This class defines a Rational.  A rational represents a number that can be 
 *  expressed as a ratio of two integers
 * 
 * @author Jerome Gorin
 * 
 */
class Rational {
public:
  // default constructor

  /**
  *  @brief Default constructor of Rational.
  */
  Rational() : num(0), den(1) {}

  /**
  *  @brief Constructor of Rational with initial value.
  *
  *  @param n : numerator value
  *
  *  @param d : denominator value
  */
  Rational(long n, long d = 1);

  /**
  *  @brief Copy constructor of Rational.
  *
  *  @param rhs : the Rational to copy
  */
  Rational(const Rational& rhs) : num(rhs.num), den(rhs.den) {}

  /**
  *  @brief Destructor of Rational.
  */
  ~Rational() {}

  /**
  *  @brief Return the numerator of the rational.
  *
  *  @return the numerator of the Rational
  */
  long numerator() const { return num; }
  
  /**
  *  @brief Return the denominator of the rational.
  *
  *  @return the denominator of the Rational
  */
  long denominator() const { return den; }

  /**
  *  @brief Set the value of the rational.
  *
  *  @param rhs : value of the Rational
  */
  Rational& operator=(const Rational& rhs);
  Rational& operator=(long rhs);

  // unary operators
  Rational operator+(void) const { return *this; }
  Rational operator-(void) const { return Rational(-num, den); }
  Rational invert(void) const { return Rational(den, num); }

  // binary shortcut operators
  const Rational& operator+=(const Rational& rhs);
  const Rational& operator-=(const Rational& rhs);
  const Rational& operator*=(const Rational& rhs);
  const Rational& operator/=(const Rational& rhs);
  const Rational& operator+=(long rhs);
  const Rational& operator-=(long rhs);
  const Rational& operator*=(long rhs);
  const Rational& operator/=(long rhs);
  
  // increment/decrement iterators
  const Rational& operator++();
  const Rational operator++(int);
  const Rational& operator--();
  const Rational operator--(int);

private:
  // Data
  long num;  // numerator
  long den;  // denominator (keep > 0!)

  // auxillary helper function to normalize the rationals
  long gcd(long, long);
};

// assignment operators
inline Rational& Rational::operator=(const Rational& rhs) {
  num = rhs.num;
  den = rhs.den;
  return *this;
}

inline Rational& Rational::operator=(long rhs) {
  num = rhs;
  den = 1;
  return *this;
}

// Rational -> double conversion
inline double toDouble (const Rational& r) {
  return double(r.numerator())/r.denominator();
}

// Rational -> long conversions
inline long trunc(const Rational& r) {
  return r.numerator() / r.denominator();
}

inline long floor(const Rational& r) {
  long q = r.numerator() / r.denominator();
  return (r.numerator() < 0 && r.denominator() != 1) ? --q : q;
}

inline long ceil(const Rational& r) {
  long q = r.numerator() / r.denominator();
  return (r.numerator() >= 0 && r.denominator() != 1) ? ++q : q;
}

// double -> Rational conversion
Rational toRational(double x, int iterations = 5);

// binary operators
const Rational operator+(const Rational& lhs, const Rational& rhs);
const Rational operator-(const Rational& lhs, const Rational& rhs);
const Rational operator*(const Rational& lhs, const Rational& rhs);
const Rational operator/(const Rational& lhs, const Rational& rhs);
Rational rabs(const Rational& rhs);

// boolean operators
bool operator==(const Rational& lhs, const Rational& rhs);
bool operator!=(const Rational& lhs, const Rational& rhs);
bool operator<=(const Rational& lhs, const Rational& rhs);
bool operator>=(const Rational& lhs, const Rational& rhs);
bool operator<(const Rational& lhs, const Rational& rhs);
bool operator>(const Rational& lhs, const Rational& rhs);

// output operator
std::ostream& operator<< (std::ostream& ostr, const Rational& r);
std::istream& operator>> (std::istream& istr, Rational& r);

#endif