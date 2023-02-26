package net.diamonddev.libgenetics.common.api.v1.util;

public interface ReturnableTriConsumer<R, A, B ,C> {
    R accept(A a, B b, C c);
}
