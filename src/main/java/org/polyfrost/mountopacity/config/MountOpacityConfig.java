package org.polyfrost.mountopacity.config;

import org.polyfrost.mountopacity.MountOpacity;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider;

public class MountOpacityConfig extends Config {
    public MountOpacityConfig() {
        super(MountOpacity.ID + ".json", MountOpacity.NAME, Category.QOL);
    }

    @Slider(
        title = "Ridden Horse Opacity (%)",
        description = "Change the opacity of the horse you're currently riding for visibility.",
        min = 0F, max = 100
    )
    public int riddenHorseOpacity = 100;

    @Slider(
        title = "Ridden Pig Opacity (%)",
        description = "Change the opacity of the pig you're currently riding for visibility.",
        min = 0F, max = 100
    )
    public int riddenPigOpacity = 100;

}
