/*
 * medidas.h
 *
 *  Created on: 24/02/2014
 *      Author: mchavarrias
 */

#ifndef MEDIDAS_H_
#define MEDIDAS_H_

#include <ti/csl/csl_tsc.h>

CSL_Uint64 save_refresco (CSL_Uint64 aux);

int activa_medidas (int on);

int sincronizacion (int sincro);

int activa_lectura(int lectura);

double guarda_medidas(double input);

#endif /* MEDIDAS_H_ */





