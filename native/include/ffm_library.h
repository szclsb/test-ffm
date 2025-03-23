#pragma once

#include <string>
#include <functional>

extern "C" {
    typedef struct Point {
        int x;
        int y;
    } Point;

    __declspec(dllexport) void printHello();

    __declspec(dllexport) void passHello(void(*callback)(const char*));

    __declspec(dllexport) const char* getHello();

    __declspec(dllexport) void* allocateForeignInt(int value);

    __declspec(dllexport) void freeForeign(void* p);

    __declspec(dllexport) void vec4add(const float *a, const float *b, float *r);

    __declspec(dllexport) float addFloat(const float a, const float b);

    __declspec(dllexport) void pointAddRef(const Point *a, const Point *b, Point *r);

    __declspec(dllexport) Point pointAdd(const Point a, const Point b);
}