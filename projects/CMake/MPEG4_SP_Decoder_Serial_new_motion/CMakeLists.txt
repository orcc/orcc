add_executable (MPEG4_SP_Decoder_Serial_new_motion
address.c
addressing.c
blkexp.c
clip.c
column.c
DCsplit.c
framebuf_interp_add.c
IAP.c
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

target_link_libraries(MPEG4_SP_Decoder_Serial_new_motion liborcc ${APR_LIBRARY} ${SDL_LIBRARY})
