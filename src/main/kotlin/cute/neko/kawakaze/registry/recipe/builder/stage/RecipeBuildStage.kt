package cute.neko.kawakaze.registry.recipe.builder.stage

import cute.neko.kawakaze.registry.recipe.Recipe

interface RecipeBuildStage {
    fun build(): Recipe
}