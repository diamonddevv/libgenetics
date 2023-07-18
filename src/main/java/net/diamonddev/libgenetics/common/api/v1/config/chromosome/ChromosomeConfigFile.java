package net.diamonddev.libgenetics.common.api.v1.config.chromosome;


import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.ConfigSerializer;

public interface ChromosomeConfigFile {
    String getFilePathFromConfigDirectory();
    ConfigSerializer getSerializer();
}
