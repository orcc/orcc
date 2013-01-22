# 1.2.2 2013

### Notable changes and features

- Add a new experimental backend called 'High Level Synthesis' (or HLS) which use Vivado
toolset to generate HDL designs.
- Add a new experimental backend called 'COMPA'. It generate C source code, without any dependency
to external libraries (SDL, pthreads, etc.). This backend can't compile classical applications,
because they use Display.cal and needs SDL to work.
- LLVM/TTA backends do not use broadcast actors anymore. The broadcasting is directly made
by the source actor.
- TTA backend:
	* Support 'printf' and 'native' functions.
	* Global performance improvement.

### Bugfixes

- Caching function: Only files different from existing ones are printed on disk.
- UI : when creating an instance in a graph, existing names are checked in case insensitive
mode to fix Microsoft Windows bug when printing source files.
- Disable threaded generation to fix random bugs in generated files (without performance lost).

### Misc

- Move Graphiti-editor plugins into Orcc repository. Only one eclipse feature has to be
installed to use Orcc. Separate Graphiti-editor plugin is not needed anymore.
- All backends (but XLIM) use now Xtend instead of Stringtemplate to print code.
- Global improvement of the information displayed by the compiler. The following levels of 
messages are possible : LOG, DEBUG, NOTICE, WARNING, DEFECT.

# 1.2.1 Oct 2012

- Fix library extraction in C backend

# 1.2.0 Sept 2012

### Notable changes and features

- Front-end: experimental support of new floating point types (half and double).
- New LLVM backend with static compilation (the old version is renamed as Jade 
backend).
- The C++ backend is back with an included runtime.
- For backends which export a runtime library (C, TTA, Java, LLVM), it is now
possible to deactivate this export, to not overwrite user-modified library.
- LLVM/Jade backends: experimental support of floating point type.
- TTA backend: 
    * new interconnection mechanism between the processors.
    * support simulation and profiling using ttanetsim (experimental branch of TCE).
    * add option to choose the configuration of the processors between several predefined profiles.
    * support the mapping of several actors on the same processor.
- C/LLVM backend : Folder structure of generated application has changed. It is now
  <pre>
    /
    |-bin/
    |-build/
    |-libs/
    |  |-orcc/
    |  |-roxml/
    |  |-windows/
    |-src
  </pre>
  CMake must be run from the root folder of the application (and not
  from src/ anymore)
  Final executable is now written in bin/ folder
- UI: Improve user experience by printing better error messages.
	
### Bugfixes

- UI: Removing an existing mapping was not possible without removing the runtime configuration.
- Tools: The classifier, the action merger and the actor merger are (almost) working back and their performances have been improved.
- Interpreter: working with imports within imports.
- RoXML: Update to latest version to fix some parsing errors on Windows caused by CRLF for end of line.

### Misc

- Move the 'models' plugin in Orcc repository and remove dependencies to dftools.
- The Java backend is now using Xtend instead StringTemplate to generate the files.

# 1.1.1 April 2012

### Notable changes and features

- Interpreter: bit exact simulation and floating point simulation.

### Smaller features

- TTA backend: reduction of the logic utilization on the target platform.

# 1.1 March 2012

### Notable changes and features

- Embedded runtime libraries (C, Java and TTA) automatically extracted during the code generation.

### Smaller features

- TTA back-end: support Xilinx device.
- Code generator is now able to print For loops.

### Misc

- Addition of a New Control Flow Graph API.
- Removed dependencies to JGraphT (FSM builder, Merger, etc) in favor of DFtools graph API.

# 1.0 January 2012

### Notable changes and features

- First usable version of the TTA back-end (only for Altera device).

### Misc

- Deletion of the VHDL back-end (not maintained anymore).
- Deletion of the C++ back-end (not used anymore).

# 0.9.5 May 2012

### Notable changes and features

- Addition of an earlier version of the TTA back-end.
