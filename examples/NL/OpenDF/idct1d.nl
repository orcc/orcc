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

network idct1d (MEM_SZ) X0, X1, ROW ==> Y0, Y1, Y2, Y3:

var

	COEFF_SZ = 13;
	SCALE_SZ = 30;
	ACC_SZ = 24;

entities

	scale = Scale(isz = MEM_SZ, osz = SCALE_SZ, csz = COEFF_SZ);
	
	combine = Combine(isz = SCALE_SZ, osz = ACC_SZ);
	
	shufflefly = ShuffleFly(sz = ACC_SZ);

	shuffle = Shuffle(sz = ACC_SZ);
	
	final = Final(isz = ACC_SZ, osz = MEM_SZ);
	
structure

	X0 --> scale.X0;
	X1 --> scale.X1;
	ROW --> combine.ROW;

	scale.Y0 --> combine.X0;
	scale.Y1 --> combine.X1;
	scale.Y2 --> combine.X2;
	scale.Y3 --> combine.X3;
	
	combine.Y0 --> shufflefly.X0;
	combine.Y1 --> shufflefly.X1;

	shufflefly.Y0 --> shuffle.X0;	
	shufflefly.Y1 --> shuffle.X1;	
	shufflefly.Y2 --> shuffle.X2;	
	shufflefly.Y3 --> shuffle.X3;	
	
	shuffle.Y0 --> final.X0;
	shuffle.Y1 --> final.X1;
	shuffle.Y2 --> final.X2;
	shuffle.Y3 --> final.X3;
	
	final.Y0 --> Y0;
	final.Y1 --> Y1;
	final.Y2 --> Y2;
	final.Y3 --> Y3;
	
end