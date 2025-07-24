package dev.microcontrollers.mountopacity.hook;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.microcontrollers.mountopacity.config.MountOpacityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Strider;

public class EntityHook {
    private static Entity savedEntity = null;
    private static final LocalPlayer player = Minecraft.getInstance().player;

    public static <E extends Entity> void setEntity(E entity) {
        savedEntity = entity;
    }

    public static void clearEntity() {
        savedEntity = null;
    }

    public static Entity getEntity() {
        return savedEntity;
    }

    public static RenderType setRenderLayer(EntityModel instance, ResourceLocation resourceLocation, Operation<RenderType> original) {
        if (player != null && player.isPassenger() &&
                ((MountOpacityConfig.CONFIG.instance().horseOpacity   != 100 && savedEntity instanceof AbstractHorse) ||
                 (MountOpacityConfig.CONFIG.instance().pigOpacity     != 100 && savedEntity instanceof Pig) ||
                 (MountOpacityConfig.CONFIG.instance().striderOpacity != 100 && savedEntity instanceof Strider) ||
                 (MountOpacityConfig.CONFIG.instance().camelOpacity   != 100 && savedEntity instanceof Camel))) {
            return RenderType.entityTranslucent(resourceLocation);
        }
        else return instance != null ? original.call(instance, resourceLocation) : original.call(resourceLocation);
    }

    public static int setOpacity(int color) {
        if (player == null) return color;

        Entity entity = EntityHook.getEntity();
        if (entity == null || !entity.hasPassenger(player)) return color;

        float opacity = EntityHook.entityOpacity(entity);
        if (opacity < 0F) return color;

        return ARGB.colorFromFloat(opacity / 100F, 1.0F, 1.0F, 1.0F);
    }

    public static float entityOpacity(Entity localEntity) {
        return switch (localEntity) {
            case Camel camel -> MountOpacityConfig.CONFIG.instance().camelOpacity;
            case AbstractHorse abstractHorse -> MountOpacityConfig.CONFIG.instance().horseOpacity;
            case Pig pig -> MountOpacityConfig.CONFIG.instance().pigOpacity;
            case Strider strider -> MountOpacityConfig.CONFIG.instance().striderOpacity;
            case null, default -> -1F;
        };
    }
}
