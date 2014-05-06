# Next release
### Notable changes and features
### Bugfixes
- Network editor
    + Grouping/ungrouping instances in a Network now manage correctly arguments and parameters
    + Conflicts in created vertex names when grouping instances are now checked and resolved smoothly
### Known issues

# 2.0.0 April 29, 2014
### Notable changes and features
- Front-end:
    * Floating-point type network ports now supported
- User interface:
    * New network editor based on Eclipse Graphiti SDK. It can be used exactly in the same way as the original one but provides more flexibility for future improvements. Some exclusive features are already available:
        - Instances have different colors if assigned to networks, actors, or nothing.
        - All ports are displayed when an instance is refined.
        - Automatic layout (2 different algorithms).
        - Easy creation of connections: Drag and drop from a port to another
        - Easy creation of refined instances: Drag and drop an actor/network from the project to the current network (this also can be used to update refinement of existing instance, see #77).
        - Better control on properties definitions (variables, parameters, connection size, etc.) [#46].
        - Automatically group a set of selected instances in a new network. Selection is replaced by a new instance, refined on the newly created network, with all connections updated.
        - Automatically replace an instance refined on a network by the content of the network (all instances and connections).
    * CAL editor:
        - Warning on useless imports of units.
    * Miscellaneous
        - A default 'src' folder is created in each new Orcc project.
- Back-ends:
    * [C] Profiling capabilities (actors workload, communication, firings) thanks to code instrumentation: Generate an XML file that can be pretty printed afterwards.
    * [C] Dynamic mapping of actors on multi-core processors (different strategies are available). Mapping algorithms can also be used as independant program called Orcc-map.
    * [C] Global optimization of the generated code:
        - Avoid copy of tokens into local variables that are used as procedure arguments when FIFO accesses are aligned.
    * [C] Optimization of multi-core communications thanks to cache-efficient FIFO implementations.
    * [C] Easy kernel optimization using annotation in CAL code. This has been tested using SSE kernels in the HEVC decoder.
    * [C] Experimental debugging features:
        - Use "@DEBUG" annotation on actor header to display its internal scheduling during the execution.
        - Add option to find array out-of-bounds accesses using asserts.
    * [C/Simulator] Enable easy configuration of FIFO size thanks to an XML file (known as BXDF).
    * [C] Runtime library improvements:
        - Easy import of external libraries (Metis and OpenHEVC).
        - Use data structure representing dataflow-related information.
    * [TTA] Display estimation of actor memory consumption during code generation.
    * [TTA] Add support of code compressor to reduce memory consumption.
    * [Promela] Better, stronger and faster.
    * New DAL backend inheriting from the C backend has been added: This backend targets the Distributed Application Layer, a manycore-oriented programming framework developed at ETH Zürich.

### Bugfixes
- Front-end:
    * Refactoring features (rename or move file) have been updated
        - Work on Windows
        - [#71] When a .xdf file is renamed/moved, corrresponding .xdfdiag file follow the modification.
        - [#1] Better check for names conflicts between functions, procedures, variables, parameters, etc.
    * [#59] Fix the use of output list variables as procedure arguments.
- Backends:
    * [#56] Library files are not overwritten at code generation. Unmodified files are recompiled only if necessary.
    * [TTA, LLVM] [#83] Add support of self-loop actors.
- Network editor / User interface:
    * [#13] List of proposed entity for refinement only contains Actors and Networks (without Units anymore).
    * [#64] Network can be created only in a valid project's source folder.

### Known issues
- Refactoring: Moving more than one file at a time could cause errors in updated files. Undo should always be available if errors appears. Moving files one by one should work as expected.
- Using sub-list as procedure argument produces wrong code.

### Miscellaneous
- Java, C++ and OpenCL backends have been removed. They were not maintained for a long time, and seemed to be unused.
- SDL library:
    * Exported library for Windows has been updated to version 1.2.15, fixing compilation issues under x86_64 platforms [#12].
    * Useless dependencies to SDLImage have been removed.
    * Experimental support of SDL2 has been added
- Clean C runtime library:
    * Remove the actor mapping based on genetic algorithm.
    * Remove unfinished soket-based FIFO implementation.

# 1.4.0 October 2013

### Notable changes and features
- Backends:
    * New experimental HMPP backend
    * Buffer size is checked when compiling with orcc. Size must be a power of 2.
    * [C] FIFO optimizations to allow vectorization by compilers.
    * [LLVM, TTA] Apply transformation removing disconnected output ports.
    * [LLVM, TTA] Apply new short-circuit transformation.
    * [LLVM] Support of 64 bits types.
    * [TTA] New post treatment scripts for results analysis.
- Front-end:
    * Optimizations when building dependent entities

### Bugfixes
- Front-end:
    * [#5] When editing a Unit, dependent Actors are built in the correct folder
    * [#9] When an Actor or a Unit contains errors, the corresponding IR file is deleted. In that case,
running a backend stops with an error message.

### Known issues
- HEVC decoder built in release mode with LLVM Backend does not work.


# 1.3.1 May 2013

### Notable changes and features

- Front-end:
    * Annotations are allowed on a call instruction
- Backends:
    * [C] New option "-f <frames_count>" added to generated applications. It should replace "-l
<loops_count>" in most cases.

### Bugfixes

- Front-end:
    * Fixed #44: Use of an annotation multiple time in the same scope cause editor to report
a syntax error

# 1.3.0 March 2013

### Notable changes and features

- Front-end:
    * Optimization of the generated intermediated representation by removing most of useless
    memory copies.
    * New CLI option to build only the actors needed by a given network instead of building
    the whole project and all its dependancies.
- Simulator:
    * New option to generate files containing statistics and profiling data.
    * Simulation runtime has been reworked. A simulation can now be properly stopped by clicking on
    the red square in the console tab or by closing the display window.
- Backends:
    * Mapping files (XCF) are always generated, even if there is no mapping set in the UI.
    * New CLI options such as '-m2m' which applies the Multi2MonoToken transformation.
    * [C] Global improvement in the readibility of the generated code.
    * [C, TTA] Automatic actor mapping onto multicore platform based on profiling informations.
    * [LLVM, TTA] Direct broadcasting by the source actor instead of dedicated broadcast.
    * [TTA] Support light weight printing library of the TCE.
    * [TTA] Support a given set of 'native' functions for simulation purpose.
    * [TTA] Global performance improvement.
    * [TTA] Support of caching feature to speed-up the TCE compilation.
    * New 'High Level Synthesis' backend which uses Vivado toolset to generate HDL designs.
    * New 'COMPA' backend which generates C source code, without any dependency to external
    libraries (SDL, pthreads, etc.). This backend can't compile classical applications, because
    they use Display.cal and needs to link with SDL. This backend was needed by members of COMPA
    research project (see http://compa-ietr.insa-rennes.fr)
- User interface:
    * Improve code completion.
    * Global improvement of the informations displayed by the compiler. The following levels of
    messages are available: DEBUG, TRACE, NOTICE, WARNING, SEVERE.
- Classifier:
    * Supports now the Z3 solver v4.12+, the support of other solver is dropped.

### Bugfixes

- User interface:
    * When creating an instance in a graph, existing names are checked in case insensitive
    mode to fix Microsoft Windows bug when printing source files.
    * Graphiti does not crash anymore during the opening of an xdf containing errors.
    * Fix NullPointerException occuring when a wrong XDF path is set.
- Backends:
    * Disable threaded generation to fix random bugs in generated files (without performance lost).
    * (TTA) Transform all boolean variables in i8 since TCE do not support boolean.
    * Caching function: Only files different from existing ones are printed on disk.
    * Traces now works under windows, pathes are escaped with a backslash in source files generated.
- Fix classification in case of CSDF schedule depending of actor parameter.
- Fix merging of static actor.
- Fix a bug related to the support of floating-point operation.

### Misc

- Move Graphiti-editor plugins into Orcc repository. Only one eclipse feature has to be
installed to use Orcc. Separate Graphiti-editor plugin is not needed anymore.
- All backends use now Xtend instead of StringTemplate to print code.
- Deletion of the XLIM back-end (replaced by Xronos).

### Known issues

- Declaration of a variable outside the explicit 'var' declaration blocks.
- Wrong classification in case of complex data dependant behavior or floating points types usage.

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
- C/LLVM backend: Folder structure of generated application has changed. It is now
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
CMake must be run from the root folder of the application (and not from ```src``` anymore). Final executable
is now written in ```bin``` folder
- UI: Improve user experience by printing better error messages.

### Bugfixes

- UI: Removing an existing mapping was not possible without removing the runtime configuration.
- Tools: The classifier, the action merger and the actor merger are (almost) working back and their
performances have been improved.
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

# 0.9.5 May 2011

### Notable changes and features

- Addition of an earlier version of the TTA back-end.
