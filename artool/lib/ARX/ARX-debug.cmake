#----------------------------------------------------------------
# Generated CMake target import file for configuration "Debug".
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Import target "ARX" for configuration "Debug"
set_property(TARGET ARX APPEND PROPERTY IMPORTED_CONFIGURATIONS DEBUG)
set_target_properties(ARX PROPERTIES
  IMPORTED_LOCATION_DEBUG "${_IMPORT_PREFIX}/lib/arm64-v8a/libARX.so"
  IMPORTED_SONAME_DEBUG "libARX.so"
  )

list(APPEND _cmake_import_check_targets ARX )
list(APPEND _cmake_import_check_files_for_ARX "${_IMPORT_PREFIX}/lib/arm64-v8a/libARX.so" )

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
