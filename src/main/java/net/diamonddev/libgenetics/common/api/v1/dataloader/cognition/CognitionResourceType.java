package net.diamonddev.libgenetics.common.api.v1.dataloader.cognition;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

public interface CognitionResourceType {
    Identifier getId();
    void addJsonKeys(ArrayList<String> keys);
}
