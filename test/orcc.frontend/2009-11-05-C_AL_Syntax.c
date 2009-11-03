// PASS: syntactically correct C-AL actor

actor Actor (bool PARAM) (int<3> input1, int input2 ==> int<5> output1) {

  int<4> y; // state variable
  const int<4> x = 5; // state variable with constant value
  int z = 5; // state variable with initial value
    
  int f(int<3> g, int<3> h) {
    int<4> i;
    bool j;
    float k;

    return
      if (x > 3) g + h * i else if (x < 0) g - h / i else 0;
  }
  
  void proc(float m) {
    unsigned int<2> h;
    return h + m;
  }
    
  action a.a ( ==> ) {
    if (a == 5) {
      f();
    } else {
    }
  }
    
  action a.b ( ==> ) {
  }

}
