package cute.neko.kawakaze

import com.google.common.eventbus.Subscribe
import cute.neko.kawakaze.service.Service
import net.minecraft.Item
import net.minecraft.ItemStack
import net.xiaoyu233.fml.reload.event.MITEEvents
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent

@Suppress("UnstableApiUsage")
object EventHandler : Service {

    @Suppress("unused")
    @Subscribe
    private fun onRecipeRegister(event: RecipeRegistryEvent) {
        event.registerShapelessRecipe(
            ItemStack(Item.swordIron),
            true,
            Item.stick
        )
    }

    override fun initialize() {
        MITEEvents.MITE_EVENT_BUS.register(this)
    }

    override fun shutdown() {
        MITEEvents.MITE_EVENT_BUS.unregister(this)
    }
}