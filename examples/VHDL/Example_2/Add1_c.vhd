-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Add1_c.vhd
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

entity Add1_C is
   port( 
      A_i    : in     std_logic;
      B_i    : in     std_logic;
      Cin_i  : in     std_logic;
      Cout_o : out    std_logic;
      S_o    : out    std_logic  
   );

-- Declarations

end Add1_C ;


architecture Struct of Add1_c is

   -- Architecture declarations

   -- Internal signal declarations
   signal S_o1 : std_logic;
   signal S_o2 : Std_Logic;
   signal S_o3 : Std_Logic;


   -- Component Declarations
   component AND2
   port (
      A_i : in     Std_Logic ;
      B_i : in     Std_Logic ;
      S_o : out    Std_Logic 
   );
   end component;
   component OR2
   port (
      A_i : in     Std_Logic ; -- Entree A.
      B_i : in     Std_Logic ; -- Entree B.
      S_o : out    Std_Logic   -- A_i OU B_i.
   );
   end component;
   component XOR2
   port (
      A_i : in     std_logic ;
      B_i : in     std_logic ;
      S_o : out    std_logic 
   );
   end component;

   -- Optional embedded configurations
   for all : AND2 use entity work.AND2;
   for all : OR2 use entity work.OR2;
   for all : XOR2 use entity work.XOR2;


begin

   -- Instance port mappings.
   I2 : AND2
      port map (
         A_i => Cin_i,
         B_i => S_o1,
         S_o => S_o3
      );
   I3 : AND2
      port map (
         A_i => A_i,
         B_i => B_i,
         S_o => S_o2
      );
   I1 : OR2
      port map (
         A_i => S_o3,
         B_i => S_o2,
         S_o => Cout_o
      );
   I0 : XOR2
      port map (
         A_i => A_i,
         B_i => B_i,
         S_o => S_o1
      );
   I4 : XOR2
      port map (
         A_i => Cin_i,
         B_i => S_o1,
         S_o => S_o
      );

end Struct;