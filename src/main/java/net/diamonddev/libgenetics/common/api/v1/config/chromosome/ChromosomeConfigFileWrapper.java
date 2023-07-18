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
            boolean wasCreated = paired.getRight();

            if (wasCreated) {
                serializer.writeClassToFile(config, readClass, file);
            }

            FileReader reader = new FileReader(file);
            T readToClass = serializer.readFileToClass(this.config, readClass, reader);
            reader.close();

            if (readToClass == null) {
                // delete file, try again
                logger.warn("Config at path {} was empty, deleting file and attempting to re-create..", getPath(this.filename));
                if (!file.delete()) {
                    logger.warn("Deletion of {} failed, crashing..", getPath(filename));
                    throw new RuntimeException("Failed to delete config file at '"+ getPath(filename) +"' to fix/recreate.");
                } else {
                    return read(readClass);
                }
            }

            return readToClass;

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
