#include "SDL.h"

#include "fifo.h"

extern struct fifo_s *img_display_width;
extern struct fifo_s *img_display_height;
extern struct fifo_s *img_display_r;
extern struct fifo_s *img_display_g;
extern struct fifo_s *img_display_b;

static SDL_Surface *m_screen;

static int init = 0;

static void press_a_key(int code) {
	char buf[2];
	printf("Press enter to continue\n");
	fgets(buf, 2, stdin);
	exit(code);
}

static int m_width;
static int m_height;
static SDL_Surface *m_screen;
static SDL_Surface *m_image;
static int m_count;

static void img_display_init() {
	m_count = m_width * m_height;

	/* First, initialize SDL's video subsystem. */
    if(SDL_Init(SDL_INIT_VIDEO) < 0) {
        /* Failed, exit. */
        fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		press_a_key(-1);
    }

	m_screen = SDL_SetVideoMode(m_width, m_height, 24, SDL_HWSURFACE | SDL_DOUBLEBUF);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", m_width, m_height,
			SDL_GetError());
		press_a_key(-1);
	}

	SDL_WM_SetCaption("img_display", NULL);

	atexit(SDL_Quit);

	m_image = SDL_CreateRGBSurface(SDL_SWSURFACE, m_width, m_height, m_screen->format->BitsPerPixel,
		m_screen->format->Rmask, m_screen->format->Gmask, m_screen->format->Bmask, m_screen->format->Amask);

	init = 1;
}

enum states {
	s_width,
	s_height,
	s_contents,
	s_done
};

enum states _FSM_state = s_width;

static int width_scheduler() {
	int res;
	int *ptr;

	if (hasTokens(img_display_width, 1)) {
		ptr = readFifo(img_display_width, 1);
		m_width = ptr[0];
		_FSM_state = s_height;
		res = 1;
	} else {
		res = 0;
	}

	return res;
}

static int height_scheduler() {
	int res;
	int *ptr;

	if (hasTokens(img_display_height, 1)) {
		ptr = readFifo(img_display_height, 1);
		m_height = ptr[0];
		_FSM_state = s_contents;

		img_display_init();

		res = 1;
	} else {
		res = 0;
	}

	return res;
}

static int contents_scheduler() {
	int res;
	int *ptr;
	int red;
	int green;
	int blue;
	int pixel;
	static int i = 0;

	while (i < m_count && hasTokens(img_display_r, 1) && hasTokens(img_display_g, 1) && hasTokens(img_display_b, 1)) {
		SDL_PixelFormat *format = m_image->format;

		ptr = readFifo(img_display_r, 1);
		red = ptr[0];
		ptr = readFifo(img_display_g, 1);
		green = ptr[0];
		ptr = readFifo(img_display_b, 1);
		blue = ptr[0];

		pixel = (red << format->Rshift) & format->Rmask
			| (green << format->Gshift) & format->Gmask
			| (blue << format->Bshift) & format->Bmask;
			
		* (int *) &((char *)m_image->pixels)[i * format->BytesPerPixel] = pixel;
		i++;
	}

	// Draws the image on the screen:
	SDL_BlitSurface(m_image, NULL, m_screen, NULL);
	SDL_Flip(m_screen);

	if (i == m_count) {
		_FSM_state = s_done;
		res = 1;
	} else {
		res = 0;
	}

	return res;
}

int img_display_scheduler() {
	int res;

	do {
		switch (_FSM_state) {
			case s_width:
				res = width_scheduler();
				break;
			case s_height:
				res = height_scheduler();
				break;
			case s_contents:
				res = contents_scheduler();
				break;
			case s_done:
				res = 0;
				break;
			default:
				res = 0;
				break;
		}
	} while (res);

	return 0;
}
