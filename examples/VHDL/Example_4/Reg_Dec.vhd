-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Reg_Dec.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle d'un systeme
--                  sequentiel
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--    Registre a decalage de 4 cellules 
-----------------------------------------------------------------------

LIBRARY ieee;
  use ieee.std_logic_1164.ALL;

entity Reg_Dec is
   port( 
      CLK_i   : in     std_logic;
      In_i    : in     std_logic;
      Reset_i : in     std_logic;
      Out_o   : out    std_logic   );
end Reg_Dec ;

architecture struct of Reg_Dec is

   -- Internal signal declarations
   signal Q1_s : std_logic;
   signal Q2_s : std_logic;
   signal Q3_s : std_logic;


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

   -- Instance port mappings.
   I1 : DFF
      port map (
         Clk_i   => CLK_i,
         D_i     => In_i,
         Reset_i => Reset_i,
         Q_o     => Q1_s,
         nQ_o    => open
      );
   I2 : DFF
      port map (
         Clk_i   => CLK_i,
         D_i     => Q1_s,
         Reset_i => Reset_i,
         Q_o     => Q2_s,
         nQ_o    => open
      );
   I3 : DFF
      port map (
         Clk_i   => CLK_i,
         D_i     => Q2_s,
         Reset_i => Reset_i,
         Q_o     => Q3_s,
         nQ_o    => open
      );
   I4 : DFF
      port map (
         Clk_i   => CLK_i,
         D_i     => Q3_s,
         Reset_i => Reset_i,
         Q_o     => Out_o,
         nQ_o    => open
      );

end struct;
