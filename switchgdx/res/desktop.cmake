cmake_minimum_required(VERSION 3.0)
project(SwitchGDX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-volatile")
set(CMAKE_C_STANDARD 11)
set(OpenGL_GL_PREFERENCE GLVND)

SET(CMAKE_C_RESPONSE_FILE_LINK_FLAG "@")
SET(CMAKE_CXX_RESPONSE_FILE_LINK_FLAG "@")
SET(CMAKE_NINJA_FORCE_RESPONSE_FILE 1 CACHE INTERNAL "")

list(APPEND CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR})

include(FindPkgConfig)
pkg_search_module(SDL2 REQUIRED sdl2)
pkg_search_module(SDL2_mixer REQUIRED SDL2_mixer)
find_package(OpenGL REQUIRED)
find_package(ZLIB REQUIRED)
find_package(Threads REQUIRED)
find_package(Freetype REQUIRED)
find_package(CURL REQUIRED)
find_package(ZZip REQUIRED)
find_package(FFI REQUIRED)

add_definitions(-DNOJNI -DASMJIT_STATIC)

file(GLOB_RECURSE SRCS src/*.cpp src/*.c)
include_directories(src)
add_executable(SwitchGDX ${SRCS})

target_compile_options(SwitchGDX PRIVATE -Wno-return-type)

target_include_directories(SwitchGDX PUBLIC ${SDL2_INCLUDE_DIRS} ${SDL2_mixer_INCLUDE_DIRS} ${OPENGL_INCLUDE_DIR} ${ZLIB_INCLUDE_DIRS} ${FREETYPE_INCLUDE_DIRS} ${CURL_INCLUDE_DIRS} ${ZZip_INCLUDE_DIRS} ${FFI_INCLUDE_DIRS})
target_link_libraries(SwitchGDX /opt/homebrew/opt/sdl2/lib/libSDL2.dylib /opt/homebrew/Cellar/sdl2_mixer/2.8.0/lib/libSDL2_mixer.dylib ${OPENGL_LIBRARIES} ${ZLIB_LIBRARIES} ${FREETYPE_LIBRARIES} ${CURL_LIBRARIES} ${ZZip_LIBRARIES} ${FFI_LIBRARIES})
if(CMAKE_THREAD_LIBS_INIT)
  target_link_libraries(SwitchGDX "${CMAKE_THREAD_LIBS_INIT}")
endif()
if(WIN32)
  target_link_libraries(SwitchGDX wsock32 ws2_32)
endif()
