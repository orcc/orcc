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

network idct2d (INP_SZ, PIX_SZ) \in\, signed ==> out :

var
	MEM_SZ = 16;
	OUT_SZ = 10;
	
entities

	rowsort = RowSort(sz = INP_SZ);
	
	fairmerge = FairMerge(rsz = INP_SZ, csz = MEM_SZ);
	
	downsample = Downsample();
	
	idct1d = idct1d(MEM_SZ = MEM_SZ);
	
	sep = Separate(isz = MEM_SZ, rsz = MEM_SZ, csz = OUT_SZ);

	trans = Transpose(sz = MEM_SZ);
	
	retrans = Retranspose(isz = OUT_SZ, osz = OUT_SZ);
	
	clip = Clip(isz = OUT_SZ, osz = PIX_SZ);

structure

	\in\ --> rowsort.ROW;
	signed --> clip.SIGNED;

	rowsort.Y0 --> fairmerge.R0;
	rowsort.Y1 --> fairmerge.R1;
	
	fairmerge.Y0 --> idct1d.X0;
	fairmerge.Y1 --> idct1d.X1;
	fairmerge.ROWOUT --> downsample.R;
	fairmerge.ROWOUT --> idct1d.ROW;

	downsample.R2 --> sep.ROW;

	idct1d.Y0 --> sep.X0;
	idct1d.Y1 --> sep.X1;
	idct1d.Y2 --> sep.X2;
	idct1d.Y3 --> sep.X3;

	sep.R0 --> trans.X0;
	sep.R1 --> trans.X1;
	sep.R2 --> trans.X2;
	sep.R3 --> trans.X3;
	sep.C0 --> retrans.X0;
	sep.C1 --> retrans.X1;
	sep.C2 --> retrans.X2;
	sep.C3 --> retrans.X3;

	trans.Y0 --> fairmerge.C0;
	trans.Y1 --> fairmerge.C1;

	retrans.Y --> clip.I;
	
	clip.O --> out;

end
