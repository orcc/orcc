if [ ! -f "test_avc_baseline_compare.command" ] ; then
newdir=`echo $0 | sed 's/test_avc_baseline_compare.command//'`
cd $newdir/build/AVC_CBP_Compare/Release
fi

./Orcc_AVC_CBP_decoder_compare -i /Users/mraulet/Movies/AVC/CAVLC/general/AVCNL-1/NL1_Sony_D.jsv -o /Users/mraulet/Movies/AVC/CAVLC/general/AVCNL-1/NL1_Sony_D.yuv 
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCNL-2/SVA_NL1_B.264 -o ~/Movies/AVC/CAVLC/general/AVCNL-2/SVA_NL1_B_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCNL-3/NL2_Sony_H.jsv -o ~/Movies/AVC/CAVLC/general/AVCNL-3/NL2_Sony_H.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCNL-4/SVA_NL2_E.264 -o ~/Movies/AVC/CAVLC/general/AVCNL-4/SVA_NL2_E_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-1/BA1_Sony_D.jsv -o ~/Movies/AVC/CAVLC/general/AVCBA-1/BA1_Sony_D.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-2/SVA_BA1_B.264 -o ~/Movies/AVC/CAVLC/general/AVCBA-2/SVA_BA1_B_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-3/BA2_Sony_F.jsv -o ~/Movies/AVC/CAVLC/general/AVCBA-3/BA2_Sony_F.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-4/SVA_BA2_D.264 -o ~/Movies/AVC/CAVLC/general/AVCBA-4/SVA_BA2_D_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-5/BA_MW_D.264 -o ~/Movies/AVC/CAVLC/general/AVCBA-5/BA_MW_D_rec.qcif
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-6/BANM_MW_D.264 -o ~/Movies/AVC/CAVLC/general/AVCBA-6/BANM_MW_D_rec.qcif
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCBA-7/BA1_FT_C.264 -o ~/Movies/AVC/CAVLC/general/AVCBA-7/BA1_FT_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCMQ-1/NLMQ1_JVC_C.264 -o ~/Movies/AVC/CAVLC/general/AVCMQ-1/NLMQ1_JVC_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCMQ-2/NLMQ2_JVC_C.264 -o ~/Movies/AVC/CAVLC/general/AVCMQ-2/NLMQ2_JVC_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCMQ-3/BAMQ1_JVC_C.264 -o ~/Movies/AVC/CAVLC/general/AVCMQ-3/BAMQ1_JVC_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCMQ-4/BAMQ2_JVC_C.264 -o ~/Movies/AVC/CAVLC/general/AVCMQ-4/BAMQ2_JVC_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCSL-1/SVA_Base_B.264 -o ~/Movies/AVC/CAVLC/general/AVCSL-1/SVA_Base_B_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCSL-2/SVA_FM1_E.264 -o ~/Movies/AVC/CAVLC/general/AVCSL-2/SVA_FM1_E_rec.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCSQ-1/BASQP1_Sony_C.jsv -o ~/Movies/AVC/CAVLC/general/AVCSQ-1/BASQP1_Sony_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCCI-1/CI_MW_D.264 -o ~/Movies/AVC/CAVLC/general/AVCCI-1/CI_MW_D_rec.qcif
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCCI-2/SVA_CL1_E.264 -o ~/Movies/AVC/CAVLC/general/AVCCI-2/SVA_CL1_E_rec.yuv
# parser ok ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCCI-3/CI1_FT_B.264 -o ~/Movies/AVC/CAVLC/general/AVCCI-3/CI1_FT_B.yuv

# frame cropping ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCFC-1/CVFC1_Sony_C.jsv -o ~/Movies/AVC/CAVLC/general/AVCFC-1/CVFC1_Sony_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCAUD-1/AUD_MW_E.264 -o ~/Movies/AVC/CAVLC/general/AVCAUD-1/AUD_MW_E_rec.qcif
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCMIDR-1/MIDR_MW_D.264 -o ~/Movies/AVC/CAVLC/general/AVCMIDR-1/MIDR_MW_D_rec.qcif
# parser to check ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCNRF-1/NRF_MW_E.264 -o ~/Movies/AVC/CAVLC/general/AVCNRF-1/NRF_MW_E_rec.qcif
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/general/AVCMPS-1/MPS_MW_A.264 -o ~/Movies/AVC/CAVLC/general/AVCMPS-1/MPS_MW_A_rec.qcif
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/IPCM/AVCPCM-1/CVPCMNL1_SVA_C.264 -o ~/Movies/AVC/CAVLC/IPCM/AVCPCM-1/CVPCMNL1_SVA_C_rec.yuv
# parser to check ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/IPCM/AVCPCM-2/CVPCMNL2_SVA_C.264 -o ~/Movies/AVC/CAVLC/IPCM/AVCPCM-2/CVPCMNL2_SVA_C_rec.yuv

./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/Long_Sequence/AVCLS-1/LS_SVA_D.264 -o ~/Movies/AVC/CAVLC/Long_Sequence/AVCLS-1/LS_SVA_D_rec.yuv

./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-1/MR1_BT_A.h264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-1/MR1_BT_A.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-2/MR2_TANDBERG_E.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-2/MR2_TANDBERG_E.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-3/MR3_TANDBERG_B.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-3/MR3_TANDBERG_B.yuv
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-4/MR4_TANDBERG_C.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-4/MR4_TANDBERG_C.yuv
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-5/MR5_TANDBERG_C.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-5/MR5_TANDBERG_C.yuv
./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-6/MR1_MW_A.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-6/MR1_MW_A_rec.qcif
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-7/MR2_MW_A.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-7/MR2_MW_A_rec.qcif
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-11/HCBP1_HHI_A.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-11/HCBP1_HHI_A.yuv
# ./Orcc_AVC_CBP_decoder_compare -i ~/Movies/AVC/CAVLC/MMCO/AVCMR-12/HCBP2_HHI_A.264 -o ~/Movies/AVC/CAVLC/MMCO/AVCMR-12/HCBP2_HHI_A.yuv


