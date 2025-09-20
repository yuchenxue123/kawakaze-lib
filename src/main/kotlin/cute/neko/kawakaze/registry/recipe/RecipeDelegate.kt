package cute.neko.kawakaze.registry.recipe

import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent

object RecipeDelegate {

    private val recipes = mutableListOf<Recipe>()

    fun delegate(recipe: Recipe) {
        recipes.add(recipe)
    }

    fun register(event: RecipeRegistryEvent) {
        recipes.forEach { it.register(event) }
    }
}