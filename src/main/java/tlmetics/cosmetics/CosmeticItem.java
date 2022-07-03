package tlmetics.cosmetics;

import tlmetics.render.cosmetics.CosmeticItemRenderer;

public record CosmeticItem(String name, CosmeticItemRenderer renderer) {

    public String getName() {
        return this.name;
    }

    public CosmeticItemRenderer getRenderer() {
        return this.renderer;
    }
}
