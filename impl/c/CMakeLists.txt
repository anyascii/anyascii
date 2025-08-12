cmake_minimum_required(VERSION 3.1...4.1 FATAL_ERROR)

project(anyascii VERSION 0.4.0 LANGUAGES C)
set(CMAKE_C_EXTENSIONS OFF)

add_library(anyascii anyascii.h anyascii.c)
set_property(TARGET anyascii PROPERTY C_STANDARD 90)

add_executable(anyascii-main utf8.h utf8.c main.c)
set_property(TARGET anyascii-main PROPERTY C_STANDARD 99)
set_property(TARGET anyascii-main PROPERTY OUTPUT_NAME anyascii)
target_link_libraries(anyascii-main anyascii)

enable_testing()
add_executable(anyascii-test utf8.h utf8.c test.c)
set_property(TARGET anyascii-test PROPERTY C_STANDARD 99)
target_link_libraries(anyascii-test anyascii)
add_test(test anyascii-test)
