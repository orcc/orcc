onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -color White /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/clock
add wave -noupdate -color White /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/reset_n
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Rowsort
add wave -noupdate -divider <NULL>
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/row_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/row_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/row_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/y0_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/y0_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/y0_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/y1_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/y1_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/y1_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color Magenta -radix unsigned /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/actor_data
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/actor_send
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/actor_rdy
add wave -noupdate -color Magenta -radix unsigned /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/network_data
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/network_send
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/network_ack
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/management
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/rowsort/comarbiter_y0/data_in_reg
add wave -noupdate -divider <NULL>
add wave -noupdate -divider farimerge
add wave -noupdate -divider <NULL>
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/r0_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/r0_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/r0_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/r1_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/r1_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/r1_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/c0_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/c0_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/c0_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/c1_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/c1_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/c1_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y0_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y0_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y0_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y1_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y1_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y1_ack
add wave -noupdate -color {Orange Red} -format Literal /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/rowout_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/rowout_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/rowout_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color Gold /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/fsm
add wave -noupdate -divider <NULL>
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y0_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/y1_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/fairmerge/rowout_rdy
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Broadcast
add wave -noupdate -divider <NULL>
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/input_data
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/input_send
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/input_ack
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_data
add wave -noupdate -color {Orange Red} -expand -subitemconfig {/tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_send(1) {-color #ffff45450000 -height 16} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_send(0) {-color #ffff45450000 -height 16}} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_send
add wave -noupdate -color {Orange Red} -expand -subitemconfig {/tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_ack(1) {-color #ffff45450000 -height 16} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_ack(0) {-color #ffff45450000 -height 16}} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/output_ack
add wave -noupdate -color magenta -expand -subitemconfig {/tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/internalack(1) {-color #ffff0000ffff -height 16} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/internalack(0) {-color #ffff0000ffff -height 16}} /tb_xilinx_idct2d/xilinx_idct2d_orcc/broadcast_fairmerge_rowout/internalack
add wave -noupdate -divider <NULL>
add wave -noupdate -divider idct1D
add wave -noupdate -divider <NULL>
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/x0_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/x0_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/x0_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/x1_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/x1_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/x1_ack
add wave -noupdate -color Cyan -format Literal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/row_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/row_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/row_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -group idct1d -group Scale -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/scale/x0_data
add wave -noupdate -group idct1d -group Scale -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/scale/x1_data
add wave -noupdate -group idct1d -group Scale -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/scale/y0_data
add wave -noupdate -group idct1d -group Scale -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/scale/y1_data
add wave -noupdate -group idct1d -group Scale -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/scale/y2_data
add wave -noupdate -group idct1d -group Scale -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/scale/y3_data
add wave -noupdate -group idct1d -divider <NULL>
add wave -noupdate -group idct1d -group Combine -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/x0_data
add wave -noupdate -group idct1d -group Combine -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/x1_data
add wave -noupdate -group idct1d -group Combine -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/x2_data
add wave -noupdate -group idct1d -group Combine -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/x3_data
add wave -noupdate -group idct1d -group Combine -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/row_data
add wave -noupdate -group idct1d -group Combine -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/y0_data
add wave -noupdate -group idct1d -group Combine -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/combine/y1_data
add wave -noupdate -group idct1d -divider <NULL>
add wave -noupdate -group idct1d -group ShuffleFly -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shufflefly/x0_data
add wave -noupdate -group idct1d -group ShuffleFly -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shufflefly/x1_data
add wave -noupdate -group idct1d -group ShuffleFly -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shufflefly/y0_data
add wave -noupdate -group idct1d -group ShuffleFly -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shufflefly/y1_data
add wave -noupdate -group idct1d -group ShuffleFly -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shufflefly/y2_data
add wave -noupdate -group idct1d -group ShuffleFly -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shufflefly/y3_data
add wave -noupdate -group idct1d -divider <NULL>
add wave -noupdate -group idct1d -group Shuffle -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/x0_data
add wave -noupdate -group idct1d -group Shuffle -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/x1_data
add wave -noupdate -group idct1d -group Shuffle -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/x2_data
add wave -noupdate -group idct1d -group Shuffle -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/x3_data
add wave -noupdate -group idct1d -group Shuffle -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/y0_data
add wave -noupdate -group idct1d -group Shuffle -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/y1_data
add wave -noupdate -group idct1d -group Shuffle -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/y2_data
add wave -noupdate -group idct1d -group Shuffle -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/shuffle/y3_data
add wave -noupdate -group idct1d -divider <NULL>
add wave -noupdate -group idct1d -group Final -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/x0_data
add wave -noupdate -group idct1d -group Final -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/x1_data
add wave -noupdate -group idct1d -group Final -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/x2_data
add wave -noupdate -group idct1d -group Final -color cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/x3_data
add wave -noupdate -group idct1d -group Final -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/y0_data
add wave -noupdate -group idct1d -group Final -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/y1_data
add wave -noupdate -group idct1d -group Final -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/y2_data
add wave -noupdate -group idct1d -group Final -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/final/y3_data
add wave -noupdate -group idct1d -group Final -divider <NULL>
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y1_data
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y0_send
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y0_ack
add wave -noupdate -color {Orange red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y0_data
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y1_send
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y1_ack
add wave -noupdate -color {Orange red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y2_data
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y2_send
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y2_ack
add wave -noupdate -color {Orange red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y3_data
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y3_send
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/idct1d/y3_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -divider downsample
add wave -noupdate -divider <NULL>
add wave -noupdate -color cyan -format Literal /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r_data
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r_send
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange Red} -format Literal /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r2_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r2_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r2_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color Magenta -format Literal -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r2_idata
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r2_isend
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/downsample/r2_rdy
add wave -noupdate -divider <NULL>
add wave -noupdate -divider sep
add wave -noupdate -divider <NULL>
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x0_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x0_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x0_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x1_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x1_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x1_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x2_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x2_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x2_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x3_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x3_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/x3_ack
add wave -noupdate -color Cyan -format Literal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/row_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/row_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/row_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r0_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r0_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r0_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r1_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r1_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r1_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r2_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r2_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r2_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r3_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r3_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r3_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c0_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c0_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c0_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c1_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c1_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c1_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c2_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c2_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c2_ack
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c3_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c3_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c3_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color Gold /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/untagged_0_go
add wave -noupdate -color Gold /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/untagged_1_go
add wave -noupdate -divider <NULL>
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r0_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r1_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r2_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/r3_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c0_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c1_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c2_rdy
add wave -noupdate -color Magenta /tb_xilinx_idct2d/xilinx_idct2d_orcc/sep/c3_rdy
add wave -noupdate -divider <NULL>
add wave -noupdate -divider trans
add wave -noupdate -divider <NULL>
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x0_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x0_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x0_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x1_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x1_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x1_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x2_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x2_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x2_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x3_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x3_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/x3_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/y0_data
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/y0_send
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/y0_ack
add wave -noupdate -color {Orange red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/y1_data
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/y1_send
add wave -noupdate -color {Orange red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/trans/y1_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -divider retrans
add wave -noupdate -divider <NULL>
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x0_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x0_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x0_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x1_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x1_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x1_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x2_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x2_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x2_ack
add wave -noupdate -color Cyan -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x3_data
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x3_send
add wave -noupdate -color Cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/x3_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange Red} -radix decimal /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/y_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/y_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/retrans/y_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -divider Clip
add wave -noupdate -divider <NULL>
add wave -noupdate -color cyan -radix unsigned /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/i_data
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/i_send
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/i_ack
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/signed_data
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/signed_send
add wave -noupdate -color cyan /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/signed_ack
add wave -noupdate -divider <NULL>
add wave -noupdate -color {Orange Red} -radix unsigned /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/o_data
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/o_send
add wave -noupdate -color {Orange Red} /tb_xilinx_idct2d/xilinx_idct2d_orcc/clip/o_ack
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {3535 ns} 0}
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
configure wave -timelineunits ns
update
WaveRestoreZoom {3414 ns} {4349 ns}
