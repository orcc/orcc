// Generated from "blkexp"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *blkexp_RUN;
extern struct fifo_s *blkexp_VALUE;
extern struct fifo_s *blkexp_LAST;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *blkexp_OUT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SAMPLE_COUNT_SZ = 8;
static int SAMPLE_SZ = 13;
static int BLOCK_SIZE = 64;
static char count = 0;
static char run = -1;
static short next_value;
static int last = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void done() {
  count = 0;
  run = -1;
  last = 0;
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 == 64;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void write_value() {
  short *OUT;
  short _tmp1_1;
  
  OUT = getWritePtr(blkexp_OUT, 1);
  run = -1;
  count++;
  _tmp1_1 = next_value;
  OUT[0] = _tmp1_1;
}

static int isSchedulable_write_value() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = run;
  _tmp0_1 = _tmp1_1 == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void write_zero() {
  short *OUT;
  
  OUT = getWritePtr(blkexp_OUT, 1);
  run--;
  count++;
  OUT[0] = 0;
}

static int isSchedulable_write_zero() {
  char _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = run;
  _tmp2_1 = last;
  _tmp0_1 = _tmp1_1 > 0 || _tmp2_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void read_immediate() {
  short *OUT;
  char *RUN;
  short *VALUE;
  int *LAST;
  short v_1;
  int l_1;
  
  OUT = getWritePtr(blkexp_OUT, 1);
  RUN = getReadPtr(blkexp_RUN, 1);
  VALUE = getReadPtr(blkexp_VALUE, 1);
  v_1 = VALUE[0];
  LAST = getReadPtr(blkexp_LAST, 1);
  l_1 = LAST[0];
  last = l_1;
  count++;
  OUT[0] = v_1;
}

static int isSchedulable_read_immediate() {
  int *LAST;
  short *VALUE;
  char *RUN;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  char r_1;
  char _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(blkexp_RUN, 1);
  _tmp2_1 = hasTokens(blkexp_VALUE, 1);
  _tmp3_1 = hasTokens(blkexp_LAST, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1) {
    RUN = getPeekPtr(blkexp_RUN, 1);
    r_1 = RUN[0];
    VALUE = getPeekPtr(blkexp_VALUE, 1);
    LAST = getPeekPtr(blkexp_LAST, 1);
    _tmp4_1 = count;
    _tmp0_1 = r_1 == 0 && _tmp4_1 != 64;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_save() {
  short *OUT;
  char *RUN;
  short *VALUE;
  int *LAST;
  char r_1;
  short v_1;
  int l_1;
  
  OUT = getWritePtr(blkexp_OUT, 1);
  RUN = getReadPtr(blkexp_RUN, 1);
  r_1 = RUN[0];
  VALUE = getReadPtr(blkexp_VALUE, 1);
  v_1 = VALUE[0];
  LAST = getReadPtr(blkexp_LAST, 1);
  l_1 = LAST[0];
  run = r_1 - 1;
  next_value = v_1;
  last = l_1;
  count++;
  OUT[0] = 0;
}

static int isSchedulable_read_save() {
  int *LAST;
  short *VALUE;
  char *RUN;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  char _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(blkexp_RUN, 1);
  _tmp2_1 = hasTokens(blkexp_VALUE, 1);
  _tmp3_1 = hasTokens(blkexp_LAST, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1) {
    RUN = getPeekPtr(blkexp_RUN, 1);
    VALUE = getPeekPtr(blkexp_VALUE, 1);
    LAST = getPeekPtr(blkexp_LAST, 1);
    _tmp4_1 = count;
    _tmp0_1 = _tmp4_1 != 64;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

int blkexp_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_done()) {
      done();
      res = 1;
    } else {
      if (isSchedulable_write_value()) {
        if (hasRoom(blkexp_OUT, 1)) {
          write_value();
          res = 1;
        } else {
          res = 0;
        }
      } else {
        if (isSchedulable_write_zero()) {
          if (hasRoom(blkexp_OUT, 1)) {
            write_zero();
            res = 1;
          } else {
            res = 0;
          }
        } else {
          if (isSchedulable_read_immediate()) {
            if (hasRoom(blkexp_OUT, 1)) {
              read_immediate();
              res = 1;
            } else {
              res = 0;
            }
          } else {
            if (isSchedulable_read_save()) {
              if (hasRoom(blkexp_OUT, 1)) {
                read_save();
                res = 1;
              } else {
                res = 0;
              }
            } else {
              res = 0;
            }
          }
        }
      }
    }
  }
  
  return 0;
}
