package cute.neko.kawakaze.service.types

import cute.neko.event.EventListener
import cute.neko.event.handler
import cute.neko.kawakaze.events.RecipeRegisterEvent
import cute.neko.kawakaze.prepare.Prepares.RECIPE_PREPARES
import cute.neko.kawakaze.service.Service

object HandlerService : Service, EventListener {

    @Suppress("unused")
    private val handleRecipeRegister = handler<RecipeRegisterEvent> { handler ->
        RECIPE_PREPARES.forEach { prepare ->
            prepare.prepare()
            prepare.recipes.forEach { recipe ->
                recipe.register(handler)
            }
        }
    }

    override fun initialize() {
        // empty
    }

    override fun shutdown() {
        // empty
    }
}