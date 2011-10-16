# Create Working library
vlib work

# Compile Other components
vcom -93 -quiet -work work counter.vhd
vcom -93 -quiet -work work segment_display_conv.vhd
vcom -93 -quiet -work work segment_display_sel.vhd
vcom -93 -quiet -work work fps_eval.vhd

vcom -93 -quiet -work work fps_eval_tb.vhd

# Simulate
vsim -novopt work.fps_eval_tb -t ps -do "view wave; add wave *;"
