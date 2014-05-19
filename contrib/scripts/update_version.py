#!/usr/bin/python
'''
Copyright (c) 2014, IETR/INSA of Rennes
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.
* Neither the name of the IETR/INSA of Rennes nor the names of its
contributors may be used to endorse or promote products derived from this
software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.
'''

import os, sys, re
import argparse
import fnmatch

def main():
    global parser
    args = parser.parse_args()

    workdir = args.directory
    current = args.current
    new     = args.new

    for root, dirnames, filenames in os.walk(workdir):
        for filename in fnmatch.filter(filenames, 'feature.xml'):
            filepath = os.path.join(root, filename)

            basePattern = 'version="%s.qualifier"'
            simpleReplace(filepath, basePattern % current, basePattern % new)

        for filename in fnmatch.filter(filenames, 'pom.xml'):
            filepath = os.path.join(root, filename)

            basePattern = '<version>%s.qualifier</version>'
            simpleReplace(filepath, basePattern % current, basePattern % new)

        for filename in fnmatch.filter(filenames, 'MANIFEST.MF'):
            filepath = os.path.join(root, filename)

            basePattern = 'Bundle-Version: %s.qualifier'
            simpleReplace(filepath, basePattern % current, basePattern % new)

            basePattern = 'net.sf.orcc.([a-zA-Z.]+);bundle-version="%s"(,|;)'   % current
            replacement = 'net.sf.orcc.\\1;bundle-version="%s"\\2'              % new
            regExpReplace(filepath, basePattern, replacement)

def simpleReplace(filepath, pattern, subst):
    f = open(filepath,'r')
    filedata = f.read()
    f.close()
    newdata = filedata.replace(pattern,subst)
    f = open(filepath,'w')
    f.write(newdata)
    f.close()

def regExpReplace(filepath, pattern, subst):
    f = open(filepath,'r')
    filedata = f.read()
    f.close()
    newdata = re.sub(pattern, subst, filedata)
    f = open(filepath,'w')
    f.write(newdata)
    f.close()

def setupCommandLine():
    global parser
    parser = argparse.ArgumentParser(description='', version=1.0)
    options = parser.add_argument_group(title="Parameters")
    options.add_argument("-d", "--directory", action="store", dest="directory", required=True,
                        help="Directory where replacements must be performed")
    options.add_argument("-c", "--current", action="store", dest="current", required=True,
                        help="The current version number (3 digits: 1.2.3)")
    options.add_argument("-n", "--new", action="store", dest="new", required=True,
                        help="The new version number (3 digits: 1.2.3)")

if __name__ == "__main__":
    setupCommandLine()
    main()
    print("All replacements have been performed. If you updated Orcc version number,")
    print("don't forget to apply this script on Xronos too")
