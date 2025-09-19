package cute.neko.kawakaze.events

import cute.neko.event.Event
import net.minecraft.ItemStack
import net.minecraft.ShapedRecipes
import net.minecraft.ShapelessRecipes

class RecipeRegisterEvent(
    val shaped: (ItemStack, Boolean, Array<Any>) -> ShapedRecipes,
    val shapeless: (ItemStack, Boolean, Array<Any>) -> ShapelessRecipes
) : Event