# Java Foreign Function and Memory API TEST

This project aims to showcase how *Java Foreign Function and Memory API (FFM)*
can be used with *Java Service Provider Interface (SPI)* to call native *C* code.

## Project structure

- **app**: Test program, loads the service providers using *SPI*.
- **api**: Provides the interfaces of the service providers.
- **impl**: Implements the default service providers by calling native *C* code using *FFM*.
- **foreign**: Implements the native C library and provides the `ffm.dll` using *CMake*.
(*C* code is a mess, I'm sorry :/ )

## Run Test Program

* CLI:
  ```
  java --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign ch.szclsb.test.ffm.Main.java
  ```
* Intellij: Add VM Options
  ```
  --enable-native-access=ALL-UNNAMED
  --add-modules jdk.incubator.foreign
  ```
