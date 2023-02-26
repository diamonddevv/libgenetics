package net.diamonddev.libgenetics.common.api.v1.util;

public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
