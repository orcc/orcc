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

network acdc 
	(MAXW_IN_MB, MB_COORD_SZ, BTYPE_SZ, SAMPLE_SZ, NEWVOP, INTRA, INTER,
	 QUANT_MASK, ROUND_TYPE, FCODE_MASK, FCODE_SHIFT, MOTION, ACCODED, ACPRED,
	 QUANT_SZ) BTYPE, data ==> signed, out:
	 
entities

	seq = Sequence(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ROUND_TYPE = ROUND_TYPE,
		FCODE_MASK = FCODE_MASK,
		FCODE_SHIFT = FCODE_SHIFT
	);
	
	dcsplit = DCSplit(SAMPLE_SZ = SAMPLE_SZ);
	
	dcpred = DCPred(
		DCVAL = 128 * 8,
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
	
	zzaddr = ZigzagAddr();
	
	zigzag = Zigzag(SAMPLE_SZ = SAMPLE_SZ);
	
	acpred = ACPred(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		SAMPLE_SZ = SAMPLE_SZ
	);
	
	dequant = Dequant(SAMPLE_SZ = SAMPLE_SZ, QUANT_SZ = QUANT_SZ);
	
structure

	BTYPE --> seq.BTYPE;
	BTYPE --> dcpred.BTYPE;
	data --> dcsplit.IN;

	seq.A --> dcpred.A;
	seq.B --> dcpred.B;
	seq.C --> dcpred.C;
	
	dcsplit.DC --> dcpred.IN;
	dcsplit.AC --> zigzag.AC;

	dcpred.SIGNED --> signed; 
	dcpred.QUANT --> dequant.QP;
	dcpred.OUT --> dequant.DC;
	dcpred.PTR --> acpred.PTR;
	dcpred.START --> zzaddr.START;
	dcpred.START --> zigzag.START;
	dcpred.START --> acpred.START;
	
	zzaddr.ADDR --> zigzag.ADDR;

	zigzag.OUT --> acpred.AC;
	
	acpred.OUT --> dequant.AC;
	
	dequant.OUT --> out;	

end