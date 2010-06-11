if [ ! -f "test_cabac.command" ] ; then
newdir=`echo $0 | sed 's/test_cabac.command//'`
cd $newdir/build/AVC_CBP_Compare/Release
fi


./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-1/CANL1_TOSHIBA_G.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-1/CANL1_TOSHIBA_G_dec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-2/CANL1_Sony_E.jsv -o ~/Movies/AVC/CABAC/general/AVCCANL-2/CANL1_Sony_E.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-3/CANL2_Sony_E.jsv -o ~/Movies/AVC/CABAC/general/AVCCANL-3/CANL2_Sony_E.yuv
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-4/CANL3_Sony_C.jsv -o ~/Movies/AVC/CABAC/general/AVCCANL-4/CANL3_Sony_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-5/CANL1_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-5/CANL1_SVA_B-rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-6/CANL2_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-6/CANL2_SVA_B_rec.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-7/CANL3_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-7/CANL3_SVA_B_rec.yuv
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-8/CANL4_SVA_B.264 -o -i ~/Movies/AVC/CABAC/general/AVCCANL-8/CANL4_SVA_B_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-2/CABA2_Sony_E.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-2/CABA2_Sony_E.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-3/CABA3_Sony_C.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-3/CABA3_Sony_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-1/CABA1_Sony_D.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-1/CABA1_Sony_D.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-4/CABA3_TOSHIBA_E.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-4/CABA3_TOSHIBA_E_dec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-5/CABA1_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-5/CABA1_SVA_B_rec.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-6/CABA2_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-6/CABA2_SVA_B_rec.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-7/CABA3_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-7/CABA3_SVA_B_rec.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-8/camp_mot_frm0_full.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-8/camp_mot_frm0_full_rec.yuv
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.jsv -o ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.yuv
 