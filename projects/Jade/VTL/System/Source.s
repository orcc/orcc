@O = global i8* null 
@O_ptr = global i8* null
@source = global i8* null

define i1 @isSchedulable_send_data() nounwind {
	ret i1 1
}

define i32 @send_data() nounwind {
entry:
  %source = load i8** @source
  %O = bitcast i8** @O_ptr to [1 x i8]*
  %O_elt = getelementptr [1 x i8]* %O, i32 0, i32 0
  call void @get_src(i8* %source, i8* %O_elt) nounwind ;
  br label %return

return:                                           ; preds = %bb6
  ret i32 1
}

declare void @get_src(i8*, i8*)

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
!3 = metadata !{metadata !4, metadata !"O", i8** @O}
!4 = metadata  !{ i32 8 ,  null }
!5 = metadata !{metadata !"get_src", i1 1 , void(i8*, i8*)* @get_src}
!6 = metadata !{metadata !7, metadata !8, null, i8** @source}
!7 = metadata !{metadata !"source", i1 0, i32 0,  i32 0}
!8 = metadata  !{ i32 8 ,  null }

!9 = metadata !{ null, null , metadata !10, metadata !11, metadata !12}
!10 = metadata !{metadata !14, metadata !15, null}
!11 = metadata  !{metadata !"isSchedulable_send_data", i1 0, i1()* @isSchedulable_send_data}
!12 = metadata  !{metadata !"send_data", i1 0, i32()* @send_data}
!13 = metadata !{metadata !9}
!14 = metadata !{metadata !3, i32 1}
!15 = metadata !{metadata !3, i8** @O_ptr}

