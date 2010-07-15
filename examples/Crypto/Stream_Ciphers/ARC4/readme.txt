*********** CAL Implementation of Alleged RC4 (ARC4) ***********

Junaid Jameel Ahmad, Shujun Li @ Uni-Konstanz

15 July, 2010

**********************************************************

Due to its simplicity, this CAL implementation of ARC4 contains only one CAL file: ARC4.cal.

Testbed\ARC4_testbed.XDF is the testbed, which has been tested under both Orcc and OpenDF.
Testbed\Source_ARC4.cal and Testbed\Validator_ARC4.cal are used by the testbed to get data and validate the response.

To test with OpenDF, you need to uncomment some lines in Testbed\Validator_ARC4.cal.
Search for "OpenDF" to fine where these lines are.