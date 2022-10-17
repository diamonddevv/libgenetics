package net.diamonddev.libgenetics.core.mixin;

import com.google.gson.JsonElement;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public class IngredientMixin {

    @Inject(method = "fromJson", at = @At("HEAD"), cancellable = true)
    private static void libgenetics$allowNullIngredientAndDefaultToEmpty(JsonElement json, CallbackInfoReturnable<Ingredient> cir) {
        if (json == null || json.isJsonNull()) {
            cir.setReturnValue(Ingredient.EMPTY);
        }
    }
}
