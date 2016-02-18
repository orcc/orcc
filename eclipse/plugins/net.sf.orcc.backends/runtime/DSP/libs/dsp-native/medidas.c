/*
 * medidas.c
 *
 *  Created on: 24/02/2014
 *      Author: mchavarrias
 */
#include "medidas.h"
 #include "omp.h"

#pragma DATA_SECTION (dif_refresco,  ".NC")
CSL_Uint64 aux_refresco = 0;
#pragma DATA_SECTION (activadas,  ".NC")
int activadas = 0;
#pragma DATA_SECTION (medida,  ".NC")
double medida = 0;
#pragma DATA_SECTION (comienzo,  ".NC")
int comienzo = 0;
#pragma DATA_SECTION (paso,  ".NC")
int paso = 0;

CSL_Uint64 save_refresco (CSL_Uint64 aux){

	if (aux == 1)
		return (aux_refresco);
	else
		aux_refresco = aux;

}

int sincronizacion (int sincro){

	if(sincro == 1){
		paso++;}

	if(paso > 0){
		return 1;
	}else{
		return 0;}


}


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

int activa_lectura(int lectura){
	if(lectura == 1)
		comienzo = 1;
	else if(lectura == 2)
		comienzo = 0;

	return comienzo;
}


double guarda_medidas(double input){
	if(input==0){
		return medida;
	}else
	medida = input;

}
