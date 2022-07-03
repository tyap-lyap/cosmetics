package tlmetics.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import static net.minecraft.client.render.entity.animation.AnimationHelper.rotate;
import static net.minecraft.client.render.entity.animation.AnimationHelper.translate;
import static net.minecraft.client.render.entity.animation.Transformation.Interpolations.*;

public class CosmeticAnimations {
    public static final Animation HALO_MOVIN = Animation.Builder.create(4f).looping()
            .addBoneAnimation("halo", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0f, rotate(-5f, 0f, 0f), LINEAR),
                    new Keyframe(1f, rotate(0f, 0f, 5f), LINEAR),
                    new Keyframe(2f, rotate(5f, 0f, 0f), LINEAR),
                    new Keyframe(3f, rotate(0f, 0f, -5f), LINEAR),
                    new Keyframe(4f, rotate(-5f, 0f, 0f), LINEAR))
            ).build();

    public static final Animation HALO_UPNDOWN = Animation.Builder.create(4f).looping()
            .addBoneAnimation("halo", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0f, translate(0f, 0f, 0f), SPLINE),
                    new Keyframe(1f, translate(0f, 1f, 0f), SPLINE),
                    new Keyframe(2f, translate(0f, 0f, 0f), SPLINE),
                    new Keyframe(3f, translate(0f, -1f, 0f), SPLINE),
                    new Keyframe(4f, translate(0f, 0f, 0f), SPLINE))
            ).build();
}
