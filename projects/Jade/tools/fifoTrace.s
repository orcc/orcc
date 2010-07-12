; ModuleID = 'fifo.c'
target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32-n8:16:32"
target triple = "i386-mingw32"

%struct.FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }
%struct.fifo_char_s = type { i32, i8*, i32, i32, i32, %struct.FILE*, [1024 x i8] }
%struct.fifo_int_s = type { i32, i32*, i32, i32, i32, %struct.FILE*, [1024 x i32] }
%struct.fifo_short_s = type { i32, i16*, i32, i32, i32, %struct.FILE*, [1024 x i16] }
%struct.fifo_u_char_s = type { i32, i8*, i32, i32, i32, %struct.FILE*, [1024 x i8] }
%struct.fifo_u_int_s = type { i32, i32*, i32, i32, i32, %struct.FILE*, [1024 x i32] }
%struct.fifo_u_short_s = type { i32, i16*, i32, i32, i32, %struct.FILE*, [1024 x i16] }

@.str = private constant [5 x i8] c"%d \0A\00", align 1 ; <[5 x i8]*> [#uses=1]

define internal i32 @fifo_char_has_tokens(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_char_has_room(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_char_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i8* @fifo_char_peek(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_char_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_char_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_char_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_char_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_char_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
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
  %30 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_char_s* %30, i32 0, i32 1 ; <i8**> [#uses=1]
  %32 = load i8** %31, align 4                    ; <i8*> [#uses=1]
  %33 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %34 = getelementptr inbounds %struct.fifo_char_s* %33, i32 0, i32 2 ; <i32*> [#uses=1]
  %35 = load i32* %34, align 4                    ; <i32> [#uses=1]
  %36 = getelementptr inbounds i8* %32, i32 %35   ; <i8*> [#uses=1]
  %37 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_char_s* %37, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i8]* %38, i32 0, i32 0 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %39, i8* %36, i32 %29, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %40 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %41 = icmp ne i32 %40, 0                        ; <i1> [#uses=1]
  br i1 %41, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %42 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %43 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_char_s* %43, i32 0, i32 1 ; <i8**> [#uses=1]
  %45 = load i8** %44, align 4                    ; <i8*> [#uses=1]
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_char_s* %47, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %49 = getelementptr inbounds [1024 x i8]* %48, i32 0, i32 %46 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %49, i8* %45, i32 %42, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %50 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_char_s* %50, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
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

define internal i8* @fifo_char_read(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i8* @fifo_char_peek(%struct.fifo_char_s* %1, i32 %2) nounwind ; <i8*> [#uses=1]
  store i8* %3, i8** %0, align 4
  %4 = load i8** %0, align 4                      ; <i8*> [#uses=1]
  store i8* %4, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_char_read_end(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_char_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_char_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_char_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_char_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_char_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_char_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_char_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_char_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub nsw i32 %27, %30                      ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_char_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i8* @fifo_char_write(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_char_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_char_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_char_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_char_s* %17, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
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

define internal void @fifo_char_write_end(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=21]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %i = alloca i32                                 ; <i32*> [#uses=4]
  %cpt = alloca i32                               ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_char_s* %0, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %2 = load %struct.FILE** %1, align 4            ; <%struct.FILE*> [#uses=1]
  %3 = icmp ne %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb5

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  store i32 0, i32* %cpt, align 4
  br label %bb4

bb1:                                              ; preds = %bb4
  %4 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_char_s* %4, i32 0, i32 0 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %cpt, align 4                    ; <i32> [#uses=1]
  %8 = icmp eq i32 %6, %7                         ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  store i32 0, i32* %cpt, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %9 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_char_s* %9, i32 0, i32 1 ; <i8**> [#uses=1]
  %11 = load i8** %10, align 4                    ; <i8*> [#uses=1]
  %12 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_char_s* %12, i32 0, i32 3 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i8* %11, i32 %16   ; <i8*> [#uses=1]
  %18 = load i8* %17, align 1                     ; <i8> [#uses=1]
  %19 = sext i8 %18 to i32                        ; <i32> [#uses=1]
  %20 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_char_s* %20, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* %22, i8* getelementptr inbounds ([5 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %25 = getelementptr inbounds %struct.fifo_char_s* %24, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %26 = load %struct.FILE** %25, align 4          ; <%struct.FILE*> [#uses=1]
  %27 = call i32 @fflush(%struct.FILE* %26) nounwind ; <i32> [#uses=0]
  %28 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %29 = add nsw i32 %28, 1                        ; <i32> [#uses=1]
  store i32 %29, i32* %i, align 4
  %30 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %31 = add nsw i32 %30, 1                        ; <i32> [#uses=1]
  store i32 %31, i32* %cpt, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %34 = icmp slt i32 %32, %33                     ; <i1> [#uses=1]
  br i1 %34, label %bb1, label %bb5

bb5:                                              ; preds = %bb4, %entry
  %35 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_char_s* %35, i32 0, i32 4 ; <i32*> [#uses=1]
  %37 = load i32* %36, align 4                    ; <i32> [#uses=1]
  %38 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %39 = add nsw i32 %37, %38                      ; <i32> [#uses=1]
  %40 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_char_s* %40, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %39, i32* %41, align 4
  %42 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_char_s* %42, i32 0, i32 3 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 4                    ; <i32> [#uses=1]
  %45 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %46 = add nsw i32 %44, %45                      ; <i32> [#uses=1]
  %47 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_char_s* %47, i32 0, i32 0 ; <i32*> [#uses=1]
  %49 = load i32* %48, align 4                    ; <i32> [#uses=1]
  %50 = icmp sle i32 %46, %49                     ; <i1> [#uses=1]
  br i1 %50, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %51 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_char_s* %51, i32 0, i32 3 ; <i32*> [#uses=1]
  %53 = load i32* %52, align 4                    ; <i32> [#uses=1]
  %54 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %55 = add nsw i32 %53, %54                      ; <i32> [#uses=1]
  %56 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_char_s* %56, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %55, i32* %57, align 4
  br label %bb12

bb7:                                              ; preds = %bb5
  %58 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %59 = getelementptr inbounds %struct.fifo_char_s* %58, i32 0, i32 0 ; <i32*> [#uses=1]
  %60 = load i32* %59, align 4                    ; <i32> [#uses=1]
  %61 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_char_s* %61, i32 0, i32 3 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = sub nsw i32 %60, %63                      ; <i32> [#uses=1]
  store i32 %64, i32* %num_end, align 4
  %65 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %66 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %67 = sub nsw i32 %65, %66                      ; <i32> [#uses=1]
  store i32 %67, i32* %num_beginning, align 4
  %68 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %69 = icmp ne i32 %68, 0                        ; <i1> [#uses=1]
  br i1 %69, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %70 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %71 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %72 = getelementptr inbounds %struct.fifo_char_s* %71, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %73 = getelementptr inbounds [1024 x i8]* %72, i32 0, i32 0 ; <i8*> [#uses=1]
  %74 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_char_s* %74, i32 0, i32 1 ; <i8**> [#uses=1]
  %76 = load i8** %75, align 4                    ; <i8*> [#uses=1]
  %77 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_char_s* %77, i32 0, i32 3 ; <i32*> [#uses=1]
  %79 = load i32* %78, align 4                    ; <i32> [#uses=1]
  %80 = getelementptr inbounds i8* %76, i32 %79   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %80, i8* %73, i32 %70, i32 1)
  br label %bb9

bb9:                                              ; preds = %bb8, %bb7
  %81 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %82 = icmp ne i32 %81, 0                        ; <i1> [#uses=1]
  br i1 %82, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %83 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %84 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %85 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %86 = getelementptr inbounds %struct.fifo_char_s* %85, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %87 = getelementptr inbounds [1024 x i8]* %86, i32 0, i32 %84 ; <i8*> [#uses=1]
  %88 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %89 = getelementptr inbounds %struct.fifo_char_s* %88, i32 0, i32 1 ; <i8**> [#uses=1]
  %90 = load i8** %89, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %90, i8* %87, i32 %83, i32 1)
  br label %bb11

bb11:                                             ; preds = %bb10, %bb9
  %91 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %92 = getelementptr inbounds %struct.fifo_char_s* %91, i32 0, i32 3 ; <i32*> [#uses=1]
  %93 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %93, i32* %92, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb6
  br label %return

return:                                           ; preds = %bb12
  ret void
}

declare i32 @fprintf(%struct.FILE*, i8*, ...) nounwind

declare i32 @fflush(%struct.FILE*) nounwind

define internal i32 @fifo_u_char_has_tokens(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_u_char_has_room(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_char_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i8* @fifo_u_char_peek(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_char_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_char_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_char_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_char_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_char_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
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
  %30 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %31 = getelementptr inbounds %struct.fifo_char_s* %30, i32 0, i32 1 ; <i8**> [#uses=1]
  %32 = load i8** %31, align 4                    ; <i8*> [#uses=1]
  %33 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %34 = getelementptr inbounds %struct.fifo_char_s* %33, i32 0, i32 2 ; <i32*> [#uses=1]
  %35 = load i32* %34, align 4                    ; <i32> [#uses=1]
  %36 = getelementptr inbounds i8* %32, i32 %35   ; <i8*> [#uses=1]
  %37 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %38 = getelementptr inbounds %struct.fifo_char_s* %37, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %39 = getelementptr inbounds [1024 x i8]* %38, i32 0, i32 0 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %39, i8* %36, i32 %29, i32 1)
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %40 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %41 = icmp ne i32 %40, 0                        ; <i1> [#uses=1]
  br i1 %41, label %bb4, label %bb5

bb4:                                              ; preds = %bb3
  %42 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %43 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %44 = getelementptr inbounds %struct.fifo_char_s* %43, i32 0, i32 1 ; <i8**> [#uses=1]
  %45 = load i8** %44, align 4                    ; <i8*> [#uses=1]
  %46 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %47 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_char_s* %47, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %49 = getelementptr inbounds [1024 x i8]* %48, i32 0, i32 %46 ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %49, i8* %45, i32 %42, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %50 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_char_s* %50, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
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

define internal i8* @fifo_u_char_read(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i8* @fifo_u_char_peek(%struct.fifo_char_s* %1, i32 %2) nounwind ; <i8*> [#uses=1]
  store i8* %3, i8** %0, align 4
  %4 = load i8** %0, align 4                      ; <i8*> [#uses=1]
  store i8* %4, i8** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i8** %retval                    ; <i8*> [#uses=1]
  ret i8* %retval1
}

define internal void @fifo_u_char_read_end(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_char_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_char_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_char_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_char_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_char_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_char_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_char_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_char_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub nsw i32 %27, %30                      ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_char_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i8* @fifo_u_char_write(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i8*                            ; <i8**> [#uses=2]
  %0 = alloca i8*                                 ; <i8**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_char_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_char_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_char_s* %10, i32 0, i32 1 ; <i8**> [#uses=1]
  %12 = load i8** %11, align 4                    ; <i8*> [#uses=1]
  %13 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_char_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i8* %12, i32 %15   ; <i8*> [#uses=1]
  store i8* %16, i8** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_char_s* %17, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
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

define internal void @fifo_u_char_write_end(%struct.fifo_char_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_char_s*        ; <%struct.fifo_char_s**> [#uses=21]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %i = alloca i32                                 ; <i32*> [#uses=4]
  %cpt = alloca i32                               ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_char_s* %fifo, %struct.fifo_char_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_char_s* %0, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %2 = load %struct.FILE** %1, align 4            ; <%struct.FILE*> [#uses=1]
  %3 = icmp ne %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb5

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  store i32 0, i32* %cpt, align 4
  br label %bb4

bb1:                                              ; preds = %bb4
  %4 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_char_s* %4, i32 0, i32 0 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %cpt, align 4                    ; <i32> [#uses=1]
  %8 = icmp eq i32 %6, %7                         ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  store i32 0, i32* %cpt, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %9 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_char_s* %9, i32 0, i32 1 ; <i8**> [#uses=1]
  %11 = load i8** %10, align 4                    ; <i8*> [#uses=1]
  %12 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_char_s* %12, i32 0, i32 3 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i8* %11, i32 %16   ; <i8*> [#uses=1]
  %18 = load i8* %17, align 1                     ; <i8> [#uses=1]
  %19 = zext i8 %18 to i32                        ; <i32> [#uses=1]
  %20 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_char_s* %20, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* %22, i8* getelementptr inbounds ([5 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %25 = getelementptr inbounds %struct.fifo_char_s* %24, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %26 = load %struct.FILE** %25, align 4          ; <%struct.FILE*> [#uses=1]
  %27 = call i32 @fflush(%struct.FILE* %26) nounwind ; <i32> [#uses=0]
  %28 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %29 = add nsw i32 %28, 1                        ; <i32> [#uses=1]
  store i32 %29, i32* %i, align 4
  %30 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %31 = add nsw i32 %30, 1                        ; <i32> [#uses=1]
  store i32 %31, i32* %cpt, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %34 = icmp slt i32 %32, %33                     ; <i1> [#uses=1]
  br i1 %34, label %bb1, label %bb5

bb5:                                              ; preds = %bb4, %entry
  %35 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_char_s* %35, i32 0, i32 4 ; <i32*> [#uses=1]
  %37 = load i32* %36, align 4                    ; <i32> [#uses=1]
  %38 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %39 = add nsw i32 %37, %38                      ; <i32> [#uses=1]
  %40 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_char_s* %40, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %39, i32* %41, align 4
  %42 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_char_s* %42, i32 0, i32 3 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 4                    ; <i32> [#uses=1]
  %45 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %46 = add nsw i32 %44, %45                      ; <i32> [#uses=1]
  %47 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_char_s* %47, i32 0, i32 0 ; <i32*> [#uses=1]
  %49 = load i32* %48, align 4                    ; <i32> [#uses=1]
  %50 = icmp sle i32 %46, %49                     ; <i1> [#uses=1]
  br i1 %50, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %51 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_char_s* %51, i32 0, i32 3 ; <i32*> [#uses=1]
  %53 = load i32* %52, align 4                    ; <i32> [#uses=1]
  %54 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %55 = add nsw i32 %53, %54                      ; <i32> [#uses=1]
  %56 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_char_s* %56, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %55, i32* %57, align 4
  br label %bb12

bb7:                                              ; preds = %bb5
  %58 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %59 = getelementptr inbounds %struct.fifo_char_s* %58, i32 0, i32 0 ; <i32*> [#uses=1]
  %60 = load i32* %59, align 4                    ; <i32> [#uses=1]
  %61 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_char_s* %61, i32 0, i32 3 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = sub nsw i32 %60, %63                      ; <i32> [#uses=1]
  store i32 %64, i32* %num_end, align 4
  %65 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %66 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %67 = sub nsw i32 %65, %66                      ; <i32> [#uses=1]
  store i32 %67, i32* %num_beginning, align 4
  %68 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %69 = icmp ne i32 %68, 0                        ; <i1> [#uses=1]
  br i1 %69, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %70 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %71 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %72 = getelementptr inbounds %struct.fifo_char_s* %71, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %73 = getelementptr inbounds [1024 x i8]* %72, i32 0, i32 0 ; <i8*> [#uses=1]
  %74 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_char_s* %74, i32 0, i32 1 ; <i8**> [#uses=1]
  %76 = load i8** %75, align 4                    ; <i8*> [#uses=1]
  %77 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_char_s* %77, i32 0, i32 3 ; <i32*> [#uses=1]
  %79 = load i32* %78, align 4                    ; <i32> [#uses=1]
  %80 = getelementptr inbounds i8* %76, i32 %79   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %80, i8* %73, i32 %70, i32 1)
  br label %bb9

bb9:                                              ; preds = %bb8, %bb7
  %81 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %82 = icmp ne i32 %81, 0                        ; <i1> [#uses=1]
  br i1 %82, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %83 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %84 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %85 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %86 = getelementptr inbounds %struct.fifo_char_s* %85, i32 0, i32 6 ; <[1024 x i8]*> [#uses=1]
  %87 = getelementptr inbounds [1024 x i8]* %86, i32 0, i32 %84 ; <i8*> [#uses=1]
  %88 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %89 = getelementptr inbounds %struct.fifo_char_s* %88, i32 0, i32 1 ; <i8**> [#uses=1]
  %90 = load i8** %89, align 4                    ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %90, i8* %87, i32 %83, i32 1)
  br label %bb11

bb11:                                             ; preds = %bb10, %bb9
  %91 = load %struct.fifo_char_s** %fifo_addr, align 4 ; <%struct.fifo_char_s*> [#uses=1]
  %92 = getelementptr inbounds %struct.fifo_char_s* %91, i32 0, i32 3 ; <i32*> [#uses=1]
  %93 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %93, i32* %92, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb6
  br label %return

return:                                           ; preds = %bb12
  ret void
}

define internal i32 @fifo_short_has_tokens(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_short_has_room(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_short_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i16* @fifo_short_peek(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_short_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_short_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_short_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_short_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_short_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
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
  %31 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_short_s* %31, i32 0, i32 1 ; <i16**> [#uses=1]
  %33 = load i16** %32, align 4                   ; <i16*> [#uses=1]
  %34 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_short_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i16* %33, i32 %36  ; <i16*> [#uses=1]
  %38 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_short_s* %38, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
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
  %47 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_short_s* %47, i32 0, i32 1 ; <i16**> [#uses=1]
  %49 = load i16** %48, align 4                   ; <i16*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_short_s* %51, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i16]* %52, i32 0, i32 %50 ; <i16*> [#uses=1]
  %54 = bitcast i16* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i16* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_short_s* %56, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
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

define internal i16* @fifo_short_read(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i16* @fifo_short_peek(%struct.fifo_short_s* %1, i32 %2) nounwind ; <i16*> [#uses=1]
  store i16* %3, i16** %0, align 4
  %4 = load i16** %0, align 4                     ; <i16*> [#uses=1]
  store i16* %4, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_short_read_end(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_short_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_short_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_short_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_short_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_short_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_short_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_short_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_short_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub nsw i32 %27, %30                      ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_short_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i16* @fifo_short_write(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_short_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_short_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_short_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_short_s* %17, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
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

define internal void @fifo_short_write_end(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=21]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %i = alloca i32                                 ; <i32*> [#uses=4]
  %cpt = alloca i32                               ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_short_s* %0, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %2 = load %struct.FILE** %1, align 4            ; <%struct.FILE*> [#uses=1]
  %3 = icmp ne %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb5

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  store i32 0, i32* %cpt, align 4
  br label %bb4

bb1:                                              ; preds = %bb4
  %4 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_short_s* %4, i32 0, i32 0 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %cpt, align 4                    ; <i32> [#uses=1]
  %8 = icmp eq i32 %6, %7                         ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  store i32 0, i32* %cpt, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %9 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_short_s* %9, i32 0, i32 1 ; <i16**> [#uses=1]
  %11 = load i16** %10, align 4                   ; <i16*> [#uses=1]
  %12 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_short_s* %12, i32 0, i32 3 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i16* %11, i32 %16  ; <i16*> [#uses=1]
  %18 = load i16* %17, align 1                    ; <i16> [#uses=1]
  %19 = sext i16 %18 to i32                       ; <i32> [#uses=1]
  %20 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_short_s* %20, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* %22, i8* getelementptr inbounds ([5 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %25 = getelementptr inbounds %struct.fifo_short_s* %24, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %26 = load %struct.FILE** %25, align 4          ; <%struct.FILE*> [#uses=1]
  %27 = call i32 @fflush(%struct.FILE* %26) nounwind ; <i32> [#uses=0]
  %28 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %29 = add nsw i32 %28, 1                        ; <i32> [#uses=1]
  store i32 %29, i32* %i, align 4
  %30 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %31 = add nsw i32 %30, 1                        ; <i32> [#uses=1]
  store i32 %31, i32* %cpt, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %34 = icmp slt i32 %32, %33                     ; <i1> [#uses=1]
  br i1 %34, label %bb1, label %bb5

bb5:                                              ; preds = %bb4, %entry
  %35 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_short_s* %35, i32 0, i32 4 ; <i32*> [#uses=1]
  %37 = load i32* %36, align 4                    ; <i32> [#uses=1]
  %38 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %39 = add nsw i32 %37, %38                      ; <i32> [#uses=1]
  %40 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_short_s* %40, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %39, i32* %41, align 4
  %42 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_short_s* %42, i32 0, i32 3 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 4                    ; <i32> [#uses=1]
  %45 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %46 = add nsw i32 %44, %45                      ; <i32> [#uses=1]
  %47 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_short_s* %47, i32 0, i32 0 ; <i32*> [#uses=1]
  %49 = load i32* %48, align 4                    ; <i32> [#uses=1]
  %50 = icmp sle i32 %46, %49                     ; <i1> [#uses=1]
  br i1 %50, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %51 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_short_s* %51, i32 0, i32 3 ; <i32*> [#uses=1]
  %53 = load i32* %52, align 4                    ; <i32> [#uses=1]
  %54 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %55 = add nsw i32 %53, %54                      ; <i32> [#uses=1]
  %56 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_short_s* %56, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %55, i32* %57, align 4
  br label %bb12

bb7:                                              ; preds = %bb5
  %58 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %59 = getelementptr inbounds %struct.fifo_short_s* %58, i32 0, i32 0 ; <i32*> [#uses=1]
  %60 = load i32* %59, align 4                    ; <i32> [#uses=1]
  %61 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_short_s* %61, i32 0, i32 3 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = sub nsw i32 %60, %63                      ; <i32> [#uses=1]
  store i32 %64, i32* %num_end, align 4
  %65 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %66 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %67 = sub nsw i32 %65, %66                      ; <i32> [#uses=1]
  store i32 %67, i32* %num_beginning, align 4
  %68 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %69 = icmp ne i32 %68, 0                        ; <i1> [#uses=1]
  br i1 %69, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %70 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %71 = mul i32 %70, 2                            ; <i32> [#uses=1]
  %72 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %73 = getelementptr inbounds %struct.fifo_short_s* %72, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
  %74 = getelementptr inbounds [1024 x i16]* %73, i32 0, i32 0 ; <i16*> [#uses=1]
  %75 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %76 = getelementptr inbounds %struct.fifo_short_s* %75, i32 0, i32 1 ; <i16**> [#uses=1]
  %77 = load i16** %76, align 4                   ; <i16*> [#uses=1]
  %78 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %79 = getelementptr inbounds %struct.fifo_short_s* %78, i32 0, i32 3 ; <i32*> [#uses=1]
  %80 = load i32* %79, align 4                    ; <i32> [#uses=1]
  %81 = getelementptr inbounds i16* %77, i32 %80  ; <i16*> [#uses=1]
  %82 = bitcast i16* %81 to i8*                   ; <i8*> [#uses=1]
  %83 = bitcast i16* %74 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %82, i8* %83, i32 %71, i32 1)
  br label %bb9

bb9:                                              ; preds = %bb8, %bb7
  %84 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %85 = icmp ne i32 %84, 0                        ; <i1> [#uses=1]
  br i1 %85, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %86 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %87 = mul i32 %86, 2                            ; <i32> [#uses=1]
  %88 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %89 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %90 = getelementptr inbounds %struct.fifo_short_s* %89, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
  %91 = getelementptr inbounds [1024 x i16]* %90, i32 0, i32 %88 ; <i16*> [#uses=1]
  %92 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %93 = getelementptr inbounds %struct.fifo_short_s* %92, i32 0, i32 1 ; <i16**> [#uses=1]
  %94 = load i16** %93, align 4                   ; <i16*> [#uses=1]
  %95 = bitcast i16* %94 to i8*                   ; <i8*> [#uses=1]
  %96 = bitcast i16* %91 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %95, i8* %96, i32 %87, i32 1)
  br label %bb11

bb11:                                             ; preds = %bb10, %bb9
  %97 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %98 = getelementptr inbounds %struct.fifo_short_s* %97, i32 0, i32 3 ; <i32*> [#uses=1]
  %99 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %99, i32* %98, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb6
  br label %return

return:                                           ; preds = %bb12
  ret void
}

define internal i32 @fifo_u_short_has_tokens(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_u_short_has_room(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_short_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i16* @fifo_u_short_peek(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_short_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_short_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_short_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_short_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_short_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
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
  %31 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_short_s* %31, i32 0, i32 1 ; <i16**> [#uses=1]
  %33 = load i16** %32, align 4                   ; <i16*> [#uses=1]
  %34 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_short_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i16* %33, i32 %36  ; <i16*> [#uses=1]
  %38 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_short_s* %38, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
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
  %47 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_short_s* %47, i32 0, i32 1 ; <i16**> [#uses=1]
  %49 = load i16** %48, align 4                   ; <i16*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_short_s* %51, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i16]* %52, i32 0, i32 %50 ; <i16*> [#uses=1]
  %54 = bitcast i16* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i16* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_short_s* %56, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
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

define internal i16* @fifo_u_short_read(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i16* @fifo_u_short_peek(%struct.fifo_short_s* %1, i32 %2) nounwind ; <i16*> [#uses=1]
  store i16* %3, i16** %0, align 4
  %4 = load i16** %0, align 4                     ; <i16*> [#uses=1]
  store i16* %4, i16** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i16** %retval                   ; <i16*> [#uses=1]
  ret i16* %retval1
}

define internal void @fifo_u_short_read_end(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_short_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_short_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_short_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_short_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_short_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_short_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_short_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_short_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub nsw i32 %27, %30                      ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_short_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i16* @fifo_u_short_write(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i16*                           ; <i16**> [#uses=2]
  %0 = alloca i16*                                ; <i16**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_short_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_short_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_short_s* %10, i32 0, i32 1 ; <i16**> [#uses=1]
  %12 = load i16** %11, align 4                   ; <i16*> [#uses=1]
  %13 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_short_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i16* %12, i32 %15  ; <i16*> [#uses=1]
  store i16* %16, i16** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_short_s* %17, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
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

define internal void @fifo_u_short_write_end(%struct.fifo_short_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_short_s*       ; <%struct.fifo_short_s**> [#uses=21]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %i = alloca i32                                 ; <i32*> [#uses=4]
  %cpt = alloca i32                               ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_short_s* %fifo, %struct.fifo_short_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_short_s* %0, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %2 = load %struct.FILE** %1, align 4            ; <%struct.FILE*> [#uses=1]
  %3 = icmp ne %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb5

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  store i32 0, i32* %cpt, align 4
  br label %bb4

bb1:                                              ; preds = %bb4
  %4 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_short_s* %4, i32 0, i32 0 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %cpt, align 4                    ; <i32> [#uses=1]
  %8 = icmp eq i32 %6, %7                         ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  store i32 0, i32* %cpt, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %9 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_short_s* %9, i32 0, i32 1 ; <i16**> [#uses=1]
  %11 = load i16** %10, align 4                   ; <i16*> [#uses=1]
  %12 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_short_s* %12, i32 0, i32 3 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i16* %11, i32 %16  ; <i16*> [#uses=1]
  %18 = load i16* %17, align 1                    ; <i16> [#uses=1]
  %19 = zext i16 %18 to i32                       ; <i32> [#uses=1]
  %20 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_short_s* %20, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %22 = load %struct.FILE** %21, align 4          ; <%struct.FILE*> [#uses=1]
  %23 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* %22, i8* getelementptr inbounds ([5 x i8]* @.str, i32 0, i32 0), i32 %19) nounwind ; <i32> [#uses=0]
  %24 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %25 = getelementptr inbounds %struct.fifo_short_s* %24, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %26 = load %struct.FILE** %25, align 4          ; <%struct.FILE*> [#uses=1]
  %27 = call i32 @fflush(%struct.FILE* %26) nounwind ; <i32> [#uses=0]
  %28 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %29 = add nsw i32 %28, 1                        ; <i32> [#uses=1]
  store i32 %29, i32* %i, align 4
  %30 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %31 = add nsw i32 %30, 1                        ; <i32> [#uses=1]
  store i32 %31, i32* %cpt, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb
  %32 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %33 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %34 = icmp slt i32 %32, %33                     ; <i1> [#uses=1]
  br i1 %34, label %bb1, label %bb5

bb5:                                              ; preds = %bb4, %entry
  %35 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %36 = getelementptr inbounds %struct.fifo_short_s* %35, i32 0, i32 4 ; <i32*> [#uses=1]
  %37 = load i32* %36, align 4                    ; <i32> [#uses=1]
  %38 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %39 = add nsw i32 %37, %38                      ; <i32> [#uses=1]
  %40 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %41 = getelementptr inbounds %struct.fifo_short_s* %40, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %39, i32* %41, align 4
  %42 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %43 = getelementptr inbounds %struct.fifo_short_s* %42, i32 0, i32 3 ; <i32*> [#uses=1]
  %44 = load i32* %43, align 4                    ; <i32> [#uses=1]
  %45 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %46 = add nsw i32 %44, %45                      ; <i32> [#uses=1]
  %47 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_short_s* %47, i32 0, i32 0 ; <i32*> [#uses=1]
  %49 = load i32* %48, align 4                    ; <i32> [#uses=1]
  %50 = icmp sle i32 %46, %49                     ; <i1> [#uses=1]
  br i1 %50, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %51 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_short_s* %51, i32 0, i32 3 ; <i32*> [#uses=1]
  %53 = load i32* %52, align 4                    ; <i32> [#uses=1]
  %54 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %55 = add nsw i32 %53, %54                      ; <i32> [#uses=1]
  %56 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_short_s* %56, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %55, i32* %57, align 4
  br label %bb12

bb7:                                              ; preds = %bb5
  %58 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %59 = getelementptr inbounds %struct.fifo_short_s* %58, i32 0, i32 0 ; <i32*> [#uses=1]
  %60 = load i32* %59, align 4                    ; <i32> [#uses=1]
  %61 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %62 = getelementptr inbounds %struct.fifo_short_s* %61, i32 0, i32 3 ; <i32*> [#uses=1]
  %63 = load i32* %62, align 4                    ; <i32> [#uses=1]
  %64 = sub nsw i32 %60, %63                      ; <i32> [#uses=1]
  store i32 %64, i32* %num_end, align 4
  %65 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %66 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %67 = sub nsw i32 %65, %66                      ; <i32> [#uses=1]
  store i32 %67, i32* %num_beginning, align 4
  %68 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %69 = icmp ne i32 %68, 0                        ; <i1> [#uses=1]
  br i1 %69, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %70 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %71 = mul i32 %70, 2                            ; <i32> [#uses=1]
  %72 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %73 = getelementptr inbounds %struct.fifo_short_s* %72, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
  %74 = getelementptr inbounds [1024 x i16]* %73, i32 0, i32 0 ; <i16*> [#uses=1]
  %75 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %76 = getelementptr inbounds %struct.fifo_short_s* %75, i32 0, i32 1 ; <i16**> [#uses=1]
  %77 = load i16** %76, align 4                   ; <i16*> [#uses=1]
  %78 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %79 = getelementptr inbounds %struct.fifo_short_s* %78, i32 0, i32 3 ; <i32*> [#uses=1]
  %80 = load i32* %79, align 4                    ; <i32> [#uses=1]
  %81 = getelementptr inbounds i16* %77, i32 %80  ; <i16*> [#uses=1]
  %82 = bitcast i16* %81 to i8*                   ; <i8*> [#uses=1]
  %83 = bitcast i16* %74 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %82, i8* %83, i32 %71, i32 1)
  br label %bb9

bb9:                                              ; preds = %bb8, %bb7
  %84 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %85 = icmp ne i32 %84, 0                        ; <i1> [#uses=1]
  br i1 %85, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %86 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %87 = mul i32 %86, 2                            ; <i32> [#uses=1]
  %88 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %89 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %90 = getelementptr inbounds %struct.fifo_short_s* %89, i32 0, i32 6 ; <[1024 x i16]*> [#uses=1]
  %91 = getelementptr inbounds [1024 x i16]* %90, i32 0, i32 %88 ; <i16*> [#uses=1]
  %92 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %93 = getelementptr inbounds %struct.fifo_short_s* %92, i32 0, i32 1 ; <i16**> [#uses=1]
  %94 = load i16** %93, align 4                   ; <i16*> [#uses=1]
  %95 = bitcast i16* %94 to i8*                   ; <i8*> [#uses=1]
  %96 = bitcast i16* %91 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %95, i8* %96, i32 %87, i32 1)
  br label %bb11

bb11:                                             ; preds = %bb10, %bb9
  %97 = load %struct.fifo_short_s** %fifo_addr, align 4 ; <%struct.fifo_short_s*> [#uses=1]
  %98 = getelementptr inbounds %struct.fifo_short_s* %97, i32 0, i32 3 ; <i32*> [#uses=1]
  %99 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %99, i32* %98, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb6
  br label %return

return:                                           ; preds = %bb12
  ret void
}

define internal i32 @fifo_int_has_tokens(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_int_has_room(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_int_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32* @fifo_int_peek(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_int_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_int_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_int_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_int_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_int_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
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
  %31 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_int_s* %31, i32 0, i32 1 ; <i32**> [#uses=1]
  %33 = load i32** %32, align 4                   ; <i32*> [#uses=1]
  %34 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_int_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i32* %33, i32 %36  ; <i32*> [#uses=1]
  %38 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_int_s* %38, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
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
  %47 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_int_s* %47, i32 0, i32 1 ; <i32**> [#uses=1]
  %49 = load i32** %48, align 4                   ; <i32*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_int_s* %51, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i32]* %52, i32 0, i32 %50 ; <i32*> [#uses=1]
  %54 = bitcast i32* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i32* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_int_s* %56, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
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

define internal i32* @fifo_int_read(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i32* @fifo_int_peek(%struct.fifo_int_s* %1, i32 %2) nounwind ; <i32*> [#uses=1]
  store i32* %3, i32** %0, align 4
  %4 = load i32** %0, align 4                     ; <i32*> [#uses=1]
  store i32* %4, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_int_read_end(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_int_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_int_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_int_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_int_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_int_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_int_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_int_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_int_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub nsw i32 %27, %30                      ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_int_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i32* @fifo_int_write(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_int_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_int_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_int_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_int_s* %17, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
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

define internal void @fifo_int_write_end(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=21]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %i = alloca i32                                 ; <i32*> [#uses=4]
  %cpt = alloca i32                               ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_int_s* %0, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %2 = load %struct.FILE** %1, align 4            ; <%struct.FILE*> [#uses=1]
  %3 = icmp ne %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb5

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  store i32 0, i32* %cpt, align 4
  br label %bb4

bb1:                                              ; preds = %bb4
  %4 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_int_s* %4, i32 0, i32 0 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %cpt, align 4                    ; <i32> [#uses=1]
  %8 = icmp eq i32 %6, %7                         ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  store i32 0, i32* %cpt, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %9 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_int_s* %9, i32 0, i32 1 ; <i32**> [#uses=1]
  %11 = load i32** %10, align 4                   ; <i32*> [#uses=1]
  %12 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_int_s* %12, i32 0, i32 3 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i32* %11, i32 %16  ; <i32*> [#uses=1]
  %18 = load i32* %17, align 1                    ; <i32> [#uses=1]
  %19 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %20 = getelementptr inbounds %struct.fifo_int_s* %19, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %21 = load %struct.FILE** %20, align 4          ; <%struct.FILE*> [#uses=1]
  %22 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* %21, i8* getelementptr inbounds ([5 x i8]* @.str, i32 0, i32 0), i32 %18) nounwind ; <i32> [#uses=0]
  %23 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_int_s* %23, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %25 = load %struct.FILE** %24, align 4          ; <%struct.FILE*> [#uses=1]
  %26 = call i32 @fflush(%struct.FILE* %25) nounwind ; <i32> [#uses=0]
  %27 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %28 = add nsw i32 %27, 1                        ; <i32> [#uses=1]
  store i32 %28, i32* %i, align 4
  %29 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %30 = add nsw i32 %29, 1                        ; <i32> [#uses=1]
  store i32 %30, i32* %cpt, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb
  %31 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %32 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %33 = icmp slt i32 %31, %32                     ; <i1> [#uses=1]
  br i1 %33, label %bb1, label %bb5

bb5:                                              ; preds = %bb4, %entry
  %34 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_int_s* %34, i32 0, i32 4 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_int_s* %39, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %38, i32* %40, align 4
  %41 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_int_s* %41, i32 0, i32 3 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %45 = add nsw i32 %43, %44                      ; <i32> [#uses=1]
  %46 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_int_s* %46, i32 0, i32 0 ; <i32*> [#uses=1]
  %48 = load i32* %47, align 4                    ; <i32> [#uses=1]
  %49 = icmp sle i32 %45, %48                     ; <i1> [#uses=1]
  br i1 %49, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %50 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_int_s* %50, i32 0, i32 3 ; <i32*> [#uses=1]
  %52 = load i32* %51, align 4                    ; <i32> [#uses=1]
  %53 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %54 = add nsw i32 %52, %53                      ; <i32> [#uses=1]
  %55 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %56 = getelementptr inbounds %struct.fifo_int_s* %55, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %54, i32* %56, align 4
  br label %bb12

bb7:                                              ; preds = %bb5
  %57 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_int_s* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  %59 = load i32* %58, align 4                    ; <i32> [#uses=1]
  %60 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %61 = getelementptr inbounds %struct.fifo_int_s* %60, i32 0, i32 3 ; <i32*> [#uses=1]
  %62 = load i32* %61, align 4                    ; <i32> [#uses=1]
  %63 = sub nsw i32 %59, %62                      ; <i32> [#uses=1]
  store i32 %63, i32* %num_end, align 4
  %64 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %65 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %66 = sub nsw i32 %64, %65                      ; <i32> [#uses=1]
  store i32 %66, i32* %num_beginning, align 4
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = icmp ne i32 %67, 0                        ; <i1> [#uses=1]
  br i1 %68, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %69 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %70 = mul i32 %69, 4                            ; <i32> [#uses=1]
  %71 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %72 = getelementptr inbounds %struct.fifo_int_s* %71, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
  %73 = getelementptr inbounds [1024 x i32]* %72, i32 0, i32 0 ; <i32*> [#uses=1]
  %74 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_int_s* %74, i32 0, i32 1 ; <i32**> [#uses=1]
  %76 = load i32** %75, align 4                   ; <i32*> [#uses=1]
  %77 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_int_s* %77, i32 0, i32 3 ; <i32*> [#uses=1]
  %79 = load i32* %78, align 4                    ; <i32> [#uses=1]
  %80 = getelementptr inbounds i32* %76, i32 %79  ; <i32*> [#uses=1]
  %81 = bitcast i32* %80 to i8*                   ; <i8*> [#uses=1]
  %82 = bitcast i32* %73 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %81, i8* %82, i32 %70, i32 1)
  br label %bb9

bb9:                                              ; preds = %bb8, %bb7
  %83 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %84 = icmp ne i32 %83, 0                        ; <i1> [#uses=1]
  br i1 %84, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %85 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %86 = mul i32 %85, 4                            ; <i32> [#uses=1]
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %89 = getelementptr inbounds %struct.fifo_int_s* %88, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
  %90 = getelementptr inbounds [1024 x i32]* %89, i32 0, i32 %87 ; <i32*> [#uses=1]
  %91 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %92 = getelementptr inbounds %struct.fifo_int_s* %91, i32 0, i32 1 ; <i32**> [#uses=1]
  %93 = load i32** %92, align 4                   ; <i32*> [#uses=1]
  %94 = bitcast i32* %93 to i8*                   ; <i8*> [#uses=1]
  %95 = bitcast i32* %90 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %94, i8* %95, i32 %86, i32 1)
  br label %bb11

bb11:                                             ; preds = %bb10, %bb9
  %96 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %97 = getelementptr inbounds %struct.fifo_int_s* %96, i32 0, i32 3 ; <i32*> [#uses=1]
  %98 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %98, i32* %97, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb6
  br label %return

return:                                           ; preds = %bb12
  ret void
}

define internal i32 @fifo_u_int_has_tokens(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32 @fifo_u_int_has_room(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=3]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32                            ; <i32*> [#uses=2]
  %0 = alloca i32                                 ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 0 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_int_s* %4, i32 0, i32 4 ; <i32*> [#uses=1]
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

define internal i32* @fifo_u_int_peek(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=13]
  %n_addr = alloca i32                            ; <i32*> [#uses=3]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 2 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_int_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_int_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_int_s* %13, i32 0, i32 2 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb6

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_int_s* %17, i32 0, i32 0 ; <i32*> [#uses=1]
  %19 = load i32* %18, align 4                    ; <i32> [#uses=1]
  %20 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %21 = getelementptr inbounds %struct.fifo_int_s* %20, i32 0, i32 2 ; <i32*> [#uses=1]
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
  %31 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %32 = getelementptr inbounds %struct.fifo_int_s* %31, i32 0, i32 1 ; <i32**> [#uses=1]
  %33 = load i32** %32, align 4                   ; <i32*> [#uses=1]
  %34 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_int_s* %34, i32 0, i32 2 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = getelementptr inbounds i32* %33, i32 %36  ; <i32*> [#uses=1]
  %38 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %39 = getelementptr inbounds %struct.fifo_int_s* %38, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
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
  %47 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %48 = getelementptr inbounds %struct.fifo_int_s* %47, i32 0, i32 1 ; <i32**> [#uses=1]
  %49 = load i32** %48, align 4                   ; <i32*> [#uses=1]
  %50 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %51 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %52 = getelementptr inbounds %struct.fifo_int_s* %51, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
  %53 = getelementptr inbounds [1024 x i32]* %52, i32 0, i32 %50 ; <i32*> [#uses=1]
  %54 = bitcast i32* %53 to i8*                   ; <i8*> [#uses=1]
  %55 = bitcast i32* %49 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %54, i8* %55, i32 %46, i32 1)
  br label %bb5

bb5:                                              ; preds = %bb4, %bb3
  %56 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %57 = getelementptr inbounds %struct.fifo_int_s* %56, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
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

define internal i32* @fifo_u_int_read(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=2]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %3 = call i32* @fifo_u_int_peek(%struct.fifo_int_s* %1, i32 %2) nounwind ; <i32*> [#uses=1]
  store i32* %3, i32** %0, align 4
  %4 = load i32** %0, align 4                     ; <i32*> [#uses=1]
  store i32* %4, i32** %retval, align 4
  br label %return

return:                                           ; preds = %entry
  %retval1 = load i32** %retval                   ; <i32*> [#uses=1]
  ret i32* %retval1
}

define internal void @fifo_u_int_read_end(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=10]
  %n_addr = alloca i32                            ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=2]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_int_s* %0, i32 0, i32 4 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %4 = sub nsw i32 %2, %3                         ; <i32> [#uses=1]
  %5 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %6 = getelementptr inbounds %struct.fifo_int_s* %5, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %4, i32* %6, align 4
  %7 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %8 = getelementptr inbounds %struct.fifo_int_s* %7, i32 0, i32 2 ; <i32*> [#uses=1]
  %9 = load i32* %8, align 4                      ; <i32> [#uses=1]
  %10 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %11 = add nsw i32 %9, %10                       ; <i32> [#uses=1]
  %12 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_int_s* %12, i32 0, i32 0 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = icmp sle i32 %11, %14                     ; <i1> [#uses=1]
  br i1 %15, label %bb, label %bb1

bb:                                               ; preds = %entry
  %16 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %17 = getelementptr inbounds %struct.fifo_int_s* %16, i32 0, i32 2 ; <i32*> [#uses=1]
  %18 = load i32* %17, align 4                    ; <i32> [#uses=1]
  %19 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %20 = add nsw i32 %18, %19                      ; <i32> [#uses=1]
  %21 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %22 = getelementptr inbounds %struct.fifo_int_s* %21, i32 0, i32 2 ; <i32*> [#uses=1]
  store i32 %20, i32* %22, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %23 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_int_s* %23, i32 0, i32 2 ; <i32*> [#uses=1]
  %25 = load i32* %24, align 4                    ; <i32> [#uses=1]
  %26 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %27 = add nsw i32 %25, %26                      ; <i32> [#uses=1]
  %28 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %29 = getelementptr inbounds %struct.fifo_int_s* %28, i32 0, i32 0 ; <i32*> [#uses=1]
  %30 = load i32* %29, align 4                    ; <i32> [#uses=1]
  %31 = sub nsw i32 %27, %30                      ; <i32> [#uses=1]
  store i32 %31, i32* %num_beginning, align 4
  %32 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %33 = getelementptr inbounds %struct.fifo_int_s* %32, i32 0, i32 2 ; <i32*> [#uses=1]
  %34 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %34, i32* %33, align 4
  br label %bb2

bb2:                                              ; preds = %bb1, %bb
  br label %return

return:                                           ; preds = %bb2
  ret void
}

define internal i32* @fifo_u_int_write(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=6]
  %n_addr = alloca i32                            ; <i32*> [#uses=2]
  %retval = alloca i32*                           ; <i32**> [#uses=2]
  %0 = alloca i32*                                ; <i32**> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %1 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %2 = getelementptr inbounds %struct.fifo_int_s* %1, i32 0, i32 3 ; <i32*> [#uses=1]
  %3 = load i32* %2, align 4                      ; <i32> [#uses=1]
  %4 = load i32* %n_addr, align 4                 ; <i32> [#uses=1]
  %5 = add nsw i32 %3, %4                         ; <i32> [#uses=1]
  %6 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %7 = getelementptr inbounds %struct.fifo_int_s* %6, i32 0, i32 0 ; <i32*> [#uses=1]
  %8 = load i32* %7, align 4                      ; <i32> [#uses=1]
  %9 = icmp sle i32 %5, %8                        ; <i1> [#uses=1]
  br i1 %9, label %bb, label %bb1

bb:                                               ; preds = %entry
  %10 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %11 = getelementptr inbounds %struct.fifo_int_s* %10, i32 0, i32 1 ; <i32**> [#uses=1]
  %12 = load i32** %11, align 4                   ; <i32*> [#uses=1]
  %13 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %14 = getelementptr inbounds %struct.fifo_int_s* %13, i32 0, i32 3 ; <i32*> [#uses=1]
  %15 = load i32* %14, align 4                    ; <i32> [#uses=1]
  %16 = getelementptr inbounds i32* %12, i32 %15  ; <i32*> [#uses=1]
  store i32* %16, i32** %0, align 4
  br label %bb2

bb1:                                              ; preds = %entry
  %17 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %18 = getelementptr inbounds %struct.fifo_int_s* %17, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
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

define internal void @fifo_u_int_write_end(%struct.fifo_int_s* %fifo, i32 %n) nounwind {
entry:
  %fifo_addr = alloca %struct.fifo_int_s*         ; <%struct.fifo_int_s**> [#uses=21]
  %n_addr = alloca i32                            ; <i32*> [#uses=6]
  %i = alloca i32                                 ; <i32*> [#uses=4]
  %cpt = alloca i32                               ; <i32*> [#uses=6]
  %num_end = alloca i32                           ; <i32*> [#uses=5]
  %num_beginning = alloca i32                     ; <i32*> [#uses=4]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store %struct.fifo_int_s* %fifo, %struct.fifo_int_s** %fifo_addr
  store i32 %n, i32* %n_addr
  %0 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %1 = getelementptr inbounds %struct.fifo_int_s* %0, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %2 = load %struct.FILE** %1, align 4            ; <%struct.FILE*> [#uses=1]
  %3 = icmp ne %struct.FILE* %2, null             ; <i1> [#uses=1]
  br i1 %3, label %bb, label %bb5

bb:                                               ; preds = %entry
  store i32 0, i32* %i, align 4
  store i32 0, i32* %cpt, align 4
  br label %bb4

bb1:                                              ; preds = %bb4
  %4 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %5 = getelementptr inbounds %struct.fifo_int_s* %4, i32 0, i32 0 ; <i32*> [#uses=1]
  %6 = load i32* %5, align 4                      ; <i32> [#uses=1]
  %7 = load i32* %cpt, align 4                    ; <i32> [#uses=1]
  %8 = icmp eq i32 %6, %7                         ; <i1> [#uses=1]
  br i1 %8, label %bb2, label %bb3

bb2:                                              ; preds = %bb1
  store i32 0, i32* %cpt, align 4
  br label %bb3

bb3:                                              ; preds = %bb2, %bb1
  %9 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %10 = getelementptr inbounds %struct.fifo_int_s* %9, i32 0, i32 1 ; <i32**> [#uses=1]
  %11 = load i32** %10, align 4                   ; <i32*> [#uses=1]
  %12 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %13 = getelementptr inbounds %struct.fifo_int_s* %12, i32 0, i32 3 ; <i32*> [#uses=1]
  %14 = load i32* %13, align 4                    ; <i32> [#uses=1]
  %15 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %16 = add nsw i32 %14, %15                      ; <i32> [#uses=1]
  %17 = getelementptr inbounds i32* %11, i32 %16  ; <i32*> [#uses=1]
  %18 = load i32* %17, align 1                    ; <i32> [#uses=1]
  %19 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %20 = getelementptr inbounds %struct.fifo_int_s* %19, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %21 = load %struct.FILE** %20, align 4          ; <%struct.FILE*> [#uses=1]
  %22 = call i32 (%struct.FILE*, i8*, ...)* @fprintf(%struct.FILE* %21, i8* getelementptr inbounds ([5 x i8]* @.str, i32 0, i32 0), i32 %18) nounwind ; <i32> [#uses=0]
  %23 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %24 = getelementptr inbounds %struct.fifo_int_s* %23, i32 0, i32 5 ; <%struct.FILE**> [#uses=1]
  %25 = load %struct.FILE** %24, align 4          ; <%struct.FILE*> [#uses=1]
  %26 = call i32 @fflush(%struct.FILE* %25) nounwind ; <i32> [#uses=0]
  %27 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %28 = add nsw i32 %27, 1                        ; <i32> [#uses=1]
  store i32 %28, i32* %i, align 4
  %29 = load i32* %cpt, align 4                   ; <i32> [#uses=1]
  %30 = add nsw i32 %29, 1                        ; <i32> [#uses=1]
  store i32 %30, i32* %cpt, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb
  %31 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %32 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %33 = icmp slt i32 %31, %32                     ; <i1> [#uses=1]
  br i1 %33, label %bb1, label %bb5

bb5:                                              ; preds = %bb4, %entry
  %34 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %35 = getelementptr inbounds %struct.fifo_int_s* %34, i32 0, i32 4 ; <i32*> [#uses=1]
  %36 = load i32* %35, align 4                    ; <i32> [#uses=1]
  %37 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %38 = add nsw i32 %36, %37                      ; <i32> [#uses=1]
  %39 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %40 = getelementptr inbounds %struct.fifo_int_s* %39, i32 0, i32 4 ; <i32*> [#uses=1]
  store i32 %38, i32* %40, align 4
  %41 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %42 = getelementptr inbounds %struct.fifo_int_s* %41, i32 0, i32 3 ; <i32*> [#uses=1]
  %43 = load i32* %42, align 4                    ; <i32> [#uses=1]
  %44 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %45 = add nsw i32 %43, %44                      ; <i32> [#uses=1]
  %46 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %47 = getelementptr inbounds %struct.fifo_int_s* %46, i32 0, i32 0 ; <i32*> [#uses=1]
  %48 = load i32* %47, align 4                    ; <i32> [#uses=1]
  %49 = icmp sle i32 %45, %48                     ; <i1> [#uses=1]
  br i1 %49, label %bb6, label %bb7

bb6:                                              ; preds = %bb5
  %50 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %51 = getelementptr inbounds %struct.fifo_int_s* %50, i32 0, i32 3 ; <i32*> [#uses=1]
  %52 = load i32* %51, align 4                    ; <i32> [#uses=1]
  %53 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %54 = add nsw i32 %52, %53                      ; <i32> [#uses=1]
  %55 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %56 = getelementptr inbounds %struct.fifo_int_s* %55, i32 0, i32 3 ; <i32*> [#uses=1]
  store i32 %54, i32* %56, align 4
  br label %bb12

bb7:                                              ; preds = %bb5
  %57 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %58 = getelementptr inbounds %struct.fifo_int_s* %57, i32 0, i32 0 ; <i32*> [#uses=1]
  %59 = load i32* %58, align 4                    ; <i32> [#uses=1]
  %60 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %61 = getelementptr inbounds %struct.fifo_int_s* %60, i32 0, i32 3 ; <i32*> [#uses=1]
  %62 = load i32* %61, align 4                    ; <i32> [#uses=1]
  %63 = sub nsw i32 %59, %62                      ; <i32> [#uses=1]
  store i32 %63, i32* %num_end, align 4
  %64 = load i32* %n_addr, align 4                ; <i32> [#uses=1]
  %65 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %66 = sub nsw i32 %64, %65                      ; <i32> [#uses=1]
  store i32 %66, i32* %num_beginning, align 4
  %67 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %68 = icmp ne i32 %67, 0                        ; <i1> [#uses=1]
  br i1 %68, label %bb8, label %bb9

bb8:                                              ; preds = %bb7
  %69 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %70 = mul i32 %69, 4                            ; <i32> [#uses=1]
  %71 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %72 = getelementptr inbounds %struct.fifo_int_s* %71, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
  %73 = getelementptr inbounds [1024 x i32]* %72, i32 0, i32 0 ; <i32*> [#uses=1]
  %74 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %75 = getelementptr inbounds %struct.fifo_int_s* %74, i32 0, i32 1 ; <i32**> [#uses=1]
  %76 = load i32** %75, align 4                   ; <i32*> [#uses=1]
  %77 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %78 = getelementptr inbounds %struct.fifo_int_s* %77, i32 0, i32 3 ; <i32*> [#uses=1]
  %79 = load i32* %78, align 4                    ; <i32> [#uses=1]
  %80 = getelementptr inbounds i32* %76, i32 %79  ; <i32*> [#uses=1]
  %81 = bitcast i32* %80 to i8*                   ; <i8*> [#uses=1]
  %82 = bitcast i32* %73 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %81, i8* %82, i32 %70, i32 1)
  br label %bb9

bb9:                                              ; preds = %bb8, %bb7
  %83 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %84 = icmp ne i32 %83, 0                        ; <i1> [#uses=1]
  br i1 %84, label %bb10, label %bb11

bb10:                                             ; preds = %bb9
  %85 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  %86 = mul i32 %85, 4                            ; <i32> [#uses=1]
  %87 = load i32* %num_end, align 4               ; <i32> [#uses=1]
  %88 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %89 = getelementptr inbounds %struct.fifo_int_s* %88, i32 0, i32 6 ; <[1024 x i32]*> [#uses=1]
  %90 = getelementptr inbounds [1024 x i32]* %89, i32 0, i32 %87 ; <i32*> [#uses=1]
  %91 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %92 = getelementptr inbounds %struct.fifo_int_s* %91, i32 0, i32 1 ; <i32**> [#uses=1]
  %93 = load i32** %92, align 4                   ; <i32*> [#uses=1]
  %94 = bitcast i32* %93 to i8*                   ; <i8*> [#uses=1]
  %95 = bitcast i32* %90 to i8*                   ; <i8*> [#uses=1]
  call void @llvm.memcpy.i32(i8* %94, i8* %95, i32 %86, i32 1)
  br label %bb11

bb11:                                             ; preds = %bb10, %bb9
  %96 = load %struct.fifo_int_s** %fifo_addr, align 4 ; <%struct.fifo_int_s*> [#uses=1]
  %97 = getelementptr inbounds %struct.fifo_int_s* %96, i32 0, i32 3 ; <i32*> [#uses=1]
  %98 = load i32* %num_beginning, align 4         ; <i32> [#uses=1]
  store i32 %98, i32* %97, align 4
  br label %bb12

bb12:                                             ; preds = %bb11, %bb6
  br label %return

return:                                           ; preds = %bb12
  ret void
}
