package com.snaker.common.pojo.entity;

/**
 * 返回两个对象
 */
public class ThreeTuple<T1, T2, T3> {
    private T1 t1;

    private T2 t2;

    private T3 t3;

    public ThreeTuple(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public T1 getT1() {
        return t1;
    }

    public T2 getT2() {
        return t2;
    }

    public T3 getT3() {
        return t3;
    }
}