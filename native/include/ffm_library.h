#pragma once

#include <string>
#include <functional>

#ifdef DLLDIR_EX
    #define DLLDIR  __declspec(dllexport)   // export DLL information
#else
    #define DLLDIR  __declspec(dllimport)   // import DLL information
#endif

extern "C" {
    typedef struct Point {
        int x;
        int y;
    } Point;

    void DLLDIR printHello();

    void DLLDIR passHello(void(*callback)(const char*));

    void DLLDIR vec4add(const float *a, const float *b, float *r);

    void DLLDIR pointAddRef(const Point *a, const Point *b, Point *r);

    Point DLLDIR pointAdd(const Point a, const Point b);
}