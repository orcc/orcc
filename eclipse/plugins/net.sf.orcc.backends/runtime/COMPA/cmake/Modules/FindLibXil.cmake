# - Try to find libxil.a 

find_path(LibXil_INCLUDE_DIR xparameters.h
		HINTS ${CMAKE_BINARY_DIR}/../../mb0_bsp/mb0_microblaze_0/include)
set(LibXil_INCLUDE_DIRS ${LibXil_INCLUDE_DIR} )

find_library(LibXil_LIBRARY xil 
		HINTS ${CMAKE_BINARY_DIR}/../../mb0_bsp/mb0_microblaze_0/lib/)
set(LibXil_LIBRARIES ${LibXil_LIBRARY} )

include(FindPackageHandleStandardArgs)
find_package_handle_standard_args(LibXil DEFAULT_MSG LibXil_LIBRARY LibXil_INCLUDE_DIR)

mark_as_advanced(LibXil_INCLUDE_DIR LibXil_LIBRARY )