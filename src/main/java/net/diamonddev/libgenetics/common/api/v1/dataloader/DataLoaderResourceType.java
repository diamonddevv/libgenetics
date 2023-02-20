package net.diamonddev.libgenetics.common.api.v1.dataloader;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

public interface DataLoaderResourceType {
    Identifier getId();
    void addJsonKeys(ArrayList<String> keys);
}
