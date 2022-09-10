package net.diamonddev.libgenetics.api;

import net.minecraft.util.Identifier;

public class IdentifierFactory {
    private final String namespace;

    public IdentifierFactory(String namespace) {
        this.namespace = namespace;
    }

    public Identifier create(String path) {
        return new Identifier(this.namespace, path);
    }
}
