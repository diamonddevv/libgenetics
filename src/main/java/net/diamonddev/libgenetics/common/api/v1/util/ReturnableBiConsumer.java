package net.diamonddev.libgenetics.common.api.v1.util;

public interface ReturnableBiConsumer<R, A, B> {
    R accept(A a, B b);
}
