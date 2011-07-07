// --------------------------------------------------------------------
// Copyright (c) 2010 by Terasic Technologies Inc. 
// --------------------------------------------------------------------
//
// Permission:
//
//   Terasic grants permission to use and modify this code for use
//   in synthesis for all Terasic Development Boards and Altera Development 
//   Kits made by Terasic.  Other use of this code, including the selling 
//   ,duplication, or modification of any portion is strictly prohibited.
//
// Disclaimer:
//
//   This VHDL/Verilog or C/C++ source code is intended as a design reference
//   which illustrates how these types of functions can be implemented.
//   It is the user's responsibility to verify their design for
//   consistency and functionality through the use of formal
//   verification methods.  Terasic provides no warranty regarding the use 
//   or functionality of this code.
//
// --------------------------------------------------------------------
//           
//                     Terasic Technologies Inc
//                     356 Fu-Shin E. Rd Sec. 1. JhuBei City,
//                     HsinChu County, Taiwan
//                     302
//
//                     web: http://www.terasic.com/
//                     email: support@terasic.com
//
// --------------------------------------------------------------------
//
// Major Functions:	Button Debounce
//
// --------------------------------------------------------------------
//
// Revision History :
// --------------------------------------------------------------------
//   Ver  :| Author            :| Mod. Date :| Changes Made:

// --------------------------------------------------------------------

module button_debouncer	(
							clk,
							rst_n,
							data_in,
							data_out			
						);
					
input						clk;
input						rst_n;
input						data_in;
output						data_out;
									

//=======================================================
//  REG/WIRE declarations
//=======================================================
parameter	preset_val 	= 0;
parameter 	counter_max = 100000; 


reg						data_out;									
reg						data_in_0;	
reg						data_in_1;
reg						data_in_2;
reg						data_in_3;
reg			[20:0]		counter;
	
//=======================================================
//  Structural coding
//=======================================================

always	@(posedge clk or negedge rst_n)
begin
	if	(!rst_n)
	begin
		data_out		<=	preset_val;
		counter			<=	counter_max;
		data_in_0		<=	0;
		data_in_1		<=	0;
		data_in_2		<=	0;
		data_in_3		<=	0;			
	end else begin
		if	(counter == 0) 
		begin
			data_out	<=	data_in_3;
			counter		<=	counter_max;
		end else begin
			counter		<=	counter - 1;
		end
		data_in_0	<=	data_in;	
		data_in_1	<=	data_in_0;
		data_in_2	<=	data_in_1;
		data_in_3	<=	data_in_2;
	end
end
			
				

endmodule
