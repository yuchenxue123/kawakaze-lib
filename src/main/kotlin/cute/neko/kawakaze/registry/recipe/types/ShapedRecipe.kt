package cute.neko.kawakaze.registry.recipe.types

import cute.neko.kawakaze.events.RecipeRegisterEvent
import cute.neko.kawakaze.registry.recipe.Recipe
import cute.neko.kawakaze.registry.recipe.builder.RecipeBuilder
import cute.neko.kawakaze.prepare.types.RecipePreparable
import net.minecraft.ItemStack

class ShapedRecipe(
    output: ItemStack,
    objects: Array<Any>,
    lowestCrafting: Boolean
) : Recipe(output, objects, lowestCrafting) {
    override fun register(handler: RecipeRegisterEvent) {
        if (registered) return
        handler.shaped.invoke(output, lowestCrafting, objects)
        registered = true
    }

    /**
     * @return [RecipeBuilder.ShapedRecipeBuilder] just kid
     */
    fun applyTo(prepare: RecipePreparable): RecipeBuilder.ShapedRecipeBuilder {
        prepare.recipe(this)
        return RecipeBuilder.Companion.creator().shaped()
    }
}