; ModuleID = 'source.bc'

define i32 @main() noreturn nounwind {
  br label %.backedge

.backedge:                                        ; preds = %3, %.backedge, %0
  %1 = tail call i32 asm sideeffect "STREAM_OUT_STATUS_V1", "=r,ir"(i32 0) nounwind, !srcloc !0
  %2 = icmp slt i32 %1, 256
  br i1 %2, label %3, label %.backedge

; <label>:3                                       ; preds = %.backedge
  %4 = tail call i32 asm sideeffect "STREAM_IN", "=r,ir"(i32 0) nounwind, !srcloc !1
  tail call void asm sideeffect "STREAM_OUT_V1", "ir"(i32 %4) nounwind, !srcloc !2
  br label %.backedge
}

!0 = metadata !{i32 -2147416411}                  
!1 = metadata !{i32 -2147407946}                  
!2 = metadata !{i32 -2147416131}                  
