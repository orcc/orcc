#include <stdio.h>
#include <stdlib.h>
#include "SDL.h"
#include "SDL_image.h"

#include "fifo.h"

static const char *source_file_name;

void source_set_file_name(const char *file_name) {
	source_file_name = file_name;
}

extern struct fifo_s *img_read_width;
extern struct fifo_s *img_read_height;
extern struct fifo_s *img_read_r;
extern struct fifo_s *img_read_g;
extern struct fifo_s *img_read_b;

static int init;

static void press_a_key(int code) {
	char buf[2];
	printf("Press enter to continue\n");
	fgets(buf, 2, stdin);
	exit(code);
}

int img_read_scheduler() {
	static SDL_Surface *image;
	static int i = 0;
	static int count;
	int *ptr;
	SDL_PixelFormat *format;

	if (!init) {
		image = IMG_Load(source_file_name);
		if (!image) {
			printf("IMG_Load: %s\n", IMG_GetError());
			press_a_key(1);
		}

		count = image->w * image->h;

		ptr = writeFifo(img_read_width, 1);
		ptr[0] = image->w;
		ptr = writeFifo(img_read_height, 1);
		ptr[0] = image->h;

		init = 1;
	}

	format = image->format;
	while (i < count && hasRoom(img_read_r, 1) && hasRoom(img_read_g, 1) && hasRoom(img_read_b, 1)) {
		int pixel = * (int *) & ((char *)image->pixels)[i * image->format->BytesPerPixel];
		i++;

		ptr = writeFifo(img_read_r, 1);
		ptr[0] = (pixel & format->Rmask) >> format->Rshift;

		ptr = writeFifo(img_read_g, 1);
		ptr[0] = (pixel & format->Gmask) >> format->Gshift;

		ptr = writeFifo(img_read_b, 1);
		ptr[0] = (pixel & format->Bmask) >> format->Bshift;
	}

	return 0;
}
