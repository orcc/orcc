module addr_gen_2 (rstx, clk, ack, status, addr);

		input ack;
		input clk;
		input rstx;
		output status;
		output [9:0] addr;
				
		reg statusreg;
		reg [9:0] counter;
				
		assign status = statusreg;
		assign addr = counter;

      always @(posedge clk or negedge rstx)
      begin
				if(rstx == 0)
				begin
					counter <= 0;
					statusreg <= 1;
				end
				else
				begin
					if(ack == 1)
					begin
						counter <= counter + 1;
					end
					
					if(counter == 1023)
					begin
						statusreg <= 0;
					end
				end
		end

endmodule 