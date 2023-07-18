package net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer;

import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileWrapper;
import net.diamonddev.libgenetics.common.api.v1.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ConfigSerializer {
    String getExpectedFileExtension();

    default <T> Pair<File, Boolean> fetchAndCreateFileIfNeeded(ChromosomeConfigFile config, Class<T> readClass) throws IOException {
        String path = ChromosomeConfigFileWrapper.getPath(config.getFilePathFromConfigDirectory());
        Logger logger = LogManager.getLogger(path + " Config File Creator");

        StringBuilder amendPath = new StringBuilder(); // create required directories because weird
        String[] filePathParts = path.split("/");
        int pl = filePathParts.length;
        for (String directory : filePathParts) {
            if (pl > 1) {
                amendPath.append(directory);
                var thisIsHereToGetRidOfTheWarningNoOtherReason = new File(amendPath.toString()).mkdir();
                amendPath.append("/");
                pl--;
            }
        }

        boolean created = false;
        File file = new File(path);
        if (file.createNewFile()) {
            logger.info("Created Config File at {}", file);
            created = true;
        }

        return Pair.of(file, created);
    }

    <T> T readFileToClass(ChromosomeConfigFile config, Class<T> readClass, FileReader reader) throws IOException;
    <T> void writeClassToFile(ChromosomeConfigFile config, Class<T> readClass, File file) throws IOException;


}
