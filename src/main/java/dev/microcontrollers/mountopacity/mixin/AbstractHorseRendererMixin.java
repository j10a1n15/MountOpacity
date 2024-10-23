package dev.microcontrollers.mountopacity.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseRenderer.class)
public class AbstractHorseRendererMixin {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/horse/AbstractHorse;Lnet/minecraft/client/renderer/entity/state/EquineRenderState;F)V", at = @At("HEAD"))
    private void horseColor(AbstractHorse abstractHorse, EquineRenderState equineRenderState, float f, CallbackInfo ci) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 0.5F);
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/horse/AbstractHorse;Lnet/minecraft/client/renderer/entity/state/EquineRenderState;F)V", at = @At("TAIL"))
    private void horseColorTwo(AbstractHorse abstractHorse, EquineRenderState equineRenderState, float f, CallbackInfo ci) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}
