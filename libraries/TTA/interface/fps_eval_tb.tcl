# Create Working library
vdel -all -lib work
vlib work
vmap work work

# Compile Other components
vcom -93 -quiet -work work counter.vhd
vcom -93 -quiet -work work segment_display_conv.vhd
vcom -93 -quiet -work work segment_display_sel.vhd
vcom -93 -quiet -work work fps_eval.vhd

vcom -93 -quiet -work work fps_eval_tb.vhd

# Simulate
vsim -novopt work.fps_eval_tb -t ps -do "do wave.do; run 20ms"

