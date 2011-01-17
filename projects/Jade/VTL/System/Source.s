%struct.FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }
@.str = private constant [5 x i8] c"%d \0A\00", align 1 ; <[5 x i8]*> [#uses=1]
@F = internal global %struct.FILE* null           ; <%struct.FILE**> [#uses=7]
@cnt = internal global i32 0                      ; <i32*> [#uses=3]
@input_file = global i8* null              ; <i8**> [#uses=5]
@.str1 = private constant [21 x i8] c"No input file given!\00", align 1 ; <[21 x i8]*> [#uses=1]
@.str2 = private constant [3 x i8] c"rb\00", align 1 ; <[3 x i8]*> [#uses=1]
@.str3 = private constant [7 x i8] c"<null>\00", align 1 ; <[7 x i8]*> [#uses=1]
@.str4 = private constant [26 x i8] c"could not open file \22%s\22\0A\00", align 1 ; <[26 x i8]*> [#uses=1]
@O = global %struct.fifo_i8_s* null ; <%struct.fifo_u8_s**> [#uses=3]

define i1 @isSchedulable_init() nounwind {
	ret i1 1
}


define i32 @init() nounwind {
entry:
  %0 = load i8** @input_file, align 4             ; <i8*> [#uses=1]
  %1 = icmp eq i8* %0, null                       ; <i1> [#uses=1]
  br i1 %1, label %bb, label %bb1

bb:                                               ; preds = %entry
  %2 = call i32 @puts(i8* getelementptr inbounds ([21 x i8]* @.str1, i32 0, i32 0)) nounwind ; <i32> [#uses=0]
  call void @exit(i32 1) noreturn nounwind
  unreachable

bb1:                                              ; preds = %entry
  %3 = load i8** @input_file, align 4             ; <i8*> [#uses=1]
  %4 = call %struct.FILE* @fopen(i8* %3, i8* getelementptr inbounds ([3 x i8]* @.str2, i32 0, i32 0)) nounwind ; <%struct.FILE*> [#uses=1]
  store %struct.FILE* %4, %struct.FILE** @F, align 4
  %5 = load %struct.FILE** @F, align 4            ; <%struct.FILE*> [#uses=1]
  %6 = icmp eq %struct.FILE* %5, null             ; <i1> [#uses=1]
  br i1 %6, label %bb2, label %bb5

bb2:                                              ; preds = %bb1
  %7 = load i8** @input_file, align 4             ; <i8*> [#uses=1]
  %8 = icmp eq i8* %7, null                       ; <i1> [#uses=1]
  br i1 %8, label %bb3, label %bb4

bb3:                                              ; preds = %bb2
  store i8* getelementptr inbounds ([7 x i8]* @.str3, i32 0, i32 0), i8** @input_file, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2
  %9 = load i8** @input_file, align 4             ; <i8*> [#uses=1]
  %10 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([26 x i8]* @.str4, i32 0, i32 0), i8* %9) nounwind ; <i32> [#uses=0]
  call void @exit(i32 1) noreturn nounwind
  unreachable

bb5:                                              ; preds = %bb1
  br label %return

return:                                           ; preds = %bb5
   ret i32 1
}

declare i32 @puts(i8*)

declare void @exit(i32) noreturn nounwind

declare %struct.FILE* @fopen(i8*, i8*) nounwind

declare i32 @printf(i8*, ...) nounwind

define i1 @isSchedulable_send_data() nounwind {
	ret i1 1
}

define i32 @send_data() nounwind {
entry:
  %ptr = alloca i8*                               ; <i8**> [#uses=3]
  %i = alloca i32                                 ; <i32*> [#uses=3]
  %n = alloca i32                                 ; <i32*> [#uses=3]
  %"alloca point" = bitcast i32 0 to i32          ; <i32> [#uses=0]
  store i32 0, i32* %i, align 4
  %0 = load %struct.FILE** @F, align 4            ; <%struct.FILE*> [#uses=1]
  %1 = getelementptr inbounds %struct.FILE* %0, i32 0, i32 3 ; <i32*> [#uses=1]
  %2 = load i32* %1, align 4                      ; <i32> [#uses=1]
  %3 = and i32 %2, 16                             ; <i32> [#uses=1]
  %4 = icmp ne i32 %3, 0                          ; <i1> [#uses=1]
  br i1 %4, label %bb, label %bb1

bb:                                               ; preds = %entry
  %5 = load %struct.FILE** @F, align 4            ; <%struct.FILE*> [#uses=1]
  %6 = call i32 @fseek(%struct.FILE* %5, i32 0, i32 0) nounwind ; <i32> [#uses=0]
  br label %bb1

bb1:                                              ; preds = %bb, %entry
  br label %bb5

bb2:                                              ; preds = %bb5
  %7 = load %struct.fifo_i8_s** @O, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  %8 = call i8* @fifo_u8_write(%struct.fifo_i8_s* %7, i32 1) nounwind ; <i8*> [#uses=1]
  store i8* %8, i8** %ptr, align 4
  %9 = load %struct.FILE** @F, align 4            ; <%struct.FILE*> [#uses=1]
  %10 = load i8** %ptr, align 4                   ; <i8*> [#uses=1]
  %11 = call i32 @fread(i8* %10, i32 1, i32 1, %struct.FILE* %9) nounwind ; <i32> [#uses=1]
  store i32 %11, i32* %n, align 4
  %12 = load i32* %n, align 4                     ; <i32> [#uses=1]
  %13 = icmp sle i32 %12, 0                       ; <i1> [#uses=1]
  br i1 %13, label %bb3, label %bb4

bb3:                                              ; preds = %bb2
  %14 = load %struct.FILE** @F, align 4           ; <%struct.FILE*> [#uses=1]
  %15 = call i32 @fseek(%struct.FILE* %14, i32 0, i32 0) nounwind ; <i32> [#uses=0]
  store i32 0, i32* @cnt, align 4
  %16 = load %struct.FILE** @F, align 4           ; <%struct.FILE*> [#uses=1]
  %17 = load i8** %ptr, align 4                   ; <i8*> [#uses=1]
  %18 = call i32 @fread(i8* %17, i32 1, i32 1, %struct.FILE* %16) nounwind ; <i32> [#uses=1]
  store i32 %18, i32* %n, align 4
  br label %bb4

bb4:                                              ; preds = %bb3, %bb2
  %19 = load i32* %i, align 4                     ; <i32> [#uses=1]
  %20 = add nsw i32 %19, 1                        ; <i32> [#uses=1]
  store i32 %20, i32* %i, align 4
  %21 = load i32* @cnt, align 4                   ; <i32> [#uses=1]
  %22 = add nsw i32 %21, 1                        ; <i32> [#uses=1]
  store i32 %22, i32* @cnt, align 4
  %23 = load %struct.fifo_i8_s** @O, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  call void @fifo_u8_write_end(%struct.fifo_i8_s* %23, i32 1) nounwind
  br label %bb5

bb5:                                              ; preds = %bb4, %bb1
  %24 = load %struct.fifo_i8_s** @O, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  %25 = call i32 @fifo_u8_has_room(%struct.fifo_i8_s* %24, i32 1) nounwind ; <i32> [#uses=1]
  %26 = icmp ne i32 %25, 0                        ; <i1> [#uses=1]
  br i1 %26, label %bb2, label %bb6

bb6:                                              ; preds = %bb5
  br label %return

return:                                           ; preds = %bb6
  ret i32 1
}

!source = !{!0}
!name = !{!1}
!action_scheduler = !{!2}
!outputs = !{!3}
!state_variables = !{!5, !8, !11, !14, !17, !20, !23}
!initializes = !{!26}
!actions = !{!29}

!0 = metadata !{metadata !"System/Source"}
!1 = metadata !{metadata !"System.Source"}
!2 = metadata !{ metadata !33 ,  null }
!33 = metadata !{metadata !29}
!3 = metadata !{metadata !4, metadata !"O", %struct.fifo_i8_s** @O}
!4 = metadata  !{ i32 8 ,  null }
!5 = metadata !{metadata !6, metadata !7, i8** @input_file}
!6 = metadata !{metadata !"input_file", i1 0, i32 0,  i32 0}
!7 = metadata  !{ i32 8 ,  null }
!8 = metadata !{metadata !9, metadata !10, %struct.FILE** @F}
!9 = metadata !{metadata !"File", i1 0, i32 0,  i32 0}
!10 = metadata  !{ i32 8 ,  null }
!11 = metadata !{metadata !12, metadata !13, i32* @cnt}
!12 = metadata !{metadata !"cnt", i1 0, i32 0,  i32 0}
!13 = metadata  !{ i32 32 ,  null }

!14 = metadata !{metadata !15, metadata !16, [3 x i8]* @.str2}
!15 = metadata !{metadata !".str2", i1 0, i32 0,  i32 0}
!16 = metadata  !{ i32 8 ,  null }

!17 = metadata !{metadata !18, metadata !19, [26 x i8]* @.str4}
!18 = metadata !{metadata !".str4", i1 0, i32 0,  i32 0}
!19 = metadata  !{ i32 8 ,  null }

!20 = metadata !{metadata !21, metadata !22, [21 x i8]* @.str1}
!21 = metadata !{metadata !".str1", i1 0, i32 0,  i32 0}
!22 = metadata  !{ i32 8 ,  null }

!23 = metadata !{metadata !24, metadata !25, [7 x i8]* @.str3}
!24 = metadata !{metadata !".str3", i1 0, i32 0,  i32 0}
!25 = metadata  !{ i32 8 ,  null }
!26 = metadata !{ null , null , null, metadata !27, metadata !28}
!27 = metadata  !{metadata !"isSchedulable_initialize", i1 0, i1()* @isSchedulable_init}
!28 = metadata  !{metadata !"initialize", i1 0, i32()* @init}
!29 = metadata !{ null, null , metadata !30, metadata !31, metadata !32}
!30 = metadata !{metadata !3, i32 1}
!31 = metadata  !{metadata !"isSchedulable_send_data", i1 0, i1()* @isSchedulable_send_data}
!32 = metadata  !{metadata !"send_data", i1 0, i32()* @send_data}

declare i32 @fseek(%struct.FILE*, i32, i32) nounwind
declare i32 @fread(i8*, i32, i32, %struct.FILE*) nounwind

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
