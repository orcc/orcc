# Discovering the TTA backend of Orcc

The TTA backend implements a full co-synthesis flow to execute an RVC-CAL application on a highly configurable embedded multi-core platform based on [Transport-Trigger Architecture](http://en.wikipedia.org/wiki/Transport_triggered_architecture).

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
 
## Co-design an application

- Generate an application, such as the 'HelloWorld' your write in the tutorial, using the TTA backend of Orcc. Several options are available :
  - **Mapping** :
  - **Reduce interconnections network** :
  - **Profile** :
  - **FIFO size** :
- Check the generated folder, it should be organized as follow:
  - A folder 'actors' that contains the translation of the actors in LLVM assembly, 
  - A folder 'libs' that contains the files needed to finish the generation of the design. For instance, a useful python script called 'generate' that helps you to use the TCE.
  - A folder '_generate' that is used by the 
  - And a folder for each processor that contains the hardware description of the processor and the software code executed on it. 
  - Some others files such as the VHDL description of the top-level element of the design.
- Finish the generation by executing the 'generate' script (``generate [options] input_directory``) that run the TCE tools. The following options are available:
  - ``-c``, ``--compile=[options]``: Compile the application from the generated LLVM assembly code into TTA binary
  - ``-g``, ``--generate=[options]``: Generate the VHDL files of the TTA processors
  - ``-d``, ``--debug``: Debug mode, generate extra files and print additionnal information

## Simulate an execution

The execution of the system can be quickly evaluated thanks to a cycle-accurate simulator, called TTANetSim, included in TCE. TTANetSim allows two kinds of simulation :
- The simulation of the whole platform, which can be launched by the command ``ttanetsim -n [PNDF file]`` with an optional input file ``-i [input file]``.
- The simulation of a single processor in a standalone way, which can be launched by the command ``ttanetsim -n [PNDF file] -t [processor name]``, in order to evaluate the processor independantly. If the execution of the simulated processor depends on input tokens coming from another processor, then the FIFO channel can be simulated using the *trace* files, generated from the C backend, that have to be located in the ``trace/`` folder.

Obviously, we assume that you have compile the application before simulate it.

## Synthesis the design

After a successfull generation of the hardware platform, you can synthesised it for your FPGA using your favorite software (ISE, Quartus, etc).

## FAQ

- The TTA backend being highly experimental, you may need to update Orcc to its last development revision. To do so, use the [nightly update site](http://orcc.sourceforge.net/nightly/).
- If you do not install LLVM and TCE in the default path, be sure to have them in your $PATH and $LD_LIBRARY_PATH, or add it yourself.

```
export PATH=/opt/llvm/bin:$PATH
export PATH=/opt/tce/bin:$PATH
export LD_LIBRARY_PATH=/opt/llvm/lib:${LD_LIBRARY_PATH}
export LD_LIBRARY_PATH=/opt/tce/lib:${LD_LIBRARY_PATH}
```