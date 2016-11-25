package com.dock.test;

/**
 * 
 * @author WongBen
 *
 * @param <A>
 * @param <B>
 */
public class Turple<A, B> {
    public final A first;
    public final B second;
     
    public Turple(A a, B b) {
        this.first = a;
        this.second = b;
    }
}