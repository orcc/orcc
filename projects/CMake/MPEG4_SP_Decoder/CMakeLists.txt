add_executable (MPEG4_SP_Decoder
src/acpred.c
src/add.c
src/blkexp.c
src/clip.c
src/combine.c
src/dcpred.c
src/dcsplit.c
src/ddr.c
src/dequant.c
src/downsample.c
src/fairmerge.c
src/final.c
src/interpolate.c
src/mbpack.c
src/memorymanager.c
src/mvrecon.c
src/mvseq.c
src/orcc_testbed.c
src/parseheaders.c
src/retrans.c
src/rowsort.c
src/scale.c
src/searchwin.c
src/sep.c
src/seq.c
src/serialize.c
src/shuffle.c
src/shufflefly.c
src/trans.c
src/unpack.c
src/zigzag.c
src/zzaddr.c
)

target_link_libraries(MPEG4_SP_Decoder liborcc ${APR_LIBRARY} ${SDL_LIBRARY})
