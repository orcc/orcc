; ModuleID = 'fifo.c'
target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32-n8:16:32"
target triple = "i386-mingw32"

%struct.fifo_i8_s = type { i32, i32, i8*, i32, i32 }
%struct.fifo_i32_s = type { i32, i32, i8*, i32, i32 }
%struct.fifo_i16_s = type { i32, i32, i8*, i32, i32 }
declare i32 @printf(i8* noalias , ...) nounwind 

define internal i32 @fifo_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 4
  %18 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_i8_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = sub nsw i32 %16, %19                      ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 4                    ; <i32> [#uses=1]
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
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 4                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_i8_s* %35, i32 0, i32 2 ; <i8**> [#uses=1]
  %37 = load i8** %36, align 4                    ; <i8*> [#uses=1]
  %38 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i8_s* %38, i32 0, i32 3 ; <i32*> [#uses=1]
  %40 = load i32* %39, align 4                    ; <i32> [#uses=1]
  %41 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_i8_s* %41, i32 0, i32 0 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = mul nsw i32 %40, %43                      ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %37, i32 %44   ; <i8*> [#uses=1]
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %48, i8* %45, i32 %34, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %50 = getelementptr inbounds %struct.fifo_i8_s* %49, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %50, align 4
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 4 ; <i32*> [#uses=1]
  %53 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %53, i32* %52, align 4
  %54 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i8_s* %54, i32 0, i32 1 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %58 = sub nsw i32 %56, %57                      ; <i32> [#uses=1]
  %59 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %60 = icmp sge i32 %58, %59                     ; <i1> [#uses=1]
  %61 = zext i1 %60 to i32                        ; <i32> [#uses=1]
  store i32 %61, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %62 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %62, i32* %0, align 4
  %63 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %63, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

declare void @llvm.memcpy.i32(i8* nocapture, i8* nocapture, i32, i32) nounwind

define internal i8* @fifo_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 4                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = mul nsw i32 %6, %9                        ; <i32> [#uses=1]
  %11 = getelementptr inbounds i8* %3, i32 %10    ; <i8*> [#uses=1]
  store i8* %11, i8** %0, align 4
  %12 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %12, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal i8* @fifo_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  store i8* %19, i8** %0, align 4
  %20 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %20, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i8* @fifo_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  store i8* %19, i8** %0, align 4
  %20 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %20, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32 @fifo_u_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 4
  %18 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_i8_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_u_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = sub nsw i32 %16, %19                      ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 4                    ; <i32> [#uses=1]
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
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 4                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_i8_s* %35, i32 0, i32 2 ; <i8**> [#uses=1]
  %37 = load i8** %36, align 4                    ; <i8*> [#uses=1]
  %38 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i8_s* %38, i32 0, i32 3 ; <i32*> [#uses=1]
  %40 = load i32* %39, align 4                    ; <i32> [#uses=1]
  %41 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_i8_s* %41, i32 0, i32 0 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = mul nsw i32 %40, %43                      ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %37, i32 %44   ; <i8*> [#uses=1]
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %48, i8* %45, i32 %34, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %50 = getelementptr inbounds %struct.fifo_i8_s* %49, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %50, align 4
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 4 ; <i32*> [#uses=1]
  %53 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %53, i32* %52, align 4
  %54 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i8_s* %54, i32 0, i32 1 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %58 = sub nsw i32 %56, %57                      ; <i32> [#uses=1]
  %59 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %60 = icmp sge i32 %58, %59                     ; <i1> [#uses=1]
  %61 = zext i1 %60 to i32                        ; <i32> [#uses=1]
  store i32 %61, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %62 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %62, i32* %0, align 4
  %63 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %63, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

define internal i8* @fifo_u_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 4                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = mul nsw i32 %6, %9                        ; <i32> [#uses=1]
  %11 = getelementptr inbounds i8* %3, i32 %10    ; <i8*> [#uses=1]
  store i8* %11, i8** %0, align 4
  %12 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %12, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal i8* @fifo_u_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  store i8* %19, i8** %0, align 4
  %20 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %20, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_u_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i8* @fifo_u_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  store i8* %19, i8** %0, align 4
  %20 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %20, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_u_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32 @fifo_i16_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 4
  %18 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_i8_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_i16_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = sub nsw i32 %16, %19                      ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 4                    ; <i32> [#uses=1]
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
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 4                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_i8_s* %35, i32 0, i32 2 ; <i8**> [#uses=1]
  %37 = load i8** %36, align 4                    ; <i8*> [#uses=1]
  %38 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i8_s* %38, i32 0, i32 3 ; <i32*> [#uses=1]
  %40 = load i32* %39, align 4                    ; <i32> [#uses=1]
  %41 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_i8_s* %41, i32 0, i32 0 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = mul nsw i32 %40, %43                      ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %37, i32 %44   ; <i8*> [#uses=1]
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %48, i8* %45, i32 %34, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %50 = getelementptr inbounds %struct.fifo_i8_s* %49, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %50, align 4
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 4 ; <i32*> [#uses=1]
  %53 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %53, i32* %52, align 4
  %54 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i8_s* %54, i32 0, i32 1 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %58 = sub nsw i32 %56, %57                      ; <i32> [#uses=1]
  %59 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %60 = icmp sge i32 %58, %59                     ; <i1> [#uses=1]
  %61 = zext i1 %60 to i32                        ; <i32> [#uses=1]
  store i32 %61, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %62 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %62, i32* %0, align 4
  %63 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %63, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

define internal i16* @fifo_i16_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 4                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = mul nsw i32 %6, %9                        ; <i32> [#uses=1]
  %11 = getelementptr inbounds i8* %3, i32 %10    ; <i8*> [#uses=1]
  %12 = bitcast i8* %11 to i16*                   ; <i16*> [#uses=1]
  store i16* %12, i16** %0, align 4
  %13 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %13, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal i16* @fifo_i16_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i16*                   ; <i16*> [#uses=1]
  store i16* %20, i16** %0, align 4
  %21 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %21, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_i16_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i16* @fifo_i16_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i16*                   ; <i16*> [#uses=1]
  store i16* %20, i16** %0, align 4
  %21 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %21, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_i16_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32 @fifo_u_i16_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 4
  %18 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_i8_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_u_i16_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = sub nsw i32 %16, %19                      ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 4                    ; <i32> [#uses=1]
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
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 4                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_i8_s* %35, i32 0, i32 2 ; <i8**> [#uses=1]
  %37 = load i8** %36, align 4                    ; <i8*> [#uses=1]
  %38 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i8_s* %38, i32 0, i32 3 ; <i32*> [#uses=1]
  %40 = load i32* %39, align 4                    ; <i32> [#uses=1]
  %41 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_i8_s* %41, i32 0, i32 0 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = mul nsw i32 %40, %43                      ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %37, i32 %44   ; <i8*> [#uses=1]
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %48, i8* %45, i32 %34, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %50 = getelementptr inbounds %struct.fifo_i8_s* %49, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %50, align 4
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 4 ; <i32*> [#uses=1]
  %53 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %53, i32* %52, align 4
  %54 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i8_s* %54, i32 0, i32 1 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %58 = sub nsw i32 %56, %57                      ; <i32> [#uses=1]
  %59 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %60 = icmp sge i32 %58, %59                     ; <i1> [#uses=1]
  %61 = zext i1 %60 to i32                        ; <i32> [#uses=1]
  store i32 %61, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %62 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %62, i32* %0, align 4
  %63 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %63, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

define internal i16* @fifo_u_i16_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 4                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = mul nsw i32 %6, %9                        ; <i32> [#uses=1]
  %11 = getelementptr inbounds i8* %3, i32 %10    ; <i8*> [#uses=1]
  %12 = bitcast i8* %11 to i16*                   ; <i16*> [#uses=1]
  store i16* %12, i16** %0, align 4
  %13 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %13, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal i16* @fifo_u_i16_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i16*                   ; <i16*> [#uses=1]
  store i16* %20, i16** %0, align 4
  %21 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %21, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_u_i16_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i16* @fifo_u_i16_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i16*                   ; <i16*> [#uses=1]
  store i16* %20, i16** %0, align 4
  %21 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %21, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_u_i16_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32 @fifo_i32_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 4
  %18 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_i8_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_i32_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = sub nsw i32 %16, %19                      ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 4                    ; <i32> [#uses=1]
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
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 4                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_i8_s* %35, i32 0, i32 2 ; <i8**> [#uses=1]
  %37 = load i8** %36, align 4                    ; <i8*> [#uses=1]
  %38 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i8_s* %38, i32 0, i32 3 ; <i32*> [#uses=1]
  %40 = load i32* %39, align 4                    ; <i32> [#uses=1]
  %41 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_i8_s* %41, i32 0, i32 0 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = mul nsw i32 %40, %43                      ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %37, i32 %44   ; <i8*> [#uses=1]
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %48, i8* %45, i32 %34, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %50 = getelementptr inbounds %struct.fifo_i8_s* %49, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %50, align 4
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 4 ; <i32*> [#uses=1]
  %53 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %53, i32* %52, align 4
  %54 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i8_s* %54, i32 0, i32 1 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %58 = sub nsw i32 %56, %57                      ; <i32> [#uses=1]
  %59 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %60 = icmp sge i32 %58, %59                     ; <i1> [#uses=1]
  %61 = zext i1 %60 to i32                        ; <i32> [#uses=1]
  store i32 %61, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %62 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %62, i32* %0, align 4
  %63 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %63, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

define internal i32* @fifo_i32_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 4                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = mul nsw i32 %6, %9                        ; <i32> [#uses=1]
  %11 = getelementptr inbounds i8* %3, i32 %10    ; <i8*> [#uses=1]
  %12 = bitcast i8* %11 to i32*                   ; <i32*> [#uses=1]
  store i32* %12, i32** %0, align 4
  %13 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %13, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal i32* @fifo_i32_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i32*                   ; <i32*> [#uses=1]
  store i32* %20, i32** %0, align 4
  %21 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %21, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_i32_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32* @fifo_i32_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i32*                   ; <i32*> [#uses=1]
  store i32* %20, i32** %0, align 4
  %21 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %21, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_i32_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32 @fifo_u_i32_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=5]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_tokens = alloca i32                        ; <i32*> [#uses=3]
  %res = alloca i32                               ; <i32*> [#uses=3]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %17, align 4
  %18 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %19 = getelementptr inbounds %struct.fifo_i8_s* %18, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_u_i32_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=14]
  %n_addr = alloca i32                            ; <i32*> [#uses=4]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %num_free = alloca i32                          ; <i32*> [#uses=2]
  %res = alloca i32                               ; <i32*> [#uses=4]
  %num_tokens = alloca i32                        ; <i32*> [#uses=6]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 1 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 4 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 3 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = sub nsw i32 %16, %19                      ; <i32> [#uses=1]
  store i32 %20, i32* %num_tokens, align 4
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  %23 = load i32* %22, align 4                    ; <i32> [#uses=1]
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
  %30 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_i8_s* %30, i32 0, i32 0 ; <i32*> [#uses=1]
  %32 = load i32* %31, align 4                    ; <i32> [#uses=1]
  %33 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %34 = mul nsw i32 %32, %33                      ; <i32> [#uses=1]
  %35 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_i8_s* %35, i32 0, i32 2 ; <i8**> [#uses=1]
  %37 = load i8** %36, align 4                    ; <i8*> [#uses=1]
  %38 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i8_s* %38, i32 0, i32 3 ; <i32*> [#uses=1]
  %40 = load i32* %39, align 4                    ; <i32> [#uses=1]
  %41 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_i8_s* %41, i32 0, i32 0 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = mul nsw i32 %40, %43                      ; <i32> [#uses=1]
  %45 = getelementptr inbounds i8* %37, i32 %44   ; <i8*> [#uses=1]
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %48, i8* %45, i32 %34, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %49 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %50 = getelementptr inbounds %struct.fifo_i8_s* %49, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %50, align 4
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 4 ; <i32*> [#uses=1]
  %53 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  store i32 %53, i32* %52, align 4
  %54 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i8_s* %54, i32 0, i32 1 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = load i32* %num_tokens, align 4            ; <i32> [#uses=1]
  %58 = sub nsw i32 %56, %57                      ; <i32> [#uses=1]
  %59 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %60 = icmp sge i32 %58, %59                     ; <i1> [#uses=1]
  %61 = zext i1 %60 to i32                        ; <i32> [#uses=1]
  store i32 %61, i32* %res, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb, %entry
  %62 = load i32* %res, align 4                   ; <i32> [#uses=1]
  store i32 %62, i32* %0, align 4
  %63 = load i32* %0, align 4                     ; <i32> [#uses=1]
  store i32 %63, i32* %retval, align 4
  br label %return

return:                                           ; preds = %bb4
  %retval5 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval5
}

define internal i32* @fifo_u_i32_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 2 ; <i8**> [#uses=1]
  %3 = load i8** %2, align 4                      ; <i8*> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 0 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = mul nsw i32 %6, %9                        ; <i32> [#uses=1]
  %11 = getelementptr inbounds i8* %3, i32 %10    ; <i8*> [#uses=1]
  %12 = bitcast i8* %11 to i32*                   ; <i32*> [#uses=1]
  store i32* %12, i32** %0, align 4
  %13 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %13, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal i32* @fifo_u_i32_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 3 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i32*                   ; <i32*> [#uses=1]
  store i32* %20, i32** %0, align 4
  %21 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %21, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_u_i32_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal i32* @fifo_u_i32_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %ptr = alloca i32                               ; <i32*> [#uses=2]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  store i32 %3, i32* %ptr, align 4
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %8 = add nsw i32 %6, %7                         ; <i32> [#uses=1]
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %8, i32* %10, align 4
  %11 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %12 = getelementptr inbounds %struct.fifo_i8_s* %11, i32 0, i32 2 ; <i8**> [#uses=1]
  %13 = load i8** %12, align 4                    ; <i8*> [#uses=1]
  %14 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %15 = getelementptr inbounds %struct.fifo_i8_s* %14, i32 0, i32 0 ; <i32*> [#uses=1]
  %16 = load i32* %15, align 4                    ; <i32> [#uses=1]
  %17 = load i32* %ptr, align 4                   ; <i32> [#uses=1]
  %18 = mul nsw i32 %16, %17                      ; <i32> [#uses=1]
  %19 = getelementptr inbounds i8* %13, i32 %18   ; <i8*> [#uses=1]
  %20 = bitcast i8* %19 to i32*                   ; <i32*> [#uses=1]
  store i32* %20, i32** %0, align 4
  %21 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %21, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_u_i32_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=1]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %"alloca poi32" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}
