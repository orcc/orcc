-------------------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenierie et de Gestion du canton de Vaud
-- Institut REDS, Reconfigurable & Embedded Digital Systems
--
-- Librairie    : Librairie ReDS_Lib_Base
--
-- Fichier      : or2_logic.vhd
-- Description  : OR a 2 entrees de 1 bit
--
-- Auteur       : REDS
-- Date         : 08.09.2008
-- Version      : 0.0
-- 
-- Utilise      : Exercice formation VHDL.
-- 
--| Modifications |------------------------------------------------------------
-- Version   Auteur Date               Description
-- 
-------------------------------------------------------------------------------

library IEEE;
  use IEEE.Std_Logic_1164.all;
  --use IEEE.Numeric_Std.all;

entity OR2 is
  port( 
    A_i : in  Std_Logic;   
    B_i : in  Std_Logic;   
    S_o : out Std_Logic    
  );
end OR2 ;

architecture Logic of OR2 is
begin

  S_o <= A_i or B_i;

end Logic;


