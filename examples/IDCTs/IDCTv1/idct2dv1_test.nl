network idct2dv1_test () ==> :

entities
	data_gen = idct2d_data_generate();
    idct2d = idct2d();
	print = idct2d_print();

structure
	data_gen.S --> idct2d.signed;
	data_gen.DATA --> idct2d.\in\;
    idct2d.out --> print.input;
end
