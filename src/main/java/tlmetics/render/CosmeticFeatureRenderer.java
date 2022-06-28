package tlmetics.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import tlmetics.cosmetics.Cosmetics;
import tlmetics.data.CosmeticsData;

public class CosmeticFeatureRenderer<T extends PlayerEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {

    public CosmeticFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider buffer, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(player.isInvisible()) return;
        CosmeticsData.getEntry(player.getUuid(), player.getName().getString()).ifPresent(entry -> {
            for (var cosmetic : entry.cosmetics) {
                Cosmetics.get(cosmetic).ifPresent(element -> {
                    matrices.push();
                    element.renderer().render(this.getContextModel(), matrices, buffer, light, player, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
                    matrices.pop();
                });
            }
        });
    }
}