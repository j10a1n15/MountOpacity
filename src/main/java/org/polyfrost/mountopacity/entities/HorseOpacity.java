package org.polyfrost.mountopacity.entities;

import net.minecraft.entity.passive.EntityHorse;
import org.polyfrost.mountopacity.MountOpacity;

public class HorseOpacity extends OpacityHelper {
    public HorseOpacity() {
        super(EntityHorse.class, () -> MountOpacity.config.riddenHorseOpacity);
    }
}

