package tlmetics.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import tlmetics.animation.CosmeticAnimations;

public class HaloModel extends Model {
    public final ModelPart root;
    public final ModelPart halo;

    public AnimationState state = new AnimationState();
    private static final Vec3f CACHE = new Vec3f();

    public HaloModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.root = root;
        this.halo = root.getChild("halo");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild(
                "halo", ModelPartBuilder.create()
                        .uv(-9, 0).cuboid(-4.5F, -1.0F, -4.5F, 9.0F, 0.0F, 9.0F),
                ModelTransform.pivot(0.0F, -10.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    public void setAngles(float animationProgress) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.state.update(animationProgress, 1.0F);
        this.state.run(st -> animate(this, CosmeticAnimations.HALO_UPNDOWN, st.getTimeRunning(), 1.0F, HaloModel.CACHE));
        this.state.update(animationProgress, 1.0F);
        this.state.run(st -> animate(this, CosmeticAnimations.HALO_MOVIN, st.getTimeRunning(), 1.0F, HaloModel.CACHE));
    }

    public ModelPart getPart() {
        return this.root;
    }

    public ModelPart getChild(String name) {
        return this.getPart().getChild(name);
    }

    public static void animate(HaloModel model, Animation animation, long runningTime, float strength, Vec3f animationCache) {
        float running = getRunningSeconds(animation, runningTime);

        animation.boneAnimations().forEach((str, transformations) -> {
            ModelPart part = model.getChild(str);

            transformations.forEach(transformation -> {
                Keyframe[] keyframes = transformation.keyframes();
                int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> running <= keyframes[index].timestamp()) - 1);
                int j = Math.min(keyframes.length - 1, i + 1);
                Keyframe keyframe = keyframes[i];
                Keyframe keyframe2 = keyframes[j];
                float h = running - keyframe.timestamp();
                float k = MathHelper.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                keyframe2.interpolation().apply(animationCache, k, keyframes, i, j, strength);
                transformation.target().apply(part, animationCache);
            });
        });
    }

    private static float getRunningSeconds(Animation animation, long runningTime) {
        float f = (float)runningTime / 1000.0F;
        return animation.looping() ? f % animation.lengthInSeconds() : f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        halo.render(matrices, vertices, light, overlay);
    }
}
