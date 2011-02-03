@O = global %struct.fifo_i8_s* null ; <%struct.fifo_u8_s**> [#uses=3]
@source = global i8* null

define i1 @isSchedulable_send_data() nounwind {
	ret i1 1
}

define i32 @send_data() nounwind {
entry:
  %0 = load i8** @source
  %1 = load %struct.fifo_i8_s** @O, align 4 ; <%struct.fifo_u8_s*> [#uses=1]
  %2 = call i32 @fifo_u8_get_room(%struct.fifo_i8_s* %1) nounwind ; <i8*>
  %3 = call i8* @fifo_u8_write(%struct.fifo_i8_s* %1, i32 %2) nounwind ; <i8*> [#uses=1]
  call void @get_src(i8* %0, i8* %3, i32 %2) nounwind ;
  call void @fifo_u8_write_end(%struct.fifo_i8_s* %1, i32 %2) nounwind
  br label %return

return:                                           ; preds = %bb6
  ret i32 1
}

declare void @get_src(i8*, i8*, i32)

!source = !{!0}
!name = !{!1}
!action_scheduler = !{!2}
!outputs = !{!3}
!actions = !{!9}
!procedures = !{!5}
!state_variables = !{!6}

!0 = metadata !{metadata !"System/Source"}
!1 = metadata !{metadata !"System.Source"}
!2 = metadata !{ metadata !13 ,  null }
!3 = metadata !{metadata !4, metadata !"O", %struct.fifo_i8_s** @O}
!4 = metadata  !{ i32 8 ,  null }
!5 = metadata !{metadata !"get_src", i1 1 , void(i8*, i8*, i32)* @get_src}
!6 = metadata !{metadata !7, metadata !8, null, i8** @source}
!7 = metadata !{metadata !"source", i1 0, i32 0,  i32 0}
!8 = metadata  !{ i32 8 ,  null }

!9 = metadata !{ null, null , metadata !10, metadata !11, metadata !12}
!10 = metadata !{metadata !3, i32 1}
!11 = metadata  !{metadata !"isSchedulable_send_data", i1 0, i1()* @isSchedulable_send_data}
!12 = metadata  !{metadata !"send_data", i1 0, i32()* @send_data}
!13 = metadata !{metadata !9}

%struct.fifo_i8_s = type opaque
%struct.fifo_i16_s = type opaque
%struct.fifo_i32_s = type opaque
%struct.fifo_i64_s = type opaque

declare i32 @fifo_u8_get_room(%struct.fifo_i8_s* %fifo)
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
