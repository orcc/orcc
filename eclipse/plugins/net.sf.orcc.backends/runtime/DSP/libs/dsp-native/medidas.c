/*
 * medidas.c
 *
 *  Created on: 24/02/2014
 *      Author: mchavarrias
 */
#include "medidas.h"

int activadas = 0;

int activa_medidas (int on)
{
	if (on == 1)
		activadas = 1;
	else if(on == 2)
		activadas = 0;
	else if(on == 3)
		activadas = 2;
	else if (on == 4)
		activadas =4;

	return activadas;

}
