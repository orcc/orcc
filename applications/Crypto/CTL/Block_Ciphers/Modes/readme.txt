RVC-CAL Implementation of Block Cipher Modes of Operation

**********************************************************

Junaid Jameel Ahmad, Shujun Li @ Uni-Konstanz

28 July, 2010

**********************************************************

This directory contains the CAL implementation of four block cipher modes of operation:
1) Cipher Block Chaining Mode: CBC.cal
2) Cipher Feedback Mode: CFB.cal
3) Output Feedback Mode: OFB.cal
4) Counter Mode: CTR.cal

These FUs have to be connected with a block cipher FU (ECB mode).
Information about how to connect them is available in the header comments of each file.