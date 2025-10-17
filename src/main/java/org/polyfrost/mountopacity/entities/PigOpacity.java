package org.polyfrost.mountopacity.entities;

import net.minecraft.entity.passive.EntityPig;
import org.polyfrost.mountopacity.MountOpacity;

public class PigOpacity extends OpacityHelper {
    public PigOpacity() {
        super(EntityPig.class, () -> MountOpacity.config.riddenPigOpacity);
    }
}
