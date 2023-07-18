package net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer;

import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class PropertiesConfigSerializer implements ConfigSerializer {
    @Override
    public String getExpectedFileExtension() {
        return "properties";
    }

    @Override
    public <T> T readFileToClass(ChromosomeConfigFile config, Class<T> readClass, FileReader reader) throws IOException {
        Field[] fields = readClass.getFields();
        Properties props = new Properties();
        props.load(reader);

        try {
            T clazz = readClass.getDeclaredConstructor().newInstance();

            for (Field field : fields) {
                Field f = clazz.getClass().getDeclaredField(field.getName());
                f.setAccessible(true);
                f.set(clazz, props.get(f.getName()));
            }

            return clazz;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void writeClassToFile(ChromosomeConfigFile config, Class<T> readClass, File file) throws IOException {

        FileReader reader;
        FileWriter writer;

        Field[] fields = readClass.getDeclaredFields();
        Properties expectedProps = new Properties();
        Properties actualProps = new Properties();

        try {
            for (Field field : fields) {
                String name = field.getName();
                Object obj = field.get(new Object());

                expectedProps.put(name, obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        reader = new FileReader(file);
        actualProps.load(reader);
        reader.close();

        Properties toPrint;
        if (!actualProps.isEmpty()) {
            for (Object key : actualProps.keySet()) {
                if (!actualProps.containsKey(key)) {
                    actualProps.put(key, expectedProps.get(key));
                }
            }


            toPrint = actualProps;
        } else {
            toPrint = expectedProps;
        }

        writer = new FileWriter(file);
        toPrint.store(writer, "");
        writer.close();
    }
}
