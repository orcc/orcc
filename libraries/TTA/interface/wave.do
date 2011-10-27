onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/rst_n
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/clk
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/top_frame
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/segment7
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/segment7_sel
add wave -noupdate -divider <NULL>
add wave -noupdate -divider fps_eval
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/rst_n
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/clk
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/top_frame
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment7
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment7_sel
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/top_frame2
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/top_ms
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/top_s
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/count_u
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/top_u
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/count_d
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/top_d
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/count_h
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/top_h
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/count_t
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment7_u
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment7_d
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment7_h
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment7_t
add wave -noupdate -divider <NULL>
add wave -noupdate -divider cpt_ms
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_ms/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_ms/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_ms/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_ms/count
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_ms/top
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_ms/s_count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider cpt_s
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_s/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_s/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_s/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_s/count
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_s/top
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_s/s_count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider cpt_u
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_u/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_u/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_u/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_u/count
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_u/top
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_u/s_count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider cpt_d
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_d/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_d/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_d/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_d/count
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_d/top
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_d/s_count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider cpt_h
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_h/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_h/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_h/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_h/count
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_h/top
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_h/s_count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider cpt_t
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_t/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_t/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_t/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_t/count
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_t/top
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/counter_t/s_count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider segment_display_conv
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_u/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_u/rst
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_u/bcd
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_u/segment7
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_d/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_d/rst
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_d/bcd
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_d/segment7
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_h/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_h/rst
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_h/bcd
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_h/segment7
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_t/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_t/rst
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_t/bcd
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_conv_t/segment7
add wave -noupdate -divider <NULL>
add wave -noupdate -divider segment_display_sel
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/clk
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/rst
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/refresh
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/segment7_u
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/segment7_d
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/segment7_h
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/segment7_t
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/valid
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/segment7
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/segment7_sel
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/current_level
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/current_segment7_u
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/current_segment7_d
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/current_segment7_h
add wave -noupdate -format Literal -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/current_segment7_t
add wave -noupdate -format Logic -radix hexadecimal /fps_eval_tb/fps_eval_1/segment_display_sel_component/refreshing
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {19854982678 ps} 0}
configure wave -namecolwidth 185
configure wave -valuecolwidth 100
configure wave -justifyvalue left
configure wave -signalnamewidth 1
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ns
update
WaveRestoreZoom {19854982678 ps} {19968093167 ps}
