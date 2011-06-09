/*
 *			GPAC - Multimedia Framework C SDK
 *
 *			Copyright (c) Telecom ParisTech 2010-
 *				Author(s):	Jean Le Feuvre
 *					All rights reserved
 *
 *  This file is part of GPAC / OpenSVC Decoder module
 *
 *  GPAC is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *   
 *  GPAC is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *   
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; see the file COPYING.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA. 
 *
 */


#include <gpac/modules/codec.h>
#include <gpac/avparse.h>
#include <gpac/constants.h>

#define DllImport   __declspec( dllimport )

typedef struct{
	int Width;
	int Height;
	unsigned char* pY[1];
	unsigned char* pU[1]; 
	unsigned char* pV[1];
} RVCFRAME;


DllImport int rvc_init(char *XDF, int isAVCFile);
DllImport void rvc_decode(unsigned char* nal, int nal_length, char *outBuffer, int newNalu);
DllImport void rvc_close();


typedef struct
{
	u16 ES_ID;
	u32 width, stride, height, out_size, pixel_ar, layer;
	Bool first_frame;

	u32 nalu_size_length;

	void *codec;
} RVCDec;


static GF_Err RVCD_AttachStream(GF_BaseDecoder *ifcg, GF_ESD *esd)
{
	u32 i, count;
	s32 res;
	char Picture;
	RVCDec *ctx = (RVCDec*) ifcg->privateStack;
	int isAVCFile;

	/*not supported in this version*/
	if (esd->dependsOnESID) return GF_NOT_SUPPORTED;
	
	ctx->ES_ID = esd->ESID;
	ctx->width = ctx->height = ctx->out_size = 0;

	/*initialize RVC*/
	if (!esd->decoderConfig->rvc_config) return GF_NOT_SUPPORTED;

	if(esd->decoderConfig->objectTypeIndication==GPAC_OTI_VIDEO_AVC) isAVCFile = 1;
	else isAVCFile = 0;

	rvc_init(esd->decoderConfig->rvc_config->data, isAVCFile); //->data contains the uncompressed XDF

		
	/*decoder config not known, output buffers will be reconfigured at run-time*/
	if (!esd->decoderConfig->decoderSpecificInfo || !esd->decoderConfig->decoderSpecificInfo->data) 
		return GF_OK;

	/*initialize the decoder */
	if (esd->decoderConfig->objectTypeIndication==GPAC_OTI_VIDEO_AVC) {
		GF_AVCConfig *cfg = gf_odf_avc_cfg_read(esd->decoderConfig->decoderSpecificInfo->data, esd->decoderConfig->decoderSpecificInfo->dataLength);
		if (!cfg) return GF_NON_COMPLIANT_BITSTREAM;
		ctx->nalu_size_length = cfg->nal_unit_size;
		
		/*decode all NALUs*/
		count = gf_list_count(cfg->sequenceParameterSets);
		for (i=0; i<count; i++) {
			u32 w, h, par_n, par_d;
			GF_AVCConfigSlot *slc = gf_list_get(cfg->sequenceParameterSets, i);

			gf_avc_get_sps_info(slc->data, slc->size, &slc->id, &w, &h, &par_n, &par_d);
			/*by default use the base layer*/
			if (!i) {
				if ((ctx->width<w) || (ctx->height<h)) {
					ctx->width = w;
					ctx->height = h;
					if ( ((s32)par_n>0) && ((s32)par_d>0) )
						ctx->pixel_ar = (par_n<<16) || par_d;
				}
			}
			/* call decode - warning for AVC: the data blocks do not contain startcode prefixes (00000001), you may need to add them) */
			
			rvc_decode(slc->data, slc->size, &Picture, 1);
			/*if (res<0) {
				GF_LOG(GF_LOG_ERROR, GF_LOG_CODEC, ("[SVC Decoder] Error decoding SPS %d\n", res));
			}*/
			
		}

		count = gf_list_count(cfg->pictureParameterSets);
		for (i=0; i<count; i++) {
			GF_AVCConfigSlot *slc = gf_list_get(cfg->pictureParameterSets, i);
			/*same remark as above*/

			
			rvc_decode(slc->data, slc->size, &Picture, 1);
			/*if (res<0) {
				GF_LOG(GF_LOG_ERROR, GF_LOG_CODEC, ("[SVC Decoder] Error decoding PPS %d\n", res));
			}*/
			
		}

		gf_odf_avc_cfg_del(cfg);
	} else if (esd->decoderConfig->objectTypeIndication==GPAC_OTI_VIDEO_MPEG4_PART2) {
		GF_M4VDecSpecInfo dsi;
		GF_Err e;
		/*decode DSI*/
		e = gf_m4v_get_config(esd->decoderConfig->decoderSpecificInfo->data, esd->decoderConfig->decoderSpecificInfo->dataLength, &dsi);
		if (e) return e;
		if (!dsi.width || !dsi.height) return GF_NON_COMPLIANT_BITSTREAM;
		ctx->width = dsi.width;
		ctx->height = dsi.height;
		ctx->pixel_ar = (dsi.par_num<<16) | dsi.par_den;
		
		
		rvc_decode(esd->decoderConfig->decoderSpecificInfo->data, esd->decoderConfig->decoderSpecificInfo->dataLength, &Picture, 1);
		/*if (res<0) {
			GF_LOG(GF_LOG_ERROR, GF_LOG_CODEC, ("[SVC Decoder] Error decoding PPS %d\n", res));
		}*/
		

	} else {
		/*unknown type, do what you want*/
		
		rvc_decode(esd->decoderConfig->decoderSpecificInfo->data, esd->decoderConfig->decoderSpecificInfo->dataLength, &Picture, 1);
		/*if (res<0) {
			GF_LOG(GF_LOG_ERROR, GF_LOG_CODEC, ("[SVC Decoder] Error decoding PPS %d\n", res));
		}*/
		
	}
	/*adjust stride to what you decoder uses*/
	ctx->stride = ctx->width;
	/*precompute output buffer size*/
	ctx->out_size = ctx->stride * ctx->height * 3 / 2;
	return GF_OK;
}
static GF_Err RVCD_DetachStream(GF_BaseDecoder *ifcg, u16 ES_ID)
{
	RVCDec *ctx = (RVCDec*) ifcg->privateStack;

	//close RVC decoder
	rvc_close();
	ctx->codec = NULL;
	ctx->width = ctx->height = ctx->out_size = 0;
	return GF_OK;
}
static GF_Err RVCD_GetCapabilities(GF_BaseDecoder *ifcg, GF_CodecCapability *capability)
{
	RVCDec *ctx = (RVCDec*) ifcg->privateStack;

	switch (capability->CapCode) {
	case GF_CODEC_RESILIENT:
		capability->cap.valueInt = 1;
		break;
	case GF_CODEC_WIDTH:
		capability->cap.valueInt = ctx->width;
		break;
	case GF_CODEC_HEIGHT:
		capability->cap.valueInt = ctx->height;
		break;
	case GF_CODEC_STRIDE:
		capability->cap.valueInt = ctx->stride;
		break;
	case GF_CODEC_PAR:
		capability->cap.valueInt = ctx->pixel_ar;
		break;
	case GF_CODEC_OUTPUT_SIZE:
		capability->cap.valueInt = ctx->out_size;
		break;
	case GF_CODEC_PIXEL_FORMAT:
		capability->cap.valueInt = GF_PIXEL_YV12;
		break;
	case GF_CODEC_BUFFER_MIN:
		capability->cap.valueInt = 1;
		break;
	case GF_CODEC_BUFFER_MAX:
		capability->cap.valueInt = 4;
		break;
	case GF_CODEC_PADDING_BYTES:
		capability->cap.valueInt = 32;
		break;
	case GF_CODEC_REORDER:
		capability->cap.valueInt = 1;
		break;
	/*not known at our level...*/
	case GF_CODEC_CU_DURATION:
	default:
		capability->cap.valueInt = 0;
		break;
	}
	return GF_OK;
}
static GF_Err RVCD_SetCapabilities(GF_BaseDecoder *ifcg, GF_CodecCapability capability)
{
	return GF_NOT_SUPPORTED;
}

static GF_Err RVCD_ProcessData(GF_MediaDecoder *ifcg, 
		char *inBuffer, u32 inBufferLength,
		u16 ES_ID,
		char *outBuffer, u32 *outBufferLength,
		u8 PaddingBits, u32 mmlevel)
{
	RVCDec *ctx = (RVCDec*) ifcg->privateStack;

	if (!ES_ID || (ES_ID!=ctx->ES_ID) /*|| !ctx->codec*/) {
		*outBufferLength = 0;
		return GF_OK;
	}
	if (*outBufferLength < ctx->out_size) {
		*outBufferLength = ctx->out_size;
		return GF_BUFFER_TOO_SMALL;
	}

	//if your decoder outputs directly in the memory passed, setup pointers for your decoder output picture

	
	if (ctx->nalu_size_length) {
		u32 i, nalu_size = 0;
		u8 *ptr = inBuffer;
		int newNalu = 1;
		
		while (inBufferLength) {
			for (i=0; i<ctx->nalu_size_length; i++) {
				nalu_size = (nalu_size<<8) + ptr[i];
			}
			ptr += ctx->nalu_size_length;

			//same remark as above regardin start codes
			
			rvc_decode(ptr, nalu_size, outBuffer, newNalu);

			newNalu = 0;
			
			ptr += nalu_size;
			if (inBufferLength < nalu_size + ctx->nalu_size_length) 
				break;

			inBufferLength -= nalu_size + ctx->nalu_size_length;
		}
	} else {
		u32 i, nalu_size = 0;
		u8 *ptr = inBuffer;
		

		rvc_decode(ptr, inBufferLength, outBuffer, 1);
	}

	//if (got_pic!=1) return GF_OK;

	/*if size changed during the decoding, resize the composition buffer*/
	/*if ((pic.Width != ctx->width) || (pic.Height!=ctx->height)) 
	{
		ctx->width = pic.Width;
		ctx->stride = pic.Width;
		ctx->height = pic.Height;
		ctx->out_size = ctx->stride * ctx->height * 3 / 2;
		*outBufferLength = ctx->out_size;
		return GF_BUFFER_TOO_SMALL;
	}
	
	*outBufferLength = ctx->out_size;*/

	/*if your decoder does not output directly in the memory passed, copy over the data*/
	
	/*memcpy(outBuffer, pic.pY[0], ctx->stride*ctx->height); 
	memcpy(outBuffer + ctx->stride * ctx->height, pic.pU[0], ctx->stride*ctx->height/4);
	memcpy(outBuffer + 5*ctx->stride * ctx->height/4, pic.pV[0], ctx->stride*ctx->height/4);*/

	return GF_OK;
}

static Bool RVCD_CanHandleStream(GF_BaseDecoder *dec, u32 StreamType, GF_ESD *esd, u8 PL)
{
	if (StreamType != GF_STREAM_VISUAL) return 0;
	/*media type query*/
	if (!esd) return 1;
	switch (esd->decoderConfig->objectTypeIndication) {
	case GPAC_OTI_VIDEO_AVC:
	case GPAC_OTI_VIDEO_MPEG4_PART2:
		if (!esd->decoderConfig->rvc_config) return 0;
		return 1;
	}
	return 0;
}

static const char *RVCD_GetCodecName(GF_BaseDecoder *dec)
{
	return "RVC Decoder";
}

GF_BaseDecoder *NewRVCDec()
{
	GF_MediaDecoder *ifcd;
	RVCDec *dec;
	
	GF_SAFEALLOC(ifcd, GF_MediaDecoder);
	GF_SAFEALLOC(dec, RVCDec);
	GF_REGISTER_MODULE_INTERFACE(ifcd, GF_MEDIA_DECODER_INTERFACE, "RVC Decoder", "gpac distribution")

	ifcd->privateStack = dec;

	/*setup our own interface*/	
	ifcd->AttachStream = RVCD_AttachStream;
	ifcd->DetachStream = RVCD_DetachStream;
	ifcd->GetCapabilities = RVCD_GetCapabilities;
	ifcd->SetCapabilities = RVCD_SetCapabilities;
	ifcd->GetName = RVCD_GetCodecName;
	ifcd->CanHandleStream = RVCD_CanHandleStream;
	ifcd->ProcessData = RVCD_ProcessData;
	return (GF_BaseDecoder *) ifcd;
}

void DeleteRVCDec(GF_BaseDecoder *ifcg)
{
	RVCDec *ctx = (RVCDec*) ifcg->privateStack;
	gf_free(ctx);
	gf_free(ifcg);
}

const u32 *QueryInterfaces() 
{
	static u32 si [] = {
#ifndef GPAC_DISABLE_AV_PARSERS
		GF_MEDIA_DECODER_INTERFACE,
#endif
		0
	};
	return si; 
}

GF_BaseInterface *LoadInterface(u32 InterfaceType) 
{
#ifndef GPAC_DISABLE_AV_PARSERS
	if (InterfaceType == GF_MEDIA_DECODER_INTERFACE) return (GF_BaseInterface *)NewRVCDec();
#endif
	return NULL;
}

void ShutdownInterface(GF_BaseInterface *ifce)
{
	switch (ifce->InterfaceType) {
#ifndef GPAC_DISABLE_AV_PARSERS
	case GF_MEDIA_DECODER_INTERFACE: 
		DeleteRVCDec((GF_BaseDecoder*)ifce);
		break;
#endif
	}
}
