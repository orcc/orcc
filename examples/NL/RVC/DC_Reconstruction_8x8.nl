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

network DC_Reconstruction_8x8 (DCVAL,MAXW_IN_MB,MB_COORD_SZ,BTYPE_SZ,
			SAMPLE_SZ,NEWVOP,INTRA,INTER,QUANT_MASK,ACCODED,ACPRED,
			QUANT_SZ) BTYPE, QFS_DC ==> PTR, AC_PRED_DIR, SIGNED, QF_DC, QUANT :

entities

	addressing = MPEG4_algo_DCRaddressing_8x8(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA
	);
	
	invpred = MPEG4_algo_DCRinvpred_chroma_8x8(
		DCVAL = 128*8, 
		MAXW_IN_MB = MAXW_IN_MB, 
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ACCODED = ACCODED,
		ACPRED = ACPRED,
		QUANT_SZ = QUANT_SZ
	);
	
structure

	BTYPE 	--> addressing.BTYPE;
	BTYPE	--> invpred.BTYPE; 
	
	QFS_DC	--> invpred.QFS_DC; 
	
	addressing.A	--> invpred.A; 
	addressing.B	--> invpred.B; 
	addressing.C	--> invpred.C;

	invpred.PTR 	--> PTR; 
	invpred.AC_PRED_DIR	--> AC_PRED_DIR; 
	invpred.SIGNED	--> SIGNED; 
	invpred.QF_DC 	--> QF_DC;
	invpred.QUANT	--> QUANT; 

end
