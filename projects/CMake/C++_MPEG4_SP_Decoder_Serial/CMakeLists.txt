add_executable (MPEG4_SP_Decoder_Serial
Actor_add.cpp
Actor_address.cpp
Actor_addressing.cpp
Actor_blkexp.cpp
Actor_buffer.cpp
Actor_clip.cpp
Actor_column.cpp
Actor_DCsplit.cpp
Actor_IAP.cpp
Actor_interpolation.cpp
Actor_invpred.cpp
Actor_IQ.cpp
Actor_IS.cpp
Actor_mvrecon.cpp
Actor_mvseq.cpp
Network_orcc_testbed.cpp
Actor_parseheaders.cpp
Actor_retranspose.cpp
Actor_row.cpp
Actor_serialize.cpp
Actor_transpose.cpp
)

target_link_libraries(MPEG4_SP_Decoder_Serial liborcc libcpp-epfl-aka ${SDL_LIBRARY})
