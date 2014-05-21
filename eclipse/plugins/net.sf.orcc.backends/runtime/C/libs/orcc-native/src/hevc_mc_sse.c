#include "hevc_sse.h"

#include "config.h"

#if HAVE_SSE2
#include <emmintrin.h>
#endif
#if HAVE_SSE3
#include <tmmintrin.h>
#endif
#if HAVE_SSE4
#include <smmintrin.h>
#endif

#define WEIGHTED_LOAD2()                                                       \
    r1 = _mm_loadl_epi64((__m128i *) &src[x    ])
#define WEIGHTED_LOAD4()                                                       \
    WEIGHTED_LOAD2()
#define WEIGHTED_LOAD8()                                                       \
    r1 = _mm_loadu_si128((__m128i *) &src[x    ])
#define WEIGHTED_LOAD16()                                                      \
    WEIGHTED_LOAD8();                                                          \
    r2 = _mm_loadu_si128((__m128i *) &src[x + 8])

#define WEIGHTED_LOAD2_1()                                                     \
    r3 = _mm_loadl_epi64((__m128i *) &src1[x    ])
#define WEIGHTED_LOAD4_1()                                                     \
    WEIGHTED_LOAD2_1()
#define WEIGHTED_LOAD8_1()                                                     \
    r3 = _mm_loadu_si128((__m128i *) &src1[x    ])
#define WEIGHTED_LOAD16_1()                                                    \
    WEIGHTED_LOAD8_1();                                                        \
    r4 = _mm_loadu_si128((__m128i *) &src1[x + 8])

#define WEIGHTED_STORE2_8(x)                                                   \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((short *) (dst + x)) = _mm_extract_epi16(r1, 0)
#define WEIGHTED_STORE4_8(x)                                                   \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((u32 *) &dst[x]) =_mm_cvtsi128_si32(r1)
#define WEIGHTED_STORE8_8(x)                                                   \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    _mm_storel_epi64((__m128i *) &dst[x], r1)

#define WEIGHTED_STORE16_8(x)                                                  \
    r1 = _mm_packus_epi16(r1, r2);                                             \
    _mm_store_si128((__m128i *) &dst[x], r1)

/* Adapted store operations in order to store data in zscan order */

#define WEIGHTED_STORE2_2_8(zScanIdx)                                          \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((short *) &dst[(zScanIdx << 1) + (x & 1) + ((x >> 1) << 2)]) = _mm_extract_epi16(r1, 0)

#define WEIGHTED_STORE4_4_8(zScanIdx)                                          \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    pu32Dst[zScanIdx] = _mm_cvtsi128_si32(r1);                                 \
    zScanIdx += 4

#define WEIGHTED_STORE8_4_8(zScanIdx)                                          \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    pu32Dst[zScanIdx +  0] = _mm_extract_epi32(r1, 0);                         \
    pu32Dst[zScanIdx +  4] = _mm_extract_epi32(r1, 1);                         \
    zScanIdx += (2 * 4)

#define WEIGHTED_STORE16_4_8(zScanIdx)                                         \
    r1 = _mm_packus_epi16(r1, r2);                                             \
    pu32Dst[zScanIdx +  0] = _mm_extract_epi32(r1, 0);                         \
    pu32Dst[zScanIdx +  4] = _mm_extract_epi32(r1, 1);                         \
    pu32Dst[zScanIdx +  8] = _mm_extract_epi32(r1, 2);                         \
    pu32Dst[zScanIdx + 12] = _mm_extract_epi32(r1, 3);                         \
    zScanIdx += (4 * 4)


#define WEIGHTED_STORE4_2_8(zScanIdx)                                          \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    pu16Dst[zScanIdx +  0] = _mm_extract_epi16(r1, 0);                         \
    pu16Dst[zScanIdx +  2] = _mm_extract_epi16(r1, 1);                         \
    zScanIdx += 4

#define WEIGHTED_STORE8_2_8(zScanIdx)                                          \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    pu16Dst[zScanIdx +  0] = _mm_extract_epi16(r1, 0);                         \
    pu16Dst[zScanIdx +  2] = _mm_extract_epi16(r1, 1);                         \
    pu16Dst[zScanIdx +  4] = _mm_extract_epi16(r1, 2);                         \
    pu16Dst[zScanIdx +  6] = _mm_extract_epi16(r1, 3);                         \
    zScanIdx += (2 * 4)

#define WEIGHTED_STORE16_2_8(zScanIdx)                                         \
    r1 = _mm_packus_epi16(r1, r2);                                             \
    pu16Dst[zScanIdx +  0] = _mm_extract_epi16(r1, 0);                         \
    pu16Dst[zScanIdx +  2] = _mm_extract_epi16(r1, 1);                         \
    pu16Dst[zScanIdx +  4] = _mm_extract_epi16(r1, 2);                         \
    pu16Dst[zScanIdx +  6] = _mm_extract_epi16(r1, 3);                         \
    pu16Dst[zScanIdx +  8] = _mm_extract_epi16(r1, 4);                         \
    pu16Dst[zScanIdx + 10] = _mm_extract_epi16(r1, 5);                         \
    pu16Dst[zScanIdx + 12] = _mm_extract_epi16(r1, 6);                         \
    pu16Dst[zScanIdx + 14] = _mm_extract_epi16(r1, 7);                         \
    zScanIdx += (4 * 4)

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////

#define WEIGHTED_INIT_0(H, D)                                                  \
    const int shift2 = 14 - D;                                                 \
    const __m128i m1 = _mm_set1_epi16(1 << (14 - D - 1));                      \
    WEIGHTED_INIT_0_ ## D()

#define WEIGHTED_INIT_0_8()                                                    \
    const int dststride = _dststride;                                          \
    u8 *dst = (u8 *) _dst

#define WEIGHTED_INIT_1(H, D)                                                  \
    const int shift2    = denom + 14 - D;                                      \
    const __m128i add   = _mm_set1_epi32(olxFlag << (D - 8));                  \
    const __m128i add2  = _mm_set1_epi32(1 << (shift2-1));                     \
    const __m128i m1    = _mm_set1_epi16(wlxFlag);                             \
    __m128i s1, s2, s3;                                                        \
    WEIGHTED_INIT_0_ ## D()

#define WEIGHTED_INIT_2(H, D)                                                  \
    const int shift2 = 14 + 1 - D;                                             \
    const __m128i m1 = _mm_set1_epi16(1 << (14 - D));                          \
    WEIGHTED_INIT_0_ ## D()

#define WEIGHTED_INIT_3(H, D)                                                  \
    const int log2Wd = denom + 14 - D;                                         \
    const int shift2 = log2Wd + 1;                                             \
    const int o0     = olxFlag << (D - 8);                                     \
    const int o1     = ol1Flag << (D - 8);                                     \
    const __m128i m1 = _mm_set1_epi16(wlxFlag);                                \
    const __m128i m2 = _mm_set1_epi16(wl1Flag);                                \
    const __m128i m3 = _mm_set1_epi32((o0 + o1 + 1) << log2Wd);                \
    __m128i s1, s2, s3, s4, s5, s6;                                            \
    WEIGHTED_INIT_0_ ## D()

#define WEIGHTED_INIT_4(H, D)                                                  \
    const int log2Wd = denom + 14 - D;                                         \
    const int shift2 = log2Wd + 1;                                             \
    const int o0     = olxFlag << (D - 8);                                     \
    const int o1     = ol1Flag << (D - 8);                                     \
    const __m128i m1 = _mm_set1_epi16(wlxFlag);                                \
    const __m128i m3 = _mm_set1_epi32((o0 + o1 + 1) << log2Wd);                \
    __m128i s1, s2, s3, s4, s5, s6;                                            \
    WEIGHTED_INIT_0_ ## D()

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define WEIGHTED_COMPUTE_0_8(reg1)                                             \
    reg1 = _mm_srai_epi16(_mm_adds_epi16(reg1, m1), shift2)

#define WEIGHTED_COMPUTE_0_10(reg1)                                            \
    WEIGHTED_COMPUTE_0_8(reg1);                                                \
    reg1 = _mm_max_epi16(reg1, _mm_setzero_si128());                           \
    reg1 = _mm_min_epi16(reg1, _mm_set1_epi16(0x03ff))

#define WEIGHTED_COMPUTE2_0(D)                                                 \
    WEIGHTED_COMPUTE_0_ ## D(r1)
#define WEIGHTED_COMPUTE4_0(D)                                                 \
    WEIGHTED_COMPUTE_0_ ## D(r1)
#define WEIGHTED_COMPUTE8_0(D)                                                 \
    WEIGHTED_COMPUTE_0_ ## D(r1)
#define WEIGHTED_COMPUTE16_0(D)                                                \
    WEIGHTED_COMPUTE_0_ ## D(r1);                                              \
    WEIGHTED_COMPUTE_0_ ## D(r2)

#define WEIGHTED_COMPUTE_1_8(reg1)                                             \
    s1   = _mm_mullo_epi16(reg1, m1);                                          \
    s2   = _mm_mulhi_epi16(reg1, m1);                                          \
    s3   = _mm_unpackhi_epi16(s1, s2);                                         \
    reg1 = _mm_unpacklo_epi16(s1, s2);                                         \
    reg1 = _mm_srai_epi32(_mm_add_epi32(reg1, add2), shift2);                  \
    s3   = _mm_srai_epi32(_mm_add_epi32(s3  , add2), shift2);                  \
    reg1 = _mm_add_epi32(reg1, add);                                           \
    s3   = _mm_add_epi32(s3  , add);                                           \
    reg1 = _mm_packus_epi32(reg1, s3)
#define WEIGHTED_COMPUTE_1_10(reg1)                                            \
    WEIGHTED_COMPUTE_1_8(reg1);                                                \
    reg1 = _mm_max_epi16(reg1, _mm_setzero_si128());                           \
    reg1 = _mm_min_epi16(reg1, _mm_set1_epi16(0x03ff))

#define WEIGHTED_COMPUTE2_1(D)                                                 \
    WEIGHTED_COMPUTE_1_ ## D(r1)
#define WEIGHTED_COMPUTE4_1(D)                                                 \
    WEIGHTED_COMPUTE_1_ ## D(r1)
#define WEIGHTED_COMPUTE4_1(D)                                                 \
    WEIGHTED_COMPUTE_1_ ## D(r1)
#define WEIGHTED_COMPUTE8_1(D)                                                 \
    WEIGHTED_COMPUTE_1_ ## D(r1)
#define WEIGHTED_COMPUTE16_1(D)                                                \
    WEIGHTED_COMPUTE_1_ ## D(r1);                                              \
    WEIGHTED_COMPUTE_1_ ## D(r2)

#define WEIGHTED_COMPUTE_2_8(reg1, reg2)                                       \
    reg1 = _mm_adds_epi16(reg1, m1);                                           \
    reg1 = _mm_adds_epi16(reg1, reg2);                                         \
    reg2 = _mm_srai_epi16(reg1, shift2)
#define WEIGHTED_COMPUTE_2_10(reg1, reg2)                                      \
    WEIGHTED_COMPUTE_2_8(reg1, reg2);                                          \
    reg2 = _mm_max_epi16(reg2, _mm_setzero_si128());                           \
    reg2 = _mm_min_epi16(reg2, _mm_set1_epi16(0x03ff))

#define WEIGHTED_COMPUTE2_2(D)                                                 \
    WEIGHTED_LOAD2_1();                                                        \
    WEIGHTED_COMPUTE_2_ ## D(r3, r1)
#define WEIGHTED_COMPUTE4_2(D)                                                 \
    WEIGHTED_LOAD4_1();                                                        \
    WEIGHTED_COMPUTE_2_ ## D(r3, r1)
#define WEIGHTED_COMPUTE8_2(D)                                                 \
    WEIGHTED_LOAD8_1();                                                        \
    WEIGHTED_COMPUTE_2_ ## D(r3, r1)
#define WEIGHTED_COMPUTE16_2(D)                                                \
    WEIGHTED_LOAD16_1();                                                       \
    WEIGHTED_COMPUTE_2_ ## D(r3, r1);                                          \
    WEIGHTED_COMPUTE_2_ ## D(r4, r2)

#define WEIGHTED_COMPUTE_3_8(reg1, reg2)                                       \
    s1   = _mm_mullo_epi16(reg1, m1);                                          \
    s2   = _mm_mulhi_epi16(reg1, m1);                                          \
    s3   = _mm_mullo_epi16(reg2, m2);                                          \
    s4   = _mm_mulhi_epi16(reg2, m2);                                          \
    s5   = _mm_unpacklo_epi16(s1, s2);                                         \
    s6   = _mm_unpacklo_epi16(s3, s4);                                         \
    reg1 = _mm_unpackhi_epi16(s1, s2);                                         \
    reg2 = _mm_unpackhi_epi16(s3, s4);                                         \
    reg1 = _mm_add_epi32(reg1, reg2);                                          \
    reg2 = _mm_add_epi32(s5, s6);                                              \
    reg1 = _mm_srai_epi32(_mm_add_epi32(reg1, m3), shift2);                    \
    reg2 = _mm_srai_epi32(_mm_add_epi32(reg2, m3), shift2);                    \
    reg2 = _mm_packus_epi32(reg2, reg1)

#define WEIGHTED_COMPUTE_4_8(reg1, reg2)                                       \
    s1   = _mm_mullo_epi16(reg2, m1);                                          \
    s2   = _mm_mulhi_epi16(reg2, m1);                                          \
    s5   = _mm_unpacklo_epi16(s1, s2);                                         \
    reg1 = _mm_unpackhi_epi16(s1, s2);                                         \
    reg1 = _mm_srai_epi32(_mm_add_epi32(reg1, m3), shift2);                    \
    reg2 = _mm_srai_epi32(_mm_add_epi32(s5, m3), shift2);                      \
    reg2 = _mm_packus_epi32(reg2, reg1)

#define WEIGHTED_COMPUTE2_3(D)                                                 \
    WEIGHTED_LOAD2_1();                                                        \
    WEIGHTED_COMPUTE_3_ ## D(r3, r1)
#define WEIGHTED_COMPUTE4_3(D)                                                 \
    WEIGHTED_LOAD4_1();                                                        \
    WEIGHTED_COMPUTE_3_ ## D(r3, r1)
#define WEIGHTED_COMPUTE8_3(D)                                                 \
    WEIGHTED_LOAD8_1();                                                        \
    WEIGHTED_COMPUTE_3_ ## D(r3, r1)
#define WEIGHTED_COMPUTE16_3(D)                                                \
    WEIGHTED_LOAD16_1();                                                       \
    WEIGHTED_COMPUTE_3_ ## D(r3, r1);                                          \
    WEIGHTED_COMPUTE_3_ ## D(r4, r2)

#define WEIGHTED_COMPUTE2_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE4_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE8_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE16_4(D)                                                \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1);                                          \
    WEIGHTED_COMPUTE_4_ ## D(r4, r2)

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_weighted_pred_mono8_sse
////////////////////////////////////////////////////////////////////////////////

#define WEIGHTED_PRED_MONO(H, D)                                               \
void ff_hevc_weighted_pred_mono ## H ## _ ## D ##_sse(                         \
  u8 denom,                                                                    \
  i16 wlxFlag,                                                                 \
  i16 olxFlag, i16 ol1Flag,                                                    \
  u8 *_dst, int _dststride,                                                    \
  i16 *src,                                                                    \
  int srcstride,                                                               \
  int width, int height) {                                                     \
    int x, y;                                                                  \
    __m128i r1, r2, r3, r4;                                                    \
    WEIGHTED_INIT_4(H, D);                                                     \
    for (y = 0; y < height; y++) {                                             \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _4(D);                                    \
            WEIGHTED_STORE ## H ## _ ## D(x);                                  \
        }                                                                      \
        dst  += dststride;                                                     \
        src  += srcstride;                                                     \
    }                                                                          \
}

#if HAVE_SSE4
WEIGHTED_PRED_MONO( 2, 8)
WEIGHTED_PRED_MONO( 4, 8)
WEIGHTED_PRED_MONO( 8, 8)
WEIGHTED_PRED_MONO(16, 8)
#endif // #ifdef HAVE_SSE4


/* Weighted Pred Zscan */

#define ZSCAN_VAR0(H)                                                          \
    int zScanIdx = 0;                                                          \
    u ## H  * pu ## H ## Dst = (u ## H  *) dst

#define ZSCAN_VAR_2()                                                          \
    ZSCAN_VAR0(16)

#define ZSCAN_VAR_4()                                                          \
    ZSCAN_VAR0(32)

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_put_unweighted_pred_8_sse
////////////////////////////////////////////////////////////////////////////////
#define PUT_UNWEIGHTED_PRED_VAR2()                                             \
    __m128i r1
#define PUT_UNWEIGHTED_PRED_VAR4()  PUT_UNWEIGHTED_PRED_VAR2()
#define PUT_UNWEIGHTED_PRED_VAR8()  PUT_UNWEIGHTED_PRED_VAR2()
#define PUT_UNWEIGHTED_PRED_VAR16()                                            \
    __m128i r1, r2

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_put_unweighted_pred_zscan8_sse
////////////////////////////////////////////////////////////////////////////////

#define PUT_UNWEIGHTED_PRED_ZSCAN(H, K, D)                                     \
void ff_hevc_put_unweighted_pred_zscan ## H ## _ ## K ## _ ## D ##_sse(        \
  u8 *_dst, int _dststride,                                                    \
  i16 *src, int srcstride,                                                     \
  int width, int height) {                                                     \
  int x, y;                                                                    \
  PUT_UNWEIGHTED_PRED_VAR ## H();                                              \
  WEIGHTED_INIT_0(H, D);                                                       \
  ZSCAN_VAR_ ## K ();                                                          \
  for (y = 0; y < height; y++) {                                               \
    zScanIdx = ((y & (K - 1))) + (y >> (K >> 1)) * (width);                    \
    for (x = 0; x < width; x += H) {                                           \
      WEIGHTED_LOAD ## H();                                                    \
      WEIGHTED_COMPUTE ## H ## _0(D);                                          \
      WEIGHTED_STORE ## H ## _ ## K ## _ ## D(zScanIdx);                       \
    }                                                                          \
    src += srcstride;                                                          \
  }                                                                            \
}

#if HAVE_SSE4
PUT_UNWEIGHTED_PRED_ZSCAN( 2,  2,  8)
PUT_UNWEIGHTED_PRED_ZSCAN( 4,  2,  8)
PUT_UNWEIGHTED_PRED_ZSCAN( 8,  2,  8)
PUT_UNWEIGHTED_PRED_ZSCAN(16,  2,  8)
PUT_UNWEIGHTED_PRED_ZSCAN( 4,  4,  8)
PUT_UNWEIGHTED_PRED_ZSCAN( 8,  4,  8)
PUT_UNWEIGHTED_PRED_ZSCAN(16,  4,  8)
#endif

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_put_weighted_pred_avg_zscan8_sse
////////////////////////////////////////////////////////////////////////////////

#define PUT_WEIGHTED_PRED_AVG_VAR2()                                           \
    __m128i r1, r3
#define PUT_WEIGHTED_PRED_AVG_VAR4()  PUT_WEIGHTED_PRED_AVG_VAR2()
#define PUT_WEIGHTED_PRED_AVG_VAR8()  PUT_WEIGHTED_PRED_AVG_VAR2()
#define PUT_WEIGHTED_PRED_AVG_VAR16()                                          \
    __m128i r1, r2, r3, r4

#define PUT_WEIGHTED_PRED_AVG_ZSCAN(H, K, D)                                   \
void ff_hevc_put_weighted_pred_avg_zscan ## H ## _ ## K ## _ ## D ##_sse(      \
  u8 *_dst, int _dststride,                                                    \
  i16 *src1, i16 *src,                                                         \
  int srcstride,                                                               \
  int width, int height) {                                                     \
    int x, y;                                                                  \
    PUT_WEIGHTED_PRED_AVG_VAR ## H();                                          \
    WEIGHTED_INIT_2(H, D);                                                     \
    ZSCAN_VAR_ ## K ();                                                        \
    for (y = 0; y < height; y++) {                                             \
        zScanIdx = ((y & (K - 1))) + (y >> (K >> 1)) * (width);                \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _2(D);                                    \
            WEIGHTED_STORE ## H ## _ ## K ## _ ## D(zScanIdx);                 \
        }                                                                      \
        src  += srcstride;                                                     \
        src1 += srcstride;                                                     \
    }                                                                          \
}


#if HAVE_SSE4
PUT_WEIGHTED_PRED_AVG_ZSCAN(2,  2,  8)
PUT_WEIGHTED_PRED_AVG_ZSCAN(4,  2,  8)
PUT_WEIGHTED_PRED_AVG_ZSCAN(8,  2,  8)
PUT_WEIGHTED_PRED_AVG_ZSCAN(16, 2,  8)
PUT_WEIGHTED_PRED_AVG_ZSCAN(4,  4,  8)
PUT_WEIGHTED_PRED_AVG_ZSCAN(8,  4,  8)
PUT_WEIGHTED_PRED_AVG_ZSCAN(16, 4,  8)
#endif

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_weighted_pred_zscan8_sse
////////////////////////////////////////////////////////////////////////////////
#define WEIGHTED_PRED_VAR2()                                                   \
    __m128i r1
#define WEIGHTED_PRED_VAR4()  WEIGHTED_PRED_VAR2()
#define WEIGHTED_PRED_VAR8()  WEIGHTED_PRED_VAR2()
#define WEIGHTED_PRED_VAR16()                                                  \
    __m128i r1, r2

#define WEIGHTED_PRED_ZSCAN(H, K, D)                                           \
void ff_hevc_weighted_pred_zscan ## H ## _ ## K ## _ ## D ##_sse(              \
  u8 denom,                                                                    \
  i16 wlxFlag, i16 olxFlag,                                                    \
  u8 *_dst, int _dststride,                                                    \
  i16 *src, int srcstride,                                                     \
  int width, int height) {                                                     \
    int x, y;                                                                  \
    WEIGHTED_PRED_VAR ## H();                                                  \
    WEIGHTED_INIT_1(H, D);                                                     \
    ZSCAN_VAR_ ## K ();                                                        \
    for (y = 0; y < height; y++) {                                             \
        zScanIdx = ((y & (K - 1))) + (y >> (K >> 1)) * (width);                \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _1(D);                                    \
            WEIGHTED_STORE ## H ## _ ## K ## _ ## D(zScanIdx);                 \
        }                                                                      \
        src += srcstride;                                                      \
    }                                                                          \
}

#if HAVE_SSE4
WEIGHTED_PRED_ZSCAN( 2,  2,  8)
WEIGHTED_PRED_ZSCAN( 4,  2,  8)
WEIGHTED_PRED_ZSCAN( 8,  2,  8)
WEIGHTED_PRED_ZSCAN(16,  2,  8)
WEIGHTED_PRED_ZSCAN( 4,  4,  8)
WEIGHTED_PRED_ZSCAN( 8,  4,  8)
WEIGHTED_PRED_ZSCAN(16,  4,  8)
#endif


////////////////////////////////////////////////////////////////////////////////
// ff_hevc_weighted_pred_avg_zscan8_sse
////////////////////////////////////////////////////////////////////////////////

#define WEIGHTED_PRED_AVG_VAR2()                                               \
    __m128i r1, r3
#define WEIGHTED_PRED_AVG_VAR4()  WEIGHTED_PRED_AVG_VAR2()
#define WEIGHTED_PRED_AVG_VAR8()  WEIGHTED_PRED_AVG_VAR2()
#define WEIGHTED_PRED_AVG_VAR16()                                              \
    __m128i r1, r2, r3, r4

#define WEIGHTED_PRED_AVG_ZSCAN(H, K, D)                                       \
void ff_hevc_weighted_pred_avg_zscan ## H ## _ ## K ## _ ## D ##_sse(          \
  u8 denom,                                                                    \
  i16 wlxFlag, i16 wl1Flag,                                                    \
  i16 olxFlag, i16 ol1Flag,                                                    \
  u8 *_dst, int _dststride,                                                    \
  i16 *src1, i16 *src,                                                         \
  int srcstride,                                                               \
  int width, int height) {                                                     \
    int x, y;                                                                  \
    WEIGHTED_PRED_AVG_VAR ## H();                                              \
    WEIGHTED_INIT_3(H, D);                                                     \
    ZSCAN_VAR_ ## K ();                                                        \
    for (y = 0; y < height; y++) {                                             \
        zScanIdx = ((y & (K - 1))) + (y >> (K >> 1)) * (width);                \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _3(D);                                    \
            WEIGHTED_STORE ## H ## _ ## K ## _ ## D(zScanIdx);                 \
        }                                                                      \
        src  += srcstride;                                                     \
        src1 += srcstride;                                                     \
    }                                                                          \
}

#if HAVE_SSE4
WEIGHTED_PRED_AVG_ZSCAN( 2, 2, 8)
WEIGHTED_PRED_AVG_ZSCAN( 4, 2, 8)
WEIGHTED_PRED_AVG_ZSCAN( 8, 2, 8)
WEIGHTED_PRED_AVG_ZSCAN(16, 2, 8)
WEIGHTED_PRED_AVG_ZSCAN( 4, 4, 8)
WEIGHTED_PRED_AVG_ZSCAN( 8, 4, 8)
WEIGHTED_PRED_AVG_ZSCAN(16, 4, 8)
#endif


////////////////////////////////////////////////////////////////////////////////
// ff_hevc_weighted_pred_mono_zscan8_sse
////////////////////////////////////////////////////////////////////////////////

#define WEIGHTED_PRED_MONO_ZSCAN(H, K, D)                                      \
void ff_hevc_weighted_pred_mono_zscan ## H ## _ ## K ## _ ## D ##_sse(         \
  u8 denom,                                                                    \
  i16 wlxFlag,                                                                 \
  i16 olxFlag, i16 ol1Flag,                                                    \
  u8 *_dst, int _dststride,                                                    \
  i16 *src,                                                                    \
  int srcstride,                                                               \
  int width, int height) {                                                     \
    int x, y;                                                                  \
    __m128i r1, r2, r3, r4;                                                    \
    WEIGHTED_INIT_4(H, D);                                                     \
    ZSCAN_VAR_ ## K ();                                                        \
    for (y = 0; y < height; y++) {                                             \
        zScanIdx = ((y & (K - 1))) + (y >> (K >> 1)) * (width);                \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _4(D);                                    \
            WEIGHTED_STORE ## H ## _ ## K ## _ ## D(zScanIdx);                 \
        }                                                                      \
        src  += srcstride;                                                     \
    }                                                                          \
}

#if HAVE_SSE4
WEIGHTED_PRED_MONO_ZSCAN( 2,  2,  8)
WEIGHTED_PRED_MONO_ZSCAN( 4,  2,  8)
WEIGHTED_PRED_MONO_ZSCAN( 8,  2,  8)
WEIGHTED_PRED_MONO_ZSCAN(16,  2,  8)
WEIGHTED_PRED_MONO_ZSCAN( 4,  4,  8)
WEIGHTED_PRED_MONO_ZSCAN( 8,  4,  8)
WEIGHTED_PRED_MONO_ZSCAN(16,  4,  8)
#endif // HAVE_SSE4


/* Log2CbSize in openHEVC */
/* 1 -  3 -  5 -  7 - 11 - 15 - 23 - 31 - 47 - 63 --> _width
   2 -  4 -  6 -  8 - 12 - 16 - 24 - 32 - 48 - 64 --> width
   2 -  4 -  2 -  8 -  4 - 16 -  8 - 16 - 16 - 16 --> vector size
   0 -  1 -  0 -  2 -  1 -  3 -  2 -  3 -  3 -  3 --> function id
   */
static const int lookup_tab_openhevc_function[64] = {
   -1,  0, -1,  1, -1,  0, -1,  2,
   -1, -1, -1,  1, -1, -1, -1,  3,
   -1, -1, -1, -1, -1, -1, -1,  2,
   -1, -1, -1, -1, -1, -1, -1,  3,
   -1, -1, -1, -1, -1, -1, -1, -1,
   -1, -1, -1, -1, -1, -1, -1,  3,
   -1, -1, -1, -1, -1, -1, -1, -1,
   -1, -1, -1, -1, -1, -1, -1,  3};

void weighted_pred_mono_orcc (int logWD , int weightCu[2], int offsetCu[2],
        i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64])
{
  i16 * src = _src[0];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int wX = weightCu[0] + weightCu[1];
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

  weighted_pred_mono[idx](locLogWD, wX, offsetCu[0], offsetCu[1], dst, width, src, width, width, height);
}

void put_unweighted_pred_zscan_orcc (i16 _src[2][64*64], int _width, int _height, u8 rdList, u8 _dst[64*64], int iComp)
{
  i16 * src = _src[rdList];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int idx = lookup_tab_openhevc_function[_width];

  put_unweighted_pred_zscan[iComp][idx](dst, width, src, width, width, height);
}

void put_weighted_pred_avg_zscan_orcc (i16 src[2][64*64], int _width, int _height, u8 dst[64*64], int iComp)
{
  u8 width = _width + 1;
  u8 height = _height + 1;
  int idx = lookup_tab_openhevc_function[_width];

  put_weighted_pred_avg_zscan[iComp][idx](dst, width,
    src[0], src[1], width, width, height);
}

void weighted_pred_zscan_orcc (int logWD, int weightCu[2], int offsetCu[2],
    i16 _src[2][64*64], int _width, int _height, u8 rdList, u8 _dst[64*64], int iComp)
{
  i16 * src = _src[rdList];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int wX = weightCu[rdList];
  int oX = offsetCu[rdList];
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

  weighted_pred_zscan[iComp][idx](locLogWD, wX, oX, dst, width, src, width, width, height);
}

void weighted_pred_avg_zscan_orcc(int logWD , int weightCu[2], int offsetCu[2],
        i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64], int iComp)
{
  i16 * src = _src[0];
  i16 * src1 = _src[1];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int w0 = weightCu[0];
  int w1 = weightCu[1];
  int o0 = offsetCu[0];
  int o1 = offsetCu[1];
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

  weighted_pred_avg_zscan[iComp][idx](locLogWD, w0, w1, o0, o1, dst, width, src, src1, width, width, height);
}

void weighted_pred_mono_zscan_orcc (int logWD , int weightCu[2], int offsetCu[2],
        i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64], int iComp)
{
  i16 * src = _src[0];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int wX = weightCu[0] + weightCu[1];
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

  weighted_pred_mono_zscan[iComp][idx](locLogWD, wX, offsetCu[0], offsetCu[1], dst, width, src, width, width, height);
}
