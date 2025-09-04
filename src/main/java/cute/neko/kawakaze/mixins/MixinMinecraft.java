package cute.neko.kawakaze.mixins;

import cute.neko.kawakaze.KawakazeLib;
import cute.neko.kawakaze.config.ConfigSystem;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuchenxue
 * @date 2025/07/15
 */

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "startGame", at = @At(value = "TAIL"))
    public void startGame(CallbackInfo ci) {
        KawakazeLib.INSTANCE.start();
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At(value = "INVOKE", target = "Lnet/minecraft/ILogAgent;logInfo(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    public void shutdown(CallbackInfo ci) {
        KawakazeLib.INSTANCE.shutdown();
    }
}
