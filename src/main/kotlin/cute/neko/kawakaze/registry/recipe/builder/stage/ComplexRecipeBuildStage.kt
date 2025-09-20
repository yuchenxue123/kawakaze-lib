package cute.neko.kawakaze.registry.recipe.builder.stage

import cute.neko.kawakaze.registry.recipe.Recipe

interface ComplexRecipeBuildStage<R : Recipe> : RecipeBuildStage<R> {
    fun withLowestCrafting(): RecipeBuildStage<R>
}