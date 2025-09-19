package cute.neko.kawakaze.registry.recipe.builder

import cute.neko.kawakaze.registry.recipe.builder.stage.ShapedRecipeStage
import net.minecraft.ItemStack

interface ShapedBuilder {
    fun output(output: ItemStack): ShapedRecipeStage
}