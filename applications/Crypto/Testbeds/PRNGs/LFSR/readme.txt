RVC-CAL Implementation of Linear Forward Shift Register (LFSR)

**********************************************************

Junaid Jameel Ahmad, Shujun Li @ Uni-Konstanz
26 August, 2010

**********************************************************

This is testbed for LFSR and it has been tested under Orcc.

LFSR_testbed.XDF: the network
Source_LFSR.cal: sends initial parameters to LFSR and golden response stream to Validator. 
Validator_QWordStream.cal: validates the LFSR's output bit stream with the golden response bit stream.