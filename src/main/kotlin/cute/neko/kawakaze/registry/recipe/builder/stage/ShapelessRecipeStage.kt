package cute.neko.kawakaze.registry.recipe.builder.stage

import cute.neko.kawakaze.registry.recipe.types.ShapelessRecipe
import net.minecraft.Item
import net.minecraft.ItemStack

interface ShapelessRecipeStage : ComplexRecipeBuildStage<ShapelessRecipe> {
    fun input(item: ItemStack): ShapelessRecipeStage

    fun input(item: Item): ShapelessRecipeStage
}