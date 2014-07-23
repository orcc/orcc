
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

#if defined(_MSC_VER)
#define _MM_STORE_SI128      _mm_storeu_si128
#define _MM_LOAD_SI128       _mm_loadu_si128
#else
#define _MM_STORE_SI128      _mm_store_si128
#define _MM_LOAD_SI128       _mm_load_si128
#endif

DECLARE_ALIGNED(16, static const i16, transform4x4_luma[8][8] )=
{
    {   29, +84, 29,  +84,  29, +84,  29, +84 },
    {  +74, +55, +74, +55, +74, +55, +74, +55 },
    {   55, -29,  55, -29,  55, -29,  55, -29 },
    {  +74, -84, +74, -84, +74, -84, +74, -84 },
    {   74, -74,  74, -74,  74, -74,  74, -74 },
    {    0, +74,   0, +74,   0, +74,   0, +74 },
    {   84, +55,  84, +55,  84, +55,  84, +55 },
    {  -74, -29, -74, -29, -74, -29, -74, -29 }
};

DECLARE_ALIGNED( 16, static const i16, transform4x4[4][8] ) = {
    { 64,  64, 64,  64, 64,  64, 64,  64 },
    { 64, -64, 64, -64, 64, -64, 64, -64 },
    { 83,  36, 83,  36, 83,  36, 83,  36 },
    { 36, -83, 36, -83, 36, -83, 36, -83 }
};

DECLARE_ALIGNED(16, static const i16, transform8x8[12][1][8] )=
{
    {{  89,  75,  89,  75, 89,  75, 89,  75 }},
    {{  50,  18,  50,  18, 50,  18, 50,  18 }},
    {{  75, -18,  75, -18, 75, -18, 75, -18 }},
    {{ -89, -50, -89, -50,-89, -50,-89, -50 }},
    {{  50, -89,  50, -89, 50, -89, 50, -89 }},
    {{  18,  75,  18,  75, 18,  75, 18,  75 }},
    {{  18, -50,  18, -50, 18, -50, 18, -50 }},
    {{  75, -89,  75, -89, 75, -89, 75, -89 }},
    {{  64,  64,  64,  64, 64,  64, 64,  64 }},
    {{  64, -64,  64, -64, 64, -64, 64, -64 }},
    {{  83,  36,  83,  36, 83,  36, 83,  36 }},
    {{  36, -83,  36, -83, 36, -83, 36, -83 }}
};

DECLARE_ALIGNED(16, static const i16, transform16x16_1[4][8][8] )=
{
    {/*1-3*/ /*2-6*/
        { 90,  87,  90,  87,  90,  87,  90,  87 },
        { 87,  57,  87,  57,  87,  57,  87,  57 },
        { 80,   9,  80,   9,  80,   9,  80,   9 },
        { 70, -43,  70, -43,  70, -43,  70, -43 },
        { 57, -80,  57, -80,  57, -80,  57, -80 },
        { 43, -90,  43, -90,  43, -90,  43, -90 },
        { 25, -70,  25, -70,  25, -70,  25, -70 },
        { 9,  -25,   9, -25,   9, -25,   9, -25 },
    },{ /*5-7*/ /*10-14*/
        {  80,  70,  80,  70,  80,  70,  80,  70 },
        {   9, -43,   9, -43,   9, -43,   9, -43 },
        { -70, -87, -70, -87, -70, -87, -70, -87 },
        { -87,   9, -87,   9, -87,   9, -87,   9 },
        { -25,  90, -25,  90, -25,  90, -25,  90 },
        {  57,  25,  57,  25,  57,  25,  57,  25 },
        {  90, -80,  90, -80,  90, -80,  90, -80 },
        {  43, -57,  43, -57,  43, -57,  43, -57 },
    },{ /*9-11*/ /*18-22*/
        {  57,  43,  57,  43,  57,  43,  57,  43 },
        { -80, -90, -80, -90, -80, -90, -80, -90 },
        { -25,  57, -25,  57, -25,  57, -25,  57 },
        {  90,  25,  90,  25,  90,  25,  90,  25 },
        {  -9,  -87, -9,  -87, -9,  -87, -9, -87 },
        { -87,  70, -87,  70, -87,  70, -87,  70 },
        {  43,   9,  43,   9,  43,   9,  43,   9 },
        {  70, -80,  70, -80,  70, -80,  70, -80 },
    },{/*13-15*/ /*  26-30   */
        {  25,   9,  25,   9,  25,   9,  25,   9 },
        { -70, -25, -70, -25, -70, -25, -70, -25 },
        {  90,  43,  90,  43,  90,  43,  90,  43 },
        { -80, -57, -80, -57, -80, -57, -80, -57 },
        {  43,  70,  43,  70,  43,  70,  43,  70 },
        {  9,  -80,   9, -80,   9, -80,   9, -80 },
        { -57,  87, -57,  87, -57,  87, -57,  87 },
        {  87, -90,  87, -90,  87, -90,  87, -90 },
    }
};
DECLARE_ALIGNED(16, static const i16, transform16x16_2[2][4][8] )=
{
    { /*2-6*/ /*4-12*/
        { 89,  75,  89,  75, 89,  75, 89,  75 },
        { 75, -18,  75, -18, 75, -18, 75, -18 },
        { 50, -89,  50, -89, 50, -89, 50, -89 },
        { 18, -50,  18, -50, 18, -50, 18, -50 },
    },{ /*10-14*/  /*20-28*/
        {  50,  18,  50,  18,  50,  18,  50,  18 },
        { -89, -50, -89, -50, -89, -50, -89, -50 },
        {  18,  75,  18,  75,  18,  75,  18,  75 },
        {  75, -89,  75, -89,  75, -89,  75, -89 },
    }
};

DECLARE_ALIGNED(16, static const i16, transform16x16_3[2][2][8] )=
{
    {/*4-12*/ /*8-24*/
        {  83,  36,  83,  36,  83,  36,  83,  36 },
        {  36, -83,  36, -83,  36, -83,  36, -83 },
    },{ /*0-8*/  /*0-16*/
        { 64,  64, 64,  64, 64,  64, 64,  64 },
        { 64, -64, 64, -64, 64, -64, 64, -64 },
    }
};


DECLARE_ALIGNED(16, static const i16, transform32x32[8][16][8] )=
{
    { /*   1-3     */
        { 90,  90, 90,  90, 90,  90, 90,  90 },
        { 90,  82, 90,  82, 90,  82, 90,  82 },
        { 88,  67, 88,  67, 88,  67, 88,  67 },
        { 85,  46, 85,  46, 85,  46, 85,  46 },
        { 82,  22, 82,  22, 82,  22, 82,  22 },
        { 78,  -4, 78,  -4, 78,  -4, 78,  -4 },
        { 73, -31, 73, -31, 73, -31, 73, -31 },
        { 67, -54, 67, -54, 67, -54, 67, -54 },
        { 61, -73, 61, -73, 61, -73, 61, -73 },
        { 54, -85, 54, -85, 54, -85, 54, -85 },
        { 46, -90, 46, -90, 46, -90, 46, -90 },
        { 38, -88, 38, -88, 38, -88, 38, -88 },
        { 31, -78, 31, -78, 31, -78, 31, -78 },
        { 22, -61, 22, -61, 22, -61, 22, -61 },
        { 13, -38, 13, -38, 13, -38, 13, -38 },
        { 4,  -13,  4, -13,  4, -13,  4, -13 },
    },{/*  5-7 */
        {  88,  85,  88,  85,  88,  85,  88,  85 },
        {  67,  46,  67,  46,  67,  46,  67,  46 },
        {  31, -13,  31, -13,  31, -13,  31, -13 },
        { -13, -67, -13, -67, -13, -67, -13, -67 },
        { -54, -90, -54, -90, -54, -90, -54, -90 },
        { -82, -73, -82, -73, -82, -73, -82, -73 },
        { -90, -22, -90, -22, -90, -22, -90, -22 },
        { -78,  38, -78,  38, -78,  38, -78,  38 },
        { -46,  82, -46,  82, -46,  82, -46,  82 },
        {  -4,  88,  -4,  88,  -4,  88,  -4,  88 },
        {  38,  54,  38,  54,  38,  54,  38,  54 },
        {  73,  -4,  73,  -4,  73,  -4,  73,  -4 },
        {  90, -61,  90, -61,  90, -61,  90, -61 },
        {  85, -90,  85, -90,  85, -90,  85, -90 },
        {  61, -78,  61, -78,  61, -78,  61, -78 },
        {  22, -31,  22, -31,  22, -31,  22, -31 },
    },{/*  9-11   */
        {  82,  78,  82,  78,  82,  78,  82,  78 },
        {  22,  -4,  22,  -4,  22,  -4,  22,  -4 },
        { -54, -82, -54, -82, -54, -82, -54, -82 },
        { -90, -73, -90, -73, -90, -73, -90, -73 },
        { -61,  13, -61,  13, -61,  13, -61,  13 },
        {  13,  85,  13,  85,  13,  85,  13,  85 },
        {  78,  67,  78,  67,  78,  67,  78,  67 },
        {  85, -22,  85, -22,  85, -22,  85, -22 },
        {  31, -88,  31, -88,  31, -88,  31, -88 },
        { -46, -61, -46, -61, -46, -61, -46, -61 },
        { -90,  31, -90,  31, -90,  31, -90,  31 },
        { -67,  90, -67,  90, -67,  90, -67,  90 },
        {   4,  54,   4,  54,   4,  54,   4,  54 },
        {  73, -38,  73, -38,  73, -38,  73, -38 },
        {  88, -90,  88, -90,  88, -90,  88, -90 },
        {  38, -46,  38, -46,  38, -46,  38, -46 },
    },{/*  13-15   */
        {  73,  67,  73,  67,  73,  67,  73,  67 },
        { -31, -54, -31, -54, -31, -54, -31, -54 },
        { -90, -78, -90, -78, -90, -78, -90, -78 },
        { -22,  38, -22,  38, -22,  38, -22,  38 },
        {  78,  85,  78,  85,  78,  85,  78,  85 },
        {  67, -22,  67, -22,  67, -22,  67, -22 },
        { -38, -90, -38, -90, -38, -90, -38, -90 },
        { -90,   4, -90,   4, -90,   4, -90,   4 },
        { -13,  90, -13,  90, -13,  90, -13,  90 },
        {  82,  13,  82,  13,  82,  13,  82,  13 },
        {  61, -88,  61, -88,  61, -88,  61, -88 },
        { -46, -31, -46, -31, -46, -31, -46, -31 },
        { -88,  82, -88,  82, -88,  82, -88,  82 },
        { -4,   46, -4,   46, -4,   46, -4,   46 },
        {  85, -73,  85, -73,  85, -73,  85, -73 },
        {  54, -61,  54, -61,  54, -61,  54, -61 },
    },{/*  17-19   */
        {  61,  54,  61,  54,  61,  54,  61,  54 },
        { -73, -85, -73, -85, -73, -85, -73, -85 },
        { -46,  -4, -46,  -4, -46,  -4, -46,  -4 },
        {  82,  88,  82,  88,  82,  88,  82,  88 },
        {  31, -46,  31, -46,  31, -46,  31, -46 },
        { -88, -61, -88, -61, -88, -61, -88, -61 },
        { -13,  82, -13,  82, -13,  82, -13,  82 },
        {  90,  13,  90,  13,  90,  13,  90,  13 },
        { -4, -90,  -4, -90,  -4, -90,  -4, -90 },
        { -90,  38, -90,  38, -90,  38, -90,  38 },
        {  22,  67,  22,  67,  22,  67,  22,  67 },
        {  85, -78,  85, -78,  85, -78,  85, -78 },
        { -38, -22, -38, -22, -38, -22, -38, -22 },
        { -78,  90, -78,  90, -78,  90, -78,  90 },
        {  54, -31,  54, -31,  54, -31,  54, -31 },
        {  67, -73,  67, -73,  67, -73,  67, -73 },
    },{ /*  21-23   */
        {  46,  38,  46,  38,  46,  38,  46,  38 },
        { -90, -88, -90, -88, -90, -88, -90, -88 },
        {  38,  73,  38,  73,  38,  73,  38,  73 },
        {  54,  -4,  54,  -4,  54,  -4,  54,  -4 },
        { -90, -67, -90, -67, -90, -67, -90, -67 },
        {  31,  90,  31,  90,  31,  90,  31,  90 },
        {  61, -46,  61, -46,  61, -46,  61, -46 },
        { -88, -31, -88, -31, -88, -31, -88, -31 },
        {  22,  85,  22,  85,  22,  85,  22,  85 },
        {  67, -78,  67, -78,  67, -78,  67, -78 },
        { -85,  13, -85,  13, -85,  13, -85,  13 },
        {  13,  61,  13,  61,  13,  61,  13,  61 },
        {  73, -90,  73, -90,  73, -90,  73, -90 },
        { -82,  54, -82,  54, -82,  54, -82,  54 },
        {   4,  22,   4,  22,   4,  22,   4,  22 },
        {  78, -82,  78, -82,  78, -82,  78, -82 },
    },{ /*  25-27   */
        {  31,  22,  31,  22,  31,  22,  31,  22 },
        { -78, -61, -78, -61, -78, -61, -78, -61 },
        {  90,  85,  90,  85,  90,  85,  90,  85 },
        { -61, -90, -61, -90, -61, -90, -61, -90 },
        {   4,  73,   4,  73,   4,  73,   4,  73 },
        {  54, -38,  54, -38,  54, -38,  54, -38 },
        { -88,  -4, -88,  -4, -88,  -4, -88,  -4 },
        {  82,  46,  82,  46,  82,  46,  82,  46 },
        { -38, -78, -38, -78, -38, -78, -38, -78 },
        { -22,  90, -22,  90, -22,  90, -22,  90 },
        {  73, -82,  73, -82,  73, -82,  73, -82 },
        { -90,  54, -90,  54, -90,  54, -90,  54 },
        {  67, -13,  67, -13,  67, -13,  67, -13 },
        { -13, -31, -13, -31, -13, -31, -13, -31 },
        { -46,  67, -46,  67, -46,  67, -46,  67 },
        {  85, -88,  85, -88,  85, -88,  85, -88 },
    },{/*  29-31   */
        {  13,   4,  13,   4,  13,   4,  13,   4 },
        { -38, -13, -38, -13, -38, -13, -38, -13 },
        {  61,  22,  61,  22,  61,  22,  61,  22 },
        { -78, -31, -78, -31, -78, -31, -78, -31 },
        {  88,  38,  88,  38,  88,  38,  88,  38 },
        { -90, -46, -90, -46, -90, -46, -90, -46 },
        {  85,  54,  85,  54,  85,  54,  85,  54 },
        { -73, -61, -73, -61, -73, -61, -73, -61 },
        {  54,  67,  54,  67,  54,  67,  54,  67 },
        { -31, -73, -31, -73, -31, -73, -31, -73 },
        {   4,  78,   4,  78,   4,  78,   4,  78 },
        {  22, -82,  22, -82,  22, -82,  22, -82 },
        { -46,  85, -46,  85, -46,  85, -46,  85 },
        {  67, -88,  67, -88,  67, -88,  67, -88 },
        { -82,  90, -82,  90, -82,  90, -82,  90 },
        {  90, -90,  90, -90,  90, -90,  90, -90 },
    }
};

#define shift_1st 7
#define add_1st (1 << (shift_1st - 1))

#if HAVE_SSE4
void ff_hevc_transform_skip_8_sse4(i16 *_dst, i16 *coeffs, ptrdiff_t _stride)
{
    i16 *dst = (i16*)_dst;
    ptrdiff_t stride = _stride;
    int shift = 5;
    int offset = 16;
    __m128i r0, r1, r2, r3, r4, r5, r6, r9;

    r9 = _mm_setzero_si128();
    r2 = _mm_set1_epi16(offset);

    r0 = _MM_LOAD_SI128((__m128i*)(coeffs));
    r1 = _MM_LOAD_SI128((__m128i*)(coeffs + 8));

    r0 = _mm_adds_epi16(r0, r2);
    r1 = _mm_adds_epi16(r1, r2);

    r0 = _mm_srai_epi16(r0, shift);
    r1 = _mm_srai_epi16(r1, shift);

    _MM_STORE_SI128((__m128i*)(dst    ), r0);
    _MM_STORE_SI128((__m128i*)(dst + 8), r1);
}


#endif

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define INIT_8()                                                               \
    i16 *dst = (i16*) _dst;                                            \
    ptrdiff_t stride = _stride

#define INIT_OFFSET_8(offset)                                                               \
    i16 *dst = (i16*) (_dst + offset);                                            \
    ptrdiff_t stride = _stride

#define INIT_10()                                                              \
    u16 *dst = (u16*) _dst;                                          \
    ptrdiff_t stride = _stride>>1
#define INIT8_8()                                                              \
    i16 *p_dst;                                                            \
    INIT_8()
#define INIT8_10()                                                             \
    u16 *p_dst;                                                           \
    INIT_10()

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define LOAD_EMPTY(dst, src)
#define LOAD4x4(dst, src)                                                      \
    dst ## 0 = _MM_LOAD_SI128((__m128i *) &src[0]);                           \
    dst ## 1 = _MM_LOAD_SI128((__m128i *) &src[8])
#define LOAD4x4_STEP(dst, src, sstep)                                          \
    tmp0 = _mm_loadl_epi64((__m128i *) &src[0 * sstep]);                       \
    tmp1 = _mm_loadl_epi64((__m128i *) &src[1 * sstep]);                       \
    tmp2 = _mm_loadl_epi64((__m128i *) &src[2 * sstep]);                       \
    tmp3 = _mm_loadl_epi64((__m128i *) &src[3 * sstep]);                       \
    dst ## 0 = _mm_unpacklo_epi16(tmp0, tmp2);                                 \
    dst ## 1 = _mm_unpacklo_epi16(tmp1, tmp3)
#define LOAD8x8_E(dst, src, sstep)                                             \
    dst ## 0 = _MM_LOAD_SI128((__m128i *) &src[0 * sstep]);                   \
    dst ## 1 = _MM_LOAD_SI128((__m128i *) &src[1 * sstep]);                   \
    dst ## 2 = _MM_LOAD_SI128((__m128i *) &src[2 * sstep]);                   \
    dst ## 3 = _MM_LOAD_SI128((__m128i *) &src[3 * sstep])
#define LOAD8x8_O(dst, src, sstep)                                             \
    tmp0 = _MM_LOAD_SI128((__m128i *) &src[1 * sstep]);                       \
    tmp1 = _MM_LOAD_SI128((__m128i *) &src[3 * sstep]);                       \
    tmp2 = _MM_LOAD_SI128((__m128i *) &src[5 * sstep]);                       \
    tmp3 = _MM_LOAD_SI128((__m128i *) &src[7 * sstep]);                       \
    dst ## 0 = _mm_unpacklo_epi16(tmp0, tmp1);                                 \
    dst ## 1 = _mm_unpackhi_epi16(tmp0, tmp1);                                 \
    dst ## 2 = _mm_unpacklo_epi16(tmp2, tmp3);                                 \
    dst ## 3 = _mm_unpackhi_epi16(tmp2, tmp3)
#define LOAD16x16_O(dst, src, sstep)                                           \
    LOAD8x8_O(dst, src, sstep);                                                \
    tmp0 = _MM_LOAD_SI128((__m128i *) &src[ 9 * sstep]);                      \
    tmp1 = _MM_LOAD_SI128((__m128i *) &src[11 * sstep]);                      \
    tmp2 = _MM_LOAD_SI128((__m128i *) &src[13 * sstep]);                      \
    tmp3 = _MM_LOAD_SI128((__m128i *) &src[15 * sstep]);                      \
    dst ## 4 = _mm_unpacklo_epi16(tmp0, tmp1);                                 \
    dst ## 5 = _mm_unpackhi_epi16(tmp0, tmp1);                                 \
    dst ## 6 = _mm_unpacklo_epi16(tmp2, tmp3);                                 \
    dst ## 7 = _mm_unpackhi_epi16(tmp2, tmp3)

#define LOAD_8x32(dst, dst_stride, src0, src1, idx)                            \
    src0 = _MM_LOAD_SI128((__m128i *) &dst[idx*dst_stride]);                   \
    src1 = _MM_LOAD_SI128((__m128i *) &dst[idx*dst_stride+4])

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define ASSIGN_EMPTY(dst, dst_stride, src)
#define SAVE_8x16(dst, dst_stride, src)                                        \
    _MM_STORE_SI128((__m128i *) dst, src);                                     \
    dst += 8
#define SAVE_8x16_2(dst, dst_stride, src)                                        \
    _MM_STORE_SI128((__m128i *) dst, src);                                    \
    dst += dst_stride
#define SAVE_8x32(dst, dst_stride, src0, src1, idx)                            \
    _MM_STORE_SI128((__m128i *) &dst[idx*dst_stride]  , src0);                 \
    _MM_STORE_SI128((__m128i *) &dst[idx*dst_stride+4], src1)
#define ADD_AND_SAVE_8(dst, dst_stride, src)                                   \
    tmp0 = _mm_loadl_epi64((__m128i *) dst);                                   \
    tmp0 = _mm_unpacklo_epi8(tmp0, _mm_setzero_si128());                       \
    tmp0 = _mm_add_epi16(src, tmp0);                                           \
    tmp0 = _mm_packus_epi16(tmp0, _mm_setzero_si128())
#define ADD_AND_SAVE_4x8(dst, dst_stride, src)                                 \
    ADD_AND_SAVE_8(dst, dst_stride, src);                                      \
    *((u32 *)(dst)) = _mm_cvtsi128_si32(tmp0);                            \
    dst += dst_stride
#define SAVE_4x8(dst, dst_stride, src)                                         \
    *((u32 *)(dst)) = _mm_cvtsi128_si32(src);                             \
    dst += dst_stride
#define ADD_AND_SAVE_8x8(dst, dst_stride, src)                                 \
    ADD_AND_SAVE_8(dst, dst_stride, src);                                      \
    _mm_storel_epi64((__m128i *) dst, tmp0);                                   \
    dst += dst_stride
#define SAVE_8x8(dst, dst_stride, src)                                   \
    _mm_storel_epi64((__m128i *) dst, src);                                   \
    dst += dst_stride
#define ADD_AND_SAVE_10(dst, dst_stride, src)                                  \
    tmp0 = _mm_add_epi16(src, tmp0);                                           \
    tmp1 = _mm_set1_epi16(0x03ff);                                             \
    tmp0 = _mm_max_epi16(tmp0, _mm_setzero_si128());                           \
    tmp0 = _mm_min_epi16(tmp0, tmp1)
#define ADD_AND_SAVE_4x10(dst, dst_stride, src)                                \
    tmp0 = _mm_loadl_epi64((__m128i *) dst);                                   \
    ADD_AND_SAVE_10(dst, dst_stride, src);                                     \
    _mm_storel_epi64((__m128i *) dst, tmp0);                                   \
    dst += dst_stride
#define ADD_AND_SAVE_8x10(dst, dst_stride, src)                                \
    tmp0 = _MM_LOAD_SI128((__m128i *) dst);                                   \
    ADD_AND_SAVE_10(dst, dst_stride, src);                                     \
    _MM_STORE_SI128((__m128i *) dst, tmp0);                                   \
    dst += dst_stride
#define ADD_AND_SAVE_16x8(dst, dst_stride, src)                               \
    tmp0 = _MM_LOAD_SI128((__m128i *) dst);                                   \
    tmp2 = _MM_LOAD_SI128((__m128i *) src);                                   \
    tmp3 = _MM_LOAD_SI128((__m128i *) &src[8]);                               \
    tmp1 = _mm_unpackhi_epi8(tmp0, _mm_setzero_si128());                       \
    tmp0 = _mm_unpacklo_epi8(tmp0, _mm_setzero_si128());                       \
    tmp1 = _mm_add_epi16(tmp1, tmp3);                                          \
    tmp0 = _mm_add_epi16(tmp0, tmp2);                                          \
    tmp0 = _mm_packus_epi16(tmp0, tmp1);                                       \
    _MM_STORE_SI128((__m128i *) dst, tmp0);                                   \
    dst += dst_stride
#define ADD_AND_SAVE_16x10(dst, dst_stride, src)                               \
    tmp0 = _MM_LOAD_SI128((__m128i *) dst);                                   \
    tmp1 = _MM_LOAD_SI128((__m128i *) &dst[8]);                               \
    tmp2 = _MM_LOAD_SI128((__m128i *) src);                                   \
    tmp3 = _MM_LOAD_SI128((__m128i *) &src[8]);                               \
    tmp0 = _mm_add_epi16(tmp0, tmp2);                                          \
    tmp1 = _mm_add_epi16(tmp1, tmp3);                                          \
    tmp2 = _mm_set1_epi16(0x03ff);                                             \
    tmp0 = _mm_max_epi16(tmp0, _mm_setzero_si128());                           \
    tmp1 = _mm_max_epi16(tmp1, _mm_setzero_si128());                           \
    tmp0 = _mm_min_epi16(tmp0, tmp2);                                          \
    tmp1 = _mm_min_epi16(tmp1, tmp2);                                          \
    _MM_STORE_SI128((__m128i *) dst, tmp0);                                   \
    _MM_STORE_SI128((__m128i *) &dst[8], tmp1);                               \
    dst += dst_stride

#define ASSIGN2(dst, dst_stride, src0, src1, assign)                           \
    assign(dst, dst_stride, src0);                                             \
    assign(dst, dst_stride, src1)
#define ASSIGN4(dst, dst_stride, src0, src1, src2, src3, assign)               \
    assign(dst, dst_stride, src0);                                             \
    assign(dst, dst_stride, src1);                                             \
    assign(dst, dst_stride, src2);                                             \
    assign(dst, dst_stride, src3)
#define ASSIGN4_LO(dst, dst_stride, src, assign)                               \
    ASSIGN4(dst, dst_stride, src ## 0, src ## 1, src ## 2, src ## 3, assign)
#define ASSIGN4_HI(dst, dst_stride, src, assign)                               \
    ASSIGN4(dst, dst_stride, src ## 4, src ## 5, src ## 6, src ## 7, assign)

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define TRANSPOSE4X4_16(dst)                                                   \
    tmp0 = _mm_unpacklo_epi16(dst ## 0, dst ## 1);                             \
    tmp1 = _mm_unpackhi_epi16(dst ## 0, dst ## 1);                             \
    dst ## 0 = _mm_unpacklo_epi16(tmp0, tmp1);                                 \
    dst ## 1 = _mm_unpackhi_epi16(tmp0, tmp1)
#define TRANSPOSE4X4_16_S(dst, dst_stride, src, assign)                        \
    TRANSPOSE4X4_16(src);                                                      \
    ASSIGN2(dst, dst_stride, src ## 0, src ## 1, assign)

#define TRANSPOSE8X8_16(dst)                                                   \
    tmp0 = _mm_unpacklo_epi16(dst ## 0, dst ## 1);                             \
    tmp1 = _mm_unpacklo_epi16(dst ## 2, dst ## 3);                             \
    tmp2 = _mm_unpacklo_epi16(dst ## 4, dst ## 5);                             \
    tmp3 = _mm_unpacklo_epi16(dst ## 6, dst ## 7);                             \
    src0 = _mm_unpacklo_epi32(tmp0, tmp1);                                     \
    src1 = _mm_unpacklo_epi32(tmp2, tmp3);                                     \
    src2 = _mm_unpackhi_epi32(tmp0, tmp1);                                     \
    src3 = _mm_unpackhi_epi32(tmp2, tmp3);                                     \
    tmp0 = _mm_unpackhi_epi16(dst ## 0, dst ## 1);                             \
    tmp1 = _mm_unpackhi_epi16(dst ## 2, dst ## 3);                             \
    tmp2 = _mm_unpackhi_epi16(dst ## 4, dst ## 5);                             \
    tmp3 = _mm_unpackhi_epi16(dst ## 6, dst ## 7);                             \
    dst ## 0 = _mm_unpacklo_epi64(src0 , src1);                                \
    dst ## 1 = _mm_unpackhi_epi64(src0 , src1);                                \
    dst ## 2 = _mm_unpacklo_epi64(src2 , src3);                                \
    dst ## 3 = _mm_unpackhi_epi64(src2 , src3);                                \
    src0 = _mm_unpacklo_epi32(tmp0, tmp1);                                     \
    src1 = _mm_unpacklo_epi32(tmp2, tmp3);                                     \
    src2 = _mm_unpackhi_epi32(tmp0, tmp1);                                     \
    src3 = _mm_unpackhi_epi32(tmp2, tmp3);                                     \
    dst ## 4 = _mm_unpacklo_epi64(src0 , src1);                                \
    dst ## 5 = _mm_unpackhi_epi64(src0 , src1);                                \
    dst ## 6 = _mm_unpacklo_epi64(src2 , src3);                                \
    dst ## 7 = _mm_unpackhi_epi64(src2 , src3)
#define TRANSPOSE8x8_16_S(out, sstep_out, src, assign)                         \
    TRANSPOSE8X8_16(src);                                                      \
    p_dst = out;                                                               \
    ASSIGN4_LO(p_dst, sstep_out, src, assign);                                 \
    ASSIGN4_HI(p_dst, sstep_out, src, assign)
#define TRANSPOSE8x8_16_LS(out, sstep_out, in, sstep_in, assign)               \
    e0  = _MM_LOAD_SI128((__m128i *) &in[0*sstep_in]);                         \
    e1  = _MM_LOAD_SI128((__m128i *) &in[1*sstep_in]);                         \
    e2  = _MM_LOAD_SI128((__m128i *) &in[2*sstep_in]);                         \
    e3  = _MM_LOAD_SI128((__m128i *) &in[3*sstep_in]);                         \
    e4  = _MM_LOAD_SI128((__m128i *) &in[4*sstep_in]);                         \
    e5  = _MM_LOAD_SI128((__m128i *) &in[5*sstep_in]);                         \
    e6  = _MM_LOAD_SI128((__m128i *) &in[6*sstep_in]);                         \
    e7  = _MM_LOAD_SI128((__m128i *) &in[7*sstep_in]);                         \
    TRANSPOSE8x8_16_S(out, sstep_out, e, assign)

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define TR_COMPUTE_TRANFORM(dst1, dst2, src0, src1, src2, src3, i, j, transform)\
    tmp1 = _MM_LOAD_SI128((__m128i *) transform[i  ][j]);                      \
    tmp3 = _MM_LOAD_SI128((__m128i *) transform[i+1][j]);                      \
    tmp0 = _mm_madd_epi16(src0, tmp1);                                         \
    tmp1 = _mm_madd_epi16(src1, tmp1);                                         \
    tmp2 = _mm_madd_epi16(src2, tmp3);                                         \
    tmp3 = _mm_madd_epi16(src3, tmp3);                                         \
    dst1 = _mm_add_epi32(tmp0, tmp2);                                          \
    dst2 = _mm_add_epi32(tmp1, tmp3)

#define SCALE8x8_2x32(dst0, src0, src1)                                        \
    src0 = _mm_srai_epi32(src0, shift);                                        \
    src1 = _mm_srai_epi32(src1, shift);                                        \
    dst0 = _mm_packs_epi32(src0, src1)
#define SCALE_4x32(dst0, dst1, src0, src1, src2, src3)                         \
    SCALE8x8_2x32(dst0, src0, src1);                                           \
    SCALE8x8_2x32(dst1, src2, src3)
#define SCALE16x16_2x32(dst, dst_stride, src0, src1, j)                        \
    e0   = _MM_LOAD_SI128((__m128i *) &o16[j*8+0]);                           \
    e7   = _MM_LOAD_SI128((__m128i *) &o16[j*8+4]);                           \
    tmp4 = _mm_add_epi32(src0, e0);                                            \
    src0 = _mm_sub_epi32(src0, e0);                                            \
    e0   = _mm_add_epi32(src1, e7);                                            \
    src1 = _mm_sub_epi32(src1, e7);                                            \
    SCALE_4x32(e0, e7, tmp4, e0, src0, src1);                                  \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(             j)]  , e0);     \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(dst_stride-1-j)]  , e7)

#define SCALE32x32_2x32(dst, dst_stride, j)                                    \
    e0   = _MM_LOAD_SI128((__m128i *) &e32[j*16+0]);                          \
    e1   = _MM_LOAD_SI128((__m128i *) &e32[j*16+4]);                          \
    e4   = _MM_LOAD_SI128((__m128i *) &o32[j*16+0]);                          \
    e5   = _MM_LOAD_SI128((__m128i *) &o32[j*16+4]);                          \
    tmp0 = _mm_add_epi32(e0, e4);                                              \
    tmp1 = _mm_add_epi32(e1, e5);                                              \
    tmp2 = _mm_sub_epi32(e1, e5);                                              \
    tmp3 = _mm_sub_epi32(e0, e4);                                              \
    SCALE_4x32(tmp0, tmp1, tmp0, tmp1, tmp3, tmp2);                            \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*i+0]  , tmp0);                \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(dst_stride-1-i)+0]  , tmp1)

#define SAVE16x16_2x32(dst, dst_stride, src0, src1, j)                        \
    e0   = _MM_LOAD_SI128((__m128i *) &o16[j*8+0]);                           \
    e7   = _MM_LOAD_SI128((__m128i *) &o16[j*8+4]);                           \
    tmp4 = _mm_add_epi32(src0, e0);                                            \
    src0 = _mm_sub_epi32(src0, e0);                                            \
    e0   = _mm_add_epi32(src1, e7);                                            \
    src1 = _mm_sub_epi32(src1, e7);                                            \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(             j)]  , tmp4);   \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(             j)+4], e0);     \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(dst_stride-1-j)]  , src0);   \
    _MM_STORE_SI128((__m128i *) &dst[dst_stride*(dst_stride-1-j)+4], src1)


#define SCALE8x8_2x32_WRAPPER(dst, dst_stride, dst0, src0, src1, idx)          \
    SCALE8x8_2x32(dst0, src0, src1)
#define SCALE16x16_2x32_WRAPPER(dst, dst_stride, dst0, src0, src1, idx)        \
    SCALE16x16_2x32(dst, dst_stride, src0, src1, idx)
#define SAVE16x16_2x32_WRAPPER(dst, dst_stride, dst0, src0, src1, idx)         \
    SAVE16x16_2x32(dst, dst_stride, src0, src1, idx)

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_4x4_luma_add_X_sse4
////////////////////////////////////////////////////////////////////////////////
#define COMPUTE_LUMA(dst , idx)                                                \
    tmp0 = _MM_LOAD_SI128((__m128i *) (transform4x4_luma[idx  ]));            \
    tmp1 = _MM_LOAD_SI128((__m128i *) (transform4x4_luma[idx+1]));            \
    tmp0 = _mm_madd_epi16(src0, tmp0);                                         \
    tmp1 = _mm_madd_epi16(src1, tmp1);                                         \
    dst  = _mm_add_epi32(tmp0, tmp1);                                          \
    dst  = _mm_add_epi32(dst, add);                                            \
    dst  = _mm_srai_epi32(dst, shift)
#define COMPUTE_LUMA_ALL()                                                     \
    add  = _mm_set1_epi32(1 << (shift - 1));                                   \
    src0 = _mm_unpacklo_epi16(tmp0, tmp1);                                     \
    src1 = _mm_unpackhi_epi16(tmp0, tmp1);                                     \
    COMPUTE_LUMA(res2 , 0);                                                    \
    COMPUTE_LUMA(res3 , 2);                                                    \
    res0 = _mm_packs_epi32(res2, res3);                                        \
    COMPUTE_LUMA(res2 , 4);                                                    \
    COMPUTE_LUMA(res3 , 6);                                                    \
    res1 = _mm_packs_epi32(res2, res3)

#define TRANSFORM_LUMA_ADD(D)                                                  \
void ff_hevc_transform_4x4_luma_ ## D ## _sse4(                                \
        i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                           \
    u8  shift = 7;                                                             \
    i16 *src = coeffs;                                                         \
    __m128i res0, res1, res2, res3;                                            \
    __m128i tmp0, tmp1, src0, src1, add;                                       \
    INIT_ ## D();                                                              \
    LOAD4x4(tmp, src);                                                         \
    COMPUTE_LUMA_ALL();                                                        \
    shift = 20 - D;                                                            \
    res2  = _mm_unpacklo_epi16(res0, res1);                                    \
    res3  = _mm_unpackhi_epi16(res0, res1);                                    \
    tmp0  = _mm_unpacklo_epi16(res2, res3);                                    \
    tmp1  = _mm_unpackhi_epi16(res2, res3);                                    \
    COMPUTE_LUMA_ALL();                                                        \
    TRANSPOSE4X4_16(res);                                                      \
    SAVE_8x16(dst, stride, res0);                                              \
    SAVE_8x16(dst, stride, res1);                                              \
}

TRANSFORM_LUMA_ADD( 8);
//TRANSFORM_LUMA_ADD( 10);

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_4x4_add_X_sse4
////////////////////////////////////////////////////////////////////////////////
#define COMPUTE4x4(dst0, dst1, dst2, dst3)                                     \
    tmp0 = _MM_LOAD_SI128((__m128i *) transform4x4[0]);                        \
    tmp1 = _MM_LOAD_SI128((__m128i *) transform4x4[1]);                        \
    tmp2 = _MM_LOAD_SI128((__m128i *) transform4x4[2]);                        \
    tmp3 = _MM_LOAD_SI128((__m128i *) transform4x4[3]);                        \
    tmp0 = _mm_madd_epi16(e6, tmp0);                                           \
    tmp1 = _mm_madd_epi16(e6, tmp1);                                           \
    tmp2 = _mm_madd_epi16(e7, tmp2);                                           \
    tmp3 = _mm_madd_epi16(e7, tmp3);                                           \
    e6   = _mm_set1_epi32(add);                                                \
    tmp0 = _mm_add_epi32(tmp0, e6);                                            \
    tmp1 = _mm_add_epi32(tmp1, e6);                                            \
    dst0 = _mm_add_epi32(tmp0, tmp2);                                          \
    dst1 = _mm_add_epi32(tmp1, tmp3);                                          \
    dst2 = _mm_sub_epi32(tmp1, tmp3);                                          \
    dst3 = _mm_sub_epi32(tmp0, tmp2)
#define COMPUTE4x4_LO()                                                        \
    COMPUTE4x4(e0, e1, e2, e3)
#define COMPUTE4x4_HI(dst)                                                     \
    COMPUTE4x4(e7, e6, e5, e4)

#define TR_4(dst, dst_stride, in, sstep, load, assign)                         \
    load(e, in);                                                               \
    e6 = _mm_unpacklo_epi16(e0, e1);                                           \
    e7 = _mm_unpackhi_epi16(e0, e1);                                           \
    COMPUTE4x4_LO();                                                           \
    SCALE_4x32(e0, e1, e0, e1, e2, e3);                                        \
    TRANSPOSE4X4_16_S(dst, dst_stride, e, assign)                              \

#define TR_4_1( dst, dst_stride, src)    TR_4( dst, dst_stride, src,  4, LOAD4x4, ASSIGN_EMPTY)
#define TR_4_2( dst, dst_stride, src, D) TR_4( dst, dst_stride, src,  4, LOAD_EMPTY, SAVE_8x16)

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_8x8_add_X_sse4
////////////////////////////////////////////////////////////////////////////////
#define TR_4_set8x4(in, sstep)                                                 \
    LOAD8x8_E(src, in, sstep);                                                 \
    e6 = _mm_unpacklo_epi16(src0, src2);                                       \
    e7 = _mm_unpacklo_epi16(src1, src3);                                       \
    COMPUTE4x4_LO();                                                           \
    e6 = _mm_unpackhi_epi16(src0, src2);                                       \
    e7 = _mm_unpackhi_epi16(src1, src3);                                       \
    COMPUTE4x4_HI()

#define TR_COMPUTE8x8(e0, e1, i)                                               \
    TR_COMPUTE_TRANFORM(tmp2, tmp3, src0, src1, src2, src3, i, 0, transform8x8);\
    tmp0 = _mm_add_epi32(e0, tmp2);                                            \
    tmp1 = _mm_add_epi32(e1, tmp3);                                            \
    tmp3 = _mm_sub_epi32(e1, tmp3);                                            \
    tmp2 = _mm_sub_epi32(e0, tmp2)

#define TR_8(dst, dst_stride, in, sstep, assign)                               \
    TR_4_set8x4(in, 2 * sstep);                                                \
    LOAD8x8_O(src, in, sstep);                                                 \
    TR_COMPUTE8x8(e0, e7, 0);                                                  \
    assign(dst, dst_stride, e0, tmp0, tmp1, 0);                                \
    assign(dst, dst_stride, e7, tmp2, tmp3, 7);                                \
    TR_COMPUTE8x8(e1, e6, 2);                                                  \
    assign(dst, dst_stride, e1, tmp0, tmp1, 1);                                \
    assign(dst, dst_stride, e6, tmp2, tmp3, 6);                                \
    TR_COMPUTE8x8(e2, e5, 4);                                                  \
    assign(dst, dst_stride, e2, tmp0, tmp1, 2);                                \
    assign(dst, dst_stride, e5, tmp2, tmp3, 5);                                \
    TR_COMPUTE8x8(e3, e4, 6);                                                  \
    assign(dst, dst_stride, e3, tmp0, tmp1, 3);                                \
    assign(dst, dst_stride, e4, tmp2, tmp3, 4);                                \

#define TR_8_1( dst, dst_stride, src)                                         \
    TR_8( dst, dst_stride, src,  8, SCALE8x8_2x32_WRAPPER);                    \
    TRANSPOSE8x8_16_S(dst, dst_stride, e, SAVE_8x16)
#define TR_8_2( dst, dst_stride, src, D)                                       \
    TR_8( dst, dst_stride, src,  8, SCALE8x8_2x32_WRAPPER);                    \
    TRANSPOSE8x8_16_S(dst, dst_stride, e, SAVE_8x16)

#define TR_8_1_stride( dst, dst_stride, src)                                         \
    TR_8( dst, dst_stride, src,  8, SCALE8x8_2x32_WRAPPER);                    \
    TRANSPOSE8x8_16_S(dst, dst_stride, e, SAVE_8x16_2)
#define TR_8_2_stride( dst, dst_stride, src, D)                                       \
    TR_8( dst, dst_stride, src,  8, SCALE8x8_2x32_WRAPPER);                    \
    TRANSPOSE8x8_16_S(dst, dst_stride, e, SAVE_8x16_2)

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_XxX_add_X_sse4
////////////////////////////////////////////////////////////////////////////////

#define TRANSFORM_ADD4x4(D)                                                    \
void ff_hevc_transform_4x4_ ## D ## _sse4 (                                \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                       \
    i16 *src    = coeffs;                                                  \
    int      shift  = 7;                                                       \
    int      add    = 1 << (shift - 1);                                        \
    __m128i tmp0, tmp1, tmp2, tmp3;                                            \
    __m128i e0, e1, e2, e3, e6, e7;                                            \
    INIT_ ## D();                                                              \
    TR_4_1(p_dst1, 4, src);                                                      \
    shift   = 20 - D;                                                          \
    add     = 1 << (shift - 1);                                                \
    TR_4_2(dst, stride, tmp, D);                                               \
}
#define TRANSFORM_ADD8x8(D, S)                                                    \
void ff_hevc_transform_8x8_ ## D ## S ## _sse4 (                                \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                       \
    i16 tmp[8*8];                                                          \
    i16 *src    = coeffs;                                                  \
    i16 *p_dst1 = tmp;                                                     \
    i16 *p_dst;                                                            \
    int      shift  = 7;                                                       \
    int      add    = 1 << (shift - 1);                                        \
    __m128i src0, src1, src2, src3;                                            \
    __m128i tmp0, tmp1, tmp2, tmp3;                                            \
    __m128i e0, e1, e2, e3, e4, e5, e6, e7;                                    \
    TR_8_1 ## S(p_dst1, 8, src);                                                    \
    shift   = 20 - D;                                                          \
    add     = 1 << (shift - 1);                                                \
    {                                                                          \
        INIT8_ ## D();                                                    \
        TR_8_2 ## S(dst, stride, tmp, D);                                           \
    }                                                                          \
}

TRANSFORM_ADD4x4( 8)
//TRANSFORM_ADD4x4(10)
TRANSFORM_ADD8x8( 8,)
//TRANSFORM_ADD8x8(10)
TRANSFORM_ADD8x8( 8, _stride)


////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_16x16_add_X_sse4
////////////////////////////////////////////////////////////////////////////////
#define TR_COMPUTE16x16(dst1, dst2,src0, src1, src2, src3, i, j)              \
    TR_COMPUTE_TRANFORM(dst1, dst2,src0, src1, src2, src3, i, j, transform16x16_1)
#define TR_COMPUTE16x16_FIRST(j)                                               \
    TR_COMPUTE16x16(src0, src1, e0, e1, e2, e3, 0, j)
#define TR_COMPUTE16x16_NEXT(i, j)                                             \
    TR_COMPUTE16x16(tmp0, tmp1, e4, e5, e6, e7, i, j);                         \
    src0 = _mm_add_epi32(src0, tmp0);                                          \
    src1 = _mm_add_epi32(src1, tmp1)

#define TR_16(dst, dst_stride, in, sstep, assign)                              \
    {                                                                          \
        int i;                                                                 \
        int o16[8*8];                                                          \
        LOAD16x16_O(e, in, sstep);                                             \
        for (i = 0; i < 8; i++) {                                              \
            TR_COMPUTE16x16_FIRST(i);                                          \
            TR_COMPUTE16x16_NEXT(2, i);                                        \
            SAVE_8x32(o16, 8, src0, src1, i);                                  \
        }                                                                      \
        TR_8(dst, dst_stride, in, 2 * sstep, assign);                          \
    }

#define TR_16_1( dst, dst_stride, src)        TR_16( dst, dst_stride, src,     16, SCALE16x16_2x32_WRAPPER)
#define TR_16_2( dst, dst_stride, src, sstep) TR_16( dst, dst_stride, src,  sstep, SAVE16x16_2x32_WRAPPER )

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_32x32_add_X_sse4
////////////////////////////////////////////////////////////////////////////////
#define TR_COMPUTE32x32(dst1, dst2,src0, src1, src2, src3, i, j)              \
    TR_COMPUTE_TRANFORM(dst1, dst2, src0, src1, src2, src3, i, j, transform32x32)
#define TR_COMPUTE32x32_FIRST(i, j)                                            \
    TR_COMPUTE32x32(tmp0, tmp1, e0, e1, e2, e3, i, j);                         \
    src0 = _mm_add_epi32(src0, tmp0);                                          \
    src1 = _mm_add_epi32(src1, tmp1)
#define TR_COMPUTE32x32_NEXT(i, j)                                             \
    TR_COMPUTE32x32(tmp0, tmp1, e4, e5, e6, e7, i, j);                         \
    src0 = _mm_add_epi32(src0, tmp0);                                          \
    src1 = _mm_add_epi32(src1, tmp1)

#define TR_32(dst, dst_stride, in, sstep)                                      \
    {                                                                          \
        int i;                                                                 \
        int e32[16*16];                                                        \
        int o32[16*16];                                                        \
        LOAD16x16_O(e, in, sstep);                                             \
        for (i = 0; i < 16; i++) {                                             \
            src0 = _mm_setzero_si128();                                        \
            src1 = _mm_setzero_si128();                                        \
            TR_COMPUTE32x32_FIRST(0, i);                                       \
            TR_COMPUTE32x32_NEXT(2, i);                                        \
            SAVE_8x32(o32, 16, src0, src1, i);                                 \
        }                                                                      \
        LOAD16x16_O(e, (&in[16*sstep]), sstep);                                \
        for (i = 0; i < 16; i++) {                                             \
            LOAD_8x32(o32, 16, src0, src1, i);                                 \
            TR_COMPUTE32x32_FIRST(4, i);                                       \
            TR_COMPUTE32x32_NEXT(6, i);                                        \
            SAVE_8x32(o32, 16, src0, src1, i);                                 \
        }                                                                      \
        TR_16_2(e32, 16, in, 2 * sstep);                                       \
        for (i = 0; i < 16; i++) {                                             \
            SCALE32x32_2x32(dst, dst_stride, i);                               \
        }                                                                      \
    }

#define TR_32_1( dst, dst_stride, src)        TR_32( dst, dst_stride, src, 32)

////////////////////////////////////////////////////////////////////////////////
// ff_hevc_transform_XxX_add_X_sse4
////////////////////////////////////////////////////////////////////////////////
#define TRANSFORM(H, D)                                                   \
void ff_hevc_transform_ ## H ## x ## H ## _ ## D ## _sse4 (                \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                       \
    int i, j, k, add;                                                          \
    int      shift = 7;                                                        \
    i16 *src   = coeffs;                                                   \
    i16  tmp[H*H];                                                         \
    i16  tmp_2[H*H];                                                       \
    i16  tmp_3[H*H];                                                       \
    i16 *p_dst, *p_tra = tmp_2;                                            \
    i16 stride = H;                                                        \
    __m128i src0, src1, src2, src3;                                            \
    __m128i tmp0, tmp1, tmp2, tmp3, tmp4;                                      \
    __m128i e0, e1, e2, e3, e4, e5, e6, e7;                                    \
    for (k = 0; k < 2; k++) {                                                  \
        add   = 1 << (shift - 1);                                              \
        for (i = 0; i < H; i+=8) {                                             \
            p_dst = tmp + i;                                                   \
            TR_ ## H ## _1(p_dst, H, src);                                     \
            src   += 8;                                                        \
            for (j = 0; j < H; j+=8) {                                         \
               TRANSPOSE8x8_16_LS((&p_tra[i*stride+j]), stride, (&tmp[j*H+i]), H, SAVE_8x16_2);\
            }                                                                  \
        }                                                                      \
        src   = tmp_2;                                                         \
        p_tra = _dst;                                                          \
        shift = 20 - D;                                                        \
        stride = _stride;                                                      \
    }                                                                          \
}

TRANSFORM(16,  8);
//TRANSFORM(16, 10);

TRANSFORM(32,  8);
//TRANSFORM(32, 10);

/* Zscan */

/* Zscan var */

#define ZSCAN_VAR0(H, dst)                                                       \
    u ## H  * pu ## H ## Dst = (u ## H  *) dst

#define ZSCAN_VAR_2(dst)                                                       \
    ZSCAN_VAR0(32, dst)

#define ZSCAN_VAR_4(dst)                                                       \
    ZSCAN_VAR0(64, dst)

/* Load */

#define LOAD4x4_8(dst, src, x)                                              \
    dst[x] = _MM_LOAD_SI128((__m128i *) &src[x * 8]);                     \
    dst[x + 1] = _MM_LOAD_SI128((__m128i *) &src[(x + 1) * 8])

#define LOAD8x8_8(dst, src, x)                                              \
	LOAD4x4_8(dst, src, x);                                                 \
	LOAD4x4_8(dst, src, x + 2);                                             \
	LOAD4x4_8(dst, src, x + 4);                                             \
	LOAD4x4_8(dst, src, x + 6)
#define LOAD16x16_8(dst, src, x)                                               \
	LOAD8x8_8(dst, src, x);                                                 \
	LOAD8x8_8(dst, src, x + 8);                                             \
	LOAD8x8_8(dst, src, x + 16);                                            \
	LOAD8x8_8(dst, src, x + 24)
#define LOAD32x32_8(dst, src, x)                                          \
	LOAD16x16_8(dst, src, x);                                               \
	LOAD16x16_8(dst, src, x + 32);                                          \
	LOAD16x16_8(dst, src, x + 64);                                          \
	LOAD16x16_8(dst, src, x + 96)

/* Add */

#define ADD4x4_8(dst, x)                                                   \
    dst[x]     = _mm_adds_epi16(dst[x], rB);                            \
    dst[x + 1] = _mm_adds_epi16(dst[x + 1], rB)
#define ADD8x8_8(dst, x)                                                   \
	ADD4x4_8(dst, x);                                                    \
	ADD4x4_8(dst, x + 2);                                                \
	ADD4x4_8(dst, x + 4);                                                \
	ADD4x4_8(dst, x + 6)
#define ADD16x16_8(dst, x)                                                \
	ADD8x8_8(dst, x);                                                     \
	ADD8x8_8(dst, x + 8);                                                 \
	ADD8x8_8(dst, x + 16);                                                \
	ADD8x8_8(dst, x + 24)
#define ADD32x32_8(dst, x)                                                \
	ADD16x16_8(dst, x);                                                     \
	ADD16x16_8(dst, x + 32);                                                \
	ADD16x16_8(dst, x + 64);                                                \
	ADD16x16_8(dst, x + 96)

/* Shift */

#define SHIFT4x4_8(dst, x)                                                 \
    dst[x]     = _mm_srai_epi16(dst[x], shift);                            \
    dst[x + 1] = _mm_srai_epi16(dst[x + 1], shift)
#define SHIFT8x8_8(dst, x)                                                 \
	SHIFT4x4_8(dst, x);                                                  \
	SHIFT4x4_8(dst, x + 2);                                              \
	SHIFT4x4_8(dst, x + 4);                                              \
	SHIFT4x4_8(dst, x + 6)
#define SHIFT16x16_8(dst, x)                                                \
	SHIFT8x8_8(dst, x);                                                   \
	SHIFT8x8_8(dst, x + 8);                                               \
	SHIFT8x8_8(dst, x + 16);                                              \
	SHIFT8x8_8(dst, x + 24)
#define SHIFT32x32_8(dst, x)                                                \
	SHIFT16x16_8(dst, x);                                                   \
	SHIFT16x16_8(dst, x + 32);                                              \
	SHIFT16x16_8(dst, x + 64);                                              \
	SHIFT16x16_8(dst, x + 96)

/* Store */

// Luma

int zscanTab8_4[16] =
{
   0,  4,
   1,  5,
   2,  6,
   3,  7,
   8, 12,
   9, 13,
  10, 14,
  11, 15
};

int zscanTab16_4[64] =
{
   0,  4, 16, 20,
   1,  5, 17, 21,
   2,  6, 18, 22,
   3,  7, 19, 23,
   8, 12, 24, 28,
   9, 13, 25, 29,
  10, 14, 26, 30,
  11, 15, 27, 31,
  32, 36, 48, 52,
  33, 37, 49, 53,
  34, 38, 50, 54,
  37, 39, 51, 55,
  40, 44, 56, 60,
  41, 45, 57, 61,
  42, 46, 58, 62,
  43, 47, 59, 63
};

int zscanTab32_4[256] =
{
   0,   4,  16,  20,  64,  68,  80,  84,
   1,   5,  17,  21,  65,  69,  81,  85,
   2,   6,  18,  22,  66,  70,  82,  86,
   3,   7,  19,  23,  67,  71,  83,  87,
   8,  12,  24,  28,  72,  76,  88,  92,
   9,  13,  25,  29,  73,  77,  89,  93,
  10,  14,  26,  30,  74,  78,  90,  94,
  11,  15,  27,  31,  75,  79,  91,  95,
  32,  36,  48,  52,  96, 100, 112, 116,
  33,  37,  49,  53,  97, 101, 113, 117,
  34,  38,  50,  54,  98, 102, 114, 118,
  37,  39,  51,  55, 101, 103, 115, 119,
  40,  44,  56,  60, 104, 108, 120, 124,
  41,  45,  57,  61, 105, 109, 121, 125,
  42,  46,  58,  62, 106, 110, 122, 126,
  43,  47,  59,  63, 107, 111, 123, 127,
 128, 132, 144, 148, 192, 196, 208, 212,
 129, 133, 145, 149, 193, 197, 209, 213,
 130, 134, 146, 150, 194, 198, 210, 214,
 131, 135, 147, 151, 195, 199, 211, 215,
 136, 140, 152, 156, 200, 204, 216, 220,
 137, 141, 153, 157, 201, 205, 217, 221,
 138, 142, 154, 158, 202, 206, 218, 222,
 139, 143, 155, 159, 203, 207, 219, 223,
 160, 164, 176, 180, 224, 228, 240, 244,
 161, 165, 177, 181, 225, 229, 241, 245,
 162, 166, 178, 182, 226, 230, 242, 246,
 165, 167, 179, 183, 229, 231, 243, 247,
 168, 172, 184, 188, 232, 236, 248, 252,
 169, 173, 185, 189, 233, 237, 249, 253,
 170, 174, 186, 190, 234, 238, 250, 254,
 171, 175, 187, 191, 235, 239, 251, 255
};

int zscanTab4_2[8] =
{
   0,  2,
   1,  3,
   4,  6,
   5,  7
};

int zscanTab8_2[32] =
{
   0,  2,  8, 10,
   1,  3,  9, 11,
   4,  6, 12, 14,
   5,  7, 13, 15,
  16, 18, 24, 26,
  17, 19, 25, 27,
  20, 22, 28, 30,
  21, 23, 29, 31
};

int zscanTab16_2[128] =
{
   0,   2,   8,  10,  32,  34,  40,  42,
   1,   3,   9,  11,  33,  35,  41,  43,
   4,   6,  12,  14,  36,  38,  44,  46,
   5,   7,  13,  15,  37,  39,  45,  47,
  16,  18,  24,  26,  48,  50,  56,  58,
  17,  19,  25,  27,  49,  51,  57,  59,
  20,  22,  28,  30,  52,  54,  60,  62,
  21,  23,  29,  31,  53,  55,  61,  63,

  64,  66,  72,  74,  96,  98, 104, 106,
  65,  67,  73,  75,  97,  99, 105, 107,
  68,  70,  76,  78, 100, 102, 108, 110,
  69,  71,  77,  79, 101, 103, 109, 111,
  80,  82,  88,  90, 112, 114, 120, 122,
  81,  83,  89,  91, 113, 115, 121, 123,
  84,  86,  92,  94, 116, 118, 124, 126,
  85,  87,  93,  95, 117, 119, 125, 127
};

#define STORE4_4_8(src)                                                           \
	_MM_STORE_SI128((__m128i*)(dst    ), src[0]);                                 \
    _MM_STORE_SI128((__m128i*)(dst + 8), src[1])

#if ARCH_X86_64
#define STORE8_4_8(src)                                                          \
    for (i = 0; i < 8; i++)                                                                    \
	{                                                                                                     \
      pu64Dst[zscanTab8_4[(i << 1) + 0]] = _mm_extract_epi64(src[i], 0);                       \
      pu64Dst[zscanTab8_4[(i << 1) + 1]] = _mm_extract_epi64(src[i], 1);                       \
	}

#define STORE16_4_8(src)                                                          \
	for (i = 0; i < 32; i++)                                                                    \
	{                                                                                                     \
	  pu64Dst[zscanTab16_4[(i << 1) + 0]] = _mm_extract_epi64(src[i], 0);                       \
	  pu64Dst[zscanTab16_4[(i << 1) + 1]] = _mm_extract_epi64(src[i], 1);                       \
	}

#define STORE32_4_8(src)                                                          \
	for (i = 0; i < 128; i++)                                                                    \
	{                                                                                                     \
	  pu64Dst[zscanTab32_4[(i << 1) + 0]] = _mm_extract_epi64(src[i], 0);                       \
	  pu64Dst[zscanTab32_4[(i << 1) + 1]] = _mm_extract_epi64(src[i], 1);                       \
	}
#else // ARCH_X86_64
#define STORE8_4_8(src)                                                          \
    for (i = 0; i < 8; i++)                                                                    \
	{                                                                                                     \
    	_mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[(i << 1) + 0]], src[i]);                       \
    	src[i] = _mm_unpackhi_epi64(src[i], src[i]);                                                       \
        _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[(i << 1) + 1]], src[i]);                       \
	}

#define STORE16_4_8(src)                                                          \
	for (i = 0; i < 32; i++)                                                                    \
	{                                                                                                     \
	  _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab16_4[(i << 1) + 0]], src[i]);                       \
	  src[i] = _mm_unpackhi_epi64(src[i], src[i]);                                                       \
	  _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab16_4[(i << 1) + 1]], src[i]);                       \
	}

#define STORE32_4_8(src)                                                          \
	for (i = 0; i < 128; i++)                                                                    \
	{                                                                                                     \
	  _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab32_4[(i << 1) + 0]], src[i]);                       \
	  src[i] = _mm_unpackhi_epi64(src[i], src[i]);                                                       \
	  _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab32_4[(i << 1) + 1]], src[i]);                       \
    }
#endif // ARCH_X86_XX

// Chroma

#define STORE4_2_8(src)                                                  \
    for (i = 0; i < 2; i++)                                                                    \
	{                                                                               \
	  pu32Dst[zscanTab4_2[(i << 2) + 0]] = _mm_extract_epi32(src[i], 0);                       \
      pu32Dst[zscanTab4_2[(i << 2) + 1]] = _mm_extract_epi32(src[i], 1);                       \
      pu32Dst[zscanTab4_2[(i << 2) + 2]] = _mm_extract_epi32(src[i], 2);                       \
      pu32Dst[zscanTab4_2[(i << 2) + 3]] = _mm_extract_epi32(src[i], 3);                       \
    }

#define STORE8_2_8(src)                                                  \
    for (i = 0; i < 8; i++)                                                                    \
	{                                                                               \
	  pu32Dst[zscanTab8_2[(i << 2) + 0]] = _mm_extract_epi32(src[i], 0);                       \
	  pu32Dst[zscanTab8_2[(i << 2) + 1]] = _mm_extract_epi32(src[i], 1);                       \
	  pu32Dst[zscanTab8_2[(i << 2) + 2]] = _mm_extract_epi32(src[i], 2);                       \
	  pu32Dst[zscanTab8_2[(i << 2) + 3]] = _mm_extract_epi32(src[i], 3);                       \
	}

#define STORE16_2_8(src)                                                  \
	for (i = 0; i < 32; i++)                                                                    \
	{                                                                              \
	  pu32Dst[zscanTab16_2[(i << 2) + 0]] = _mm_extract_epi32(src[i], 0);                       \
      pu32Dst[zscanTab16_2[(i << 2) + 1]] = _mm_extract_epi32(src[i], 1);                       \
      pu32Dst[zscanTab16_2[(i << 2) + 2]] = _mm_extract_epi32(src[i], 2);                       \
      pu32Dst[zscanTab16_2[(i << 2) + 3]] = _mm_extract_epi32(src[i], 3);                       \
    }

#define TRANSFORM_SKIP_ZSCAN(H, K, D)                                                                     \
void ff_hevc_transform_skip_ ## H ## _ ## K ## _zscan_sse(i16 *_dst, i16 *coeffs)      \
{                                                                                                         \
    i16 *dst = (i16*) _dst;                                                                                \
    int shift = 5;                                                                                        \
    int offset = 16;                                                                                      \
    __m128i r[(H * H) >> 3];                                                                              \
    __m128i rA, rB;                                                                              \
    int i;                                                                                                \
                                                                                                          \
    ZSCAN_VAR_ ## K (dst);                                                                                   \
                                                                                                          \
    rA = _mm_setzero_si128();                                                                             \
    rB = _mm_set1_epi16(offset);                                                                          \
                                                                                                          \
    LOAD ## H ## x ## H ## _ ## D (r, coeffs, 0);                                                           \
    ADD ## H ## x ## H ## _ ## D (r, 0);                                                                    \
    SHIFT ## H ## x ## H ## _ ## D (r, 0);                                                                  \
                                                                                                          \
    STORE ## H ## _ ## K ## _ ## D(r);                                                                         \
}

#if HAVE_SSE4
// Luma
TRANSFORM_SKIP_ZSCAN(4,  4, 8);
TRANSFORM_SKIP_ZSCAN(8,  4, 8);
TRANSFORM_SKIP_ZSCAN(16, 4, 8);
TRANSFORM_SKIP_ZSCAN(32, 4, 8);
// Chroma
TRANSFORM_SKIP_ZSCAN(4,  2, 8);
TRANSFORM_SKIP_ZSCAN(8,  2, 8);
TRANSFORM_SKIP_ZSCAN(16,  2, 8);
#endif // HAVE_SSE4

#define ASSIGN_ZSCAN(dst, dst_stride, src, assign)                           \
    assign(src)

#define TRANSPOSE4X4_16_S_ZSCAN(dst, dst_stride, src, assign)                        \
    TRANSPOSE4X4_16(src);                                                      \
    ASSIGN_ZSCAN(dst, dst_stride, src, assign)

#define TR_4_ZSCAN(dst, dst_stride, in, sstep, load, assign)                         \
    load(e, in);                                                               \
    e6 = _mm_unpacklo_epi16(e0, e1);                                           \
    e7 = _mm_unpackhi_epi16(e0, e1);                                           \
    COMPUTE4x4_LO();                                                           \
    SCALE_4x32(e0, e1, e0, e1, e2, e3);                                        \
    TRANSPOSE4X4_16_S_ZSCAN(dst, dst_stride, e, assign)

#define SAVE4_4_8(src)                                                           \
	_MM_STORE_SI128((__m128i*)(dst    ), src ## 0 );                                 \
    _MM_STORE_SI128((__m128i*)(dst + 8), src ## 1 )

#if ARCH_X86_64
#define SAVE8_4_8(src)                                                          \
    pu64Dst[zscanTab8_4[(0 << 1) + 0]] = _mm_extract_epi64(src ## 0 , 0);         \
    pu64Dst[zscanTab8_4[(0 << 1) + 1]] = _mm_extract_epi64(src ## 0 , 1);         \
    pu64Dst[zscanTab8_4[(1 << 1) + 0]] = _mm_extract_epi64(src ## 1 , 0);         \
    pu64Dst[zscanTab8_4[(1 << 1) + 1]] = _mm_extract_epi64(src ## 1 , 1);         \
    pu64Dst[zscanTab8_4[(2 << 1) + 0]] = _mm_extract_epi64(src ## 2 , 0);         \
    pu64Dst[zscanTab8_4[(2 << 1) + 1]] = _mm_extract_epi64(src ## 2 , 1);         \
    pu64Dst[zscanTab8_4[(3 << 1) + 0]] = _mm_extract_epi64(src ## 3 , 0);         \
    pu64Dst[zscanTab8_4[(3 << 1) + 1]] = _mm_extract_epi64(src ## 3 , 1);         \
    pu64Dst[zscanTab8_4[(4 << 1) + 0]] = _mm_extract_epi64(src ## 4 , 0);         \
    pu64Dst[zscanTab8_4[(4 << 1) + 1]] = _mm_extract_epi64(src ## 4 , 1);         \
    pu64Dst[zscanTab8_4[(5 << 1) + 0]] = _mm_extract_epi64(src ## 5 , 0);         \
    pu64Dst[zscanTab8_4[(5 << 1) + 1]] = _mm_extract_epi64(src ## 5 , 1);         \
    pu64Dst[zscanTab8_4[(6 << 1) + 0]] = _mm_extract_epi64(src ## 6 , 0);         \
    pu64Dst[zscanTab8_4[(6 << 1) + 1]] = _mm_extract_epi64(src ## 6 , 1);         \
    pu64Dst[zscanTab8_4[(7 << 1) + 0]] = _mm_extract_epi64(src ## 7 , 0);         \
    pu64Dst[zscanTab8_4[(7 << 1) + 1]] = _mm_extract_epi64(src ## 7 , 1)
#else // ARCH_X86_64
#define SAVE8_4_8(src)                                                          \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[0]], src ## 0 );                       \
    src ## 0  = _mm_unpackhi_epi64(src ## 0 , src ## 0 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[1]], src ## 0 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[2]], src ## 1 );                       \
    src ## 1  = _mm_unpackhi_epi64(src ## 1 , src ## 1 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[3]], src ## 1 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[4]], src ## 2 );                       \
    src ## 2  = _mm_unpackhi_epi64(src ## 2 , src ## 2 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[5]], src ## 2 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[6]], src ## 3 );                       \
    src ## 3  = _mm_unpackhi_epi64(src ## 3 , src ## 3 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[7]], src ## 3 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[8]], src ## 4 );                       \
    src ## 4  = _mm_unpackhi_epi64(src ## 4 , src ## 4 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[9]], src ## 4 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[10]], src ## 5 );                       \
    src ## 5  = _mm_unpackhi_epi64(src ## 5 , src ## 5 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[11]], src ## 5 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[12]], src ## 6 );                       \
    src ## 6  = _mm_unpackhi_epi64(src ## 6 , src ## 6 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[13]], src ## 6 );                       \
                                                                                                      \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[14]], src ## 7 );                       \
    src ## 7  = _mm_unpackhi_epi64(src ## 7 , src ## 7 );                                             \
    _mm_storel_epi64((__m128i*) &pu64Dst[zscanTab8_4[15]], src ## 7 )
#endif // ARCH_X86_64

#define SAVE4_2_8(src)                                                  \
	  pu32Dst[zscanTab4_2[0]] = _mm_extract_epi32(src ## 0 , 0);                       \
      pu32Dst[zscanTab4_2[1]] = _mm_extract_epi32(src ## 0 , 1);                       \
      pu32Dst[zscanTab4_2[2]] = _mm_extract_epi32(src ## 0 , 2);                       \
      pu32Dst[zscanTab4_2[3]] = _mm_extract_epi32(src ## 0 , 3);                       \
      pu32Dst[zscanTab4_2[4]] = _mm_extract_epi32(src ## 1 , 0);                       \
      pu32Dst[zscanTab4_2[5]] = _mm_extract_epi32(src ## 1 , 1);                       \
      pu32Dst[zscanTab4_2[6]] = _mm_extract_epi32(src ## 1 , 2);                       \
      pu32Dst[zscanTab4_2[7]] = _mm_extract_epi32(src ## 1 , 3)

#define SAVE8_2_8(src)                                                  \
	  pu32Dst[zscanTab8_2[0]] = _mm_extract_epi32(src ## 0, 0);                       \
	  pu32Dst[zscanTab8_2[1]] = _mm_extract_epi32(src ## 0, 1);                       \
	  pu32Dst[zscanTab8_2[2]] = _mm_extract_epi32(src ## 0, 2);                       \
	  pu32Dst[zscanTab8_2[3]] = _mm_extract_epi32(src ## 0, 3);                       \
	  pu32Dst[zscanTab8_2[4]] = _mm_extract_epi32(src ## 1, 0);                       \
	  pu32Dst[zscanTab8_2[5]] = _mm_extract_epi32(src ## 1, 1);                       \
	  pu32Dst[zscanTab8_2[6]] = _mm_extract_epi32(src ## 1, 2);                       \
	  pu32Dst[zscanTab8_2[7]] = _mm_extract_epi32(src ## 1, 3);                       \
	  pu32Dst[zscanTab8_2[8]] = _mm_extract_epi32(src ## 2, 0);                       \
      pu32Dst[zscanTab8_2[9]] = _mm_extract_epi32(src ## 2, 1);                       \
	  pu32Dst[zscanTab8_2[10]] = _mm_extract_epi32(src ## 2, 2);                       \
	  pu32Dst[zscanTab8_2[11]] = _mm_extract_epi32(src ## 2, 3);                       \
	  pu32Dst[zscanTab8_2[12]] = _mm_extract_epi32(src ## 3, 0);                       \
	  pu32Dst[zscanTab8_2[13]] = _mm_extract_epi32(src ## 3, 1);                       \
	  pu32Dst[zscanTab8_2[14]] = _mm_extract_epi32(src ## 3, 2);                       \
	  pu32Dst[zscanTab8_2[15]] = _mm_extract_epi32(src ## 3, 3);                       \
	  pu32Dst[zscanTab8_2[16]] = _mm_extract_epi32(src ## 4, 0);                       \
	  pu32Dst[zscanTab8_2[17]] = _mm_extract_epi32(src ## 4, 1);                       \
	  pu32Dst[zscanTab8_2[18]] = _mm_extract_epi32(src ## 4, 2);                       \
	  pu32Dst[zscanTab8_2[19]] = _mm_extract_epi32(src ## 4, 3);                       \
	  pu32Dst[zscanTab8_2[20]] = _mm_extract_epi32(src ## 5, 0);                       \
	  pu32Dst[zscanTab8_2[21]] = _mm_extract_epi32(src ## 5, 1);                       \
	  pu32Dst[zscanTab8_2[22]] = _mm_extract_epi32(src ## 5, 2);                       \
	  pu32Dst[zscanTab8_2[23]] = _mm_extract_epi32(src ## 5, 3);                       \
	  pu32Dst[zscanTab8_2[24]] = _mm_extract_epi32(src ## 6, 0);                       \
	  pu32Dst[zscanTab8_2[25]] = _mm_extract_epi32(src ## 6, 1);                       \
	  pu32Dst[zscanTab8_2[26]] = _mm_extract_epi32(src ## 6, 2);                       \
	  pu32Dst[zscanTab8_2[27]] = _mm_extract_epi32(src ## 6, 3);                       \
	  pu32Dst[zscanTab8_2[28]] = _mm_extract_epi32(src ## 7, 0);                       \
	  pu32Dst[zscanTab8_2[29]] = _mm_extract_epi32(src ## 7, 1);                       \
	  pu32Dst[zscanTab8_2[30]] = _mm_extract_epi32(src ## 7, 2);                       \
	  pu32Dst[zscanTab8_2[31]] = _mm_extract_epi32(src ## 7, 3)

#define TRANSFORM_LUMA_ZSCAN(D)                                                  \
void ff_hevc_transform_4x4_luma_ ## D ## _zscan_sse4(                                \
        i16 *_dst, i16 *coeffs, ptrdiff_t _stride, u8 offset) {               \
    u8  shift = 7;                                                             \
    i16 *src = coeffs;                                                         \
    __m128i res0, res1, res2, res3;                                            \
    __m128i tmp0, tmp1, src0, src1, add;                                       \
    INIT_OFFSET_ ## D(offset);                                                 \
    LOAD4x4(tmp, src);                                                         \
    COMPUTE_LUMA_ALL();                                                        \
    shift = 20 - D;                                                            \
    res2  = _mm_unpacklo_epi16(res0, res1);                                    \
    res3  = _mm_unpackhi_epi16(res0, res1);                                    \
    tmp0  = _mm_unpacklo_epi16(res2, res3);                                    \
    tmp1  = _mm_unpackhi_epi16(res2, res3);                                    \
    COMPUTE_LUMA_ALL();                                                        \
    TRANSPOSE4X4_16(res);                                                      \
    SAVE_8x16(dst, stride, res0);                                              \
    SAVE_8x16(dst, stride, res1);                                              \
}

TRANSFORM_LUMA_ZSCAN( 8);
//TRANSFORM_LUMA_ZSCAN( 10);

#define TR_4_2_ZSCAN( dst, dst_stride, src, K, D) TR_4_ZSCAN( dst, dst_stride, src,  4, LOAD_EMPTY, SAVE4_ ## K ## _8)

#define TRANSFORM0_4x4_ZSCAN(K, D)                                                     \
void ff_hevc_transform0_4x4_ ## K ## _ ## D ## _zscan_sse4 (                           \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride, u8 offset) {                            \
    i16 *src    = coeffs;                                                              \
    int      shift  = 7;                                                               \
    int      add    = 1 << (shift - 1);                                                \
    __m128i tmp0, tmp1, tmp2, tmp3;                                                    \
    __m128i e0, e1, e2, e3, e6, e7;                                                    \
    INIT_OFFSET_ ## D(offset);                                                         \
    ZSCAN_VAR_ ## K(dst);                                                              \
    TR_4_1(p_dst1, 4, src);                                                            \
    shift   = 20 - D;                                                                  \
    add     = 1 << (shift - 1);                                                        \
    TR_4_2_ZSCAN(dst, stride, tmp, K, D);                                              \
}
#define TRANSFORM_4x4_ZSCAN(D)                                                         \
void ff_hevc_transform_4x4_ ## D ## _zscan_sse4 (                                      \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride, u8 offset) {                            \
  ff_hevc_transform0_4x4_ ## D ## _zscan_sse4_orcc[_stride >> 1](                      \
    _dst, coeffs, _stride, offset);                                                    \
}

#if HAVE_SSE4
TRANSFORM0_4x4_ZSCAN(4, 8)
TRANSFORM0_4x4_ZSCAN(2, 8)

TRANSFORM_4x4_ZSCAN(8)
#endif // HAVE_SSE4

#define TRANSPOSE8X8_16_S_ZSCAN(dst, dst_stride, src, assign)                        \
    TRANSPOSE8X8_16(src);                                                            \
    ASSIGN_ZSCAN(dst, dst_stride, src, assign)

#define TR_8_2_ZSCAN( dst, dst_stride, src, K, D)                                    \
  TR_8(dst, dst_stride, src, 8, SCALE8x8_2x32_WRAPPER);                              \
  TRANSPOSE8X8_16_S_ZSCAN(dst, dst_stride, e, SAVE8_ ## K ## _8)

#define TRANSFORM0_8x8_ZSCAN(K, D)                                                   \
void ff_hevc_transform0_8x8_ ## K ## _ ## D ## _zscan_sse4 (                         \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                                     \
    i16 tmp[8*8];                                                                    \
    i16 *src    = coeffs;                                                            \
    i16 *p_dst1 = tmp;                                                               \
    i16 *p_dst;                                                                      \
    int      shift  = 7;                                                             \
    int      add    = 1 << (shift - 1);                                              \
    __m128i src0, src1, src2, src3;                                                  \
    __m128i tmp0, tmp1, tmp2, tmp3;                                                  \
    __m128i e0, e1, e2, e3, e4, e5, e6, e7;                                          \
    TR_8_1(p_dst1, 8, src);                                                          \
    shift   = 20 - D;                                                                \
    add     = 1 << (shift - 1);                                                      \
    {                                                                                \
        INIT8_ ## D();                                                               \
        ZSCAN_VAR_ ## K(dst);                                                        \
        TR_8_2_ZSCAN(dst, stride, tmp, K, D);                                        \
    }                                                                                \
}

#define TRANSFORM_8x8_ZSCAN(D)                                                    \
void ff_hevc_transform_8x8_ ## D ## _zscan_sse4 (                                \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                       \
  ff_hevc_transform0_8x8_ ## D ## _zscan_sse4_orcc[_stride >> 2](                 \
    _dst, coeffs, _stride);                  \
}

#if HAVE_SSE4
TRANSFORM0_8x8_ZSCAN(4, 8)
TRANSFORM0_8x8_ZSCAN(2, 8)

TRANSFORM_8x8_ZSCAN(8)
#endif // HAVE_SSE4

#define TRANSPOSE8x8_16_LS_ZSCAN(out, sstep_out, in, sstep_in, assign)               \
    e0  = _MM_LOAD_SI128((__m128i *) &in[0*sstep_in]);                         \
    e1  = _MM_LOAD_SI128((__m128i *) &in[1*sstep_in]);                         \
    e2  = _MM_LOAD_SI128((__m128i *) &in[2*sstep_in]);                         \
    e3  = _MM_LOAD_SI128((__m128i *) &in[3*sstep_in]);                         \
    e4  = _MM_LOAD_SI128((__m128i *) &in[4*sstep_in]);                         \
    e5  = _MM_LOAD_SI128((__m128i *) &in[5*sstep_in]);                         \
    e6  = _MM_LOAD_SI128((__m128i *) &in[6*sstep_in]);                         \
    e7  = _MM_LOAD_SI128((__m128i *) &in[7*sstep_in]);                         \
    TRANSPOSE8X8_16_S_ZSCAN(out, sstep_out, e, assign)

#define TRANSFORM_STEP(H, K, D, assign, transpose)                                                                 \
  add = 1 << (shift - 1);                                                                                          \
  for (i = 0; i < H; i += 8) {                                                                                     \
    p_dst = tmp + i;                                                                                               \
    TR_ ## H ## _1(p_dst, H, src);                                                                                 \
    src += 8;                                                                                                      \
    for (j = 0; j < H; j += 8) {                                                                                   \
    	ZSCAN_VAR_ ## K ((&p_tra[(i & 0x0F) * 16 + (i >> 4) * (16 * 32) + (j & 0x0F) * 8 + (j >> 4) * 256]));   \
      transpose((&p_tra[i * H + j]), H, (&tmp[j * H + i]), H, assign);                                             \
    }                                                                                                              \
  }                                                                                                                \
  src = tmp_2;                                                                                                     \
  p_tra = _dst;                                                                                                    \
  shift = 20 - D

#define TRANSFORM0_2_ZSCAN(H, K, D)                                                      \
void ff_hevc_transform0_ ## H ## x ## H ## _ ## K ## _ ## D ## _zscan_sse4 (             \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                                         \
    int i, j, k, add;                                                                    \
    int      shift = 7;                                                                  \
    i16 *src   = coeffs;                                                                 \
    i16  tmp[H*H];                                                                       \
    i16  tmp_2[H*H];                                                                     \
    i16  tmp_3[H*H];                                                                     \
    i16 *p_dst, *p_tra = tmp_2;                                                          \
    __m128i src0, src1, src2, src3;                                                      \
    __m128i tmp0, tmp1, tmp2, tmp3, tmp4;                                                \
    __m128i e0, e1, e2, e3, e4, e5, e6, e7;                                              \
    TRANSFORM_STEP(H, K, D, SAVE_8x16_2, TRANSPOSE8x8_16_LS);                            \
    TRANSFORM_STEP(H, K, D, SAVE8_ ## K ## _8, TRANSPOSE8x8_16_LS_ZSCAN);               \
}

#define TRANSFORM_2_ZSCAN(H, D)                                                         \
void ff_hevc_transform_ ## H ## x ## H ## _ ## D ## _zscan_sse4 (                       \
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride) {                                        \
  ff_hevc_transform0_ ## H ## x ## H ## _ ## D ## _zscan_sse4_orcc[_stride >> 2](       \
    _dst, coeffs, _stride);                                                             \
}

#if HAVE_SSE4
TRANSFORM0_2_ZSCAN(16, 4, 8);
// TRANSFORM0_2_ZSCAN(16, 4, 10);
TRANSFORM0_2_ZSCAN(16, 2, 8);
// TRANSFORM0_2_ZSCAN(16, 2, 10);

TRANSFORM0_2_ZSCAN(32, 4, 8);
// TRANSFORM0_2_ZSCAN(32, 4, 10);
TRANSFORM0_2_ZSCAN(32, 2, 8);
// TRANSFORM0_2_ZSCAN(32, 2, 10);

TRANSFORM_2_ZSCAN(16,  8);
// TRANSFORM_2_ZSCAN(16, 10);
TRANSFORM_2_ZSCAN(32,  8);
// TRANSFORM_2_ZSCAN(32, 10);
#endif // HAVE_SSE4
