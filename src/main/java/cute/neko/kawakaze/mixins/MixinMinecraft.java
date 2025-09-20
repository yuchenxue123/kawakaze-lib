package cute.neko.kawakaze.mixins;

import cute.neko.kawakaze.KawakazeLib;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "startGame", at = @At(value = "TAIL"))
    public void startGame(CallbackInfo ci) {
        KawakazeLib.INSTANCE.initialize();
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At(value = "INVOKE", target = "Lnet/minecraft/ILogAgent;logInfo(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    public void shutdown(CallbackInfo ci) {
        KawakazeLib.INSTANCE.shutdown();
    }
}
