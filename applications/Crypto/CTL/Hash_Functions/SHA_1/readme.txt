RVC-CAL Implementation of SHA-2 (FIPS-180-3)

**********************************************************

Junaid Jameel Ahmad @ Uni-Konstanz

03 September, 2010

**********************************************************

Secure Hash Algorithms (SHA-1) has been implemented by following NIST's FIPS-180-3 
and is composed of the following files.

== Basic FUs ==

All basic FUs were written to be compliant with the subset of RVC-CAL supported by Orcc.

-- Common Kernel FUs used--

Preprocessor64.cal

Note: This FUs is located under package CTL.Hash_Functions.common.

-- Utility FUs used--

Smaller2Bigger.cal

Note: Utilities are located under package CTL.Utilities.

== SHA-1 FU network ==

SHA-160.xdf
