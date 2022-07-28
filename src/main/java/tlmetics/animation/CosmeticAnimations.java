package tlmetics.animation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.animation.Transformation.Targets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.minecraft.client.render.entity.animation.AnimationHelper.rotate;
import static net.minecraft.client.render.entity.animation.AnimationHelper.translate;
import static net.minecraft.client.render.entity.animation.Transformation.Interpolations.*;

public class CosmeticAnimations {
	public static final Animation HALO_MOVIN = AnimationBuilder.withLength(4f).looping()
			.addAnimation("halo", new Transformation(Targets.ROTATE,
					new Keyframe(0f, rotate(-5f, 0f, 0f), LINEAR),
					new Keyframe(1f, rotate(0f, 0f, 5f), LINEAR),
					new Keyframe(2f, rotate(5f, 0f, 0f), LINEAR),
					new Keyframe(3f, rotate(0f, 0f, -5f), LINEAR),
					new Keyframe(4f, rotate(-5f, 0f, 0f), LINEAR))
			).build();

	public static final Animation HALO_UPNDOWN = AnimationBuilder.withLength(4f).looping()
			.addAnimation("halo", new Transformation(Targets.TRANSLATE,
					new Keyframe(0f, translate(0f, 0f, 0f), SPLINE),
					new Keyframe(1f, translate(0f, 1f, 0f), SPLINE),
					new Keyframe(2f, translate(0f, 0f, 0f), SPLINE),
					new Keyframe(3f, translate(0f, -1f, 0f), SPLINE),
					new Keyframe(4f, translate(0f, 0f, 0f), SPLINE))
			).build();

	public static final Animation WINGS_CLAP = AnimationBuilder.withLength(4f).looping()
			.addAnimation("left_wing", new Transformation(Targets.TRANSLATE,
					new Keyframe(0f, translate(0f, 0f, 0f), SPLINE),
					new Keyframe(1f, translate(0f, 0.5f, 0f), SPLINE),
					new Keyframe(2f, translate(0f, 0.5f, 0f), SPLINE),
					new Keyframe(4f, translate(0f, 0f, 0f), SPLINE))
			)
			.addAnimation("left_wing", new Transformation(Targets.ROTATE,
					new Keyframe(0f, rotate(0f, 0f, 0f), SPLINE),
					new Keyframe(1f, rotate(5f, 20f, 10f), SPLINE),
					new Keyframe(2f, rotate(2.5f, 15f, 7f), SPLINE),
					new Keyframe(2.5f, rotate(0f, -5f, 0f), SPLINE),
					new Keyframe(3f, rotate(-2.5f, -10f, 0f), SPLINE),
					new Keyframe(4f, rotate(0f, 0f, 0f), SPLINE))
			)
			.addAnimation("right_wing", new Transformation(Targets.TRANSLATE,
					new Keyframe(0f, translate(0f, 0f, 0f), SPLINE),
					new Keyframe(1f, translate(0f, 0.5f, 0f), SPLINE),
					new Keyframe(2f, translate(0f, 0.5f, 0f), SPLINE),
					new Keyframe(4f, translate(0f, 0f, 0f), SPLINE))
			)
			.addAnimation("right_wing", new Transformation(Targets.ROTATE,
					new Keyframe(0f, rotate(0f, 0f, 0f), SPLINE),
					new Keyframe(1f, rotate(5f, -20f, -10f), SPLINE),
					new Keyframe(2f, rotate(2.5f, -15f, -7f), SPLINE),
					new Keyframe(2.5f, rotate(0f, 5f, 0f), SPLINE),
					new Keyframe(3f, rotate(-2.5f, 10f, 0f), SPLINE),
					new Keyframe(4f, rotate(0f, 0f, 0f), SPLINE))
			).build();

	public static class AnimationBuilder {
		private final float length;
		private final Map<String, List<Transformation>> animations = Maps.newHashMap();
		private boolean looping;

		public static AnimationBuilder withLength(float length) {
			return new AnimationBuilder(length);
		}

		public AnimationBuilder(float length) {
			this.length = length;
		}

		public AnimationBuilder looping() {
			this.looping = true;
			return this;
		}

		public AnimationBuilder addAnimation(String part, Transformation transformation) {
			this.animations.computeIfAbsent(part, pt -> Lists.newArrayList()).add(transformation);
			return this;
		}

		public Animation build() {
			Map<String, List<Transformation>> map = Maps.newHashMap();

			animations.forEach((str, trs) -> {
				List<Transformation> list = Lists.newArrayList();
				trs.forEach(t -> {
					Keyframe[] sorted = sort(t);
					list.add(new Transformation(t.target(), sorted));
				});
				map.put(str, list);
			});

			return new Animation(length, looping, map);
		}

		private static Keyframe[] sort(Transformation t) {
			var frames = t.keyframes();
			Map<Float, Keyframe> timestampSlashFrame = Maps.newHashMap();

			for (Keyframe frame : frames) {
				timestampSlashFrame.put(frame.timestamp(), frame);
			}
			Object[] sortedFloats = timestampSlashFrame.keySet().toArray();
			Arrays.sort(sortedFloats);

			ArrayList<Keyframe> sortedFrames = Lists.newArrayList();

			for (var timestamp : sortedFloats) {
				sortedFrames.add(timestampSlashFrame.get((float) timestamp));
			}
			Keyframe[] casted = new Keyframe[sortedFrames.size()];

			for (int f = 0; f < sortedFrames.size(); f++) {
				casted[f] = sortedFrames.get(f);
			}
			return casted;
		}
	}
}
