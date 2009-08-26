#C Generation variable

ACTORS = $(shell ls src/*.c | sed -e "s/src\/display\.c//" -e "s/src\/source\.c//" | xargs)
SOURCES = ../../runtime/liborcc/actors/display.c ../../runtime/liborcc/actors/source.c

CFLAGS = -O3 -I ../../runtime/liborcc/scheduler
LDFLAGS =

all: c llvm

#LLVM generation variable

#Core Generation
NETWORK = $(shell ls llvm_src/*.cpp)
CXX= g++
LFF_SOURCES = ../../runtime/liborcc/actors/lff_display.c ../../runtime/liborcc/actors/lff_source.c

#VTL generation
LLVM_CC = llvm-gcc
LLVM_ACTORS = $(shell ls llvm_src/*.c | sed -e "s/llvm_src\/display\.c//" -e "s/llvm_src\/source\.c//" | xargs)


#C Generation

c: $(ACTORS:src/%.c=bin/%.o) $(SOURCES:%.c=%.o)
	$(CC) $(LDFLAGS) -o testbed $^ `sdl-config --libs`

bin:
	mkdir bin

bin/%.o : src/%.c bin
	$(CC) -c $(CFLAGS) $(CPPFLAGS) $< -o $@

%.o : %.c
	$(CC) -c $(CFLAGS) `sdl-config --cflags` $(CPPFLAGS) $< -o $@

clean:
	rm -rf bin/*.o
	rm -rf llvm_bin/*.o
	rm -rf VTL/*.bc

diff:
	for i in $(ACTORS); do \
		FILE=`echo $$i | sed -e "s/src\///"`; \
		echo Diffing file $$FILE; \
		diff reference/$$FILE src/$$FILE ; \
	done

%.diff: src/%.c
	FILE=`echo $^ | sed -e "s/src\///"`; \
	diff reference/$$FILE src/$$FILE


#LLVM generation

llvm: vtl llvm_core

llvm_bin:
	mkdir llvm_bin


llvm_core: $(NETWORK:llvm_src/%.cpp=llvm_bin/%.o) $(LFF_SOURCES:%.c=%.o)
	$(CXX) $(CFLAGS)  -o llvm_testbed $^ `llvm-config --cxxflags --ldflags --libs all` `sdl-config --libs --cflags`

llvm_bin/%.o : llvm_src/%.cpp llvm_bin
	$(CXX) -c $(CFLAGS) $(CPPFLAGS) $< -o $@ `llvm-config --cxxflags --ldflags`

#VTL Generation
vtl: $(LLVM_ACTORS:llvm_src/%.c=VTL/%.bc)

VTL:
	mkdir VTL

VTL/%.bc : llvm_src/%.c  VTL
	$(LLVM_CC) --emit-llvm  $(CFLAGS) $(CPPFLAGS) -c $< -o $@