package cute.neko.kawakaze.registry.recipe.builder.stage

import cute.neko.kawakaze.registry.recipe.types.ShapedRecipe
import net.minecraft.ItemStack

interface ShapedRecipeStage : ComplexRecipeBuildStage<ShapedRecipe> {
    fun pattern(pattern: String): ShapedRecipeStage

    fun input(key: Char, item: ItemStack): ShapedRecipeStage
}