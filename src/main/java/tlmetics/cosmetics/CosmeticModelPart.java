package tlmetics.cosmetics;

import tlmetics.render.cosmetics.CosmeticModelPartRenderer;

public record CosmeticModelPart(String name, CosmeticModelPartRenderer renderer) {

    public String getName() {
        return this.name;
    }

    public CosmeticModelPartRenderer getRenderer() {
        return this.renderer;
    }
}
