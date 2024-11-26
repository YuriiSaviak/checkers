cmake_minimum_required(VERSION 3.24.2)
project(cPlusPlus)

set(CMAKE_CXX_STANDARD 20)


set(JAVA_HOME "C:\\Program Files\\Java\\jdk-22")
include_directories(${JAVA_HOME}/include)
include_directories(${JAVA_HOME}/include/win32)
add_library(cPlusPlus SHARED game_Game.cpp Board.cpp)
target_link_options(cPlusPlus PRIVATE -static)
