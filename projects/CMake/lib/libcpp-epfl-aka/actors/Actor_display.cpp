#include "Actor_display.h"

SDL_Surface * Actor_display::m_screen = NULL;
SDL_Overlay *Actor_display::m_overlay = NULL;
int Actor_display::m_x;
int Actor_display::m_y;
int Actor_display::m_width;
int Actor_display::m_height;
int Actor_display::init;

Uint32 Actor_display::start_time;
int Actor_display::num_images_start;
int Actor_display::num_images_end;

Actor_display::Actor_display():ActorGen(display_IPORT_SIZE, display_OPORT_SIZE)
{
}


void Actor_display::press_a_key(int code) {
	char buf[2];
	printf("Press enter to continue\n");
	fgets(buf, 2, stdin);
	exit(code);
}

void Actor_display::print_fps_avg() {
	Uint32 t = SDL_GetTicks();

	printf("%i images in %f seconds: %f FPS\n",
		num_images_end, (float) t / 1000.0f,
		1000.0f * (float)num_images_end / (float)t);
}

void Actor_display::display_show_image() {
	static Uint32 t;
	SDL_Rect rect = { 0, 0, m_width, m_height };

	int t2;
	SDL_Event event;

	if (t == 0) {
		start_time = SDL_GetTicks();
		t = start_time;
	}

	if (SDL_LockYUVOverlay(m_overlay) < 0) {
		fprintf(stderr, "Can't lock screen: %s\n", SDL_GetError());
		press_a_key(-1);
	}
	memcpy(m_overlay->pixels[0], img_buf_y, m_width * m_height );
	memcpy(m_overlay->pixels[1], img_buf_u, m_width * m_height / 4 );
	memcpy(m_overlay->pixels[2], img_buf_v, m_width * m_height / 4 );

	SDL_UnlockYUVOverlay(m_overlay);
	SDL_DisplayYUVOverlay(m_overlay, &rect);
	num_images_end++;
	t2 = SDL_GetTicks();
	if (t2 - t > 3000) {
		printf("%f images/sec\n",
			1000.0f * (float)(num_images_end - num_images_start) / (float)(t2 - t));

		t = t2;
		num_images_start = num_images_end;
	}

	/* Grab all the events off the queue. */
	while (SDL_PollEvent(&event)) {
		switch (event.type) {
			case SDL_QUIT:
				exit(0);
				break;
			default:
				break;
		}
	}
}


void Actor_display::display_write_mb(short tokens[384]) {
	int i, j, x, y, cnt, base, idx;

	cnt = 0;
	base = m_y * m_width + m_x;

	for (y = 0; y < 2; y++) {
		for (x = 0; x < 2; x++) {
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					int tok = tokens[cnt];
					cnt++;

					if (tok < 0) {
						tok = 0;
					} else if (tok > 255) {
						tok = 255;
					}

					idx = base + (i + 8 * y) * m_width + (j + 8 * x);
					img_buf_y[idx] = tok;
				}
			}
		}
	}

	base = m_y / 2 * m_width / 2 + m_x / 2;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			cnt++;

			if (tok < 0) {
				tok = 0;
			} else if (tok > 255) {
				tok = 255;
			}

			idx = base + i * m_width / 2 + j;
			img_buf_u[idx] = tok;
		}
	}

	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			cnt++;

			if (tok < 0) {
				tok = 0;
			} else if (tok > 255) {
				tok = 255;
			}

			idx = base + i * m_width / 2 + j;
			img_buf_v[idx] = tok;
		}
	}

	m_x += 16;
	if (m_x == m_width) {
		m_x = 0;
		m_y += 16;
	}

	if (m_y == m_height) {
		m_x = 0;
		m_y = 0;
		display_show_image();
	}
}

void Actor_display::display_init() {
	// First, initialize SDL's video subsystem.
    if (SDL_Init( SDL_INIT_VIDEO ) < 0) {
        fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		press_a_key(-1);
    }

	SDL_WM_SetCaption("display", NULL);

	atexit(SDL_Quit);
//	atexit(print_fps_avg());

	init = 1;
}

void Actor_display::display_set_video(int width, int height) {
	if (width == m_width && height == m_height) {
		// video mode is already good
		return;
	}

	m_width = width;
	m_height = height;
	printf("set display to %ix%i\n", width, height);

	m_screen = SDL_SetVideoMode(m_width, m_height, 24, SDL_HWSURFACE | SDL_DOUBLEBUF);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", m_width, m_height,
			SDL_GetError());
		press_a_key(-1);
	}

	if (m_overlay != NULL) {
		SDL_FreeYUVOverlay(m_overlay);
	}

	m_overlay = SDL_CreateYUVOverlay(m_width, m_height, SDL_IYUV_OVERLAY, m_screen);
	if (m_overlay == NULL) {
		fprintf(stderr, "Couldn't create overlay: %s\n", SDL_GetError());
		press_a_key(-1);
	}
}




void Actor_display::process() {
	int res = 1;
	short WIDTH[1], HEIGHT[1];
	short B[384];
	while (res) {
		if (m_poTabIn[display_WIDTH]->hasTokens(1) && m_poTabIn[display_HEIGHT]->hasTokens(1)) {
			short width, height;
			m_poTabIn[display_WIDTH]->get(WIDTH, 1);
			width = WIDTH[0] * 16;
			m_poTabIn[display_HEIGHT]->get(HEIGHT, 1);
			height = HEIGHT[0] * 16;

			display_set_video(width, height);
		}

		if (m_poTabIn[display_B]->hasTokens(384)) {
			if (init) {
				m_poTabIn[display_B]->get(B, 384);
				display_write_mb(B);
				//display_show_image();
			} else {
				display_init();
			}
			res = 1;
		} else {
			res = 0;
		}
	}
}
