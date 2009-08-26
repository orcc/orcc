#include "SDL.h"
#include "fifo.h"

#define sizeof_array(a) (sizeof(a) / sizeof(a[0]))

static int contents_img_display_width[1];
static int contents_img_display_height[1];
static int contents_img_display_r[1024 * 1024];
static int contents_img_display_g[1024 * 1024];
static int contents_img_display_b[1024 * 1024];

static struct fifo_s g_img_display_width = { sizeof_array(contents_img_display_width), contents_img_display_width };
static struct fifo_s g_img_display_height = { sizeof_array(contents_img_display_height), contents_img_display_height };
static struct fifo_s g_img_display_r = { sizeof_array(contents_img_display_r), contents_img_display_r };
static struct fifo_s g_img_display_g = { sizeof_array(contents_img_display_g), contents_img_display_g };
static struct fifo_s g_img_display_b = { sizeof_array(contents_img_display_b), contents_img_display_b };

struct fifo_s *img_display_width = &g_img_display_width;
struct fifo_s *img_display_height = &g_img_display_height;
struct fifo_s *img_display_r = &g_img_display_r;
struct fifo_s *img_display_g = &g_img_display_g;
struct fifo_s *img_display_b = &g_img_display_b;

struct fifo_s *img_read_width = &g_img_display_width;
struct fifo_s *img_read_height = &g_img_display_height;
struct fifo_s *img_read_r = &g_img_display_r;
struct fifo_s *img_read_g = &g_img_display_g;
struct fifo_s *img_read_b = &g_img_display_b;

////////////////////////////////////////////////////////////////////////////////
extern int img_display_scheduler();
extern int img_read_scheduler();

static int (*actors[2])() = { img_read_scheduler, img_display_scheduler };
static int ready[2] = { 1, 1 };

void scheduler() {
	int i;
	SDL_Event event;

	while (1) {
		for (i = 0; i < 2; i++) {
			actors[i]();
		}

		/* Grab all the events off the queue. */
		while (SDL_PollEvent(&event)) {
			switch(event.type) {
				case SDL_QUIT:
					exit(0);
					break;
				default:
					break;
			}
		}
	}
}

////////////////////////////////////////////////////////////////////////////////
extern void source_set_file_name(const char *file_name);

int main(int argc, char *argv[]) {
	char buf[2];

	if (argc < 2) {
		printf("No input file provided!\n");
		fgets(buf, 2, stdin);
		return 1;
	}

	source_set_file_name(argv[1]);

	scheduler();

	printf("End of simulation! Press a key to continue\n");
	fgets(buf, 2, stdin);
	return 0;
}
