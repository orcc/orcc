module usedw_to_status (usedw, full, status);

		input [7:0] usedw;
		input full;
		output [8:0] status;

		assign status[0] = usedw[0];
		assign status[1] = usedw[1];
		assign status[2] = usedw[2];
		assign status[3] = usedw[3];
		assign status[4] = usedw[4];
		assign status[5] = usedw[5];
		assign status[6] = usedw[6];
		assign status[7] = usedw[7];
		assign status[8] = full;

endmodule 