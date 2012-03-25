# Create Working library
vdel -all -lib work
vlib work
vmap work work

vcom -93 -quiet -work work broadcast.vhd
vcom -93 -quiet -work work broadcast_tb.vhd

# Simulate
vsim -novopt work.broadcast_tb 

