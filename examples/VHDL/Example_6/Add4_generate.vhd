-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Div4_Synch.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle d'un systeme
--                  sequentiel avec for generate a taille fixe
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--	 Additionneur 4 bits utilisant un Add1 et les instructions generate
-----------------------------------------------------------------------

--¦ Description additionneur 1 bit ¦------------------------------------------

library IEEE;
  use IEEE.Std_Logic_1164.all;

entity Add1 is
  port (A_i, B_i, C_i : in  Std_Logic;
        S_o, C_o      : out Std_Logic );
end Add1;

architecture Logique of Add1 is
  signal Demi_Add_s : Std_Logic;
begin

  Demi_Add_s <= A_i xor B_i;
  S_o <= Demi_Add_s xor C_i;
  C_o <= (Demi_Add_s and C_i) or (A_i and B_i);

end Logique;


--¦ Description additionneur 4 bits ¦------------------------------------------

library IEEE;
  use IEEE.Std_Logic_1164.all;

entity Add4 is
  port (Nbr_A_i, Nbr_B_i : in  Std_Logic_Vector(3 downto 0);
        Carry_o          : out Std_Logic;
        Somme_o          : out Std_Logic_Vector(3 downto 0) );
end Add4;

architecture Struct of Add4 is
  component Add1 is
    port (A_i, B_i, C_i : in  Std_Logic;
          S_o, C_o      : out Std_Logic );
  end component;
  for all : Add1 use entity work.Add1(Logique);

  signal Vect_C_s : Std_logic_Vector(3 downto 0);
  
begin

  StrucAdd: for I in 0 to 3 generate
    --Premier addtionneur : pas de C_i, addtionneur simplifie
    Add1er: if I = 0 generate
      Somme_o(I)  <= Nbr_A_i(I) xor Nbr_B_i(I);
      Vect_C_s(I) <= Nbr_A_i(I) and Nbr_B_i(I);
    end generate;
    
    Add_N: if I > 0 generate
      I_Add: Add1 port map (A_i => Nbr_A_i(I),
                            B_i => Nbr_B_i(I),
                            C_i => Vect_C_s(I-1),
                            S_o => Somme_o(I),
                            C_o => Vect_C_s(I)  );     
      
    end generate;
  
  end generate;
  
  --affectation du Carry de sortie
  Carry_o <= Vect_C_s(3);
  
end Struct;
