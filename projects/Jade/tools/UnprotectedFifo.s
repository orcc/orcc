; ModuleID = 'fifo_generic.c'
target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:32:64-f32:32:32-f64:32:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32"
target triple = "i386-pc-linux-gnu"

%struct.fifo_s = type { i32, i32, i8*, i32, i32 }

define internal i8* @getPeekPtr(%struct.fifo_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 8                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 8                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 8                      ; <i32> [#uses=1]
  %10 = mul i32 %6, %9                            ; <i32> [#uses=1]
  %11 = sext i32 %10 to i64                       ; <i64> [#uses=1]
  %12 = getelementptr inbounds i8* %3, i64 %11    ; <i8*> [#uses=1]
  store i8* %12, i8** %0, align 8
  %13 = load i8** %0, align 8                     ; <i8*> [#uses=1]
  store i8* %13, i8** %retval, align 8
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal i8* @getReadPtr(%struct.fifo_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 8                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 8                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 8
  %11 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 8                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 8                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul i32 %16, %17                          ; <i32> [#uses=1]
  %19 = sext i32 %18 to i64                       ; <i64> [#uses=1]
  %20 = getelementptr inbounds i8* %13, i64 %19   ; <i8*> [#uses=1]
  store i8* %20, i8** %0, align 8
  %21 = load i8** %0, align 8                     ; <i8*> [#uses=1]
  store i8* %21, i8** %retval, align 8
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal i32 @hasRoom(%struct.fifo_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %num_free, align 4
  %8 = load i32* %num_free, align 4               ; <i32> [#uses=1]
  %9 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %10 = icmp sge i32 %8, %9                       ; <i1> [#uses=1]
  %11 = zext i1 %10 to i32                        ; <i32> [#uses=1]
  store i32 %11, i32* %res, align 4
  %12 = load i32* %res, align 4                   ; <i32> [#uses=1]
  %13 = icmp eq i32 %12, 0                        ; <i1> [#uses=1]
  br i1 %13, label %bb, label %bb4

bb:                                               ; preds = %entry
  %14 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 8                    ; <i32> [#uses=1]
  %20 = sub i32 %16, %19                          ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 8                    ; <i32> [#uses=1]
  %24 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %25 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %26 = add nsw i32 %24, %25                      ; <i32> [#uses=1]
  %27 = icmp sge i32 %23, %26                     ; <i1> [#uses=1]
  br i1 %27, label %bb1, label %bb4

bb1:                                              ; preds = %bb
  %28 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %29 = icmp sgt i32 %28, 0                       ; <i1> [#uses=1]
  br i1 %29, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %30 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 8                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul i32 %32, %33                          ; <i32> [#uses=1]
  %35 = sext i32 %34 to i64                       ; <i64> [#uses=1]
  %36 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_s* %36, i32 0, i32 2 ; <i8**> [#uses=1]
  %38 = load i8** %37, align 8                    ; <i8*> [#uses=1]
  %39 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_s* %39, i32 0, i32 3 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 8                    ; <i32> [#uses=1]
  %42 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_s* %42, i32 0, i32 0 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 8                    ; <i32> [#uses=1]
  %45 = mul i32 %41, %44                          ; <i32> [#uses=1]
  %46 = sext i32 %45 to i64                       ; <i64> [#uses=1]
  %47 = getelementptr inbounds i8* %38, i64 %46   ; <i8*> [#uses=1]
  %48 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_s* %48, i32 0, i32 2 ; <i8**> [#uses=1]
  %50 = load i8** %49, align 8                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i64(i8* %50, i8* %47, i64 %35, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %51 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_s* %51, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %52, align 8
  %53 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %54 = getelementptr inbounds %struct.fifo_s* %53, i32 0, i32 4 ; <i32*> [#uses=1]
  %55 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %55, i32* %54, align 4
  %56 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_s* %56, i32 0, i32 1 ; <i32*> [#uses=1]
  %58 = load i32* %57, align 4                    ; <i32> [#uses=1]
  %59 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %60 = sub i32 %58, %59                          ; <i32> [#uses=1]
  %61 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %62 = icmp sge i32 %60, %61                     ; <i1> [#uses=1]
  %63 = zext i1 %62 to i32                        ; <i32> [#uses=1]
  store i32 %63, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %64 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %64, i32* %0, align 4
  %65 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %65, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

declare void @llvm.memcpy.i64(i8* nocapture, i8* nocapture, i64, i32) nounwind
declare i32 @printf(i8* noalias , ...) nounwind 

define internal i32 @hasTokens(%struct.fifo_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 8                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %num_tokens, align 4
  %8 = load i32* %num_tokens, align 4             ; <i32> [#uses=1]
  %9 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %10 = icmp sge i32 %8, %9                       ; <i1> [#uses=1]
  %11 = zext i1 %10 to i32                        ; <i32> [#uses=1]
  store i32 %11, i32* %res, align 4
  %12 = load i32* %res, align 4                   ; <i32> [#uses=1]
  %13 = icmp eq i32 %12, 0                        ; <i1> [#uses=1]
  br i1 %13, label %bb, label %bb2

bb:                                               ; preds = %entry
  %14 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %15 = icmp eq i32 %14, 0                        ; <i1> [#uses=1]
  br i1 %15, label %bb1, label %bb2

bb1:                                              ; preds = %bb
  %16 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 8
  %18 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %19, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb, %entry
  %20 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %20, i32* %0, align 4
  %21 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %21, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval3
}

define internal i8* @getWritePtr(%struct.fifo_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_s** %fifo_addr, align 8  ; <%struct.fifo_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 8                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_s** %fifo_addr, align 8 ; <%struct.fifo_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 8                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul i32 %16, %17                          ; <i32> [#uses=1]
  %19 = sext i32 %18 to i64                       ; <i64> [#uses=1]
  %20 = getelementptr inbounds i8* %13, i64 %19   ; <i8*> [#uses=1]
  store i8* %20, i8** %0, align 8
  %21 = load i8** %0, align 8                     ; <i8*> [#uses=1]
  store i8* %21, i8** %retval, align 8
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @setWriteEnd(%struct.fifo_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal void @setReadEnd(%struct.fifo_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

%struct.fifo_i8_s = type { i32, i8*, i32, i32, i32, [1024 x i8] }
%struct.fifo_i32_s = type { i32, i32*, i32, i32, i32, [1024 x i32] }
%struct.fifo_i16_s = type { i32, i16*, i32, i32, i32, [1024 x i16] }

define internal i32 @fifo_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = icmp sge i32 %3, %4                        ; <i1> [#uses=1]
  %6 = zext i1 %5 to i32                          ; <i32> [#uses=1]
  store i32 %6, i32* %0, align 4
  %7 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %7, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32 @fifo_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  %8 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %9 = icmp sge i32 %7, %8                        ; <i1> [#uses=1]
  %10 = zext i1 %9 to i32                         ; <i32> [#uses=1]
  store i32 %10, i32* %0, align 4
  %11 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %11, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i8* @fifo_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i8_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i8_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i8_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i8_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub i32 %19, %22                          ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub i32 %24, %25                          ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 1 ; <i8**> [#uses=1]
  %32 = load i8** %31, align 4                    ; <i8*> [#uses=1]
  %33 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %34 = getelementptr inbounds %struct.fifo_i8_s* %33, i32 0, i32 2 ; <i32*> [#uses=1]
  %35 = load i32* %34, align 4                    ; <i32> [#uses=1]
  %36 = getelementptr inbounds i8* %32, i32 %35   ; <i8*> [#uses=1]
  %37 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i8_s* %37, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i8]* %38, i32 0, i32 0 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %39, i8* %36, i32 %29, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %40 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %41 = icmp ne i32 %40, 0                        ; <i1> [#uses=1]
  br i1 %41, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %42 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %43 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i8_s* %43, i32 0, i32 1 ; <i8**> [#uses=1]
  %45 = load i8** %44, align 4                    ; <i8*> [#uses=1]
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i8_s* %47, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %49 = getelementptr inbounds [1024 x i8]* %48, i32 0, i32 %46 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %49, i8* %45, i32 %42, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %52 = getelementptr inbounds [1024 x i8]* %51, i32 0, i32 0 ; <i8*> [#uses=1]
  store i8* %52, i8** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %53 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %53, i8** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval7
}

declare void @llvm.memcpy.i32(i8* nocapture, i8* nocapture, i32, i32) nounwind

define internal i8* @fifo_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i8* @fifo_i8_peek(%struct.fifo_i8_s* %1, i32 %2) nounwind ; <i8*> [#uses=1]
  store i8* %3, i8** %0, align 4
  %4 = load i8** %0, align 4                      ; <i8*> [#uses=1]
  store i8* %4, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i8_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub i32 %27, %30                          ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i8_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i8* @fifo_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i8_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i8_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i8_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %19 = getelementptr inbounds [1024 x i8]* %18, i32 0, i32 0 ; <i8*> [#uses=1]
  store i8* %19, i8** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %20, i8** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval3
}

define internal void @fifo_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=15]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 0 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %27 = getelementptr inbounds %struct.fifo_i8_s* %26, i32 0, i32 3 ; <i32*> [#uses=1]
  %28 = load i32* %27, align 4                    ; <i32> [#uses=1]
  %29 = sub i32 %25, %28                          ; <i32> [#uses=1]
  store i32 %29, i32* %num_end, align 4
  %30 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %31 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %32 = sub i32 %30, %31                          ; <i32> [#uses=1]
  store i32 %32, i32* %num_beginning, align 4
  %33 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %34 = icmp ne i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %35 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %36 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_i8_s* %36, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %38 = getelementptr inbounds [1024 x i8]* %37, i32 0, i32 0 ; <i8*> [#uses=1]
  %39 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i8_s* %39, i32 0, i32 1 ; <i8**> [#uses=1]
  %41 = load i8** %40, align 4                    ; <i8*> [#uses=1]
  %42 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_i8_s* %42, i32 0, i32 3 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 4                    ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %41, i32 %44   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %45, i8* %38, i32 %35, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %46 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %47 = icmp ne i32 %46, 0                        ; <i1> [#uses=1]
  br i1 %47, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %48 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %49 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %52 = getelementptr inbounds [1024 x i8]* %51, i32 0, i32 %49 ; <i8*> [#uses=1]
  %53 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %54 = getelementptr inbounds %struct.fifo_i8_s* %53, i32 0, i32 1 ; <i8**> [#uses=1]
  %55 = load i8** %54, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %52, i32 %48, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_i8_s* %56, i32 0, i32 3 ; <i32*> [#uses=1]
  %58 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %58, i32* %57, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  br label %return

return:                                           ; preds = %bb6
  ret void
}

define internal i32 @fifo_u_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = icmp sge i32 %3, %4                        ; <i1> [#uses=1]
  %6 = zext i1 %5 to i32                          ; <i32> [#uses=1]
  store i32 %6, i32* %0, align 4
  %7 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %7, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32 @fifo_u_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  %8 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %9 = icmp sge i32 %7, %8                        ; <i1> [#uses=1]
  %10 = zext i1 %9 to i32                         ; <i32> [#uses=1]
  store i32 %10, i32* %0, align 4
  %11 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %11, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i8* @fifo_u_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i8_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i8_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i8_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i8_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub i32 %19, %22                          ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub i32 %24, %25                          ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 1 ; <i8**> [#uses=1]
  %32 = load i8** %31, align 4                    ; <i8*> [#uses=1]
  %33 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %34 = getelementptr inbounds %struct.fifo_i8_s* %33, i32 0, i32 2 ; <i32*> [#uses=1]
  %35 = load i32* %34, align 4                    ; <i32> [#uses=1]
  %36 = getelementptr inbounds i8* %32, i32 %35   ; <i8*> [#uses=1]
  %37 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i8_s* %37, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i8]* %38, i32 0, i32 0 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %39, i8* %36, i32 %29, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %40 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %41 = icmp ne i32 %40, 0                        ; <i1> [#uses=1]
  br i1 %41, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %42 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %43 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i8_s* %43, i32 0, i32 1 ; <i8**> [#uses=1]
  %45 = load i8** %44, align 4                    ; <i8*> [#uses=1]
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i8_s* %47, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %49 = getelementptr inbounds [1024 x i8]* %48, i32 0, i32 %46 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %49, i8* %45, i32 %42, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %52 = getelementptr inbounds [1024 x i8]* %51, i32 0, i32 0 ; <i8*> [#uses=1]
  store i8* %52, i8** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %53 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %53, i8** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval7
}

define internal i8* @fifo_u_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i8* @fifo_u_i8_peek(%struct.fifo_i8_s* %1, i32 %2) nounwind ; <i8*> [#uses=1]
  store i8* %3, i8** %0, align 4
  %4 = load i8** %0, align 4                      ; <i8*> [#uses=1]
  store i8* %4, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_u_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i8_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub i32 %27, %30                          ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i8_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i8* @fifo_u_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i8_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i8_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i8_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %19 = getelementptr inbounds [1024 x i8]* %18, i32 0, i32 0 ; <i8*> [#uses=1]
  store i8* %19, i8** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %20, i8** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval3
}

define internal void @fifo_u_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=15]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 0 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %27 = getelementptr inbounds %struct.fifo_i8_s* %26, i32 0, i32 3 ; <i32*> [#uses=1]
  %28 = load i32* %27, align 4                    ; <i32> [#uses=1]
  %29 = sub i32 %25, %28                          ; <i32> [#uses=1]
  store i32 %29, i32* %num_end, align 4
  %30 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %31 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %32 = sub i32 %30, %31                          ; <i32> [#uses=1]
  store i32 %32, i32* %num_beginning, align 4
  %33 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %34 = icmp ne i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %35 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %36 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_i8_s* %36, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %38 = getelementptr inbounds [1024 x i8]* %37, i32 0, i32 0 ; <i8*> [#uses=1]
  %39 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i8_s* %39, i32 0, i32 1 ; <i8**> [#uses=1]
  %41 = load i8** %40, align 4                    ; <i8*> [#uses=1]
  %42 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_i8_s* %42, i32 0, i32 3 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 4                    ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %41, i32 %44   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %45, i8* %38, i32 %35, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %46 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %47 = icmp ne i32 %46, 0                        ; <i1> [#uses=1]
  br i1 %47, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %48 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %49 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 5 ; <[1024 x i8]*> [#uses=1]
  %52 = getelementptr inbounds [1024 x i8]* %51, i32 0, i32 %49 ; <i8*> [#uses=1]
  %53 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %54 = getelementptr inbounds %struct.fifo_i8_s* %53, i32 0, i32 1 ; <i8**> [#uses=1]
  %55 = load i8** %54, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %52, i32 %48, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_i8_s* %56, i32 0, i32 3 ; <i32*> [#uses=1]
  %58 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %58, i32* %57, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  br label %return

return:                                           ; preds = %bb6
  ret void
}

define internal i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = icmp sge i32 %3, %4                        ; <i1> [#uses=1]
  %6 = zext i1 %5 to i32                          ; <i32> [#uses=1]
  store i32 %6, i32* %0, align 4
  %7 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %7, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32 @fifo_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i16_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  %8 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %9 = icmp sge i32 %7, %8                        ; <i1> [#uses=1]
  %10 = zext i1 %9 to i32                         ; <i32> [#uses=1]
  store i32 %10, i32* %0, align 4
  %11 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %11, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i16* @fifo_i16_peek(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i16_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i16_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i16_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i16_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub i32 %19, %22                          ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub i32 %24, %25                          ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = mul i32 %29, 2                            ; <i32> [#uses=1]
  %31 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_i16_s* %31, i32 0, i32 1 ; <i16**> [#uses=1]
  %33 = load i16** %32, align 4                   ; <i16*> [#uses=1]
  %34 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i16* %33, i32 %36  ; <i16*> [#uses=1]
  %38 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i16_s* %38, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %40 = getelementptr inbounds [1024 x i16]* %39, i32 0, i32 0 ; <i16*> [#uses=1]
  %41 = bitcast i16* %40 to i8*                   ; <i8*> [#uses=1]
  %42 = bitcast i16* %37 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %41, i8* %42, i32 %30, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %43 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %44 = icmp ne i32 %43, 0                        ; <i1> [#uses=1]
  br i1 %44, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %46 = mul i32 %45, 2                            ; <i32> [#uses=1]
  %47 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i16_s* %47, i32 0, i32 1 ; <i16**> [#uses=1]
  %49 = load i16** %48, align 4                   ; <i16*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i16_s* %51, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i16]* %52, i32 0, i32 %50 ; <i16*> [#uses=1]
  %54 = bitcast i16* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i16* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_i16_s* %56, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %58 = getelementptr inbounds [1024 x i16]* %57, i32 0, i32 0 ; <i16*> [#uses=1]
  store i16* %58, i16** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %59 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %59, i16** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval7
}

define internal i16* @fifo_i16_read(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i16* @fifo_i16_peek(%struct.fifo_i16_s* %1, i32 %2) nounwind ; <i16*> [#uses=1]
  store i16* %3, i16** %0, align 4
  %4 = load i16** %0, align 4                     ; <i16*> [#uses=1]
  store i16* %4, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_i16_read_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i16_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i16_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i16_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub i32 %27, %30                          ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i16_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i16* @fifo_i16_write(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i16_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i16_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i16_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %19 = getelementptr inbounds [1024 x i16]* %18, i32 0, i32 0 ; <i16*> [#uses=1]
  store i16* %19, i16** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %20, i16** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval3
}

define internal void @fifo_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=15]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i16_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i16_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 0 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %27 = getelementptr inbounds %struct.fifo_i16_s* %26, i32 0, i32 3 ; <i32*> [#uses=1]
  %28 = load i32* %27, align 4                    ; <i32> [#uses=1]
  %29 = sub i32 %25, %28                          ; <i32> [#uses=1]
  store i32 %29, i32* %num_end, align 4
  %30 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %31 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %32 = sub i32 %30, %31                          ; <i32> [#uses=1]
  store i32 %32, i32* %num_beginning, align 4
  %33 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %34 = icmp ne i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %35 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %36 = mul i32 %35, 2                            ; <i32> [#uses=1]
  %37 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i16_s* %37, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i16]* %38, i32 0, i32 0 ; <i16*> [#uses=1]
  %40 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_i16_s* %40, i32 0, i32 1 ; <i16**> [#uses=1]
  %42 = load i16** %41, align 4                   ; <i16*> [#uses=1]
  %43 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i16_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %44, align 4                    ; <i32> [#uses=1]
  %46 = getelementptr inbounds i16* %42, i32 %45  ; <i16*> [#uses=1]
  %47 = bitcast i16* %46 to i8*                   ; <i8*> [#uses=1]
  %48 = bitcast i16* %39 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %47, i8* %48, i32 %36, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %50 = icmp ne i32 %49, 0                        ; <i1> [#uses=1]
  br i1 %50, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %51 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %52 = mul i32 %51, 2                            ; <i32> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i16_s* %54, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %56 = getelementptr inbounds [1024 x i16]* %55, i32 0, i32 %53 ; <i16*> [#uses=1]
  %57 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i16_s* %57, i32 0, i32 1 ; <i16**> [#uses=1]
  %59 = load i16** %58, align 4                   ; <i16*> [#uses=1]
  %60 = bitcast i16* %59 to i8*                   ; <i8*> [#uses=1]
  %61 = bitcast i16* %56 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %60, i8* %61, i32 %52, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %62 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %63 = getelementptr inbounds %struct.fifo_i16_s* %62, i32 0, i32 3 ; <i32*> [#uses=1]
  %64 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %64, i32* %63, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  br label %return

return:                                           ; preds = %bb6
  ret void
}

define internal i32 @fifo_u_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = icmp sge i32 %3, %4                        ; <i1> [#uses=1]
  %6 = zext i1 %5 to i32                          ; <i32> [#uses=1]
  store i32 %6, i32* %0, align 4
  %7 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %7, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32 @fifo_u_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i16_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  %8 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %9 = icmp sge i32 %7, %8                        ; <i1> [#uses=1]
  %10 = zext i1 %9 to i32                         ; <i32> [#uses=1]
  store i32 %10, i32* %0, align 4
  %11 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %11, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i16* @fifo_u_i16_peek(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i16_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i16_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i16_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i16_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub i32 %19, %22                          ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub i32 %24, %25                          ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = mul i32 %29, 2                            ; <i32> [#uses=1]
  %31 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_i16_s* %31, i32 0, i32 1 ; <i16**> [#uses=1]
  %33 = load i16** %32, align 4                   ; <i16*> [#uses=1]
  %34 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i16* %33, i32 %36  ; <i16*> [#uses=1]
  %38 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i16_s* %38, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %40 = getelementptr inbounds [1024 x i16]* %39, i32 0, i32 0 ; <i16*> [#uses=1]
  %41 = bitcast i16* %40 to i8*                   ; <i8*> [#uses=1]
  %42 = bitcast i16* %37 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %41, i8* %42, i32 %30, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %43 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %44 = icmp ne i32 %43, 0                        ; <i1> [#uses=1]
  br i1 %44, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %46 = mul i32 %45, 2                            ; <i32> [#uses=1]
  %47 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i16_s* %47, i32 0, i32 1 ; <i16**> [#uses=1]
  %49 = load i16** %48, align 4                   ; <i16*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i16_s* %51, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i16]* %52, i32 0, i32 %50 ; <i16*> [#uses=1]
  %54 = bitcast i16* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i16* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_i16_s* %56, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %58 = getelementptr inbounds [1024 x i16]* %57, i32 0, i32 0 ; <i16*> [#uses=1]
  store i16* %58, i16** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %59 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %59, i16** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval7
}

define internal i16* @fifo_u_i16_read(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i16* @fifo_u_i16_peek(%struct.fifo_i16_s* %1, i32 %2) nounwind ; <i16*> [#uses=1]
  store i16* %3, i16** %0, align 4
  %4 = load i16** %0, align 4                     ; <i16*> [#uses=1]
  store i16* %4, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_u_i16_read_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i16_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i16_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i16_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub i32 %27, %30                          ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i16_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i16* @fifo_u_i16_write(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i16_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i16_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i16_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %19 = getelementptr inbounds [1024 x i16]* %18, i32 0, i32 0 ; <i16*> [#uses=1]
  store i16* %19, i16** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %20, i16** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval3
}

define internal void @fifo_u_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=15]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i16_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i16_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 0 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %27 = getelementptr inbounds %struct.fifo_i16_s* %26, i32 0, i32 3 ; <i32*> [#uses=1]
  %28 = load i32* %27, align 4                    ; <i32> [#uses=1]
  %29 = sub i32 %25, %28                          ; <i32> [#uses=1]
  store i32 %29, i32* %num_end, align 4
  %30 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %31 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %32 = sub i32 %30, %31                          ; <i32> [#uses=1]
  store i32 %32, i32* %num_beginning, align 4
  %33 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %34 = icmp ne i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %35 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %36 = mul i32 %35, 2                            ; <i32> [#uses=1]
  %37 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i16_s* %37, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i16]* %38, i32 0, i32 0 ; <i16*> [#uses=1]
  %40 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_i16_s* %40, i32 0, i32 1 ; <i16**> [#uses=1]
  %42 = load i16** %41, align 4                   ; <i16*> [#uses=1]
  %43 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i16_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %44, align 4                    ; <i32> [#uses=1]
  %46 = getelementptr inbounds i16* %42, i32 %45  ; <i16*> [#uses=1]
  %47 = bitcast i16* %46 to i8*                   ; <i8*> [#uses=1]
  %48 = bitcast i16* %39 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %47, i8* %48, i32 %36, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %50 = icmp ne i32 %49, 0                        ; <i1> [#uses=1]
  br i1 %50, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %51 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %52 = mul i32 %51, 2                            ; <i32> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i16_s* %54, i32 0, i32 5 ; <[1024 x i16]*> [#uses=1]
  %56 = getelementptr inbounds [1024 x i16]* %55, i32 0, i32 %53 ; <i16*> [#uses=1]
  %57 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i16_s* %57, i32 0, i32 1 ; <i16**> [#uses=1]
  %59 = load i16** %58, align 4                   ; <i16*> [#uses=1]
  %60 = bitcast i16* %59 to i8*                   ; <i8*> [#uses=1]
  %61 = bitcast i16* %56 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %60, i8* %61, i32 %52, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %62 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %63 = getelementptr inbounds %struct.fifo_i16_s* %62, i32 0, i32 3 ; <i32*> [#uses=1]
  %64 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %64, i32* %63, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  br label %return

return:                                           ; preds = %bb6
  ret void
}

define internal i32 @fifo_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = icmp sge i32 %3, %4                        ; <i1> [#uses=1]
  %6 = zext i1 %5 to i32                          ; <i32> [#uses=1]
  store i32 %6, i32* %0, align 4
  %7 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %7, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32 @fifo_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i32_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  %8 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %9 = icmp sge i32 %7, %8                        ; <i1> [#uses=1]
  %10 = zext i1 %9 to i32                         ; <i32> [#uses=1]
  store i32 %10, i32* %0, align 4
  %11 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %11, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32* @fifo_i32_peek(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i32_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i32_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i32_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i32_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub i32 %19, %22                          ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub i32 %24, %25                          ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = mul i32 %29, 4                            ; <i32> [#uses=1]
  %31 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_i32_s* %31, i32 0, i32 1 ; <i32**> [#uses=1]
  %33 = load i32** %32, align 4                   ; <i32*> [#uses=1]
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i32* %33, i32 %36  ; <i32*> [#uses=1]
  %38 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i32_s* %38, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %40 = getelementptr inbounds [1024 x i32]* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = bitcast i32* %40 to i8*                   ; <i8*> [#uses=1]
  %42 = bitcast i32* %37 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %41, i8* %42, i32 %30, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %43 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %44 = icmp ne i32 %43, 0                        ; <i1> [#uses=1]
  br i1 %44, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %46 = mul i32 %45, 4                            ; <i32> [#uses=1]
  %47 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i32_s* %47, i32 0, i32 1 ; <i32**> [#uses=1]
  %49 = load i32** %48, align 4                   ; <i32*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i32_s* %51, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i32]* %52, i32 0, i32 %50 ; <i32*> [#uses=1]
  %54 = bitcast i32* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i32* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_i32_s* %56, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %58 = getelementptr inbounds [1024 x i32]* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  store i32* %58, i32** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %59 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %59, i32** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval7
}

define internal i32* @fifo_i32_read(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i32* @fifo_i32_peek(%struct.fifo_i32_s* %1, i32 %2) nounwind ; <i32*> [#uses=1]
  store i32* %3, i32** %0, align 4
  %4 = load i32** %0, align 4                     ; <i32*> [#uses=1]
  store i32* %4, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_i32_read_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i32_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i32_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub i32 %27, %30                          ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i32_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i32* @fifo_i32_write(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i32_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i32_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i32_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %19 = getelementptr inbounds [1024 x i32]* %18, i32 0, i32 0 ; <i32*> [#uses=1]
  store i32* %19, i32** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %20, i32** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval3
}

define internal void @fifo_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=15]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i32_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i32_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 0 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %27 = getelementptr inbounds %struct.fifo_i32_s* %26, i32 0, i32 3 ; <i32*> [#uses=1]
  %28 = load i32* %27, align 4                    ; <i32> [#uses=1]
  %29 = sub i32 %25, %28                          ; <i32> [#uses=1]
  store i32 %29, i32* %num_end, align 4
  %30 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %31 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %32 = sub i32 %30, %31                          ; <i32> [#uses=1]
  store i32 %32, i32* %num_beginning, align 4
  %33 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %34 = icmp ne i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %35 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %36 = mul i32 %35, 4                            ; <i32> [#uses=1]
  %37 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i32_s* %37, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i32]* %38, i32 0, i32 0 ; <i32*> [#uses=1]
  %40 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_i32_s* %40, i32 0, i32 1 ; <i32**> [#uses=1]
  %42 = load i32** %41, align 4                   ; <i32*> [#uses=1]
  %43 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i32_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %44, align 4                    ; <i32> [#uses=1]
  %46 = getelementptr inbounds i32* %42, i32 %45  ; <i32*> [#uses=1]
  %47 = bitcast i32* %46 to i8*                   ; <i8*> [#uses=1]
  %48 = bitcast i32* %39 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %47, i8* %48, i32 %36, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %50 = icmp ne i32 %49, 0                        ; <i1> [#uses=1]
  br i1 %50, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %51 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %52 = mul i32 %51, 4                            ; <i32> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i32_s* %54, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %56 = getelementptr inbounds [1024 x i32]* %55, i32 0, i32 %53 ; <i32*> [#uses=1]
  %57 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i32_s* %57, i32 0, i32 1 ; <i32**> [#uses=1]
  %59 = load i32** %58, align 4                   ; <i32*> [#uses=1]
  %60 = bitcast i32* %59 to i8*                   ; <i8*> [#uses=1]
  %61 = bitcast i32* %56 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %60, i8* %61, i32 %52, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %62 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %63 = getelementptr inbounds %struct.fifo_i32_s* %62, i32 0, i32 3 ; <i32*> [#uses=1]
  %64 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %64, i32* %63, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  br label %return

return:                                           ; preds = %bb6
  ret void
}

define internal i32 @fifo_u_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = icmp sge i32 %3, %4                        ; <i1> [#uses=1]
  %6 = zext i1 %5 to i32                          ; <i32> [#uses=1]
  store i32 %6, i32* %0, align 4
  %7 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %7, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32 @fifo_u_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i32_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  %8 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %9 = icmp sge i32 %7, %8                        ; <i1> [#uses=1]
  %10 = zext i1 %9 to i32                         ; <i32> [#uses=1]
  store i32 %10, i32* %0, align 4
  %11 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %11, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define internal i32* @fifo_u_i32_peek(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i32_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i32_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i32_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i32_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub i32 %19, %22                          ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub i32 %24, %25                          ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = mul i32 %29, 4                            ; <i32> [#uses=1]
  %31 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_i32_s* %31, i32 0, i32 1 ; <i32**> [#uses=1]
  %33 = load i32** %32, align 4                   ; <i32*> [#uses=1]
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i32* %33, i32 %36  ; <i32*> [#uses=1]
  %38 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i32_s* %38, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %40 = getelementptr inbounds [1024 x i32]* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = bitcast i32* %40 to i8*                   ; <i8*> [#uses=1]
  %42 = bitcast i32* %37 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %41, i8* %42, i32 %30, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %43 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %44 = icmp ne i32 %43, 0                        ; <i1> [#uses=1]
  br i1 %44, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %46 = mul i32 %45, 4                            ; <i32> [#uses=1]
  %47 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i32_s* %47, i32 0, i32 1 ; <i32**> [#uses=1]
  %49 = load i32** %48, align 4                   ; <i32*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i32_s* %51, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i32]* %52, i32 0, i32 %50 ; <i32*> [#uses=1]
  %54 = bitcast i32* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i32* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_i32_s* %56, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %58 = getelementptr inbounds [1024 x i32]* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  store i32* %58, i32** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %59 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %59, i32** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval7
}

define internal i32* @fifo_u_i32_read(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i32* @fifo_u_i32_peek(%struct.fifo_i32_s* %1, i32 %2) nounwind ; <i32*> [#uses=1]
  store i32* %3, i32** %0, align 4
  %4 = load i32** %0, align 4                     ; <i32*> [#uses=1]
  store i32* %4, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_u_i32_read_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i32_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i32_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub i32 %27, %30                          ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i32_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i32* @fifo_u_i32_write(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i32_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i32_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i32_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %19 = getelementptr inbounds [1024 x i32]* %18, i32 0, i32 0 ; <i32*> [#uses=1]
  store i32* %19, i32** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %20, i32** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval3
}

define internal void @fifo_u_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=15]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i32_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i32_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 0 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %27 = getelementptr inbounds %struct.fifo_i32_s* %26, i32 0, i32 3 ; <i32*> [#uses=1]
  %28 = load i32* %27, align 4                    ; <i32> [#uses=1]
  %29 = sub i32 %25, %28                          ; <i32> [#uses=1]
  store i32 %29, i32* %num_end, align 4
  %30 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %31 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %32 = sub i32 %30, %31                          ; <i32> [#uses=1]
  store i32 %32, i32* %num_beginning, align 4
  %33 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %34 = icmp ne i32 %33, 0                        ; <i1> [#uses=1]
  br i1 %34, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %35 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %36 = mul i32 %35, 4                            ; <i32> [#uses=1]
  %37 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i32_s* %37, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i32]* %38, i32 0, i32 0 ; <i32*> [#uses=1]
  %40 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_i32_s* %40, i32 0, i32 1 ; <i32**> [#uses=1]
  %42 = load i32** %41, align 4                   ; <i32*> [#uses=1]
  %43 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i32_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %44, align 4                    ; <i32> [#uses=1]
  %46 = getelementptr inbounds i32* %42, i32 %45  ; <i32*> [#uses=1]
  %47 = bitcast i32* %46 to i8*                   ; <i8*> [#uses=1]
  %48 = bitcast i32* %39 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %47, i8* %48, i32 %36, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %50 = icmp ne i32 %49, 0                        ; <i1> [#uses=1]
  br i1 %50, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %51 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %52 = mul i32 %51, 4                            ; <i32> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i32_s* %54, i32 0, i32 5 ; <[1024 x i32]*> [#uses=1]
  %56 = getelementptr inbounds [1024 x i32]* %55, i32 0, i32 %53 ; <i32*> [#uses=1]
  %57 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i32_s* %57, i32 0, i32 1 ; <i32**> [#uses=1]
  %59 = load i32** %58, align 4                   ; <i32*> [#uses=1]
  %60 = bitcast i32* %59 to i8*                   ; <i8*> [#uses=1]
  %61 = bitcast i32* %56 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %60, i8* %61, i32 %52, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %62 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %63 = getelementptr inbounds %struct.fifo_i32_s* %62, i32 0, i32 3 ; <i32*> [#uses=1]
  %64 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %64, i32* %63, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  br label %return

return:                                           ; preds = %bb6
  ret void
}
