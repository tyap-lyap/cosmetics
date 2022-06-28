package tlmetics.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class HeadSlimeModel extends Model {
    public final ModelPart slime;

    public HeadSlimeModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        this.slime = root.getChild("slime");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild(
                "slime", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2.5F, -37.05F, -3.0F, 6.0F, 5.0F, 6.0F)
                        .uv(0, 11).cuboid(-1.5F, -36.1F, -2.0F, 4.0F, 4.0F, 4.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        slime.render(matrices, vertices, light, overlay);
    }

}
