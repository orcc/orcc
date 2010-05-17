#!/bin/bash

WELCOME_STRING="Welcome to AVC Sequences Test"

#print welcome string
echo $WELCOME_STRING

#define AVC Sequence directory
################### Modify Me ########################
SEQ_DIR=/home/endrix/Projects/Sequences/AVC
#####################################################

SEQ_DIR_CABAC=$SEQ_DIR/CAVLC
SEQ_DIR_CABAC=$SEQ_DIR/CABAC

#define ORCC executable directory
################### Modify Me ########################
ORCC_EXE_DIR=/home/endrix/Projects/Generated/C/build
######################################################

#define ORCC CBP and FREXT executable

ORCC_CBP=$ORCC_EXE_DIR/Orcc_AVC_CBP_decoder

ORCC_FREXT=$ORCC_EXE_DIR/Orcc_AVC_FREXT_decoder

#Declare variable choise and asign value 3(loop)

choise=3

#Print to stdout

echo "1. AVC CBP decoder"
echo "2. AVC FREXT decoder"
echo -n "Please choose a decoder [1, 2]? : "
#Loop while the variable choise is equal to 3

#read user input
read choice

# AVC decoder choise

if [ $choice -eq 1 ] ; then
 
        echo "You have chosen the AVC CBP decoder"

else                   

        if [ $choice -eq 2 ] ; then
                 echo "You have chosen the AVC FREXT decoder"

		#Test the sequencese

		$ORCC_FREXT -i $SEQ_DIR_CABAC/general/AVCCABA-1/CABA1_Sony_D.jsv
		#$ORCC_FREXT -i $SEQ_DIR_CABAC/general/AVCCABA-2/CABA2_Sony_E.jsv
		#$ORCC_FREXT -i $SEQ_DIR_CABAC/general/AVCCABA-3/CABA3_Sony_C.jsv
		#$ORCC_FREXT -i $SEQ_DIR_CABAC/general/AVCCABA-5/CABA1_SVA_B.264


        else
         
                if [ $choice -ge 3 ] ; then
                        echo "You have to choose 1. CBP Decoder or 2. FREXT Decoder"
                fi   
        fi
fi

