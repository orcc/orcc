%struct.fifo_s = type { i32, i32, i8*, i32, i32 }

define internal i8* @getPeekPtr(%struct.fifo_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=4]
  %n_addr = alloca i32                            ; <i32*> [#uses=1]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}

define internal void @setReadEnd(%struct.fifo_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_s*             ; <%struct.fifo_s**> [#uses=1]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_s* %fifo, %struct.fifo_s** %fifo_addr
  br label %return

return:                                           ; preds = %entry
  ret void
}