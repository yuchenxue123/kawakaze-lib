package cute.neko.kawakaze.registry.recipe

import cute.neko.kawakaze.events.RecipeRegisterEvent
import net.minecraft.ItemStack

abstract class Recipe(
    protected val output: ItemStack,
    protected val objects: Array<Any>,
    protected val lowestCrafting: Boolean
) {
    protected var registered = false

    abstract fun register(handler: RecipeRegisterEvent)
}