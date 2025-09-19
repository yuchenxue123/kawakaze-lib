package cute.neko.kawakaze.registry.recipe.builder.stage

import net.minecraft.ItemStack

interface ShapedRecipeStage : ComplexRecipeBuildStage {
    fun pattern(pattern: String): ShapedRecipeStage

    fun input(key: Char, item: ItemStack): ShapedRecipeStage
}