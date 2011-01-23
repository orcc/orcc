RVC-CAL Implementation of SHA-2 (FIPS-180-3)

**********************************************************

Junaid Jameel Ahmad @ Uni-Konstanz

03 September, 2010

**********************************************************

Secure Hash Algorithms (SHA-2) has been implemented by following NIST's FIPS-180-3 
and is composed of the following files.

== Basic FUs ==

All basic FUs were written to be compliant with the subset of RVC-CAL supported by Orcc.

-- Common Kernel FUs used--

Preprocessor64.cal
TruncateHash.cal

Note: These FUs are located under package CTL.Hash_Functions.common.

-- Utility FUs used--

Smaller2Bigger.cal

Note: Utilities are located under package CTL.Utilities.

== SHA-2 FU network ==

SHA-224.xdf
SHA-256.xdf

Note: SHA-384 and SHA-512 are not yet implemented because of the lack of big integers support in Orcc. 
They will be implemented after implementing a library for BigIntegers in CAL.
