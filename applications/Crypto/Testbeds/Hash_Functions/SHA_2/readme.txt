RVC-CAL Implementation of Secure Hash Algorithm (SHA-2)

**********************************************************

Junaid Jameel Ahmad @ Uni-Konstanz
03 September, 2010

**********************************************************
This folder contains the testbed files for SHA-2 and they have been verified using Orcc. Test vectors and their 
golden response for SHA-224 and SHA-256 are taken from [1] and [2] respectively. 
[1] http://csrc.nist.gov/groups/ST/toolkit/documents/Examples/SHA224.pdf.
[2] http://csrc.nist.gov/groups/ST/toolkit/documents/Examples/SHA256.pdf.

-- SHA-224 --
SHA-224_testbed.XDF: the network 
Source_SHA_224.cal: sends initial parameters to SHA-224 and golden response stream to Validator.
Validator_DWordStream.cal: validates the SHA-224's hash message stream with the golden response stream.

-- SHA-256 --
SHA-256_testbed.XDF: the network 
Source_SHA_256.cal: sends initial parameters to SHA-256 and golden response stream to Validator.
Validator_DWordStream.cal: validates the SHA-256's hash message stream with the golden response stream.
