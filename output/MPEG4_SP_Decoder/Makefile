# C target variables

ACTORS = $(shell ls src/*.c | sed -e "s/src\/display\.c//" -e "s/src\/source\.c//" | xargs)
SOURCES = ../../runtime/liborcc/src/actors/display.c ../../runtime/liborcc/src/actors/source.c ../../runtime/liborcc/src/orcc_util.c

CFLAGS = -O5 -I ../../runtime/liborcc/include `sdl-config --cflags` `apr-config --includes`
CPPFLAGS = `apr-config --cppflags`
LDFLAGS = `apr-config --link-ld`

all: c llvm

# LLVM target variables

# Core target
NETWORK = $(shell ls llvm_src/*.cpp)
CXX = g++
LFF_SOURCES = ../../runtime/liborcc/src/actors/lff_display.c ../../runtime/liborcc/src/actors/lff_source.c ../../runtime/liborcc/src/orcc_util.c

# VTL target
LLVM_CC = llvm-gcc
LLVM_ACTORS = $(shell ls llvm_src/*.c | sed -e "s/llvm_src\/display\.c//" -e "s/llvm_src\/source\.c//" | xargs)


# C target

c: $(ACTORS:src/%.c=src/%.o) $(SOURCES:%.c=%.o)
	$(CC) $(LDFLAGS) -o testbed $^ `sdl-config --libs`

clean:
	rm -rf src/*.o
	rm -rf $(SOURCES:%.c=%.o)
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


# LLVM target

llvm: vtl llvm_core

llvm_bin:
	mkdir llvm_bin


llvm_core: $(NETWORK:llvm_src/%.cpp=llvm_bin/%.o) $(LFF_SOURCES:%.c=%.o)
	$(CXX) $(CFLAGS)  -o llvm_testbed $^ `llvm-config --cxxflags --ldflags --libs all` `sdl-config --libs --cflags`

llvm_bin/%.o : llvm_src/%.cpp llvm_bin
	$(CXX) -c $(CFLAGS) $(CPPFLAGS) $< -o $@ `llvm-config --cxxflags --ldflags`

# VTL target
vtl: $(LLVM_ACTORS:llvm_src/%.c=VTL/%.bc)

VTL:
	mkdir VTL

VTL/%.bc : llvm_src/%.c  VTL
	$(LLVM_CC) --emit-llvm  $(CFLAGS) $(CPPFLAGS) -c $< -o $@
