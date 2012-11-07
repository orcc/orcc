-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  AddN_generate.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle d'un systeme
--                  sequentiel avec for generate a taille variable
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--	 Additionneur 4 bits utilisant un Add1 et les instructions generate
-----------------------------------------------------------------------


--¦ Description additionneur N bits ¦------------------------------------------

library IEEE;
  use IEEE.Std_Logic_1164.all;

entity AddN is
  generic ( Nb_Bits : Positive := 4);
  port (Nbr_A_i, Nbr_B_i : in  Std_Logic_Vector(Nb_Bits-1 downto 0);
        Carry_o          : out Std_Logic;
        Somme_o          : out Std_Logic_Vector(Nb_Bits-1 downto 0) );
end AddN;

architecture Struct of AddN is
  component Add1 is
    port (A_i, B_i, Cin_i   : in  Std_Logic;
          S_o, Cout_o       : out Std_Logic );
  end component;
  for all : Add1 use entity work.Add1(Logique);

  signal Vect_C_s : Std_logic_Vector(Nb_Bits-1 downto 0);
  
begin

  StrucAdd: for I in 0 to Nb_Bits-1 generate
    --Premier addtionneur : pas de C_i, addtionneur simplifie
    Add1er: if I = 0 generate
      Somme_o(I)  <= Nbr_A_i(I) xor Nbr_B_i(I);
      Vect_C_s(I) <= Nbr_A_i(I) and Nbr_B_i(I);
    end generate;
    
    Add_N: if I > 0 generate
      I_Add: Add1 port map (A_i    => Nbr_A_i(I),
                            B_i    => Nbr_B_i(I),
                            Cin_i  => Vect_C_s(I-1),
                            S_o    => Somme_o(I),
                            Cout_o => Vect_C_s(I)  );     
      
    end generate;
  
  end generate;
  
  --affectation du Carry de sortie
  Carry_o <= Vect_C_s(3);
  
end Struct;
