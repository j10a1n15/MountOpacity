package org.polyfrost.mountopacity.entities;

import dev.deftu.omnicore.api.client.OmniClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.function.Supplier;

public abstract class OpacityHelper {
    private final Class<? extends Entity> entityClass;
    private final Supplier<Integer> opacitySupplier;
    private boolean shouldMakeTransparent;

    protected OpacityHelper(Class<? extends Entity> entityClass, Supplier<Integer> opacitySupplier) {
        this.entityClass = entityClass;
        this.opacitySupplier = opacitySupplier;
    }

    void setTransparencyPre(Entity entity) {
        if (!entityClass.isInstance(entity)) return;

        float opacity = opacitySupplier.get();
        if (opacity >= 100f) return;

        EntityPlayerSP player = OmniClient.getPlayer();
        if (player == null) return;

        Entity ridingEntity = player.ridingEntity;
        if (ridingEntity == null) return;

        shouldMakeTransparent = ridingEntity == entity;
        if (shouldMakeTransparent) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            GlStateManager.color(1, 1, 1, opacity / 100f);
        }
    }

    void setTransparencyPost(Entity entity) {
        if (entityClass.isInstance(entity) && shouldMakeTransparent) {
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.color(1, 1, 1, 1);
        }
    }
}
