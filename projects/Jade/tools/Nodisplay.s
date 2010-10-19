; ModuleID = 'bench.c'
target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32-n8:16:32"
target triple = "i386-mingw32"

%struct.FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }

@.str = private constant [24 x i8] c"Press enter to continue\00", align 1 ; <[24 x i8]*> [#uses=1]
@_iob = dllimport global [0 x %struct.FILE]       ; <[0 x %struct.FILE]*> [#uses=1]
@m_width = internal global i32 0                  ; <i32*> [#uses=11]
@m_height = internal global i32 0                 ; <i32*> [#uses=6]
@m_overlay = internal global [3 x [414720 x i8]] zeroinitializer, align 32 ; <[3 x [414720 x i8]]*> [#uses=6]
@img_buf_y = internal global [414720 x i8] zeroinitializer, align 32 ; <[414720 x i8]*> [#uses=2]
@img_buf_u = internal global [103680 x i8] zeroinitializer, align 32 ; <[103680 x i8]*> [#uses=2]
@img_buf_v = internal global [103680 x i8] zeroinitializer, align 32 ; <[103680 x i8]*> [#uses=2]
@num_images_end = internal global i32 0           ; <i32*> [#uses=4]
@tcl = internal global i32 0                      ; <i32*> [#uses=4]
@num_images_start = internal global i32 0         ; <i32*> [#uses=2]
@.str1 = private constant [15 x i8] c"%f images/sec\0A\00", align 1 ; <[15 x i8]*> [#uses=1]
@m_y = internal global i32 0                      ; <i32*> [#uses=6]
@m_x = internal global i32 0                      ; <i32*> [#uses=7]
@init = internal global i32 0                     ; <i32*> [#uses=2]
@start_timecl = internal global i32 0             ; <i32*> [#uses=2]
@.str2 = private constant [22 x i8] c"set display to %ix%i\0A\00", align 1 ; <[22 x i8]*> [#uses=1]
@WIDTH = global %struct.fifo_i16_s* null ; <%struct.fifo_i16_s**> [#uses=3]
@HEIGHT = global %struct.fifo_i16_s* null ; <%struct.fifo_i16_s**> [#uses=3]
@B = global  %struct.fifo_u8_s*  null ; <%struct.fifo_u8_s**> [#uses=3]

declare void @llvm.memcpy.i32(i8* nocapture, i8* nocapture, i32, i32) nounwind

define internal void @press_a_key(i32 %code) nounwind {
entry:
  %code_addr = alloca i32                         ; <i32*> [#uses=2]
  %buf = alloca [2 x i8]                          ; <[2 x i8]*> [#uses=1]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 %code, i32* %code_addr
  %0 = call i32 @puts(i8* getelementptr inbounds ([24 x i8]* @.str, i32 0, i32 0)) nounwind ; <i32> [#uses=0]
  %buf1 = bitcast [2 x i8]* %buf to i8*           ; <i8*> [#uses=1]
  %1 = call i8* @fgets(i8* %buf1, i32 2, %struct.FILE* getelementptr inbounds ([0 x %struct.FILE]* @_iob, i32 0, i32 0)) nounwind ; <i8*> [#uses=0]
  %2 = load i32* %code_addr, align 4              ; <i32> [#uses=1]
  call void @exit(i32 %2) noreturn nounwind
  unreachable

return:                                           ; No predecessors!
  ret void
}

declare i32 @puts(i8*)

declare i8* @fgets(i8*, i32, %struct.FILE*) nounwind

declare void @exit(i32) noreturn nounwind

define void @show_image() nounwind {
entry:
  %t2cl = alloca i32                              ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  %0 = load i32* @m_width, align 4                ; <i32> [#uses=1]
  %1 = load i32* @m_height, align 4               ; <i32> [#uses=1]
  %2 = mul nsw i32 %0, %1                         ; <i32> [#uses=1]
  call void @llvm.memcpy.i32(i8* getelementptr inbounds ([3 x [414720 x i8]]* @m_overlay, i32 0, i32 0, i32 0), i8* getelementptr inbounds ([414720 x i8]* @img_buf_y, i32 0, i32 0), i32 %2, i32 1)
  %3 = load i32* @m_width, align 4                ; <i32> [#uses=1]
  %4 = load i32* @m_height, align 4               ; <i32> [#uses=1]
  %5 = mul nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = sdiv i32 %5, 4                             ; <i32> [#uses=1]
  call void @llvm.memcpy.i32(i8* getelementptr inbounds ([3 x [414720 x i8]]* @m_overlay, i32 0, i32 1, i32 0), i8* getelementptr inbounds ([103680 x i8]* @img_buf_u, i32 0, i32 0), i32 %6, i32 1)
  %7 = load i32* @m_width, align 4                ; <i32> [#uses=1]
  %8 = load i32* @m_height, align 4               ; <i32> [#uses=1]
  %9 = mul nsw i32 %7, %8                         ; <i32> [#uses=1]
  %10 = sdiv i32 %9, 4                            ; <i32> [#uses=1]
  call void @llvm.memcpy.i32(i8* getelementptr inbounds ([3 x [414720 x i8]]* @m_overlay, i32 0, i32 2, i32 0), i8* getelementptr inbounds ([103680 x i8]* @img_buf_v, i32 0, i32 0), i32 %10, i32 1)
  %11 = load i32* @num_images_end, align 4        ; <i32> [#uses=1]
  %12 = add nsw i32 %11, 1                        ; <i32> [#uses=1]
  store i32 %12, i32* @num_images_end, align 4
  %13 = call i32 @clock() nounwind                ; <i32> [#uses=1]
  store i32 %13, i32* %t2cl, align 4
  %14 = load i32* @tcl, align 4                   ; <i32> [#uses=1]
  %15 = load i32* %t2cl, align 4                  ; <i32> [#uses=1]
  %16 = sub nsw i32 %15, %14                      ; <i32> [#uses=1]
  %17 = icmp sgt i32 %16, 3000                    ; <i1> [#uses=1]
  br i1 %17, label %bb, label %bb1

bb:                                               ; preds = %entry
  %18 = load i32* @num_images_end, align 4        ; <i32> [#uses=1]
  %19 = load i32* @num_images_start, align 4      ; <i32> [#uses=1]
  %20 = sub nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = sitofp i32 %20 to float                   ; <float> [#uses=1]
  %22 = fmul float %21, 1.000000e+03              ; <float> [#uses=1]
  %23 = load i32* @tcl, align 4                   ; <i32> [#uses=1]
  %24 = load i32* %t2cl, align 4                  ; <i32> [#uses=1]
  %25 = sub nsw i32 %24, %23                      ; <i32> [#uses=1]
  %26 = sitofp i32 %25 to float                   ; <float> [#uses=1]
  %27 = fdiv float %22, %26                       ; <float> [#uses=1]
  %28 = fpext float %27 to double                 ; <double> [#uses=1]
  %29 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([15 x i8]* @.str1, i32 0, i32 0), double %28) nounwind ; <i32> [#uses=0]
  %30 = load i32* %t2cl, align 4                  ; <i32> [#uses=1]
  store i32 %30, i32* @tcl, align 4
  %31 = load i32* @num_images_end, align 4        ; <i32> [#uses=1]
  store i32 %31, i32* @num_images_start, align 4
  br label %bb1

bb1:                                              ; preds = %bb, %entry
  br label %return

return:                                           ; preds = %bb1
  ret void
}

declare i32 @clock() nounwind

declare i32 @printf(i8*, ...) nounwind

define void @write_mb(i8* %tokens) nounwind {
entry:
  %tokens_addr = alloca i8*                       ; <i8**> [#uses=4]
  %i = alloca i32                                 ; <i32*> [#uses=15]
  %j = alloca i32                                 ; <i32*> [#uses=15]
  %cnt = alloca i32                               ; <i32*> [#uses=10]
  %base = alloca i32                              ; <i32*> [#uses=5]
  %tok = alloca i32                               ; <i32*> [#uses=2]
  %idx = alloca i32                               ; <i32*> [#uses=2]
  %tok8 = alloca i32                              ; <i32*> [#uses=2]
  %idx9 = alloca i32                              ; <i32*> [#uses=2]
  %tok16 = alloca i32                             ; <i32*> [#uses=2]
  %idx17 = alloca i32                             ; <i32*> [#uses=2]
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
  %10 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %11 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %12 = mul nsw i32 %11, %10                      ; <i32> [#uses=1]
  %13 = load i32* %base, align 4                  ; <i32> [#uses=1]
  %14 = add nsw i32 %12, %13                      ; <i32> [#uses=1]
  %15 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  store i32 %16, i32* %idx, align 4
  %17 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %18 = add nsw i32 %17, 1                        ; <i32> [#uses=1]
  store i32 %18, i32* %cnt, align 4
  %19 = load i32* %idx, align 4                   ; <i32> [#uses=1]
  %20 = load i32* %tok, align 4                   ; <i32> [#uses=1]
  %21 = trunc i32 %20 to i8                       ; <i8> [#uses=1]
  %22 = getelementptr inbounds [414720 x i8]* @img_buf_y, i32 0, i32 %19 ; <i8*> [#uses=1]
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
  br label %bb12

bb6:                                              ; preds = %bb12
  store i32 0, i32* %j, align 4
  br label %bb10

bb7:                                              ; preds = %bb10
  %39 = load i8** %tokens_addr, align 4           ; <i8*> [#uses=1]
  %40 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %41 = getelementptr inbounds i8* %39, i32 %40   ; <i8*> [#uses=1]
  %42 = load i8* %41, align 1                     ; <i8> [#uses=1]
  %43 = zext i8 %42 to i32                        ; <i32> [#uses=1]
  store i32 %43, i32* %tok8, align 4
  %44 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %45 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %46 = mul nsw i32 %45, %44                      ; <i32> [#uses=1]
  %47 = sdiv i32 %46, 2                           ; <i32> [#uses=1]
  %48 = load i32* %base, align 4                  ; <i32> [#uses=1]
  %49 = add nsw i32 %47, %48                      ; <i32> [#uses=1]
  %50 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %51 = add nsw i32 %49, %50                      ; <i32> [#uses=1]
  store i32 %51, i32* %idx9, align 4
  %52 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %53 = add nsw i32 %52, 1                        ; <i32> [#uses=1]
  store i32 %53, i32* %cnt, align 4
  %54 = load i32* %idx9, align 4                  ; <i32> [#uses=1]
  %55 = load i32* %tok8, align 4                  ; <i32> [#uses=1]
  %56 = trunc i32 %55 to i8                       ; <i8> [#uses=1]
  %57 = getelementptr inbounds [103680 x i8]* @img_buf_u, i32 0, i32 %54 ; <i8*> [#uses=1]
  store i8 %56, i8* %57, align 1
  %58 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %59 = add nsw i32 %58, 1                        ; <i32> [#uses=1]
  store i32 %59, i32* %j, align 4
  br label %bb10

bb10:                                             ; preds = %bb7, %bb6
  %60 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %61 = icmp sle i32 %60, 7                       ; <i1> [#uses=1]
  br i1 %61, label %bb7, label %bb11

bb11:                                             ; preds = %bb10
  %62 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %63 = add nsw i32 %62, 1                        ; <i32> [#uses=1]
  store i32 %63, i32* %i, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb5
  %64 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %65 = icmp sle i32 %64, 7                       ; <i1> [#uses=1]
  br i1 %65, label %bb6, label %bb13

bb13:                                             ; preds = %bb12
  store i32 0, i32* %i, align 4
  br label %bb20

bb14:                                             ; preds = %bb20
  store i32 0, i32* %j, align 4
  br label %bb18

bb15:                                             ; preds = %bb18
  %66 = load i8** %tokens_addr, align 4           ; <i8*> [#uses=1]
  %67 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %68 = getelementptr inbounds i8* %66, i32 %67   ; <i8*> [#uses=1]
  %69 = load i8* %68, align 1                     ; <i8> [#uses=1]
  %70 = zext i8 %69 to i32                        ; <i32> [#uses=1]
  store i32 %70, i32* %tok16, align 4
  %71 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %72 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %73 = mul nsw i32 %72, %71                      ; <i32> [#uses=1]
  %74 = sdiv i32 %73, 2                           ; <i32> [#uses=1]
  %75 = load i32* %base, align 4                  ; <i32> [#uses=1]
  %76 = add nsw i32 %74, %75                      ; <i32> [#uses=1]
  %77 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %78 = add nsw i32 %76, %77                      ; <i32> [#uses=1]
  store i32 %78, i32* %idx17, align 4
  %79 = load i32* %cnt, align 4                   ; <i32> [#uses=1]
  %80 = add nsw i32 %79, 1                        ; <i32> [#uses=1]
  store i32 %80, i32* %cnt, align 4
  %81 = load i32* %idx17, align 4                 ; <i32> [#uses=1]
  %82 = load i32* %tok16, align 4                 ; <i32> [#uses=1]
  %83 = trunc i32 %82 to i8                       ; <i8> [#uses=1]
  %84 = getelementptr inbounds [103680 x i8]* @img_buf_v, i32 0, i32 %81 ; <i8*> [#uses=1]
  store i8 %83, i8* %84, align 1
  %85 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %86 = add nsw i32 %85, 1                        ; <i32> [#uses=1]
  store i32 %86, i32* %j, align 4
  br label %bb18

bb18:                                             ; preds = %bb15, %bb14
  %87 = load i32* %j, align 4                     ; <i32> [#uses=1]
  %88 = icmp sle i32 %87, 7                       ; <i1> [#uses=1]
  br i1 %88, label %bb15, label %bb19

bb19:                                             ; preds = %bb18
  %89 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %90 = add nsw i32 %89, 1                        ; <i32> [#uses=1]
  store i32 %90, i32* %i, align 4
  br label %bb20

bb20:                                             ; preds = %bb19, %bb13
  %91 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %92 = icmp sle i32 %91, 7                       ; <i1> [#uses=1]
  br i1 %92, label %bb14, label %bb21

bb21:                                             ; preds = %bb20
  %93 = load i32* @m_x, align 4                   ; <i32> [#uses=1]
  %94 = add nsw i32 %93, 16                       ; <i32> [#uses=1]
  store i32 %94, i32* @m_x, align 4
  %95 = load i32* @m_x, align 4                   ; <i32> [#uses=1]
  %96 = load i32* @m_width, align 4               ; <i32> [#uses=1]
  %97 = icmp eq i32 %95, %96                      ; <i1> [#uses=1]
  br i1 %97, label %bb22, label %bb23

bb22:                                             ; preds = %bb21
  store i32 0, i32* @m_x, align 4
  %98 = load i32* @m_y, align 4                   ; <i32> [#uses=1]
  %99 = add nsw i32 %98, 16                       ; <i32> [#uses=1]
  store i32 %99, i32* @m_y, align 4
  br label %bb23

bb23:                                             ; preds = %bb22, %bb21
  %100 = load i32* @m_y, align 4                  ; <i32> [#uses=1]
  %101 = load i32* @m_height, align 4             ; <i32> [#uses=1]
  %102 = icmp eq i32 %100, %101                   ; <i1> [#uses=1]
  br i1 %102, label %bb24, label %bb25

bb24:                                             ; preds = %bb23
  store i32 0, i32* @m_x, align 4
  store i32 0, i32* @m_y, align 4
  call void @show_image() nounwind
  br label %bb25

bb25:                                             ; preds = %bb24, %bb23
  br label %return

return:                                           ; preds = %bb25
  ret void
}

define internal void @set_init() nounwind {
entry:
  %0 = call i32 @clock() nounwind                 ; <i32> [#uses=1]
  store i32 %0, i32* @start_timecl, align 4
  %1 = load i32* @start_timecl, align 4           ; <i32> [#uses=1]
  store i32 %1, i32* @tcl, align 4
  store i32 1, i32* @init, align 4
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal void @set_video(i32 %width, i32 %height) nounwind {
entry:
  %width_addr = alloca i32                        ; <i32*> [#uses=4]
  %height_addr = alloca i32                       ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 %width, i32* %width_addr
  store i32 %height, i32* %height_addr
  %0 = load i32* @m_width, align 4                ; <i32> [#uses=1]
  %1 = load i32* %width_addr, align 4             ; <i32> [#uses=1]
  %2 = icmp eq i32 %1, %0                         ; <i1> [#uses=1]
  br i1 %2, label %bb, label %bb1

bb:                                               ; preds = %entry
  %3 = load i32* @m_height, align 4               ; <i32> [#uses=1]
  %4 = load i32* %height_addr, align 4            ; <i32> [#uses=1]
  %5 = icmp eq i32 %4, %3                         ; <i1> [#uses=1]
  br i1 %5, label %bb2, label %bb1

bb1:                                              ; preds = %bb, %entry
  %6 = load i32* %width_addr, align 4             ; <i32> [#uses=1]
  store i32 %6, i32* @m_width, align 4
  %7 = load i32* %height_addr, align 4            ; <i32> [#uses=1]
  store i32 %7, i32* @m_height, align 4
  %8 = load i32* %width_addr, align 4             ; <i32> [#uses=1]
  %9 = load i32* %height_addr, align 4            ; <i32> [#uses=1]
  %10 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([22 x i8]* @.str2, i32 0, i32 0), i32 %8, i32 %9) nounwind ; <i32> [#uses=0]
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define i32 @scheduler() nounwind {
entry:
  %i = alloca i32                                 ; <i32*> [#uses=5]
  %ptr = alloca i16*                              ; <i16**> [#uses=4]
  %width = alloca i16                             ; <i16*> [#uses=2]
  %height = alloca i16                            ; <i16*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 0, i32* %i, align 4
  br label %bb

bb:                                               ; preds = %bb6, %entry
  %0 = load %struct.fifo_i16_s** @WIDTH, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = call i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %0, i32 1) nounwind ; <i32> [#uses=1]
  %2 = icmp ne i32 %1, 0                          ; <i1> [#uses=1]
  br i1 %2, label %bb1, label %bb3

bb1:                                              ; preds = %bb
  %3 = load %struct.fifo_i16_s** @HEIGHT, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %4 = call i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %3, i32 1) nounwind ; <i32> [#uses=1]
  %5 = icmp ne i32 %4, 0                          ; <i1> [#uses=1]
  br i1 %5, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %6 = load %struct.fifo_i16_s** @WIDTH, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
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
  %24 = load i16* %height, align 2                ; <i16> [#uses=1]
  %25 = sext i16 %24 to i32                       ; <i32> [#uses=1]
  %26 = load i16* %width, align 2                 ; <i16> [#uses=1]
  %27 = sext i16 %26 to i32                       ; <i32> [#uses=1]
  call void @set_video(i32 %27, i32 %25) nounwind
  %28 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %29 = add nsw i32 %28, 1                        ; <i32> [#uses=1]
  store i32 %29, i32* %i, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1, %bb
  %30 = load %struct.fifo_u8_s** @B, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  %31 = call i32 @fifo_i8_has_tokens(%struct.fifo_u8_s* %30, i32 384) nounwind ; <i32> [#uses=1]
  %32 = icmp ne i32 %31, 0                        ; <i1> [#uses=1]
  br i1 %32, label %bb4, label %bb7

bb4:                                              ; preds = %bb3
  %33 = load i32* @init, align 4                  ; <i32> [#uses=1]
  %34 = icmp eq i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb5, label %bb6

bb5:                                              ; preds = %bb4
  call void @set_init() nounwind
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %35 = load %struct.fifo_u8_s** @B, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  %36 = call i8* @fifo_i8_read(%struct.fifo_u8_s* %35, i32 384) nounwind ; <i8*> [#uses=1]
  call void @write_mb(i8* %36) nounwind
  %37 = load %struct.fifo_u8_s** @B, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  call void @fifo_i8_read_end(%struct.fifo_u8_s* %37, i32 384) nounwind
  %38 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %39 = add nsw i32 %38, 1                        ; <i32> [#uses=1]
  store i32 %39, i32* %i, align 4
  br label %bb

bb7:                                              ; preds = %bb3
  br label %return

return:                                           ; preds = %bb7
  ret i32 1
}


!source = !{!0}
!name = !{!1}
!action_scheduler = !{!2}
!inputs = !{!3, !5, !7}
!state_variables = !{!9, !12, !15, !18, !21, !24, !27, !30, !33, !36, !39, !42, !45, !48, !51, !54}
!procedures = !{!57, !58, !59, !60}

!0 = metadata !{metadata !"tools/Display.bc"}
!1 = metadata !{metadata !"Display"}
!2 = metadata !{null, null, i32()* @scheduler}
!3 = metadata !{metadata !4, metadata !"B", %struct.fifo_u8_s** @B}
!4 = metadata  !{ i32 8 ,  null }
!5 = metadata !{metadata !6, metadata !"WIDTH", %struct.fifo_i16_s** @WIDTH}
!6 = metadata  !{ i32 16 ,  null }
!7 = metadata !{metadata !8, metadata !"HEIGHT", %struct.fifo_i16_s** @HEIGHT}
!8 = metadata  !{ i32 16 ,  null }
!9 = metadata !{metadata !10, metadata !11, i32* @init}
!10 = metadata !{metadata !"init", i1 0, i32 0,  i32 0}
!11 = metadata  !{ i32 32 ,  null }
!12 = metadata !{metadata !13, metadata !14, i32* @start_timecl}
!13 = metadata !{metadata !"start_timecl", i1 0, i32 0,  i32 0}
!14 = metadata  !{ i32 32 ,  null }
!15 = metadata !{metadata !16, metadata !17, i32* @tcl}
!16 = metadata !{metadata !"tcl", i1 0, i32 0,  i32 0}
!17 = metadata  !{ i32 32 ,  null }
!18 = metadata !{metadata !19, metadata !20, i32* @m_width}
!19 = metadata !{metadata !"m_width", i1 0, i32 0,  i32 0}
!20 = metadata  !{ i32 32 ,  null }
!21 = metadata !{metadata !22, metadata !23, i32* @m_height}
!22 = metadata !{metadata !"m_height", i1 0, i32 0,  i32 0}
!23 = metadata  !{ i32 32 ,  null }
!24 = metadata !{metadata !25, metadata !26, i32* @m_x}
!25 = metadata !{metadata !"m_x", i1 0, i32 0,  i32 0}
!26 = metadata  !{ i32 32 ,  null }
!27 = metadata !{metadata !28, metadata !29, i32* @m_y}
!28 = metadata !{metadata !"m_y", i1 0, i32 0,  i32 0}
!29 = metadata  !{ i32 32 ,  null }
!30 = metadata !{metadata !31, metadata !32, [414720 x i8]* @img_buf_y}
!31 = metadata !{metadata !"img_buf_y", i1 0, i32 0,  i32 0}
!32 = metadata  !{ i32 8,  i32 414720}
!33 = metadata !{metadata !34, metadata !35, [103680 x i8]* @img_buf_u}
!34 = metadata !{metadata !"img_buf_u", i1 0, i32 0,  i32 0}
!35 = metadata  !{ i32 32,  i32 103680 }
!36 = metadata !{metadata !37, metadata !38, [103680 x i8]* @img_buf_v}
!37 = metadata !{metadata !"img_buf_v", i1 0, i32 0,  i32 0}
!38 = metadata  !{ i32 32,  i32 103680 }
!39 = metadata !{metadata !40, metadata !41, i32* @num_images_end}
!40 = metadata !{metadata !"num_images_end", i1 0, i32 0,  i32 0}
!41 = metadata  !{ i32 32 ,  null }
!42 = metadata !{metadata !43, metadata !44, i32* @num_images_start}
!43 = metadata !{metadata !"num_images_start", i1 0, i32 0,  i32 0}
!44 = metadata  !{ i32 32 ,  null }
!45 = metadata !{metadata !46, metadata !47, [24 x i8]* @.str}
!46 = metadata !{metadata !".str", i1 0, i32 0,  i32 0}
!47 = metadata  !{ i32 8 ,  i32 24 }
!48 = metadata !{metadata !49, metadata !50, [15 x i8]* @.str1}
!49 = metadata !{metadata !".str1", i1 0, i32 0,  i32 0}
!50 = metadata  !{ i32 8 ,  i32 15 }
!51 = metadata !{metadata !52, metadata !53, [22 x i8]* @.str2}
!52 = metadata !{metadata !".str2", i1 0, i32 0,  i32 0}
!53 = metadata  !{ i32 8 ,  i32 22 }
!54 = metadata !{metadata !55, metadata !56, [3 x [414720 x i8]]* @m_overlay}
!55 = metadata !{metadata !"m_overlay", i1 0, i32 0,  i32 0}
!56 = metadata  !{ i32 8 ,  i32 414720, i32 3 }
!57 = metadata !{metadata !"set_video", i1 0 , void(i32, i32)* @set_video}
!58 = metadata !{metadata !"set_init", i1 0 , void()* @set_init}
!59 = metadata !{metadata !"write_mb", i1 0 , void(i8*)* @write_mb}
!60 = metadata !{metadata !"show_image", i1 0 , void()* @show_image}

%struct.FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }

%struct.fifo_i8_s = type opaque
%struct.fifo_i32_s = type opaque
%struct.fifo_i16_s = type opaque
%struct.fifo_u8_s = type opaque
%struct.fifo_u32_s = type opaque
%struct.fifo_u16_s = type opaque

declare i32 @fifo_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n)
declare i32 @fifo_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_i8_read(%struct.fifo_i8_s* %fifo, i32 %n)
declare void @fifo_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n)
declare i8* @fifo_i8_write(%struct.fifo_i8_s* %fifo, i32 %n)
declare void @fifo_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n)
declare i32 @fifo_u8_has_tokens(%struct.fifo_u8_s* %fifo, i32 %n)
declare i32 @fifo_u8_has_room(%struct.fifo_u8_s* %fifo, i32 %n)
declare i8* @fifo_u8_peek(%struct.fifo_u8_s* %fifo, i32 %n)
declare i8* @fifo_u8_read(%struct.fifo_u8_s* %fifo, i32 %n)
declare void @fifo_u8_read_end(%struct.fifo_u8_s* %fifo, i32 %n)
declare i8* @fifo_u8_write(%struct.fifo_u8_s* %fifo, i32 %n)
declare void @fifo_u8_write_end(%struct.fifo_u8_s* %fifo, i32 %n)
declare i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_i16_peek(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_i16_read(%struct.fifo_i16_s* %fifo, i32 %n)
declare void @fifo_i16_read_end(%struct.fifo_i16_s* %fifo, i32 %n)
declare i16* @fifo_i16_write(%struct.fifo_i16_s* %fifo, i32 %n)
declare void @fifo_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n)
declare i32 @fifo_u16_has_room(%struct.fifo_u16_s* %fifo, i32 %n)
declare i16* @fifo_u16_peek(%struct.fifo_u16_s* %fifo, i32 %n)
declare i16* @fifo_u16_read(%struct.fifo_u16_s* %fifo, i32 %n) 
declare void @fifo_u16_read_end(%struct.fifo_u16_s* %fifo, i32 %n)
declare i16* @fifo_u16_write(%struct.fifo_u16_s* %fifo, i32 %n)
declare void @fifo_u16_write_end(%struct.fifo_u16_s* %fifo, i32 %n)
declare i32 @fifo_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32 @fifo_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_i32_peek(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_i32_read(%struct.fifo_i32_s* %fifo, i32 %n)
declare void @fifo_i32_read_end(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32* @fifo_i32_write(%struct.fifo_i32_s* %fifo, i32 %n)
declare void @fifo_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n)
declare i32 @fifo_u32_has_tokens(%struct.fifo_u32_s* %fifo, i32 %n)
declare i32 @fifo_u32_has_room(%struct.fifo_u32_s* %fifo, i32 %n)
declare i32* @fifo_u32_peek(%struct.fifo_u32_s* %fifo, i32 %n)
declare i32* @fifo_u32_read(%struct.fifo_u32_s* %fifo, i32 %n)
declare void @fifo_u32_read_end(%struct.fifo_u32_s* %fifo, i32 %n)
declare i32* @fifo_u32_write(%struct.fifo_u32_s* %fifo, i32 %n)
declare void @fifo_u32_write_end(%struct.fifo_u32_s* %fifo, i32 %n) 
