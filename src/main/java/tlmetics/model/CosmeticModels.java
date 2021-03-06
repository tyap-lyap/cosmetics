package tlmetics.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import static tlmetics.model.CosmeticModelLayers.*;

public class CosmeticModels {
	public static final HeadSlimeModel SLIME_MODEL = new HeadSlimeModel(getModelPart(SLIME_LAYER));
	public static final HaloModel HALO_MODEL = new HaloModel(getModelPart(HALO_LAYER));
	public static final SimpleWingsModel WINGS_MODEL = new SimpleWingsModel(getModelPart(TECHNO_WINGS_LAYER));
	public static final KitsuneMaskModel KITSUNE_MASK_MODEL = new KitsuneMaskModel(getModelPart(KITSUNE_MASK_LAYER));

	public static ModelPart getModelPart(EntityModelLayer layer) {
		return MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer);
	}
}
