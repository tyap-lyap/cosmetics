package tlmetics.model;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import tlmetics.Mod;

import java.util.LinkedHashMap;
import java.util.Map;

public class CosmeticModelLayers {
	public static final Map<EntityModelLayer, TexturedModelData> LAYERS = new LinkedHashMap<>();
	public static final EntityModelLayer SLIME_LAYER = of("slime", HeadSlimeModel.getTexturedModelData());
	public static final EntityModelLayer HALO_LAYER = of("halo", HaloModel.getTexturedModelData());
	public static final EntityModelLayer TECHNO_WINGS_LAYER = of("techno_wings", SimpleWingsModel.getTexturedModelData());
	public static final EntityModelLayer KITSUNE_MASK_LAYER = of("kitsune_mask", KitsuneMaskModel.getTexturedModelData());

	public static void init() {
		CosmeticModelLayers.LAYERS.forEach((layer, data) -> EntityModelLayerRegistry.registerModelLayer(layer, () -> data));
	}

	private static EntityModelLayer of(String name, TexturedModelData data) {
		EntityModelLayer layer = new EntityModelLayer(Mod.id(name), "main");
		LAYERS.put(layer, data);
		return layer;
	}
}
