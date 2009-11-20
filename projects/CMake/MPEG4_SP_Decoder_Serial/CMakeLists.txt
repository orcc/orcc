add_executable (MPEG4_SP_Decoder_Serial
add.c
address.c
addressing.c
blkexp.c
buffer.c
clip.c
column.c
DCsplit.c
IAP.c
interpolation.c
invpred.c
IQ.c
IS.c
mvrecon.c
mvseq.c
orcc_testbed.c
parseheaders.c
retranspose.c
row.c
serialize.c
transpose.c
)

target_link_libraries(MPEG4_SP_Decoder_Serial liborcc ${SDL_LIBRARY})
