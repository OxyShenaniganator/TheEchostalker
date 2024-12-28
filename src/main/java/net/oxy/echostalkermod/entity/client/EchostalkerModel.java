package net.oxy.echostalkermod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.oxy.echostalkermod.EchostalkerMod;
import net.oxy.echostalkermod.entity.custom.EchostalkerEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class EchostalkerModel extends GeoModel<EchostalkerEntity> {
    @Override
    public ResourceLocation getModelResource(EchostalkerEntity echostalkerEntity) {
        return new ResourceLocation(EchostalkerMod.MOD_ID, "geo/echostalker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EchostalkerEntity echostalkerEntity) {
        return new ResourceLocation(EchostalkerMod.MOD_ID, "textures/entity/echostalker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EchostalkerEntity echostalkerEntity) {
        return new ResourceLocation(EchostalkerMod.MOD_ID, "animations/echostalker.animation.json");
    }

    @Override
    public void setCustomAnimations(EchostalkerEntity animatable, long instanceId, AnimationState<EchostalkerEntity> animationState) {

        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(head.getRotX() + entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(head.getRotY() + entityData.netHeadYaw() * Mth.DEG_TO_RAD);

        }

        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
