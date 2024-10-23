package dev.microcontrollers.mountopacity;

import dev.microcontrollers.mountopacity.config.MountOpacityConfig;
//? if fabric
import net.fabricmc.api.ModInitializer;
//? if neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}

//? if neoforge
/*@Mod(value = "mountopacity", dist = Dist.CLIENT)*/
public class MountOpacity /*? if fabric {*/ implements ModInitializer /*?}*/ {
	//? if fabric {
	@Override
	public void onInitialize() {
		MountOpacityConfig.CONFIG.load();
	}
	//?}

	//? if neoforge {
    /*public MountOpacity() {
        MountOpacityConfig.CONFIG.load();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> MountOpacityConfig.configScreen(parent));
    }
	*///?}
}
