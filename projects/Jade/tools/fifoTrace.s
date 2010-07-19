; ModuleID = 'fifo.c'
target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:32:64-f32:32:32-f64:32:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32"
target triple = "i386-pc-linux-gnu"

%struct.FILE = type { i32, i8*, i8*, i8*, i8*, i8*, i8*, i8*, i8*, i8*, i8*, i8*, %struct._IO_marker*, %struct.FILE*, i32, i32, i32, i16, i8, [1 x i8], i8*, i64, i8*, i8*, i8*, i8*, i32, i32, [40 x i8] }
%struct._IO_marker = type { %struct._IO_marker*, %struct.FILE*, i32 }
%struct.fifo_i8_s = type { i32, i8*, i8*, i32, i32, i32, %struct.FILE* }
%struct.fifo_i32_s = type { i32, i32*, i32*, i32, i32, i32, %struct.FILE* }
%struct.fifo_i16_s = type { i32, i16*, i16*, i32, i32, i32, %struct.FILE* }

@.str = private constant [4 x i8] c"%d\0A\00", align 1 ; <[4 x i8]*> [#uses=1]

declare i32 @fseek(%struct.FILE*, i32, i32) nounwind
declare i32 @fread(i8*, i32, i32, %struct.FILE*) nounwind
declare i32 @puts(i8*)
declare void @exit(i32) noreturn nounwind
declare %struct.FILE* @fopen(i8*, i8*) nounwind
declare i32 @printf(i8*, ...) nounwind


define internal i32 @fifo_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
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

define internal i32 @fifo_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=3]
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

define internal i32 @fifo_i8_get_room(%struct.fifo_i8_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=3]
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
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
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

define internal i8* @fifo_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
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

define internal void @fifo_i8_read_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
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
  %42 = sub i32 %38, %41                          ; <i32> [#uses=1]
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

define internal i8* @fifo_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
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

define internal void @fifo_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=25]
  %n_addr = alloca i32                            ; <i32*> [#uses=9]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 0 ; <i32*> [#uses=1]
  %7 = load i32* %6, align 4                      ; <i32> [#uses=1]
  %8 = icmp sle i32 %4, %7                        ; <i1> [#uses=1]
  br i1 %8, label %bb, label %bb4

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 1 ; <i8**> [#uses=1]
  %11 = load i8** %10, align 4                    ; <i8*> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 4 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i8* %11, i32 %16   ; <i8*> [#uses=1]
  %18 = load i8* %17, align 1                     ; <i8> [#uses=1]
  %19 = sext i8 %18 to i32                        ; <i32> [#uses=1]
  %20 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i8_s* %20, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %22, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %25 = add nsw i32 %24, 1                        ; <i32> [#uses=1]
  store i32 %25, i32* %i, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %26 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %27 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %28 = icmp slt i32 %26, %27                     ; <i1> [#uses=1]
  br i1 %28, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  br label %bb7

bb4:                                              ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb6

bb5:                                              ; preds = %bb6
  %29 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %30 = getelementptr inbounds %struct.fifo_i8_s* %29, i32 0, i32 2 ; <i8**> [#uses=1]
  %31 = load i8** %30, align 4                    ; <i8*> [#uses=1]
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = getelementptr inbounds i8* %31, i32 %32   ; <i8*> [#uses=1]
  %34 = load i8* %33, align 1                     ; <i8> [#uses=1]
  %35 = sext i8 %34 to i32                        ; <i32> [#uses=1]
  %36 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_i8_s* %36, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %38 = load %struct.FILE** %37, align 4          ; <%struct.FILE*> [#uses=1]
  %39 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %38, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %35) nounwind ; <i32> [#uses=0]
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = add nsw i32 %40, 1                        ; <i32> [#uses=1]
  store i32 %41, i32* %i, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %42 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %43 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %44 = icmp slt i32 %42, %43                     ; <i1> [#uses=1]
  br i1 %44, label %bb5, label %bb7

bb7:                                              ; preds = %bb6, %bb3
  %45 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %46 = getelementptr inbounds %struct.fifo_i8_s* %45, i32 0, i32 5 ; <i32*> [#uses=1]
  %47 = load i32* %46, align 4                    ; <i32> [#uses=1]
  %48 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %49 = add nsw i32 %47, %48                      ; <i32> [#uses=1]
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %49, i32* %51, align 4
  %52 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %53 = getelementptr inbounds %struct.fifo_i8_s* %52, i32 0, i32 4 ; <i32*> [#uses=1]
  %54 = load i32* %53, align 4                    ; <i32> [#uses=1]
  %55 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %56 = add nsw i32 %54, %55                      ; <i32> [#uses=1]
  %57 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i8_s* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  %59 = load i32* %58, align 4                    ; <i32> [#uses=1]
  %60 = icmp slt i32 %56, %59                     ; <i1> [#uses=1]
  br i1 %60, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %61 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_i8_s* %61, i32 0, i32 4 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %65 = add nsw i32 %63, %64                      ; <i32> [#uses=1]
  %66 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %67 = getelementptr inbounds %struct.fifo_i8_s* %66, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %65, i32* %67, align 4
  br label %bb16

bb9:                                              ; preds = %bb7
  %68 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %69 = getelementptr inbounds %struct.fifo_i8_s* %68, i32 0, i32 4 ; <i32*> [#uses=1]
  %70 = load i32* %69, align 4                    ; <i32> [#uses=1]
  %71 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %72 = add nsw i32 %70, %71                      ; <i32> [#uses=1]
  %73 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %74 = getelementptr inbounds %struct.fifo_i8_s* %73, i32 0, i32 0 ; <i32*> [#uses=1]
  %75 = load i32* %74, align 4                    ; <i32> [#uses=1]
  %76 = icmp eq i32 %72, %75                      ; <i1> [#uses=1]
  br i1 %76, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %77 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_i8_s* %77, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %78, align 4
  br label %bb16

bb11:                                             ; preds = %bb9
  %79 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %80 = getelementptr inbounds %struct.fifo_i8_s* %79, i32 0, i32 0 ; <i32*> [#uses=1]
  %81 = load i32* %80, align 4                    ; <i32> [#uses=1]
  %82 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %83 = getelementptr inbounds %struct.fifo_i8_s* %82, i32 0, i32 4 ; <i32*> [#uses=1]
  %84 = load i32* %83, align 4                    ; <i32> [#uses=1]
  %85 = sub i32 %81, %84                          ; <i32> [#uses=1]
  store i32 %85, i32* %num_end, align 4
  %86 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = sub i32 %86, %87                          ; <i32> [#uses=1]
  store i32 %88, i32* %num_beginning, align 4
  %89 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %90 = icmp ne i32 %89, 0                        ; <i1> [#uses=1]
  br i1 %90, label %bb12, label %bb13

bb12:                                             ; preds = %bb11
  %91 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %92 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %93 = getelementptr inbounds %struct.fifo_i8_s* %92, i32 0, i32 2 ; <i8**> [#uses=1]
  %94 = load i8** %93, align 4                    ; <i8*> [#uses=1]
  %95 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %96 = getelementptr inbounds %struct.fifo_i8_s* %95, i32 0, i32 1 ; <i8**> [#uses=1]
  %97 = load i8** %96, align 4                    ; <i8*> [#uses=1]
  %98 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %99 = getelementptr inbounds %struct.fifo_i8_s* %98, i32 0, i32 4 ; <i32*> [#uses=1]
  %100 = load i32* %99, align 4                   ; <i32> [#uses=1]
  %101 = getelementptr inbounds i8* %97, i32 %100 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %101, i8* %94, i32 %91, i32 1)
  br label %bb13

bb13:                                             ; preds = %bb12, %bb11
  %102 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %103 = icmp ne i32 %102, 0                      ; <i1> [#uses=1]
  br i1 %103, label %bb14, label %bb15

bb14:                                             ; preds = %bb13
  %104 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %105 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %106 = getelementptr inbounds %struct.fifo_i8_s* %105, i32 0, i32 2 ; <i8**> [#uses=1]
  %107 = load i8** %106, align 4                  ; <i8*> [#uses=1]
  %108 = load i32* %num_end, align 4              ; <i32> [#uses=1]
  %109 = getelementptr inbounds i8* %107, i32 %108 ; <i8*> [#uses=1]
  %110 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %111 = getelementptr inbounds %struct.fifo_i8_s* %110, i32 0, i32 1 ; <i8**> [#uses=1]
  %112 = load i8** %111, align 4                  ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %112, i8* %109, i32 %104, i32 1)
  br label %bb15

bb15:                                             ; preds = %bb14, %bb13
  %113 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %114 = getelementptr inbounds %struct.fifo_i8_s* %113, i32 0, i32 4 ; <i32*> [#uses=1]
  %115 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  store i32 %115, i32* %114, align 4
  br label %bb16

bb16:                                             ; preds = %bb15, %bb10, %bb8
  br label %return

return:                                           ; preds = %bb16
  ret void
}

declare i32 @fprintf(%struct.FILE* noalias, i8* noalias, ...) nounwind

define internal i32 @fifo_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
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

define internal i32 @fifo_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=3]
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

define internal i32 @fifo_i16_get_room(%struct.fifo_i16_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=3]
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
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
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

define internal i16* @fifo_i16_read(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
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

define internal void @fifo_i16_read_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
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
  %42 = sub i32 %38, %41                          ; <i32> [#uses=1]
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

define internal i16* @fifo_i16_write(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=6]
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

define internal void @fifo_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=25]
  %n_addr = alloca i32                            ; <i32*> [#uses=9]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 0 ; <i32*> [#uses=1]
  %7 = load i32* %6, align 4                      ; <i32> [#uses=1]
  %8 = icmp sle i32 %4, %7                        ; <i1> [#uses=1]
  br i1 %8, label %bb, label %bb4

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %9 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i16_s* %9, i32 0, i32 1 ; <i16**> [#uses=1]
  %11 = load i16** %10, align 4                   ; <i16*> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 4 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i16* %11, i32 %16  ; <i16*> [#uses=1]
  %18 = load i16* %17, align 1                    ; <i16> [#uses=1]
  %19 = sext i16 %18 to i32                       ; <i32> [#uses=1]
  %20 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i16_s* %20, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %22, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %25 = add nsw i32 %24, 1                        ; <i32> [#uses=1]
  store i32 %25, i32* %i, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %26 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %27 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %28 = icmp slt i32 %26, %27                     ; <i1> [#uses=1]
  br i1 %28, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  br label %bb7

bb4:                                              ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb6

bb5:                                              ; preds = %bb6
  %29 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %30 = getelementptr inbounds %struct.fifo_i16_s* %29, i32 0, i32 2 ; <i16**> [#uses=1]
  %31 = load i16** %30, align 4                   ; <i16*> [#uses=1]
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = getelementptr inbounds i16* %31, i32 %32  ; <i16*> [#uses=1]
  %34 = load i16* %33, align 1                    ; <i16> [#uses=1]
  %35 = sext i16 %34 to i32                       ; <i32> [#uses=1]
  %36 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_i16_s* %36, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %38 = load %struct.FILE** %37, align 4          ; <%struct.FILE*> [#uses=1]
  %39 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %38, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %35) nounwind ; <i32> [#uses=0]
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = add nsw i32 %40, 1                        ; <i32> [#uses=1]
  store i32 %41, i32* %i, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %42 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %43 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %44 = icmp slt i32 %42, %43                     ; <i1> [#uses=1]
  br i1 %44, label %bb5, label %bb7

bb7:                                              ; preds = %bb6, %bb3
  %45 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %46 = getelementptr inbounds %struct.fifo_i16_s* %45, i32 0, i32 5 ; <i32*> [#uses=1]
  %47 = load i32* %46, align 4                    ; <i32> [#uses=1]
  %48 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %49 = add nsw i32 %47, %48                      ; <i32> [#uses=1]
  %50 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i16_s* %50, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %49, i32* %51, align 4
  %52 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %53 = getelementptr inbounds %struct.fifo_i16_s* %52, i32 0, i32 4 ; <i32*> [#uses=1]
  %54 = load i32* %53, align 4                    ; <i32> [#uses=1]
  %55 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %56 = add nsw i32 %54, %55                      ; <i32> [#uses=1]
  %57 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i16_s* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  %59 = load i32* %58, align 4                    ; <i32> [#uses=1]
  %60 = icmp slt i32 %56, %59                     ; <i1> [#uses=1]
  br i1 %60, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %61 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_i16_s* %61, i32 0, i32 4 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %65 = add nsw i32 %63, %64                      ; <i32> [#uses=1]
  %66 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %67 = getelementptr inbounds %struct.fifo_i16_s* %66, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %65, i32* %67, align 4
  br label %bb16

bb9:                                              ; preds = %bb7
  %68 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %69 = getelementptr inbounds %struct.fifo_i16_s* %68, i32 0, i32 4 ; <i32*> [#uses=1]
  %70 = load i32* %69, align 4                    ; <i32> [#uses=1]
  %71 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %72 = add nsw i32 %70, %71                      ; <i32> [#uses=1]
  %73 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %74 = getelementptr inbounds %struct.fifo_i16_s* %73, i32 0, i32 0 ; <i32*> [#uses=1]
  %75 = load i32* %74, align 4                    ; <i32> [#uses=1]
  %76 = icmp eq i32 %72, %75                      ; <i1> [#uses=1]
  br i1 %76, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %77 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_i16_s* %77, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %78, align 4
  br label %bb16

bb11:                                             ; preds = %bb9
  %79 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %80 = getelementptr inbounds %struct.fifo_i16_s* %79, i32 0, i32 0 ; <i32*> [#uses=1]
  %81 = load i32* %80, align 4                    ; <i32> [#uses=1]
  %82 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %83 = getelementptr inbounds %struct.fifo_i16_s* %82, i32 0, i32 4 ; <i32*> [#uses=1]
  %84 = load i32* %83, align 4                    ; <i32> [#uses=1]
  %85 = sub i32 %81, %84                          ; <i32> [#uses=1]
  store i32 %85, i32* %num_end, align 4
  %86 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = sub i32 %86, %87                          ; <i32> [#uses=1]
  store i32 %88, i32* %num_beginning, align 4
  %89 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %90 = icmp ne i32 %89, 0                        ; <i1> [#uses=1]
  br i1 %90, label %bb12, label %bb13

bb12:                                             ; preds = %bb11
  %91 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %92 = mul i32 %91, 2                            ; <i32> [#uses=1]
  %93 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %94 = getelementptr inbounds %struct.fifo_i16_s* %93, i32 0, i32 2 ; <i16**> [#uses=1]
  %95 = load i16** %94, align 4                   ; <i16*> [#uses=1]
  %96 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %97 = getelementptr inbounds %struct.fifo_i16_s* %96, i32 0, i32 1 ; <i16**> [#uses=1]
  %98 = load i16** %97, align 4                   ; <i16*> [#uses=1]
  %99 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %100 = getelementptr inbounds %struct.fifo_i16_s* %99, i32 0, i32 4 ; <i32*> [#uses=1]
  %101 = load i32* %100, align 4                  ; <i32> [#uses=1]
  %102 = getelementptr inbounds i16* %98, i32 %101 ; <i16*> [#uses=1]
  %103 = bitcast i16* %102 to i8*                 ; <i8*> [#uses=1]
  %104 = bitcast i16* %95 to i8*                  ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %103, i8* %104, i32 %92, i32 1)
  br label %bb13

bb13:                                             ; preds = %bb12, %bb11
  %105 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %106 = icmp ne i32 %105, 0                      ; <i1> [#uses=1]
  br i1 %106, label %bb14, label %bb15

bb14:                                             ; preds = %bb13
  %107 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %108 = mul i32 %107, 2                          ; <i32> [#uses=1]
  %109 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %110 = getelementptr inbounds %struct.fifo_i16_s* %109, i32 0, i32 2 ; <i16**> [#uses=1]
  %111 = load i16** %110, align 4                 ; <i16*> [#uses=1]
  %112 = load i32* %num_end, align 4              ; <i32> [#uses=1]
  %113 = getelementptr inbounds i16* %111, i32 %112 ; <i16*> [#uses=1]
  %114 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %115 = getelementptr inbounds %struct.fifo_i16_s* %114, i32 0, i32 1 ; <i16**> [#uses=1]
  %116 = load i16** %115, align 4                 ; <i16*> [#uses=1]
  %117 = bitcast i16* %116 to i8*                 ; <i8*> [#uses=1]
  %118 = bitcast i16* %113 to i8*                 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %117, i8* %118, i32 %108, i32 1)
  br label %bb15

bb15:                                             ; preds = %bb14, %bb13
  %119 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %120 = getelementptr inbounds %struct.fifo_i16_s* %119, i32 0, i32 4 ; <i32*> [#uses=1]
  %121 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  store i32 %121, i32* %120, align 4
  br label %bb16

bb16:                                             ; preds = %bb15, %bb10, %bb8
  br label %return

return:                                           ; preds = %bb16
  ret void
}

define internal i32 @fifo_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal i32 @fifo_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal i32 @fifo_i32_get_room(%struct.fifo_i32_s* %fifo) nounwind {
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
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
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

define internal i32* @fifo_i32_read(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal void @fifo_i32_read_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
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
  %42 = sub i32 %38, %41                          ; <i32> [#uses=1]
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

define internal i32* @fifo_i32_write(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal void @fifo_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=25]
  %n_addr = alloca i32                            ; <i32*> [#uses=9]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 0 ; <i32*> [#uses=1]
  %7 = load i32* %6, align 4                      ; <i32> [#uses=1]
  %8 = icmp sle i32 %4, %7                        ; <i1> [#uses=1]
  br i1 %8, label %bb, label %bb4

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %9 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i32_s* %9, i32 0, i32 1 ; <i32**> [#uses=1]
  %11 = load i32** %10, align 4                   ; <i32*> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 4 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i32* %11, i32 %16  ; <i32*> [#uses=1]
  %18 = load i32* %17, align 1                    ; <i32> [#uses=1]
  %19 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %20 = getelementptr inbounds %struct.fifo_i32_s* %19, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %21 = load %struct.FILE** %20, align 4          ; <%struct.FILE*> [#uses=1]
  %22 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %21, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %18) nounwind ; <i32> [#uses=0]
  %23 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %24 = add nsw i32 %23, 1                        ; <i32> [#uses=1]
  store i32 %24, i32* %i, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %25 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = icmp slt i32 %25, %26                     ; <i1> [#uses=1]
  br i1 %27, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  br label %bb7

bb4:                                              ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb6

bb5:                                              ; preds = %bb6
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 2 ; <i32**> [#uses=1]
  %30 = load i32** %29, align 4                   ; <i32*> [#uses=1]
  %31 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %32 = getelementptr inbounds i32* %30, i32 %31  ; <i32*> [#uses=1]
  %33 = load i32* %32, align 1                    ; <i32> [#uses=1]
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %36 = load %struct.FILE** %35, align 4          ; <%struct.FILE*> [#uses=1]
  %37 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %36, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %33) nounwind ; <i32> [#uses=0]
  %38 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %39 = add nsw i32 %38, 1                        ; <i32> [#uses=1]
  store i32 %39, i32* %i, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = icmp slt i32 %40, %41                     ; <i1> [#uses=1]
  br i1 %42, label %bb5, label %bb7

bb7:                                              ; preds = %bb6, %bb3
  %43 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i32_s* %43, i32 0, i32 5 ; <i32*> [#uses=1]
  %45 = load i32* %44, align 4                    ; <i32> [#uses=1]
  %46 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %47 = add nsw i32 %45, %46                      ; <i32> [#uses=1]
  %48 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i32_s* %48, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %47, i32* %49, align 4
  %50 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i32_s* %50, i32 0, i32 4 ; <i32*> [#uses=1]
  %52 = load i32* %51, align 4                    ; <i32> [#uses=1]
  %53 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %54 = add nsw i32 %52, %53                      ; <i32> [#uses=1]
  %55 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %56 = getelementptr inbounds %struct.fifo_i32_s* %55, i32 0, i32 0 ; <i32*> [#uses=1]
  %57 = load i32* %56, align 4                    ; <i32> [#uses=1]
  %58 = icmp slt i32 %54, %57                     ; <i1> [#uses=1]
  br i1 %58, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %59 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %60 = getelementptr inbounds %struct.fifo_i32_s* %59, i32 0, i32 4 ; <i32*> [#uses=1]
  %61 = load i32* %60, align 4                    ; <i32> [#uses=1]
  %62 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %63 = add nsw i32 %61, %62                      ; <i32> [#uses=1]
  %64 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i32_s* %64, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %63, i32* %65, align 4
  br label %bb16

bb9:                                              ; preds = %bb7
  %66 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %67 = getelementptr inbounds %struct.fifo_i32_s* %66, i32 0, i32 4 ; <i32*> [#uses=1]
  %68 = load i32* %67, align 4                    ; <i32> [#uses=1]
  %69 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %70 = add nsw i32 %68, %69                      ; <i32> [#uses=1]
  %71 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %72 = getelementptr inbounds %struct.fifo_i32_s* %71, i32 0, i32 0 ; <i32*> [#uses=1]
  %73 = load i32* %72, align 4                    ; <i32> [#uses=1]
  %74 = icmp eq i32 %70, %73                      ; <i1> [#uses=1]
  br i1 %74, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %75 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %76 = getelementptr inbounds %struct.fifo_i32_s* %75, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %76, align 4
  br label %bb16

bb11:                                             ; preds = %bb9
  %77 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_i32_s* %77, i32 0, i32 0 ; <i32*> [#uses=1]
  %79 = load i32* %78, align 4                    ; <i32> [#uses=1]
  %80 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %81 = getelementptr inbounds %struct.fifo_i32_s* %80, i32 0, i32 4 ; <i32*> [#uses=1]
  %82 = load i32* %81, align 4                    ; <i32> [#uses=1]
  %83 = sub i32 %79, %82                          ; <i32> [#uses=1]
  store i32 %83, i32* %num_end, align 4
  %84 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %85 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %86 = sub i32 %84, %85                          ; <i32> [#uses=1]
  store i32 %86, i32* %num_beginning, align 4
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = icmp ne i32 %87, 0                        ; <i1> [#uses=1]
  br i1 %88, label %bb12, label %bb13

bb12:                                             ; preds = %bb11
  %89 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %90 = mul i32 %89, 4                            ; <i32> [#uses=1]
  %91 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %92 = getelementptr inbounds %struct.fifo_i32_s* %91, i32 0, i32 2 ; <i32**> [#uses=1]
  %93 = load i32** %92, align 4                   ; <i32*> [#uses=1]
  %94 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %95 = getelementptr inbounds %struct.fifo_i32_s* %94, i32 0, i32 1 ; <i32**> [#uses=1]
  %96 = load i32** %95, align 4                   ; <i32*> [#uses=1]
  %97 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %98 = getelementptr inbounds %struct.fifo_i32_s* %97, i32 0, i32 4 ; <i32*> [#uses=1]
  %99 = load i32* %98, align 4                    ; <i32> [#uses=1]
  %100 = getelementptr inbounds i32* %96, i32 %99 ; <i32*> [#uses=1]
  %101 = bitcast i32* %100 to i8*                 ; <i8*> [#uses=1]
  %102 = bitcast i32* %93 to i8*                  ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %101, i8* %102, i32 %90, i32 1)
  br label %bb13

bb13:                                             ; preds = %bb12, %bb11
  %103 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %104 = icmp ne i32 %103, 0                      ; <i1> [#uses=1]
  br i1 %104, label %bb14, label %bb15

bb14:                                             ; preds = %bb13
  %105 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %106 = mul i32 %105, 4                          ; <i32> [#uses=1]
  %107 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %108 = getelementptr inbounds %struct.fifo_i32_s* %107, i32 0, i32 2 ; <i32**> [#uses=1]
  %109 = load i32** %108, align 4                 ; <i32*> [#uses=1]
  %110 = load i32* %num_end, align 4              ; <i32> [#uses=1]
  %111 = getelementptr inbounds i32* %109, i32 %110 ; <i32*> [#uses=1]
  %112 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %113 = getelementptr inbounds %struct.fifo_i32_s* %112, i32 0, i32 1 ; <i32**> [#uses=1]
  %114 = load i32** %113, align 4                 ; <i32*> [#uses=1]
  %115 = bitcast i32* %114 to i8*                 ; <i8*> [#uses=1]
  %116 = bitcast i32* %111 to i8*                 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %115, i8* %116, i32 %106, i32 1)
  br label %bb15

bb15:                                             ; preds = %bb14, %bb13
  %117 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %118 = getelementptr inbounds %struct.fifo_i32_s* %117, i32 0, i32 4 ; <i32*> [#uses=1]
  %119 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  store i32 %119, i32* %118, align 4
  br label %bb16

bb16:                                             ; preds = %bb15, %bb10, %bb8
  br label %return

return:                                           ; preds = %bb16
  ret void
}

define internal i32 @fifo_u_i8_has_tokens(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
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

define internal i32 @fifo_u_i8_has_room(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=3]
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

define internal i32 @fifo_u_i8_get_room(%struct.fifo_i8_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=3]
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
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
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

define internal i8* @fifo_u_i8_read(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
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
  %42 = sub i32 %38, %41                          ; <i32> [#uses=1]
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

define internal i8* @fifo_u_i8_write(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=6]
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

define internal void @fifo_u_i8_write_end(%struct.fifo_i8_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i8_s*        ; <%struct.fifo_i8_s**> [#uses=25]
  %n_addr = alloca i32                            ; <i32*> [#uses=9]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i8_s* %fifo, %struct.fifo_i8_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i8_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i8_s* %5, i32 0, i32 0 ; <i32*> [#uses=1]
  %7 = load i32* %6, align 4                      ; <i32> [#uses=1]
  %8 = icmp sle i32 %4, %7                        ; <i1> [#uses=1]
  br i1 %8, label %bb, label %bb4

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %9 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i8_s* %9, i32 0, i32 1 ; <i8**> [#uses=1]
  %11 = load i8** %10, align 4                    ; <i8*> [#uses=1]
  %12 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i8_s* %12, i32 0, i32 4 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i8* %11, i32 %16   ; <i8*> [#uses=1]
  %18 = load i8* %17, align 1                     ; <i8> [#uses=1]
  %19 = zext i8 %18 to i32                        ; <i32> [#uses=1]
  %20 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i8_s* %20, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %22, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %25 = add nsw i32 %24, 1                        ; <i32> [#uses=1]
  store i32 %25, i32* %i, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %26 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %27 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %28 = icmp slt i32 %26, %27                     ; <i1> [#uses=1]
  br i1 %28, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  br label %bb7

bb4:                                              ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb6

bb5:                                              ; preds = %bb6
  %29 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %30 = getelementptr inbounds %struct.fifo_i8_s* %29, i32 0, i32 2 ; <i8**> [#uses=1]
  %31 = load i8** %30, align 4                    ; <i8*> [#uses=1]
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = getelementptr inbounds i8* %31, i32 %32   ; <i8*> [#uses=1]
  %34 = load i8* %33, align 1                     ; <i8> [#uses=1]
  %35 = zext i8 %34 to i32                        ; <i32> [#uses=1]
  %36 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_i8_s* %36, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %38 = load %struct.FILE** %37, align 4          ; <%struct.FILE*> [#uses=1]
  %39 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %38, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %35) nounwind ; <i32> [#uses=0]
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = add nsw i32 %40, 1                        ; <i32> [#uses=1]
  store i32 %41, i32* %i, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %42 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %43 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %44 = icmp slt i32 %42, %43                     ; <i1> [#uses=1]
  br i1 %44, label %bb5, label %bb7

bb7:                                              ; preds = %bb6, %bb3
  %45 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %46 = getelementptr inbounds %struct.fifo_i8_s* %45, i32 0, i32 5 ; <i32*> [#uses=1]
  %47 = load i32* %46, align 4                    ; <i32> [#uses=1]
  %48 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %49 = add nsw i32 %47, %48                      ; <i32> [#uses=1]
  %50 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i8_s* %50, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %49, i32* %51, align 4
  %52 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %53 = getelementptr inbounds %struct.fifo_i8_s* %52, i32 0, i32 4 ; <i32*> [#uses=1]
  %54 = load i32* %53, align 4                    ; <i32> [#uses=1]
  %55 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %56 = add nsw i32 %54, %55                      ; <i32> [#uses=1]
  %57 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i8_s* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  %59 = load i32* %58, align 4                    ; <i32> [#uses=1]
  %60 = icmp slt i32 %56, %59                     ; <i1> [#uses=1]
  br i1 %60, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %61 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_i8_s* %61, i32 0, i32 4 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %65 = add nsw i32 %63, %64                      ; <i32> [#uses=1]
  %66 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %67 = getelementptr inbounds %struct.fifo_i8_s* %66, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %65, i32* %67, align 4
  br label %bb16

bb9:                                              ; preds = %bb7
  %68 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %69 = getelementptr inbounds %struct.fifo_i8_s* %68, i32 0, i32 4 ; <i32*> [#uses=1]
  %70 = load i32* %69, align 4                    ; <i32> [#uses=1]
  %71 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %72 = add nsw i32 %70, %71                      ; <i32> [#uses=1]
  %73 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %74 = getelementptr inbounds %struct.fifo_i8_s* %73, i32 0, i32 0 ; <i32*> [#uses=1]
  %75 = load i32* %74, align 4                    ; <i32> [#uses=1]
  %76 = icmp eq i32 %72, %75                      ; <i1> [#uses=1]
  br i1 %76, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %77 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_i8_s* %77, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %78, align 4
  br label %bb16

bb11:                                             ; preds = %bb9
  %79 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %80 = getelementptr inbounds %struct.fifo_i8_s* %79, i32 0, i32 0 ; <i32*> [#uses=1]
  %81 = load i32* %80, align 4                    ; <i32> [#uses=1]
  %82 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %83 = getelementptr inbounds %struct.fifo_i8_s* %82, i32 0, i32 4 ; <i32*> [#uses=1]
  %84 = load i32* %83, align 4                    ; <i32> [#uses=1]
  %85 = sub i32 %81, %84                          ; <i32> [#uses=1]
  store i32 %85, i32* %num_end, align 4
  %86 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = sub i32 %86, %87                          ; <i32> [#uses=1]
  store i32 %88, i32* %num_beginning, align 4
  %89 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %90 = icmp ne i32 %89, 0                        ; <i1> [#uses=1]
  br i1 %90, label %bb12, label %bb13

bb12:                                             ; preds = %bb11
  %91 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %92 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %93 = getelementptr inbounds %struct.fifo_i8_s* %92, i32 0, i32 2 ; <i8**> [#uses=1]
  %94 = load i8** %93, align 4                    ; <i8*> [#uses=1]
  %95 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %96 = getelementptr inbounds %struct.fifo_i8_s* %95, i32 0, i32 1 ; <i8**> [#uses=1]
  %97 = load i8** %96, align 4                    ; <i8*> [#uses=1]
  %98 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %99 = getelementptr inbounds %struct.fifo_i8_s* %98, i32 0, i32 4 ; <i32*> [#uses=1]
  %100 = load i32* %99, align 4                   ; <i32> [#uses=1]
  %101 = getelementptr inbounds i8* %97, i32 %100 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %101, i8* %94, i32 %91, i32 1)
  br label %bb13

bb13:                                             ; preds = %bb12, %bb11
  %102 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %103 = icmp ne i32 %102, 0                      ; <i1> [#uses=1]
  br i1 %103, label %bb14, label %bb15

bb14:                                             ; preds = %bb13
  %104 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %105 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %106 = getelementptr inbounds %struct.fifo_i8_s* %105, i32 0, i32 2 ; <i8**> [#uses=1]
  %107 = load i8** %106, align 4                  ; <i8*> [#uses=1]
  %108 = load i32* %num_end, align 4              ; <i32> [#uses=1]
  %109 = getelementptr inbounds i8* %107, i32 %108 ; <i8*> [#uses=1]
  %110 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %111 = getelementptr inbounds %struct.fifo_i8_s* %110, i32 0, i32 1 ; <i8**> [#uses=1]
  %112 = load i8** %111, align 4                  ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %112, i8* %109, i32 %104, i32 1)
  br label %bb15

bb15:                                             ; preds = %bb14, %bb13
  %113 = load %struct.fifo_i8_s** %fifo_addr, align 4 ; <%struct.fifo_i8_s*> [#uses=1]
  %114 = getelementptr inbounds %struct.fifo_i8_s* %113, i32 0, i32 4 ; <i32*> [#uses=1]
  %115 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  store i32 %115, i32* %114, align 4
  br label %bb16

bb16:                                             ; preds = %bb15, %bb10, %bb8
  br label %return

return:                                           ; preds = %bb16
  ret void
}

define internal i32 @fifo_u_i16_has_tokens(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
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

define internal i32 @fifo_u_i16_has_room(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=3]
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

define internal i32 @fifo_u_i16_get_room(%struct.fifo_i16_s* %fifo) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=3]
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
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
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

define internal i16* @fifo_u_i16_read(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
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
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 5 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
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
  %42 = sub i32 %38, %41                          ; <i32> [#uses=1]
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

define internal i16* @fifo_u_i16_write(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=6]
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

define internal void @fifo_u_i16_write_end(%struct.fifo_i16_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i16_s*       ; <%struct.fifo_i16_s**> [#uses=25]
  %n_addr = alloca i32                            ; <i32*> [#uses=9]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i16_s* %fifo, %struct.fifo_i16_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i16_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i16_s* %5, i32 0, i32 0 ; <i32*> [#uses=1]
  %7 = load i32* %6, align 4                      ; <i32> [#uses=1]
  %8 = icmp sle i32 %4, %7                        ; <i1> [#uses=1]
  br i1 %8, label %bb, label %bb4

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %9 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i16_s* %9, i32 0, i32 1 ; <i16**> [#uses=1]
  %11 = load i16** %10, align 4                   ; <i16*> [#uses=1]
  %12 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i16_s* %12, i32 0, i32 4 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i16* %11, i32 %16  ; <i16*> [#uses=1]
  %18 = load i16* %17, align 1                    ; <i16> [#uses=1]
  %19 = zext i16 %18 to i32                       ; <i32> [#uses=1]
  %20 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_i16_s* %20, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %22, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %25 = add nsw i32 %24, 1                        ; <i32> [#uses=1]
  store i32 %25, i32* %i, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %26 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %27 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %28 = icmp slt i32 %26, %27                     ; <i1> [#uses=1]
  br i1 %28, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  br label %bb7

bb4:                                              ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb6

bb5:                                              ; preds = %bb6
  %29 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %30 = getelementptr inbounds %struct.fifo_i16_s* %29, i32 0, i32 2 ; <i16**> [#uses=1]
  %31 = load i16** %30, align 4                   ; <i16*> [#uses=1]
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = getelementptr inbounds i16* %31, i32 %32  ; <i16*> [#uses=1]
  %34 = load i16* %33, align 1                    ; <i16> [#uses=1]
  %35 = zext i16 %34 to i32                       ; <i32> [#uses=1]
  %36 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %37 = getelementptr inbounds %struct.fifo_i16_s* %36, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %38 = load %struct.FILE** %37, align 4          ; <%struct.FILE*> [#uses=1]
  %39 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %38, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %35) nounwind ; <i32> [#uses=0]
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = add nsw i32 %40, 1                        ; <i32> [#uses=1]
  store i32 %41, i32* %i, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %42 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %43 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %44 = icmp slt i32 %42, %43                     ; <i1> [#uses=1]
  br i1 %44, label %bb5, label %bb7

bb7:                                              ; preds = %bb6, %bb3
  %45 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %46 = getelementptr inbounds %struct.fifo_i16_s* %45, i32 0, i32 5 ; <i32*> [#uses=1]
  %47 = load i32* %46, align 4                    ; <i32> [#uses=1]
  %48 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %49 = add nsw i32 %47, %48                      ; <i32> [#uses=1]
  %50 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i16_s* %50, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %49, i32* %51, align 4
  %52 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %53 = getelementptr inbounds %struct.fifo_i16_s* %52, i32 0, i32 4 ; <i32*> [#uses=1]
  %54 = load i32* %53, align 4                    ; <i32> [#uses=1]
  %55 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %56 = add nsw i32 %54, %55                      ; <i32> [#uses=1]
  %57 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_i16_s* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  %59 = load i32* %58, align 4                    ; <i32> [#uses=1]
  %60 = icmp slt i32 %56, %59                     ; <i1> [#uses=1]
  br i1 %60, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %61 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_i16_s* %61, i32 0, i32 4 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %65 = add nsw i32 %63, %64                      ; <i32> [#uses=1]
  %66 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %67 = getelementptr inbounds %struct.fifo_i16_s* %66, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %65, i32* %67, align 4
  br label %bb16

bb9:                                              ; preds = %bb7
  %68 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %69 = getelementptr inbounds %struct.fifo_i16_s* %68, i32 0, i32 4 ; <i32*> [#uses=1]
  %70 = load i32* %69, align 4                    ; <i32> [#uses=1]
  %71 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %72 = add nsw i32 %70, %71                      ; <i32> [#uses=1]
  %73 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %74 = getelementptr inbounds %struct.fifo_i16_s* %73, i32 0, i32 0 ; <i32*> [#uses=1]
  %75 = load i32* %74, align 4                    ; <i32> [#uses=1]
  %76 = icmp eq i32 %72, %75                      ; <i1> [#uses=1]
  br i1 %76, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %77 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_i16_s* %77, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %78, align 4
  br label %bb16

bb11:                                             ; preds = %bb9
  %79 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %80 = getelementptr inbounds %struct.fifo_i16_s* %79, i32 0, i32 0 ; <i32*> [#uses=1]
  %81 = load i32* %80, align 4                    ; <i32> [#uses=1]
  %82 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %83 = getelementptr inbounds %struct.fifo_i16_s* %82, i32 0, i32 4 ; <i32*> [#uses=1]
  %84 = load i32* %83, align 4                    ; <i32> [#uses=1]
  %85 = sub i32 %81, %84                          ; <i32> [#uses=1]
  store i32 %85, i32* %num_end, align 4
  %86 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = sub i32 %86, %87                          ; <i32> [#uses=1]
  store i32 %88, i32* %num_beginning, align 4
  %89 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %90 = icmp ne i32 %89, 0                        ; <i1> [#uses=1]
  br i1 %90, label %bb12, label %bb13

bb12:                                             ; preds = %bb11
  %91 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %92 = mul i32 %91, 2                            ; <i32> [#uses=1]
  %93 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %94 = getelementptr inbounds %struct.fifo_i16_s* %93, i32 0, i32 2 ; <i16**> [#uses=1]
  %95 = load i16** %94, align 4                   ; <i16*> [#uses=1]
  %96 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %97 = getelementptr inbounds %struct.fifo_i16_s* %96, i32 0, i32 1 ; <i16**> [#uses=1]
  %98 = load i16** %97, align 4                   ; <i16*> [#uses=1]
  %99 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %100 = getelementptr inbounds %struct.fifo_i16_s* %99, i32 0, i32 4 ; <i32*> [#uses=1]
  %101 = load i32* %100, align 4                  ; <i32> [#uses=1]
  %102 = getelementptr inbounds i16* %98, i32 %101 ; <i16*> [#uses=1]
  %103 = bitcast i16* %102 to i8*                 ; <i8*> [#uses=1]
  %104 = bitcast i16* %95 to i8*                  ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %103, i8* %104, i32 %92, i32 1)
  br label %bb13

bb13:                                             ; preds = %bb12, %bb11
  %105 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %106 = icmp ne i32 %105, 0                      ; <i1> [#uses=1]
  br i1 %106, label %bb14, label %bb15

bb14:                                             ; preds = %bb13
  %107 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %108 = mul i32 %107, 2                          ; <i32> [#uses=1]
  %109 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %110 = getelementptr inbounds %struct.fifo_i16_s* %109, i32 0, i32 2 ; <i16**> [#uses=1]
  %111 = load i16** %110, align 4                 ; <i16*> [#uses=1]
  %112 = load i32* %num_end, align 4              ; <i32> [#uses=1]
  %113 = getelementptr inbounds i16* %111, i32 %112 ; <i16*> [#uses=1]
  %114 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %115 = getelementptr inbounds %struct.fifo_i16_s* %114, i32 0, i32 1 ; <i16**> [#uses=1]
  %116 = load i16** %115, align 4                 ; <i16*> [#uses=1]
  %117 = bitcast i16* %116 to i8*                 ; <i8*> [#uses=1]
  %118 = bitcast i16* %113 to i8*                 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %117, i8* %118, i32 %108, i32 1)
  br label %bb15

bb15:                                             ; preds = %bb14, %bb13
  %119 = load %struct.fifo_i16_s** %fifo_addr, align 4 ; <%struct.fifo_i16_s*> [#uses=1]
  %120 = getelementptr inbounds %struct.fifo_i16_s* %119, i32 0, i32 4 ; <i32*> [#uses=1]
  %121 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  store i32 %121, i32* %120, align 4
  br label %bb16

bb16:                                             ; preds = %bb15, %bb10, %bb8
  br label %return

return:                                           ; preds = %bb16
  ret void
}

define internal i32 @fifo_u_i32_has_tokens(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal i32 @fifo_u_i32_has_room(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal i32 @fifo_u_i32_get_room(%struct.fifo_i32_s* %fifo) nounwind {
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
  %7 = sub i32 %3, %6                             ; <i32> [#uses=1]
  store i32 %7, i32* %0, align 4
  %8 = load i32* %0, align 4                      ; <i32> [#uses=1]
  store i32 %8, i32* %retval, align 4
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

define internal i32* @fifo_u_i32_read(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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
  %4 = sub i32 %2, %3                             ; <i32> [#uses=1]
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
  %42 = sub i32 %38, %41                          ; <i32> [#uses=1]
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

define internal i32* @fifo_u_i32_write(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
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

define internal void @fifo_u_i32_write_end(%struct.fifo_i32_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_i32_s*         ; <%struct.fifo_i32_s**> [#uses=25]
  %n_addr = alloca i32                            ; <i32*> [#uses=9]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %i = alloca i32                                 ; <i32*> [#uses=10]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_i32_s* %fifo, %struct.fifo_i32_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_i32_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = add nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_i32_s* %5, i32 0, i32 0 ; <i32*> [#uses=1]
  %7 = load i32* %6, align 4                      ; <i32> [#uses=1]
  %8 = icmp sle i32 %4, %7                        ; <i1> [#uses=1]
  br i1 %8, label %bb, label %bb4

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb2

bb1:                                              ; preds = %bb2
  %9 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_i32_s* %9, i32 0, i32 1 ; <i32**> [#uses=1]
  %11 = load i32** %10, align 4                   ; <i32*> [#uses=1]
  %12 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_i32_s* %12, i32 0, i32 4 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i32* %11, i32 %16  ; <i32*> [#uses=1]
  %18 = load i32* %17, align 1                    ; <i32> [#uses=1]
  %19 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %20 = getelementptr inbounds %struct.fifo_i32_s* %19, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %21 = load %struct.FILE** %20, align 4          ; <%struct.FILE*> [#uses=1]
  %22 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %21, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %18) nounwind ; <i32> [#uses=0]
  %23 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %24 = add nsw i32 %23, 1                        ; <i32> [#uses=1]
  store i32 %24, i32* %i, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  %25 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = icmp slt i32 %25, %26                     ; <i1> [#uses=1]
  br i1 %27, label %bb1, label %bb3

bb3:                                              ; preds = %bb2
  br label %bb7

bb4:                                              ; preds = %entry
  store i32 0, i32* %i, align 4
  br label %bb6

bb5:                                              ; preds = %bb6
  %28 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_i32_s* %28, i32 0, i32 2 ; <i32**> [#uses=1]
  %30 = load i32** %29, align 4                   ; <i32*> [#uses=1]
  %31 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %32 = getelementptr inbounds i32* %30, i32 %31  ; <i32*> [#uses=1]
  %33 = load i32* %32, align 1                    ; <i32> [#uses=1]
  %34 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_i32_s* %34, i32 0, i32 6 ; <%struct.FILE**> [#uses=1]
  %36 = load %struct.FILE** %35, align 4          ; <%struct.FILE*> [#uses=1]
  %37 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* noalias %36, i8* noalias getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %33) nounwind ; <i32> [#uses=0]
  %38 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %39 = add nsw i32 %38, 1                        ; <i32> [#uses=1]
  store i32 %39, i32* %i, align 4
  br label %bb6

bb6:                                              ; preds = %bb5, %bb4
  %40 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %41 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %42 = icmp slt i32 %40, %41                     ; <i1> [#uses=1]
  br i1 %42, label %bb5, label %bb7

bb7:                                              ; preds = %bb6, %bb3
  %43 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_i32_s* %43, i32 0, i32 5 ; <i32*> [#uses=1]
  %45 = load i32* %44, align 4                    ; <i32> [#uses=1]
  %46 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %47 = add nsw i32 %45, %46                      ; <i32> [#uses=1]
  %48 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %49 = getelementptr inbounds %struct.fifo_i32_s* %48, i32 0, i32 5 ; <i32*> [#uses=1]
  store i32 %47, i32* %49, align 4
  %50 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_i32_s* %50, i32 0, i32 4 ; <i32*> [#uses=1]
  %52 = load i32* %51, align 4                    ; <i32> [#uses=1]
  %53 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %54 = add nsw i32 %52, %53                      ; <i32> [#uses=1]
  %55 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %56 = getelementptr inbounds %struct.fifo_i32_s* %55, i32 0, i32 0 ; <i32*> [#uses=1]
  %57 = load i32* %56, align 4                    ; <i32> [#uses=1]
  %58 = icmp slt i32 %54, %57                     ; <i1> [#uses=1]
  br i1 %58, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %59 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %60 = getelementptr inbounds %struct.fifo_i32_s* %59, i32 0, i32 4 ; <i32*> [#uses=1]
  %61 = load i32* %60, align 4                    ; <i32> [#uses=1]
  %62 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %63 = add nsw i32 %61, %62                      ; <i32> [#uses=1]
  %64 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %65 = getelementptr inbounds %struct.fifo_i32_s* %64, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %63, i32* %65, align 4
  br label %bb16

bb9:                                              ; preds = %bb7
  %66 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %67 = getelementptr inbounds %struct.fifo_i32_s* %66, i32 0, i32 4 ; <i32*> [#uses=1]
  %68 = load i32* %67, align 4                    ; <i32> [#uses=1]
  %69 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %70 = add nsw i32 %68, %69                      ; <i32> [#uses=1]
  %71 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %72 = getelementptr inbounds %struct.fifo_i32_s* %71, i32 0, i32 0 ; <i32*> [#uses=1]
  %73 = load i32* %72, align 4                    ; <i32> [#uses=1]
  %74 = icmp eq i32 %70, %73                      ; <i1> [#uses=1]
  br i1 %74, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %75 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %76 = getelementptr inbounds %struct.fifo_i32_s* %75, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 0, i32* %76, align 4
  br label %bb16

bb11:                                             ; preds = %bb9
  %77 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_i32_s* %77, i32 0, i32 0 ; <i32*> [#uses=1]
  %79 = load i32* %78, align 4                    ; <i32> [#uses=1]
  %80 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %81 = getelementptr inbounds %struct.fifo_i32_s* %80, i32 0, i32 4 ; <i32*> [#uses=1]
  %82 = load i32* %81, align 4                    ; <i32> [#uses=1]
  %83 = sub i32 %79, %82                          ; <i32> [#uses=1]
  store i32 %83, i32* %num_end, align 4
  %84 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %85 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %86 = sub i32 %84, %85                          ; <i32> [#uses=1]
  store i32 %86, i32* %num_beginning, align 4
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = icmp ne i32 %87, 0                        ; <i1> [#uses=1]
  br i1 %88, label %bb12, label %bb13

bb12:                                             ; preds = %bb11
  %89 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %90 = mul i32 %89, 4                            ; <i32> [#uses=1]
  %91 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %92 = getelementptr inbounds %struct.fifo_i32_s* %91, i32 0, i32 2 ; <i32**> [#uses=1]
  %93 = load i32** %92, align 4                   ; <i32*> [#uses=1]
  %94 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %95 = getelementptr inbounds %struct.fifo_i32_s* %94, i32 0, i32 1 ; <i32**> [#uses=1]
  %96 = load i32** %95, align 4                   ; <i32*> [#uses=1]
  %97 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %98 = getelementptr inbounds %struct.fifo_i32_s* %97, i32 0, i32 4 ; <i32*> [#uses=1]
  %99 = load i32* %98, align 4                    ; <i32> [#uses=1]
  %100 = getelementptr inbounds i32* %96, i32 %99 ; <i32*> [#uses=1]
  %101 = bitcast i32* %100 to i8*                 ; <i8*> [#uses=1]
  %102 = bitcast i32* %93 to i8*                  ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %101, i8* %102, i32 %90, i32 1)
  br label %bb13

bb13:                                             ; preds = %bb12, %bb11
  %103 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %104 = icmp ne i32 %103, 0                      ; <i1> [#uses=1]
  br i1 %104, label %bb14, label %bb15

bb14:                                             ; preds = %bb13
  %105 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  %106 = mul i32 %105, 4                          ; <i32> [#uses=1]
  %107 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %108 = getelementptr inbounds %struct.fifo_i32_s* %107, i32 0, i32 2 ; <i32**> [#uses=1]
  %109 = load i32** %108, align 4                 ; <i32*> [#uses=1]
  %110 = load i32* %num_end, align 4              ; <i32> [#uses=1]
  %111 = getelementptr inbounds i32* %109, i32 %110 ; <i32*> [#uses=1]
  %112 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %113 = getelementptr inbounds %struct.fifo_i32_s* %112, i32 0, i32 1 ; <i32**> [#uses=1]
  %114 = load i32** %113, align 4                 ; <i32*> [#uses=1]
  %115 = bitcast i32* %114 to i8*                 ; <i8*> [#uses=1]
  %116 = bitcast i32* %111 to i8*                 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %115, i8* %116, i32 %106, i32 1)
  br label %bb15

bb15:                                             ; preds = %bb14, %bb13
  %117 = load %struct.fifo_i32_s** %fifo_addr, align 4 ; <%struct.fifo_i32_s*> [#uses=1]
  %118 = getelementptr inbounds %struct.fifo_i32_s* %117, i32 0, i32 4 ; <i32*> [#uses=1]
  %119 = load i32* %num_beginning, align 4        ; <i32> [#uses=1]
  store i32 %119, i32* %118, align 4
  br label %bb16

bb16:                                             ; preds = %bb15, %bb10, %bb8
  br label %return

return:                                           ; preds = %bb16
  ret void
}
