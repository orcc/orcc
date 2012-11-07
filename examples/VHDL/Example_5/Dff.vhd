-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  DFF.vhd
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
--    Flip-flop D
-----------------------------------------------------------------------

library IEEE;
  use IEEE.Std_Logic_1164.all;
  --use IEEE.Numeric_Std.all;

entity DFF is
  port( 
    Clk_i   : in  Std_Logic;
    D_i     : in  Std_Logic;
    Reset_i : in  Std_Logic;
    Q_o     : out Std_Logic;
    nQ_o    : out Std_Logic
  );
end DFF ;

architecture Comport of DFF is

  signal Q_s : Std_Logic;

begin

  process(Clk_i, Reset_i)
  begin
    if (Reset_i = '1') then
      Q_s <= '0';
    elsif Rising_Edge(Clk_i) then
      Q_s <= D_i;
    end if;
  end process;
  
  Q_o  <= Q_s;
  nQ_o <= not Q_s;
  
end Comport;

