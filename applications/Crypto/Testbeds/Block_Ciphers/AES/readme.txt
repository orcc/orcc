Testbeds of RVC-CAL Implementation of AES (Rijndael)

**********************************************************

Shujun Li, Junaid Jameel Ahmad @ Uni-Konstanz

16 August, 2010

**********************************************************

== I/O FUs ==

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
Validator_AES_1B.cal: the same as Validator_AES.cal but showing results byte by byte

== Testbeds (ECB mode) ==

---- Test vectors and golden response ----

AES_Cipher_testbed.xdf
AES_InvCipher_testbed.xdf

---- Read a file and encrypt/decrypt it ----

AES_Cipher_Orcc.xdf
AES_InvCipher_Orcc.xdf
