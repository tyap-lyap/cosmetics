package tlmetics.render.cosmetics;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import tlmetics.Mod;
import tlmetics.model.CosmeticModels;

public class SimpleWingsRenderer extends CosmeticItemRenderer {
    private final String name;

    public SimpleWingsRenderer(String name) {
        this.name = name;
    }

    @Override
    public void render(BipedEntityModel<?> parent, MatrixStack matrices, VertexConsumerProvider buffer, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) {
            return;
        }
        matrices.push();
        parent.body.rotate(matrices);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderLayer.getEntityCutoutNoCull(Mod.id("textures/cosmetics/" + name + ".png")));
        CosmeticModels.WINGS_MODEL.setAngles(player, animationProgress);
        CosmeticModels.WINGS_MODEL.render(matrices, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();
    }
}
