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
#else
#define _MM_STORE_SI128      _mm_store_si128
#endif


#if ARCH_X86_64
#define STORE8(out, sstep_out)                                                 \
    *((uint64_t *) &out[0*sstep_out]) =_mm_cvtsi128_si64(m10);                 \
    *((uint64_t *) &out[1*sstep_out]) =_mm_extract_epi64(m10, 1);              \
    *((uint64_t *) &out[2*sstep_out]) =_mm_cvtsi128_si64(m12);                 \
    *((uint64_t *) &out[3*sstep_out]) =_mm_extract_epi64(m12, 1);              \
    *((uint64_t *) &out[4*sstep_out]) =_mm_cvtsi128_si64(m11);                 \
    *((uint64_t *) &out[5*sstep_out]) =_mm_extract_epi64(m11, 1);              \
    *((uint64_t *) &out[6*sstep_out]) =_mm_cvtsi128_si64(m13);                 \
    *((uint64_t *) &out[7*sstep_out]) =_mm_extract_epi64(m13, 1)
#else
#define STORE8(out, sstep_out)                                                 \
    _mm_storel_epi64((__m128i*)&out[0*sstep_out], m10);                        \
    _mm_storel_epi64((__m128i*)&out[2*sstep_out], m12);                        \
    _mm_storel_epi64((__m128i*)&out[4*sstep_out], m11);                        \
    _mm_storel_epi64((__m128i*)&out[6*sstep_out], m13);                        \
    m10 = _mm_unpackhi_epi64(m10, m10);                                        \
    m12 = _mm_unpackhi_epi64(m12, m12);                                        \
    m11 = _mm_unpackhi_epi64(m11, m11);                                        \
    m13 = _mm_unpackhi_epi64(m13, m13);                                        \
    _mm_storel_epi64((__m128i*)&out[1*sstep_out], m10);                        \
    _mm_storel_epi64((__m128i*)&out[3*sstep_out], m12);                        \
    _mm_storel_epi64((__m128i*)&out[5*sstep_out], m11);                        \
    _mm_storel_epi64((__m128i*)&out[7*sstep_out], m13)
#endif
#define STORE16(out, sstep_out)                                                \
    _mm_storeu_si128((__m128i *) &out[0*sstep_out], m0);                       \
    _mm_storeu_si128((__m128i *) &out[1*sstep_out], m1);                       \
    _mm_storeu_si128((__m128i *) &out[2*sstep_out], m2);                       \
    _mm_storeu_si128((__m128i *) &out[3*sstep_out], m3);                       \
    _mm_storeu_si128((__m128i *) &out[4*sstep_out], m4);                       \
    _mm_storeu_si128((__m128i *) &out[5*sstep_out], m5);                       \
    _mm_storeu_si128((__m128i *) &out[6*sstep_out], m6);                       \
    _mm_storeu_si128((__m128i *) &out[7*sstep_out], m7)

#define TRANSPOSE4x4_8(in, sstep_in, out, sstep_out)                           \
    {                                                                          \
        __m128i m0  = _mm_loadl_epi64((__m128i *) &in[0*sstep_in]);            \
        __m128i m1  = _mm_loadl_epi64((__m128i *) &in[1*sstep_in]);            \
        __m128i m2  = _mm_loadl_epi64((__m128i *) &in[2*sstep_in]);            \
        __m128i m3  = _mm_loadl_epi64((__m128i *) &in[3*sstep_in]);            \
                                                                               \
        __m128i m10 = _mm_unpacklo_epi8(m0, m1);                               \
        __m128i m11 = _mm_unpacklo_epi8(m2, m3);                               \
                                                                               \
        m0  = _mm_unpacklo_epi16(m10, m11);                                    \
                                                                               \
        *((uint32_t *) (out+0*sstep_out)) =_mm_cvtsi128_si32(m0);              \
        *((uint32_t *) (out+1*sstep_out)) =_mm_extract_epi32(m0, 1);           \
        *((uint32_t *) (out+2*sstep_out)) =_mm_extract_epi32(m0, 2);           \
        *((uint32_t *) (out+3*sstep_out)) =_mm_extract_epi32(m0, 3);           \
    }
#define TRANSPOSE8x8_8(in, sstep_in, out, sstep_out)                           \
    {                                                                          \
        __m128i m0  = _mm_loadl_epi64((__m128i *) &in[0*sstep_in]);            \
        __m128i m1  = _mm_loadl_epi64((__m128i *) &in[1*sstep_in]);            \
        __m128i m2  = _mm_loadl_epi64((__m128i *) &in[2*sstep_in]);            \
        __m128i m3  = _mm_loadl_epi64((__m128i *) &in[3*sstep_in]);            \
        __m128i m4  = _mm_loadl_epi64((__m128i *) &in[4*sstep_in]);            \
        __m128i m5  = _mm_loadl_epi64((__m128i *) &in[5*sstep_in]);            \
        __m128i m6  = _mm_loadl_epi64((__m128i *) &in[6*sstep_in]);            \
        __m128i m7  = _mm_loadl_epi64((__m128i *) &in[7*sstep_in]);            \
                                                                               \
        __m128i m10 = _mm_unpacklo_epi8(m0, m1);                               \
        __m128i m11 = _mm_unpacklo_epi8(m2, m3);                               \
        __m128i m12 = _mm_unpacklo_epi8(m4, m5);                               \
        __m128i m13 = _mm_unpacklo_epi8(m6, m7);                               \
                                                                               \
        m0  = _mm_unpacklo_epi16(m10, m11);                                    \
        m1  = _mm_unpacklo_epi16(m12, m13);                                    \
        m2  = _mm_unpackhi_epi16(m10, m11);                                    \
        m3  = _mm_unpackhi_epi16(m12, m13);                                    \
                                                                               \
        m10 = _mm_unpacklo_epi32(m0 , m1 );                                    \
        m11 = _mm_unpacklo_epi32(m2 , m3 );                                    \
        m12 = _mm_unpackhi_epi32(m0 , m1 );                                    \
        m13 = _mm_unpackhi_epi32(m2 , m3 );                                    \
                                                                               \
        STORE8(out, sstep_out);                                                \
    }
#define TRANSPOSE16x16_8(in, sstep_in, out, sstep_out)                        \
    for (y = 0; y < sstep_in; y+=8)                                           \
        for (x = 0; x < sstep_in; x+=8)                                       \
            TRANSPOSE8x8_8((&in[y*sstep_in+x]), sstep_in, (&out[x*sstep_out+y]), sstep_out)
#define TRANSPOSE32x32_8(in, sstep_in, out, sstep_out)                        \
    for (y = 0; y < sstep_in; y+=8)                                           \
        for (x = 0; x < sstep_in; x+=8)                                       \
            TRANSPOSE8x8_8((&in[y*sstep_in+x]), sstep_in, (&out[x*sstep_out+y]), sstep_out)

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define TRANSPOSE4x4_10(in, sstep_in, out, sstep_out)                          \
    {                                                                          \
        __m128i m0  = _mm_loadl_epi64((__m128i *) &in[0*sstep_in]);            \
        __m128i m1  = _mm_loadl_epi64((__m128i *) &in[1*sstep_in]);            \
        __m128i m2  = _mm_loadl_epi64((__m128i *) &in[2*sstep_in]);            \
        __m128i m3  = _mm_loadl_epi64((__m128i *) &in[3*sstep_in]);            \
                                                                               \
        __m128i m10 = _mm_unpacklo_epi16(m0, m1);                              \
        __m128i m11 = _mm_unpacklo_epi16(m2, m3);                              \
                                                                               \
        m0  = _mm_unpacklo_epi32(m10, m11);                                    \
        m1  = _mm_unpackhi_epi32(m10, m11);                                    \
                                                                               \
        _mm_storel_epi64((__m128i *) (out+0*sstep_out) , m0);                  \
        _mm_storel_epi64((__m128i *) (out+1*sstep_out) , _mm_unpackhi_epi64(m0, m0));\
        _mm_storel_epi64((__m128i *) (out+2*sstep_out) , m1);                  \
        _mm_storel_epi64((__m128i *) (out+3*sstep_out) , _mm_unpackhi_epi64(m1, m1));\
    }
#define TRANSPOSE8x8_10(in, sstep_in, out, sstep_out)                          \
    {                                                                          \
        __m128i tmp0, tmp1, tmp2, tmp3, src0, src1, src2, src3;                \
        __m128i m0  = _mm_loadu_si128((__m128i *) &in[0*sstep_in]);            \
        __m128i m1  = _mm_loadu_si128((__m128i *) &in[1*sstep_in]);            \
        __m128i m2  = _mm_loadu_si128((__m128i *) &in[2*sstep_in]);            \
        __m128i m3  = _mm_loadu_si128((__m128i *) &in[3*sstep_in]);            \
        __m128i m4  = _mm_loadu_si128((__m128i *) &in[4*sstep_in]);            \
        __m128i m5  = _mm_loadu_si128((__m128i *) &in[5*sstep_in]);            \
        __m128i m6  = _mm_loadu_si128((__m128i *) &in[6*sstep_in]);            \
        __m128i m7  = _mm_loadu_si128((__m128i *) &in[7*sstep_in]);            \
                                                                               \
        tmp0 = _mm_unpacklo_epi16(m0, m1);                                     \
        tmp1 = _mm_unpacklo_epi16(m2, m3);                                     \
        tmp2 = _mm_unpacklo_epi16(m4, m5);                                     \
        tmp3 = _mm_unpacklo_epi16(m6, m7);                                     \
        src0 = _mm_unpacklo_epi32(tmp0, tmp1);                                 \
        src1 = _mm_unpacklo_epi32(tmp2, tmp3);                                 \
        src2 = _mm_unpackhi_epi32(tmp0, tmp1);                                 \
        src3 = _mm_unpackhi_epi32(tmp2, tmp3);                                 \
        tmp0 = _mm_unpackhi_epi16(m0, m1);                                     \
        tmp1 = _mm_unpackhi_epi16(m2, m3);                                     \
        tmp2 = _mm_unpackhi_epi16(m4, m5);                                     \
        tmp3 = _mm_unpackhi_epi16(m6, m7);                                     \
        m0   = _mm_unpacklo_epi64(src0 , src1);                                \
        m1   = _mm_unpackhi_epi64(src0 , src1);                                \
        m2   = _mm_unpacklo_epi64(src2 , src3);                                \
        m3   = _mm_unpackhi_epi64(src2 , src3);                                \
        src0 = _mm_unpacklo_epi32(tmp0, tmp1);                                 \
        src1 = _mm_unpacklo_epi32(tmp2, tmp3);                                 \
        src2 = _mm_unpackhi_epi32(tmp0, tmp1);                                 \
        src3 = _mm_unpackhi_epi32(tmp2, tmp3);                                 \
        m4   = _mm_unpacklo_epi64(src0 , src1);                                \
        m5   = _mm_unpackhi_epi64(src0 , src1);                                \
        m6   = _mm_unpacklo_epi64(src2 , src3);                                \
        m7   = _mm_unpackhi_epi64(src2 , src3);                                \
        STORE16(out, sstep_out);                                               \
    }
#define TRANSPOSE16x16_10(in, sstep_in, out, sstep_out)                        \
    for (y = 0; y < sstep_in; y+=8)                                           \
        for (x = 0; x < sstep_in; x+=8)                                       \
            TRANSPOSE8x8_10((&in[y*sstep_in+x]), sstep_in, (&out[x*sstep_out+y]), sstep_out)
#define TRANSPOSE32x32_10(in, sstep_in, out, sstep_out)                        \
    for (y = 0; y < sstep_in; y+=8)                                           \
        for (x = 0; x < sstep_in; x+=8)                                       \
            TRANSPOSE8x8_10((&in[y*sstep_in+x]), sstep_in, (&out[x*sstep_out+y]), sstep_out)


////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define ANGULAR_COMPUTE_8(W)                                                   \
    for (x = 0; x < W; x += 8) {                                               \
        r3 = _mm_set1_epi16((fact << 8) + (32 - fact));                        \
        r1 = _mm_loadu_si128((__m128i*)(&ref[x+idx+1]));                       \
        r0 = _mm_srli_si128(r1, 1);                                            \
        r1 = _mm_unpacklo_epi8(r1, r0);                                        \
        r1 = _mm_maddubs_epi16(r1, r3);                                        \
        r1 = _mm_add_epi16(r1, add);                                           \
        r1 = _mm_srai_epi16(r1, 5);                                            \
        r1 = _mm_packus_epi16(r1, r1);                                         \
        _mm_storel_epi64((__m128i *) &p_src[x], r1);                           \
    }


#define ANGULAR_COMPUTE4_8()                                                   \
    r3 = _mm_set1_epi16((fact << 8) + (32 - fact));                            \
    r1 = _mm_loadu_si128((__m128i*)(&ref[idx+1]));                             \
    r0 = _mm_srli_si128(r1, 1);                                                \
    r1 = _mm_unpacklo_epi8(r1, r0);                                            \
    r1 = _mm_maddubs_epi16(r1, r3);                                            \
    r1 = _mm_add_epi16(r1, add);                                               \
    r1 = _mm_srai_epi16(r1, 5);                                                \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((uint32_t *)p_src) = _mm_cvtsi128_si32(r1)
#define ANGULAR_COMPUTE8_8()     ANGULAR_COMPUTE_8( 8)
#define ANGULAR_COMPUTE16_8()    ANGULAR_COMPUTE_8(16)
#define ANGULAR_COMPUTE32_8()    ANGULAR_COMPUTE_8(32)

#define ANGULAR_COMPUTE_ELSE4_8()                                              \
    r1 = _mm_loadl_epi64((__m128i*) &ref[idx+1]);                              \
    *((uint32_t *)p_src) = _mm_cvtsi128_si32(r1)
#define ANGULAR_COMPUTE_ELSE8_8()                                              \
    r1 = _mm_loadl_epi64((__m128i*) &ref[idx+1]);                              \
    _mm_storel_epi64((__m128i *) p_src, r1)
#define ANGULAR_COMPUTE_ELSE16_8()                                             \
    r1 = _mm_loadu_si128((__m128i*) &ref[idx+1]);                              \
    _mm_storeu_si128((__m128i *) p_src, r1)
#define ANGULAR_COMPUTE_ELSE32_8()                                             \
    r1 = _mm_loadu_si128((__m128i*) &ref[idx+1]);                              \
    _mm_storeu_si128((__m128i *) p_src ,r1);                                   \
    r1 = _mm_loadu_si128((__m128i*) &ref[idx+17]);                             \
    _mm_storeu_si128((__m128i *)&p_src[16] ,r1)

#define CLIP_PIXEL(src1, src2)                                                 \
    r3  = _mm_loadu_si128((__m128i*)src1);                                     \
    r1  = _mm_set1_epi16(src1[-1]);                                            \
    r2  = _mm_set1_epi16(src2[0]);                                             \
    r0  = _mm_unpacklo_epi8(r3,_mm_setzero_si128());                           \
    r0  = _mm_subs_epi16(r0, r1);                                              \
    r0  = _mm_srai_epi16(r0, 1);                                               \
    r0  = _mm_add_epi16(r0, r2)
#define CLIP_PIXEL_HI()                                                        \
    r3  = _mm_unpackhi_epi8(r3,_mm_setzero_si128());                           \
    r3  = _mm_subs_epi16(r3, r1);                                              \
    r3  = _mm_srai_epi16(r3, 1);                                               \
    r3  = _mm_add_epi16(r3, r2)

#define CLIP_PIXEL1_4_8()                                                      \
    p_src = src;                                                               \
    CLIP_PIXEL(src2, src1);                                                    \
    r0  = _mm_packus_epi16(r0, r0);                                            \
    *((char *) p_src) = _mm_extract_epi8(r0, 0);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 1);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 2);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 3)
#define CLIP_PIXEL1_8_8()                                                      \
    CLIP_PIXEL1_4_8();                                                         \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 4);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 5);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 6);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 7)
#define CLIP_PIXEL1_16_8()                                                     \
    p_src = src;                                                               \
    CLIP_PIXEL(src2, src1);                                                    \
    CLIP_PIXEL_HI();                                                           \
    r0  = _mm_packus_epi16(r0, r3);                                            \
    *((char *) p_src) = _mm_extract_epi8(r0, 0);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 1);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 2);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 3);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 4);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 5);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 6);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 7);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 8);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0, 9);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0,10);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0,11);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0,12);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0,13);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0,14);                               \
    p_src += stride;                                                           \
    *((char *) p_src) = _mm_extract_epi8(r0,15)
#define CLIP_PIXEL1_32_8()

#define CLIP_PIXEL2_4_8()                                                      \
    CLIP_PIXEL(src2, src1);                                                    \
    r0  = _mm_packus_epi16(r0, r0);                                            \
    *((uint32_t *)_src) = _mm_cvtsi128_si32(r0)
#define CLIP_PIXEL2_8_8()                                                      \
    CLIP_PIXEL(src2, src1);                                                    \
    r0  = _mm_packus_epi16(r0, r0);                                            \
    _mm_storel_epi64((__m128i*)_src, r0)
#define CLIP_PIXEL2_16_8()                                                     \
    CLIP_PIXEL(src2, src1);                                                    \
    CLIP_PIXEL_HI();                                                           \
    r0  = _mm_packus_epi16(r0, r3);                                            \
    _mm_storeu_si128((__m128i*) _src , r0)
#define CLIP_PIXEL2_32_8()

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define ANGULAR_COMPUTE_10(W)                                                  \
    for (x = 0; x < W; x += 4) {                                               \
        r3 = _mm_set1_epi32((fact << 16) + (32 - fact));                       \
        r1 = _mm_loadu_si128((__m128i*)(&ref[x+idx+1]));                       \
        r0 = _mm_srli_si128(r1, 2);                                            \
        r1 = _mm_unpacklo_epi16(r1, r0);                                       \
        r1 = _mm_madd_epi16(r1, r3);                                           \
        r1 = _mm_add_epi32(r1, add);                                           \
        r1 = _mm_srai_epi32(r1, 5);                                            \
        r1 = _mm_packus_epi32(r1, r1);                                         \
        _mm_storel_epi64((__m128i *) &p_src[x], r1);                           \
    }
#define ANGULAR_COMPUTE4_10()    ANGULAR_COMPUTE_10( 4)
#define ANGULAR_COMPUTE8_10()    ANGULAR_COMPUTE_10( 8)
#define ANGULAR_COMPUTE16_10()   ANGULAR_COMPUTE_10(16)
#define ANGULAR_COMPUTE32_10()   ANGULAR_COMPUTE_10(32)

#define ANGULAR_COMPUTE_ELSE_10(W)                                             \
    for (x = 0; x < W; x += 8) {                                               \
        r1 = _mm_loadu_si128((__m128i*)(&ref[x+idx+1]));                       \
        _mm_storeu_si128((__m128i *) &p_src[x], r1);                           \
    }

#define ANGULAR_COMPUTE_ELSE4_10()                                             \
    r1 = _mm_loadl_epi64((__m128i*)(&ref[idx+1]));                             \
    _mm_storel_epi64((__m128i *) p_src, r1)

#define ANGULAR_COMPUTE_ELSE8_10()      ANGULAR_COMPUTE_ELSE_10(8)
#define ANGULAR_COMPUTE_ELSE16_10()     ANGULAR_COMPUTE_ELSE_10(16)
#define ANGULAR_COMPUTE_ELSE32_10()     ANGULAR_COMPUTE_ELSE_10(32)

#define CLIP_PIXEL_10()                                                        \
    r0  = _mm_loadu_si128((__m128i*)src2);                                     \
    r1  = _mm_set1_epi16(src2[-1]);                                            \
    r2  = _mm_set1_epi16(src1[0]);                                             \
    r0  = _mm_subs_epi16(r0, r1);                                              \
    r0  = _mm_srai_epi16(r0, 1);                                               \
    r0  = _mm_add_epi16(r0, r2)
#define CLIP_PIXEL_HI_10()                                                     \
    r3  = _mm_loadu_si128((__m128i*)&src2[8]);                                 \
    r3  = _mm_subs_epi16(r3, r1);                                              \
    r3  = _mm_srai_epi16(r3, 1);                                               \
    r3  = _mm_add_epi16(r3, r2)

#define CLIP_PIXEL1_4_10()                                                     \
    p_src = src;                                                               \
    CLIP_PIXEL_10();                                                           \
    r0  = _mm_max_epi16(r0, _mm_setzero_si128());                              \
    r0  = _mm_min_epi16(r0, _mm_set1_epi16(0x03ff));                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 0);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 1);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 2);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 3)
#define CLIP_PIXEL1_8_10()                                                     \
    CLIP_PIXEL1_4_10();                                                        \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 4);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 5);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 6);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 7)
#define CLIP_PIXEL1_16_10()                                                    \
    p_src = src;                                                               \
    CLIP_PIXEL_10();                                                           \
    CLIP_PIXEL_HI_10();                                                        \
    r0  = _mm_max_epi16(r0, _mm_setzero_si128());                              \
    r0  = _mm_min_epi16(r0, _mm_set1_epi16(0x03ff));                           \
    r3  = _mm_max_epi16(r3, _mm_setzero_si128());                              \
    r3  = _mm_min_epi16(r3, _mm_set1_epi16(0x03ff));                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 0);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 1);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 2);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 3);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 4);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 5);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 6);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r0, 7);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 0);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 1);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 2);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 3);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 4);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 5);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 6);                          \
    p_src += stride;                                                           \
    *((uint16_t *) p_src) = _mm_extract_epi16(r3, 7)
#define CLIP_PIXEL1_32_10()

#define CLIP_PIXEL2_4_10()                                                     \
    CLIP_PIXEL_10();                                                           \
    r0  = _mm_max_epi16(r0, _mm_setzero_si128());                              \
    r0  = _mm_min_epi16(r0, _mm_set1_epi16(0x03ff));                           \
    _mm_storel_epi64((__m128i*) _src    , r0)
#define CLIP_PIXEL2_8_10()                                                     \
    CLIP_PIXEL_10();                                                           \
    r0  = _mm_max_epi16(r0, _mm_setzero_si128());                              \
    r0  = _mm_min_epi16(r0, _mm_set1_epi16(0x03ff));                           \
    _mm_storeu_si128((__m128i*) _src    , r0)
#define CLIP_PIXEL2_16_10()                                                    \
    CLIP_PIXEL_10();                                                           \
    CLIP_PIXEL_HI_10();                                                        \
    r0  = _mm_max_epi16(r0, _mm_setzero_si128());                              \
    r0  = _mm_min_epi16(r0, _mm_set1_epi16(0x03ff));                           \
    r3  = _mm_max_epi16(r3, _mm_setzero_si128());                              \
    r3  = _mm_min_epi16(r3, _mm_set1_epi16(0x03ff));                           \
    _mm_storeu_si128((__m128i*) p_out    , r0);                                \
    _mm_storeu_si128((__m128i*) &p_out[8], r3);

#define CLIP_PIXEL2_32_10()

////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
#define PRED_ANGULAR_INIT_8(W)                                                 \
    const uint8_t *src1;                                                       \
    const uint8_t *src2;                                                       \
    const __m128i  add     = _mm_set1_epi16(16);                               \
    uint8_t       *ref, *p_src, *src, *p_out;                                  \
    uint8_t        src_tmp[W*W];                                               \
    uint8_t        ref_array[3 * W + 4];                                       \
    if (mode >= 18) {                                                          \
        src1   = (const uint8_t*) _top;                                        \
        src2   = (const uint8_t*) _left;                                       \
        src    = (uint8_t*) _src;                                              \
        stride = _stride;                                                      \
        p_src  = src;                                                          \
    } else {                                                                   \
        src1   = (const uint8_t*) _left;                                       \
        src2   = (const uint8_t*) _top;                                        \
        src    = &src_tmp[0];                                                  \
        stride = W;                                                            \
        p_src  = src;                                                          \
    }                                                                          \
    p_out  = (uint8_t*) _src;                                                  \
    ref = (uint8_t*) (src1 - 1)
#define PRED_ANGULAR_INIT_10(W)                                                \
    const uint16_t *src1;                                                      \
    const uint16_t *src2;                                                      \
    const __m128i  add     = _mm_set1_epi32(16);                               \
    uint16_t       *ref, *p_src, *src, *p_out;                                 \
    uint16_t        src_tmp[W*W];                                              \
    uint16_t        ref_array[3 * W + 4];                                      \
    if (mode >= 18) {                                                          \
        src1   = (const uint16_t*) _top;                                       \
        src2   = (const uint16_t*) _left;                                      \
        src    = (uint16_t*) _src;                                             \
        stride = _stride;                                                      \
        p_src  = src;                                                          \
    } else {                                                                   \
        src1   = (const uint16_t*) _left;                                      \
        src2   = (const uint16_t*) _top;                                       \
        src    = &src_tmp[0];                                                  \
        stride = W;                                                            \
        p_src  = src;                                                          \
    }                                                                          \
    p_out  = (uint16_t*) _src;                                                 \
    ref = (uint16_t*) (src1 - 1)

#define PRED_ANGULAR_STORE1_4_8()                                              \
    *((uint32_t *) ref) = *((uint32_t *) (src1-1));                            \
    ref[4] = src1[3]
#define PRED_ANGULAR_STORE1_8_8()                                              \
    r0 = _mm_loadl_epi64((__m128i*) (src1-1));                                 \
    _mm_storel_epi64((__m128i *) ref, r0);                                     \
    ref[8] = src1[7]
#define PRED_ANGULAR_STORE1_16_8()                                             \
    r0 = _mm_loadu_si128((__m128i*) (src1-1));                                 \
    _mm_storeu_si128((__m128i *) ref, r0);                                     \
    ref[16] = src1[15]
#define PRED_ANGULAR_STORE1_32_8()                                             \
    r0 = _mm_loadu_si128((__m128i*) (src1-1));                                 \
    _MM_STORE_SI128((__m128i *) ref, r0);                                      \
    r0 = _mm_loadu_si128((__m128i*) (src1+15));                                \
    _MM_STORE_SI128((__m128i *) (ref + 16), r0);                               \
    ref[32] = src1[31]

#define PRED_ANGULAR_STORE1_4_10()                                             \
    r0 = _mm_loadl_epi64((__m128i*) (&src1[-1]));                              \
    _mm_storel_epi64((__m128i *) ref, r0);                                     \
    ref[4] = src1[3]
#define PRED_ANGULAR_STORE1_8_10()                                             \
    r0 = _mm_loadu_si128((__m128i*) (&src1[-1]));                              \
    _MM_STORE_SI128((__m128i *) ref, r0);                                      \
    ref[8] = src1[7]
#define PRED_ANGULAR_STORE1_16_10()                                            \
    r0 = _mm_loadu_si128((__m128i*) (&src1[-1]));                              \
    _MM_STORE_SI128((__m128i *) ref, r0);                                      \
    r0 = _mm_loadu_si128((__m128i*) (&src1[7]));                               \
    _MM_STORE_SI128((__m128i *) (&ref[8]), r0);                                \
    ref[16] = src1[15]
#define PRED_ANGULAR_STORE1_32_10()                                            \
    r0 = _mm_loadu_si128((__m128i*) (&src1[-1]));                              \
    _MM_STORE_SI128((__m128i *) ref, r0);                                      \
    r0 = _mm_loadu_si128((__m128i*) (&src1[7]));                               \
    _MM_STORE_SI128((__m128i *) (&ref[ 8]), r0);                               \
    r0 = _mm_loadu_si128((__m128i*) (&src1[15]));                              \
    _MM_STORE_SI128((__m128i *) (&ref[16]), r0);                               \
    r0 = _mm_loadu_si128((__m128i*) (&src1[23]));                              \
    _MM_STORE_SI128((__m128i *) (&ref[24]), r0);                               \
    ref[32] = src1[31]

#define PRED_ANGULAR_WAR()                                                     \
    int y;                                                                     \
    __m128i r0, r1, r3

#define PRED_ANGULAR_WAR4_8()                                                  \
    PRED_ANGULAR_WAR();                                                        \
    __m128i r2
#define PRED_ANGULAR_WAR8_8()                                                  \
    PRED_ANGULAR_WAR4_8();                                                     \
    int x
#define PRED_ANGULAR_WAR16_8()                                                 \
    PRED_ANGULAR_WAR8_8()
#define PRED_ANGULAR_WAR32_8()                                                 \
    PRED_ANGULAR_WAR();                                                        \
    int x

#define PRED_ANGULAR_WAR4_10()    PRED_ANGULAR_WAR8_8()
#define PRED_ANGULAR_WAR8_10()    PRED_ANGULAR_WAR8_8()
#define PRED_ANGULAR_WAR16_10()   PRED_ANGULAR_WAR16_8()
#define PRED_ANGULAR_WAR32_10()   PRED_ANGULAR_WAR32_8()

#define PRED_ANGULAR(W, D)                                                     \
void pred_angular_ ## W ##_ ## D ## _sse(uint8_t *_src, const uint8_t *_top,   \
        const uint8_t *_left, ptrdiff_t _stride, int c_idx, int mode) {        \
    const int intra_pred_angle[] = {                                           \
         32, 26, 21, 17, 13,  9,  5,  2,  0, -2, -5, -9,-13,-17,-21,-26,       \
        -32,-26,-21,-17,-13, -9, -5, -2,  0,  2,  5,  9, 13, 17, 21, 26, 32    \
    };                                                                         \
    const int inv_angle[] = {                                                  \
        -4096, -1638, -910, -630, -482, -390, -315, -256, -315, -390, -482,    \
        -630, -910, -1638, -4096                                               \
    };                                                                         \
    PRED_ANGULAR_WAR ## W ## _ ## D();                                         \
    int            angle   = intra_pred_angle[mode-2];                         \
    int            angle_i = angle;                                            \
    int            last    = (W * angle) >> 5;                                 \
    int            stride;                                                     \
    PRED_ANGULAR_INIT_ ## D(W);                                                \
    if (angle < 0 && last < -1) {                                              \
        ref = ref_array + W;                                                   \
        for (y = last; y <= -1; y++)                                           \
            ref[y] = src2[-1 + ((y * inv_angle[mode-11] + 128) >> 8)];         \
         PRED_ANGULAR_STORE1_ ## W ## _ ## D();                                \
    }                                                                          \
    for (y = 0; y < W; y++) {                                                  \
        int idx  = (angle_i) >> 5;                                             \
        int fact = (angle_i) & 31;                                             \
        if (fact) {                                                            \
            ANGULAR_COMPUTE ## W ## _ ## D();                                  \
        } else {                                                               \
            ANGULAR_COMPUTE_ELSE ## W ## _ ## D();                             \
        }                                                                      \
        angle_i += angle;                                                      \
        p_src   += stride;                                                     \
    }                                                                          \
    if (mode >= 18) {                                                          \
        if (mode == 26 && c_idx == 0) {                                        \
            CLIP_PIXEL1_ ## W ## _ ## D();                                     \
        }                                                                      \
    } else {                                                                   \
        TRANSPOSE ## W ## x ## W ## _ ## D(src_tmp, W, p_out, _stride);        \
        if (mode == 10 && c_idx == 0) {                                        \
            CLIP_PIXEL2_ ## W ## _ ## D();                                     \
        }                                                                      \
    }                                                                          \
}

#if HAVE_SSE4
PRED_ANGULAR( 4, 8)
PRED_ANGULAR( 8, 8)
PRED_ANGULAR(16, 8)
PRED_ANGULAR(32, 8)

//PRED_ANGULAR( 4,10)
//PRED_ANGULAR( 8,10)
//PRED_ANGULAR(16,10)
//PRED_ANGULAR(32,10)
#endif


void pred_angular_orcc(u8 _src[4096], u8 _top[129], u8 _left[129], i32 stride, i32 idx, u8 mode, i32 log2size){
    u8 *src        = _src;
    u8 *top  = _top + 1;
    u8 *left = _left + 1;

    pred_angular[log2size - 2](src, top, left, stride, idx, mode);
}
