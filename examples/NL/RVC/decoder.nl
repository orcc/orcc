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

network decoder () bits ==> VID :

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
	
entities
	serialize = byte2bit();

	parser = parser(
		SAMPLE_COUNT_SZ = SAMPLE_COUNT_SZ,
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
	
	texture_Y = texture_16x16(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ACPRED = ACPRED,
		ACCODED = ACCODED,
		QUANT_SZ = QUANT_SZ
	);
	
	texture_U = texture_8x8(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ACPRED = ACPRED,
		ACCODED = ACCODED,
		QUANT_SZ = QUANT_SZ
	);
	
	texture_V = texture_8x8(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ACPRED = ACPRED,
		ACCODED = ACCODED,
		QUANT_SZ = QUANT_SZ
	);
	
	motion_Y = motion(
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
		MOTION = MOTION,
		LAYOUT = 1
	);
	
	motion_U = motion(
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
		MOTION = MOTION,
		LAYOUT = 0
	);
	
	motion_V = motion(
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
		MOTION = MOTION,
		LAYOUT = 0
	);
	
	GEN_mgnt_Merger420 = GEN_mgnt_Merger420 (
		PIX_SZ = PIX_SZ
	);
	
	      
structure

	bits --> serialize.in8 {size = 1024;};

	serialize.out --> parser.BITS;    
	
	parser.MV_Y 	--> motion_Y.MV;
	parser.BTYPE_Y 	--> motion_Y.BTYPE;
	parser.BTYPE_Y 	--> texture_Y.BTYPE;
	parser.B_Y 		--> texture_Y.QFS; 
	texture_Y.f		--> motion_Y.TEX ;

	parser.MV_U 	--> motion_U.MV;
	parser.BTYPE_U 	--> motion_U.BTYPE;
	parser.BTYPE_U 	--> texture_U.BTYPE;
	parser.B_U 		--> texture_U.QFS; 
	texture_U.f		--> motion_U.TEX ;
	
	parser.MV_V 	--> motion_V.MV;
	parser.BTYPE_V 	--> motion_V.BTYPE;
	parser.BTYPE_V 	--> texture_V.BTYPE;
	parser.B_V 		--> texture_V.QFS; 
	texture_V.f		--> motion_V.TEX; 
			
	motion_Y.VID 	--> GEN_mgnt_Merger420.Y;
	motion_U.VID 	--> GEN_mgnt_Merger420.U;
	motion_V.VID 	--> GEN_mgnt_Merger420.V;
	
	GEN_mgnt_Merger420.YUV --> VID;

end