RVC-CAL Implementation of TDES (FIPS 46-3)

**********************************************************

Junaid Jameel Ahmad, Shujun Li @ Uni-Konstanz

14 August, 2010

**********************************************************

This RVC-CAL implementation of TDES (FIPS 46-3, NIST SP 800-67) is based on DES implementation (available under package CTL.Block_Ciphers.DES).
Therefore, folder Only have the XDF networks for all TDES encipher/decipher.

== TDES encipher and decipher as FU networks ==

-- ECB mode --

TDES_Encipher.xdf
TDES_Decipher.xdf

-- CBC mode --

TDES_CBC_Encipher.xdf
TDES_CBC_Decipher.xdf

-- 8-bit CFB mode --

TDES_CFB8_Encipher.xdf
TDES_CFB8_Decipher.xdf

-- 64-bit CFB mode --

TDES_CFB64_Encipher.xdf
TDES_CFB64_Decipher.xdf

-- OFB mode --

TDES_OFB_Cipher.xdf (encipher/decipher)

-- CTR mode --

TDES_CTR_Cipher.xdf (encipher/decipher)



Note: CBC, CFB, OFB and CTR modes have external dependencies on the CAL files in CAL.Block_Ciphers.Modes.