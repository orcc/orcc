%struct.FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }
%struct.stat = type { i32, i16, i16, i16, i16, i16, i32, i32, i32, i32, i32 }

declare void @llvm.memcpy.i32(i8* nocapture, i8* nocapture, i32, i32) nounwind

@FrameCounter = global i32 0             ; <i32*> [#uses=4]
@ARG_INPUTFILE = internal global i8* null         ; <i8**> [#uses=0]
@images = global i32 0                   ; <i32*> [#uses=4]
@.str = private constant [3 x i8] c"rb\00", align 1 ; <[3 x i8]*> [#uses=1]
@ptfile = global %struct.FILE* null      ; <%struct.FILE**> [#uses=7]
@.str1 = private constant [63 x i8] c"Cannot open yuv_file concatenated input file '%s' for reading\0A\00", align 4 ; <[63 x i8]*> [#uses=1]
@xsize_int = global i32 0                ; <i32*> [#uses=5]
@.str2 = private constant [18 x i8] c"xsize %d invalid\0A\00", align 1 ; <[18 x i8]*> [#uses=1]
@ysize_int = global i32 0                ; <i32*> [#uses=5]
@.str3 = private constant [18 x i8] c"ysize %d invalid\0A\00", align 1 ; <[18 x i8]*> [#uses=1]
@NumberOfFrames = global i32 0           ; <i32*> [#uses=3]
@.str4 = private constant [76 x i8] c"error %d instead of %d at position : i = %d, j = %d, mb_x = %d, mb_y = %d \0A\00", align 4 ; <[76 x i8]*> [#uses=1]
@.str5 = private constant [24 x i8] c"error %d !!!!!!!!!!!!!\0A\00", align 1 ; <[24 x i8]*> [#uses=1]
@m_y = global i32 0                      ; <i32*> [#uses=6]
@m_width = global i32 0                  ; <i32*> [#uses=10]
@m_x = global i32 0                      ; <i32*> [#uses=7]
@img_buf_y = global [405504 x i8] zeroinitializer, align 32 ; <[405504 x i8]*> [#uses=2]
@img_buf_u = global [101376 x i8] zeroinitializer, align 32 ; <[101376 x i8]*> [#uses=2]
@img_buf_v = global [101376 x i8] zeroinitializer, align 32 ; <[101376 x i8]*> [#uses=2]
@m_height = global i32 0                 ; <i32*> [#uses=5]
@.str6 = private constant [18 x i8] c"Frame number %d \0A\00", align 1 ; <[18 x i8]*> [#uses=1]
@Y = internal global [405504 x i8] zeroinitializer, align 32 ; <[405504 x i8]*> [#uses=1]
@U = internal global [101376 x i8] zeroinitializer, align 32 ; <[101376 x i8]*> [#uses=1]
@V = internal global [101376 x i8] zeroinitializer, align 32 ; <[101376 x i8]*> [#uses=1]
@yuv_file = global i8* null                ; <i8**> [#uses=1]
@init = global i32 1                     ; <i32*> [#uses=3]
@B = external global %struct.fifo_i8_s*           ; <%struct.fifo_i8_s**> [#uses=4]
@WIDTH = external global %struct.fifo_i16_s*      ; <%struct.fifo_i16_s**> [#uses=3]
@HEIGHT = external global %struct.fifo_i16_s*     ; <%struct.fifo_i16_s**> [#uses=3]

define void @Read_YUV_init(i32 %xsize, i32 %ysize, i8* %filename) nounwind {
entry:
  %xsize_addr = alloca i32                        ; <i32*> [#uses=5]
  %ysize_addr = alloca i32                        ; <i32*> [#uses=5]
  %filename_addr = alloca i8*                     ; <i8**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 %xsize, i32* %xsize_addr
  store i32 %ysize, i32* %ysize_addr
  store i8* %filename, i8** %filename_addr
  %0 = load i8** %filename_addr, align 4          ; <i8*> [#uses=1]
  %1 = call %struct.FILE* @fopen(i8* %0, i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0)) nounwind ; <%struct.FILE*> [#uses=1]
  store %struct.FILE* %1, %struct.FILE** @ptfile, align 4
  %2 = load %struct.FILE** @ptfile, align 4       ; <%struct.FILE*> [#uses=1]
  %3 = icmp eq %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb1

bb:                                               ; preds = %entry
  %4 = load i8** %filename_addr, align 4          ; <i8*> [#uses=1]
  %5 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([63 x i8]* @.str1, i32 0, i32 0), i8* %4) nounwind ; <i32> [#uses=0]
  call void @exit(i32 -1) noreturn nounwind
  unreachable

bb1:                                              ; preds = %entry
  %6 = load i32* %xsize_addr, align 4             ; <i32> [#uses=1]
  store i32 %6, i32* @xsize_int, align 4
  %7 = load i32* @xsize_int, align 4              ; <i32> [#uses=1]
  %8 = icmp eq i32 %7, 0                          ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %9 = load i32* %xsize_addr, align 4             ; <i32> [#uses=1]
  %10 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([18 x i8]* @.str2, i32 0, i32 0), i32 %9) nounwind ; <i32> [#uses=0]
  call void @exit(i32 -2) noreturn nounwind
  unreachable

bb3:                                              ; preds = %bb1
  %11 = load i32* %ysize_addr, align 4            ; <i32> [#uses=1]
  store i32 %11, i32* @ysize_int, align 4
  %12 = load i32* @ysize_int, align 4             ; <i32> [#uses=1]
  %13 = icmp eq i32 %12, 0                        ; <i1> [#uses=1]
  br i1 %13, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %14 = load i32* %ysize_addr, align 4            ; <i32> [#uses=1]
  %15 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([18 x i8]* @.str3, i32 0, i32 0), i32 %14) nounwind ; <i32> [#uses=0]
  call void @exit(i32 -3) noreturn nounwind
  unreachable

bb5:                                              ; preds = %bb3
  %16 = load %struct.FILE** @ptfile, align 4      ; <%struct.FILE*> [#uses=1]
  %17 = call i32 @Filesize() nounwind ; <i32> [#uses=1]
  %18 = load i32* %xsize_addr, align 4            ; <i32> [#uses=1]
  %19 = load i32* %ysize_addr, align 4            ; <i32> [#uses=1]
  %20 = mul nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load i32* %xsize_addr, align 4            ; <i32> [#uses=1]
  %22 = load i32* %ysize_addr, align 4            ; <i32> [#uses=1]
  %23 = mul nsw i32 %21, %22                      ; <i32> [#uses=1]
  %24 = sdiv i32 %23, 2                           ; <i32> [#uses=1]
  %25 = add nsw i32 %20, %24                      ; <i32> [#uses=1]
  %26 = sdiv i32 %17, %25                         ; <i32> [#uses=1]
  store i32 %26, i32* @NumberOfFrames, align 4
  br label %return

return:                                           ; preds = %bb5
  ret void
}

declare %struct.FILE* @fopen(i8*, i8*) nounwind

declare i32 @printf(i8*, ...) nounwind

declare void @exit(i32) noreturn nounwind

declare i32 @Filesize()

define void @Read_YUV(i8* %Y, i8* %U, i8* %V) nounwind {
entry:
  %Y_addr = alloca i8*                            ; <i8**> [#uses=2]
  %U_addr = alloca i8*                            ; <i8**> [#uses=2]
  %V_addr = alloca i8*                            ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i8* %Y, i8** %Y_addr
  store i8* %U, i8** %U_addr
  store i8* %V, i8** %V_addr
  %0 = load %struct.FILE** @ptfile, align 4       ; <%struct.FILE*> [#uses=1]
  %1 = load i32* @xsize_int, align 4              ; <i32> [#uses=1]
  %2 = load i32* @ysize_int, align 4              ; <i32> [#uses=1]
  %3 = mul nsw i32 %1, %2                         ; <i32> [#uses=1]
  %4 = load i8** %Y_addr, align 4                 ; <i8*> [#uses=1]
  %5 = call i32 @fread(i8* %4, i32 1, i32 %3, %struct.FILE* %0) nounwind ; <i32> [#uses=0]
  %6 = load %struct.FILE** @ptfile, align 4       ; <%struct.FILE*> [#uses=1]
  %7 = load i32* @xsize_int, align 4              ; <i32> [#uses=1]
  %8 = load i32* @ysize_int, align 4              ; <i32> [#uses=1]
  %9 = mul nsw i32 %7, %8                         ; <i32> [#uses=1]
  %10 = sdiv i32 %9, 4                            ; <i32> [#uses=1]
  %11 = load i8** %U_addr, align 4                ; <i8*> [#uses=1]
  %12 = call i32 @fread(i8* %11, i32 1, i32 %10, %struct.FILE* %6) nounwind ; <i32> [#uses=0]
  %13 = load %struct.FILE** @ptfile, align 4      ; <%struct.FILE*> [#uses=1]
  %14 = load i32* @xsize_int, align 4             ; <i32> [#uses=1]
  %15 = load i32* @ysize_int, align 4             ; <i32> [#uses=1]
  %16 = mul nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = sdiv i32 %16, 4                           ; <i32> [#uses=1]
  %18 = load i8** %V_addr, align 4                ; <i8*> [#uses=1]
  %19 = call i32 @fread(i8* %18, i32 1, i32 %17, %struct.FILE* %13) nounwind ; <i32> [#uses=0]
  %20 = load i32* @images, align 4                ; <i32> [#uses=1]
  %21 = add nsw i32 %20, 1                        ; <i32> [#uses=1]
  store i32 %21, i32* @images, align 4
  %22 = load i32* @images, align 4                ; <i32> [#uses=1]
  %23 = load i32* @NumberOfFrames, align 4        ; <i32> [#uses=1]
  %24 = icmp eq i32 %22, %23                      ; <i1> [#uses=1]
  br i1 %24, label %bb, label %bb1

bb:                                               ; preds = %entry
  %25 = load %struct.FILE** @ptfile, align 4      ; <%struct.FILE*> [#uses=1]
  %26 = call i32 @fseek(%struct.FILE* %25, i32 0, i32 0) nounwind ; <i32> [#uses=0]
  store i32 0, i32* @images, align 4
  br label %bb1

bb1:                                              ; preds = %bb, %entry
  br label %return

return:                                           ; preds = %bb1
  ret void
}

declare i32 @fread(i8*, i32, i32, %struct.FILE*) nounwind

declare i32 @fseek(%struct.FILE*, i32, i32) nounwind

define void @DiffUcharImage(i32 %x_size, i32 %y_size, i8* %img1_uchar, i8* %img2_uchar) nounwind {
entry:
  %x_size_addr = alloca i32                       ; <i32*> [#uses=6]
  %y_size_addr = alloca i32                       ; <i32*> [#uses=2]
  %img1_uchar_addr = alloca i8*                   ; <i8**> [#uses=3]
  %img2_uchar_addr = alloca i8*                   ; <i8**> [#uses=3]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %j = alloca i32                                 ; <i32*> [#uses=10]
  %error = alloca i32                             ; <i32*> [#uses=6]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 %x_size, i32* %x_size_addr
  store i32 %y_size, i32* %y_size_addr
  store i8* %img1_uchar, i8** %img1_uchar_addr
  store i8* %img2_uchar, i8** %img2_uchar_addr
  store i32 0, i32* %error, align 4
  store i32 0, i32* %j, align 4
  br label %bb7

bb:                                               ; preds = %bb7
  store i32 0, i32* %i, align 4
  br label %bb5

bb1:                                              ; preds = %bb5
  %0 = load i32* %j, align 4                      ; <i32> [#uses=1]
  %1 = load i32* %x_size_addr, align 4            ; <i32> [#uses=1]
  %2 = mul nsw i32 %0, %1                         ; <i32> [#uses=1]
  %3 = load i32* %i, align 4                      ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load i8** %img1_uchar_addr, align 4        ; <i8*> [#uses=1]
  %6 = getelementptr inbounds i8* %5, i32 %4      ; <i8*> [#uses=1]
  %7 = load i8* %6, align 1                       ; <i8> [#uses=1]
  %8 = load i32* %j, align 4                      ; <i32> [#uses=1]
  %9 = load i32* %x_size_addr, align 4            ; <i32> [#uses=1]
  %10 = mul nsw i32 %8, %9                        ; <i32> [#uses=1]
  %11 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %12 = add nsw i32 %10, %11                      ; <i32> [#uses=1]
  %13 = load i8** %img2_uchar_addr, align 4       ; <i8*> [#uses=1]
  %14 = getelementptr inbounds i8* %13, i32 %12   ; <i8*> [#uses=1]
  %15 = load i8* %14, align 1                     ; <i8> [#uses=1]
  %16 = icmp ne i8 %7, %15                        ; <i1> [#uses=1]
  br i1 %16, label %bb2, label %bb4

bb2:                                              ; preds = %bb1
  %17 = load i32* %error, align 4                 ; <i32> [#uses=1]
  %18 = add nsw i32 %17, 1                        ; <i32> [#uses=1]
  store i32 %18, i32* %error, align 4
  %19 = load i32* %error, align 4                 ; <i32> [#uses=1]
  %20 = icmp sle i32 %19, 99                      ; <i1> [#uses=1]
  br i1 %20, label %bb3, label %bb4

bb3:                                              ; preds = %bb2
  %21 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %22 = sdiv i32 %21, 16                          ; <i32> [#uses=1]
  %23 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %24 = sdiv i32 %23, 16                          ; <i32> [#uses=1]
  %25 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %26 = load i32* %x_size_addr, align 4           ; <i32> [#uses=1]
  %27 = mul nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %29 = add nsw i32 %27, %28                      ; <i32> [#uses=1]
  %30 = load i8** %img2_uchar_addr, align 4       ; <i8*> [#uses=1]
  %31 = getelementptr inbounds i8* %30, i32 %29   ; <i8*> [#uses=1]
  %32 = load i8* %31, align 1                     ; <i8> [#uses=1]
  %33 = zext i8 %32 to i32                        ; <i32> [#uses=1]
  %34 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %35 = load i32* %x_size_addr, align 4           ; <i32> [#uses=1]
  %36 = mul nsw i32 %34, %35                      ; <i32> [#uses=1]
  %37 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load i8** %img1_uchar_addr, align 4       ; <i8*> [#uses=1]
  %40 = getelementptr inbounds i8* %39, i32 %38   ; <i8*> [#uses=1]
  %41 = load i8* %40, align 1                     ; <i8> [#uses=1]
  %42 = zext i8 %41 to i32                        ; <i32> [#uses=1]
  %43 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %44 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %45 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([76 x i8]* @.str4, i32 0, i32 0), i32 %42, i32 %33, i32 %43, i32 %44, i32 %24, i32 %22) nounwind ; <i32> [#uses=0]
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb1
  %46 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %47 = add nsw i32 %46, 1                        ; <i32> [#uses=1]
  store i32 %47, i32* %i, align 4
  br label %bb5

bb5:                                              ; preds = %bb4, %bb
  %48 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %49 = load i32* %x_size_addr, align 4           ; <i32> [#uses=1]
  %50 = icmp slt i32 %48, %49                     ; <i1> [#uses=1]
  br i1 %50, label %bb1, label %bb6

bb6:                                              ; preds = %bb5
  %51 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %52 = add nsw i32 %51, 1                        ; <i32> [#uses=1]
  store i32 %52, i32* %j, align 4
  br label %bb7

bb7:                                              ; preds = %bb6, %entry
  %53 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %54 = load i32* %y_size_addr, align 4           ; <i32> [#uses=1]
  %55 = icmp slt i32 %53, %54                     ; <i1> [#uses=1]
  br i1 %55, label %bb, label %bb8

bb8:                                              ; preds = %bb7
  %56 = load i32* %error, align 4                 ; <i32> [#uses=1]
  %57 = icmp ne i32 %56, 0                        ; <i1> [#uses=1]
  br i1 %57, label %bb9, label %bb10

bb9:                                              ; preds = %bb8
  %58 = load i32* %error, align 4                 ; <i32> [#uses=1]
  %59 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([24 x i8]* @.str5, i32 0, i32 0), i32 %58) nounwind ; <i32> [#uses=0]
  br label %bb10

bb10:                                             ; preds = %bb9, %bb8
  br label %return

return:                                           ; preds = %bb10
  ret void
}

define void @write_mb(i8* %tokens) nounwind {
entry:
  %tokens_addr = alloca i8*                       ; <i8**> [#uses=4]
  %i = alloca i32                                 ; <i32*> [#uses=15]
  %j = alloca i32                                 ; <i32*> [#uses=15]
  %cnt = alloca i32                               ; <i32*> [#uses=10]
  %base = alloca i32                              ; <i32*> [#uses=5]
  %idx = alloca i32                               ; <i32*> [#uses=6]
  %tok = alloca i32                               ; <i32*> [#uses=2]
  %tok8 = alloca i32                              ; <i32*> [#uses=2]
  %tok15 = alloca i32                             ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i8* %tokens, i8** %tokens_addr
  store i32 0, i32* %cnt, align 4
  %0 = load i32* @m_y, align 4                    ; <i32> [#uses=1]
  %1 = load i32* @m_width, align 4                ; <i32> [#uses=1]
  %2 = mul nsw i32 %0, %1                         ; <i32> [#uses=1]
  %3 = load i32* @m_x, align 4                    ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  store i32 %4, i32* %base, align 4
  store i32 0, i32* %i, align 4
  br label %bb4

bb:                                               ; preds = %bb4
  store i32 0, i32* %j, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %5 = load i8** %tokens_addr, align 4            ; <i8*> [#uses=1]
  %6 = load i32* %cnt, align 4                    ; <i32> [#uses=1]
  %7 = getelementptr inbounds i8* %5, i32 %6      ; <i8*> [#uses=1]
  %8 = load i8* %7, align 1                       ; <i8> [#uses=1]
  %9 = zext i8 %8 to i32                          ; <i32> [#uses=1]
  store i32 %9, i32* %tok, align 4
  %10 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %11 = add nsw i32 %10, 1                        ; <i32> [#uses=1]
  store i32 %11, i32* %cnt, align 4
  %12 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %13 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %14 = mul nsw i32 %13, %12                      ; <i32> [#uses=1]
  %15 = load i32* %base, align 4                  ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %18 = add nsw i32 %16, %17                      ; <i32> [#uses=1]
  store i32 %18, i32* %idx, align 4
  %19 = load i32* %idx, align 4                   ; <i32> [#uses=1]
  %20 = load i32* %tok, align 4                   ; <i32> [#uses=1]
  %21 = trunc i32 %20 to i8                       ; <i8> [#uses=1]
  %22 = getelementptr inbounds [405504 x i8]* @img_buf_y, i32 0, i32 %19 ; <i8*> [#uses=1]
  store i8 %21, i8* %22, align 1
  %23 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %24 = add nsw i32 %23, 1                        ; <i32> [#uses=1]
  store i32 %24, i32* %j, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %25 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %26 = icmp sle i32 %25, 15                      ; <i1> [#uses=1]
  br i1 %26, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  %27 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %28 = add nsw i32 %27, 1                        ; <i32> [#uses=1]
  store i32 %28, i32* %i, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %entry
  %29 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %30 = icmp sle i32 %29, 15                      ; <i1> [#uses=1]
  br i1 %30, label %bb, label %bb5

bb5:                                              ; preds = %bb4
  %31 = load i32* @m_y, align 4                   ; <i32> [#uses=1]
  %32 = sdiv i32 %31, 2                           ; <i32> [#uses=1]
  %33 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = sdiv i32 %34, 2                           ; <i32> [#uses=1]
  %36 = load i32* @m_x, align 4                   ; <i32> [#uses=1]
  %37 = sdiv i32 %36, 2                           ; <i32> [#uses=1]
  %38 = add nsw i32 %35, %37                      ; <i32> [#uses=1]
  store i32 %38, i32* %base, align 4
  store i32 0, i32* %i, align 4
  br label %bb11

bb6:                                              ; preds = %bb11
  store i32 0, i32* %j, align 4
  br label %bb9

bb7:                                              ; preds = %bb9
  %39 = load i8** %tokens_addr, align 4           ; <i8*> [#uses=1]
  %40 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %41 = getelementptr inbounds i8* %39, i32 %40   ; <i8*> [#uses=1]
  %42 = load i8* %41, align 1                     ; <i8> [#uses=1]
  %43 = zext i8 %42 to i32                        ; <i32> [#uses=1]
  store i32 %43, i32* %tok8, align 4
  %44 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %45 = add nsw i32 %44, 1                        ; <i32> [#uses=1]
  store i32 %45, i32* %cnt, align 4
  %46 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %47 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %48 = mul nsw i32 %47, %46                      ; <i32> [#uses=1]
  %49 = sdiv i32 %48, 2                           ; <i32> [#uses=1]
  %50 = load i32* %base, align 4                  ; <i32> [#uses=1]
  %51 = add nsw i32 %49, %50                      ; <i32> [#uses=1]
  %52 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %53 = add nsw i32 %51, %52                      ; <i32> [#uses=1]
  store i32 %53, i32* %idx, align 4
  %54 = load i32* %idx, align 4                   ; <i32> [#uses=1]
  %55 = load i32* %tok8, align 4                  ; <i32> [#uses=1]
  %56 = trunc i32 %55 to i8                       ; <i8> [#uses=1]
  %57 = getelementptr inbounds [101376 x i8]* @img_buf_u, i32 0, i32 %54 ; <i8*> [#uses=1]
  store i8 %56, i8* %57, align 1
  %58 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %59 = add nsw i32 %58, 1                        ; <i32> [#uses=1]
  store i32 %59, i32* %j, align 4
  br label %bb9

bb9:                                              ; preds = %bb7, %bb6
  %60 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %61 = icmp sle i32 %60, 7                       ; <i1> [#uses=1]
  br i1 %61, label %bb7, label %bb10

bb10:                                             ; preds = %bb9
  %62 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %63 = add nsw i32 %62, 1                        ; <i32> [#uses=1]
  store i32 %63, i32* %i, align 4
  br label %bb11

bb11:                                             ; preds = %bb10, %bb5
  %64 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %65 = icmp sle i32 %64, 7                       ; <i1> [#uses=1]
  br i1 %65, label %bb6, label %bb12

bb12:                                             ; preds = %bb11
  store i32 0, i32* %i, align 4
  br label %bb18

bb13:                                             ; preds = %bb18
  store i32 0, i32* %j, align 4
  br label %bb16

bb14:                                             ; preds = %bb16
  %66 = load i8** %tokens_addr, align 4           ; <i8*> [#uses=1]
  %67 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %68 = getelementptr inbounds i8* %66, i32 %67   ; <i8*> [#uses=1]
  %69 = load i8* %68, align 1                     ; <i8> [#uses=1]
  %70 = zext i8 %69 to i32                        ; <i32> [#uses=1]
  store i32 %70, i32* %tok15, align 4
  %71 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %72 = add nsw i32 %71, 1                        ; <i32> [#uses=1]
  store i32 %72, i32* %cnt, align 4
  %73 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %74 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %75 = mul nsw i32 %74, %73                      ; <i32> [#uses=1]
  %76 = sdiv i32 %75, 2                           ; <i32> [#uses=1]
  %77 = load i32* %base, align 4                  ; <i32> [#uses=1]
  %78 = add nsw i32 %76, %77                      ; <i32> [#uses=1]
  %79 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %80 = add nsw i32 %78, %79                      ; <i32> [#uses=1]
  store i32 %80, i32* %idx, align 4
  %81 = load i32* %idx, align 4                   ; <i32> [#uses=1]
  %82 = load i32* %tok15, align 4                 ; <i32> [#uses=1]
  %83 = trunc i32 %82 to i8                       ; <i8> [#uses=1]
  %84 = getelementptr inbounds [101376 x i8]* @img_buf_v, i32 0, i32 %81 ; <i8*> [#uses=1]
  store i8 %83, i8* %84, align 1
  %85 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %86 = add nsw i32 %85, 1                        ; <i32> [#uses=1]
  store i32 %86, i32* %j, align 4
  br label %bb16

bb16:                                             ; preds = %bb14, %bb13
  %87 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %88 = icmp sle i32 %87, 7                       ; <i1> [#uses=1]
  br i1 %88, label %bb14, label %bb17

bb17:                                             ; preds = %bb16
  %89 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %90 = add nsw i32 %89, 1                        ; <i32> [#uses=1]
  store i32 %90, i32* %i, align 4
  br label %bb18

bb18:                                             ; preds = %bb17, %bb12
  %91 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %92 = icmp sle i32 %91, 7                       ; <i1> [#uses=1]
  br i1 %92, label %bb13, label %bb19

bb19:                                             ; preds = %bb18
  %93 = load i32* @m_x, align 4                   ; <i32> [#uses=1]
  %94 = add nsw i32 %93, 16                       ; <i32> [#uses=1]
  store i32 %94, i32* @m_x, align 4
  %95 = load i32* @m_x, align 4                   ; <i32> [#uses=1]
  %96 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %97 = icmp eq i32 %95, %96                      ; <i1> [#uses=1]
  br i1 %97, label %bb20, label %bb21

bb20:                                             ; preds = %bb19
  store i32 0, i32* @m_x, align 4
  %98 = load i32* @m_y, align 4                   ; <i32> [#uses=1]
  %99 = add nsw i32 %98, 16                       ; <i32> [#uses=1]
  store i32 %99, i32* @m_y, align 4
  br label %bb21

bb21:                                             ; preds = %bb20, %bb19
  %100 = load i32* @m_y, align 4                  ; <i32> [#uses=1]
  %101 = load i32* @m_height, align 4             ; <i32> [#uses=1]
  %102 = icmp eq i32 %100, %101                   ; <i1> [#uses=1]
  br i1 %102, label %bb22, label %bb25

bb22:                                             ; preds = %bb21
  store i32 0, i32* @m_x, align 4
  store i32 0, i32* @m_y, align 4
  %103 = load i32* @FrameCounter, align 4         ; <i32> [#uses=1]
  %104 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([18 x i8]* @.str6, i32 0, i32 0), i32 %103) nounwind ; <i32> [#uses=0]
  call void @Read_YUV(i8* getelementptr inbounds ([405504 x i8]* @Y, i32 0, i32 0), i8* getelementptr inbounds ([101376 x i8]* @U, i32 0, i32 0), i8* getelementptr inbounds ([101376 x i8]* @V, i32 0, i32 0)) nounwind
  %105 = load i32* @m_height, align 4             ; <i32> [#uses=1]
  %106 = load i32* @m_width, align 4              ; <i32> [#uses=1]
  call void @DiffUcharImage(i32 %106, i32 %105, i8* getelementptr inbounds ([405504 x i8]* @Y, i32 0, i32 0), i8* getelementptr inbounds ([405504 x i8]* @img_buf_y, i32 0, i32 0)) nounwind
  %107 = load i32* @m_height, align 4             ; <i32> [#uses=1]
  %108 = ashr i32 %107, 1                         ; <i32> [#uses=1]
  %109 = load i32* @m_width, align 4              ; <i32> [#uses=1]
  %110 = ashr i32 %109, 1                         ; <i32> [#uses=1]
  call void @DiffUcharImage(i32 %110, i32 %108, i8* getelementptr inbounds ([101376 x i8]* @U, i32 0, i32 0), i8* getelementptr inbounds ([101376 x i8]* @img_buf_u, i32 0, i32 0)) nounwind
  %111 = load i32* @m_height, align 4             ; <i32> [#uses=1]
  %112 = ashr i32 %111, 1                         ; <i32> [#uses=1]
  %113 = load i32* @m_width, align 4              ; <i32> [#uses=1]
  %114 = ashr i32 %113, 1                         ; <i32> [#uses=1]
  call void @DiffUcharImage(i32 %114, i32 %112, i8* getelementptr inbounds ([101376 x i8]* @V, i32 0, i32 0), i8* getelementptr inbounds ([101376 x i8]* @img_buf_v, i32 0, i32 0)) nounwind
  %115 = load i32* @NumberOfFrames, align 4       ; <i32> [#uses=1]
  %116 = load i32* @FrameCounter, align 4         ; <i32> [#uses=1]
  %117 = icmp eq i32 %115, %116                   ; <i1> [#uses=1]
  br i1 %117, label %bb23, label %bb24

bb23:                                             ; preds = %bb22
  call void @exit(i32 666) noreturn nounwind
  unreachable

bb24:                                             ; preds = %bb22
  %118 = load i32* @FrameCounter, align 4         ; <i32> [#uses=1]
  %119 = add nsw i32 %118, 1                      ; <i32> [#uses=1]
  store i32 %119, i32* @FrameCounter, align 4
  br label %bb25

bb25:                                             ; preds = %bb24, %bb21
  br label %return

return:                                           ; preds = %bb25
  ret void
}

define void @set_init(i32 %width, i32 %height) nounwind {
entry:
  %width_addr = alloca i32                        ; <i32*> [#uses=3]
  %height_addr = alloca i32                       ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 %width, i32* %width_addr
  store i32 %height, i32* %height_addr
  %0 = load i32* %width_addr, align 4             ; <i32> [#uses=1]
  store i32 %0, i32* @m_width, align 4
  %1 = load i32* %height_addr, align 4            ; <i32> [#uses=1]
  store i32 %1, i32* @m_height, align 4
  %2 = load i8** @yuv_file, align 4               ; <i8*> [#uses=1]
  %3 = load i32* %width_addr, align 4             ; <i32> [#uses=1]
  %4 = load i32* %height_addr, align 4            ; <i32> [#uses=1]
  call void @Read_YUV_init(i32 %3, i32 %4, i8* %2) nounwind
  br label %return

return:                                           ; preds = %entry
  ret void
}

define i1 @isSchedulable_get_data() nounwind {
entry:
  %0 = load %struct.fifo_i8_s** @B, align 4      ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = call i32 @fifo_u8_has_tokens(%struct.fifo_i8_s* %0, i32 384) nounwind ; <i32> [#uses=1]
  %2 = icmp ne i32 %1, 0                        ; <i1> [#uses=1]
  br i1 %2, label %ok, label %nok
 
ok:
	ret i1 1
nok:
	ret i1 0
}

define void @get_data() nounwind {
entry:
  %i = alloca i32                                 ; <i32*> [#uses=5]
  %ptr = alloca i16*                              ; <i16**> [#uses=4]
  %width = alloca i16                             ; <i16*> [#uses=2]
  %height = alloca i16                            ; <i16*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 0, i32* %i, align 4
  br label %bb

bb:                                               ; preds = %bb7, %entry
  %0 = load %struct.fifo_i16_s** @WIDTH, align 4  ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = call i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %0, i32 1) nounwind ; <i32> [#uses=1]
  %2 = icmp ne i32 %1, 0                          ; <i1> [#uses=1]
  br i1 %2, label %bb1, label %bb5

bb1:                                              ; preds = %bb
  %3 = load %struct.fifo_i16_s** @HEIGHT, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %4 = call i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %3, i32 1) nounwind ; <i32> [#uses=1]
  %5 = icmp ne i32 %4, 0                          ; <i1> [#uses=1]
  br i1 %5, label %bb2, label %bb5

bb2:                                              ; preds = %bb1
  %6 = load %struct.fifo_i16_s** @WIDTH, align 4  ; <%struct.fifo_i16_s*> [#uses=1]
  %7 = call i16* @fifo_i16_read(%struct.fifo_i16_s* %6, i32 1) nounwind ; <i16*> [#uses=1]
  store i16* %7, i16** %ptr, align 4
  %8 = load i16** %ptr, align 4                   ; <i16*> [#uses=1]
  %9 = getelementptr inbounds i16* %8, i32 0      ; <i16*> [#uses=1]
  %10 = load i16* %9, align 1                     ; <i16> [#uses=1]
  %11 = sext i16 %10 to i32                       ; <i32> [#uses=1]
  %12 = mul nsw i32 %11, 16                       ; <i32> [#uses=1]
  %13 = trunc i32 %12 to i16                      ; <i16> [#uses=1]
  store i16 %13, i16* %width, align 2
  %14 = load %struct.fifo_i16_s** @WIDTH, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  call void @fifo_i16_read_end(%struct.fifo_i16_s* %14, i32 1) nounwind
  %15 = load %struct.fifo_i16_s** @HEIGHT, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %16 = call i16* @fifo_i16_read(%struct.fifo_i16_s* %15, i32 1) nounwind ; <i16*> [#uses=1]
  store i16* %16, i16** %ptr, align 4
  %17 = load i16** %ptr, align 4                  ; <i16*> [#uses=1]
  %18 = getelementptr inbounds i16* %17, i32 0    ; <i16*> [#uses=1]
  %19 = load i16* %18, align 1                    ; <i16> [#uses=1]
  %20 = sext i16 %19 to i32                       ; <i32> [#uses=1]
  %21 = mul nsw i32 %20, 16                       ; <i32> [#uses=1]
  %22 = trunc i32 %21 to i16                      ; <i16> [#uses=1]
  store i16 %22, i16* %height, align 2
  %23 = load %struct.fifo_i16_s** @HEIGHT, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  call void @fifo_i16_read_end(%struct.fifo_i16_s* %23, i32 1) nounwind
  %24 = load i32* @init, align 4                  ; <i32> [#uses=1]
  %25 = icmp eq i32 %24, 1                        ; <i1> [#uses=1]
  br i1 %25, label %bb3, label %bb4

bb3:                                              ; preds = %bb2
  %26 = load i16* %height, align 2                ; <i16> [#uses=1]
  %27 = sext i16 %26 to i32                       ; <i32> [#uses=1]
  %28 = load i16* %width, align 2                 ; <i16> [#uses=1]
  %29 = sext i16 %28 to i32                       ; <i32> [#uses=1]
  call void @set_init(i32 %29, i32 %27) nounwind
  store i32 0, i32* @init, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2
  %30 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %31 = add nsw i32 %30, 1                        ; <i32> [#uses=1]
  store i32 %31, i32* %i, align 4
  br label %bb5

bb5:                                              ; preds = %bb4, %bb1, %bb
  %32 = load %struct.fifo_i8_s** @B, align 4      ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = call i32 @fifo_u8_has_tokens(%struct.fifo_i8_s* %32, i32 384) nounwind ; <i32> [#uses=1]
  %34 = icmp eq i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb8, label %bb6

bb6:                                              ; preds = %bb5
  %35 = load i32* @init, align 4                  ; <i32> [#uses=1]
  %36 = icmp ne i32 %35, 0                        ; <i1> [#uses=1]
  br i1 %36, label %bb8, label %bb7

bb7:                                              ; preds = %bb6
  %37 = load %struct.fifo_i8_s** @B, align 4      ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = call i8* @fifo_u8_read(%struct.fifo_i8_s* %37, i32 384) nounwind ; <u8*> [#uses=1]
  call void @write_mb(i8* %38) nounwind
  %39 = load %struct.fifo_i8_s** @B, align 4      ; <%struct.fifo_i8_s*> [#uses=1]
  call void @fifo_u8_read_end(%struct.fifo_i8_s* %39, i32 384) nounwind ; <i32> [#uses=0]
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = add nsw i32 %40, 1                        ; <i32> [#uses=1]
  store i32 %41, i32* %i, align 4
  br label %bb

bb8:                                              ; preds = %bb6, %bb5
  br label %return

return:                                           ; preds = %bb8
  ret void
}

!source = !{!0}
!name = !{!1}
!action_scheduler = !{!2}
!inputs = !{!3, !5, !7}
!state_variables = !{!9, !12, !15, !18, !21, !24, !27, !30, !33, !36, !39, !42, !45, !48, !51}
!procedures = !{!58, !59, !60, !61, !62, !63}
!actions = !{!54}


!0 = metadata !{metadata !"tools/Compare.bc"}
!1 = metadata !{metadata !"Compare"}
!2 = metadata !{metadata !64, null}
!64 = metadata !{metadata !54}
!3 = metadata !{metadata !4, metadata !"B", %struct.fifo_i8_s** @B}
!4 = metadata  !{ i32 8 ,  null }
!5 = metadata !{metadata !6, metadata !"WIDTH", %struct.fifo_i16_s** @WIDTH}
!6 = metadata  !{ i32 16 ,  null }
!7 = metadata !{metadata !8, metadata !"HEIGHT", %struct.fifo_i16_s** @HEIGHT}
!8 = metadata  !{ i32 16 ,  null }
!9 = metadata !{metadata !10, metadata !11, i32* @init}
!10 = metadata !{metadata !"init", i1 0, i32 0,  i32 0}
!11 = metadata  !{ i32 32 ,  null }
!12 = metadata !{metadata !13, metadata !14, i32* @m_width}
!13 = metadata !{metadata !"m_width", i1 0, i32 0,  i32 0}
!14 = metadata  !{ i32 32 ,  null }
!15 = metadata !{metadata !16, metadata !17, i32* @m_height}
!16 = metadata !{metadata !"m_height", i1 0, i32 0,  i32 0}
!17 = metadata  !{ i32 32 ,  null }
!18 = metadata !{metadata !19, metadata !20, i8** @yuv_file}
!19 = metadata !{metadata !"yuv_file", i1 0, i32 0,  i32 0}
!20 = metadata  !{ i32 8 ,  null }
!21 = metadata !{metadata !22, metadata !23, %struct.FILE** @ptfile}
!22 = metadata !{metadata !"ptfile", i1 0, i32 0,  i32 0}
!23 = metadata  !{ i32 8 ,  null }
!24 = metadata !{metadata !25, metadata !26, i32* @m_y}
!25 = metadata !{metadata !"m_y", i1 0, i32 0,  i32 0}
!26 = metadata  !{ i32 32 ,  null }
!27 = metadata !{metadata !28, metadata !29, i32* @m_x}
!28 = metadata !{metadata !"m_x", i1 0, i32 0,  i32 0}
!29 = metadata  !{ i32 32 ,  null }
!30 = metadata !{metadata !31, metadata !32, i32* @xsize_int}
!31 = metadata !{metadata !"xsize_int", i1 0, i32 0,  i32 0}
!32 = metadata  !{ i32 32 ,  null }
!33 = metadata !{metadata !34, metadata !35, i32* @ysize_int}
!34 = metadata !{metadata !"ysize_int", i1 0, i32 0,  i32 0}
!35 = metadata  !{ i32 32 ,  null }
!36 = metadata !{metadata !37, metadata !38, i32* @NumberOfFrames}
!37 = metadata !{metadata !"NumberOfFrames", i1 0, i32 0,  i32 0}
!38 = metadata  !{ i32 32 ,  null }
!39 = metadata !{metadata !40, metadata !41, [405504 x i8]* @img_buf_y}
!40 = metadata !{metadata !"img_buf_y", i1 0, i32 0,  i32 0}
!41 = metadata  !{ i32 8 ,  i32 405504 }
!42 = metadata !{metadata !43, metadata !44, [101376 x i8]* @img_buf_u}
!43 = metadata !{metadata !"img_buf_u", i1 0, i32 0,  i32 0}
!44 = metadata  !{ i32 8 ,  i32 101376 }
!45 = metadata !{metadata !46, metadata !47, [101376 x i8]* @img_buf_v}
!46 = metadata !{metadata !"img_buf_v", i1 0, i32 0,  i32 0}
!47 = metadata  !{ i32 8 ,  i32 101376 }
!48 = metadata !{metadata !49, metadata !50, i32* @FrameCounter}
!49 = metadata !{metadata !"FrameCounter", i1 0, i32 0,  i32 0}
!50 = metadata  !{ i32 32 ,  null }
!51 = metadata !{metadata !52, metadata !53, i32* @images}
!52 = metadata !{metadata !"images", i1 0, i32 0,  i32 0}
!53 = metadata  !{ i32 32 ,  null }
!54 = metadata !{ null, metadata !55, null, metadata !56, metadata !57}
!55 = metadata !{metadata !3, i32 1}
!56 = metadata  !{metadata !"isSchedulable_get_data", i1 0, i1()* @isSchedulable_get_data}
!57 = metadata  !{metadata !"get_data", i1 0, void()* @get_data}
!58 = metadata !{metadata !"Filesize", i1 1 , i32()* @Filesize}
!59 = metadata !{metadata !"set_init", i1 0 , void(i32, i32)* @set_init}
!60 = metadata !{metadata !"write_mb", i1 0 , void(i8*)* @write_mb}
!61 = metadata !{metadata !"Read_YUV_init", i1 0 , void(i32, i32, i8*)* @Read_YUV_init}
!62 = metadata !{metadata !"Read_YUV", i1 0 , void(i8*, i8*, i8*)* @Read_YUV}
!63 = metadata !{metadata !"DiffUcharImage", i1 0 , void(i32, i32, i8*, i8*)* @DiffUcharImage}

%struct.fifo_i8_s = type opaque
%struct.fifo_i16_s = type opaque
%struct.fifo_i32_s = type opaque
%struct.fifo_i64_s = type opaque

declare i32 @fifo_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n)
declare i32 @fifo_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_i8_read(%struct.fifo_i8_s* %fifo, i32 %n)
declare void @fifo_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_i8_write(%struct.fifo_i8_s* %fifo, i32 %n)
declare void @fifo_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n)
declare i32 @fifo_u8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n)
declare i32 @fifo_u8_has_room(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_u8_peek(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_u8_read(%struct.fifo_i8_s* %fifo, i32 %n)
declare void @fifo_u8_read_end(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_u8_write(%struct.fifo_i8_s* %fifo, i32 %n)
declare void @fifo_u8_write_end(%struct.fifo_i8_s* %fifo, i32 %n)
declare i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_i16_peek(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_i16_read(%struct.fifo_i16_s* %fifo, i32 %n)
declare void @fifo_i16_read_end(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_i16_write(%struct.fifo_i16_s* %fifo, i32 %n)
declare void @fifo_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_u16_has_room(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_u16_peek(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_u16_read(%struct.fifo_i16_s* %fifo, i32 %n) 
declare void @fifo_u16_read_end(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_u16_write(%struct.fifo_i16_s* %fifo, i32 %n)
declare void @fifo_u16_write_end(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_u16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32 @fifo_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_i32_peek(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_i32_read(%struct.fifo_i32_s* %fifo, i32 %n)
declare void @fifo_i32_read_end(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_i32_write(%struct.fifo_i32_s* %fifo, i32 %n)
declare void @fifo_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32 @fifo_u32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32 @fifo_u32_has_room(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_u32_peek(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_u32_read(%struct.fifo_i32_s* %fifo, i32 %n)
declare void @fifo_u32_read_end(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_u32_write(%struct.fifo_i32_s* %fifo, i32 %n)
declare void @fifo_u32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) 
declare i32 @fifo_i64_has_tokens(%struct.fifo_i64_s* %fifo, i32 %n)
declare i32 @fifo_i64_get_room(%struct.fifo_i64_s* %fifo)
declare i64* @fifo_i64_peek(%struct.fifo_i64_s* %fifo, i32 %n)
declare i64* @fifo_i64_read(%struct.fifo_i64_s* %fifo, i32 %n)
declare void @fifo_i64_read_end(%struct.fifo_i64_s* %fifo, i32 %n)
declare i64* @fifo_i64_write(%struct.fifo_i64_s* %fifo, i32 %n)
declare void @fifo_i64_write_end(%struct.fifo_i64_s* %fifo, i32 %n)
declare i32 @fifo_u64_has_tokens(%struct.fifo_i64_s* %fifo, i32 %n)
declare i32 @fifo_u64_has_room(%struct.fifo_i64_s* %fifo, i32 %n)
declare i32 @fifo_u64_get_room(%struct.fifo_i64_s* %fifo)
declare i64* @fifo_u64_peek(%struct.fifo_i64_s* %fifo, i32 %n)
declare i64* @fifo_u64_read(%struct.fifo_i64_s* %fifo, i32 %n)
declare void @fifo_u64_read_end(%struct.fifo_i64_s* %fifo, i32 %n)
declare i64* @fifo_u64_write(%struct.fifo_i64_s* %fifo, i32 %n)
declare void @fifo_u64_write_end(%struct.fifo_i64_s* %fifo, i32 %n)