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

network parser (SAMPLE_COUNT_SZ, SAMPLE_SZ, MAXW_IN_MB, MB_COORD_SZ, BTYPE_SZ, NEWVOP, INTRA, INTER, QUANT_MASK,
                ROUND_TYPE, FCODE_MASK, FCODE_SHIFT, MOTION, FOURMV, ACPRED, ACCODED, MV_SZ) 
        BITS ==> BTYPE_Y, BTYPE_U, BTYPE_V, MV_Y, MV_U, MV_V, B_Y, B_U, B_V:

entities

	parseheaders = ParseHeaders(
		MAXW_IN_MB = MAXW_IN_MB,
		SAMPLE_COUNT_SZ = SAMPLE_COUNT_SZ,
		SAMPLE_SZ = SAMPLE_SZ,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		QUANT_MASK = QUANT_MASK,
		ROUND_TYPE = ROUND_TYPE,
		FCODE_MASK = FCODE_MASK,
		FCODE_SHIFT = FCODE_SHIFT,
		ACCODED = ACCODED,
		ACPRED = ACPRED,
		FOURMV = FOURMV,
		MOTION = MOTION,
		MV_SZ = MV_SZ
	);
	
	mvseq = MVSequence(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTER = INTER,
		FOURMV = FOURMV,
		MOTION = MOTION
	);
	
	blkexp = BlockExpand(
		SAMPLE_COUNT_SZ = SAMPLE_COUNT_SZ,
		SAMPLE_SZ = SAMPLE_SZ
	);
	
	mvrecon = MVReconstruct(
		MAXW_IN_MB = MAXW_IN_MB,
		MB_COORD_SZ = MB_COORD_SZ,
		BTYPE_SZ = BTYPE_SZ,
		MV_SZ = MV_SZ,
		NEWVOP = NEWVOP,
		INTER = INTER,
		FCODE_MASK = FCODE_MASK,
		FCODE_SHIFT = FCODE_SHIFT,
		FOURMV = FOURMV,
		MOTION = MOTION
	);
	
	splitter_BTYPE = splitter_BTYPE(
		BTYPE_SZ = BTYPE_SZ,
  		NEWVOP = NEWVOP
  	); 
  	
  	splitter_MV = splitter_MV(				
	  	BTYPE_SZ = BTYPE_SZ,
  		MV_SZ = MV_SZ,  
  		MOTION = MOTION,
  		NEWVOP = NEWVOP
	); 

	splitter_420_B = splitter_420_B(
		BLOCK_SZ = 64,
		SAMPLE_SZ = SAMPLE_SZ,
		BTYPE_SZ = BTYPE_SZ,
		NEWVOP = NEWVOP,
		INTRA = INTRA,
		INTER = INTER,
		ACCODED = ACCODED
	);

 

structure

	BITS --> parseheaders.bits;		

	parseheaders.BTYPE --> splitter_BTYPE.BTYPE;  
	parseheaders.BTYPE --> mvseq.BTYPE;  
	parseheaders.BTYPE --> mvrecon.BTYPE;  
	parseheaders.MV --> mvrecon.MVIN;  
	parseheaders.RUN --> blkexp.RUN;  
	parseheaders.VALUE --> blkexp.VALUE;
	parseheaders.LAST --> blkexp.LAST;
	
	mvseq.A --> mvrecon.A;
	
	blkexp.OUT --> splitter_420_B.B;
	parseheaders.BTYPE --> splitter_420_B.BTYPE;
	
	mvrecon.MV --> splitter_MV.MV;
	parseheaders.BTYPE --> splitter_MV.BTYPE;
	
	splitter_BTYPE.Y --> BTYPE_Y;
	splitter_BTYPE.U --> BTYPE_U;
	splitter_BTYPE.V --> BTYPE_V;
	
	splitter_MV.Y --> MV_Y;
	splitter_MV.U --> MV_U;
	splitter_MV.V --> MV_V;
	
	splitter_420_B.Y --> B_Y; 
	splitter_420_B.U --> B_U; 	
	splitter_420_B.V --> B_V; 

end
