package tlmetics.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import tlmetics.animation.AnimationStateStuck;
import tlmetics.animation.CosmeticAnimations;

public class SimpleWingsModel extends Model {
    public final ModelPart root;
    public final ModelPart leftWing;
    public final ModelPart rightWing;

    private static final Vec3f CACHE = new Vec3f();

    public SimpleWingsModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.root = root;
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("left_wing", ModelPartBuilder.create().uv(0, 0)
            .cuboid(-24.0F, -9.0F, 0.0F, 24.0F, 16.0F, 0.0F),
            ModelTransform.pivot(-3.0F, 4.0F, 5.0F)
        );
        root.addChild("right_wing", ModelPartBuilder.create().uv(0, 16)
            .cuboid(0.0F, -9.0F, 0.0F, 24.0F, 16.0F, 0.0F),
            ModelTransform.pivot(3.0F, 4.0F, 5.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    public void setAngles(PlayerEntity player, float animationProgress) {
        var clap = AnimationStateStuck.get(player, CosmeticAnimations.WINGS_CLAP);
        clap.startIfNotRunning(player.age);
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        clap.update(animationProgress, 1.0F);
        clap.run(st -> animate(this, CosmeticAnimations.WINGS_CLAP, st.getTimeRunning(), 1.0F, SimpleWingsModel.CACHE));
    }

    public ModelPart getPart() {
        return this.root;
    }

    public ModelPart getChild(String name) {
        return this.getPart().getChild(name);
    }

    public static void animate(SimpleWingsModel model, Animation animation, long runningTime, float strength, Vec3f animationCache) {
        float running = getRunningSeconds(animation, runningTime);

        animation.boneAnimations().forEach((modelPartName, transformations) -> {
            transformations.forEach(transformation -> {
                Keyframe[] keyframes = transformation.keyframes();
                int last = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> running <= keyframes[index].timestamp()) - 1);
                int next = Math.min(keyframes.length - 1, last + 1);
                Keyframe lastFrame = keyframes[last];
                Keyframe nextFrame = keyframes[next];
                float time = running - lastFrame.timestamp();
                float delta = MathHelper.clamp(time / (nextFrame.timestamp() - lastFrame.timestamp()), 0.0F, 1.0F);
                nextFrame.interpolation().apply(animationCache, delta, keyframes, last, next, strength);
                transformation.target().apply(model.getChild(modelPartName), animationCache);
            });
        });
    }

    private static float getRunningSeconds(Animation animation, long runningTime) {
        float secs = (float)runningTime / 1000.0F;
        return animation.looping() ? secs % animation.lengthInSeconds() : secs;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        leftWing.render(matrices, vertices, light, overlay);
        rightWing.render(matrices, vertices, light, overlay);
    }
}
