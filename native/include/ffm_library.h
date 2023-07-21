#pragma once

#include <string>
#include <functional>

#ifdef DLLDIR_EX
    #define DLLDIR  __declspec(dllexport)   // export DLL information
#else
    #define DLLDIR  __declspec(dllimport)   // import DLL information
#endif

extern "C" {
    void DLLDIR printHello();

    void DLLDIR passHello(void(*callback)(const char*));

    void DLLDIR vec4add(const float *a, const float *b, float *r);
}