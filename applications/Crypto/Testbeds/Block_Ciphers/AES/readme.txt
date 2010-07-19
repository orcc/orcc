Testbeds of RVC-CAL Implementation of AES (Rijndael)

**********************************************************

Shujun Li, Junaid Jameel Ahmad @ Uni-Konstanz

20 July, 2010

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

---- Testbeds for Orcc (Test vectors and golden response) ----

AES_Cipher_testbed.xdf
AES_InvCipher_testbed.xdf

---- Testbeds for OpenDF (Test vectors and golden response) ----

AES_Cipher_testbed_OpenDF.xdf
AES_InvCipher_testbed_OpenDF.xdf

---- Testbeds for Orcc (Read a file and encrypt/decrypt it) ----

AES_Cipher_Orcc.xdf
AES_InvCipher_Orcc.xdf

== Testbeds (CBC mode) ==

---- Testbeds for Orcc (Test vectors and golden response) ----

AES_CBC_Encipher_testbed.xdf
AES_CBC_Decipher_testbed.xdf

== Testbeds (OFB mode) ==

---- Testbeds for Orcc (Test vectors and golden response) ----

AES_OFB_Encipher_testbed.xdf
AES_OFB_Decipher_testbed.xdf

== Testbeds (8-bit CFB mode) ==

---- Testbeds for Orcc (Test vectors and golden response) ----

AES_CFB8_Encipher_testbed.xdf
AES_CFB8_Decipher_testbed.xdf

== Testbeds (128-bit CFB mode) ==

---- Testbeds for Orcc (Test vectors and golden response) ----

AES_CFB128_Encipher_testbed.xdf
AES_CFB128_Decipher_testbed.xdf
