#ifndef ORCC_CONFIG_H
#define ORCC_CONFIG_H

#define TRUE  1
#define FALSE 0

#define ON  1
#define OFF 0

#define HAVE_AVX     OFF
#define HAVE_AVX2    OFF

#define HAVE_SSE2    OFF
#define HAVE_SSE3    OFF
#define HAVE_SSE4    OFF
#define HAVE_SSE42   OFF
#define HAVE_SSSE3   OFF

#define OPENHEVC_ENABLE 0

#define NMETODO_ENABLE

#define XPFCMD (*(volatile unsigned int*)0x08000300)

#endif /* ORCC_CONFIG_H */
