package cute.neko.kawakaze.registry.recipe.builder

import cute.neko.kawakaze.registry.recipe.builder.stage.RecipeBuildStage
import cute.neko.kawakaze.registry.recipe.builder.stage.ShapedRecipeStage
import cute.neko.kawakaze.registry.recipe.builder.stage.ShapelessRecipeStage
import cute.neko.kawakaze.registry.recipe.types.ShapedRecipe
import cute.neko.kawakaze.registry.recipe.types.ShapelessRecipe
import net.minecraft.Item
import net.minecraft.ItemStack

sealed class RecipeBuilder {
    protected lateinit var output: ItemStack
    protected var lowestCrafting: Boolean = false

    protected val patterns: MutableList<String> = mutableListOf()
    protected val inputsShaped: MutableMap<Char, ItemStack> = mutableMapOf()
    protected val inputsShapeless: MutableList<Any> = mutableListOf()

    companion object {
        fun creator(): RecipeCreator = RecipeCreator()
    }

    class RecipeCreator {
        fun shaped(): ShapedRecipeBuilder {
            return ShapedRecipeBuilder()
        }

        fun shapeless(): ShapelessRecipeBuilder {
            return ShapelessRecipeBuilder()
        }
    }

    class ShapedRecipeBuilder : RecipeBuilder(), ShapedBuilder, ShapedRecipeStage {
        override fun output(output: ItemStack): ShapedRecipeStage {
            this.output = output
            return this
        }

        override fun pattern(pattern: String): ShapedRecipeStage {
            require(patterns.size <= 3) { "Patterns size cannot larger than 3!" }
            patterns.add(pattern)
            return this
        }

        override fun input(key: Char, item: ItemStack): ShapedRecipeStage {
            inputsShaped[key] = item
            return this
        }

        override fun withLowestCrafting(): RecipeBuildStage<ShapedRecipe> {
            this.lowestCrafting = true
            return this
        }

        override fun build(): ShapedRecipe {
            val objects = (patterns + inputsShaped.flatMap { (key, item) -> listOf(key, item) }).toTypedArray<Any>()

            return ShapedRecipe(output, objects, lowestCrafting)
        }
    }

    class ShapelessRecipeBuilder : RecipeBuilder(), ShapelessBuilder, ShapelessRecipeStage {
        override fun output(output: ItemStack): ShapelessRecipeStage {
            this.output = output
            return this
        }

        override fun input(item: ItemStack): ShapelessRecipeStage {
            this.inputsShapeless.add(item)
            return this
        }

        override fun input(item: Item): ShapelessRecipeStage {
            this.inputsShapeless.add(item)
            return this
        }

        override fun withLowestCrafting(): RecipeBuildStage<ShapelessRecipe> {
            this.lowestCrafting = true
            return this
        }

        override fun build(): ShapelessRecipe {
            val objects = inputsShapeless.toTypedArray<Any>()

            return ShapelessRecipe(output, objects, lowestCrafting)
        }
    }
}