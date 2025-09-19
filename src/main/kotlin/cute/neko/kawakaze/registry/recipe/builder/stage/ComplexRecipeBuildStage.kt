package cute.neko.kawakaze.registry.recipe.builder.stage

interface ComplexRecipeBuildStage : RecipeBuildStage {
    fun withLowestCrafting() : RecipeBuildStage
}