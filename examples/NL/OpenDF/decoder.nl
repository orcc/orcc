/* 
BEGINCOPYRIGHT X
	
	Copyright (c) 2007, Xilinx Inc.
	All rights reserved.
	
	Redistribution and use in source and binary forms, 
	with or without modification, are permitted provided 
	that the following conditions are met:
	- Redistributions of source code must retain the above 
	  copyright notice, this list of conditions and the 
	  following disclaimer.
	- Redistributions in binary form must reproduce the 
	  above copyright notice, this list of conditions and 
	  the following disclaimer in the documentation and/or 
	  other materials provided with the distribution.
	- Neither the name of the copyright holder nor the names 
	  of its contributors may be used to endorse or promote 
	  products derived from this software without specific 
	  prior written permission.
	
	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
	CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
	INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
	DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
	CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
	SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
	HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
	CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
	OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	
ENDCOPYRIGHT
*/

network decoder () bits, MCD ==> VIDEO, MBD, MBA, MCA :

var
	MAXW_IN_MB = 121;
	MAXH_IN_MB = 69;
	
	ADDR_SZ = 24;
	PIX_SZ = 9;
	MV_SZ = 9;
	SAMPLE_COUNT_SZ = 8;
	SAMPLE_SZ = 13;
	MB_COORD_SZ = 8;
	BTYPE_SZ = 12;

	NEWVOP = 2048;
	INTRA = 1024;
	INTER = 512;
	QUANT_MASK = 31;
	ROUND_TYPE = 32;
	FCODE_MASK = 448;
	FCODE_SHIFT = 6;
	ACPRED = 1;
	ACCODED = 2;
	FOURMV = 4;
	MOTION = 8;
	QUANT_SZ = 6;
	
	scs = 8;
	
entities
	serialize = byte2bit();

	parser = parser(
		SAMPLE_COUNT_SZ = scs, // SAMPLE_COUNT_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ROUND_TYPE = ROUND_TYPE,
		FCODE_MASK = FCODE_MASK,
		FCODE_SHIFT = FCODE_SHIFT,
		MOTION = MOTION,
		FOURMV = FOURMV,
		ACPRED = ACPRED,
		ACCODED = ACCODED,
		MV_SZ = MV_SZ
	);
	
	acdc = acdc(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ROUND_TYPE = ROUND_TYPE,
		FCODE_MASK = FCODE_MASK,
		FCODE_SHIFT = FCODE_SHIFT,
		MOTION = MOTION,
		ACPRED = ACPRED,
		ACCODED = ACCODED,
		QUANT_SZ = QUANT_SZ
	);
	
	idct2d = idct2d(
		INP_SZ = SAMPLE_SZ,
		PIX_SZ = PIX_SZ
	);
	
	motion = motion3(
		SEARCHWIN_IN_MB = 3,
		MAXH_IN_MB = MAXH_IN_MB,
		MAXW_IN_MB = MAXW_IN_MB,
		ADDR_SZ = ADDR_SZ,
		MV_SZ = MV_SZ,
		PIX_SZ = PIX_SZ,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		ACCODED = ACCODED,
		ROUND_TYPE = ROUND_TYPE,
		MOTION = MOTION
	);
	      
structure

	bits --> serialize.in8 {size = 1024;};
	MCD --> motion.MCD;

	serialize.out --> parser.BITS;    
	
	parser.BTYPE --> acdc.BTYPE;
	parser.BTYPE --> motion.BTYPE;
	parser.B --> acdc.data;
	parser.MV --> motion.MV;
	
	acdc.out --> idct2d.\in\;
	acdc.signed --> idct2d.signed;

	idct2d.out --> motion.TEX;
	
	motion.MBD --> MBD;
	motion.MBA --> MBA;
	motion.MCA --> MCA;

	motion.VID --> VIDEO;
end