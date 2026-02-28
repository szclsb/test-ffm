cmake_minimum_required(VERSION 3.20)
project(ffm)

set(CMAKE_CXX_STANDARD 20)
set(SOURCE_FILES
        cpp/ffm_library.cpp
        )
add_library(${PROJECT_NAME} SHARED ${SOURCE_FILES})
target_include_directories(${PROJECT_NAME} PUBLIC include)
