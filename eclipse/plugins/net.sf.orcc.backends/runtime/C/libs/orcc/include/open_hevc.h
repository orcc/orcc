#ifndef ORCC_OPEN_HEVC_H
#define ORCC_OPEN_HEVC_H

#if defined(__ICC) && __ICC < 1200 || defined(__SUNPRO_C)
    #define DECLARE_ALIGNED(n,t,v)      t __attribute__ ((aligned (n))) v
#elif defined(__TI_COMPILER_VERSION__)
    #define DECLARE_ALIGNED(n,t,v)                      \
        AV_PRAGMA(DATA_ALIGN(v,n))                      \
        t __attribute__((aligned(n))) v
#elif defined(__GNUC__)
    #define DECLARE_ALIGNED(n,t,v)      t __attribute__ ((aligned (n))) v
#elif defined(_MSC_VER)
    #define DECLARE_ALIGNED(n,t,v)      __declspec(align(n)) t v
#else
    #define DECLARE_ALIGNED(n,t,v)      t v
#endif

#include "types.h"

int openHEVCIsPresent();

void put_hevc_qpel_pixel_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  u8 width, u8 height);
void put_hevc_epel_pixel_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  u8 width, u8 height, i32 component);

void put_hevc_qpel_h_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  i32 filterIdx,  u8 width, u8 height);

void put_hevc_qpel_v_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  i32 filterIdx,  u8 width, u8 height);

void put_hevc_epel_h_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  i32 filterIdx,  u8 width, u8 height, i32 component);

void put_hevc_epel_v_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  i32 filterIdx,  u8 width, u8 height, i32 component);

void put_hevc_qpel_hv_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  i32 filterIdx[2],  u8 width, u8 height);

void put_hevc_epel_hv_orcc(i16 _dst[2][3][64*64], u8 listIdx,
  u8 _src[3][71*71], u8 srcstride,
  i32 filterIdx[2],  u8 width, u8 height, i32 component);

void put_unweighted_pred_orcc(u8 _dst[2][3][64*64],
  i16 _src[2][3][64*64], u8 width, u8 height, u8 rdList, i32 component);

void put_unweighted_pred_avg_orcc(u8 _dst[2][3][64*64],
  i16 _src[2][3][64*64], u8 width, u8 height, i32 component);

#endif
