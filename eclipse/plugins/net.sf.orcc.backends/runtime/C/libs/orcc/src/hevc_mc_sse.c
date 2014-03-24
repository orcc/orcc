#include "sse.h"

#ifdef __SSE2__
#include <emmintrin.h>
#endif
#ifdef __SSSE3__
#include <tmmintrin.h>
#endif
#ifdef __SSE4_1__
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

#define WEIGHTED_STORE2_8()                                                    \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((short *) (dst + x)) = _mm_extract_epi16(r1, 0)
#define WEIGHTED_STORE4_8()                                                    \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((u32 *) &dst[x]) =_mm_cvtsi128_si32(r1)
#define WEIGHTED_STORE8_8()                                                    \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    _mm_storel_epi64((__m128i *) &dst[x], r1)

#define WEIGHTED_STORE16_8()                                                   \
    r1 = _mm_packus_epi16(r1, r2);                                             \
    _mm_store_si128((__m128i *) &dst[x], r1)

#define WEIGHTED_INIT_0_8()                                                    \
    const int dststride = _dststride;                                          \
    u8 *dst = (u8 *) _dst

#define WEIGHTED_INIT_4(H, D)                                                  \
    const int log2Wd = denom + 14 - D;                                         \
    const int shift2 = log2Wd + 1;                                             \
    const int o0     = olxFlag << (D - 8);                                     \
    const int o1     = ol1Flag << (D - 8);                                     \
    const __m128i m1 = _mm_set1_epi16(wlxFlag);                                \
    const __m128i m3 = _mm_set1_epi32((o0 + o1 + 1) << log2Wd);                \
    __m128i s1, s2, s3, s4, s5, s6;                                            \
    WEIGHTED_INIT_0_ ## D()

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

#define WEIGHTED_COMPUTE2_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE4_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE8_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE16_4(D)                                                \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1);                                          \
    WEIGHTED_COMPUTE_4_ ## D(r4, r2)

#define WEIGHTED_PRED_MONO(H, D)                                               \
void ff_hevc_weighted_pred_mono ## H ## _ ## D ##_sse(                         \
                                    u8 denom,                                  \
                                    i16 wlxFlag,                               \
                                    i16 olxFlag, i16 ol1Flag,                  \
                                    u8 *_dst, int _dststride,                  \
                                    i16 *src,                                  \
                                    int srcstride,                             \
                                    int width, int height) {                   \
    int x, y;                                                                  \
    __m128i r1, r2, r3, r4;                                                    \
    WEIGHTED_INIT_4(H, D);                                                     \
    for (y = 0; y < height; y++) {                                             \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _4(D);                                    \
            WEIGHTED_STORE ## H ## _ ## D();                                   \
        }                                                                      \
        dst  += dststride;                                                     \
        src  += srcstride;                                                     \
    }                                                                          \
}

#ifdef __SSE4_1__
WEIGHTED_PRED_MONO( 2, 8)
WEIGHTED_PRED_MONO( 4, 8)
WEIGHTED_PRED_MONO( 8, 8)
WEIGHTED_PRED_MONO(16, 8)
#endif // #ifdef __SSE4_1__


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
