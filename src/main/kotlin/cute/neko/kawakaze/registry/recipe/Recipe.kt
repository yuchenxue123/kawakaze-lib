package cute.neko.kawakaze.registry.recipe

import cute.neko.kawakaze.events.RecipeRegisterEvent
import cute.neko.kawakaze.registry.recipe.builder.ShapedBuilder
import cute.neko.kawakaze.registry.recipe.builder.ShapelessBuilder
import cute.neko.kawakaze.registry.recipe.builder.stage.RecipeBuildStage
import cute.neko.kawakaze.registry.recipe.builder.stage.ShapedRecipeStage
import cute.neko.kawakaze.registry.recipe.builder.stage.ShapelessRecipeStage
import net.minecraft.Item
import net.minecraft.ItemStack

class Recipe(
    private val type: RecipeType,
    private val output: ItemStack,
    private val objects: Array<Any>,
    private val lowestCrafting: Boolean
) {

    private var registered = false

    fun register(handler: RecipeRegisterEvent) {
        if (registered) return
        type.register(handler, this)
        registered = true
    }

    fun apply(prepare: RecipePrepare) {
        prepare.recipe(this)
    }

    enum class RecipeType {
        SHAPED,
        SHAPELESS

        ;

        fun register(handler: RecipeRegisterEvent, recipe: Recipe) {
            when (this) {
                SHAPED -> {
                    recipe.apply {
                        handler.shaped.invoke(output, lowestCrafting, objects)
                    }
                }

                SHAPELESS -> {
                    recipe.apply {
                        handler.shapeless.invoke(output, lowestCrafting, objects)
                    }
                }
            }
        }
    }

    sealed class Builder {
        protected lateinit var output: ItemStack
        protected var lowestCrafting: Boolean = false

        protected val patterns: MutableList<String> = mutableListOf()
        protected val inputsShaped: MutableMap<Char, ItemStack> = mutableMapOf()
        protected val inputsShapeless: MutableList<Any> = mutableListOf()

        companion object {
            fun shaped(): ShapedBuilder {
                return Shaped()
            }

            fun shapeless(): ShapelessBuilder {
                return Shapeless()
            }
        }

        class Shaped : Builder(), ShapedBuilder, ShapedRecipeStage {
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

            override fun withLowestCrafting(): RecipeBuildStage {
                this.lowestCrafting = true
                return this
            }

            override fun build(): Recipe {
                val objects = (patterns + inputsShaped.flatMap { (key, item) -> listOf(key, item) }).toTypedArray<Any>()

                return Recipe(RecipeType.SHAPED, output, objects, lowestCrafting)
            }
        }

        class Shapeless : Builder(), ShapelessBuilder, ShapelessRecipeStage {
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

            override fun withLowestCrafting(): RecipeBuildStage {
                this.lowestCrafting = true
                return this
            }

            override fun build(): Recipe {
                val objects = inputsShapeless.toTypedArray<Any>()

                return Recipe(RecipeType.SHAPELESS, output, objects, lowestCrafting)
            }
        }
    }
}