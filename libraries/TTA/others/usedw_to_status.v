module usedw_to_status (usedw, full, status);

		input [7:0] usedw;
		input full;
		output [31:0] status;

		assign status[0] = usedw[0];
		assign status[1] = usedw[1];
		assign status[2] = usedw[2];
		assign status[3] = usedw[3];
		assign status[4] = usedw[4];
		assign status[5] = usedw[5];
		assign status[6] = usedw[6];
		assign status[7] = usedw[7];
		assign status[8] = full;
		assign status[9] = 0;
		assign status[10] = 0;
		assign status[11] = 0;
		assign status[12] = 0;
		assign status[13] = 0;
		assign status[14] = 0;
		assign status[15] = 0;
		assign status[16] = 0;
		assign status[17] = 0;
		assign status[18] = 0;
		assign status[19] = 0;
		assign status[20] = 0;
		assign status[21] = 0;
		assign status[22] = 0;
		assign status[23] = 0;
		assign status[24] = 0;
		assign status[25] = 0;
		assign status[26] = 0;
		assign status[27] = 0;
		assign status[28] = 0;
		assign status[29] = 0;
		assign status[30] = 0;
		assign status[31] = 0;
		
endmodule 
