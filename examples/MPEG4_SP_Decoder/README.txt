
The decoder.nl model contains an MPEG4 Simple Profile decoder. The decoder model
was created with the goal of generating synthesizable hardware descriptions, 
thus it is very explicit about data sizes and mostly uses only CAL constructs 
that can be compiled into hardware.

Note the rather large and complex ParseHeaders.cal actor, which illustrates a 
sophisticated use of action schedules and priorities.