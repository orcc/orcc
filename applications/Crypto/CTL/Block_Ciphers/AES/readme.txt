RVC-CAL Implementation of AES (Rijndael)

**********************************************************

Shujun Li, Junaid Jameel Ahmad @ Uni-Konstanz

8 August, 2010

**********************************************************

This RVC-CAL implementation of AES (FIPS 197) is composed of the following files.
The naming of cal files follow the terms used in FIPS 197.

The test vectors and golden responses are taken from FIPS 197.

All the three editions of AES are implemented: AES-128, AES-192, and AES-256.
The FUs are written to automatically recognize the key size and work in the corresponding edition.

To support parallel processing of multiple blocks, the output of each FU is expanded to include the round number.
That is, each 16-byte block is expanded to 17-byte block, whose first byte is the current round index of the block. 

== Basic FUs ==

All basic FUs were written to be compliant with the subset of RVC-CAL supported by Orcc.

-- Common FUs for both AES cipher and inverse cipher --

AddRoundKey.cal

-- For AES cipher (encipher) --

SubBytes.cal
ShiftRows.cal
MixColumns.cal

-- For AES inverse cipher (decipher) --

InvSubBytes.cal
InvShiftRows.cal
InvMixColumns.cal

== AES encipher and decipher as FU networks ==

-- ECB mode --

AES_Cipher.xdf: AES encipher (as described in NIST AES standard)
AES_InvCipher.xdf: AES decipher (as described in NIST AES standard)

-- CBC mode --

AES_CBC_Encipher.xdf: AES encipher
AES_CBC_Decipher.xdf: AES decipher

-- CFB mode --

AES_CFB8_Encipher.xdf: 8-bit CFB AES encipher
AES_CFB8_Decipher.xdf: 8-bit CFB AES decipher

AES_CFB128_Encipher.xdf: 128-bit CFB AES encipher
AES_CFB128_Decipher.xdf: 128-bit CFB AES decipher

-- OFB mode --

AES_OFB_Cipher.xdf: AES encipher/decipher

-- CTR mode --

AES_CTR_Cipher.xdf: AES encipher/decipher



Note: CBC, CFB, OFB and CTR modes have external dependencies on the CAL files are located under package CTL.Block_Ciphers.Modes.