@echo off
SET F=%1
echo **************** Testing %~n1%... ************************
REM java -cp "%OPENDF%\classes;%OPENDF%\source\xslt;%OPENDF%\lib\saxon8.jar;%OPENDF%\lib\saxon8-dom.jar" net.sf.opendf.cli.Simulator -mp . %~n1
java -cp "%OPENDF%\classes;%OPENDF%\source\xslt;%OPENDF%\lib\saxon8.jar;%OPENDF%\lib\saxon8-dom.jar" net.sf.opendf.cli.SSAGenerator %1 %2 %3 %4 %5 %6 %7 %8