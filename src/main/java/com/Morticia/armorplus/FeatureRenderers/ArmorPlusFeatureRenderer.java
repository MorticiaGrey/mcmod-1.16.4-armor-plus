package com.Morticia.armorplus.FeatureRenderers;

import com.Morticia.armorplus.Registry.ModItems;
import com.Morticia.armorplus.item.GearType;
import com.Morticia.armorplus.item.PlayerStats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorPlusFeatureRenderer<T extends PlayerEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {
    public static double[] cowlPos = {0.0D, -1.4D};

    public ArmorPlusFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        for (int i = 0; i <= entity.inventory.main.size() - 37 && !(i >= 12); i++) {
            Item item = entity.inventory.main.get(36 + i).getItem();

            if (!entity.isInvisible() && item instanceof PlayerStats) {
                if (((PlayerStats) item).getType() == GearType.COWL) { // 0.0D, -1.4D
                    renderItem(matrices, vertexConsumers, light, entity, 0.65F, 0.0, -1.7, 0.0D, item, GearType.COWL, false);
                } else if (((PlayerStats) item).getType() == GearType.BELT) {
                    renderItem(matrices, vertexConsumers, light, entity, 0.7F, 0.0, -0.74, 0.0D, item, GearType.BELT, false);
                } else if (((PlayerStats) item).getType() == GearType.BACKPACK) {
                    renderItem(matrices, vertexConsumers, light, entity, 0.525F, 0.0D, -0.75D, 0.0D, item, GearType.BACKPACK, false);
                } else if (((PlayerStats) item).getType() == GearType.GLOVES) { // -0.6D
                    renderItem(matrices, vertexConsumers, light, entity, 0.7F, 0.42, -1.16, 0.0D, item, GearType.GLOVES, true);
                    renderItem(matrices, vertexConsumers, light, entity, 0.7F, 0.42, -1.16, 0.0D, item, GearType.GLOVES, false);
                } else if (((PlayerStats) item).getType() == GearType.SHIRT) {
                    renderItem(matrices, vertexConsumers, light, entity, 0.7F, 0.0, -1.04, 0.0D, item, GearType.SHIRT, false);
                    renderItem(matrices, vertexConsumers, light, entity, 1.05F, 0.48, 0.13, 0.0D, ModItems.TEST_SLEEVE, GearType.SLEEVE, true);
                    renderItem(matrices, vertexConsumers, light, entity, 1.05F, 0.32, 0.13, 0.0D, ModItems.TEST_SLEEVE, GearType.SLEEVE, false);
                }
            }
        }
    }

    private void renderItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntity entity, float scale, double x, double y, double z, Item item, GearType gearType, boolean leftGlove) {
        matrices.push();
        if (gearType == GearType.COWL) {
            this.getContextModel().head.rotate(matrices);
        }
        if (gearType == GearType.GLOVES || gearType == GearType.SLEEVE) {
            if (leftGlove) {
                this.getContextModel().leftArm.rotate(matrices);
            } else {
                this.getContextModel().rightArm.rotate(matrices);
            }
        }
        matrices.translate(x, y, z);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        matrices.scale(scale, -scale, -scale);
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity, new ItemStack(item), ModelTransformation.Mode.HEAD, false, matrices, vertexConsumers, entity.world, light, LivingEntityRenderer.getOverlay(entity, 0.0F));
        matrices.pop();
    }
}
