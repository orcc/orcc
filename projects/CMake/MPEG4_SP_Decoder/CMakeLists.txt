add_executable (MPEG4_SP_Decoder
acpred.c
add.c
blkexp.c
clip.c
combine.c
dcpred.c
dcsplit.c
ddr.c
dequant.c
downsample.c
fairmerge.c
final.c
interpolate.c
mbpack.c
memorymanager.c
mvrecon.c
mvseq.c
orcc_testbed.c
parseheaders.c
retrans.c
rowsort.c
scale.c
searchwin.c
sep.c
seq.c
serialize.c
shuffle.c
shufflefly.c
trans.c
unpack.c
zigzag.c
zzaddr.c
)

target_link_libraries(MPEG4_SP_Decoder liborcc ${APR_LIBRARY} ${SDL_LIBRARY})
