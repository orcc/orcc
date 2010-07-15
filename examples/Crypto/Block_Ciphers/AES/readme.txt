*********** CAL Implementation of AES Standard ***********

Shujun Li, Junaid Jameel Ahmad @ Uni-Konstanz, 15 July, 2010

**********************************************************

This CAL implementation of AES Standard (FIPS 197) is composed of the following files.
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

AES_Cipher.xdf: AES encipher (input ports: plaintext and key, output port: ciphertext)

AES_InvCipher.xdf: AES decipher (input ports: ciphertext and key, output port: plaintext)

== Testbeds ==

-- Source FUs --

Source.cal: dummy FU for Orcc

Source_AES_Cipher.cal: Source FU with test vectors and golden responses for AES encipher
Source_AES_InvCipher.cal: Source FU with test vectors and golden responses for AES decipher

-- KeyGenerator FU --

KeyGenerator_AES.cal: generating key for AES encipher and decipher

-- Display FUs --

Display_Byte.cal: Display FU showing results as hexadecimal numbers

-- Validator FU --

Validator_AES.cal: comparing the output of a FU against a golden response (used for validating the functionalities of AES encipher and decipher)

-- FU Networks --

---- Testbeds for Orcc (Test vectors and golden response) ----

AES_Cipher_testbed.xdf
AES_InvCipher_testbed.xdf

---- Testbeds for Orcc (Read a file and encrypt/decrypt it) ----

AES_Cipher_Orcc.xdf
AES_InvCipher_Orcc.xdf
