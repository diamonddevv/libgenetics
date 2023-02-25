package net.diamonddev.libgenetics.common.api.v1.util;

public interface ReturnableConsumer<R, T> {
    R accept(T t);
}
