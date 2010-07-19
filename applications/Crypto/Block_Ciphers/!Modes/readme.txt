*********** CAL Implementation of Cryptographic Operation Modes ***********

Junaid Jameel Ahmad, Shujun Li @ Uni-Konstanz

18 July, 2010

**********************************************************

This directory contains the CAL implementation of three block cipher modes of operation:
1) Cipher Block Chaining Mode: CBC.cal
2) Cipher Feedback Mode: CFB.cal
3) Output Feedback Mode: OFB.cal

These FUs have to be connected with a block cipher FU (ECB mode).
Information about how to connect them is available in the header comments of each file.

Testbed_AES.zip contains examples of different modes of AES and testbeds.
All the testbeds in Testbed_AES.zip have been tested with both Orcc and OpenDF.

To test with OpenDF, you need to uncomment some lines in Testbed\Validator_AES.cal or Testbed\Validator_AES_1B.cal.
Search for "OpenDF" to find where these lines are.