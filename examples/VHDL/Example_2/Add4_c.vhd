-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Add4_c.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle simple
--                  avec deux niveau de hierarchie
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--      Additionneur 4 bits avec carry
-----------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
--USE ieee.numeric_std.ALL;

entity Add4_c is
   port( 
      Cin_i   : in     std_logic;
      Nbr_A_i : in     Std_Logic_Vector (3 downto 0);
      Nbr_B_i : in     Std_Logic_Vector (3 downto 0);
      Cout_o  : out    std_logic;
      Somme_o : out    Std_Logic_Vector (3 downto 0)
   );

end Add4_c ;

architecture struct of Add4_c is

   -- Architecture declarations

   -- Internal signal declarations
   signal Cout_o1 : std_logic;
   signal Cout_o2 : std_logic;
   signal Cout_o3 : std_logic;


   -- Component Declarations
   component Add1_C
   port (
      A_i    : in     std_logic;
      B_i    : in     std_logic;
      Cin_i  : in     std_logic;
      Cout_o : out    std_logic;
      S_o    : out    std_logic
   );
   end component;

   -- Optional embedded configurations
   for all : Add1_C use entity work.Add1_C;


begin

   -- Instance port mappings.
   I0 : Add1_C
      port map (
         A_i    => Nbr_A_i(0),
         B_i    => Nbr_B_i(0),
         Cin_i  => Cin_i,
         Cout_o => Cout_o1,
         S_o    => Somme_o(0)
      );
   I1 : Add1_C
      port map (
         A_i    => Nbr_A_i(1),
         B_i    => Nbr_B_i(1),
         Cin_i  => Cout_o1,
         Cout_o => Cout_o2,
         S_o    => Somme_o(1)
      );
   I2 : Add1_C
      port map (
         A_i    => Nbr_A_i(2),
         B_i    => Nbr_B_i(2),
         Cin_i  => Cout_o2,
         Cout_o => Cout_o3,
         S_o    => Somme_o(2)
      );
   I3 : Add1_C
      port map (
         A_i    => Nbr_A_i(3),
         B_i    => Nbr_B_i(3),
         Cin_i  => Cout_o3,
         Cout_o => Cout_o,
         S_o    => Somme_o(3)
      );

end struct;
