package cute.neko.kawakaze.registry.recipe.types

import cute.neko.kawakaze.registry.recipe.Recipe
import cute.neko.kawakaze.registry.recipe.RecipeDelegate
import cute.neko.kawakaze.registry.recipe.builder.RecipeBuilder
import net.minecraft.ItemStack
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent

class ShapedRecipe(
    output: ItemStack,
    objects: Array<Any>,
    lowestCrafting: Boolean
) : Recipe(output, objects, lowestCrafting) {
    override fun register(event: RecipeRegistryEvent) {
        if (registered) return
        event.registerShapedRecipe(output, lowestCrafting, objects)
        registered = true
    }

    /**
     * @return [RecipeBuilder.ShapedRecipeBuilder] just kid
     */
    fun delegate(): RecipeBuilder.ShapedRecipeBuilder {
        RecipeDelegate.delegate(this)
        return RecipeBuilder.creator().shaped()
    }
}