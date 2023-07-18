package net.diamonddev.libgenetics.common.api.v1.util;

public class Pair<A, B> {
    private A a;
    private B b;

    private Pair(A a, B b) {
        this.a = a; this.b = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    public A getLeft() {
        return a;
    }
    public void setLeft(A a) {
        this.a = a;
    }

    public B getRight() {
        return b;
    }
    public void setRight(B b) {
        this.b = b;
    }
}
