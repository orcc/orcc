if [ ! -f "test_mpeg4.command" ] ; then
newdir=`echo $0 | sed 's/test_mpeg4.command//'`
cd $newdir/../build/xilinx/Release
fi


./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/hit000.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/hit000.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/jvc000.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/jvc000.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/san000.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/san000.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/san001.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/I-VOP/san001.yuv

./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit001.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit001.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit002.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit002.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit003.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit003.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit004.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit004.yuv

echo intraQ interQ 
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit005.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit005.yuv
echo intraQ interQ 
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit006.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit006.yuv

./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit007.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit007.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit008.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit008.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit009.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit009.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit010.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit010.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit011.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit011.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit013.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit013.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit014.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit014.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc001.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc001.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc002.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc002.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc003.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc003.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc004.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc004.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc005.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc005.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc006.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc006.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc007.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc007.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc008.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc008.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc009.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc009.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc010.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc010.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc011.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc011.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc014.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc014.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc015.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc015.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc016.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc016.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc017.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc017.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc018.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc018.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc019.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc019.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc020.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc020.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc021.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc021.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san002.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san002.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san003.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san003.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san004.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san004.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san005.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san005.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san006.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san006.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san007.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san007.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san009.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san009.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san010.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san010.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san011.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san011.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san012.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san012.yuv


./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit016.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit016.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit017.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit017.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit018.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit018.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit019.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit019.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit020.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit020.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit021.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit021.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit022.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit022.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit023.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit023.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit024.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Overall/hit024.yuv

echo ./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat001.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat001.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat004.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat004.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat005.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat005.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat006.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat006.yuv

echo ./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat007.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat007.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat008.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat008.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat009.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat009.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat010.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat010.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat013.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat013.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat014.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat014.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat015.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat015.yuv

./Xilinx_top_compare  -i /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat016.m4v -o /Users/mraulet/Movies/MPEG4/CORE/I-VOP/mat016.yuv


echo ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs1.bits -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs1.yuv
echo ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs2.bits -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs2.yuv
echo ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs3.bits -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs3.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs4.cmp -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs4.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs5.cmp -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs5.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs6.cmp -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs6.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs7.cmp -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs7.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs8.bits -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs8.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs9.bits -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs9.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs10.cmp -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs10.yuv
echo ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs11.cmp -o /Users/mraulet/Movies/MPEG4/SIMPLE/vcon-scs11.yuv


echo XVID sequences
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/foreman_cif_xvid_384kbps_I_P.m4v -o /Users/mraulet/Movies/MPEG4/foreman_cif_xvid_384kbps_I_P.yuv
echo ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/foreman_cif_xvid_384kbps_I_P_B.m4v -o /Users/mraulet/Movies/MPEG4/foreman_cif_xvid_384kbps_I_P_B.yuv
echo ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/foreman_cif_xvid_700kbps_I_P_B.m4v -o /Users/mraulet/Movies/MPEG4/foreman_cif_xvid_700kbps_I_P_B.yuv


./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc013.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc013.yuv

./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit012.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/hit012.yuv
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san008.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san008.yuv

./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san014.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san014.yuv


echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc012.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/jvc012.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san013.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san013.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san015.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san015.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san016.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san016.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san017.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san017.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san018.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san018.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san019.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san019.yuv

echo intraQ
./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san020.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/P-VOP/san020.yuv


echo parser not ok HEC and resync_marker ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Error/hit025.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Error/hit025.yuv
echo parser not ok HEC and resync_marker ./Xilinx_top_compare -i /Users/mraulet/Movies/MPEG4/SIMPLE/Error/hit026.m4v -o /Users/mraulet/Movies/MPEG4/SIMPLE/Error/hit026.yuv

