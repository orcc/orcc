

# Start with Orcc

  1. Install Orcc: http://orcc.sourceforge.net/getting-started/install-orcc/
  2. Say Hello to Orcc: http://orcc.sourceforge.net/tutorials/hello-orcc/
  3. Write a very simple Network: http://orcc.sourceforge.net/tutorials/a-very-simple-actor/
  4. Get the existing applications: http://orcc.sourceforge.net/getting-started/get-applications/
  5. Build an HEVC video decoder: http://orcc.sourceforge.net/tutorials/make-an-hevc-decoder/

# Install the TTA-based Co-design Environment (TCE)

  1. Download the specific branch of the TCE using Bazaar: `bzr branch lp:~elldekaa/tce/Multiprocessor`
  2. Install the TCE as described in the installation procedure available in tce/tce/INSTALL.Since you have download the TCE from the version control, you have to do the optional step 'Creating the build scripts' before building the TCE).
 
# Try the TTA backend of Orcc

  1. Update Orcc to its last development revision using the nightly update site available at http://orcc.sourceforge.net/nightly/
  2. Be sure to have LLVM and TCE tools in your $PATH