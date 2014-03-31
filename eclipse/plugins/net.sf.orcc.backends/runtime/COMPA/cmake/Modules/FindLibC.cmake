# - Try to find libc.a 

find_path(LibC_INCLUDE_DIR xil_printf.h 
		HINTS ${CMAKE_BINARY_DIR}/../../mb0_bsp/mb0_microblaze_0/include)
set(LibC_INCLUDE_DIRS ${LibC_INCLUDE_DIR} )

find_library(LibC_LIBRARY c 
		HINTS ${CMAKE_BINARY_DIR}/../../mb0_bsp/mb0_microblaze_0/lib/)
set(LibC_LIBRARIES ${LibC_LIBRARY} )

include(FindPackageHandleStandardArgs)
find_package_handle_standard_args(LibC DEFAULT_MSG LibC_LIBRARY LibC_INCLUDE_DIR)

mark_as_advanced(LibC_INCLUDE_DIR LibC_LIBRARY )