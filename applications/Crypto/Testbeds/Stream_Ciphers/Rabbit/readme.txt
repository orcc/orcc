RVC-CAL Implementation of eStreams' Rabbit Stream Cipher

**********************************************************

Junaid Jameel Ahmad @ Uni-Konstanz
09 September, 2010

**********************************************************
This folder contains the testbed files for Rabbit and they have been verified using Orcc. Test vectors and their 
golden response taken from [1]. 
[1] http://www.cryptico.com/Files/Filer/WP_Rabbit_Specification.pdf.

-- Rabbit with out IV --
Rabbit_testbed.xdf: the network 
Source_Rabbit.cal: sends initial parameters to Rabbit and golden response stream to Validator.
Validator_1B: validates the Rabbit's key stream with the golden response stream.

-- Rabbit with IV --
Rabbit_IV_testbed.xdf: the network 
Source_Rabbit_IV.cal: sends initial parameters to Rabbit and golden response stream to Validator.
Validator_1B: validates the Rabbit's key stream with the golden response stream.
