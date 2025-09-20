package cute.neko.kawakaze.registry.recipe

import net.minecraft.ItemStack
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent

abstract class Recipe(
    protected val output: ItemStack,
    protected val objects: Array<Any>,
    protected val lowestCrafting: Boolean
) {
    protected var registered = false

    abstract fun register(event: RecipeRegistryEvent)
}