package net.diamonddev.libgenetics.common.api.v1.recipe.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Nullable;

public class CountedIngredient {

    public static final CountedIngredient EMPTY = new CountedIngredient(Ingredient.EMPTY, 0);

    private static final String INGREDIENT_KEY = "ingredient";
    private static final String COUNT_KEY = "count";

    private final Ingredient ing;
    private final int cnt;

    /**
    Example CountedIngredient Json:

     <pre>
     "CountedIngredientObject": {
        "ingredient": {
            "item": "minecraft:dirt"
        },
        "count: 12
     }
     </pre>


    Naturally, it also works with tags, as the "ingredient" key is an instance of Ingredient.

    @see Ingredient
     **/
    public CountedIngredient(Ingredient ingredient, int count) {
        this.ing = ingredient;
        this.cnt = count;
    }

    public Ingredient getIngredientComponent() {
        return this.ing;
    }

    public int getCountComponent() {
        return this.cnt;
    }

    public static CountedIngredient fromJson(@Nullable JsonElement json) {
        Ingredient ingredient;
        int count;

        if (json != null && !json.isJsonNull()) {
            if (json.isJsonObject()) {
                JsonObject obj = json.getAsJsonObject();
               if (obj.has(INGREDIENT_KEY)) {
                   ingredient = Ingredient.fromJson(obj.get(INGREDIENT_KEY));
                   count = obj.has(COUNT_KEY) ? obj.get(COUNT_KEY).getAsInt() : 0;
                   if (count == 0) count = 1;
                   return new CountedIngredient(ingredient, count);
               } else {
                   throw new JsonSyntaxException("Expected Ingredient Object, but none was found! (This could be because a normal Ingredient Format was used - this will not work, use a CountedIngredient Object Instead.)");
               }
            } else {
                throw new JsonSyntaxException("JSON was not null, but an Object was not found!");
            }
        }

        return EMPTY;
    }
    public static CountedIngredient fromPacket(PacketByteBuf buf) {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        int count = buf.readInt();
        return new CountedIngredient(ingredient, count);
    }
    public void toPacket(PacketByteBuf buf) {
        this.ing.write(buf);
        buf.writeInt(this.cnt);
    }

    public boolean test(ItemStack stack) {
        boolean ingredient = this.ing.test(stack);
        boolean count = this.cnt <= stack.getCount();
        return ingredient && count;
    }
}
