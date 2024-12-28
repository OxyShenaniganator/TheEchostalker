package net.oxy.echostalkermod.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oxy.echostalkermod.EchostalkerMod;
import net.oxy.echostalkermod.entity.ModEntities;
import net.oxy.echostalkermod.entity.custom.EchostalkerEntity;

@Mod.EventBusSubscriber(modid = EchostalkerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ECHOSTALKER.get(), EchostalkerEntity.setAttributes());
    }

}
