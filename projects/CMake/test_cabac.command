if [ ! -f "test_cabac.command" ] ; then
newdir=`echo $0 | sed 's/test_cabac.command//'`
cd $newdir/build/AVC_CBP_Compare/Release
fi

./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-1/CABA1_Sony_D.jsv -o ~/Movies/AVC/CABAC/general/AVCCABA-1/CABA1_Sony_D.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/general/AVCCABA-5/CABA1_SVA_B.264 -o ~/Movies/AVC/CABAC/general/AVCCABA-5/CABA1_SVA_B_rec.yuv
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.jsv -o ~/Movies/AVC/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.jsv