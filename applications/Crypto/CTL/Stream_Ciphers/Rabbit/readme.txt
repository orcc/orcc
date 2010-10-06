RVC-CAL Implementation of eStreams' Rabbit Stream Cipher

**********************************************************

Junaid Jameel Ahmad @ Uni-Konstanz

09 September, 2010

**********************************************************

eStreams' Rabbit stream cipher has been implemented by following their specification in
http://www.ecrypt.eu.org/stream/p3ciphers/rabbit/rabbit_p3.pdf or 
http://www.cryptico.com/Files/Filer/WP_Rabbit_Specification.pdf 
This implementation is composed of the following files.

== Basic FUs ==

All basic FUs were written to be compliant with the subset of RVC-CAL supported by Orcc.
-- Core FUs --

Rabbit.cal

-- Utility FUs used --

Bigger2Smaller.cal
Smaller2Bigger.cal

Note: Utilities are located in CTL\Utilities folder.

-- FU network --

Rabbit_Cipher.xdf -- Rabbit cipher to encrypt/decrypt.
KeyStreamGenerator.xdf -- Rabbit key stream generator.
