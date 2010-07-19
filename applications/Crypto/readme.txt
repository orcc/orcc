RVC-CAL Crypto library (with testbeds)

*****************************************************

Shujun Li, Junaid Jameel Ahmad @ Uni-Konstanz

19 July 2010

*****************************************************

This library is a collection of cryptosystems implemented with RVC-CAL and tested with Orcc/OpenDF.

Each cryptosystem is a self-contained set of CAL/XDF/layout files that you can directly use and test.

In the root folder of each cryptosystem (like "Block_Ciphers\AES"), you can find the core files.

The sub-folder "Testbed" accommodates files needed for testing the core files.
Due to the discrepancies between Orcc and OpenDF, we chose to keep the files to pass Orcc simulation.
If you want to test the files with OpenDF, you need to do the following:
-- copy all files in the root folder to the sub-folder "Testbed";
-- read the "readme.txt" file in the root folder to find out if they need to comment/uncomment some lines in some CAL files;
-- try to run those XDF files ending with "_OpenDF.xdf".
