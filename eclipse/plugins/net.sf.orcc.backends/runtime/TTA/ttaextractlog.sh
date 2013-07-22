#!/bin/bash

# Functions
function usage {
	echo "Open RVC-CAL Compiler - TTA backend"
    echo "ttaextractlog - TTA Simulation logs extractor"
    echo "Usage: ttaextractlog [options] [output_tag]"
    echo ""
    echo "output_tag : allow to tag extraction with a name (default value = logs)"

    echo "Options:"
    echo "   -n, Number of frames of the sequence (default value = 10)"
    echo "   -f, Frequency in MHz (default value = 50)"
	exit 0
}

function errorFunc {
	echo "! Error :" $ERROR_MSG
	echo ""
	usage
	exit 1	
}

# Default values for parameters
OUTPUT_TAG="logs"
FREQUENCY=50
NBFRAME=10
ERROR_MSG=""

# Parameters parsing
for i in $@
	do
	echo $i
	# Help
	if [ $i = "--h" ] 
		then
		usage
	fi
	# Number of frames
	if [ ${i:0:2} = "-n" ]
	 	then
		NBFRAME=${i:2};
		echo NBFRAME : $NBFRAME;
	fi
	# Frequency
	if [ ${i:0:2} = "-f" ]
	 	then
		FREQUENCY=${i:2};
		echo FREQUENCY : $FREQUENCY;
	fi
	# Output tag
	if [ ${i:0:2} = "-o" ]
	 	then
		OUTPUT_TAG=${i:2};
		echo OUTPUT_TAG : $OUTPUT_TAG;
	fi
done

# Setting variables
SUMMARY="summary_"$FREQUENCY"MHz_"$NBFRAME"frames"
SUMMARY_LOG=$SUMMARY".log"
SUMMARY_CSV=$SUMMARY".csv"
SUMMARY_HTML=$SUMMARY".html"
SUMMARY_PDF=$SUMMARY".pdf"
SCALE=10

# Cleaning previous same summary
echo "...Cleaning"
rm -Rf $OUTPUT_TAG
mkdir $OUTPUT_TAG

# Extracting informations from log files
echo "...Extracting informations from log files"

# Summary log header
echo "Summary results for " $OUTPUT_TAG > $SUMMARY_LOG
echo ".....Frequency    = " $FREQUENCY >> $SUMMARY_LOG
echo ".....Nb of frames = " $NBFRAME >> $SUMMARY_LOG
echo ".....Calcul scale = " $SCALE >> $SUMMARY_LOG
echo "" >> $SUMMARY_LOG

echo "Actor,Cycle count,FPS@"$FREQUENCY"MHz,Status" > $SUMMARY_CSV

echo "<html lang=\"fr\">" > $SUMMARY_HTML
echo "  <head>" >> $SUMMARY_HTML
echo "  <style type=\"text/css\">" >> $SUMMARY_HTML
echo "  <!--" >> $SUMMARY_HTML
echo "  @import url(\"style.css\");" >> $SUMMARY_HTML
echo "  -->" >> $SUMMARY_HTML
echo "  </style>" >> $SUMMARY_HTML
echo "  </head>" >> $SUMMARY_HTML
echo "	<body>" >> $SUMMARY_HTML
# echo "	<h2>Summary results for " $OUTPUT_TAG "</h2>">> $SUMMARY_HTML
# echo "		<table id=\"rounded-corner\">" >> $SUMMARY_HTML
# echo "			<tbody>" >> $SUMMARY_HTML
# echo "				<tr>" >> $SUMMARY_HTML
# echo "					<td>Frequency</td>" >> $SUMMARY_HTML
# echo "					<td class=\"num\">"$FREQUENCY"</td>" >> $SUMMARY_HTML
# echo "				</tr>" >> $SUMMARY_HTML
# echo "				<tr>" >> $SUMMARY_HTML
# echo "					<td>Nb of frames</td>" >> $SUMMARY_HTML
# echo "					<td class=\"num\">"$NBFRAME"</td>" >> $SUMMARY_HTML
# echo "				</tr>" >> $SUMMARY_HTML
# echo "				<tr>" >> $SUMMARY_HTML
# echo "					<td>Calcul scale</td>" >> $SUMMARY_HTML
# echo "					<td class=\"num\">"$SCALE"</td>" >> $SUMMARY_HTML
# echo "				</tr>" >> $SUMMARY_HTML
# echo "			</tbody>" >> $SUMMARY_HTML
# echo "		</table>" >> $SUMMARY_HTML
echo "		<table id=\"rounded-corner\">" >> $SUMMARY_HTML
echo "			<thead>" >> $SUMMARY_HTML
echo "				<tr>" >> $SUMMARY_HTML
echo "					<th scope=\"col\" class=\"rounded-head-left\"></th>" >> $SUMMARY_HTML
echo "					<th scope=\"col\" colspan=3 class=\"rounded-head-right\">"$OUTPUT_TAG"</th>" >> $SUMMARY_HTML
echo "				</tr>" >> $SUMMARY_HTML
echo "				<tr>" >> $SUMMARY_HTML
echo "					<th scope=\"col\">Actor</th>" >> $SUMMARY_HTML
echo "					<th scope=\"col\">Cycle count</th>" >> $SUMMARY_HTML
echo "					<th scope=\"col\">FPS@"$FREQUENCY"MHz</th>" >> $SUMMARY_HTML
echo "					<th scope=\"col\">Status</th>" >> $SUMMARY_HTML
echo "				</tr>" >> $SUMMARY_HTML
echo "			</thead>" >> $SUMMARY_HTML
echo "			<tbody>" >> $SUMMARY_HTML

#Summary log
for i in processor_*.log; 
	do 
	OKKO="OK";
	CHAINE1="Cycle count = ";
	CHAINE2=`grep -i "Cycle count = " $i`;
	CYCLES=${CHAINE2##*$CHAINE1};
	# FPS @ 50MHz = Nb_frame / (CycleCount / 50 000 000)
	FPS_RES=`echo "scale=$SCALE;$NBFRAME/($CYCLES/($FREQUENCY*1000000))"| bc`;
	FPS=`printf "%.2f" "${FPS_RES/\./,}"`;
	FILE_NAME=`basename $i .log`;
	PRE_NAME="processor_";
	ACTOR_NAME=${FILE_NAME##*$PRE_NAME};
	NBERROR=`grep -c Error $i`;
	if [ $NBERROR -gt 0 ]
	then 
		OKKO="KO"
	fi 
	printf "%2s %42s   Cycle count = %9s    FPS@%2sMHz = %10s \n" "$OKKO" "$ACTOR_NAME" "$CYCLES" "$FREQUENCY" "$FPS" >> $SUMMARY_LOG;
	printf "%s,%s,%s,%s\n" "$ACTOR_NAME" "$CYCLES" "$FPS" "$OKKO" >> $SUMMARY_CSV;
	echo "				<tr>" >> $SUMMARY_HTML
	echo "					<td>"$ACTOR_NAME"</td>" >> $SUMMARY_HTML
	echo "					<td class=\"num\">"$CYCLES"</td>" >> $SUMMARY_HTML
	echo "					<td class=\"num\">"$FPS"</td>" >> $SUMMARY_HTML
	if [ $NBERROR -gt 0 ]
	then 
		echo "					<td class=\"ko\">"$OKKO"</td>" >> $SUMMARY_HTML
	else
		echo "					<td class=\"ok\">"$OKKO"</td>" >> $SUMMARY_HTML
	fi 
	echo "				</tr>" >> $SUMMARY_HTML
done

# Summary log footer
echo "Nb of actors OK = " `grep -c Error processor_*.log | grep ":0" | wc -l` >> $SUMMARY_LOG
echo "Nb of actors KO = " `grep -c Error processor_*.log | grep -v ":0" | wc -l` >> $SUMMARY_LOG

echo "			</tbody>" >> $SUMMARY_HTML
echo "			<tfoot>" >> $SUMMARY_HTML
echo "				<tr>" >> $SUMMARY_HTML
echo "					<td class=\"rounded-foot-left\"></td>" >> $SUMMARY_HTML
echo "					<td colspan=\"3\" class=\"rounded-foot-right\"></td>" >> $SUMMARY_HTML
echo "				</tr>" >> $SUMMARY_HTML
echo "			</tfoot>" >> $SUMMARY_HTML
echo "		</table>" >> $SUMMARY_HTML
echo "    </body>" >> $SUMMARY_HTML
echo "</html>" >> $SUMMARY_HTML

# Archiving logs
echo "...Archiving in directory "$OUTPUT_TAG
cp processor_*.log $OUTPUT_TAG
mv $SUMMARY_LOG $OUTPUT_TAG
mv $SUMMARY_CSV $OUTPUT_TAG
mv $SUMMARY_HTML $OUTPUT_TAG
cp ~/tools/style.css $OUTPUT_TAG

# Generate a PDF file from HTML Summary
wkhtmltopdf $OUTPUT_TAG/$SUMMARY_HTML $OUTPUT_TAG/$SUMMARY_PDF

# Show summary log in stdout
echo ""
cat $OUTPUT_TAG/$SUMMARY_LOG
exit 0