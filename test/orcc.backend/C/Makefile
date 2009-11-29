SOURCES = $(shell ls *.c)

CFLAGS = -I ../../../projects/CMake/include/liborcc

executable: $(SOURCES:%.c=%.o)
	$(CC) $(LDFLAGS) -o $@ $^ 

clean:
	rm -f executable *.o
