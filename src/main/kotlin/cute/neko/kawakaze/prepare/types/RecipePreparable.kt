package cute.neko.kawakaze.prepare.types

import cute.neko.kawakaze.prepare.Preparable
import cute.neko.kawakaze.registry.recipe.Recipe
import cute.neko.kawakaze.registry.recipe.builder.RecipeBuilder

abstract class RecipePreparable : Preparable {
    val recipes = mutableListOf<Recipe>()

    override fun prepare() {
        onRecipeCreate(RecipeBuilder.creator())
    }

    abstract fun onRecipeCreate(creator: RecipeBuilder.RecipeCreator)

    /**
     * Add recipe to recipes
     */
    fun recipe(vararg recipe: Recipe) {
        recipes += recipe
    }
}