SOURCES = $(shell ls *.c)

CFLAGS = -I ../../../runtime/liborcc/include

executable: $(SOURCES:%.c=%.o)
	$(CC) $(LDFLAGS) -o $@ $^ 

clean:
	rm -f executable *.o
