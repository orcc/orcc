-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Add8_gen.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle d'un systeme
--                  sequentiel avec generic
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--	 Additionneur 8 bits utilisant un AddN avec un generique
-----------------------------------------------------------------------

library IEEE;
  use IEEE.Std_Logic_1164.all;

entity Add8 is
  port (Nbr_A_i, Nbr_B_i : in  Std_Logic_Vector(7 downto 0);
        Carry_o          : out Std_Logic;
        Somme_o          : out Std_Logic_Vector(7 downto 0) );
end Add8;

architecture Struct of Add8 is
  component AddN
    generic ( Nb_Bits : Positive := 4);
    port (Nbr_A_i, Nbr_B_i : in  Std_Logic_Vector(Nb_Bits-1 downto 0);
          Carry_o          : out Std_Logic;
          Somme_o          : out Std_Logic_Vector(Nb_Bits-1 downto 0) );
  end component;
  for all : AddN use entity work.AddN(Struct);

  signal Vect_C_s : Std_logic_Vector(3 downto 0);
  
begin

   I0: AddN 
      generic map (Nb_Bits => Nbr_A_i'length)  
      port map (Nbr_A_i => Nbr_A_i,
                Nbr_B_i => Nbr_B_i,
                Carry_o => Carry_o,
                Somme_o => Somme_o        );     
       
end Struct;
