package org.polyfrost.mountopacity.entities;

import net.minecraft.entity.Entity;
import org.polyfrost.oneconfig.api.event.v1.events.RenderLivingEvent;
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe;

public class EventHelper {

    OpacityHelper[] helpers = new OpacityHelper[] {
        new HorseOpacity(),
        new PigOpacity()
    };

    @Subscribe
    public void onRenderPre(RenderLivingEvent.Pre event) {
        for (OpacityHelper helper : helpers) {
            helper.setTransparencyPre((Entity) event.getEntity());
        }
    }

    @Subscribe
    public void onRenderPost(RenderLivingEvent.Post event) {
        for (OpacityHelper helper : helpers) {
            helper.setTransparencyPost((Entity) event.getEntity());
        }
    }

}
