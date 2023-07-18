package net.diamonddev.libgenetics.common.api.v1.config.chromosome;

import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.ConfigSerializer;
import net.diamonddev.libgenetics.common.api.v1.util.Pair;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChromosomeConfigFileWrapper {

    public final String filename;
    private final Logger logger;
    private final ChromosomeConfigFile config;

    protected ChromosomeConfigFileWrapper(ChromosomeConfigFile configFile) {
        this.filename = configFile.getFilePathFromConfigDirectory();
        this.config = configFile;
        this.logger = LogManager.getLogger(filename + " Config File Manager");
    }

    public <T> T read(Class<T> readClass) {
        try {
            ConfigSerializer serializer = this.config.getSerializer();


            Pair<File, Boolean> paired = serializer.fetchAndCreateFileIfNeeded(config, readClass);
            File file = paired.getLeft();

            FileReader reader = new FileReader(file);
            T returned = serializer.readFileToClass(this.config, readClass, reader);
            reader.close();

            FileWriter writer = new FileWriter(file);
            reader = new FileReader(file);

            if (paired.getRight()) serializer.writeClassToFile(config, readClass, writer, reader);

            reader.close();
            writer.close();

            if (paired.getRight()) returned = readNoFileManagement(readClass);

            return returned;

        } catch (IOException e) {
            throw new RuntimeException("Malformed Config File of serializer class '" + this.config.getSerializer().getClass() + "' at '" + getPath(this.filename) + "' (Something went wrong)");
        }
    }

    public <T> T readNoFileManagement(Class<T> clazz) {
        try {
            FileReader r = new FileReader(ChromosomeConfigFileWrapper.getPath(config.getFilePathFromConfigDirectory()));
            T obj = this.config.getSerializer().readFileToClass(this.config, clazz, r);
            r.close();
            return obj;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPath(String filename) {
        String path = FabricLoaderImpl.INSTANCE.getConfigDir() + "/" + filename;
        return path.replace('\\', '/'); // correct path because oPeRaTiNg SyStEmS aRe WeIrD sOmEtImEs
    }
}
