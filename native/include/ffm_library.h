#pragma once

#include <string>
#include <functional>

extern "C" {
    typedef struct Point {
        int x;
        int y;
    } Point;

    typedef struct Instance_T* Instance;

    __declspec(dllexport) void printHello();

    __declspec(dllexport) void passHello(void(*callback)(const char*));

    __declspec(dllexport) const char* getHello();

    __declspec(dllexport) void* allocateForeignInt(int value);

    __declspec(dllexport) void freeForeign(void* p);

    __declspec(dllexport) void vec4add(const float *a, const float *b, float *r);

    __declspec(dllexport) float addFloat(const float a, const float b);

    __declspec(dllexport) void pointAddRef(const Point *a, const Point *b, Point *r);

    __declspec(dllexport) Point pointAdd(const Point a, const Point b);

    __declspec(dllexport) void createInstance(int a, int b, Instance* pInstance);

    __declspec(dllexport) void useInstance(Instance instance);

    __declspec(dllexport) void destroyInstance(Instance instance);

    __declspec(dllexport) int increment_int(int value);

    __declspec(dllexport) void increment_p_int(int* pValue);

    __declspec(dllexport) void increment_pp_int(int** ppValue);
}