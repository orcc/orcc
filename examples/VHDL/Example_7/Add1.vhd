-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Add1.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--      Additionneur 1 bits avec carry, description avec equation logique
-----------------------------------------------------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity Add1 is
   port( 
      A_i    : in     std_logic;
      B_i    : in     std_logic;
      Cin_i  : in     std_logic;
      Cout_o : out    std_logic;
      S_o    : out    std_logic  
   );

-- Declarations

end Add1 ;


architecture Logique of Add1 is
begin
  S_o <= A_i xor B_i xor Cin_i;
  Cout_o <= ((A_i xor B_i) and Cin_i) or (A_i and B_i);
end Logique;