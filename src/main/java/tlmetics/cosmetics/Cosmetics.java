package tlmetics.cosmetics;

import tlmetics.render.cosmetics.CosmeticItemRenderer;
import tlmetics.render.cosmetics.HaloRenderer;
import tlmetics.render.cosmetics.HeadSlimeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cosmetics {
    private static final List<CosmeticItem> COSMETICS = new ArrayList<>();

    public static void init() {
        add("slime", new HeadSlimeRenderer("slime"));
        add("honey_slime", new HeadSlimeRenderer("honey_slime"));
        add("sakura_slime", new HeadSlimeRenderer("sakura_slime"));
        add("water_slime", new HeadSlimeRenderer("water_slime"));
        add("halo", new HaloRenderer());
//        add("kitsune_mask", new KitsuneMaskRenderer("kitsune_mask"));
//        add("blue_kitsune_mask", new KitsuneMaskRenderer("blue_kitsune_mask"));
    }

    private static void add(String name, CosmeticItemRenderer renderer) {
        Cosmetics.add(new CosmeticItem(name, renderer));
    }

    private static void add(CosmeticItem item) {
        COSMETICS.add(item);
    }

    public static Optional<CosmeticItem> get(String name) {
        for (var item : COSMETICS) if(item.getName().equals(name)) return Optional.of(item);
        return Optional.empty();
    }
}
