; ModuleID = 'display.bc'

@frame_sum = internal unnamed_addr global i32 0, align 4
@comp = internal unnamed_addr global i32 0, align 4
@frame_count = internal unnamed_addr global i32 0, align 4
@m_x = internal unnamed_addr global i32 0, align 4
@m_width = internal unnamed_addr global i32 0, align 4
@m_y = internal unnamed_addr global i32 0, align 4
@m_height = internal unnamed_addr global i32 0, align 4



define i32 @main() noreturn nounwind {
  %B_buf = alloca [64 x i32], align 4
  %1 = load i32* @frame_count, align 4, !tbaa !0
  call void asm sideeffect "STREAM_OUT_V1", "ir"(i32 %1) nounwind, !srcloc !3
  br label %display_write_mb.exit

display_write_mb.exit:                            ; preds = %105, %81, %12, %0
  %2 = call i32 asm sideeffect "STREAM_IN_STATUS_V2", "=r,ir"(i32 0) nounwind, !srcloc !4
  %3 = icmp sgt i32 %2, 0
  br i1 %3, label %4, label %12

; <label>:4                                       ; preds = %display_write_mb.exit
  %5 = call i32 asm sideeffect "STREAM_IN_STATUS_V3", "=r,ir"(i32 0) nounwind, !srcloc !5
  %6 = icmp sgt i32 %5, 0
  br i1 %6, label %7, label %12

; <label>:7                                       ; preds = %4
  %8 = call i32 asm sideeffect "STREAM_IN_V2", "=r,ir"(i32 0) nounwind, !srcloc !6
  %9 = shl nsw i32 %8, 4
  %10 = call i32 asm sideeffect "STREAM_IN_V3", "=r,ir"(i32 0) nounwind, !srcloc !7
  %11 = shl nsw i32 %10, 4
  store i32 %9, i32* @m_width, align 4, !tbaa !0
  store i32 %11, i32* @m_height, align 4, !tbaa !0
  br label %12

; <label>:12                                      ; preds = %7, %4, %display_write_mb.exit
  %13 = call i32 asm sideeffect "STREAM_IN_STATUS_V1", "=r,ir"(i32 0) nounwind, !srcloc !8
  %14 = icmp sgt i32 %13, 63
  br i1 %14, label %.preheader, label %display_write_mb.exit

.preheader:                                       ; preds = %.preheader, %12
  %i.01.i1 = phi i32 [ %30, %.preheader ], [ 0, %12 ]
  %scevgep.i2 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %i.01.i1
  %15 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %15, i32* %scevgep.i2, align 4, !tbaa !0
  %16 = or i32 %i.01.i1, 1
  %scevgep.i2.1 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %16
  %17 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %17, i32* %scevgep.i2.1, align 4, !tbaa !0
  %18 = or i32 %i.01.i1, 2
  %scevgep.i2.2 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %18
  %19 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %19, i32* %scevgep.i2.2, align 4, !tbaa !0
  %20 = or i32 %i.01.i1, 3
  %scevgep.i2.3 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %20
  %21 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %21, i32* %scevgep.i2.3, align 4, !tbaa !0
  %22 = or i32 %i.01.i1, 4
  %scevgep.i2.4 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %22
  %23 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %23, i32* %scevgep.i2.4, align 4, !tbaa !0
  %24 = or i32 %i.01.i1, 5
  %scevgep.i2.5 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %24
  %25 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %25, i32* %scevgep.i2.5, align 4, !tbaa !0
  %26 = or i32 %i.01.i1, 6
  %scevgep.i2.6 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %26
  %27 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %27, i32* %scevgep.i2.6, align 4, !tbaa !0
  %28 = or i32 %i.01.i1, 7
  %scevgep.i2.7 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %28
  %29 = call i32 asm sideeffect "STREAM_IN_V1", "=r,ir"(i32 0) nounwind, !srcloc !9
  store i32 %29, i32* %scevgep.i2.7, align 4, !tbaa !0
  %30 = add i32 %i.01.i1, 8
  %exitcond1.7 = icmp eq i32 %30, 64
  br i1 %exitcond1.7, label %fifo_1_read.exit, label %.preheader

fifo_1_read.exit:                                 ; preds = %.preheader
  %.pre.i = load i32* @frame_sum, align 4, !tbaa !0
  br label %31

; <label>:31                                      ; preds = %31, %fifo_1_read.exit
  %32 = phi i32 [ %.pre.i, %fifo_1_read.exit ], [ %79, %31 ]
  %i.01.i = phi i32 [ 0, %fifo_1_read.exit ], [ %80, %31 ]
  %scevgep.i = getelementptr [64 x i32]* %B_buf, i32 0, i32 %i.01.i
  %33 = load i32* %scevgep.i, align 4, !tbaa !0
  %34 = add nsw i32 %33, %32
  %35 = or i32 %i.01.i, 1
  %scevgep.i.1 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %35
  %36 = load i32* %scevgep.i.1, align 4, !tbaa !0
  %37 = add nsw i32 %36, %34
  %38 = or i32 %i.01.i, 2
  %scevgep.i.2 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %38
  %39 = load i32* %scevgep.i.2, align 4, !tbaa !0
  %40 = add nsw i32 %39, %37
  %41 = or i32 %i.01.i, 3
  %scevgep.i.3 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %41
  %42 = load i32* %scevgep.i.3, align 4, !tbaa !0
  %43 = add nsw i32 %42, %40
  %44 = or i32 %i.01.i, 4
  %scevgep.i.4 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %44
  %45 = load i32* %scevgep.i.4, align 4, !tbaa !0
  %46 = add nsw i32 %45, %43
  %47 = or i32 %i.01.i, 5
  %scevgep.i.5 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %47
  %48 = load i32* %scevgep.i.5, align 4, !tbaa !0
  %49 = add nsw i32 %48, %46
  %50 = or i32 %i.01.i, 6
  %scevgep.i.6 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %50
  %51 = load i32* %scevgep.i.6, align 4, !tbaa !0
  %52 = add nsw i32 %51, %49
  %53 = or i32 %i.01.i, 7
  %scevgep.i.7 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %53
  %54 = load i32* %scevgep.i.7, align 4, !tbaa !0
  %55 = add nsw i32 %54, %52
  %56 = or i32 %i.01.i, 8
  %scevgep.i.8 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %56
  %57 = load i32* %scevgep.i.8, align 4, !tbaa !0
  %58 = add nsw i32 %57, %55
  %59 = or i32 %i.01.i, 9
  %scevgep.i.9 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %59
  %60 = load i32* %scevgep.i.9, align 4, !tbaa !0
  %61 = add nsw i32 %60, %58
  %62 = or i32 %i.01.i, 10
  %scevgep.i.10 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %62
  %63 = load i32* %scevgep.i.10, align 4, !tbaa !0
  %64 = add nsw i32 %63, %61
  %65 = or i32 %i.01.i, 11
  %scevgep.i.11 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %65
  %66 = load i32* %scevgep.i.11, align 4, !tbaa !0
  %67 = add nsw i32 %66, %64
  %68 = or i32 %i.01.i, 12
  %scevgep.i.12 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %68
  %69 = load i32* %scevgep.i.12, align 4, !tbaa !0
  %70 = add nsw i32 %69, %67
  %71 = or i32 %i.01.i, 13
  %scevgep.i.13 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %71
  %72 = load i32* %scevgep.i.13, align 4, !tbaa !0
  %73 = add nsw i32 %72, %70
  %74 = or i32 %i.01.i, 14
  %scevgep.i.14 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %74
  %75 = load i32* %scevgep.i.14, align 4, !tbaa !0
  %76 = add nsw i32 %75, %73
  %77 = or i32 %i.01.i, 15
  %scevgep.i.15 = getelementptr [64 x i32]* %B_buf, i32 0, i32 %77
  %78 = load i32* %scevgep.i.15, align 4, !tbaa !0
  %79 = add nsw i32 %78, %76
  %80 = add i32 %i.01.i, 16
  %exitcond.15 = icmp eq i32 %80, 64
  br i1 %exitcond.15, label %81, label %31

; <label>:81                                      ; preds = %31
  store i32 %79, i32* @frame_sum, align 4
  %82 = load i32* @comp, align 4, !tbaa !0
  %83 = add nsw i32 %82, 1
  store i32 %83, i32* @comp, align 4, !tbaa !0
  %84 = icmp eq i32 %83, 6
  br i1 %84, label %85, label %display_write_mb.exit

; <label>:85                                      ; preds = %81
  %86 = load i32* @m_x, align 4, !tbaa !0
  %87 = add nsw i32 %86, 16
  store i32 %87, i32* @m_x, align 4, !tbaa !0
  %88 = load i32* @m_width, align 4, !tbaa !0
  %89 = icmp eq i32 %87, %88
  br i1 %89, label %90, label %._crit_edge.i

; <label>:90                                      ; preds = %85
  store i32 0, i32* @m_x, align 4, !tbaa !0
  %91 = load i32* @m_y, align 4, !tbaa !0
  %92 = add nsw i32 %91, 16
  store i32 %92, i32* @m_y, align 4, !tbaa !0
  br label %._crit_edge.i

._crit_edge.i:                                    ; preds = %90, %85
  %93 = load i32* @m_y, align 4, !tbaa !0
  %94 = load i32* @m_height, align 4, !tbaa !0
  %95 = icmp eq i32 %93, %94
  br i1 %95, label %96, label %105

; <label>:96                                      ; preds = %._crit_edge.i
  store i32 0, i32* @m_x, align 4, !tbaa !0
  store i32 0, i32* @m_y, align 4, !tbaa !0
  %97 = call i32 asm sideeffect "STREAM_IN", "=r,ir"(i32 0) nounwind, !srcloc !10
  %98 = load i32* @frame_sum, align 4, !tbaa !0
  %99 = load i32* @frame_count, align 4, !tbaa !0
  %100 = add nsw i32 %99, 1
  store i32 %100, i32* @frame_count, align 4, !tbaa !0
  %tmp.i.i = xor i32 %98, %97
  %tmp1.i.i = and i32 %tmp.i.i, 255
  %101 = icmp eq i32 %tmp1.i.i, 0
  br i1 %101, label %104, label %102

; <label>:102                                     ; preds = %96
  %103 = add i32 %99, 129
  call void asm sideeffect "STREAM_OUT_V1", "ir"(i32 %103) nounwind, !srcloc !11
  br label %display_show_image.exit.i

; <label>:104                                     ; preds = %96
  call void asm sideeffect "STREAM_OUT_V1", "ir"(i32 %100) nounwind, !srcloc !12
  br label %display_show_image.exit.i

display_show_image.exit.i:                        ; preds = %104, %102
  store i32 0, i32* @frame_sum, align 4, !tbaa !0
  br label %105

; <label>:105                                     ; preds = %display_show_image.exit.i, %._crit_edge.i
  store i32 0, i32* @comp, align 4, !tbaa !0
  br label %display_write_mb.exit
}

!0 = metadata !{metadata !"int", metadata !1}
!1 = metadata !{metadata !"omnipotent char", metadata !2}
!2 = metadata !{metadata !"Simple C/C++ TBAA", null}
!3 = metadata !{i32 -2147405766}                  
!4 = metadata !{i32 -2147423824}                  
!5 = metadata !{i32 -2147421974}                  
!6 = metadata !{i32 -2147423016}                  
!7 = metadata !{i32 -2147421166}                  
!8 = metadata !{i32 -2147425674}                  
!9 = metadata !{i32 -2147424866}                  
!10 = metadata !{i32 -2147406080}                 
!11 = metadata !{i32 -2147405940}                 
!12 = metadata !{i32 -2147405857}                 
