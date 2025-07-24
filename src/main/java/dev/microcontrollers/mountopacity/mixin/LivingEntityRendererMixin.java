package dev.microcontrollers.mountopacity.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.microcontrollers.mountopacity.config.MountOpacityConfig;
import dev.microcontrollers.mountopacity.hook.EntityHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Strider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin <S extends LivingEntityRenderState> {
    @SuppressWarnings("rawtypes")
    @WrapOperation(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType transparentEntityRenderLayer(EntityModel instance, ResourceLocation resourceLocation, Operation<RenderType> original, S renderState, boolean isVisible) {
        return EntityHook.setRenderLayer(instance, resourceLocation, original);
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"), index = 4)
    private int transparentRiddenEntity(int color) {
        return EntityHook.setOpacity(color);
    }

    // if it's 0, let's just cancel the rendering
    @Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void cancelRiddenEntity(LivingEntityRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CallbackInfo ci) {
        if ((EntityHook.getEntity() instanceof AbstractHorse && MountOpacityConfig.CONFIG.instance().horseOpacity == 0) ||
            (EntityHook.getEntity() instanceof Pig           && MountOpacityConfig.CONFIG.instance().pigOpacity == 0) ||
            (EntityHook.getEntity() instanceof Strider       && MountOpacityConfig.CONFIG.instance().striderOpacity == 0) ||
            (EntityHook.getEntity() instanceof Camel         && MountOpacityConfig.CONFIG.instance().camelOpacity == 0)) {
            ci.cancel();
        }
    }
}
