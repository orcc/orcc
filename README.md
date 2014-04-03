Open RVC-CAL Compiler
=====================

Orcc is a development environment under BSD license dedicated to dataflow programming. Its primary purpose is to provide developers with a compiler infrastructure to allow several languages (software and hardware) to be generated from the same description composed of RVC-CAL actors and XDF networks. Orcc does not generate assembly or executable code directly, rather it generates source code that must be compiled by another tool.

Documentation
-------------

* Documentation is located at http://orcc.sf.net
* Javadoc is available at http://orcc.sf.net/javadoc/

Applications
------------

Applications are available in the Open RVC-CAL Applications project : https://github.com/orcc/orc-apps

Backends
--------

The current status of the existing back-ends is summed up in the following table :

<table>
  <tr>
    <th></th>
    <th>MPEG-4 Part 2 SP</th>
    <th>MPEG-4 Part 10</th>
    <th>MPEG-H Part 2</th>
    <th>JPEG</th>
  </tr>
  <tr>
    <th>C</th>
    <td>OK</td>
    <td>OK</td>
    <td>OK</td>
    <td>OK</td>
  </tr>
  <tr>
    <th>HLS</th>
    <td>OK</td>
    <td>NOK</td>
    <td>NOK</td>
    <td>N/A</td>
  </tr>
  <tr>
    <th>Jade</th>
    <td>OK</td>
    <td>OK</td>
    <td>OK</td>
    <td>N/A</td>
  </tr>
  <tr>
    <th>LLVM</th>
    <td>OK</td>
    <td>OK</td>
    <td>OK</td>
    <td>N/A</td>
  </tr>
  <tr>
    <th>Promela</th>
    <td>OK</td>
    <td>N/A</td>
    <td>N/A</td>
    <td>N/A</td>
  </tr>
  <tr>
    <th>Simulator</th>
    <td>OK</td>
    <td>OK</td>
    <td>NOK</td>
    <td>OK</td>
  </tr>
  <tr>
    <th>TTA</th>
    <td>OK</td>
    <td>NOK</td>
    <td>OK</td>
    <td>N/A</td>
  </tr>
  <tr>
    <th>Xronos</th>
    <td>OK</td>
    <td>NOK</td>
    <td>N/A</td>
    <td>OK</td>
  </tr>
</table>
