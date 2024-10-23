package dev.microcontrollers.mountopacity.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.microcontrollers.mountopacity.config.MountOpacityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
//? if >=1.21.2
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
//? if =1.21
/*import net.minecraft.util.FastColor;*/
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Strider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {

    @SuppressWarnings("rawtypes")
    @WrapOperation(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType transparentEntityRenderLayer(EntityModel instance, ResourceLocation resourceLocation, Operation<RenderType> original) {
        // let's not set this unless we absolutely have to
        // TODO: fix pig saddles
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isPassenger() &&
                ((MountOpacityConfig.CONFIG.instance().horseOpacity != 100 && resourceLocation.toString().contains("horse")) ||
                        (MountOpacityConfig.CONFIG.instance().pigOpacity != 100 && resourceLocation.toString().contains("pig")) ||
                        (MountOpacityConfig.CONFIG.instance().striderOpacity != 100 && resourceLocation.toString().contains("strider")) ||
                        (MountOpacityConfig.CONFIG.instance().camelOpacity != 100 && resourceLocation.toString().contains("camel")))) {
            return RenderType.entityTranslucent(resourceLocation);
        }
        return original.call(instance, resourceLocation);
    }

//    @ModifyArgs(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = /*? if =1.21 {*/ /*"Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V" *//*?} else {*/ "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V" /*?}*/))
//    private void transparentRiddenEntity(Args args, T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
//        if (Minecraft.getInstance().player == null) return;
//        if (entity instanceof AbstractHorse && entity.hasPassenger(Minecraft.getInstance().player) && MountOpacityConfig.CONFIG.instance().horseOpacity != 0) {
//            //? if >=1.21 {
//            args.set(4, FastColor.ARGB32.colorFromFloat(MountOpacityConfig.CONFIG.instance().horseOpacity / 100F, 1.0F, 1.0F, 1.0F));
//            //?} else {
//            /*args.set(7, MountOpacityConfig.CONFIG.instance().horseOpacity / 100F);
//            *///?}
//        } else if (entity instanceof Pig && entity.hasPassenger(Minecraft.getInstance().player) && MountOpacityConfig.CONFIG.instance().pigOpacity != 0) {
//            //? if >=1.21 {
//            args.set(4, FastColor.ARGB32.colorFromFloat(MountOpacityConfig.CONFIG.instance().pigOpacity / 100F, 1.0F, 1.0F, 1.0F));
//            //?} else {
//            /*args.set(7, MountOpacityConfig.CONFIG.instance().pigOpacity / 100F);
//            *///?}
//        } else if (entity instanceof Strider && entity.hasPassenger(Minecraft.getInstance().player) && MountOpacityConfig.CONFIG.instance().striderOpacity != 0) {
//            //? if >=1.21 {
//            args.set(4, FastColor.ARGB32.colorFromFloat(MountOpacityConfig.CONFIG.instance().striderOpacity / 100F, 1.0F, 1.0F, 1.0F));
//            //?} else {
//            /*args.set(7, MountOpacityConfig.CONFIG.instance().striderOpacity / 100F);
//             *///?}
//        } else if (entity instanceof Camel && entity.hasPassenger(Minecraft.getInstance().player) && MountOpacityConfig.CONFIG.instance().camelOpacity != 0) {
//            //? if >=1.21 {
//            args.set(4, FastColor.ARGB32.colorFromFloat(MountOpacityConfig.CONFIG.instance().camelOpacity / 100F, 1.0F, 1.0F, 1.0F));
//            //?} else {
//            /*args.set(7, MountOpacityConfig.CONFIG.instance().camelOpacity / 100F);
//             *///?}
//        }
//    }
//
//    // if it's 0, let's just cancel the rendering. this will also help prevent translucency sorting issues
//    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
//    private void cancelRiddenEntity(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CallbackInfo ci) {
//        if ((entity instanceof AbstractHorse && MountOpacityConfig.CONFIG.instance().horseOpacity == 0) ||
//                (entity instanceof Pig && MountOpacityConfig.CONFIG.instance().pigOpacity == 0) ||
//                (entity instanceof Strider && MountOpacityConfig.CONFIG.instance().striderOpacity == 0) ||
//                (entity instanceof Camel && MountOpacityConfig.CONFIG.instance().camelOpacity == 0)) {
//            ci.cancel();
//        }
//
//    }
}
