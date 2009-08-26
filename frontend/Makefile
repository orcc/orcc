.PHONY = all clean debug default doc release

default:
	@echo Usage:
	@echo 'make all'
	@echo '    Builds debug bytecode and release versions of cal2ir'
	@echo 'make debug'
	@echo '    Builds a debug bytecode version of cal2ir'
	@echo 'make release'
	@echo '    Builds a release version of cal2ir'
	@echo 'make doc'
	@echo '    Builds documentation for cal2ir'

all:
	ocamlbuild src/cli/cal2ir.d.byte src/cli/cal2ir.native	

debug:
	ocamlbuild src/cli/cal2ir.d.byte

release:
	ocamlbuild src/cli/cal2ir.native

doc:
	rm -rf cal2ir.docdir
	ocamlbuild cal2ir.docdir/index.html

clean:
	ocamlbuild -clean
	rm -rf cal2ir.d.byte cal2ir.native cal2ir.docdir
