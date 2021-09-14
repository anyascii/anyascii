cmake_minimum_required(VERSION 3.16)
project(anyascii C)

add_library(anyascii anyascii.h anyascii.c)

enable_testing()
add_executable(test-exe test.c)
target_link_libraries(test-exe anyascii)
add_test(test test-exe)
