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

network motion (SEARCHWIN_IN_MB, MAXW_IN_MB, MAXH_IN_MB, ADDR_SZ, MV_SZ, PIX_SZ, MB_COORD_SZ, 
                 BTYPE_SZ, NEWVOP, INTRA, INTER, ACCODED, ROUND_TYPE, MOTION, LAYOUT) 
                MV, BTYPE, TEX, MCD ==> MBD, MBA, MCA, VID:

var
	FLAG_SZ = 4;
	BUF_SZ =   MAXW_IN_MB * ( 384 * MAXH_IN_MB + SEARCHWIN_IN_MB * 192 + 384 );
	
entities

	address = MPEG4_mgnt_Address(
		SEARCHWIN_IN_MB = SEARCHWIN_IN_MB,
		MAXW_IN_MB = MAXW_IN_MB,
		MAXH_IN_MB = MAXH_IN_MB,
		ADDR_SZ = ADDR_SZ,
		FLAG_SZ = FLAG_SZ,
		MV_SZ = MV_SZ,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		INTRA = INTRA,
		NEWVOP = NEWVOP,
		ROUND_TYPE = ROUND_TYPE,
		MOTION = MOTION,
		LAYOUT = LAYOUT // JWJ: LAYOUT does not exist --- should it be a parameter?
	); 
	
	buffer = MPEG4_mgnt_Framebuf(
		ADDR_SZ = ADDR_SZ,
		PIX_SZ = PIX_SZ,
		BUF_SZ = BUF_SZ
	);
	
	interpolation = MPEG4_algo_Interpolation_halfpel(
		PIX_SZ = PIX_SZ,
		FLAG_SZ = FLAG_SZ
	);
	
	add = MPEG4_algo_Add(
		PIX_SZ = PIX_SZ,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		ACCODED = ACCODED
	);	  
	
structure

	MV --> address.MV;

	BTYPE --> address.BTYPE;
	BTYPE --> add.BTYPE;
	
	TEX --> add.TEX;
	
	address.WA --> buffer.WA;
	address.RA --> buffer.RA;
	address.halfpel --> interpolation.halfpel; 
	
	buffer.RD --> interpolation.RD; 
	
	interpolation.MOT --> add.MOT; 
	
	add.VID --> buffer.WD; 
	add.VID --> VID;
	
end
