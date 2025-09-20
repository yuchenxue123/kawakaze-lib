package cute.neko.kawakaze.prepare.types

import cute.neko.kawakaze.prepare.Preparable
import cute.neko.kawakaze.registry.recipe.builder.RecipeBuilder

abstract class RecipePreparable : Preparable {
    override fun prepare() {
        onRecipeCreate(RecipeBuilder.creator())
    }

    abstract fun onRecipeCreate(creator: RecipeBuilder.RecipeCreator)
}