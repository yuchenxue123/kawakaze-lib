package cute.neko.kawakaze.mixins;

import cute.neko.event.EventManager;
import cute.neko.kawakaze.KawakazeLib;
import cute.neko.kawakaze.events.MinecraftInitializeEvent;
import cute.neko.kawakaze.events.MinecraftShutdownEvent;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "startGame", at = @At(value = "TAIL"))
    public void startGame(CallbackInfo ci) {
        EventManager.INSTANCE.callEvent(MinecraftInitializeEvent.INSTANCE);
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At(value = "INVOKE", target = "Lnet/minecraft/ILogAgent;logInfo(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    public void shutdown(CallbackInfo ci) {
        EventManager.INSTANCE.callEvent(MinecraftShutdownEvent.INSTANCE);
    }
}
