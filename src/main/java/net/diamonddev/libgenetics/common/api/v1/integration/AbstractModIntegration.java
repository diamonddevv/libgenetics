package net.diamonddev.libgenetics.common.api.v1.integration;

import net.diamonddev.libgenetics.common.api.v1.util.IdentifierBuilder;
import net.diamonddev.libgenetics.core.libGeneticsMod;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Optionally-Dependent Mod Integration Framework Superclass. Based on BCLib's ModIntegration API (by Quiqueck and paulevs)
 *
 * WIP.
 */
public abstract class AbstractModIntegration {

    private final IdentifierBuilder idbuilder;
    public final String modid;
    public final Logger integrationlogger;

    /**
     * Creates a ModIntegration.
     *
     * @param targetModID - The Mod ID of the target mod.
     * @param associatedModName - The Name of THIS mod. For Example, if libGenetics was using an integration for DiaLabs, this would be "libGenetics".
     */
    protected AbstractModIntegration(String targetModID, String associatedModName) {
        this.idbuilder = new IdentifierBuilder(targetModID);
        this.modid = targetModID;
        String modname = FabricLoaderImpl.INSTANCE.getModContainer(this.modid).orElseThrow().getMetadata().getName();

        integrationlogger = LogManager.getLogger(associatedModName + "/Mod Integration - " + modname);
    }

    public boolean getModLoaded() {
        return FabricLoaderImpl.INSTANCE.isModLoaded(this.modid);
    }

    // Stuff Integrations can Do
    public Identifier buildIdentifier(String path) {
        return idbuilder.build(path);
    }
    public Registry<?> getRegistry(String path) {
        return Registry.REGISTRIES.get(this.idbuilder.build(path));
    }
    public <T> T getRegistryEntry(Registry<T> registry, String path) {
        return registry.get(idbuilder.build(path));
    }

    // Thanks to BCLib for these :)

    public Class<?> getClass(String classname) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            integrationlogger.warn(e.getMessage());
            if (libGeneticsMod.isDevEnv()) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    public Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
        if (clazz != null) {
            try {
                return clazz.getMethod(methodName, args);
            } catch (NoSuchMethodException | SecurityException e) {
                integrationlogger.warn(e);
                if (libGeneticsMod.isDevEnv()) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Object executeMethod(Object instance, Method method, Object... args) {
        if (method != null) {
            try {
                return method.invoke(instance, args);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                integrationlogger.error(e.getMessage());
                if (libGeneticsMod.isDevEnv()) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Object getAndExecuteStaticMethod(Class<?> clazz, String methodName, Object... args) {
        if (clazz != null) {
            try {
                Method method = clazz.getMethod(methodName);
                return method.invoke(null, args);
            } catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
                integrationlogger.warn(e);
                if (libGeneticsMod.isDevEnv()) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Object newInstance(Class<?> clazz, Object... args) {
        if (clazz != null) {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                if (constructor.getParameterCount() == args.length) {
                    try {
                        return constructor.newInstance(args);
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                             InvocationTargetException e) {
                        integrationlogger.error(e.getMessage());
                        if (libGeneticsMod.isDevEnv()) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    //
    public abstract void init();

}
