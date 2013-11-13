#include "open_hevc.h"

int openHEVCIsPresent(){
#ifdef OPEN_HEVC_ENABLE
    return 1;
#else
    return 0;
#endif
}
#define av_clip_pixel(a) av_clip_uint8(a)false

static av_clip_uint8(int a)
{
    if (a&(~0xFF)) return (-a)>>31;
    else           return a;
}

void put_hevc_qpel_pixel_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
u8 width, u8 height)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[0][3+3*srcstride];
    i16 *dst = _dst[listIdx][0];

    ff_hevc_put_hevc_qpel_pixels_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
#endif
}

void put_hevc_epel_pixel_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
u8 width, u8 height, i32 component)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[component][1+1*srcstride];
    i16 *dst = _dst[listIdx][component];
    ff_hevc_put_hevc_epel_pixels_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
#endif
}

void put_hevc_qpel_h_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
i32 filterIdx,  u8 width, u8 height)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[0][3+3*srcstride];
    i16 *dst = _dst[listIdx][0];

    if(filterIdx == 1) {
        ff_hevc_put_hevc_qpel_h_1_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
    }else if(filterIdx == 2){
        ff_hevc_put_hevc_qpel_h_2_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
    }else{
        ff_hevc_put_hevc_qpel_h_3_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
    }
#endif
}

void put_hevc_qpel_v_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
i32 filterIdx,  u8 width, u8 height)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[0][3+3*srcstride];
    i16 *dst = _dst[listIdx][0];

    if(filterIdx == 1) {
        ff_hevc_put_hevc_qpel_v_1_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
    }else if(filterIdx == 2){
        ff_hevc_put_hevc_qpel_v_2_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
    }else{
        ff_hevc_put_hevc_qpel_v_3_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0);
    }
#endif
}

void put_hevc_epel_h_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
i32 filterIdx,  u8 width, u8 height, i32 component)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[component][1+1*srcstride];
    i16 *dst = _dst[listIdx][component];

    ff_hevc_put_hevc_epel_h_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, filterIdx, 0, 0);
#endif
}

void put_hevc_epel_v_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
i32 filterIdx,  u8 width, u8 height, i32 component)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[component][1+1*srcstride];
    i16 *dst = _dst[listIdx][component];

    ff_hevc_put_hevc_epel_v_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, 0, filterIdx, 0);
#endif
}

void put_hevc_qpel_hv_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
i32 filterIdx[2],  u8 width, u8 height)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[0][3+ 3*srcstride];
    i16 *dst = _dst[listIdx][0];
    DECLARE_ALIGNED(16, i16, mc_buffer[(64 + 7) * 64]);

    if(filterIdx[0] == 1) {
        if(filterIdx[1] == 1){
            ff_hevc_put_hevc_qpel_h_1_v_1_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }else if(filterIdx[1] == 2){
            ff_hevc_put_hevc_qpel_h_1_v_2_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }else{
            ff_hevc_put_hevc_qpel_h_1_v_3_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }
    }else if(filterIdx[0] == 2){
        if(filterIdx[1] == 1){
            ff_hevc_put_hevc_qpel_h_2_v_1_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }else if(filterIdx[1] == 2){
            ff_hevc_put_hevc_qpel_h_2_v_2_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }else{
            ff_hevc_put_hevc_qpel_h_2_v_3_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }
    }else{
        if(filterIdx[1] == 1){
            ff_hevc_put_hevc_qpel_h_3_v_1_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }else if(filterIdx[1] == 2){
            ff_hevc_put_hevc_qpel_h_3_v_2_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }else{
            ff_hevc_put_hevc_qpel_h_3_v_3_sse(dst, width + 1, src, srcstride, width + 1, height + 1, &mc_buffer);
        }
    }
#endif
}

void put_hevc_epel_hv_orcc(i16 _dst[2][3][64*64], u8 listIdx,
u8 _src[3][71*71], u8 srcstride,
i32 filterIdx[2],  u8 width, u8 height, i32 component)
{
#ifdef OPEN_HEVC_ENABLE
    u8  *src = &_src[component][1+1*srcstride];
    i16 *dst = _dst[listIdx][component];
    DECLARE_ALIGNED(16, i16, mc_buffer[(64 + 7) * 64]);

    ff_hevc_put_hevc_epel_hv_8_sse(dst, width + 1, src, srcstride, width + 1, height + 1, filterIdx[0], filterIdx[1], &mc_buffer);
#endif
}

void put_unweighted_pred_orcc(u8 _dst[2][3][64*64], i16 _src[2][3][64*64], u8 width, u8 height, u8 rdList, i32 component)
{
#ifdef OPEN_HEVC_ENABLE
    i16 *src = &_src[0][component];
    u8 *dst = _dst[rdList][component];

    ff_hevc_put_unweighted_pred_8_sse(dst, width + 1, src, width + 1, width + 1, height + 1);
#endif
}


void put_unweighted_pred_avg_orcc(u8 _dst[2][3][64*64], i16 _src[2][3][64*64], u8 width, u8 height, i32 component)
{
#ifdef OPEN_HEVC_ENABLE
    i16 *src1 = &_src[0][component][0];
    i16 *src2 = &_src[1][component][0];
    u8 *dst = _dst[0][component][0];

    ff_hevc_put_weighted_pred_avg_8_sse(dst, width + 1, src1, src2, width + 1, width + 1, height + 1);
#endif
}
