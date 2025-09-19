package cute.neko.kawakaze.registry.recipe.builder

import cute.neko.kawakaze.registry.recipe.builder.stage.ShapelessRecipeStage
import net.minecraft.ItemStack

interface ShapelessBuilder {
    fun output(output: ItemStack): ShapelessRecipeStage
}