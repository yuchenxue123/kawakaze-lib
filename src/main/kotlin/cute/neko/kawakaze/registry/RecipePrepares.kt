package cute.neko.kawakaze.registry

import cute.neko.event.EventListener
import cute.neko.event.handler
import cute.neko.kawakaze.events.RecipeRegisterEvent
import cute.neko.kawakaze.registry.recipe.RecipePrepare

object RecipePrepares : EventListener {
    private val RECIPE_PREPARES = mutableListOf<RecipePrepare>()

    @Suppress("unused")
    private val handleRecipeRegister = handler<RecipeRegisterEvent> { handler ->
        RECIPE_PREPARES.forEach { prepare ->
            prepare.prepare()
            prepare.recipes.forEach { recipe ->
                recipe.register(handler)
            }
        }
    }

    /**
     * Register prepare
     */
    fun register(prepare: RecipePrepare) {
        RECIPE_PREPARES.add(prepare)
    }
}