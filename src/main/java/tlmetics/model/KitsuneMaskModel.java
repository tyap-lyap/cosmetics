package tlmetics.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class KitsuneMaskModel extends Model {
	public final ModelPart mask;

	public KitsuneMaskModel(ModelPart root) {
		super(RenderLayer::getEntityCutoutNoCull);
		this.mask = root.getChild("mask");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData mask = root.addChild("mask", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		mask.addChild("base", ModelPartBuilder.create().uv(0, 13).cuboid(-6.0F, -42.0F, -1.0F, 14.0F, 3.0F, 0.0F)
				.uv(18, 3).cuboid(2.0F, -47.0F, -2.0F, 3.0F, 2.0F, 1.0F)
				.uv(18, 0).cuboid(-3.0F, -47.0F, -2.0F, 3.0F, 2.0F, 1.0F)
				.uv(0, 8).cuboid(-0.5F, -41.0F, -4.0F, 3.0F, 3.0F, 2.0F)
				.uv(0, 0).cuboid(-3.0F, -45.0F, -2.0F, 8.0F, 7.0F, 1.0F), ModelTransform.of(-12.0F, 10.0F, 1.0F, -0.2094F, 1.5708F, 0.0F));

		mask.addChild("string1", ModelPartBuilder.create().uv(26, 0).cuboid(-2.0F, -29.0F, -9.0F, 1.0F, 7.0F, 0.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.7854F, 0.0F));
		mask.addChild("string2", ModelPartBuilder.create().uv(26, 0).cuboid(-2.0F, -29.0F, 9.0F, 1.0F, 7.0F, 0.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1309F, -0.7854F, 0.0F));

		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		mask.render(matrices, vertices, light, overlay);
	}
}
