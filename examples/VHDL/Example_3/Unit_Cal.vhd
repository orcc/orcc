-----------------------------------------------------------------------
-- HEIG-VD, Haute Ecole d'Ingenerie et de Gestion du Canton de Vaud
-- Institut REDS
--
-- Fichier :  Unit_Cal.vhd
-- Auteur  :  E. Messerli
-- Date    :  12.03.2009
--
-- Utilise dans   : Exemple description structurelle simple
--                  avec tous les composant dans le meme fichier
-----------------------------------------------------------------------
--Ce fichier contient 3 modules VHDL :
--   Unit_Cal (struct) ----> Incr
--                       \-> MUX2_1
-----------------------------------------------------------------------
-- Modifications (No de version, quand, pourquoi, comment, par qui) :
-- 
-----------------------------------------------------------------------
-- Fonctionnement vu de l'exterieur :                                   
--     
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- Description bloc incrementeur
-----------------------------------------------------------------------
Library IEEE;
use IEEE.Std_Logic_1164.all;
use IEEE.Numeric_Std.all;

entity  Incr is
  port (Valeur  : in  Std_Logic_Vector(3 downto 0);
        Val_Inc : out Std_Logic_Vector(3 downto 0) );
end Incr;

architecture Comport of Incr is
  signal Val_Int, Result : Unsigned(3 downto 0);
begin

  --Possible en une affectation sans signaux internes 
  Val_Inc <= std_Logic_Vector( Unsigned(Valeur) + 1 );

end Comport;

-----------------------------------------------------------------------
-- Description bloc multiplexeur
-----------------------------------------------------------------------
Library IEEE;
use IEEE.Std_Logic_1164.all;

entity  MUX2_1 is
  port (In0, In1 : in  Std_Logic_Vector(3 downto 0);
        Sel      : in  Std_Logic;
        Y        : out Std_Logic_Vector(3 downto 0) );
end MUX2_1;

architecture Flot_Don of MUX2_1 is
begin

  Y <= In1 when Sel = '1' else
       In0;

end Flot_Don;



-----------------------------------------------------------------------
-- Description du top structurelle
-----------------------------------------------------------------------
Library IEEE;
use IEEE.Std_Logic_1164.all;

entity Unit_Cal is
  port (Valeur  : in  Std_Logic_Vector(3 downto 0);
        Sel     : in  Std_Logic;
        Val_Cal : out Std_Logic_Vector(3 downto 0) );
end Unit_Cal;

architecture Struct of Unit_Cal is

  component Incr is --is: accepte en VHDL93
    port (Valeur  : in  Std_Logic_Vector(3 downto 0);
          Val_Inc : out Std_Logic_Vector(3 downto 0) );
  end component;
  for all : Incr use entity work.Incr(Comport);

  component  MUX2_1 is -- is: accepte en VHDL93
    port (In0, In1 : in  Std_Logic_Vector(3 downto 0);
          Sel      : in  Std_Logic;
          Y        : out Std_Logic_Vector(3 downto 0) );
  end component;
  for all : MUX2_1 use entity work.MUX2_1(Flot_Don);

  signal Val_Inc_Int : Std_logic_Vector(3 downto 0);
  
begin

  Cal:Incr port map(Valeur  => Valeur,
                    Val_Inc => Val_Inc_Int
                    );

  Mux:MUX2_1 port map(In0 => Valeur,
                      In1 => Val_Inc_Int,
                      Sel => Sel,
                      Y   => Val_Cal
                      );

end Struct;
