@echo off
set OPENDF=%~f1%\core
pushd actors
FOR %%f IN (*.cal) DO CALL ..\open_cal %%f
popd
