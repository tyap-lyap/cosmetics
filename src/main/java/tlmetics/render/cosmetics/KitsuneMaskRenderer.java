package tlmetics.render.cosmetics;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import tlmetics.Mod;
import tlmetics.model.CosmeticModels;

public class KitsuneMaskRenderer extends CosmeticItemRenderer {
	public final String name;

	public KitsuneMaskRenderer(String name) {
		this.name = name;
	}

	@Override
	public void render(BipedEntityModel<?> parent, MatrixStack matrices, VertexConsumerProvider buffer, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		matrices.push();
		parent.head.rotate(matrices);
		if(player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem) {
			matrices.translate(-0.03125, -0.0625, 0);
		}
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderLayer.getEntityCutoutNoCull(Mod.id("textures/cosmetics/" + name + ".png")));
		CosmeticModels.KITSUNE_MASK_MODEL.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrices.pop();
	}
}
