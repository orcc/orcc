/*
 * Copyright (c) 2014, Heriot-Watt University, Edinburgh
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
 * Date: 22.08.2014
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

#include "cv.h"
#include "highgui.h"
#include <stdio.h>
#include <stdlib.h>

IplImage* img;
CvCapture* capture;
int width, height;

/* read the next camera frame to R G and B chanel arrays to be then
 * used by an RVC-CAL actor compiled with Orcc */
void readCameraFrame(uchar *rArr, uchar *gArr, uchar *bArr){

    /* for loop initalisation variables */
    int h, w;

    /* try and grab the next frame */
    if(!cvGrabFrame(capture)){
        printf("Could not grab a camera frame\n");
        exit(0);
    }
    img=cvRetrieveFrame(capture,0);
    int nchannels = img->nChannels;
    int step      = img->widthStep;
    uchar *frame = ( uchar* )img->imageData;

    /* loop over all pixels, writing the R G and B channel uchar values
    * to its corresponding array */
    const int R = 2;
    const int G = 1;
    const int B = 0;
    int pixelCount=0;
    for (h=0; h < height; h++){
        for (w = 0; w < width; w++){
            char* rgb = frame + step * h + w * 3;
            bArr[pixelCount] = rgb[B];
            gArr[pixelCount] = rgb[G];
            rArr[pixelCount] = rgb[R];
            pixelCount++;
        }
    }
}

/* must be called before `readCameraFrame` to initalise the camera */
void source_camera_init(int local_width, int local_height){
    capture = cvCaptureFromCAM(0);
    img = 0;
    width=local_width;
    height=local_height;
}


/*
 * Uncomment this main to test a camera connection and the OpenCV
 * libraries. It presents a video window displaying what the connected
 * camera can see. The WIDTH and HEIGHT constants must be set to the
 * resolution of the source camera. Compile in Linux with
 *
 * $ gcc `pkg-config --cflags --libs opencv` -lm source_camera.c
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

    cvNamedWindow("image", CV_WINDOW_AUTOSIZE);

    for (;;){
        readCameraFrame(rArr,gArr,bArr);
        cvShowImage("image", img);
        if(cvWaitKey(30) >= 0) break;
    }
    cvDestroyWindow("image");
}
*/
