package net.oxy.echostalkermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.oxy.echostalkermod.EchostalkerMod;
import net.oxy.echostalkermod.entity.custom.EchostalkerEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class EchostalkerEyesLayer extends GeoRenderLayer<EchostalkerEntity> {

    public static ResourceLocation TEXTURE = new ResourceLocation(EchostalkerMod.MOD_ID,
            "textures/entity/echostalker_eyes.png");

    public EchostalkerEyesLayer(GeoRenderer<EchostalkerEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, EchostalkerEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        packedLight = 15728880;
        RenderType eyesRenderType = RenderType.entityCutoutNoCull(TEXTURE);

        this.getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, eyesRenderType,
                bufferSource.getBuffer(eyesRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);

    }
}
