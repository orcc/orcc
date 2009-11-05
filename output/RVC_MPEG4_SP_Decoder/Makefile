ACTORS = $(shell ls src/*.c | sed -e "s/src\/display\.c//" -e "s/src\/Compare\.c//" -e "s/src\/source\.c//" | xargs)
SOURCES = ../../runtime/liborcc/src/actors/display.c ../../runtime/liborcc/src/actors/source.c ../../runtime/liborcc/src/orcc_util.c

CFLAGS = -O3 -I ../../runtime/liborcc/include `apr-1-config --cflags`
LDFLAGS =

all: $(ACTORS:src/%.c=bin/%.o) $(SOURCES:%.c=%.o)
	$(CC) $(LDFLAGS) -o bin/RVC_MPEG4_SP_Decoder $^ `sdl-config --libs` `apr-1-config --link-ld --libs`

bin:
	mkdir bin

bin/%.o : src/%.c bin
	$(CC) -c $(CFLAGS) $(CPPFLAGS) $< -o $@

%.o : %.c
	$(CC) -c $(CFLAGS) `sdl-config --cflags` $(CPPFLAGS) $< -o $@

clean:
	rm -rf bin/*.o