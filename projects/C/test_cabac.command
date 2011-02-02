if [ ! -f "test_cabac.command" ] ; then
newdir=`echo $0 | sed 's/test_cabac.command//'`
cd $newdir/build/AVC_CBP_Compare/Release
fi

echo "AVCCANL-1"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-1/CANL1_TOSHIBA_G.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-1/CANL1_TOSHIBA_G_dec.yuv
echo "AVCCANL-2"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-2/CANL1_Sony_E.jsv -o ~/Movies/AVC/CABAC/general/AVCCANL-2/CANL1_Sony_E.yuv
echo "AVCCANL-3"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-3/CANL2_Sony_E.jsv -o ~/Movies/AVC/CABAC/general/AVCCANL-3/CANL2_Sony_E.yuv
echo "AVCCANL-4 B slices not yet supported"
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-4/CANL3_Sony_C.jsv -o ~/Movies/AVC/CABAC/general/AVCCANL-4/CANL3_Sony_C.yuv
echo "AVCCANL-5"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-5/CANL1_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-5/CANL1_SVA_B_rec.yuv
echo "AVCCANL-6"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-6/CANL2_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-6/CANL2_SVA_B_rec.yuv
echo "AVCCANL-7"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-7/CANL3_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCANL-7/CANL3_SVA_B_rec.yuv
echo "AVCCANL-8 B slices not yet supported"
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCANL-8/CANL4_SVA_B.264 -o -i ~/Movies/AVC/CABAC/general/AVCCANL-8/CANL4_SVA_B_rec.yuv

echo "AVCCABA-1"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-1/CABA1_Sony_D.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-1/CABA1_Sony_D.yuv
echo "AVCCABA-2"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-2/CABA2_Sony_E.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-2/CABA2_Sony_E.yuv
echo "AVCCABA-3 B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-3/CABA3_Sony_C.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-3/CABA3_Sony_C.yuv
echo "AVCCABA-4"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-4/CABA3_TOSHIBA_E.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-4/CABA3_TOSHIBA_E_dec.yuv
echo "AVCCABA-5"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-5/CABA1_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-5/CABA1_SVA_B_rec.yuv
echo "AVCCABA-6"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-6/CABA2_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-6/CABA2_SVA_B_rec.yuv
echo "AVCCABA-7 B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-7/CABA3_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-7/CABA3_SVA_B_rec.yuv
echo "AVCCABA-8 B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-8/camp_mot_frm0_full.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-8/camp_mot_frm0_full_rec.yuv

echo "AVCCAIN-1 cabal init not yet supported with  B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/init/AVCCAIN-1/CABACI3_Sony_B.jsv -o ~/Movies/AVC/CABAC/init/AVCCAIN-1/CABACI3_Sony_B.yuv

echo "AVCCAQP-1 not yet supported MB QP Delta"
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.jsv -o ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.yuv
echo "AVCCAQP-2 not yet supported MB QP Delta with  B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-2/CACQP3_Sony_D.jsv -o ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-2/CACQP3_Sony_D.yuv

echo "AVCCASL-1 multiple slice not yet supported with  B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/SLICE/AVCCASL-1/CABAST3_Sony_E.jsv -o ~/Movies/AVC/CABAC/SLICE/AVCCASL-1/CABAST3_Sony_E.yuv
echo "AVCCASL-2 multiple slice not yet supported with  B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/SLICE/AVCCASL-2/CABASTBR3_Sony_B.jsv -o ~/Movies/AVC/CABAC/SLICE/AVCCASL-2/CABASTBR3_Sony_B.yuv

echo "AVCCAMR-1 MMCO not yet supported with  B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/MMCO/AVCCAMR-1/MR9_BT_B.h264 -o ~/Movies/AVC/CABAC/MMCO/AVCCAMR-1/MR9_BT_B.yuv
echo "AVCCAMR-2 MMCO not yet supported with  B slices not yet supported"
#./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/MMCO/AVCCAMR-2/HCMP1_HHI_A.264 -o ~/Movies/AVC/CABAC/MMCO/AVCCAMR-2/HCMP1_HHI_A.yuv

 