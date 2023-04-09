package net.diamonddev.libgenetics.common.api.v1.network.nerve;


public enum Pathway {
    S2C,
    C2S;

    public static Pathway other(Pathway pathway) {
        return pathway == C2S ? S2C : C2S;
    }
}
