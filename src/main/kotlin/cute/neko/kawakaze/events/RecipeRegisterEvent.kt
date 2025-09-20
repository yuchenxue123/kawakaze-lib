package cute.neko.kawakaze.events

import cute.neko.event.Event
import net.minecraft.ItemStack

class RecipeRegisterEvent(
    val shaped: (ItemStack, Boolean, Array<Any>) -> Unit,
    val shapeless: (ItemStack, Boolean, Array<Any>) -> Unit
) : Event