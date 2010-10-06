RVC-CAL Implementation of Linear Congruential Generator (LCG)

**********************************************************

Junaid Jameel Ahmad, Shujun Li @ Uni-Konstanz
26 August, 2010

**********************************************************
There are two testbeds for 32-bit and 64 bit numbers. Both of these testbeds have been tested under Orcc.

-- LCG32 --
LCG32_testbed.XDF: the network 
Source_LCG32.cal: sends initial parameters to LCG32 and golden response stream to Validator.
Validator_DWordStream.cal: validates the LCG32's output stream with the golden response stream.


-- LCG64 --
LCG64_testbed.XDF: the network
Source_LCG64.cal: sends initial parameters to LCG64 and golden response stream to Validator.
Validator_QWordStream.cal: validates the LCG64's output stream with the golden response stream.

Note: Due to un-availability of Big Integers manipulation in Orcc, the output stream of this FU has not been verified yet.