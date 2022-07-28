package tlmetics.render.cosmetics;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public abstract class CosmeticItemRenderer {
	abstract public void render(BipedEntityModel<?> parent, MatrixStack matrices, VertexConsumerProvider buffer, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch);
}
