package net.diamonddev.libgenetics.common.api.v1.dataloader.cognition;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class CognitionDataResource {
    private final HashMap<String, JsonElement> objHash;
    private final CognitionResourceType type;
    private final Identifier id;

    public CognitionDataResource(CognitionResourceType type, Identifier identifier) {
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

    public <T> T getAsClass(Class<T> clazz) {
        return new Gson().fromJson(hashToObject(this.getHash()), clazz);
    }

    private static JsonObject hashToObject(HashMap<String, JsonElement> objHash) {
        JsonObject obj = new JsonObject();
        objHash.forEach(obj::add);
        return obj;
    }
}
