package dev.microcontrollers.mountopacity.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MountOpacityConfig {
    public static final ConfigClassHandler<MountOpacityConfig> CONFIG = ConfigClassHandler.createBuilder(MountOpacityConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("mountopacity.json"))
                    .build())
            .build();

    @SerialEntry public float horseOpacity = 100F;
    @SerialEntry public float pigOpacity = 100F;
    @SerialEntry public float striderOpacity = 100F;
    @SerialEntry public float camelOpacity = 100F;

    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Component.translatable("mount-opacity.mount-opacity"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("mount-opacity.mount-opacity"))
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("mount-opacity.horse-opacity"))
                                .description(OptionDescription.of(Component.translatable("mount-opacity.horse-opacity.description")))
                                .binding(100F, () -> config.horseOpacity, newVal -> config.horseOpacity = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .formatValue(value -> Component.literal(String.format("%,.0f", value) + "%"))
                                        .range(0F, 100F)
                                        .step(1F))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("mount-opacity.pig-opacity"))
                                .description(OptionDescription.of(Component.translatable("mount-opacity.pig-opacity.description")))
                                .binding(100F, () -> config.pigOpacity, newVal -> config.pigOpacity = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .formatValue(value -> Component.literal(String.format("%,.0f", value) + "%"))
                                        .range(0F, 100F)
                                        .step(1F))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("mount-opacity.strider-opacity"))
                                .description(OptionDescription.of(Component.translatable("mount-opacity.strider-opacity.description")))
                                .binding(100F, () -> config.striderOpacity, newVal -> config.striderOpacity = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .formatValue(value -> Component.literal(String.format("%,.0f", value) + "%"))
                                        .range(0F, 100F)
                                        .step(1F))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("mount-opacity.camel-opacity"))
                                .description(OptionDescription.of(Component.translatable("mount-opacity.camel-opacity.description")))
                                .binding(100F, () -> config.camelOpacity, newVal -> config.camelOpacity = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .formatValue(value -> Component.literal(String.format("%,.0f", value) + "%"))
                                        .range(0F, 100F)
                                        .step(1F))
                                .build())
                        .build())
        )).generateScreen(parent);
    }
}
