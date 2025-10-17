package org.polyfrost.mountopacity;

//#if FABRIC
//$$ import net.fabricmc.api.ModInitializer;
//#elseif FORGE
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#endif

import org.polyfrost.mountopacity.config.MountOpacityConfig;
import org.polyfrost.mountopacity.entities.EventHelper;
import org.polyfrost.oneconfig.api.event.v1.EventManager;

//#if FORGE-LIKE
@Mod(modid = MountOpacity.ID, name = MountOpacity.NAME, version = MountOpacity.VERSION)
//#endif
public class MountOpacity
    //#if FABRIC
    //$$ implements ModInitializer
    //#endif
{
    public static final String ID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String VERSION = "@MOD_VERSION@";

    public static MountOpacityConfig config;

    //#if FABRIC
    //$$ @Override
    //#elseif FORGE
    @Mod.EventHandler
    //#endif
    public void onInitialize(
        //#if FORGE
        FMLInitializationEvent event
        //#endif
    ) {
        config = new MountOpacityConfig();

        EventManager.INSTANCE.register(new EventHelper());
    }
}
