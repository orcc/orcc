-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Div4_Synch.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle d'un systeme
--                  sequentiel
--                  Inclu des affectation textuelle
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--    Diviseur synchrone par 4 avec deux flip-flop 
-----------------------------------------------------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

entity Div4_Synch is
   port( Horloge_i : in     std_logic;
         Reset_i   : in     std_logic;
         F_Div4_o  : out    std_logic);
end Div4_Synch ;

architecture struct of Div4_Synch is

   -- Architecture declarations

   -- Internal signal declarations
   signal D0_s : std_logic;
   signal D1_s : std_logic;
   signal Q0_s : std_logic;
   signal Q1_s : std_logic;


   -- Component Declarations
   component DFF
   port (
      Clk_i   : in     Std_Logic ;
      D_i     : in     Std_Logic ;
      Reset_i : in     Std_Logic ;
      Q_o     : out    Std_Logic ;
      nQ_o    : out    Std_Logic 
   );
   end component;

   -- Optional embedded configurations
   for all : DFF use entity work.DFF;

begin

   --Description textuelle porte XOR
   D1_s <= Q1_s xor Q0_s;


   -- Instance port mappings.
   I0 : DFF
      port map (
         Clk_i   => Horloge_i,
         D_i     => D0_s,
         Reset_i => Reset_i,
         Q_o     => Q0_s,
         nQ_o    => D0_s      );
   I1 : DFF
      port map (
         Clk_i   => Horloge_i,
         D_i     => D1_s,
         Reset_i => Reset_i,
         Q_o     => Q1_s,
         nQ_o    => open       );

   --Affectation de la sortie
   F_Div4_o <= Q1_s;                       

end struct;
