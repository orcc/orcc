#!/bin/bash

WELCOME_STRING="Welcome to RVC-CAL AVC Sequences Test"

#print welcome string
echo $WELCOME_STRING

#define AVC Sequence directory
################### Modify Me ########################
SEQ_DIR=/media/Ubuntu32/home/endrix/Projects/Sequences/AVC
#####################################################

SEQ_DIR_CABAC=$SEQ_DIR/CAVLC
SEQ_DIR_CABAC=$SEQ_DIR/CABAC

#define ORCC executable directory
################### Modify Me ########################
ORCC_EXE_DIR=/home/endrix/Projects/Generated/Compare/build
######################################################

#define ORCC CBP and FREXT executable

ORCC_CBP=$ORCC_EXE_DIR/Top_AVC_CBP_decoder_compare

ORCC_FREXT=$ORCC_EXE_DIR/Top_AVC_FREXT_decoder_compare


#Test the FREXT Sequences

#CABA1
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-1/CABA1_Sony_D.jsv -o $SEQ_DIR/CABAC/general/AVCCABA-1/CABA1_Sony_D.yuv

#CABA-2
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-2/CABA2_Sony_E.jsv -o $SEQ_DIR/CABAC/general/AVCCABA-2/CABA2_Sony_E.yuv

#CABA-3
#$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-3/CABA3_Sony_C.jsv -o $SEQ_DIR/CABAC/general/AVCCABA-3/CABA3_Sony_C.yuv

#CABA-3 TOSHIBA
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-4/CABA3_TOSHIBA_E.264 -o $SEQ_DIR/CABAC/general/AVCCABA-4/CABA3_TOSHIBA_E_dec.yuv

#CABA-1 SVA
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-5/CABA1_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCABA-5/CABA1_SVA_B_rec.yuv

#CABA-2 SVA
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-6/CABA2_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCABA-6/CABA2_SVA_B_rec.yuv

#CABA-3 SVA
#$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-7/CABA3_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCABA-7/CABA3_SVA_B_rec.yuv

#camp_mot_frm0
#$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCABA-8/camp_mot_frm0_full.26l -o $SEQ_DIR/CABAC/general/AVCCABA-7/camp_mot_frm0_full_rec.yuv

#CANL1 TOSHIBA
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-1/CANL1_TOSHIBA_G.264 -o $SEQ_DIR/CABAC/general/AVCCANL-1/CANL1_TOSHIBA_G_dec.yuv

#CANL1 SONY
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-2/CANL1_Sony_E.jsv -o $SEQ_DIR/CABAC/general/AVCCANL-2/CANL1_Sony_E.yuv

#CANL2 SONY
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-3/CANL2_Sony_E.jsv -o $SEQ_DIR/CABAC/general/AVCCANL-3/CANL2_Sony_E.yuv

#CANL3 SONY
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-4/CANL3_Sony_C.jsv -o $SEQ_DIR/CABAC/general/AVCCANL-4/CANL3_Sony_C.yuv

#CANL-1 SVA
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-5/CANL1_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCANL-5/CANL1_SVA_B_rec.yuv

#CANL-2 SVA
$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-6/CANL2_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCANL-6/CANL2_SVA_B_rec.yuv

#CANL-3 SVA
#$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANL-7/CANL3_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCANL-7/CANL3_SVA_B_rec.yuv

#CANL-4 SVA
#$ORCC_FREXT -i $SEQ_DIR/CABAC/general/AVCCANA-8/CANL4_SVA_B.264 -o $SEQ_DIR/CABAC/general/AVCCABA-8/CANL4_SVA_B_rec.yuv

#CAQP1 SVA
#$ORCC_FREXT -i $SEQ_DIR/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.jsv -o $SEQ_DIR/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP1_Sony_B.yuv

#CAQP2 SVA
#$ORCC_FREXT -i $SEQ_DIR/CABAC/MB_QP_DELTA/AVCCAQP-2/CAQP3_Sony_D.jsv -o $SEQ_DIR/CABAC/MB_QP_DELTA/AVCCAQP-1/CAQP3_Sony_D.yuv

