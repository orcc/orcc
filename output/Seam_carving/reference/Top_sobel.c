// Generated from "Top_sobel"

#include <locale.h>
#include <stdio.h>
#include <stdlib.h>

#ifdef _WIN32
  #include <conio.h>
#else
  #include <termio.h>
#endif

#include "fifo.h"

#define SIZE 10000

////////////////////////////////////////////////////////////////////////////////
// FIFO allocation

static char array_0[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_0 = { sizeof(unsigned char), SIZE, array_0, 0, 0 };
static char array_1[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_1 = { sizeof(unsigned char), SIZE, array_1, 0, 0 };
static char array_2[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_2 = { sizeof(unsigned char), SIZE, array_2, 0, 0 };
static char array_3[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_3 = { sizeof(unsigned char), SIZE, array_3, 0, 0 };
static char array_4[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_4 = { sizeof(unsigned char), SIZE, array_4, 0, 0 };
static char array_5[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_5 = { sizeof(unsigned char), SIZE, array_5, 0, 0 };
static char array_6[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_6 = { sizeof(unsigned char), SIZE, array_6, 0, 0 };
static char array_7[SIZE * sizeof(unsigned char)];
static struct fifo_s fifo_7 = { sizeof(unsigned char), SIZE, array_7, 0, 0 };
static char array_8[1 * sizeof(unsigned short)];
static struct fifo_s fifo_8 = { sizeof(unsigned short), 1, array_8, 0, 0 };
static char array_9[1 * sizeof(unsigned short)];
static struct fifo_s fifo_9 = { sizeof(unsigned short), 1, array_9, 0, 0 };
static char array_10[SIZE * sizeof(unsigned short)];
static struct fifo_s fifo_10 = { sizeof(unsigned short), SIZE, array_10, 0, 0 };
static char array_11[SIZE * sizeof(unsigned short)];
static struct fifo_s fifo_11 = { sizeof(unsigned short), SIZE, array_11, 0, 0 };
static char array_12[SIZE * sizeof(unsigned short)];
static struct fifo_s fifo_12 = { sizeof(unsigned short), SIZE, array_12, 0, 0 };
static char array_13[1 * sizeof(unsigned short)];
static struct fifo_s fifo_13 = { sizeof(unsigned short), 1, array_13, 0, 0 };

////////////////////////////////////////////////////////////////////////////////
// FIFO pointer assignments

struct fifo_s *broadcast_img_read_height_output_0 = &fifo_12;
struct fifo_s *sobel_height = &fifo_12;
struct fifo_s *broadcast_img_read_height_output_1 = &fifo_13;
struct fifo_s *img_display_height = &fifo_13;
struct fifo_s *broadcast_img_read_width_output_0 = &fifo_9;
struct fifo_s *img_display_width = &fifo_9;
struct fifo_s *broadcast_img_read_width_output_1 = &fifo_10;
struct fifo_s *sobel_width = &fifo_10;
struct fifo_s *img_read_red = &fifo_5;
struct fifo_s *rgb2y_red = &fifo_5;
struct fifo_s *img_read_green = &fifo_6;
struct fifo_s *rgb2y_green = &fifo_6;
struct fifo_s *img_read_blue = &fifo_7;
struct fifo_s *rgb2y_blue = &fifo_7;
struct fifo_s *img_read_width = &fifo_8;
struct fifo_s *broadcast_img_read_width_input = &fifo_8;
struct fifo_s *img_read_height = &fifo_11;
struct fifo_s *broadcast_img_read_height_input = &fifo_11;
struct fifo_s *rgb2y_y = &fifo_3;
struct fifo_s *sobel_y = &fifo_3;
struct fifo_s *sobel_gradient = &fifo_4;
struct fifo_s *y2rgb_y = &fifo_4;
struct fifo_s *y2rgb_red = &fifo_0;
struct fifo_s *img_display_red = &fifo_0;
struct fifo_s *y2rgb_green = &fifo_1;
struct fifo_s *img_display_green = &fifo_1;
struct fifo_s *y2rgb_blue = &fifo_2;
struct fifo_s *img_display_blue = &fifo_2;

////////////////////////////////////////////////////////////////////////////////
// Broadcasts

int broadcast_img_read_height_scheduler() {
  unsigned short *tok_input;
  unsigned short *tok_output_0;
  unsigned short *tok_output_1;
  
  while (hasTokens(broadcast_img_read_height_input, 1) && hasRoom(broadcast_img_read_height_output_0, 1)
   && hasRoom(broadcast_img_read_height_output_1, 1)) {
    tok_input = getReadPtr(broadcast_img_read_height_input, 1);
    tok_output_0 = getWritePtr(broadcast_img_read_height_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_img_read_height_output_1, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
  }
  
  return 0;
}

int broadcast_img_read_width_scheduler() {
  unsigned short *tok_input;
  unsigned short *tok_output_0;
  unsigned short *tok_output_1;
  
  while (hasTokens(broadcast_img_read_width_input, 1) && hasRoom(broadcast_img_read_width_output_0, 1)
   && hasRoom(broadcast_img_read_width_output_1, 1)) {
    tok_input = getReadPtr(broadcast_img_read_width_input, 1);
    tok_output_0 = getWritePtr(broadcast_img_read_width_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_img_read_width_output_1, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
  }
  
  return 0;
}

////////////////////////////////////////////////////////////////////////////////
// Action schedulers

extern int img_display_scheduler();
extern int img_read_scheduler();
extern int rgb2y_scheduler();
extern int sobel_scheduler();
extern int y2rgb_scheduler();

////////////////////////////////////////////////////////////////////////////////
// Actor scheduler

static void scheduler() {
  while (1) {
    broadcast_img_read_height_scheduler();
    broadcast_img_read_width_scheduler();
    img_display_scheduler();
    img_read_scheduler();
    rgb2y_scheduler();
    sobel_scheduler();
    y2rgb_scheduler();
  }
}

////////////////////////////////////////////////////////////////////////////////

void pause() {
#ifndef _WIN32
  struct termios oldT, newT;
  char c;
#endif
  printf("Press a key to continue\n");

#ifdef _WIN32
  _getch();
#else
  ioctl(0, TCGETS, &oldT);
  newT.c_lflag &= ~ICANON; // one char @ a time
  ioctl(0, TCSETS, &newT); // set new terminal mode
  read(0, &c, 1); // read 1 char @ a time from stdin
  ioctl(0, TCSETS, &oldT); // restore previous terminal mode
#endif
}

extern void source_set_file_name(const char *file_name);

int main(int argc, char *argv[]) {
  if (argc < 2) {
    printf("No input file provided!\n");
    pause();
    return 1;
  }
  
  source_set_file_name(argv[1]);
  
  scheduler();
  
  printf("End of simulation! Press a key to continue\n");
  pause();
  return 0;
}

