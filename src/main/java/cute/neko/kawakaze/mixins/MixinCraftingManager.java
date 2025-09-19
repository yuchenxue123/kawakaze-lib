package cute.neko.kawakaze.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import cute.neko.event.EventManager;
import cute.neko.kawakaze.events.RecipeRegisterEvent;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Comparator;
import java.util.List;

@Mixin(CraftingManager.class)
public abstract class MixinCraftingManager {

    @Shadow
    abstract ShapedRecipes addRecipe(ItemStack par1ItemStack, boolean include_in_lowest_crafting_difficulty_determination, Object... par2ArrayOfObj);

    @Shadow
    abstract ShapelessRecipes addShapelessRecipe(ItemStack par1ItemStack, boolean include_in_lowest_crafting_difficulty_determination, Object... par2ArrayOfObj);

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Collections;sort(Ljava/util/List;Ljava/util/Comparator;)V"))
    private <T> void hookRecipeRegistry(List<T> list, Comparator<? super T> c, Operation<Void> original) {
        EventManager.INSTANCE.callEvent(new RecipeRegisterEvent(
                this::addRecipe,
                this::addShapelessRecipe
        ));
    }
}
