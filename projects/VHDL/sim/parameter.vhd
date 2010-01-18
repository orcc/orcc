-------------------------------------------------------------------------------
-- Title      : Parameter (modified)
-- Project    : ORCC - CAL2HDL
-------------------------------------------------------------------------------
-- File       : Parameter.vhd
-- Author     : LTD
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-01-14
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
--
-- Copyright (c) 2009, Lead Tech Design
-- All rights reserved.
-- 
-- Modification and sales of the source are not permitted.
--
-- Redistribution and use in source and binary forms  are permitted provided
-- that the following conditions are met:
-- 
--  -- Redistributions of source code must retain the above copyright notice,
--     this list of conditions and the following disclaimer.
--  -- Redistributions in binary form must reproduce the above copyright notice,
--     this list of conditions and the following disclaimer in the documentation
--     and/or other materials provided with the distribution.
--  -- Neither the name of Lead Tech Design nor the names of its
--     contributors may be used to endorse or promote products derived from this
--     code without specific prior written permission.
-- 
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
-- ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
-- LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
-- CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
-- SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
-- INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
-- STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
-- WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
-- SUCH DAMAGE.
--
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
--             1.0      LTD      Created
-- 2009-03-12  2.0      Nicolas  Updated
-------------------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

package parameter is

  -----------------------------------------------------------------------------
  -- Constant definition
  -----------------------------------------------------------------------------
  constant data_size : integer := 9;

end;
