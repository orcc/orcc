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

network texture_8x8(MAXW_IN_MB,MB_COORD_SZ,BTYPE_SZ,SAMPLE_SZ,
						NEWVOP,INTRA,INTER,QUANT_MASK,ACPRED,ACCODED,
						QUANT_SZ) 
						BTYPE, QFS ==> f :

entities

	DCsplit = GEN_mgnt_DCSplit(
		SAMPLE_SZ = SAMPLE_SZ
	);
	
	DCRecontruction = DC_Reconstruction_8x8(
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
 		
	IS = MPEG4_algo_IS(
		SAMPLE_SZ = SAMPLE_SZ
	);
	
	IAP = MPEG4_algo_IAP_8x8(
		MAXW_IN_MB = MAXW_IN_MB, 
		MB_COORD_SZ = MB_COORD_SZ,
		SAMPLE_SZ = SAMPLE_SZ
	);
	
	IQ = MPEG4_algo_Inversequant(
		SAMPLE_SZ = SAMPLE_SZ, 
		QUANT_SZ = QUANT_SZ
	);
	
	idct2d = idct2d ( ); 
	
structure

	BTYPE 	--> DCRecontruction.BTYPE;
	QFS		--> DCsplit.IN; 
	
	DCsplit.DC	--> DCRecontruction.QFS_DC;
	DCsplit.AC	--> IS. QFS_AC;
	
	IS.PQF_AC 	--> IAP.PQF_AC;
	IAP.QF_AC	--> IQ.AC;
	IQ.OUT		--> idct2d.\in\; 
	
	idct2d.out 	--> f; 
	
	DCRecontruction.SIGNED		--> idct2d.signed;
	DCRecontruction.QUANT		--> IQ.QP;
	DCRecontruction.QF_DC		--> IQ.DC;
	DCRecontruction.PTR			--> IAP.PTR;
	DCRecontruction.AC_PRED_DIR	--> IAP.AC_PRED_DIR; 
	DCRecontruction.AC_PRED_DIR	--> IS.AC_PRED_DIR; 

end