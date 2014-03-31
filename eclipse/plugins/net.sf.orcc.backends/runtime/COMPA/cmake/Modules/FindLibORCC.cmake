# - Try to find liborcc.a 

find_path(LibORCC_INCLUDE_DIR orcc.h 
		HINTS ${CMAKE_SOURCE_DIR}/../libs/orcc/include)
set(LibORCC_INCLUDE_DIRS ${LibORCC_INCLUDE_DIR} )

find_library(LibORCC_LIBRARY orcc 
		HINTS ${CMAKE_BINARY_DIR}/../libs/orcc)
set(LibORCC_LIBRARIES ${LibORCC_LIBRARY} )

include(FindPackageHandleStandardArgs)
find_package_handle_standard_args(LibORCC DEFAULT_MSG LibORCC_LIBRARY LibORCC_INCLUDE_DIR)

mark_as_advanced(LibORCC_INCLUDE_DIR LibORCC_LIBRARY )