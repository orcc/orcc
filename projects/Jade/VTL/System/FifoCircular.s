; ModuleID = 'orcc_fifo.c'
target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32-n8:16:32"
target triple = "i386-mingw32"

%struct.fifo_i16_s = type { i32, i16*, i16*, i32, i32, i32 }
%struct.fifo_i32_s = type { i32, i32*, i32*, i32, i32, i32 }
%struct.fifo_i64_s = type { i32, i64*, i64*, i32, i32, i32 }
%struct.fifo_i8_s = type { i32, i8*, i8*, i32, i32, i32 }

define i32 @fifo_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_i8_get_room(%struct.fifo_i8_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define  i8* @fifo_i8_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i8_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
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
  %34 = getelementptr inbounds %struct.fifo_i8_s* %33, i32 0, i32 3 ; <i32*> [#uses=1]
  %35 = load i32* %34, align 4                    ; <i32> [#uses=1]
  %36 = getelementptr inbounds i8* %32, i32 %35   ; <i8*> [#uses=1]
  %37 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i8_s* %37, i32 0, i32 2 ; <i8**> [#uses=1]
  %39 = load i8** %38, align 4                    ; <i8*> [#uses=1]
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
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  %49 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %50 = getelementptr inbounds i8* %48, i32 %49   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %50, i8* %45, i32 %42, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 2 ; <i8**> [#uses=1]
  %53 = load i8** %52, align 4                    ; <i8*> [#uses=1]
  store i8* %53, i8** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %54 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %54, i8** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval7
}

declare void @llvm.memcpy.i32(i8* nocapture, i8* nocapture, i32, i32) nounwind

define  i8* @fifo_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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

define  void @fifo_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
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
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i8_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i8_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i8_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i8_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i8_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define  i8* @fifo_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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
  %14 = getelementptr inbounds %struct.fifo_i8_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 2 ; <i8**> [#uses=1]
  %19 = load i8** %18, align 4                    ; <i8*> [#uses=1]
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

define  void @fifo_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i8_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i8_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i8_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i8_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i8_s* %47, i32 0, i32 2 ; <i8**> [#uses=1]
  %49 = load i8** %48, align 4                    ; <i8*> [#uses=1]
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 1 ; <i8**> [#uses=1]
  %52 = load i8** %51, align 4                    ; <i8*> [#uses=1]
  %53 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %54 = getelementptr inbounds %struct.fifo_i8_s* %53, i32 0, i32 4 ; <i32*> [#uses=1]
  %55 = load i32* %54, align 4                    ; <i32> [#uses=1]
  %56 = getelementptr inbounds i8* %52, i32 %55   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %56, i8* %49, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %58 = icmp ne i32 %57, 0                        ; <i1> [#uses=1]
  br i1 %58, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %59 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %60 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %61 = getelementptr inbounds %struct.fifo_i8_s* %60, i32 0, i32 2 ; <i8**> [#uses=1]
  %62 = load i8** %61, align 4                    ; <i8*> [#uses=1]
  %63 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %64 = getelementptr inbounds i8* %62, i32 %63   ; <i8*> [#uses=1]
  %65 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %66 = getelementptr inbounds %struct.fifo_i8_s* %65, i32 0, i32 1 ; <i8**> [#uses=1]
  %67 = load i8** %66, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %67, i8* %64, i32 %59, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %68 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %69 = getelementptr inbounds %struct.fifo_i8_s* %68, i32 0, i32 4 ; <i32*> [#uses=1]
  %70 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %70, i32* %69, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i16_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_i16_get_room(%struct.fifo_i16_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i16_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define  i16* @fifo_i16_peek(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i16_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
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
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i16* %33, i32 %36  ; <i16*> [#uses=1]
  %38 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i16_s* %38, i32 0, i32 2 ; <i16**> [#uses=1]
  %40 = load i16** %39, align 4                   ; <i16*> [#uses=1]
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
  %50 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i16_s* %50, i32 0, i32 2 ; <i16**> [#uses=1]
  %52 = load i16** %51, align 4                   ; <i16*> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = getelementptr inbounds i16* %52, i32 %53  ; <i16*> [#uses=1]
  %55 = bitcast i16* %54 to i8*                   ; <i8*> [#uses=1]
  %56 = bitcast i16* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %56, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i16_s* %57, i32 0, i32 2 ; <i16**> [#uses=1]
  %59 = load i16** %58, align 4                   ; <i16*> [#uses=1]
  store i16* %59, i16** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %60 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %60, i16** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval7
}

define  i16* @fifo_i16_read(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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

define  void @fifo_i16_read_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
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
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i16_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i16_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i16_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i16_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define  i16* @fifo_i16_write(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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
  %14 = getelementptr inbounds %struct.fifo_i16_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 2 ; <i16**> [#uses=1]
  %19 = load i16** %18, align 4                   ; <i16*> [#uses=1]
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

define  void @fifo_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i16_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i16_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i16_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i16_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i16_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = mul i32 %46, 2                            ; <i32> [#uses=1]
  %48 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i16_s* %48, i32 0, i32 2 ; <i16**> [#uses=1]
  %50 = load i16** %49, align 4                   ; <i16*> [#uses=1]
  %51 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i16_s* %51, i32 0, i32 1 ; <i16**> [#uses=1]
  %53 = load i16** %52, align 4                   ; <i16*> [#uses=1]
  %54 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i16_s* %54, i32 0, i32 4 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = getelementptr inbounds i16* %53, i32 %56  ; <i16*> [#uses=1]
  %58 = bitcast i16* %57 to i8*                   ; <i8*> [#uses=1]
  %59 = bitcast i16* %50 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %58, i8* %59, i32 %47, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %60 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %61 = icmp ne i32 %60, 0                        ; <i1> [#uses=1]
  br i1 %61, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %62 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %63 = mul i32 %62, 2                            ; <i32> [#uses=1]
  %64 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i16_s* %64, i32 0, i32 2 ; <i16**> [#uses=1]
  %66 = load i16** %65, align 4                   ; <i16*> [#uses=1]
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = getelementptr inbounds i16* %66, i32 %67  ; <i16*> [#uses=1]
  %69 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %70 = getelementptr inbounds %struct.fifo_i16_s* %69, i32 0, i32 1 ; <i16**> [#uses=1]
  %71 = load i16** %70, align 4                   ; <i16*> [#uses=1]
  %72 = bitcast i16* %71 to i8*                   ; <i8*> [#uses=1]
  %73 = bitcast i16* %68 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %72, i8* %73, i32 %63, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %74 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_i16_s* %74, i32 0, i32 4 ; <i32*> [#uses=1]
  %76 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %76, i32* %75, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i32_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_i32_get_room(%struct.fifo_i32_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i32_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define i32* @fifo_i32_peek(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i32_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
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
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i32* %33, i32 %36  ; <i32*> [#uses=1]
  %38 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i32_s* %38, i32 0, i32 2 ; <i32**> [#uses=1]
  %40 = load i32** %39, align 4                   ; <i32*> [#uses=1]
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
  %50 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i32_s* %50, i32 0, i32 2 ; <i32**> [#uses=1]
  %52 = load i32** %51, align 4                   ; <i32*> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = getelementptr inbounds i32* %52, i32 %53  ; <i32*> [#uses=1]
  %55 = bitcast i32* %54 to i8*                   ; <i8*> [#uses=1]
  %56 = bitcast i32* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %56, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i32_s* %57, i32 0, i32 2 ; <i32**> [#uses=1]
  %59 = load i32** %58, align 4                   ; <i32*> [#uses=1]
  store i32* %59, i32** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %60 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %60, i32** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval7
}

define i32* @fifo_i32_read(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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

define  void @fifo_i32_read_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
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
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i32_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i32_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i32_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define i32* @fifo_i32_write(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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
  %14 = getelementptr inbounds %struct.fifo_i32_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 2 ; <i32**> [#uses=1]
  %19 = load i32** %18, align 4                   ; <i32*> [#uses=1]
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

define  void @fifo_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i32_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i32_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i32_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i32_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = mul i32 %46, 4                            ; <i32> [#uses=1]
  %48 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i32_s* %48, i32 0, i32 2 ; <i32**> [#uses=1]
  %50 = load i32** %49, align 4                   ; <i32*> [#uses=1]
  %51 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i32_s* %51, i32 0, i32 1 ; <i32**> [#uses=1]
  %53 = load i32** %52, align 4                   ; <i32*> [#uses=1]
  %54 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i32_s* %54, i32 0, i32 4 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = getelementptr inbounds i32* %53, i32 %56  ; <i32*> [#uses=1]
  %58 = bitcast i32* %57 to i8*                   ; <i8*> [#uses=1]
  %59 = bitcast i32* %50 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %58, i8* %59, i32 %47, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %60 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %61 = icmp ne i32 %60, 0                        ; <i1> [#uses=1]
  br i1 %61, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %62 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %63 = mul i32 %62, 4                            ; <i32> [#uses=1]
  %64 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i32_s* %64, i32 0, i32 2 ; <i32**> [#uses=1]
  %66 = load i32** %65, align 4                   ; <i32*> [#uses=1]
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = getelementptr inbounds i32* %66, i32 %67  ; <i32*> [#uses=1]
  %69 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %70 = getelementptr inbounds %struct.fifo_i32_s* %69, i32 0, i32 1 ; <i32**> [#uses=1]
  %71 = load i32** %70, align 4                   ; <i32*> [#uses=1]
  %72 = bitcast i32* %71 to i8*                   ; <i8*> [#uses=1]
  %73 = bitcast i32* %68 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %72, i8* %73, i32 %63, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %74 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_i32_s* %74, i32 0, i32 4 ; <i32*> [#uses=1]
  %76 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %76, i32* %75, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_i64_has_tokens(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_i64_has_room(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i64_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_i64_get_room(%struct.fifo_i64_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i64_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define  i64* @fifo_i64_peek(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i64*                           ; <i64**> [#uses=2]
  %0 = alloca i64*                                ; <i64**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i64_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i64_s* %10, i32 0, i32 1 ; <i64**> [#uses=1]
  %12 = load i64** %11, align 4                   ; <i64*> [#uses=1]
  %13 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i64_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i64* %12, i32 %15  ; <i64*> [#uses=1]
  store i64* %16, i64** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i64_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i64_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = mul i32 %29, 8                            ; <i32> [#uses=1]
  %31 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_i64_s* %31, i32 0, i32 1 ; <i64**> [#uses=1]
  %33 = load i64** %32, align 4                   ; <i64*> [#uses=1]
  %34 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i64_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i64* %33, i32 %36  ; <i64*> [#uses=1]
  %38 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i64_s* %38, i32 0, i32 2 ; <i64**> [#uses=1]
  %40 = load i64** %39, align 4                   ; <i64*> [#uses=1]
  %41 = bitcast i64* %40 to i8*                   ; <i8*> [#uses=1]
  %42 = bitcast i64* %37 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %41, i8* %42, i32 %30, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %43 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %44 = icmp ne i32 %43, 0                        ; <i1> [#uses=1]
  br i1 %44, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %46 = mul i32 %45, 8                            ; <i32> [#uses=1]
  %47 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i64_s* %47, i32 0, i32 1 ; <i64**> [#uses=1]
  %49 = load i64** %48, align 4                   ; <i64*> [#uses=1]
  %50 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i64_s* %50, i32 0, i32 2 ; <i64**> [#uses=1]
  %52 = load i64** %51, align 4                   ; <i64*> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = getelementptr inbounds i64* %52, i32 %53  ; <i64*> [#uses=1]
  %55 = bitcast i64* %54 to i8*                   ; <i8*> [#uses=1]
  %56 = bitcast i64* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %56, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i64_s* %57, i32 0, i32 2 ; <i64**> [#uses=1]
  %59 = load i64** %58, align 4                   ; <i64*> [#uses=1]
  store i64* %59, i64** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %60 = load i64** %0, align 4                    ; <i64*> [#uses=1]
  store i64* %60, i64** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i64** %retval                   ; <i64*> [#uses=1]
  ret i64* %retval7
}

define  i64* @fifo_i64_read(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i64*                           ; <i64**> [#uses=2]
  %0 = alloca i64*                                ; <i64**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i64* @fifo_i64_peek(%struct.fifo_i64_s* %1, i32 %2) nounwind ; <i64*> [#uses=1]
  store i64* %3, i64** %0, align 4
  %4 = load i64** %0, align 4                     ; <i64*> [#uses=1]
  store i64* %4, i64** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i64** %retval                   ; <i64*> [#uses=1]
  ret i64* %retval1
}

define  void @fifo_i64_read_end(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i64_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i64_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i64_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i64_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i64_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i64_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i64_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i64_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i64_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i64_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i64_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i64_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define  i64* @fifo_i64_write(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i64*                           ; <i64**> [#uses=2]
  %0 = alloca i64*                                ; <i64**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i64_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i64_s* %10, i32 0, i32 1 ; <i64**> [#uses=1]
  %12 = load i64** %11, align 4                   ; <i64*> [#uses=1]
  %13 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i64_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i64* %12, i32 %15  ; <i64*> [#uses=1]
  store i64* %16, i64** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i64_s* %17, i32 0, i32 2 ; <i64**> [#uses=1]
  %19 = load i64** %18, align 4                   ; <i64*> [#uses=1]
  store i64* %19, i64** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i64** %0, align 4                    ; <i64*> [#uses=1]
  store i64* %20, i64** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i64** %retval                   ; <i64*> [#uses=1]
  ret i64* %retval3
}

define  void @fifo_i64_write_end(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i64_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i64_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i64_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i64_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i64_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i64_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i64_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i64_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i64_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i64_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i64_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = mul i32 %46, 8                            ; <i32> [#uses=1]
  %48 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i64_s* %48, i32 0, i32 2 ; <i64**> [#uses=1]
  %50 = load i64** %49, align 4                   ; <i64*> [#uses=1]
  %51 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i64_s* %51, i32 0, i32 1 ; <i64**> [#uses=1]
  %53 = load i64** %52, align 4                   ; <i64*> [#uses=1]
  %54 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i64_s* %54, i32 0, i32 4 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = getelementptr inbounds i64* %53, i32 %56  ; <i64*> [#uses=1]
  %58 = bitcast i64* %57 to i8*                   ; <i8*> [#uses=1]
  %59 = bitcast i64* %50 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %58, i8* %59, i32 %47, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %60 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %61 = icmp ne i32 %60, 0                        ; <i1> [#uses=1]
  br i1 %61, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %62 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %63 = mul i32 %62, 8                            ; <i32> [#uses=1]
  %64 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i64_s* %64, i32 0, i32 2 ; <i64**> [#uses=1]
  %66 = load i64** %65, align 4                   ; <i64*> [#uses=1]
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = getelementptr inbounds i64* %66, i32 %67  ; <i64*> [#uses=1]
  %69 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %70 = getelementptr inbounds %struct.fifo_i64_s* %69, i32 0, i32 1 ; <i64**> [#uses=1]
  %71 = load i64** %70, align 4                   ; <i64*> [#uses=1]
  %72 = bitcast i64* %71 to i8*                   ; <i8*> [#uses=1]
  %73 = bitcast i64* %68 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %72, i8* %73, i32 %63, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %74 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_i64_s* %74, i32 0, i32 4 ; <i32*> [#uses=1]
  %76 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %76, i32* %75, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_u8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_u8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_u8_get_room(%struct.fifo_i8_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i8_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define  i8* @fifo_u8_peek(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i8_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
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
  %34 = getelementptr inbounds %struct.fifo_i8_s* %33, i32 0, i32 3 ; <i32*> [#uses=1]
  %35 = load i32* %34, align 4                    ; <i32> [#uses=1]
  %36 = getelementptr inbounds i8* %32, i32 %35   ; <i8*> [#uses=1]
  %37 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i8_s* %37, i32 0, i32 2 ; <i8**> [#uses=1]
  %39 = load i8** %38, align 4                    ; <i8*> [#uses=1]
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
  %46 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_i8_s* %46, i32 0, i32 2 ; <i8**> [#uses=1]
  %48 = load i8** %47, align 4                    ; <i8*> [#uses=1]
  %49 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %50 = getelementptr inbounds i8* %48, i32 %49   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %50, i8* %45, i32 %42, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %51 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i8_s* %51, i32 0, i32 2 ; <i8**> [#uses=1]
  %53 = load i8** %52, align 4                    ; <i8*> [#uses=1]
  store i8* %53, i8** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %54 = load i8** %0, align 4                     ; <i8*> [#uses=1]
  store i8* %54, i8** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval7
}

define  i8* @fifo_u8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i8* @fifo_u8_peek(%struct.fifo_i8_s* %1, i32 %2) nounwind ; <i8*> [#uses=1]
  store i8* %3, i8** %0, align 4
  %4 = load i8** %0, align 4                      ; <i8*> [#uses=1]
  store i8* %4, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define  void @fifo_u8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
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
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i8_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i8_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i8_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i8_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i8_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define  i8* @fifo_u8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i8_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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
  %14 = getelementptr inbounds %struct.fifo_i8_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i8_s* %17, i32 0, i32 2 ; <i8**> [#uses=1]
  %19 = load i8** %18, align 4                    ; <i8*> [#uses=1]
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

define  void @fifo_u8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*          ; <%struct.fifo_i8_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i8_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i8_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i8_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i8_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i8_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i8_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i8_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i8_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i8_s* %47, i32 0, i32 2 ; <i8**> [#uses=1]
  %49 = load i8** %48, align 4                    ; <i8*> [#uses=1]
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 1 ; <i8**> [#uses=1]
  %52 = load i8** %51, align 4                    ; <i8*> [#uses=1]
  %53 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %54 = getelementptr inbounds %struct.fifo_i8_s* %53, i32 0, i32 4 ; <i32*> [#uses=1]
  %55 = load i32* %54, align 4                    ; <i32> [#uses=1]
  %56 = getelementptr inbounds i8* %52, i32 %55   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %56, i8* %49, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %58 = icmp ne i32 %57, 0                        ; <i1> [#uses=1]
  br i1 %58, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %59 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %60 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %61 = getelementptr inbounds %struct.fifo_i8_s* %60, i32 0, i32 2 ; <i8**> [#uses=1]
  %62 = load i8** %61, align 4                    ; <i8*> [#uses=1]
  %63 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %64 = getelementptr inbounds i8* %62, i32 %63   ; <i8*> [#uses=1]
  %65 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %66 = getelementptr inbounds %struct.fifo_i8_s* %65, i32 0, i32 1 ; <i8**> [#uses=1]
  %67 = load i8** %66, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %67, i8* %64, i32 %59, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %68 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %69 = getelementptr inbounds %struct.fifo_i8_s* %68, i32 0, i32 4 ; <i32*> [#uses=1]
  %70 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %70, i32* %69, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_u16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_u16_has_room(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i16_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_u16_get_room(%struct.fifo_i16_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i16_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define  i16* @fifo_u16_peek(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i16_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
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
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i16* %33, i32 %36  ; <i16*> [#uses=1]
  %38 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i16_s* %38, i32 0, i32 2 ; <i16**> [#uses=1]
  %40 = load i16** %39, align 4                   ; <i16*> [#uses=1]
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
  %50 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i16_s* %50, i32 0, i32 2 ; <i16**> [#uses=1]
  %52 = load i16** %51, align 4                   ; <i16*> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = getelementptr inbounds i16* %52, i32 %53  ; <i16*> [#uses=1]
  %55 = bitcast i16* %54 to i8*                   ; <i8*> [#uses=1]
  %56 = bitcast i16* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %56, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i16_s* %57, i32 0, i32 2 ; <i16**> [#uses=1]
  %59 = load i16** %58, align 4                   ; <i16*> [#uses=1]
  store i16* %59, i16** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %60 = load i16** %0, align 4                    ; <i16*> [#uses=1]
  store i16* %60, i16** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval7
}

define  i16* @fifo_u16_read(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i16* @fifo_u16_peek(%struct.fifo_i16_s* %1, i32 %2) nounwind ; <i16*> [#uses=1]
  store i16* %3, i16** %0, align 4
  %4 = load i16** %0, align 4                     ; <i16*> [#uses=1]
  store i16* %4, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define  void @fifo_u16_read_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
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
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i16_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i16_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i16_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i16_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define  i16* @fifo_u16_write(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i16_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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
  %14 = getelementptr inbounds %struct.fifo_i16_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i16_s* %17, i32 0, i32 2 ; <i16**> [#uses=1]
  %19 = load i16** %18, align 4                   ; <i16*> [#uses=1]
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

define  void @fifo_u16_write_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*         ; <%struct.fifo_i16_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i16_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i16_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i16_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i16_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i16_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i16_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i16_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i16_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = mul i32 %46, 2                            ; <i32> [#uses=1]
  %48 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i16_s* %48, i32 0, i32 2 ; <i16**> [#uses=1]
  %50 = load i16** %49, align 4                   ; <i16*> [#uses=1]
  %51 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i16_s* %51, i32 0, i32 1 ; <i16**> [#uses=1]
  %53 = load i16** %52, align 4                   ; <i16*> [#uses=1]
  %54 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i16_s* %54, i32 0, i32 4 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = getelementptr inbounds i16* %53, i32 %56  ; <i16*> [#uses=1]
  %58 = bitcast i16* %57 to i8*                   ; <i8*> [#uses=1]
  %59 = bitcast i16* %50 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %58, i8* %59, i32 %47, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %60 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %61 = icmp ne i32 %60, 0                        ; <i1> [#uses=1]
  br i1 %61, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %62 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %63 = mul i32 %62, 2                            ; <i32> [#uses=1]
  %64 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i16_s* %64, i32 0, i32 2 ; <i16**> [#uses=1]
  %66 = load i16** %65, align 4                   ; <i16*> [#uses=1]
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = getelementptr inbounds i16* %66, i32 %67  ; <i16*> [#uses=1]
  %69 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %70 = getelementptr inbounds %struct.fifo_i16_s* %69, i32 0, i32 1 ; <i16**> [#uses=1]
  %71 = load i16** %70, align 4                   ; <i16*> [#uses=1]
  %72 = bitcast i16* %71 to i8*                   ; <i8*> [#uses=1]
  %73 = bitcast i16* %68 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %72, i8* %73, i32 %63, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %74 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_i16_s* %74, i32 0, i32 4 ; <i32*> [#uses=1]
  %76 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %76, i32* %75, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_u32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_u32_has_room(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i32_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_u32_get_room(%struct.fifo_i32_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i32_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define i32* @fifo_u32_peek(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i32_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
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
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i32* %33, i32 %36  ; <i32*> [#uses=1]
  %38 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i32_s* %38, i32 0, i32 2 ; <i32**> [#uses=1]
  %40 = load i32** %39, align 4                   ; <i32*> [#uses=1]
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
  %50 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i32_s* %50, i32 0, i32 2 ; <i32**> [#uses=1]
  %52 = load i32** %51, align 4                   ; <i32*> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = getelementptr inbounds i32* %52, i32 %53  ; <i32*> [#uses=1]
  %55 = bitcast i32* %54 to i8*                   ; <i8*> [#uses=1]
  %56 = bitcast i32* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %56, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i32_s* %57, i32 0, i32 2 ; <i32**> [#uses=1]
  %59 = load i32** %58, align 4                   ; <i32*> [#uses=1]
  store i32* %59, i32** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %60 = load i32** %0, align 4                    ; <i32*> [#uses=1]
  store i32* %60, i32** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval7
}

define i32* @fifo_u32_read(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i32* @fifo_u32_peek(%struct.fifo_i32_s* %1, i32 %2) nounwind ; <i32*> [#uses=1]
  store i32* %3, i32** %0, align 4
  %4 = load i32** %0, align 4                     ; <i32*> [#uses=1]
  store i32* %4, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define  void @fifo_u32_read_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
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
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i32_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i32_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i32_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define i32* @fifo_u32_write(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i32_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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
  %14 = getelementptr inbounds %struct.fifo_i32_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i32_s* %17, i32 0, i32 2 ; <i32**> [#uses=1]
  %19 = load i32** %18, align 4                   ; <i32*> [#uses=1]
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

define  void @fifo_u32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i32_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i32_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i32_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i32_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i32_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i32_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = mul i32 %46, 4                            ; <i32> [#uses=1]
  %48 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i32_s* %48, i32 0, i32 2 ; <i32**> [#uses=1]
  %50 = load i32** %49, align 4                   ; <i32*> [#uses=1]
  %51 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i32_s* %51, i32 0, i32 1 ; <i32**> [#uses=1]
  %53 = load i32** %52, align 4                   ; <i32*> [#uses=1]
  %54 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i32_s* %54, i32 0, i32 4 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = getelementptr inbounds i32* %53, i32 %56  ; <i32*> [#uses=1]
  %58 = bitcast i32* %57 to i8*                   ; <i8*> [#uses=1]
  %59 = bitcast i32* %50 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %58, i8* %59, i32 %47, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %60 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %61 = icmp ne i32 %60, 0                        ; <i1> [#uses=1]
  br i1 %61, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %62 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %63 = mul i32 %62, 4                            ; <i32> [#uses=1]
  %64 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i32_s* %64, i32 0, i32 2 ; <i32**> [#uses=1]
  %66 = load i32** %65, align 4                   ; <i32*> [#uses=1]
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = getelementptr inbounds i32* %66, i32 %67  ; <i32*> [#uses=1]
  %69 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %70 = getelementptr inbounds %struct.fifo_i32_s* %69, i32 0, i32 1 ; <i32**> [#uses=1]
  %71 = load i32** %70, align 4                   ; <i32*> [#uses=1]
  %72 = bitcast i32* %71 to i8*                   ; <i8*> [#uses=1]
  %73 = bitcast i32* %68 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %72, i8* %73, i32 %63, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %74 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_i32_s* %74, i32 0, i32 4 ; <i32*> [#uses=1]
  %76 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %76, i32* %75, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

define i32 @fifo_u64_has_tokens(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 5 ; <i32*> [#uses=1]
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

define i32 @fifo_u64_has_room(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i64_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
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

define i32 @fifo_u64_get_room(%struct.fifo_i64_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=3]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_i64_s* %4, i32 0, i32 5 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = sub nsw i32 %3, %6                         ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32* %retval                    ; <i32> [#uses=1]
  ret i32 %retval1
}

define  i64* @fifo_u64_peek(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i64*                           ; <i64**> [#uses=2]
  %0 = alloca i64*                                ; <i64**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i64_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i64_s* %10, i32 0, i32 1 ; <i64**> [#uses=1]
  %12 = load i64** %11, align 4                   ; <i64*> [#uses=1]
  %13 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i64_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i64* %12, i32 %15  ; <i64*> [#uses=1]
  store i64* %16, i64** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i64_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i64_s* %20, i32 0, i32 3 ; <i32*> [#uses=1]
  %22 = load i32* %21, align 4                    ; <i32> [#uses=1]
  %23 = sub nsw i32 %19, %22                      ; <i32> [#uses=1]
  store i32 %23, i32* %num_end, align 4
  %24 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %25 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %26 = sub nsw i32 %24, %25                      ; <i32> [#uses=1]
  store i32 %26, i32* %num_beginning, align 4
  %27 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %28 = icmp ne i32 %27, 0                        ; <i1> [#uses=1]
  br i1 %28, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %29 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %30 = mul i32 %29, 8                            ; <i32> [#uses=1]
  %31 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_i64_s* %31, i32 0, i32 1 ; <i64**> [#uses=1]
  %33 = load i64** %32, align 4                   ; <i64*> [#uses=1]
  %34 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i64_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i64* %33, i32 %36  ; <i64*> [#uses=1]
  %38 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_i64_s* %38, i32 0, i32 2 ; <i64**> [#uses=1]
  %40 = load i64** %39, align 4                   ; <i64*> [#uses=1]
  %41 = bitcast i64* %40 to i8*                   ; <i8*> [#uses=1]
  %42 = bitcast i64* %37 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %41, i8* %42, i32 %30, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %43 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %44 = icmp ne i32 %43, 0                        ; <i1> [#uses=1]
  br i1 %44, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %46 = mul i32 %45, 8                            ; <i32> [#uses=1]
  %47 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_i64_s* %47, i32 0, i32 1 ; <i64**> [#uses=1]
  %49 = load i64** %48, align 4                   ; <i64*> [#uses=1]
  %50 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i64_s* %50, i32 0, i32 2 ; <i64**> [#uses=1]
  %52 = load i64** %51, align 4                   ; <i64*> [#uses=1]
  %53 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %54 = getelementptr inbounds i64* %52, i32 %53  ; <i64*> [#uses=1]
  %55 = bitcast i64* %54 to i8*                   ; <i8*> [#uses=1]
  %56 = bitcast i64* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %55, i8* %56, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %57 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i64_s* %57, i32 0, i32 2 ; <i64**> [#uses=1]
  %59 = load i64** %58, align 4                   ; <i64*> [#uses=1]
  store i64* %59, i64** %0, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb
  %60 = load i64** %0, align 4                    ; <i64*> [#uses=1]
  store i64* %60, i64** %retval, align 4
  br label %return

return:                                           ; preds = %bb6
  %retval7 = load i64** %retval                   ; <i64*> [#uses=1]
  ret i64* %retval7
}

define  i64* @fifo_u64_read(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i64*                           ; <i64**> [#uses=2]
  %0 = alloca i64*                                ; <i64**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i64* @fifo_u64_peek(%struct.fifo_i64_s* %1, i32 %2) nounwind ; <i64*> [#uses=1]
  store i64* %3, i64** %0, align 4
  %4 = load i64** %0, align 4                     ; <i64*> [#uses=1]
  store i64* %4, i64** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i64** %retval                   ; <i64*> [#uses=1]
  ret i64* %retval1
}

define  void @fifo_u64_read_end(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i64_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i64_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i64_s* %7, i32 0, i32 3 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i64_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i64_s* %16, i32 0, i32 3 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i64_s* %21, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb4

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i64_s* %23, i32 0, i32 3 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i64_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i64_s* %32, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb4

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i64_s* %34, i32 0, i32 3 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_i64_s* %39, i32 0, i32 0 ; <i32*> [#uses=1]
  %41 = load i32* %40, align 4                    ; <i32> [#uses=1]
  %42 = sub nsw i32 %38, %41                      ; <i32> [#uses=1]
  store i32 %42, i32* %num_beginning, align 4
  %43 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i64_s* %43, i32 0, i32 3 ; <i32*> [#uses=1]
  %45 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %45, i32* %44, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2, %bb
  br label %return

return:                                           ; preds = %bb4
  ret void
}

define  i64* @fifo_u64_write(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i64*                           ; <i64**> [#uses=2]
  %0 = alloca i64*                                ; <i64**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_i64_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_i64_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_i64_s* %10, i32 0, i32 1 ; <i64**> [#uses=1]
  %12 = load i64** %11, align 4                   ; <i64*> [#uses=1]
  %13 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_i64_s* %13, i32 0, i32 4 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i64* %12, i32 %15  ; <i64*> [#uses=1]
  store i64* %16, i64** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_i64_s* %17, i32 0, i32 2 ; <i64**> [#uses=1]
  %19 = load i64** %18, align 4                   ; <i64*> [#uses=1]
  store i64* %19, i64** %0, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %20 = load i64** %0, align 4                    ; <i64*> [#uses=1]
  store i64* %20, i64** %retval, align 4
  br label %return

return:                                           ; preds = %bb2
  %retval3 = load i64** %retval                   ; <i64*> [#uses=1]
  ret i64* %retval3
}

define  void @fifo_u64_write_end(%struct.fifo_i64_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i64_s*         ; <%struct.fifo_i64_s**> [#uses=18]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i64_s* %fifo, %struct.fifo_i64_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i64_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i64_s* %5, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_i64_s* %7, i32 0, i32 4 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i64_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp slt i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_i64_s* %16, i32 0, i32 4 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_i64_s* %21, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb8

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_i64_s* %23, i32 0, i32 4 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i64_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = icmp eq i32 %27, %30                      ; <i1> [#uses=1]
  br i1 %31, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  %32 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_i64_s* %32, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %33, align 4
  br label %bb8

bb3:                                              ; preds = %bb1
  %34 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i64_s* %34, i32 0, i32 0 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_i64_s* %37, i32 0, i32 4 ; <i32*> [#uses=1]
  %39 = load i32* %38, align 4                    ; <i32> [#uses=1]
  %40 = sub nsw i32 %36, %39                      ; <i32> [#uses=1]
  store i32 %40, i32* %num_end, align 4
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %43 = sub nsw i32 %41, %42                      ; <i32> [#uses=1]
  store i32 %43, i32* %num_beginning, align 4
  %44 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %45 = icmp ne i32 %44, 0                        ; <i1> [#uses=1]
  br i1 %45, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = mul i32 %46, 8                            ; <i32> [#uses=1]
  %48 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i64_s* %48, i32 0, i32 2 ; <i64**> [#uses=1]
  %50 = load i64** %49, align 4                   ; <i64*> [#uses=1]
  %51 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_i64_s* %51, i32 0, i32 1 ; <i64**> [#uses=1]
  %53 = load i64** %52, align 4                   ; <i64*> [#uses=1]
  %54 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %55 = getelementptr inbounds %struct.fifo_i64_s* %54, i32 0, i32 4 ; <i32*> [#uses=1]
  %56 = load i32* %55, align 4                    ; <i32> [#uses=1]
  %57 = getelementptr inbounds i64* %53, i32 %56  ; <i64*> [#uses=1]
  %58 = bitcast i64* %57 to i8*                   ; <i8*> [#uses=1]
  %59 = bitcast i64* %50 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %58, i8* %59, i32 %47, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %60 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %61 = icmp ne i32 %60, 0                        ; <i1> [#uses=1]
  br i1 %61, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %62 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %63 = mul i32 %62, 8                            ; <i32> [#uses=1]
  %64 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i64_s* %64, i32 0, i32 2 ; <i64**> [#uses=1]
  %66 = load i64** %65, align 4                   ; <i64*> [#uses=1]
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = getelementptr inbounds i64* %66, i32 %67  ; <i64*> [#uses=1]
  %69 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %70 = getelementptr inbounds %struct.fifo_i64_s* %69, i32 0, i32 1 ; <i64**> [#uses=1]
  %71 = load i64** %70, align 4                   ; <i64*> [#uses=1]
  %72 = bitcast i64* %71 to i8*                   ; <i8*> [#uses=1]
  %73 = bitcast i64* %68 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %72, i8* %73, i32 %63, i32 1)
  br label %bb7

bb7:                                              ; preds = %bb6, %bb5
  %74 = load %struct.fifo_i64_s** %fifo_addr, align 4 ; <%struct.fifo_i64_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_i64_s* %74, i32 0, i32 4 ; <i32*> [#uses=1]
  %76 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %76, i32* %75, align 4
  br label %bb8

bb8:                                              ; preds = %bb7, %bb2, %bb
  br label %return

return:                                           ; preds = %bb8
  ret void
}

!fifo = !{!0, !4, !8, !12}
!0 = metadata !{ i32 8,  metadata !1,  metadata !2, metadata !3}
!1 = metadata !{metadata !"struct.fifo_i8_s", %struct.fifo_i8_s* null}
!2 = metadata !{i32(%struct.fifo_i8_s*, i32)* @fifo_i8_has_tokens, i32(%struct.fifo_i8_s*, i32)* @fifo_i8_has_room, i32(%struct.fifo_i8_s*)* @fifo_i8_get_room, i8*(%struct.fifo_i8_s*, i32)* @fifo_i8_peek, i8*(%struct.fifo_i8_s*, i32)* @fifo_i8_read, void(%struct.fifo_i8_s*, i32)* @fifo_i8_read_end, i8*(%struct.fifo_i8_s*, i32)* @fifo_i8_write, void(%struct.fifo_i8_s*, i32)* @fifo_i8_write_end}
!3 = metadata !{i32(%struct.fifo_i8_s*, i32)* @fifo_u8_has_tokens, i32(%struct.fifo_i8_s*, i32)* @fifo_u8_has_room, i32(%struct.fifo_i8_s*)* @fifo_u8_get_room, i8*(%struct.fifo_i8_s*, i32)* @fifo_u8_peek, i8*(%struct.fifo_i8_s*, i32)* @fifo_u8_read, void(%struct.fifo_i8_s*, i32)* @fifo_u8_read_end, i8*(%struct.fifo_i8_s*, i32)* @fifo_u8_write, void(%struct.fifo_i8_s*, i32)* @fifo_u8_write_end}

!4 = metadata !{ i32 16, metadata !5,  metadata !6, metadata !7}
!5 = metadata !{metadata !"struct.fifo_i16_s", %struct.fifo_i16_s* null}
!6 = metadata !{i32(%struct.fifo_i16_s*, i32)* @fifo_i16_has_tokens, i32(%struct.fifo_i16_s*, i32)* @fifo_i16_has_room, i32(%struct.fifo_i16_s*)* @fifo_i16_get_room, i16*(%struct.fifo_i16_s*, i32)* @fifo_i16_peek, i16*(%struct.fifo_i16_s*, i32)* @fifo_i16_read, void(%struct.fifo_i16_s*, i32)* @fifo_i16_read_end, i16*(%struct.fifo_i16_s*, i32)* @fifo_i16_write, void(%struct.fifo_i16_s*, i32)* @fifo_i16_write_end}
!7 = metadata !{i32(%struct.fifo_i16_s*, i32)* @fifo_u16_has_tokens, i32(%struct.fifo_i16_s*, i32)* @fifo_u16_has_room, i32(%struct.fifo_i16_s*)* @fifo_u16_get_room, i16*(%struct.fifo_i16_s*, i32)* @fifo_u16_peek, i16*(%struct.fifo_i16_s*, i32)* @fifo_u16_read, void(%struct.fifo_i16_s*, i32)* @fifo_u16_read_end, i16*(%struct.fifo_i16_s*, i32)* @fifo_u16_write, void(%struct.fifo_i16_s*, i32)* @fifo_u16_write_end}

!8 = metadata !{ i32 32, metadata !9,  metadata !10, metadata !11}
!9 = metadata !{metadata !"struct.fifo_i32_s", %struct.fifo_i32_s* null}
!10 = metadata !{i32(%struct.fifo_i32_s*, i32)* @fifo_i32_has_tokens, i32(%struct.fifo_i32_s*, i32)* @fifo_i32_has_room, i32(%struct.fifo_i32_s*)* @fifo_i32_get_room, i32*(%struct.fifo_i32_s*, i32)* @fifo_i32_peek, i32*(%struct.fifo_i32_s*, i32)* @fifo_i32_read, void(%struct.fifo_i32_s*, i32)* @fifo_i32_read_end, i32*(%struct.fifo_i32_s*, i32)* @fifo_i32_write, void(%struct.fifo_i32_s*, i32)* @fifo_i32_write_end}
!11 = metadata !{i32(%struct.fifo_i32_s*, i32)* @fifo_u32_has_tokens, i32(%struct.fifo_i32_s*, i32)* @fifo_u32_has_room, i32(%struct.fifo_i32_s*)* @fifo_u32_get_room, i32*(%struct.fifo_i32_s*, i32)* @fifo_u32_peek, i32*(%struct.fifo_i32_s*, i32)* @fifo_u32_read, void(%struct.fifo_i32_s*, i32)* @fifo_u32_read_end, i32*(%struct.fifo_i32_s*, i32)* @fifo_u32_write, void(%struct.fifo_i32_s*, i32)* @fifo_u32_write_end}

!12 = metadata !{ i32 64, metadata !13,  metadata !14, metadata !15}
!13 = metadata !{metadata !"struct.fifo_i64_s", %struct.fifo_i64_s* null}
!14 = metadata !{i32(%struct.fifo_i64_s*, i32)* @fifo_i64_has_tokens, i32(%struct.fifo_i64_s*, i32)* @fifo_i64_has_room, i32(%struct.fifo_i64_s*)* @fifo_i64_get_room, i64*(%struct.fifo_i64_s*, i32)* @fifo_i64_peek, i64*(%struct.fifo_i64_s*, i32)* @fifo_i64_read, void(%struct.fifo_i64_s*, i32)* @fifo_i64_read_end, i64*(%struct.fifo_i64_s*, i32)* @fifo_i64_write, void(%struct.fifo_i64_s*, i32)* @fifo_i64_write_end}
!15 = metadata !{i32(%struct.fifo_i64_s*, i32)* @fifo_u64_has_tokens, i32(%struct.fifo_i64_s*, i32)* @fifo_u64_has_room, i32(%struct.fifo_i64_s*)* @fifo_u64_get_room, i64*(%struct.fifo_i64_s*, i32)* @fifo_u64_peek, i64*(%struct.fifo_i64_s*, i32)* @fifo_u64_read, void(%struct.fifo_i64_s*, i32)* @fifo_u64_read_end, i64*(%struct.fifo_i64_s*, i32)* @fifo_u64_write, void(%struct.fifo_i64_s*, i32)* @fifo_u64_write_end}