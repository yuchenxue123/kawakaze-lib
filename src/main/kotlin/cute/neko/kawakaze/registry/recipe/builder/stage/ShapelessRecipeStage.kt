package cute.neko.kawakaze.registry.recipe.builder.stage

import net.minecraft.Item
import net.minecraft.ItemStack

interface ShapelessRecipeStage : ComplexRecipeBuildStage {
    fun input(item: ItemStack): ShapelessRecipeStage

    fun input(item: Item): ShapelessRecipeStage
}