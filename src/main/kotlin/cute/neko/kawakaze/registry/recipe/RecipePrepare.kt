package cute.neko.kawakaze.registry.recipe

import cute.neko.kawakaze.registry.prepare.Prepare

abstract class RecipePrepare : Prepare() {
    val recipes = mutableListOf<Recipe>()

    override fun prepare() {
        onPrepareRecipe()
    }

    abstract fun onPrepareRecipe()

    /**
     * Add recipe to recipes
     */
    fun recipe(vararg recipe: Recipe) {
        recipes += recipe
    }
}