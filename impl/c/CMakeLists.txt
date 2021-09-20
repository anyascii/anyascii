cmake_minimum_required(VERSION 2.8.12.2)
project(anyascii C)

add_library(anyascii anyascii.h anyascii.c)

add_executable(anyascii-main utf8.h utf8.c main.c)
set_target_properties(anyascii-main PROPERTIES OUTPUT_NAME anyascii)
target_link_libraries(anyascii-main anyascii)

enable_testing()
add_executable(anyascii-test utf8.h utf8.c test.c)
target_link_libraries(anyascii-test anyascii)
add_test(test anyascii-test)
