@display = global i8* null
@WIDTH_ptr = global i16* null
@HEIGHT_ptr =global i16* null
@B_ptr = global i8* null

define i1 @isSchedulable_get_size() nounwind {
entry:
	ret i1 1
}

define void @get_size() nounwind {
entry:
	%WIDTH = bitcast i16** @WIDTH_ptr to [1 x i16]*
	%HEIGHT = bitcast i16** @HEIGHT_ptr to [1 x i16]*
	%display = load i8** @display
	%WIDTH_elt = getelementptr [1 x i16]* %WIDTH, i32 0, i32 0
	%HEIGHT_elt = getelementptr [1 x i16]* %HEIGHT, i32 0, i32 0
	%0 = load i16* %WIDTH_elt
	%1 = load i16* %HEIGHT_elt
	%2 = sext i16 %0 to i32
	%3 = sext i16 %1 to i32
	call void @set_video(i8* %display, i32 %2, i32 %3)
	ret void
}

define i1 @isSchedulable_get_data() nounwind {
entry:
	ret i1 1
}

define void @get_data() nounwind {
entry:
	%display = load i8** @display
	%B = load i8** @B_ptr
	call void @write_mb(i8* %display, i8* %B) nounwind
	ret void
}

declare void @set_video(i8*, i32, i32)

declare void @write_mb(i8*, i8*)

!source = !{!0}
!name = !{!1}
!action_scheduler = !{!2}
!inputs = !{!3, !5, !7}
!state_variables = !{!9}
!procedures = !{!12, !14}
!actions = !{!15, !21}

!0 = metadata !{metadata !"System/Display.bc"}
!1 = metadata !{metadata !"System.Display"}
!2 = metadata !{metadata !27, null}

!3 = metadata !{metadata !4, metadata !"Byte", i8** @B_ptr}
!4 = metadata  !{ i32 8 ,  null }
!5 = metadata !{metadata !6, metadata !"Width", i16** @WIDTH_ptr}
!6 = metadata  !{ i32 16 ,  null }
!7 = metadata !{metadata !8, metadata !"Height", i16** @HEIGHT_ptr}
!8 = metadata  !{ i32 16 ,  null }
!9 = metadata !{metadata !10, metadata !11, null, i8** @display}
!10 = metadata !{metadata !"display", i1 0, i32 0,  i32 0}
!11 = metadata  !{ i32 8 ,  null }
!12 = metadata !{metadata !"set_video", i1 1 , void(i8*, i32, i32)* @set_video}
!14 = metadata !{metadata !"write_mb", i1 1 , void(i8*, i8*)* @write_mb}
!15 = metadata !{ null, metadata !16, null, metadata !17, metadata !18}
!16 = metadata !{metadata !19, metadata !20, null}
!17 = metadata  !{metadata !"isSchedulable_get_data", i1 0, i1()* @isSchedulable_get_data}
!18 = metadata  !{metadata !"get_data", i1 0, void()* @get_data}
!19 = metadata !{metadata !3, i32 384}
!20 = metadata !{metadata !3}

!21 = metadata !{ null, metadata !22, null, metadata !23, metadata !24}
!22 = metadata !{metadata !25, metadata !26, null}
!23 = metadata  !{metadata !"isSchedulable_get_size", i1 0, i1()* @isSchedulable_get_size}
!24 = metadata  !{metadata !"get_size", i1 0, void()* @get_size}
!25 = metadata !{metadata !5, i32 1, metadata !7, i32 1}
!26 = metadata !{metadata !5, metadata !7}
!27 = metadata !{metadata !21, metadata !15}
