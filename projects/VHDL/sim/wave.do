onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Rowsort
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/reset_n
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/row_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/row_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/row_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/y0_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/y0_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/y0_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/y1_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/y1_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/y1_write
add wave -noupdate -divider <NULL>
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a0_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a1_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a2_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a3_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a4_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a5_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a6_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a7_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a8_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/a9_go
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/x0
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/x1
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/x2
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/x3
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/x5
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/rowsort/fsm
add wave -noupdate -divider <NULL>
add wave -noupdate -divider farimerge
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/reset_n
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/r0_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/r0_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/r0_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/r1_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/r1_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/r1_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/c0_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/c0_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/c0_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/c1_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/c1_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/c1_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/y0_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/y0_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/y0_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/y1_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/y1_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/y1_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/rowout_full
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/rowout_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/rowout_write
add wave -noupdate -divider <NULL>
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/row_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/col_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/col_low_go
add wave -noupdate -color green -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/row_low_go
add wave -noupdate -color green -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/fairmerge/fsm
add wave -noupdate -divider <NULL>
add wave -noupdate -divider idct1D
add wave -noupdate -divider <NULL>
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/x0_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/x0_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/x0_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/x1_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/x1_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/x1_ack
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/row_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/row_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/row_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Scale
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/reset_n
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/x0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/x0_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/x0_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/x1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/x1_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/x1_ack
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y0_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y0_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y1_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y1_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y2_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y2_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y3_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/y3_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/untagged_0_go
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/ww0
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/ww1
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/scale/index
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Combine
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/reset_n
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x0_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x0_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x1_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x1_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x2_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x2_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x3_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/x3_ack
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/row_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/row_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/row_ack
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/y0_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/y0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/y0_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/y1_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/y1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/y1_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/row_go
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/col_go
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/combine/count
add wave -noupdate -divider <NULL>
add wave -noupdate -divider ShuflleFly
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/reset_n
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/x0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/x0_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/x0_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/x1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/x1_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/x1_ack
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y0_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y0_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y1_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y1_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y2_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y2_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y3_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/y3_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/a0_go
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/a1_go
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/d0
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/d1
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shufflefly/fsm
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Shuflle
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/reset_n
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x0_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x0_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x1_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x1_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x2_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x2_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x3_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x3_ack
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y0_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y0_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y1_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y1_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y2_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y2_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y3_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/y3_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/a0_go
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/a1_go
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/a2_go
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x4
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x5
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x6h
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x7h
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x6l
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/x7l
add wave -noupdate -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/shuffle/fsm
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Final
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/reset_n
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x0_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x0_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x1_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x1_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x2_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x2_ack
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x3_send
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/x3_ack
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y0_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y0_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y0_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y1_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y1_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y1_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y2_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y2_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y2_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y3_full
add wave -noupdate -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y3_data
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/y3_write
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/final/untagged_0_go
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y0_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y0_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y0_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y1_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y1_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y1_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y2_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y2_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y2_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y3_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y3_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/idct1d/y3_full
add wave -noupdate -divider <NULL>
add wave -noupdate -divider dawnsample
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/reset_n
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/r_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/r_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/r_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/r2_full
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/r2_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/r2_write
add wave -noupdate -divider <NULL>
add wave -noupdate -color green -format Logic -itemcolor white /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/a0_go
add wave -noupdate -color green -format Logic -itemcolor white /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/a1_go
add wave -noupdate -color green -format Literal -itemcolor white /xilinx_idct2d_testbench/xilinx_idct2d_orcc/downsample/fsm
add wave -noupdate -divider <NULL>
add wave -noupdate -divider trans
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/reset_n
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x0_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x0_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x0_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x1_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x1_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x1_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x2_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x2_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x2_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x3_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x3_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/x3_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/y0_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/y0_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/y0_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/y1_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/y1_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/trans/y1_write
add wave -noupdate -divider <NULL>
add wave -noupdate -divider sep
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/reset_n
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x0_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x0_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x0_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x1_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x1_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x1_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x2_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x2_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x2_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x3_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x3_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/x3_ack
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/row_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/row_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/row_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r0_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r0_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r0_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r1_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r1_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r1_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r2_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r2_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r2_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r3_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r3_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/r3_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c0_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c0_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c0_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c1_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c1_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c1_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c2_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c2_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c2_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c3_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c3_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/sep/c3_write
add wave -noupdate -divider <NULL>
add wave -noupdate -divider retrans
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/clock
add wave -noupdate -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/reset_n
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x0_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x0_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x0_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x1_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x1_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x1_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x2_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x2_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x2_ack
add wave -noupdate -color orange -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x3_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x3_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/x3_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/y_full
add wave -noupdate -color magenta -format Literal -radix decimal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/y_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/retrans/y_write
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Clip
add wave -noupdate -divider <NULL>
add wave -noupdate -color orange -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/i_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/i_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/i_ack
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/signed_data
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/signed_send
add wave -noupdate -color orange -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/signed_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/o_full
add wave -noupdate -color magenta -format Literal /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/o_data
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/o_write
add wave -noupdate -color magenta -format Logic /xilinx_idct2d_testbench/xilinx_idct2d_orcc/clip/read_signed_go
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {1531 ns} 0}
configure wave -namecolwidth 222
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
update
WaveRestoreZoom {0 ns} {3150 ns}
