package tlmetics.model;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import tlmetics.CosmeticsMod;

import java.util.LinkedHashMap;
import java.util.Map;

public class CosmeticModelLayers {
    public static final Map<EntityModelLayer, TexturedModelData> LAYERS = new LinkedHashMap<>();
    public static final EntityModelLayer SLIME_LAYER = of("slime", HeadSlimeModel.getTexturedModelData());
//    public static final EntityModelLayer KITSUNE_MASK_LAYER = of("kitsune_mask", KitsuneMaskModel.createBodyLayer());

    private static EntityModelLayer of(String name, TexturedModelData data) {
        EntityModelLayer layer = new EntityModelLayer(CosmeticsMod.id(name), "main");
        LAYERS.put(layer, data);
        return layer;
    }
}
