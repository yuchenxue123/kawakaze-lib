package cute.neko.kawakaze

import com.google.common.eventbus.Subscribe
import cute.neko.kawakaze.prepare.Prepares
import cute.neko.kawakaze.prepare.types.RecipePreparable
import cute.neko.kawakaze.registry.recipe.RecipeDelegate
import cute.neko.kawakaze.service.Service
import net.xiaoyu233.fml.reload.event.MITEEvents
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent

@Suppress("UnstableApiUsage")
object EventHandler : Service{

    @Subscribe
    @Suppress("unused")
    private fun onRecipeRegister(event: RecipeRegistryEvent) {
        Prepares.RECIPE_PREPARES.forEach(RecipePreparable::prepare)
        RecipeDelegate.register(event)
    }

    override fun initialize() {
        MITEEvents.MITE_EVENT_BUS.register(this)
    }

    override fun shutdown() {
        MITEEvents.MITE_EVENT_BUS.unregister(this)
    }
}