@echo off

if defined VS100COMNTOOLS (
  echo Visual Studio 2010 found, calling "%VS100COMNTOOLS%vsvars32.bat"
  call "%VS100COMNTOOLS%vsvars32.bat"
) else if defined VS90COMNTOOLS (
  echo Visual Studio 2008 found, calling "%VS90COMNTOOLS%vsvars32.bat"
  call "%VS90COMNTOOLS%vsvars32.bat"
) else if defined VS80COMNTOOLS (
  echo Visual Studio 2005 found, calling "%VS80COMNTOOLS%vsvars32.bat"
  call "%VS80COMNTOOLS%vsvars32.bat"
) else (
  echo Visual Studio not found.
)

set CMAKE_PREFIX_PATH=%CD%\SDL-1.2.14;%CD%\SDL_image-1.2.10;%CD%\orcc
set CMAKE_INSTALL_PREFIX=%CD%\orcc

echo Launching "cmake-gui"
cmake-gui
