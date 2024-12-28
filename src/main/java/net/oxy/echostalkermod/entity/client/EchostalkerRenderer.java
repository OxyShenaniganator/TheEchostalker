package net.oxy.echostalkermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.oxy.echostalkermod.EchostalkerMod;
import net.oxy.echostalkermod.entity.custom.EchostalkerEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EchostalkerRenderer extends GeoEntityRenderer<EchostalkerEntity> {
    public EchostalkerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EchostalkerModel());

        addRenderLayer(new EchostalkerEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EchostalkerEntity animatable) {
        return new ResourceLocation(EchostalkerMod.MOD_ID, "textures/entity/echostalker.png");
    }

    @Override
    public void render(EchostalkerEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
