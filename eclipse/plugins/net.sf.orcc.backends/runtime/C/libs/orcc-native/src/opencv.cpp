/*
 * Copyright (c) 2014 - 2017, Heriot-Watt University, Edinburgh
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
 *   * Neither the name of Heriot-Watt University nor the names of its
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

/*
 * Author: Rob Stewart, R.Stewart@hw.ac.uk
 *
 * Description: Uses the OpenCV library to provide @native two
 * procedures for RVC-CAL programs compiled with Orcc. The first is
 * `source_camera_init` is passed the width and height of the
 * resolution of the source camera. It must be called before
 * `readCameraFrame` which captures one complete frame from the source
 * camera and writes to three arrays for R G and B respectively, which
 * can then be used by the calling RVC-CAL actor.
 *
 * The `main` function below can be uncommented to compile and test
 * this file in isolation.
 */

#include "opencv2/opencv.hpp"
using namespace cv;

Mat img, imgDisplay;
VideoCapture capture;
int width, height;
int mousePositionX = -1;
int mousePositionY = -1;

const int R = 2;
const int G = 1;
const int B = 0;

/* read the next camera frame to R G and B chanel arrays to be then
 * used by an RVC-CAL actor compiled with Orcc */
extern "C" void readCameraFrame(uchar *rArr, uchar *gArr, uchar *bArr){
  capture >> img;
  /* loop over all pixels, writing the R G and B channel uchar values
   * to its corresponding array */
  for (int h=0; h < height; h++){
    for (int w = 0; w < width; w++){
      Vec3b & color = img.at<Vec3b>(h,w);
      bArr[h*width+w] = color[B];
      gArr[h*width+w] = color[G];
      rArr[h*width+w] = color[R];
    }
  }
}

/* must be called before `readCameraFrame` to initalise the camera */
extern "C" void source_camera_init(int local_width, int local_height){
  /* try and grab the next frame */
  capture = VideoCapture(0);
  if(!capture.isOpened()){
    printf("Could not grab a camera frame\n");
    exit(0);
  }
  width=local_width;
  height=local_height;
}

void clickCallBack(int event, int x, int y, int flags, void* userdata)
{
  if  ( event == EVENT_LBUTTONDOWN ) {
    mousePositionX = x;
    mousePositionY = y;
  }
}

extern "C" void initDisplayGray(int width, int height) {
  namedWindow( "image" , WINDOW_AUTOSIZE );
  setMouseCallback("image", clickCallBack, NULL);
  imgDisplay = Mat(height,width, CV_8UC1);
}

extern "C" void initDisplayRGB(int width, int height) {
  namedWindow( "image" , WINDOW_AUTOSIZE );
  setMouseCallback("image", clickCallBack, NULL);
  imgDisplay = Mat(height,width, CV_8UC3);
}

extern "C" void displayGray(int w, int h, uchar *grayArr, int *mousePositions){
  for (int y = 0; y < h; y++) {
    for (int x = 0; x < w; x++) {
      imgDisplay.at<uchar>(y,x) = grayArr[w*y+x];
    }
  }
  imshow("image",imgDisplay);
  waitKey(1);

  /* tell the actor the X and Y position of the pixel clicked */
  mousePositions[0] = mousePositionX;
  mousePositions[1] = mousePositionY;
  mousePositionX = -1;
  mousePositionY = -1;

}

extern "C" void displayRGB(int w, int h, uchar *rArr, uchar *gArr,
                           uchar *bArr, int *mousePositions){
  for (int y = 0; y < h; y++) {
    for (int x = 0; x < w; x++) {
      imgDisplay.at<Vec3b>(Point(x,y)) = Vec3b(bArr[w*y+x],gArr[w*y+x],rArr[w*y+x]);
    }
  }
  imshow("image",imgDisplay);
  waitKey(1);

  /* tell the actor the X and Y position of the pixel clicked */
  mousePositions[0] = mousePositionX;
  mousePositions[1] = mousePositionY;
  mousePositionX = -1;
  mousePositionY = -1;
  
}

/*
 * Uncomment this main to test a camera connection and the OpenCV
 * libraries. It presents a video window displaying what the connected
 * camera can see. The WIDTH and HEIGHT constants must be set to the
 * resolution of the source camera. Compile in Linux with
 *
 * $ g++ opencv.cpp `pkg-config --cflags --libs opencv`
 * $ ./a.out
 */

/*
#define WIDTH 640
#define HEIGHT 480
#define PIXELS (WIDTH * HEIGHT)

int main()
{
  source_camera_init(WIDTH,HEIGHT);
  static uchar rArr[PIXELS];
  static uchar gArr[PIXELS];
  static uchar bArr[PIXELS];

  namedWindow("image", WINDOW_AUTOSIZE);
  for (;;){
    readCameraFrame(rArr,gArr,bArr);
    imshow("image", img);
    if(waitKey(30) >= 0) break;
  }
  cvDestroyWindow("image");
}
*/
