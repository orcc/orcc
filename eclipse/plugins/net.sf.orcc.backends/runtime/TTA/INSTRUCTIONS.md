# Discovering the TTA backend of Orcc

The TTA backend implements a full co-synthesis flow to execute an RVC-CAL application on a highly configurable embedded multi-core platform based on [Transport-Trigger Architecture](http://en.wikipedia.org/wiki/Transport_triggered_architecture).

The TTA backend being highly experimental, you may need to update Orcc to its last development revision. To do so, use the [nightly update site](http://orcc.sourceforge.net/nightly/).

## Start with Orcc

We assume that you have already [installed Orcc](http://orcc.sourceforge.net/getting-started/install-orcc/). Since this co-synthesis flow is quite complex to use, we recommend you to start by familiarize yourself to Orcc with the following tutorials:
- Start with the simple [say hello to Orcc](http://orcc.sourceforge.net/tutorials/hello-orcc/).
- Then, try to [write a very simple network](http://orcc.sourceforge.net/tutorials/a-very-simple-actor/).
- And, [get the existing applications](http://orcc.sourceforge.net/getting-started/get-applications/).
- In order to [build and run an HEVC video decoder](http://orcc.sourceforge.net/tutorials/make-an-hevc-decoder/).

## Set up the TCE

The TTA backend uses an open-source toolset, called [TTA-based Co-design Environment (TCE)](http://tce.cs.tut.fi/), to perform the lower stages of the design flow :
- Download a specific branch of the TCE using Bazaar `bzr branch lp:~elldekaa/tce/Multiprocessor`.
- Install the TCE as described in the installation procedure available in tce/tce/INSTALL. Since the TCE has been downloaded from the version control, you have to do the optional step 'Creating the build scripts' before building the TCE.
- Do not forget to check that your environment is correcty setting up by running ``tce-selftest -v``.

If you do not install LLVM and TCE in the default path, you may need to put them in your $PATH and $LD_LIBRARY_PATH, or add it yourself.

```
export PATH=/opt/llvm/bin:$PATH
export PATH=/opt/tce/bin:$PATH
export LD_LIBRARY_PATH=/opt/llvm/lib:${LD_LIBRARY_PATH}
export LD_LIBRARY_PATH=/opt/tce/lib:${LD_LIBRARY_PATH}
```

## Write your own RVC-CAL application

You may have to write your own application. The TTA backend should support any RVC-CAL application, but the simulator currently supports only a subset of CAL native functions:
- The basic printing function, ``print`` and ``println``.
- Some functions to acces an input file from the simulator, check the actor ``fr.irisa.common.Source`` for an example. Be careful, you need to import the *Source* unit from the System project ``import std.stdio.Source.*;``.
- A function to get the number of cycle that occurs since the beginning of the simulation ``print_cyclecount()``. You need to import the *Simulation* unit from the Reasearch project ``import fr.irisa.common.Simulation.*;``

## Co-design an application

- Generate an application, such as the 'HelloWorld' your write in the tutorial, using the TTA backend of Orcc. Several options are available :
  - **Mapping** : Set up your own partioning using an input XCF file (you can set up using the graphical interface as well, check the mapping tab).
  - **Reduce interconnections network** : Try to map the FIFO channels on the same memory to reduce the connectivity between processor.
  - **Profile** : Forbid the inlining of the actions in order to evaluate their cost with profiling tools.
  - **Debug** : Debugging mode, the *printing* is not removed... Be careful with the performance, since printing is costly on the TTA processors.
  - **FIFO size** : Set up the size of the FIFO channels.
- Check the generated folder, it should be organized as follow:
  - A folder *actors* that contains the translation of the actors in LLVM assembly, 
  - A folder *libs* that contains the files needed to finish the generation of the design. For instance, a useful python script called 'generate' that helps you to use the TCE.
  - A folder *_generate* that is used by the 
  - And a folder for each processor that contains the hardware description of the processor and the software code executed on it. 
  - Some others files such as the VHDL description of the top-level element of the design.
- Finish the generation by executing the *generate* script that runs the TCE tools. Basically, you can compile your application with the command ``./libs/generate -c .``. 
More details about the *generate* script can be found with the help, ``./libs/generate -h``.

## Simulate an execution

If your application has been succesfully compiled, the execution of the system can be quickly evaluated thanks to a cycle-accurate simulator, called TTANetSim, included in TCE. TTANetSim allows two kinds of simulation :
- First, you need to help the simulator to localize the native functions using the command ``export TCE_OSAL_PATH=path/to/libs/opset``.
- The simulation of the whole platform, which can be launched by the command ``ttanetsim -n [PNDF file]`` with an optional input file ``-i [input file]``.
- The simulation of a single processor in a standalone way, which can be launched by the command ``ttanetsim -n [PNDF file] -t [processor name]``, in order to evaluate the processor independantly. If the execution of the simulated processor depends on input tokens coming from another processor, then the FIFO channel can be simulated using the *trace* files, generated from the C backend, that have to be located in the ``trace/`` folder.

## Profile an application

You can accuratly profile the execution of an application using TTANetSim, in order to get some feedback about the cost of your actors:
- Generate the application with the **Profile** option activated.
- Obviously, you have to recompile your application (``./libs/generate -c .``).
- Run the simulation in profiling mode with ``-p`` option.
- Since the simulation is probably never ending alone, you need to exit it manually using ``CTRL-C`` command then ``q`` and ``Enter``.
- Make the profiling data compatible with KCacheGrind [this](http://tce.cs.tut.fi/user_manual/TCE/node41.html#SECTION00714100000000000000).
- Open the new produced files with KCacheGrind to analyse it.

## Synthesis the design

You can generate the hardware description of your platform using ``./libs/generate -g .``. After a successfull generation of the hardware platform, you can synthesised it for your FPGA using your favorite software (ISE, Quartus, etc). Keep in mind that native functions are not supported on the FPGA...

