#include "../include/ffm_library.h"
#include <iostream>

using namespace std;

void printHello() {
    cout << "hello1 from dll" << endl;
}

void passHello(void(*callback)(const char*)) {
    callback("hello2 from dll");
}

const char* getHello() {
    const char* x = "hello3 from dll";
    return x;
}

void* allocateForeignInt(int value) {
    int* p = (int*) malloc(4);
    *p = value;
    return p;
}

void freeForeign(void* p) {
    free(p);
}

float addFloat(const float a, const float b) {
    return a + b;
}

void vec4add(const float *a, const float *b, float *r) {
    *r = *a + *b;
    *(r+1) = *(a+1) + *(b+1);
    *(r+2) = *(a+2) + *(b+2);
    *(r+3) = *(a+3) + *(b+3);
}

void pointAddRef(const Point *a, const Point *b, Point *r) {
    r->x = a->x + b->x;
    r->y = a->y + b->y;
}

Point pointAdd(const Point a, const Point b) {
    return Point{a.x + b.x, a.y + b.y};
}

void createInstance(int a, int b, Instance* pInstance) {
    Instance instance = (Instance) malloc(8);
    *((int*)instance + 0) = a;
    *((int*)instance + 4) = b;
    *pInstance = instance;
}

void useInstance(Instance instance) {
    int a = *((int*)instance + 0);
    int b = *((int*)instance + 4);
    const int value = a + b;
    cout << "instance value: " << value << endl;
}

void destroyInstance(Instance instance) {
    free(instance);
}

int increment_int(int value) {
    return value + 1;
}

void increment_p_int(int* pValue) {
    (*pValue)++;
}

void increment_pp_int(int** ppValue) {
    increment_p_int(*ppValue);
}