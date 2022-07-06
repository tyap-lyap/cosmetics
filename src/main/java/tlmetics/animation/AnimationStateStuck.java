package tlmetics.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.player.PlayerEntity;
import tlmetics.Mod;

import java.util.*;

public class AnimationStateStuck {
    private static List<Entry> stuck = new ArrayList<>();

    public static void clear() {
        if(!stuck.isEmpty()) {
            stuck = new ArrayList<>();
            Mod.log("Animation state stuck successfully cleared");
        }
    }

    public static AnimationState get(PlayerEntity player, Animation animation) {
        for(var entry : stuck) {
            if(entry.uuid.equals(player.getUuid()) && entry.animation.equals(animation)) {
                return entry.state;
            }
        }
        Entry entry = new Entry(player.getUuid(), animation, new AnimationState());
        stuck.add(entry);
        return entry.state;
    }

    public static class Entry {
        public UUID uuid;
        public Animation animation;
        public AnimationState state;

        public Entry(UUID uuid, Animation animation, AnimationState state) {
            this.uuid = uuid;
            this.animation = animation;
            this.state = state;
        }
    }
}
