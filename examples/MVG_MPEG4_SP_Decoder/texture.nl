network texture (MAXW_IN_MB, MB_COORD_SZ, BTYPE_SZ, SAMPLE_SZ, NEWVOP, INTRA, INTER, QUANT_MASK, ACPRED, ACCODED, QUANT_SZ)
BTYPE, QFS ==> f :

entities

    DCsplit = DCsplit(
        SAMPLE_SZ = SAMPLE_SZ
    );

    DCRecontruction = DC_Reconstruction(
        ACCODED = ACCODED,
        ACPRED = ACPRED,
        BTYPE_SZ = BTYPE_SZ,
        DCVAL = 128*8,
        INTER = INTER,
        INTRA = INTRA,
        MAXW_IN_MB = MAXW_IN_MB,
        MB_COORD_SZ = MB_COORD_SZ,
        NEWVOP = NEWVOP,
        QUANT_MASK = QUANT_MASK,
        QUANT_SZ = QUANT_SZ,
        SAMPLE_SZ = SAMPLE_SZ
    );

    IS = MPEG4_algo_IS(
        SAMPLE_SZ = SAMPLE_SZ
    );

    IAP = MPEG4_algo_IAP(
        MAXW_IN_MB = MAXW_IN_MB,
        MB_COORD_SZ = MB_COORD_SZ,
        SAMPLE_SZ = SAMPLE_SZ
    );

    IQ = MPEG4_algo_Inversequant(
        QUANT_SZ = QUANT_SZ,
        SAMPLE_SZ = SAMPLE_SZ
    );

    idct2d = idct2d(
    );

structure

    BTYPE --> DCRecontruction.BTYPE;
    QFS --> DCsplit.IN;
    DCsplit.DC --> DCRecontruction.QFS_DC;
    IAP.QF_AC --> IQ.AC;
    IQ.OUT --> idct2d.\in\;
    idct2d.out --> f;
    DCRecontruction.SIGNED --> idct2d.signed;
    DCRecontruction.QUANT --> IQ.QP;
    DCRecontruction.QF_DC --> IQ.DC;
    DCRecontruction.PTR --> IAP.PTR;
    DCRecontruction.AC_PRED_DIR --> IAP.AC_PRED_DIR;

    DCRecontruction.AC_PRED_DIR --> IS.AC_PRED_DIR;
    DCsplit.AC --> IS.QFS_AC;
    IS.PQF_AC --> IAP.PQF_AC;
end
