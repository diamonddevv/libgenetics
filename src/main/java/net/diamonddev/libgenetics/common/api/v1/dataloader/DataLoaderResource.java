package net.diamonddev.libgenetics.common.api.v1.dataloader;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class DataLoaderResource {
    private final HashMap<String, JsonElement> objHash;
    private final DataLoaderResourceType type;
    private final Identifier id;

    public DataLoaderResource(DataLoaderResourceType type, Identifier identifier) {
        this.type = type;
        this.id = identifier;
        this.objHash = new HashMap<>();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public HashMap<String, JsonElement> getHash() {
        return objHash;
    }

    public JsonElement getObject(String jsonKey) {
        return objHash.get(jsonKey);
    }

    public String getString(String jsonKey) {
        return getObject(jsonKey).getAsString();
    }
    public int getInt(String jsonKey) {
        return getObject(jsonKey).getAsInt();
    }
    public boolean getBool(String jsonKey) {
        return getObject(jsonKey).getAsBoolean();
    }
    public Identifier getIdentifier(String jsonKey) {
        return new Identifier(getString(jsonKey));
    }
}
