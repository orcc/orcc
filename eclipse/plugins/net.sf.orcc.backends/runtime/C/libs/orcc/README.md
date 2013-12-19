Orcc Actor Mapping Library
==========================

Orcc-map is a library dedicated to the mapping of dynamic dataflow programs over multi-core platforms.

Installation
------------

First, you have to download and install [Metis](http://glaros.dtc.umn.edu/gkhome/metis/metis/overview).

```
wget http://glaros.dtc.umn.edu/gkhome/fetch/sw/metis/metis-5.1.0.tar.gz
tar xfz metis-5.1.0.tar.gz
cd metis-5.1.0/
make config && make
sudo make install
```

Then, you can download and compile Orcc-map.

```
git clone git@github.com:orcc/orcc-map.git
cd orcc-map/
mkdir build
cd build/
cmake .. -DCMAKE_BUILD_TYPE=Release
make
```

Orcc-map is finally ready to be used !

Documentation
-------------

Run ``orcc-map --help`` to print the documentation.
