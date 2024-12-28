package net.oxy.echostalkermod.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oxy.echostalkermod.EchostalkerMod;
import net.oxy.echostalkermod.entity.custom.EchostalkerEntity;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EchostalkerMod.MOD_ID);


    public static final RegistryObject<EntityType<EchostalkerEntity>> ECHOSTALKER =
            ENTITY_TYPE.register("echostalker",
                    () -> EntityType.Builder.of(EchostalkerEntity::new, MobCategory.MISC)
                            .sized(1.5f, 3)
                            .build(new ResourceLocation(EchostalkerMod.MOD_ID, "echostalker").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }

}
