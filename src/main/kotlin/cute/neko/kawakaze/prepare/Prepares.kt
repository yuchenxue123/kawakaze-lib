package cute.neko.kawakaze.prepare

import cute.neko.kawakaze.prepare.types.ConfigPreparable
import cute.neko.kawakaze.prepare.types.RecipePreparable

object Prepares {
    val RECIPE_PREPARES = mutableListOf<RecipePreparable>()
    val CONFIG_PREPARES = mutableListOf<ConfigPreparable>()

    fun register(preparable: RecipePreparable) {
        RECIPE_PREPARES += preparable
    }

    fun register(preparable: ConfigPreparable) {
        CONFIG_PREPARES += preparable
    }
}