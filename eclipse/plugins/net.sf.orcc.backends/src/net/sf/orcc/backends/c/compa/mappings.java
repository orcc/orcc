package net.sf.orcc.backends.c.compa;

public class mappings {
	boolean[][][] mapping = { { {false,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true} }, 				// Mapping 1
							  { {false,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true}, 					// Mapping 2
								{false,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true} },
							  { {false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false},		// Mapping 3
							    {false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false},
							    {false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false} },
							  { {false,false,false,true,false,false,false,false,false,false,false,false,false,false,false},		// Mapping 4
								{false,false,false,false,true,false,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,true,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,true,false,false,false,false,false,false,false,false} },
							  { {false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},		// Mapping 5
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,true,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,false,false,false,true,false,false},		// Mapping 6
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
							    {false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,false,false,true,false,false,false},		// Mapping 7
								{false,false,false,false,false,false,false,false,false,false,false,false,true,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
							    {false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
							    {false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,false,false,true,false,false,false},		// Mapping 8
								{false,false,false,false,false,false,false,false,false,false,false,false,true,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,true,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,false,false,false,true,false,false},		// Mapping 9
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},		// Mapping 10
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},		// Mapping 11
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},		// Mapping 12
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},		// Mapping 13
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false} },
							  { {false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},		// Mapping 14
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false} },
							  { {false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false},		// Mapping 15
								{false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false},
								{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true} } };
//	
//		public void setMapping_2() {
//			// Mapping 2
//			// Mapping index 1, Processor index 0
//			mapping[1][0][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[1][0][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[1][0][ActorsIndex.MERGER.ordinal()] = true;
//			mapping[1][0][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = true;
//			mapping[1][0][ActorsIndex.PARSER_MVSEQ.ordinal()] = true;
//			mapping[1][0][ActorsIndex.PARSER_BLKEXP.ordinal()] = true;
//			mapping[1][0][ActorsIndex.PARSER_MVRECON.ordinal()] = false;
//			mapping[1][0][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = true;
//			mapping[1][0][ActorsIndex.TEXTURE_IS.ordinal()] = true;
//			mapping[1][0][ActorsIndex.TEXTURE_IAP.ordinal()] = true;
//			mapping[1][0][ActorsIndex.TEXTURE_IQ.ordinal()] = false;
//			mapping[1][0][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = true;
//			mapping[1][0][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[1][0][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[1][0][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[1][0][ActorsIndex.MOTION_ADD.ordinal()] = false;
//			mapping[1][0][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 1, Processor index 1
//			mapping[1][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[1][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[1][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[1][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[1][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = false;
//			mapping[1][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = false;
//			mapping[1][1][ActorsIndex.PARSER_MVRECON.ordinal()] = true;
//			mapping[1][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[1][1][ActorsIndex.TEXTURE_IS.ordinal()] = false;
//			mapping[1][1][ActorsIndex.TEXTURE_IAP.ordinal()] = false;
//			mapping[1][1][ActorsIndex.TEXTURE_IQ.ordinal()] = true;
//			mapping[1][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[1][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = true;
//			mapping[1][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = true;
//			mapping[1][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = true;			
//			mapping[1][1][ActorsIndex.MOTION_ADD.ordinal()] = true;
//			mapping[1][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = true;
//		}
//		
//		public void setMapping_7(){
//
//			// Mapping 8
//			// Mapping index 6, Processor index 0
//			mapping[6][0][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_MERGER.ordinal()] = true;
//			mapping[6][0][ActorsIndex.DECODER_PARSER_PARSEHEADERS.ordinal()] = true;
//			mapping[6][0][ActorsIndex.DECODER_PARSER_MVSEQ.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_PARSER_BLKEXP.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_PARSER_MVRECON.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_IS.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_IAP.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_IQ.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[6][0][ActorsIndex.DECODER_MOTION_ADD.ordinal()] = false;
//			mapping[6][0][ActorsIndex.DECODER_MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 6, Processor index 1
//			mapping[6][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = true;
//			mapping[6][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = true;
//			mapping[6][1][ActorsIndex.PARSER_MVRECON.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_IS.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_IAP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[6][1][ActorsIndex.MOTION_ADD.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 6, Processor index 2
//			mapping[6][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVRECON.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IAP.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_IQ.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[6][1][ActorsIndex.MOTION_ADD.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 6, Processor index 3
//			mapping[6][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVRECON.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IAP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = true;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = true;
//			mapping[6][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[6][1][ActorsIndex.MOTION_ADD.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 6, Processor index 4
//			mapping[6][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVRECON.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IAP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = true;			
//			mapping[6][1][ActorsIndex.MOTION_ADD.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 6, Processor index 5
//			mapping[6][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVRECON.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IAP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[6][1][ActorsIndex.MOTION_ADD.ordinal()] = true;
//			mapping[6][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = false;
//			
//			// Mapping index 6, Processor index 6
//			mapping[6][1][ActorsIndex.SOURCE.ordinal()] = false;
//			mapping[6][1][ActorsIndex.DISPLAY.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MERGER.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_PARSEHEADERS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVSEQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_BLKEXP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.PARSER_MVRECON.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCSPLIT.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IS.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IAP.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IQ.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_IDCT2D.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_ADDR.ordinal()] = false;
//			mapping[6][1][ActorsIndex.TEXTURE_DCRECONST_INVPRED.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_INTERPOLATION.ordinal()] = false;			
//			mapping[6][1][ActorsIndex.MOTION_ADD.ordinal()] = false;
//			mapping[6][1][ActorsIndex.MOTION_FRAMEBUF.ordinal()] = true;
//		}
	}
